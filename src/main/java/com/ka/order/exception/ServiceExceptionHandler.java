package com.ka.order.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatus status, @NonNull WebRequest request) {

        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                errors);
        return handleExceptionInternal(ex, response, headers, response.getStatus(), request);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatus status, @NonNull WebRequest request) {

        String error = ex.getParameterName() + " parameter is missing";
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                error);

        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Illegal argument", new HttpHeaders(), HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        final String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();
        final ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(
                response, new HttpHeaders(), response.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }

        ErrorResponse response =
                new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<>(
                response, new HttpHeaders(), response.getStatus());
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods())
                .forEach(t -> builder.append(t).append(" "));

        ErrorResponse response = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), builder.toString());

        return new ResponseEntity<>(
                response, new HttpHeaders(), response.getStatus());
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        ErrorResponse response = new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));

        return new ResponseEntity<>(
                response, new HttpHeaders(), response.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "Unknown error occurred");

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(
                response, new HttpHeaders(), response.getStatus());
    }

}
