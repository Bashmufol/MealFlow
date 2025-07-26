package com.bash.mealflow.customException;

import com.bash.mealflow.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()

        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()

        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()

        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, status);
    }


//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ModelAndView handleResourceNotFoundHtml(ResourceNotFoundException ex, HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView("error/404"); // Name of your 404 error Thymeleaf template
//        mav.addObject("errorMessage", ex.getMessage());
//        mav.addObject("status", HttpStatus.NOT_FOUND.value());
//        mav.addObject("error", HttpStatus.NOT_FOUND.getReasonPhrase());
//        mav.addObject("path", request.getRequestURI());
//        return mav;
//    }
//
//
//    // Generic Exception Handler for HTML (500 Internal Server Error)
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleGenericExceptionHtml(Exception ex, HttpServletRequest request) {
//        System.err.println("An unexpected error occurred: " + ex.getMessage());
//        ex.printStackTrace();
//
//        ModelAndView mav = new ModelAndView("error/500"); // Name of your 500 error Thymeleaf template
//        mav.addObject("errorMessage", "An unexpected error occurred. Please try again later.");
//        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        mav.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        mav.addObject("path", request.getRequestURI());
//        // In development, you might add: mav.addObject("stackTrace", ex.getStackTrace());
//        return mav;
//    }
//
//    // You might also want a specific handler for general bad requests for HTML forms
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ModelAndView handleIllegalArgumentHtml(IllegalArgumentException ex, HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView("error/400"); // A generic bad request page
//        mav.addObject("errorMessage", ex.getMessage());
//        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
//        mav.addObject("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
//        mav.addObject("path", request.getRequestURI());
//        return mav;
//    }

}
