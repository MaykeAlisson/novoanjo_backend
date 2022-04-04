package br.com.novoanjo.novoanjo.controller;

import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.infra.exception.NotFoundException;
import br.com.novoanjo.novoanjo.infra.exception.StandardError;
import br.com.novoanjo.novoanjo.infra.exception.ValidateError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ResoucerExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

//    @Override
//    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
//                                                         WebRequest request) {
//
//        return handleValidationInternal(ex);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleValidationInternal(ex);
    }

    private ResponseEntity<Object> handleValidationInternal(MethodArgumentNotValidException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Set<ValidateError> errors = new HashSet<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            final String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ValidateError erro = new ValidateError(e.getField(), message);
            errors.add(erro);
        });

        return ResponseEntity.status(status).body(errors);
    }

//    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
//                                                            HttpStatus status, WebRequest request, BindingResult bindingResult) {
//        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
//        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
//
//        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
//                .map(objectError -> {
//                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
//
//                    String name = objectError.getObjectName();
//
//                    if (objectError instanceof FieldError) {
//                        name = ((FieldError) objectError).getField();
//                    }
//
//                    return Problem.Object.builder()
//                            .name(name)
//                            .userMessage(message)
//                            .build();
//                })
//                .collect(Collectors.toList());
//
//        Problem problem = createProblemBuilder(status, problemType, detail)
//                .userMessage(detail)
//                .objects(problemObjects)
//                .build();
//
//        return handleExceptionInternal(ex, problem, headers, status, request);
//    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(NotFoundException e, HttpServletRequest request) {
        String error = "Resource nou found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<StandardError> resourceBussinesException(BussinesException e, HttpServletRequest request) {
        String error = "Bussines error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }





}
