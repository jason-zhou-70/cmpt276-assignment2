package com.cmpt276.assignment2.controllers;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.cmpt276.assignment2.models.Student;
import com.cmpt276.assignment2.models.StudentRepository;


@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/")
    public String showHome(){
        return "users/home";
    }

    @GetMapping("/users/view")
    public String getAllUsers(Model model){

        List<Student> students = studentRepo.findAll();

        model.addAttribute("us", students);
        return "users/showAll";
    }

    @GetMapping("/users/add")
    public String showAddForm(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "users/add";
    }

    @PostMapping("/users/add")
    public String addStudent(@ModelAttribute("student") Student newStudent){
        studentRepo.save(newStudent);
        return "users/added";
    }

    @GetMapping("/users/edit")
    public String editForm(Model model){
        if (studentRepo.count() > 0){
            List<Student> students = studentRepo.findAll();
            model.addAttribute("studentList", students);

            Student targetStudent = new Student();
            model.addAttribute("targetStudent", targetStudent);

            return "users/edit";
        }
        else
            return "users/empty";
    }

    @PostMapping("/users/edit")
    public String editStudent(@ModelAttribute("targetStudent") Student student){
        Student editStudent = studentRepo.getById(student.getUid());
        editStudent.setName(student.getName());
        editStudent.setWeight(student.getWeight());
        editStudent.setHeight(student.getHeight());
        editStudent.setHairColour(student.getHairColour());
        editStudent.setGpa(student.getGpa());
        studentRepo.save(editStudent);
        return "users/added";
    }

    @GetMapping("/users/delete")
    public String deleteForm(Model model){
        if (studentRepo.count() > 0){
            List<Student> students = studentRepo.findAll();
            model.addAttribute("studentList", students);

            Student targetStudent = new Student();
            model.addAttribute("targetStudent", targetStudent);

            return "users/delete";
        }
        else
            return "users/empty";
    }

    @PostMapping("/users/delete")
    public String deleteStudent(@ModelAttribute("targetStudent") Student student){
        studentRepo.delete(studentRepo.getById(student.getUid()));
        return "users/deleted";
    }
}
