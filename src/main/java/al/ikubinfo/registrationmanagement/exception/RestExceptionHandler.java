package al.ikubinfo.registrationmanagement.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String COURSE_DELETED = "COURSE_DELETED";
    private static final String STUDENT_DELETED = "STUDENT_DELETED";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourseDeletedException.class)
    @ResponseBody
    public ErrorResponse handleCourseDeleted(final Throwable ex) {
        log.error(COURSE_DELETED, ex);
        return new ErrorResponse(COURSE_DELETED, "This course has been deleted");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentDeletedException.class)
    @ResponseBody
    public ErrorResponse handleStudentDeleted(final Throwable ex) {
        log.error(STUDENT_DELETED, ex);
        return new ErrorResponse(STUDENT_DELETED, "This student has been deleted");
    }


    @ExceptionHandler(AccessDeniedException.class)  //handle this exception
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String accessDenied(final AccessDeniedException ex) {
//        model.addAttribute("errorMessage", throwable.getMessage()); //custom message to render in HTML
        return "redirect:/login";  //the html page in resources/templates folder
    }


    @Data
    public static class ErrorResponse {
        private final String code;
        private final String message;
    }
}
