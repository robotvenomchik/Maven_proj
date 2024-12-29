package org.example.Homework30;


import jakarta.persistence.*;

import java.util.List;

public class StudentDao implements GenericDao<Student, Long> {


    private EntityManagerFactory emf;

    public StudentDao() {
        this.emf = Persistence.createEntityManagerFactory("hillel-persistence-unit");
    }
    @Override
    public void save(Student entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Student findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Student student = em.find(Student.class, id);
        em.close();
        return student;
    }

    @Override
    public Student findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class);
        query.setParameter("email", email);
        Student student = query.getSingleResult();
        em.close();
        return student;
    }

    @Override
    public List<Student> findAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
        List<Student> students = query.getResultList();
        em.close();
        return students;
    }

    @Override
    public Student update(Student entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student updatedStudent = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return updatedStudent;
    }

    @Override
    public boolean deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = em.find(Student.class, id);
        if (student != null) {
            em.remove(student);
            em.getTransaction().commit();
            em.close();
            return true;
        }
        em.close();
        return false;
    }
}

