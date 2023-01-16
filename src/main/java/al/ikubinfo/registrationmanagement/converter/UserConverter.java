package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.userDtos.UserDto;
import al.ikubinfo.registrationmanagement.dto.userDtos.ValidatedUserDto;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
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
        entity.setBirthDate(dto.getBirthDate());
        entity.setReachForm(dto.getReachForm());
        entity.set_assigned(dto.isAssigned());
        entity.setRole(roleConverter.toEntity(dto.getRole()));
        return entity;
    }

    public UserEntity toValidatedUserEntity(ValidatedUserDto dto) {
        UserEntity entity = new UserEntity();

        entity.setBirthDate(dto.getBirthDate());
        entity.setReachForm(dto.getReachForm());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.set_assigned(dto.isAssigned());
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
        dto.setReachForm(entity.getReachForm());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        entity.setBirthDate(dto.getBirthDate());
        entity.setReachForm(dto.getReachForm());
        entity.set_assigned(entity.is_assigned());
        return dto;
    }

    public UserEntity toUpdateStudentEntity(ValidatedUserDto dto, UserEntity entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setReachForm(dto.getReachForm());
        entity.set_assigned(dto.isAssigned());
        return entity;
    }

    public List<UserDto> toStudentDtoList(List<UserEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }


}
