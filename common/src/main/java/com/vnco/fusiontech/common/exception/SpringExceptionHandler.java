package com.vnco.fusiontech.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class SpringExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleNotFound(EntityNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    ProblemDetail handleInvalid(InvalidRequestException ex) {
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {
        var errors = ex
                .getConstraintViolations()
                .stream()
                .map(v -> String.format("%s: %s, received: %s",
                        Optional
                                .ofNullable(v.getPropertyPath())
                                .map(String::valueOf)
                                .filter(prop -> prop.contains("."))
                                .map(prop -> prop.substring(prop.lastIndexOf('.') + 1))
                                .orElse(convertString(v.getPropertyPath())),
                        v.getMessage(),
                        convertString(v.getInvalidValue())))
                .toList();
        var problem = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Violated constraints request.");
        problem.setProperty("errors", errors);
        return problem;
    }

    private String convertString(@Nullable Object invalid) {
        if (ObjectUtils.isArray(invalid)) {
            return Arrays.toString((ObjectUtils.toObjectArray(invalid)));
        }
        return String.valueOf(invalid);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        var errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> String.format("Field: %s -> %s, received: '%s'",
                        err.getField(),
                        convertString(err.getRejectedValue())))
                .toList();
        var problem = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Invalid request content.");
        problem.setProperty("errors", errors);
        return handleExceptionInternal(ex, problem, headers, BAD_REQUEST, request);
    }

    @Nullable
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

//    @ExceptionHandler(RuntimeException.class)
//    ProblemDetail handleRuntimeException(RuntimeException ex) {
//        return ProblemDetail.forStatusAndDetail(I_AM_A_TEAPOT, ex.getMessage());
//    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ProblemDetail handleDataIntegration(DataIntegrityViolationException ex) {
        var cause = ex.getCause();
        var root = ex.getRootCause();
        var specific = ex.getMostSpecificCause();
        if (cause instanceof org.hibernate.exception.ConstraintViolationException constraintEx) {
            var sqlCode = constraintEx.getSQLState();
            String errorMessage = "";
            switch (sqlCode) {
                case "23502" -> {
                    var exMessage = constraintEx.getSQLException().getMessage();
                    errorMessage = String.format("%s - [%s]",
                                                 exMessage.substring(0, exMessage.indexOf(";")).replace("\"", "'"),
                                                 sqlCode);
                    break;
                }
                case "23506" -> errorMessage = String.format("""
                                                             Lỗi tính toàn vẹn dữ liệu (liên kết khoá ngoại [%s]): %s.
                                                             """,
                                                             sqlCode,
                                                             constraintEx.getConstraintName());
            }
            return ProblemDetail.forStatusAndDetail(CONFLICT, errorMessage);
        }
        return ProblemDetail.forStatusAndDetail(CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    ProblemDetail handleTransaction(TransactionSystemException ex) {

        return ProblemDetail.forStatusAndDetail(CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    ProblemDetail handleDuplicateKey(DuplicateKeyException ex) {
        return ProblemDetail.forStatusAndDetail(CONFLICT, ex.getMessage());
    }

}
