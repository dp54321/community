package life.dengpeng.community.exception;

import life.dengpeng.community.enums.IBJEnum;

/**
 * 异常处理
 */
public class BJException extends RuntimeException{

    private Integer code;
    private String message;


    public BJException(IBJEnum ibjEnum){
        this.code = ibjEnum.getCode();
        this.message = ibjEnum.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
