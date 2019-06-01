package life.dengpeng.community.exception;

import life.dengpeng.community.enums.IBJEnum;


public class BJException extends RuntimeException{

    private String message;

    public BJException(IBJEnum ibjEnum){
        this.message = ibjEnum.getMessage();
    }

    public BJException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
