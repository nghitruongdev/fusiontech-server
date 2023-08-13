package com.vnco.fusiontech.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class SpringExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleNotFound(EntityNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull  MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        var errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> String.format("Field: %s -> %s, received: '%s'",
                        err.getField(),
                        err.getDefaultMessage(),
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    ProblemDetail handleDataIntegration(DataIntegrityViolationException ex) {
        var cause = ex.getCause();
        var root = ex.getRootCause();
        var specific = ex.getMostSpecificCause();
                if (cause instanceof org.hibernate.exception.ConstraintViolationException constraintEx) {
                    log.debug(constraintEx.getConstraintName());
            return ProblemDetail.forStatusAndDetail(CONFLICT, getMessage(constraintEx));
        }
        return ProblemDetail.forStatusAndDetail(CONFLICT,ex.getMessage());
        
    }
    private String getMessage(org.hibernate.exception.ConstraintViolationException ex){
        var sqlCode = ex.getSQLState();
        var errorCode = ex.getErrorCode();
        var constraint = ex.getSQLException().getMessage();
        if(errorCode == 1451){
            if(constraint.contains("FK_inventory_detail_inventory"))
                return """
                       Lỗi ràng buộc dữ liệu: %s - [%s].
                       """.formatted("phiếu nhập kho vẫn còn chi tiết nhập kho liên quan", errorCode);
        }
        var error = "";
        if(constraint.startsWith("Duplicate")){
            String  regex   = "'.+?'"; // Regular expression to match inside single quotes
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(constraint);
            if(matcher.find()){
                return String.format("Lỗi trùng lặp dữ liệu: %s - [%s]", matcher.group(),  errorCode);
            }
        }
//        else{
//            error = constraint.substring(0, constraint.indexOf(";"))
//                              .replace("\"", "");
//        }
//        switch (sqlCode) {
//            case "23502" -> {
//                var exMessage = constraintEx.getConstraintName();
//                errorMessage = String.format("%s - [%s]",
//                                             exMessage.substring(0, exMessage.indexOf(";")).replace("\"", "'"),
//                                             sqlCode);
//            }
//            case "23506" -> errorMessage = String.format("""
//                                                             Lỗi tính toàn vẹn dữ liệu (liên kết khoá ngoại [%s]): %s.
//                                                             """,
//                                                         sqlCode,
//                                                         constraintEx.getConstraintName());
//            default -> errorMessage = constraintEx.getSQLException().getMessage();
//        }
        return String.format("%s - [%s]", StringUtils.hasText(error)? error: constraint, sqlCode);
    }
    
    @ExceptionHandler(TransactionSystemException.class)
    ProblemDetail handleTransaction(TransactionSystemException ex) {

        return ProblemDetail.forStatusAndDetail(CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    ProblemDetail handleResourceNotFound(RecordNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    ProblemDetail handleDuplicateKey(DuplicateKeyException ex) {
        return ProblemDetail.forStatusAndDetail(CONFLICT, ex.getMessage());
    }
    
    @ExceptionHandler(InvalidRequestException.class)
    ProblemDetail handleInvalid(InvalidRequestException ex) {
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
    }
    
    @ExceptionHandler(RecordExistsException.class)
    ProblemDetail handleExists(RecordExistsException ex) {
        return ProblemDetail.forStatusAndDetail(CONFLICT, ex.getMessage());
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    ProblemDetail handleUnauthorized(UnauthorizedException ex) {
        return ProblemDetail.forStatusAndDetail(UNAUTHORIZED, ex.getMessage());
    }
    
    @ExceptionHandler(NotAcceptedRequestException.class)
    ProblemDetail handleNotAccepted(NotAcceptedRequestException ex) {
        return ProblemDetail.forStatusAndDetail(NOT_ACCEPTABLE, ex.getMessage());
    }
    
}
