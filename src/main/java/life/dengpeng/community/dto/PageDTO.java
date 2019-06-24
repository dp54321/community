package life.dengpeng.community.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class PageDTO implements Serializable {
    private List rows; //当前页数据
    private Integer page;//当前页
    private Integer totalPage; //总页数
    private Integer total;//总条数
    private List<Integer> pages = new ArrayList<>();//页码
    private boolean lastPage; //上一页
    private boolean nextPage;//下一页
    private boolean firstPage;//首页
    private boolean endPage;//尾页


    //赋值PageDTO 分页
    public static PageDTO setPageDTO(Integer total,Integer page,Integer size) {

        PageDTO pageDTO = new PageDTO();
        pageDTO.setTotal(total); //总记录
        if (total % size == 0) {   //总页数
            pageDTO.setTotalPage(total / size);
        } else {
            pageDTO.setTotalPage(total / size + 1);
        }

        if(page < 1){
            page = 1;
        }
        if(page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }
        pageDTO.setPage(page);  //当前页
        List<Integer> pages = new ArrayList<>();
        pages.add(page);
        for(int i=1;i<=2;i++){
            if(page-i>0){   //向前追加两个页码
                pages.add(0,page-i);
            }
            if(page+i<=pageDTO.getTotalPage()){  //向后追加两个页码
                pages.add(page+i);
            }
        }

        pageDTO.setPages(pages);

        if (pageDTO.getPages().contains(1)) { //是否显示首页
            pageDTO.setFirstPage(false);
        } else {
            pageDTO.setFirstPage(true);
        }

        if (pageDTO.getPages().contains(pageDTO.getTotalPage())) {  //是否显示尾页
            pageDTO.setEndPage(false);
        } else {
            pageDTO.setEndPage(true);
        }

        if (page > 1) {  //是否显示上一页
            pageDTO.setLastPage(true);
        }
        if(page<pageDTO.getTotalPage()){  //是否显示下一页
            pageDTO.setNextPage(true);
        }
        return pageDTO;

    }


}
