package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.UserConverter;
import al.ikubinfo.registrationmanagement.dto.NewUserDto;
import al.ikubinfo.registrationmanagement.dto.UpdateStudentDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import al.ikubinfo.registrationmanagement.repository.RoleRepository;
import al.ikubinfo.registrationmanagement.repository.UserEntityManagerRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.UserSpecification;
import al.ikubinfo.registrationmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserSpecification specification;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEntityManagerRepository userEMRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<UserDto> filterUsers(@RequestBody UserCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        return userRepository
                .findAll(specification.filter(criteria), pageable)
                .map(userConverter::toDto);
    }

    @Override
    public UserDto getStudentById(Long id) {
        return userConverter.toDto(getStudentEntity(id));

    }

    @Override
    public void saveStudent(NewUserDto dto) {
        UserEntity userEntity = userConverter.toNewUserEntity(dto);
        userEntity.setRole(roleRepository.findByName("STUDENT"));
        userRepository.save(userEntity);
    }

    @Override
    public UserDto updateStudent(UpdateStudentDto student) {
        UserEntity currentEntity = getStudentEntity(student.getId());
        UserEntity userEntity = userConverter.toUpdateStudentEntity(student, currentEntity);
        return userConverter.toDto(userRepository.save(userEntity));
    }


    @Override
    public void deleteStudentById(Long id) {
        UserEntity student = getStudentEntity(id);
        student.setDeleted(true);
        userRepository.save(student);
    }


    public UserEntity getStudentEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<UserDto> getUserEM() {
        return userEMRepository.getAllStudentsWithPaidCurses()
        .stream()
        .map(userConverter::toDto)
        .collect(Collectors.toList());
    }

}
