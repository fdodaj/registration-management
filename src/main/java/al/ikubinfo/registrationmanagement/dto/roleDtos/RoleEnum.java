package al.ikubinfo.registrationmanagement.dto.roleDtos;
public enum RoleEnum {
    ADMIN("ADMIN"),
    STUDENT("SDTUDENT");
    final String roleName;

    RoleEnum(String role) {
        this.roleName = role;
    }
}
