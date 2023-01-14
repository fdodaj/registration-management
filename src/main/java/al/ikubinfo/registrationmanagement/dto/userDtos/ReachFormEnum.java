package al.ikubinfo.registrationmanagement.dto.userDtos;

public enum ReachFormEnum {

    EMPLOYEE("EMPLOYEE"),
    FRIEND("FRIEND"),
    INSTAGRAM("INSTAGRAM"),
    ONLINE("ONLINE"),
    LINKEDIN("LINKEDIN");

    private final String displayValue;

    ReachFormEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    UserStatusEnum getValueByCode(String code) {
        return UserStatusEnum.valueOf(code);
    }

}
