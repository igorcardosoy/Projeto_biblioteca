package Model.Dao;

import Model.Entity.Book;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BookDaoImpl implements BookDao{
    private static BookDaoImpl instance;
    private static Set<Book> dataset;

    public BookDaoImpl(){
        dataset = new TreeSet<>();
    }

    public static BookDaoImpl getInstance(){
        if(instance == null){
            instance = new BookDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean insert(Book book) {
        //TODO
        return false;
    }

    @Override
    public boolean delete(Book book) {
        //TODO
        return false;
    }

    @Override
    public List<Book> getAll() {
        //TODO
        return null;
    }

    @Override
    public Book getById(String title, int edition) {
        //TODO
        return null;
    }
}