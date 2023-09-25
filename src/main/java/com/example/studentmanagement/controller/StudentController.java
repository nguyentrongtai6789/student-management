package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.DetailStudentAndSubject;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.service.IDetailService;
import com.example.studentmanagement.service.IStatusService;
import com.example.studentmanagement.service.IStudentService;
import com.example.studentmanagement.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IStatusService statusService;
    @Autowired
    private IDetailService detailService;
    @Autowired
    private ISubjectService subjectService;
    @Value("${upload}")
    private String fileUpload;


    @GetMapping("/getListSubject")
    public ResponseEntity<List<Subject>> getListSubject() {
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Student>> pageStudent(Pageable pageable) {
        return new ResponseEntity<>(studentService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/getAllSubject")
    public ResponseEntity<List<Subject>> getAllSubject() {
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getSubjectCheckedByStudent/{id}")
    public ResponseEntity<List<Subject>> getSubjectCheckedByStudent(@PathVariable Long id) {
        Student student = studentService.findById(id).get();
        List<DetailStudentAndSubject> detailStudentAndSubjectList
                = detailService.findAllByStudent(student);
        List<Subject> subjects = subjectService.findAll();
        List<Subject> subjects1 = new ArrayList<>();
        for (Subject subject : subjects) {
            for (DetailStudentAndSubject detailStudentAndSubject : detailStudentAndSubjectList) {
                if (subject.getId() == detailStudentAndSubject.getSubject().getId()) {
                    subjects1.add(subject); // tạo ra list subject mà student đã đăng kí
                }
            }
        }
        return new ResponseEntity<>(subjects1, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> saveEditStudent(
            @RequestParam("arrayIdSubject") List<Long> listIdSubject,
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("id_status") Long id_status,
            @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        Student student = new Student();
        student.setName(name);
        student.setAddress(address);
        student.setStatus(statusService.findById(id_status).get());
        student.setMultipartFile(multipartFile);
        student.setCount_subject(listIdSubject.size());

        // nếu id = 0 tức là tạo mới thì cho id = null;
        if (id == 0) {
            student.setId(null);
            return validStudent(student);
        }
        student.setId(id);
        // xoá hết các detail cũ của student:
        detailService.deleteAllByStudent_Id(id);
        for (Long idSubject: listIdSubject) {
            detailService.save(new DetailStudentAndSubject(student, subjectService.findById(idSubject).get()));
        }
        return validStudent(student);
    }
    @PostMapping("/addNewStudent")
    public ResponseEntity<Map<String, String>> saveCreateStudent(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("id_status") Long id_status,
            @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        Student student = new Student();
        student.setName(name);
        student.setAddress(address);
        student.setStatus(statusService.findById(id_status).get());
        student.setMultipartFile(multipartFile);
        student.setCount_subject(0);
        student.setId(null);
        return validStudent(student);
    }
    private ResponseEntity<Map<String, String>> validStudent(Student student) throws IOException {
        Map<String, String> errors = new HashMap<>();
        if (student.getName().isEmpty() || student.getName().equals("")) {
            errors.put("name", "Ten khong duoc trong");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }
        if (student.getAddress().isEmpty() || student.getAddress().equals("")) {
            errors.put("address", "Dia chi khong duoc trong");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }
        if (student.getMultipartFile().isEmpty() || student.getMultipartFile().getSize() == 0) {
            student.setUrl_img("default.jpg");
            studentService.save(student);
            return new ResponseEntity<>(Collections.singletonMap("message", "Product created successfully"), HttpStatus.CREATED);
        }
        saveImg(student);
        studentService.save(student);
        return new ResponseEntity<>(Collections.singletonMap("message", "Product created successfully"), HttpStatus.CREATED);
    }

    private void saveImg(Student student) throws IOException {
        MultipartFile multipartFile = student.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        //
        FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
        //
        student.setUrl_img(fileName);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
        detailService.deleteAllByStudent_Id(id);
        studentService.deleteById(id);
        return new ResponseEntity<>("Da xoa thanh cong", HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView show2() {
        return new ModelAndView("error");
    }
    @PostMapping("/searchByname{name}")
    public ResponseEntity<Page> searchByName(@PathVariable("name") String name, Pageable pageable){
        Page<List<Student>>  listPage = studentService.searchByName(name, pageable);
        ResponseEntity response = new ResponseEntity(listPage, HttpStatus.OK);
        return response;
    }
}
