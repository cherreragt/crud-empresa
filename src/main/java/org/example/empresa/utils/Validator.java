package org.example.empresa.utils;

import org.example.empresa.exception.BadRequestException;
import org.springframework.validation.BindingResult;

public class Validator {

    public static void validation(BindingResult result) throws BadRequestException {
        var size = result.getAllErrors().size();
        if (size > 0) {
            var objectError = result.getAllErrors().get(0);
            var error = objectError.getDefaultMessage();
            throw new BadRequestException(error);
        }
    }
}
