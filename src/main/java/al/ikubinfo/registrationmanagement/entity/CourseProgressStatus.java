package al.ikubinfo.registrationmanagement.entity;

public enum CourseProgressStatus {

    READY_TO_START("ready to start"),
    IN_PROGRESS("in progress"),
    FINISHED("finished");

    private final String displayValue;

    CourseProgressStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

