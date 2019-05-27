package life.dengpeng.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dp
 * @create 2019-05-27 12:45
 */
@Data
public class PageDTO {
    private List rows; //当前页数据
    private Integer page;//当前页
    private Integer totalPage; //总页数
    private Integer total;//总条数
    private List<Integer> pages = new ArrayList<>();//页码
    private boolean lastPage; //上一页
    private boolean nextPage;//下一页
    private boolean firstPage;//首页
    private boolean endPage;//尾页


}
