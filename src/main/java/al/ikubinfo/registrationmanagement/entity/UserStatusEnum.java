package al.ikubinfo.registrationmanagement.entity;


public enum UserStatusEnum {

    REGISTERED("Registered"),
    INTERESTED("Interested"),
    PAID("Paid");

    private final String displayValue;

    UserStatusEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
