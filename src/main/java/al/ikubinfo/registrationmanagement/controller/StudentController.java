package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.repository.criteria.StudentCriteria;
import al.ikubinfo.registrationmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {
    private static final String REDIRECT_TO_HOMEPAGE_URL = "redirect:/students";
    private static final String STUDENTS = "students";
    @Autowired
    private StudentService studentService;



    /**
     * Get all students. if criteria is applied, students are filter accordingly
     *
     * @param criteria filter object
     * @return ModelAndView -> students filtered list
     */
    @GetMapping("/students")
    public ModelAndView listStudents(StudentCriteria criteria) {
        Page<StudentDto> students = studentService.filterStudents(criteria);
        ModelAndView mv = new ModelAndView(STUDENTS);
        mv.addObject(STUDENTS, students);
        return mv;
    }

    /**
     * Retrieve student details
     *
     * @param id student id
     * @return ModelAndView with student details
     */
    @GetMapping("/students/{id}")
    public ModelAndView getStudentById(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("student_details");
        mv.addObject("student", studentService.getStudentById(id));
        return mv;
    }


    /**
     * Retrieve form of student creation
     *
     * @param student student dto
     * @return ModelAndView
     */
    @GetMapping("/students/new")
    public ModelAndView retrieveNewStudentView(StudentDto student) {
        ModelAndView mv = new ModelAndView("create_student");
        mv.addObject("student", student);
        return mv;
    }

    /**
     * Save student
     *
     * @param student student dto
     * @return ModelAndView
     */
    @PostMapping("/students")
    public ModelAndView saveStudent(@ModelAttribute("student") StudentDto student) {
        studentService.saveStudent(student);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }


    /**
     * Retrieve student edition view
     *
     * @param id student id
     * @return ModelAndView
     */
    @GetMapping("/students/edit/{id}")
    public ModelAndView updateStudentView(@PathVariable("id") Long id) {
        StudentDto studentDto = studentService.getStudentById(id);
        ModelAndView mv = new ModelAndView("edit_student.html");
        mv.addObject("student", studentDto);
        return mv;
    }


    /**
     * Update student
     *
     * @param id      student id
     * @param student Student updated dto
     * @return
     */
    @PutMapping("/students/{id}")
    public ModelAndView updateStudent(@PathVariable Long id, @ModelAttribute("student") UpdateStudentDto student) {
        StudentDto dto = studentService.updateStudent(student);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Delete student by id
     *
     * @param id student id
     * @return ModelAndView homepage
     */
    @GetMapping("/students/delete/{id}")
    public ModelAndView deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

}
