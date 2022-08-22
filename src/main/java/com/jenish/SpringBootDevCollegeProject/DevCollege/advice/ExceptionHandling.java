package com.jenish.SpringBootDevCollegeProject.DevCollege.advice;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.EnrolmentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> HandlingInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> HandlingInvalidInput(HttpMessageNotReadableException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Input Error","Please enter valid input");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CourseNotFoundException.class)
    public Map<String, String> handleBusinessException(CourseNotFoundException exception) {
        Map<String, String> courseException = new HashMap<>();
        courseException.put("errorMessage", exception.getMessage());
        return courseException;
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public Map<String, String> handleBusinessException(StudentNotFoundException exception) {
        Map<String, String> studentException = new HashMap<>();
        studentException.put("errorMessage", exception.getMessage());
        return studentException;
    }

    @ExceptionHandler(EnrolmentNotFoundException.class)
    public Map<String, String> handleBusinessException(EnrolmentNotFoundException exception) {
        Map<String, String> enrolmentException = new HashMap<>();
        enrolmentException.put("errorMessage", exception.getMessage());
        return enrolmentException;
    }
}
