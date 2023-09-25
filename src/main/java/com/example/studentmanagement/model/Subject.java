package com.example.studentmanagement.model;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Ten khong duoc trong")
    @Column(unique = true)
    private String name;
    @Min(0)
    @Max(value = 10, message = "Mot phan mon chi toi da la 10 sinh vien dang ky!")
    private int count_student = 0;

    public Subject() {
    }

    public Subject(Long id, String name, int count_student) {
        this.id = id;
        this.name = name;
        this.count_student = count_student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount_student() {
        return count_student;
    }

    public void setCount_student(int count_student) {
        this.count_student = count_student;
    }
}
