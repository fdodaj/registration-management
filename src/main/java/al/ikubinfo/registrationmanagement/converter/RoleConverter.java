package al.ikubinfo.registrationmanagement.converter;

import al.ikubinfo.registrationmanagement.dto.roleDtos.RoleDto;
import al.ikubinfo.registrationmanagement.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements BidirectionalConverter<RoleDto, RoleEntity> {


    @Override
    public RoleDto toDto(RoleEntity entity) {
        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

    @Override
    public RoleEntity toEntity(RoleDto dto) {
        RoleEntity entity = new RoleEntity();
        entity.setId(entity.getId());
        entity.setName(entity.getName());
        entity.setDescription(entity.getDescription());
        entity.setCreatedDate(entity.getCreatedDate());
        entity.setModifiedDate(entity.getModifiedDate());
        return entity;
    }
}
