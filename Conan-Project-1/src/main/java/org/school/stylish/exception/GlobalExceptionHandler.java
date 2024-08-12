package org.school.stylish.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.school.stylish.dto.other.ErrorResponse;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    /**
     * Handles validation errors for method arguments.
     *
     * @param ex The MethodArgumentNotValidException thrown when validation fails.
     * @return A ResponseEntity containing an ErrorResponse with validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        log.error("Validation error: {}", bindingResult);
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
    }

    /**
     * Handles exceptions for unsupported media types in HTTP requests.
     *
     * @param e The HttpMediaTypeNotSupportedException thrown when an unsupported media type is used.
     * @return A ResponseEntity containing an ErrorResponse with details about supported media types.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String message = "Unsupported media type. Supported media types are " +
                e.getSupportedMediaTypes().stream()
                        .map(MediaType::toString)
                        .collect(Collectors.joining(", "));

        log.error("Media type not supported: {}", message);

        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * Handles SQLIntegrityConstraintViolationException.
     *
     * @param e The SQLIntegrityConstraintViolationException thrown when an integrity constraint violation occurs.
     * @return A ResponseEntity containing an ErrorResponse with details about the SQLIntegrityConstraintViolationException.
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        log.error("SQLIntegrityConstraintViolationException: {}", message);
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ErrorResponse> handleCannotCreateTransactionException(CannotCreateTransactionException ex) {
        log.error("Database connection error", ex);
        ErrorResponse errorResponse = new ErrorResponse("Unable to connect to the database. Please try again later.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
