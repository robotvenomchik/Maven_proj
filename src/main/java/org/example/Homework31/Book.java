package org.example.Homework31;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Column(unique = true)  // Забезпечує унікальність поля IBN
    private String ibn;      // Міжнародний номер книги (IBN)

    private int year;        // Рік написання

    public Book() {}

    public Book(String title, String author, String ibn, int year) {
        this.title = title;
        this.author = author;
        this.ibn = ibn;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIbn() {
        return ibn;
    }

    public void setIbn(String ibn) {
        this.ibn = ibn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(ibn, book.ibn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, ibn, year);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ibn='" + ibn + '\'' +
                ", year=" + year +
                '}';
    }
}

