package ru.miaat.Ecommerce.Exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.miaat.Ecommerce.Dto.Response;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleAllException(Exception e, WebRequest request) {
        Response errorResponse = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFoundException(Exception e, WebRequest request) {
        Response notFoundResponse = Response.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(notFoundResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response> handleInvalidCredentialsException(Exception e, WebRequest request) {
        Response response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
