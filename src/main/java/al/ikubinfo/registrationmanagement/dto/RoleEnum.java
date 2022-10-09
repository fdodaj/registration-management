package al.ikubinfo.registrationmanagement.dto;

public enum RoleEnum {
    ADMIN("ADMIN"),
    STUDENT("DTUDENT");

    final String roleName;

    RoleEnum(String role) {
        this.roleName = role;
    }
}
