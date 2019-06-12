package life.dengpeng.community.dto;

import life.dengpeng.community.enums.BJEnum;
import life.dengpeng.community.enums.IBJEnum;
import lombok.Data;

/**
 * @author dp
 * @create 2019-06-04 11:59
 */
@Data
public class ResultDTO<T> {

    private Integer code;
    private String message;
    private T t;

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultDTO(Integer code, String message,T t) {
        this.code = code;
        this.message = message;
        this.t = t;
    }

    public static ResultDTO errorOf(Integer code,String message){
        return new ResultDTO(code,message);
    }

    public static ResultDTO errorOf(IBJEnum ibjEnum){
        return new ResultDTO(ibjEnum.getCode(),ibjEnum.getMessage());
    }

    public static ResultDTO okOf(){

        return new ResultDTO(200,"请求成功");
    }

    public static <T> ResultDTO okOf(T t){

        return new ResultDTO(200,"请求成功",t);
    }



}
