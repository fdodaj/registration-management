package al.ikub.hracademy.controller;

import al.ikub.hracademy.entity.StudentEntity;
import al.ikub.hracademy.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class StudentController {
	
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
	}

	@GetMapping("/students/{id}")
	public String getStudentById(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "student_details";
	}


	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		StudentEntity student = new StudentEntity();
		model.addAttribute("student", student);
		return "create_student";
		
	}

	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") StudentEntity student) {
		studentService.saveStudent(student);
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}



	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,
								@ModelAttribute("student") StudentEntity student) {

		StudentEntity existingStudent = studentService.getStudentById(id);
		existingStudent.setName(student.getName());
		existingStudent.setPhoneNumber(student.getPhoneNumber());
		existingStudent.setEmail(student.getEmail());
		existingStudent.setStatus(student.getStatus());
		existingStudent.setReference(student.getReference());
		existingStudent.setPriceReduction(student.getPriceReduction());
		existingStudent.setPricePaid(student.getPricePaid());
		existingStudent.setComment(student.getComment());
		existingStudent.setLast_modified(LocalDate.now());

		studentService.updateStudent(existingStudent);
		return "redirect:/students";
	}

	
	@GetMapping("/students/delete/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}
	
}
