package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.dto.RoleDto;

public interface RoleService {

    RoleDto getRoleById(Long id);

    String getRoleByName(String roleName);
}
