package life.dengpeng.community.service;

import life.dengpeng.community.dto.NotifyDTO;
import life.dengpeng.community.dto.PageDTO;
import life.dengpeng.community.enums.BJEnum;
import life.dengpeng.community.enums.NotifyTypeEnum;
import life.dengpeng.community.exception.BJException;
import life.dengpeng.community.mapper.TbNotifyMapper;
import life.dengpeng.community.mapper.TbQuestionMapper;
import life.dengpeng.community.mapper.TbUserMapper;
import life.dengpeng.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dp
 * @create 2019-06-23 11:18
 */
@Service
public class TbNotifyService {

    @Autowired
    private TbNotifyMapper tbNotifyMapper;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbQuestionMapper tbQuestionMapper;

    public PageDTO findNotifyByReceiver(Long uid,int page,int size) {
        TbNotifyExample example = new TbNotifyExample();
        example.createCriteria().andReceiverEqualTo(uid);
        Integer total = (int)tbNotifyMapper.countByExample(example);
        PageDTO pageDTO  =  PageDTO.setPageDTO(total, page, size);
        if(total != null && total>0){
            TbNotifyExample example1 = new TbNotifyExample();
            example1.createCriteria().andReceiverEqualTo(uid);
            example1.setOrderByClause("gmt_create desc");
            int offset = (page - 1) * size;
            List<TbNotify> tbNotifies = tbNotifyMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
            List<Long> collect = tbNotifies.stream().map(notify -> notify.getNotifier()).collect(Collectors.toList());
            TbUserExample userExample = new TbUserExample();
            userExample.createCriteria().andUidIn(collect);
            List<TbUser> tbUsers = tbUserMapper.selectByExample(userExample);
            Map<Long, TbUser> userMap = tbUsers.stream().collect(Collectors.toMap(u -> u.getUid(), u -> u));
            ArrayList<NotifyDTO> list = new ArrayList<>();
            for (TbNotify notify : tbNotifies){
                NotifyDTO notifyDTO = new NotifyDTO();
                notifyDTO.setTbUser(userMap.get(notify.getNotifier()));
                notifyDTO.setNotifyId(notify.getId());
                notifyDTO.setTitle(notify.getNotifyName());
                notifyDTO.setTypeName(NotifyTypeEnum.nameOfType(notify.getType()));
                notifyDTO.setStatus(notify.getStatus());
                list.add(notifyDTO);
            }
            pageDTO.setRows(list);
        }
        return pageDTO;

    }

    public Long updateNotifyStatus(Long id) {
        TbNotify notify = tbNotifyMapper.selectByPrimaryKey(id);
        TbQuestion question = tbQuestionMapper.selectByPrimaryKey(notify.getQuestionid());
        if(question == null){
            throw new BJException(BJEnum.NOTIFY_QUESTION_NULL);
        }
        if(notify ==null){
            throw new BJException(BJEnum.NOTIFY_QUESTION_NULL);
        }
        notify.setStatus(1);
        tbNotifyMapper.updateByPrimaryKey(notify);
        return notify.getQuestionid();
    }
}
