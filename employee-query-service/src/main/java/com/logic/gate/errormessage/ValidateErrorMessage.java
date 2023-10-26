package com.logic.gate.errormessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateErrorMessage {
    private HttpStatus httpStatus;
    private String message;
    private String description;
    private Date date;
}
