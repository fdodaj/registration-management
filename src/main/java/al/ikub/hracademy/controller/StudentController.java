package al.ikub.hracademy.controller;

import al.ikub.hracademy.converter.StudentConverter;
import al.ikub.hracademy.dto.StudentDto;
import al.ikub.hracademy.dto.UpdateStudentDto;
import al.ikub.hracademy.entity.StudentEntity;
import al.ikub.hracademy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentController {

	@Autowired
	private StudentConverter converter;

	private static final String ADD_NEW_STUDENT_URL = "create_student.html";
	private static final  String REDIRECT_TO_HOMEPAGE_URL = "redirect:/students";
	private static final  String EDIT_STUDENT_URL = "edit_student.html";
	private static final String STUDENT_DETAILS_URL = "student_details";

	private static final String STUDENT_LIST_URL = "students";


	@Autowired
	private  StudentService studentService;


	@GetMapping("/students")
	public ModelAndView listStudents() {
		ModelAndView mv = new ModelAndView(STUDENT_LIST_URL);
		mv.addObject("students", studentService.getAllStudents());
		return mv;
	}


	@GetMapping("/students/{id}")
	public ModelAndView getStudentById(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView(STUDENT_DETAILS_URL);
		mv.addObject("student", studentService.getStudentById(id));
		return mv;
	}


	@GetMapping("/students/new")
	public ModelAndView goToAddStudentPage(StudentEntity student) {
		ModelAndView mv = new ModelAndView(ADD_NEW_STUDENT_URL);
		mv.addObject("student", student);
		return mv;
	}

	@PostMapping("/students")
	public ModelAndView saveStudent(@ModelAttribute("student") StudentDto student) {
		studentService.saveStudent(student);
		return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
	}


	@GetMapping("/students/edit/{id}")
	public ModelAndView goToEditStudentPage(@PathVariable("id") Long id) {
	UpdateStudentDto studentDto = converter.toUpdateStudentDto(studentService.getStudentById(id));
	ModelAndView mv = new ModelAndView(EDIT_STUDENT_URL);
	mv.addObject("student", studentDto);
	return mv;
	}



	@PostMapping("/students/{id}")
	public ModelAndView updateStudent(@PathVariable Long id, @ModelAttribute("student") StudentEntity student) {
		studentService.updateStudent(converter.toUpdateStudentDto(student));
		return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
	}

	
	@GetMapping("/students/delete/{id}")
	public ModelAndView deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
	}
	
}
