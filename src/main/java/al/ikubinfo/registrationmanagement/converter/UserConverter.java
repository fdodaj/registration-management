package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.userDtos.*;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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



    public SimplifiedUserDto toSimplifiedUserDto(UserEntity entity) {
        SimplifiedUserDto dto = new SimplifiedUserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setReachForm(entity.getReachForm());
        dto.setBirthDate(entity.getBirthDate());
        dto.setReachForm(entity.getReachForm());
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

    public UserEntity toUpdateStudentEntity(UpdateUserDto dto, UserEntity entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setReachForm(dto.getReachForm());
        return entity;
    }
}
