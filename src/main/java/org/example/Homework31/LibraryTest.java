package org.example.Homework31;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private static Library library;

    @BeforeAll
    static void setUp() {
        library = new Library();
    }
    @AfterEach
    void tearDown() {
        library.removeAllBooks();
    }

    @Test
    @Order(1)
    void testAddBookWithUniqueIBN() {
        Book book1 = new Book("Departure", "Alan Wake", "1234567890", 2020);
        library.addBook(book1);
        assertEquals(1, library.getBookCount(), "There should be 1 book in the library");
    }

    @Test
    @Order(1)
    void testAddSecondBookWithUniqueIBN() {
        Book book1 = new Book("Departure", "Alan Wake", "1234567890", 2020);
        Book book2 = new Book("Eneida", "Ivan Kotliarevsky", "0987654321", 2021);

        library.addBook(book1);
        library.addBook(book2);

        assertEquals(2, library.getBookCount(), "There should be 2 books in the library");
    }

    @Test
    @Order(1)
    void testAddBookWithDuplicateIBN() {
        Book book1 = new Book("Departure", "Alan Wake", "1234567890", 2020);
        Book book2 = new Book("Eneida", "Ivan Kotliarevsky", "1234567890", 2021);

        library.addBook(book1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(book2));
        assertEquals("Book with this IBN already exists", exception.getMessage(), "Duplicate IBN should throw an exception");
    }

    @Test
    @Order(2)
    void testAddBookWithNullIBN() {
        Book book = new Book("Taras Shevchenko", "Kobzar", null, 2020);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
        assertEquals("Invalid book object or null IBN", exception.getMessage(), "Book with null IBN should throw an exception");
    }

    @Test
    @Order(3)
    void testGetBookByIbn() {
        Book book = new Book("Taras Shevchenko", "Kobzar", "1234567890", 2020);
        library.addBook(book);

        Book fetchedBook = library.getBookByIbn("1234567890");
        assertNotNull(fetchedBook, "Book should be found by IBN");
    }

    @Test
    @Order(4)
    void testGetBookByIbnTitle() {
        Book book = new Book("The Forest Song", "Lesya Ukraninka", "1234567890", 2020);
        library.addBook(book);

        Book fetchedBook = library.getBookByIbn("1234567890");
        assertEquals("The Forest Song", fetchedBook.getTitle(), "Fetched book title should match");
    }

    @Test
    @Order(5)
    void testRemoveBookByIbn() {
        Book book = new Book("Black Council", "Panteleimon Kulish", "1234567890", 2020);
        library.addBook(book);

        Book fetchedBook = library.getBookByIbn("1234567890");
        assertTrue(library.removeBook(fetchedBook), "Book should be removed successfully");
    }

    @Test
    @Order(6)
    void testRemoveBookByIbnWhenNoSuchBook() {
        Book book = new Book("Instytutka", "Marko Vovchok", "1234567890", 2020);
        library.addBook(book);

        Book nonExistingBook = new Book("Non Existing Title", "Non Existing Author", "0987654321", 2021);
        assertFalse(library.removeBook(nonExistingBook), "Removing a non-existing book should return false");
    }

    @Test
    @Order(7)
    void testGetBooksEmptyLibrary() {
        assertTrue(library.getBooks().isEmpty(), "Library should return an empty list when no books are present");
    }

    @Test
    @Order(8)
    void testGetBookCountEmptyLibrary() {
        assertEquals(0, library.getBookCount(), "Book count should be 0 for an empty library");
    }
}

