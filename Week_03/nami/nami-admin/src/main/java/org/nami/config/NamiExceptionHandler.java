package org.nami.config;

import org.nami.exception.NamiException;
import org.nami.vo.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * NamiExceptionHandler
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@RestControllerAdvice
public class NamiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handlerBusinessException(Exception exception) {
        return Result.error(convertNamiException(exception));
    }

    private NamiException convertNamiException(Exception exception) {
        NamiException namiException;
        if (exception instanceof NamiException) {
            namiException = (NamiException) exception;

        } else if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            BindingResult bindingResult = bindException.getBindingResult();
            namiException = new NamiException(getErrorMsg(bindingResult));

        } else if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) exception;
            BindingResult bindingResult = validException.getBindingResult();
            namiException = new NamiException(getErrorMsg(bindingResult));

        } else {
            namiException = new NamiException(exception.getMessage());
        }
        return namiException;
    }

    private String getErrorMsg(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        StringBuilder sb = new StringBuilder();
        fieldErrors.forEach(fieldError -> {
            sb.append(fieldError.getDefaultMessage());
            sb.append("-");
        });
        return sb.toString();
    }
}
