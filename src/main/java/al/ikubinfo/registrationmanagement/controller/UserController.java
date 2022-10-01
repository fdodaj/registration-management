package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import al.ikubinfo.registrationmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Validated
@Controller
public class UserController {
    private static final String REDIRECT_TO_HOMEPAGE_URL = "redirect:/students";
    private static final String USERS = "users";
    @Autowired
    private UserService userService;


    /**
     * Get all students. if criteria is applied, students are filter accordingly
     *
     * @param criteria filter object
     * @return ModelAndView -> students filtered list
     */
    @GetMapping("/students")
    public ModelAndView listStudents(@Valid UserCriteria criteria) {
        Page<UserDto> users = userService.filterUsers(criteria);
        ModelAndView mv = new ModelAndView(USERS);
        mv.addObject(USERS, users);
        return mv;
    }


    /**
     * Retrieve student details
     *
     * @param id student id
     * @return ModelAndView with student details
     */
    @GetMapping("/students/{id}")
    public ModelAndView getStudentById(@Valid @PathVariable Long id) {
        ModelAndView mv = new ModelAndView("student_details");
        mv.addObject("student", userService.getStudentById(id));
        return mv;
    }


    /**
     * Retrieve form of student creation
     *
     * @param student student dto
     * @return ModelAndView
     */
    @GetMapping("/students/new")
    public ModelAndView retrieveNewStudentView(@Valid UserDto student) {
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
    public ModelAndView saveStudent(@ModelAttribute("student") @Valid UserDto student, BindingResult result) {
        userService.saveStudent(student);
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
        UserDto userDto = userService.getStudentById(id);
        ModelAndView mv = new ModelAndView("edit_student.html");
        mv.addObject("student", userDto);
        return mv;
    }


    /**
     * Update student
     *
     * @param id      student id
     * @param student Student updated dto
     * @return
     */
    @PostMapping("/students/{id}")
    public ModelAndView updateStudent(@PathVariable Long id, @ModelAttribute("student") UpdateStudentDto student) {
        UserDto dto = userService.updateStudent(student);
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
        userService.deleteStudentById(id);
        return new ModelAndView(REDIRECT_TO_HOMEPAGE_URL);
    }

    /**
     * Get student with PAID courses using EntityManager
     * For testing purposes
     *
     * @return ResponseEntity<List < UserDto>>
     */
    @GetMapping("/students/em")
    public ResponseEntity<List<UserDto>> listStudentsUsingEntityManager() {
        List<UserDto> list = userService.getUserEM();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

}
