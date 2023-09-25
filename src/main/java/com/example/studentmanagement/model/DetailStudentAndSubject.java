package com.example.studentmanagement.model;

import javax.persistence.*;

@Entity
@Table(name = "detail")
public class DetailStudentAndSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "id_subject")
    private Subject subject;

    public DetailStudentAndSubject(Long id, Student student, Subject subject) {
        this.id = id;
        this.student = student;
        this.subject = subject;
    }

    public DetailStudentAndSubject(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }

    public DetailStudentAndSubject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
