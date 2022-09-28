package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.RoleConverter;
import al.ikubinfo.registrationmanagement.dto.RoleDto;
import al.ikubinfo.registrationmanagement.repository.RoleRepository;
import al.ikubinfo.registrationmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter converter;

    @Override
    public RoleDto getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(converter::toDto)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public String getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).getName();
    }
}
