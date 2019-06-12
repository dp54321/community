package life.dengpeng.community.enums;

/**
 * 问题异常信息
 */
public enum BJEnum implements IBJEnum{
    QUESTION_NOT_FOUND(2001,"这个问题不存在,要不要换一个试试"),
    NO_LOGIN(2002,"未登录，请先登录"),
    COMMENT_QUESTION_NOT_FOUND(2003,"你回复的问题有可能被删除,要不要换一个试试"),
    COMMENT_NOT_FOUND(2004,"回复这个评论可能被删除,要不要换一个试试"),
    SYS_ERROR(2005,"系统出现错误,请稍后再试"),
    NO_COMMENT(2006,"没有该评论类型"),
    QUESTION_COMMENT_NOT_FOUND(2007,"你评论的问题或评论可能被删除"),
    COMMENT_NULL(2008,"评论内容为空");
    private String message;

    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    BJEnum(Integer code,String message) {
        this.code  = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
