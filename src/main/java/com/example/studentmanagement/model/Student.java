package com.example.studentmanagement.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Ten khong duoc trong!")

    private String name;

    @NotEmpty(message = "Dia chi khong duoc trong!")
    private String address;

    private String url_img;
    @Transient
    private MultipartFile multipartFile;
    @Min(value = 0)
    @Max(value = 3, message = "Dang ki toi da 3 phan mon!")
    private int count_subject = 0;
    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    public Student(Long id, String name, String address, String url_img, MultipartFile multipartFile, int count_subject, Status status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.url_img = url_img;
        this.multipartFile = multipartFile;
        this.count_subject = count_subject;
        this.status = status;
    }

    public Student() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public int getCount_subject() {
        return count_subject;
    }

    public void setCount_subject(int count_subject) {
        this.count_subject = count_subject;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
