package al.ikubinfo.registrationmanagement.exception;
public class StudentDeletedException extends RuntimeException {
    public StudentDeletedException(String errorMessage) {
        super(errorMessage);
    }
}
