package com.example.springCloud.test;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/**
 * 行为参数传递
 */
public class BookTest {


    @Test
    public void testFilterBook() {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setAddress("123213123");
        book1.setAuthor("66121232132");
        books.add(book1);
        List<Book> bookList = filterBooks(books, book -> "66121232132".equals(book.getAddress()));
        System.out.println(bookList);
    }

    public static List<Book> filterBooks(List<Book> books, Predicate<Book> p) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (p.test(book)) {
                result.add(book);
            }
        }
        return result;
    }

    @Data
    class Book {
        private String author;
        private String address;

    }
}
