package life.dengpeng.community.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagDTO {
    String type;
    List<String> tags = new ArrayList<>();


    public TagDTO(String type) {
        this.type = type;
    }
}
