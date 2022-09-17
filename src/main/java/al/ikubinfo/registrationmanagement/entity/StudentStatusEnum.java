package al.ikubinfo.registrationmanagement.entity;


public enum StudentStatusEnum {

    REGISTERED("Registered"),
    INTERESTED("Interested"),
    PAID("Paid");

    private final String displayValue;

    StudentStatusEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
