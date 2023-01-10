package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.PasswordDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.dto.ValidatedUserDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    /**
     * Get all users. if criteria is applied, users are filter accordingly
     *
     * @param criteria UserCriteria
     * @return Page<UserDto>
     */
    Page<UserDto> filterUsers(UserCriteria criteria);


    /**
     * Retrieve user details
     *
     * @param id user id
     * @return UserDto
     */
    UserDto getUserById(Long id);

    /**
     * Save user/student
     *
     * @param student ValidatedUserDto
     * @return UserDto
     */
    UserDto saveUser(ValidatedUserDto student);

    /**
     * Update user
     *
     * @param student ValidatedUserDto
     * @return UserDto
     */
    UserDto updateUser(ValidatedUserDto student);

    /**
     * Delete user
     *
     * @param id user id
     */
    void deleteUserById(Long id);

    /**
     * Get user using entity manager. Created for testing purposes
     *
     * @return List<UserDto>
     */
    List<UserDto> getUsersEM();

    List<UserDto> getUnassignedUsers();

     UserDto changePassword(PasswordDto passwordDto);


}
