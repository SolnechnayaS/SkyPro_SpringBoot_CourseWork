package org.ru.skypro.coursework.AuctionSystem.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class AuctionSystemExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuctionSystemExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<?> handlerIOException(IOException ioException) {
        logger.error("Not Found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<?> handlerSQLException(SQLException sqlException) {
        logger.error("Internal Server Error");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerException(Exception exception) {
        logger.error("Forbidden");
        return new ResponseEntity<>("Ошибка запроса, переданы некорректные параметры",HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNullPointerException(NullPointerException nullPointerException) {
        logger.error("Null Pointer Exception");
        return new ResponseEntity<>("Данные не найдены",HttpStatus.BAD_REQUEST);
    }

}
