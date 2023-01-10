package al.ikubinfo.registrationmanagement.dto;

public enum CourseStatus {

    READY_TO_START("READY_TO_START"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");

    private final String displayValue;

    CourseStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

