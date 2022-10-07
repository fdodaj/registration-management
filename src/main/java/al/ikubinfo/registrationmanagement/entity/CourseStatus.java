package al.ikubinfo.registrationmanagement.entity;

public enum CourseStatus {

    READY_TO_START("ready to start"),
    IN_PROGRESS("in progress"),
    FINISHED("finished");

    private final String displayValue;

    CourseStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

