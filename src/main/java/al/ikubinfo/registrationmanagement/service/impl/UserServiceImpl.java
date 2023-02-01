package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.UserConverter;
import al.ikubinfo.registrationmanagement.dto.authDtos.PasswordDto;
import al.ikubinfo.registrationmanagement.dto.roleDtos.RoleEnum;
import al.ikubinfo.registrationmanagement.dto.userDtos.NewUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UpdateUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import al.ikubinfo.registrationmanagement.repository.CourseUserRepository;
import al.ikubinfo.registrationmanagement.repository.RoleRepository;
import al.ikubinfo.registrationmanagement.repository.UserEntityManagerRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.UserSpecification;
import al.ikubinfo.registrationmanagement.security.Utils;
import al.ikubinfo.registrationmanagement.service.ServiceTemplate;
import al.ikubinfo.registrationmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceTemplate<UserCriteria, UserEntity, UserRepository, UserSpecification> implements UserService {
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserSpecification specification;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserEntityManagerRepository userEMRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    protected UserServiceImpl(UserRepository repository, UserSpecification specificationBuilder) {
        super(repository, specificationBuilder);
    }

    @Override
    public Page<UserDto> filterUsers(UserCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        return userRepository
                .findAll(specification.filter(criteria), pageable)
                .map(userConverter::toDto);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userConverter.toDto(getStudentEntity(id));
    }


    @Override
    public UserDto saveUser(NewUserDto dto) {
        UserEntity userEntity = userConverter.toNewUserEntity(dto);
        userEntity.set_assigned(Boolean.FALSE);
        userEntity.setModifiedDate(LocalDate.now());
        userEntity.setCreatedDate(LocalDate.now());
        userEntity.setDeleted(Boolean.FALSE);
        userEntity.setRole(roleRepository.findByName(RoleEnum.STUDENT.toString()));
        return userConverter.toDto(userRepository.save(userEntity));
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    public UserDto updateUser(UpdateUserDto user) {
        UserEntity currentEntity = getStudentEntity(user.getId());
        UserEntity userEntity = userConverter.toUpdateStudentEntity(user, currentEntity);
        return userConverter.toDto(userRepository.save(userEntity));
    }

    @Override
    public void deleteUserById(Long id) {
        UserEntity student = getStudentEntity(id);
        courseUserRepository.getCourseUserEntitiesByUserId(id).forEach(e -> e.setDeleted(true));
        student.setDeleted(true);
        userRepository.save(student);
    }

    public UserEntity getStudentEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<UserDto> getUsersEM() {
        return userEMRepository.getAllRegisteredUsers()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUnassignedUsers() {
        return userEMRepository.getAllUnassignedUsers()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    public UserDto changePassword(PasswordDto passwordDto) {
        UserEntity user = userRepository
                .findByEmail(Utils.getCurrentEmail().orElseThrow(null))
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!authenticate(user, passwordDto.getCurrentPassword())) {
            throw new AccessDeniedException("Access denied. Cannot change password");
        }
        user.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
        return userConverter.toDto(userRepository.save(user));
    }

    private boolean authenticate(UserEntity user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }


    @Override
    public String[] getHeaders() {
        return new String[]{
                "Emer", "Mbiemer", "Email", "Numer telefoni", "Referimi"
        };
    }

    @Override
    public String[] populate(UserEntity entity) {
        return new String[]{
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getReachForm().toString(),
        };
    }
}
