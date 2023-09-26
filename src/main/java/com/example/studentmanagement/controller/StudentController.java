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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
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
            @RequestParam("arrayIdSubject") List<Long> listIdSubject, // trả về 1 list id của các subject được đăng kí
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("id_status") Long id_status,
            @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        Student student = new Student();
        student.setName(name);
        student.setAddress(address);
        student.setStatus(statusService.findById(id_status).get());
        if (id == 0) {
            student.setId(null);
            return validStudent(student);
        }
        student.setMultipartFile(multipartFile);
        student.setId(id);
        if (multipartFile == null) { // nếu không chọn ảnh thì set lại url cũ
            student.setUrl_img(studentService.findById(id).get().getUrl_img());
        }
        student.setCount_subject(listIdSubject.size()); // set số các môn học đã đăng kí cho học sinh
        // set số các học sinh đã đăng kí cho môn học:
        // lấy ra tất cả các môn học:
        List<Subject> allSubject = subjectService.findAll();
        // nếu id môn học có trong cái list id được chọn thì thêm số lượng +1:
        for (Subject subject : allSubject) {
            for (Long idSub : listIdSubject) {
                int count = subject.getCount_student();
                if (idSub == subject.getId()) {
                    count++;
                    subject.setCount_student(count);
                    subjectService.save(subject);
                }
            }
        }
        // xem trong cái detail cũ cái subject nào không có thì trừ count_student đi:
        List<DetailStudentAndSubject> details = detailService.selectAllByStudent_Id(id);
        for (Subject subject: allSubject) {
            for (DetailStudentAndSubject detail: details) {
                if (subject.getId() == detail.getSubject().getId()) {// lấy ra được subject đăng kí trước đó
                    if (listIdSubject.size() == 0) {
                        int count = subject.getCount_student();
                        count --;
                        if (count < 0) {
                            count = 0;
                        }
                        subject.setCount_student(count);
                        subjectService.save(subject);
                    } else {
                        for (Long idSub: listIdSubject) { // duyện list đăng kí mới
                            int count = subject.getCount_student();
                            if (idSub!= subject.getId()) { // nếu subject cũ không có trong list
                                count--;
                                if (count < 0) {
                                    count = 0;
                                }
                                subject.setCount_student(count);
                                subjectService.save(subject);
                            }
                        }
                    }

                }
            }
        }
        // mặc định là xoá hết các detail cũ của student:
        detailService.deleteAllByStudent_Id(id);
        for (Long idSubject : listIdSubject) {
            // set lại cái detail với student này và subject mới
            detailService.save(new DetailStudentAndSubject(student, subjectService.findById(idSubject).get()));
        }
        // lấy ra list detail mới:
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
        if (student.getMultipartFile() == null) {
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
}
