package com.edu.neu.healthlung.interpretor;

import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.util.DTO;
import com.edu.neu.healthlung.util.DTOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({BadDataException.class})
    public DTO handleBadDataException(BadDataException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "未知错误";
        }
        return DTOFactory.forbiddenDTO(msg);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({DataIntegrityViolationException.class,})
    public DTO handleDataIntegrityViolationException(DataIntegrityViolationException e){
        String msg = "插入或删除操作缺少必要字段";
        logger.error(msg, e);
        return DTOFactory.forbiddenDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public DTO handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String msg = "违反唯一约束条件";
        logger.error(msg, e);
        return DTOFactory.forbiddenDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({DuplicateKeyException.class})
    public DTO handleDuplicateKeyException(DuplicateKeyException e){
        String msg = "违反唯一条件约束";
        logger.error(msg, e);
        return DTOFactory.forbiddenDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public DTO handleNotFoundException(NotFoundException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "资源未找到";
        }
        return DTOFactory.notFoundDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoAuthException.class)
    public DTO handleNoAuthException(NoAuthException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "无权限";
        }
        return DTOFactory.unauthorizedDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DefaultException.class)
    public DTO handleDefaultException(DefaultException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "其他错误";
        }
        return DTOFactory.unKnownErrorDTO(msg);
    }


    /**
     * 用来处理bean validation异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public  DTO resolveConstraintViolationException(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if(!CollectionUtils.isEmpty(constraintViolations)){
            StringBuilder msgBuilder = new StringBuilder();
            for(ConstraintViolation constraintViolation :constraintViolations){
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if(errorMessage.length()>1){
                errorMessage = errorMessage.substring(0,errorMessage.length()-1);
            }
            return DTOFactory.forbiddenDTO(errorMessage);
        }
        return DTOFactory.forbiddenDTO(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public DTO resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if(!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return DTOFactory.forbiddenDTO(errorMessage);
        }
        return DTOFactory.forbiddenDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public DTO handleException(Exception e) {
        String msg = e.getMessage();
        logger.error(msg, e);
        msg = "服务器未知错误";
        return DTOFactory.unKnownErrorDTO(msg);
    }

}
