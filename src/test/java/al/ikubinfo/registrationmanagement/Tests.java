package al.ikubinfo.registrationmanagement;

import al.ikubinfo.registrationmanagement.converter.RoleConverter;
import al.ikubinfo.registrationmanagement.dto.CourseStatus;
import al.ikubinfo.registrationmanagement.dto.ReachFormEnum;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.RoleEntity;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.repository.RoleRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import al.ikubinfo.registrationmanagement.service.impl.CourseServiceImpl;
import al.ikubinfo.registrationmanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@SpringBootTest(classes = RegistrationManagementApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class Tests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Test
    @DisplayName("Save role")
    void saveRole() {

        RoleEntity role = new RoleEntity();
        role.setName("CTO");
        role.setDescription("CTO");
        role.setModifiedDate(LocalDate.now());
        role.setCreatedDate(LocalDate.now());
        role.setDeleted(false);

        roleRepository.save(role);
        Assertions.assertEquals(3, roleRepository.findAll().size());// sepse 2 jane ne db
    }

    @Test
    @DisplayName("Save user")
    void saveUser() {

        UserEntity user = new UserEntity();
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setPhoneNumber("0699999999");
        user.setEmail("testEmail@gmail.com");
        user.setPassword("password");
        user.setReachForm(ReachFormEnum.EMPLOYEE);
        user.setBirthDate(LocalDate.now());
        user.setCreatedDate(LocalDate.now());
        user.setModifiedDate(LocalDate.now());
        user.setRole(roleRepository.findByName("CTO"));
        user.setDeleted(false);
        userRepository.save(user);
        Assertions.assertEquals(4, userRepository.findAll().size());// sepse 3 jane ne db
    }

    @Test
    @DisplayName("Save course")
    void saveCourse() {

        CourseEntity course = new CourseEntity();
        course.setCourseName("test");
        course.setCourseStartDate(LocalDate.of(2022, 1, 10));
        course.setCourseEndDate(LocalDate.of(2022, 2, 11));
        course.setRegistrationStartDate(LocalDate.of(2022, 1, 10));
        course.setRegistrationEndDate(LocalDate.of(2022, 2, 11));
        course.setPrice(123);
        course.setStatus(CourseStatus.READY_TO_START);
        course.setCreatedDate(LocalDate.now());
        course.setModifiedDate(LocalDate.now());
        course.setDeleted(false);
        courseRepository.save(course);
        Assertions.assertEquals(4, courseRepository.findAll().size());// sepse 3 jane ne db
    }
}