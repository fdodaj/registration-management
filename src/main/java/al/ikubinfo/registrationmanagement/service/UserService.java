package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    Page<UserDto> filterUsers(UserCriteria criteria);

    UserDto getStudentById(Long id);

    void saveStudent(UserDto student);

    UserDto updateStudent(UpdateStudentDto student);

    void deleteStudentById(Long id);

    public List<UserDto> getUserEM();
}