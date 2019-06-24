package life.dengpeng.community.enums;

/**
 * @author dp
 * @create 2019-06-23 10:37
 */
public enum NotifyTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    LIKE_QUESTION(3,"点赞了问题"),
    LIKE_COMMENT(4,"点赞了评论");

    private int type;
    private String typeName;


    NotifyTypeEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getType() {
        return type;
    }

    public  static String nameOfType(int type){
        for (NotifyTypeEnum notifyTypeEnum : NotifyTypeEnum.values()) {
            if(notifyTypeEnum.getType() == type){
                return notifyTypeEnum.getTypeName();
            }
        }
        return "";
    }
}
