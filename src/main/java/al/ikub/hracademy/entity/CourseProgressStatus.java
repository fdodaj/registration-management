package al.ikub.hracademy.entity;

public enum CourseProgressStatus {

    READY_TO_START("ready to start"),
    IN_PROGRESS("in progress"),
    FINISHED("finished");

    private final String displayValue;

    private CourseProgressStatus(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
    }

