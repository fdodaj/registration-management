package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.converter.StudentConverter;
import al.ikubinfo.registrationmanagement.converter.CourseConverter;
import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.StudentDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.entity.StudentEntity;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentController {

	@Autowired
	private StudentConverter converter;

	@Autowired
	private CourseConverter courseConverter;

	private static final String ADD_NEW_STUDENT_URL = "create_student.html";
	private static final  String REDIRECT_TO_HOMEPAGE_URL = "redirect:/students";
	private static final  String EDIT_STUDENT_URL = "edit_student.html";
	private static final String STUDENT_DETAILS_URL = "student_details";

	private static final String STUDENT_LIST_URL = "students";


	@Autowired
	private  StudentService studentService;


	@Autowired
	private CourseService courseService;

	@GetMapping("/students")
	public ModelAndView listStudents() {
		ModelAndView mv = new ModelAndView("students");
		mv.addObject("students", studentService.getAllStudents());
		return mv;
	}


	@GetMapping("/students/{id}")
	public ModelAndView getStudentById(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("student_details");
		mv.addObject("student", studentService.getStudentById(id));
		return mv;
	}


	@GetMapping("/students/new")
	public ModelAndView goToAddStudentPage(StudentDto student) {
		ModelAndView mv = new ModelAndView("create_student");
		List<CourseDto> courseDto = courseService.getAllCourses();
		mv.addObject("courses" ,courseDto );
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
	ModelAndView mv = new ModelAndView("edit_student.html");
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
