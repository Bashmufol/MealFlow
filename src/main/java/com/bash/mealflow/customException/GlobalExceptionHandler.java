package com.bash.mealflow.customException;

import com.bash.mealflow.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @ExceptionHandler(value = IllegalArgumentException.class, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @ExceptionHandler(value = IllegalStateException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(value = Exception.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                "An unexpected error occurred. Please try again later." + ex.getMessage(),
                request.getRequestURI()

        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class, produces = MediaType.APPLICATION_JSON_VALUE)
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


    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundHtml(ResourceNotFoundException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/404"); // Path to your 404.html Thymeleaf template
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("status", HttpStatus.NOT_FOUND.value());
        mav.addObject("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        mav.addObject("path", request.getRequestURI());
        return mav;
    }

    // Handles IllegalArgumentException for browser requests
    @ExceptionHandler(value = IllegalArgumentException.class, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleIllegalArgumentHtml(IllegalArgumentException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/400"); // Path to 400.html Thymeleaf template
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        mav.addObject("path", request.getRequestURI());
        return mav;
    }

    // Handles IllegalStateException for browser requests (prefers text/html)
    @ExceptionHandler(value = IllegalStateException.class, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleIllegalStateHtml(IllegalStateException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/409"); // Reusing 400 for now, or create error/409.html
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        mav.addObject("path", request.getRequestURI());
        return mav;
    }

    // Handles UsernameNotFoundException for browser requests (prefers text/html)
    @ExceptionHandler(value = UsernameNotFoundException.class, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleUsernameNotFoundHtml(UsernameNotFoundException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/400");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        mav.addObject("path", request.getRequestURI());
        return mav;
    }


    // Handles Generic Exception
    @ExceptionHandler(value = Exception.class, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleGenericExceptionHtml(Exception ex, HttpServletRequest request) {
        System.err.println("An unexpected error occurred: " + ex.getMessage());
        ex.printStackTrace();

        ModelAndView mav = new ModelAndView("error/500"); // Path to 500 Thymeleaf template
        mav.addObject("errorMessage", "An unexpected error occurred. Please try again later.");
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        mav.addObject("path", request.getRequestURI());
        return mav;
    }

}
