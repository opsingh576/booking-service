package com.movie.ticket.booking.system.service.handlers;

import com.movie.ticket.booking.system.service.dtos.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BookingServiceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ResponseEntity<ResponseDTO> MethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException )
    {

      //this is not good for multithreaded environment,if 4 thread acess this and same error they will print 4*nosOferror
        //because here List<> errorMessages are immutable
       /* List<ObjectError> errors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        List<String> errorMessages = new ArrayList<>();

        for (ObjectError error : errors) {
            errorMessages.add(error.getDefaultMessage());
        }
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .errorMessage(errorMessages)
                .build(), HttpStatus.BAD_REQUEST);*/

        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .errorDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessage(
                        methodArgumentNotValidException.getBindingResult().getAllErrors()
                                .stream()
                                //.map(objectError -> objectError.getDefaultMessage()) //lambda can replace with method reference
                                //but all lambda can not be replace with method reference, here single call so we are doing
                                .map(ObjectError::getDefaultMessage)
                                .collect(Collectors.toList())

                )
                .build(), HttpStatus.BAD_REQUEST);

    }
}
