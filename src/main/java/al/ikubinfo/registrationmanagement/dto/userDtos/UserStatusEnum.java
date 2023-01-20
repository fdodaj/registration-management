package al.ikubinfo.registrationmanagement.dto.userDtos;
public enum UserStatusEnum {
    REGISTERED("REGISTERED"),
    INTERESTED("INTERESTED"),

    PAID("PAID");
    private final String displayValue;
    UserStatusEnum(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue() {
        return displayValue;
    }
}
