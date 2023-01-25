package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.courseDtos.CourseDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.CourseUserListDto;
import al.ikubinfo.registrationmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.NewUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.SimplifiedUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UpdateUserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter implements BidirectionalConverter<UserDto, UserEntity> {
    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private CourseConverter courseConverter;

    @Override
    public UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setReachForm(dto.getReachForm());
        entity.set_assigned(dto.isAssigned());
        entity.setRole(roleConverter.toEntity(dto.getRole()));
        return entity;
    }

    public NewUserDto toNewUserDto(UserEntity entity) {
        NewUserDto dto = new NewUserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setReachForm(entity.getReachForm());
        dto.setBirthDate(entity.getBirthDate());
        entity.setBirthDate(dto.getBirthDate());
        entity.setReachForm(dto.getReachForm());
        entity.set_assigned(entity.is_assigned());
        return dto;
    }


    public UserEntity toNewUserEntity(NewUserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setReachForm(dto.getReachForm());
        return entity;
    }



    @Override
    public UserDto toDto(UserEntity entity) {
        CourseUserConverter courseUserConverter = new CourseUserConverter();
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setReachForm(entity.getReachForm());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setAssigned(entity.is_assigned());
        dto.setReachForm(entity.getReachForm());
        dto.setRole(roleConverter.toDto(entity.getRole()));
        dto.setUserCourses(courseUserConverter.toCourseUserDtoList(entity.getUserCourses()));
        return dto;
    }
    public SimplifiedUserDto toSimplifiedUserDto(UserEntity entity) {
        SimplifiedUserDto dto = new SimplifiedUserDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setReachForm(entity.getReachForm());
        dto.setBirthDate(entity.getBirthDate());
        dto.setReachForm(entity.getReachForm());
        return dto;
    }

    public UserEntity toUpdateStudentEntity(UpdateUserDto dto, UserEntity entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public List<UserDto> toUserDtoList(List<UserEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
