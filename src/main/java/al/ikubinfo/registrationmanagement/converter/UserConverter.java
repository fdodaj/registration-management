package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.*;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import al.ikubinfo.registrationmanagement.entity.UserStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter implements BidirectionalConverter<UserDto, UserEntity> {

    @Autowired
    private RoleConverter roleConverter;

    @Override
    public UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setRole(roleConverter.toEntity(dto.getRole()));
        return entity;
    }


    public UserEntity toNewUserEntity(NewUserDto dto) {
        UserEntity entity = new UserEntity();

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());;
        entity.setRole(roleConverter.toEntity(dto.getRole()));
        return entity;
    }


    public UserEntity toValidatedUserEntity(ValidatedUserDto dto) {
        UserEntity entity = new UserEntity();

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setRole(roleConverter.toEntity(dto.getRole()));
        return entity;
    }


    @Override
    public UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

    public UserEntity toUpdateStudentEntity(UpdateUserDto dto, UserEntity entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public List<UserDto> toStudentDtoList(List<UserEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }



}
