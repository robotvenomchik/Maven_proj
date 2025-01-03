package org.example.Homework30;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentDao studentDao = new StudentDao();

        Student student1 = new Student();
        student1.setFirstName("Symon");
        student1.setLastName("Petlura");
        student1.setEmail("petlura221.symon@unr.com");

        Student student2 = new Student();
        student2.setFirstName("Pepa");
        student2.setLastName("Pig");
        student2.setEmail("pepa_pig221@hrushka.com");

        studentDao.save(student1);
        studentDao.save(student2);



        Homework homework1 = new Homework();
        homework1.setDescription("send to your teacher");
        homework1.setDeadline(LocalDate.now().plusDays(5));
        homework1.setMark(85);
        homework1.setStudent(student2);

        Homework homework2 = new Homework();
        homework2.setDescription("Java project");
        homework2.setDeadline(LocalDate.now().plusDays(10));
        homework2.setMark(90);
        homework2.setStudent(student1);

        student2.addHomework(homework1);
        student1.addHomework(homework2);
        studentDao.update(student1);
        studentDao.update(student2);

        List<Student> students = studentDao.findAll();
        System.out.println("All Students:");
        students.forEach(System.out::println);


        Student retrievedStudent = studentDao.findByEmail("petlura221.symon@unr.com");
        System.out.println("Retrieved Student:");
        System.out.println(retrievedStudent);

        retrievedStudent.setLastName("UpdatedLastName");
        studentDao.update(retrievedStudent);

        Student updatedStudent = studentDao.findById(retrievedStudent.getId());
        System.out.println("Updated Student:");
        System.out.println(updatedStudent);



        boolean isDeleted = studentDao.deleteById(student2.getId());
        System.out.println("Deleted Student with ID " + student2.getId() + ": " + isDeleted);



        List<Student> remainingStudents = studentDao.findAll();
        System.out.println("Remaining Students:");
        remainingStudents.forEach(System.out::println);



        studentDao.deleteById(student1.getId());
    }
}


