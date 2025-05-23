package com.company.coursemanager.utils.exception;

import com.company.coursemanager.utils.RestResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Contains all custom exceptions with corresponding constructor enabled.
 */
public class GlobalExceptionWrapper {

    private GlobalExceptionWrapper() {
    }

    /**
     * Returns the bad request exception with the given message and bad request http status code
     */
    @Getter
    public static class BadRequestException extends RuntimeException implements IGlobalException {

        private final HttpStatus httpStatus;

        public BadRequestException(String message) {
            super(message);
            this.httpStatus = HttpStatus.BAD_REQUEST;
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            return ResponseEntity.badRequest().body(setErrorResponse(exception));
        }
    }

    /**
     * Returns the not found exception with the given message and not found http status code
     */
    @Getter
    public static class NotFoundException extends RuntimeException implements IGlobalException {

        private final HttpStatus httpStatus;

        public NotFoundException(String message) {
            super(message);
            this.httpStatus = HttpStatus.NOT_FOUND;
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            RestResponse errorResponse = setErrorResponse(exception);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns the custom io exception with the given message and internal server error http status
     * code
     */
    @Getter
    public static class FileOperationException extends IOException implements IGlobalException {

        public FileOperationException(String message) {
            super(message);
        }
    }

    /**
     * Returns the generic exception with the exception message and internal server error http status
     * code
     */
    @Getter
    public static class GenericException extends Exception implements IGlobalException {

        private final Exception exception;

        public GenericException(Exception exception) {
            this.exception = exception;
        }
    }
}
