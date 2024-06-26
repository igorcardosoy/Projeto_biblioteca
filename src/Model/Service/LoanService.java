package Model.Service;

import Exceptions.*;
import Model.Dao.BookDaoImpl;
import Model.Dao.LoanDao;
import Model.Dao.LoanDaoImpl;
import Model.Dao.UserDaoImpl;
import Model.Entity.Book;
import Model.Entity.Loan;
import Model.Entity.User;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class LoanService {
    private final LoanDao loanDao;
    private final BookDaoImpl bookDao;
    private final UserDaoImpl userDao;

    public LoanService() {
        this.loanDao = LoanDaoImpl.getInstance();
        this.bookDao = BookDaoImpl.getInstance();
        this.userDao = UserDaoImpl.getInstance();
    }

    public Boolean insert(Loan loan) throws InsertFailedException, InvalidValuesException {
        if (loan.getBook().isAvailable() || loan.getBailee().getPenalty() > 0 || loan.getReturnDate().isBefore(LocalDate.now()))
            throw new InvalidValuesException("Dados do emprestimo inválidos.");

        bookDao.update(loan.getBook());
        return loanDao.insert(loan);
    }

    public Boolean delete(Loan loan) throws DeleteFailedException, InvalidValuesException {
        if (loan == null)
            throw new InvalidValuesException("Emprestimo inválido.");

        bookDao.update(new Book(loan.getBook().getTitle(),
                loan.getBook().getEdition(), loan.getBook().getAuthor(), true, loan.getBook().getTimesBorrowed()));
        return loanDao.delete(loan);
    }

    public List<Loan> getAll(){
        return loanDao.getAll();
    }

    public List<Loan> getByBook(Book book) throws InvalidValuesException {
        if (book == null)
            throw new InvalidValuesException("Livro inexistente.");

        ArrayList <Loan> loans = new ArrayList<>();

        for (Loan l : loanDao.getAll()){
            if (l.getBook().equals(book)){
                loans.add(l);
            }
        }
        return loans;
    }

    public List<User> getByUser(User user) throws InvalidValuesException {
        if (user == null)
            throw new InvalidValuesException("Usuário inexistente.");

        ArrayList <User> users = new ArrayList<>();
        for (User u : userDao.getAll()){
            if (u.getEmail().equals(user.getEmail())){
                users.add(u);
            }
        }
        return users;
    }

    public List<Loan> getByReturnDate(LocalDate date) throws InvalidDateException{
        if (date == null)
            throw new InvalidDateException("Data de devolução invalida.");

        ArrayList <Loan> loans = new ArrayList<>();
        for (Loan l : loanDao.getAll()){
            if (l.getReturnDate().isEqual(date)){
                loans.add(l);
            }
        }
        return loans;
    }

    public List<Loan> getByAcquiredDate(LocalDate date) throws InvalidDateException {
        if (date == null)
            throw new InvalidDateException("Data de aquisição invalida.");

        ArrayList <Loan> loans = new ArrayList<>();
        for (Loan l : loanDao.getAll()){
            if (l.getAcquiredDate().isEqual(date)){
                loans.add(l);
            }
        }
        return loans;
    }

    public List<Loan> getLateLoans() {
        ArrayList <Loan> loans = new ArrayList<>();
        for (Loan l : loanDao.getAll()){
            if (l.isDelayed()){
                loans.add(l);
            }
        }
        return loans;
    }

    public List<Book> getLateBooks() {
        ArrayList <Book> lateBooks = new ArrayList<>();
        for(Loan l : getLateLoans()){
            lateBooks.add(l.getBook());
        }
        return lateBooks;
    }

    public List<User> getLateUsers() {
        ArrayList <User> lateUsers = new ArrayList<>();
        for(Loan l : getLateLoans()){
            lateUsers.add(l.getBailee());
        }
        return lateUsers;
    }
}