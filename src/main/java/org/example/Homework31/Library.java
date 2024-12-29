package org.example.Homework31;
import jakarta.persistence.*;
import java.util.List;

public class Library {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("hillel-persistence-unit");

    public void addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getAuthor() == null || book.getIbn() == null) {
            throw new IllegalArgumentException("Invalid book object or null IBN");
        }

        if (getBookByIbn(book.getIbn()) != null) {
            throw new IllegalArgumentException("Book with this IBN already exists");
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean removeBook(Book book) {
        if (book == null || book.getId() == null) {
            return false;
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Book managedBook = em.find(Book.class, book.getId());
            if (managedBook != null) {
                em.remove(managedBook);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } finally {
            em.close();
        }
    }

    public List<Book> getBooks() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } finally {
            em.close();
        }
    }

    public int getBookCount() {
        EntityManager em = emf.createEntityManager();
        try {
            return ((Number) em.createQuery("SELECT COUNT(b) FROM Book b").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Book getBookByIbn(String ibn) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Book b WHERE b.ibn = :ibn", Book.class)
                    .setParameter("ibn", ibn)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean removeAllBooks() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            int deletedCount = em.createQuery("DELETE FROM Book b").executeUpdate();
            em.getTransaction().commit();
            return deletedCount > 0;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

}

