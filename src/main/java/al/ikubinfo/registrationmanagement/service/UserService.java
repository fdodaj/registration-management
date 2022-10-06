package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.UpdateUserDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.dto.ValidatedUserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    Page<UserDto> filterUsers(UserCriteria criteria);

    UserDto getUserById(Long id);

    void saveUser(ValidatedUserDto student);

    UserDto updateUser(UpdateUserDto student);

    void deleteUserById(Long id);

    List<UserDto> getUserEM();
}
