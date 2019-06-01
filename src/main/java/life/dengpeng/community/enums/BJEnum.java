package life.dengpeng.community.enums;


public enum BJEnum implements IBJEnum{
    QUESTION_ERROR("这个问题不存在,要不要换一个试试");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    BJEnum(String message) {
        this.message = message;
    }
}
