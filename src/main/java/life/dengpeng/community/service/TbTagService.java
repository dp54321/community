package life.dengpeng.community.service;


import life.dengpeng.community.dto.TagDTO;
import life.dengpeng.community.mapper.TbTagExtMapper;
import life.dengpeng.community.mapper.TbTagMapper;
import life.dengpeng.community.model.TbTag;
import life.dengpeng.community.model.TbTagExample;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TbTagService {

    @Autowired
    private TbTagMapper tagMapper;

    @Autowired
    private TbTagExtMapper tbTagExtMapper;

    public List<TagDTO> findAllTag() {


        List<TbTag> tbTags = tagMapper.selectByExample(new TbTagExample());
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO tagDTO1 = new TagDTO("开发语言");
        TagDTO tagDTO2 = new TagDTO("平台架构");
        TagDTO tagDTO3 = new TagDTO("服务器");
        TagDTO tagDTO4 = new TagDTO("数据库");
        TagDTO tagDTO5 = new TagDTO("开发工具");
        TagDTO tagDTO6 = new TagDTO("其他");
        System.out.println(tbTags.size());
        for (TbTag tag : tbTags) {
            if (tag.getType() == 1) {
                tagDTO1.getTags().add(tag.getTagName());
            } else if (tag.getType() == 2) {
                tagDTO2.getTags().add(tag.getTagName());
            } else if (tag.getType() == 3) {
                tagDTO3.getTags().add(tag.getTagName());
            } else if (tag.getType() == 4) {
                tagDTO4.getTags().add(tag.getTagName());
            } else if (tag.getType() == 5) {
                tagDTO5.getTags().add(tag.getTagName());
            } else {
                tagDTO6.getTags().add(tag.getTagName());
            }
        }
        tagDTOS.add(tagDTO1);
        tagDTOS.add(tagDTO2);
        tagDTOS.add(tagDTO3);
        tagDTOS.add(tagDTO4);
        tagDTOS.add(tagDTO5);
        tagDTOS.add(tagDTO6);
        return tagDTOS;
    }


    public String filterInvalid(String tags) {
        List<String> tagNames = tbTagExtMapper.selectTagNamesAll();
        String[] split = StringUtils.split(tags, ",");
        String invalid = Arrays.stream(split).filter(t -> !tagNames.contains(t)).collect(Collectors.joining(","));
        return invalid ;
    }
}
