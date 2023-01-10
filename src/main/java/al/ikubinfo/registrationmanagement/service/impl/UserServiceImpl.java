package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.UserConverter;
import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.PasswordDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.dto.ValidatedUserDto;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import al.ikubinfo.registrationmanagement.repository.RoleRepository;
import al.ikubinfo.registrationmanagement.repository.UserEntityManagerRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.UserSpecification;
import al.ikubinfo.registrationmanagement.security.Utils;
import al.ikubinfo.registrationmanagement.service.CustomDataTable;
import al.ikubinfo.registrationmanagement.service.UserService;
import be.quodlibet.boxable.BaseTable;
import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.opencsv.ICSVParser.DEFAULT_ESCAPE_CHARACTER;
import static com.opencsv.ICSVParser.DEFAULT_SEPARATOR;
import static com.opencsv.ICSVWriter.DEFAULT_LINE_END;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserSpecification specification;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserEntityManagerRepository userEMRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public UserDto saveUser(ValidatedUserDto dto) {
        UserEntity userEntity = userConverter.toValidatedUserEntity(dto);
        userEntity.setRole(roleRepository.findByName("STUDENT"));
        return userConverter.toDto(userRepository.save(userEntity));
    }

    @Override
    public UserDto updateUser(ValidatedUserDto student) {
        UserEntity currentEntity = getStudentEntity(student.getId());
        UserEntity userEntity = userConverter.toUpdateStudentEntity(student, currentEntity);
        return userConverter.toDto(userRepository.save(userEntity));
    }


    @Override
    public void deleteUserById(Long id) {
        UserEntity student = getStudentEntity(id);
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


}
