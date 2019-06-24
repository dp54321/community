package life.dengpeng.community.service;

import life.dengpeng.community.dto.CommentDTO;
import life.dengpeng.community.dto.ResponseCommentDTO;
import life.dengpeng.community.enums.BJEnum;
import life.dengpeng.community.enums.NotifyTypeEnum;
import life.dengpeng.community.exception.BJException;
import life.dengpeng.community.mapper.*;
import life.dengpeng.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TbCommentService {

    @Autowired
    private TbCommentMapper tbCommentMapper;
    @Autowired
    private TbQuestionExtMapper tbQuestionExtMapper;
    @Autowired
    private TbQuestionMapper tbQuestionMapper;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbCommentExtMapper tbCommentExtMapper;
    @Autowired
    private TbNotifyMapper tbNotifyMapper;


    @Transactional
    public void insertComment(CommentDTO commentDTO) {
        TbComment comment = new TbComment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModifled(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setCommentCount(0L);

        if(comment.getContent() == null){
            throw new BJException(BJEnum.COMMENT_NULL);
        }


        //评论的问题被删除
        if(comment.getParentId() == null){
            throw new BJException(BJEnum.QUESTION_COMMENT_NOT_FOUND);
        }

        TbQuestion dbQuestion = tbQuestionMapper.selectByPrimaryKey(comment.getParentId());
        if(comment.getType() == 1){
            if(dbQuestion == null) {
                throw new BJException(BJEnum.COMMENT_QUESTION_NOT_FOUND);
            }
        }

        if(!(comment.getType() != 1) && !(comment.getType() != 2)){
            throw new BJException(BJEnum.NO_COMMENT);
        }
        //回复评论
        if(comment.getType() == 2){
            TbComment dbComment = tbCommentMapper.selectByPrimaryKey(comment.getParentId());
            //回复的评论被删除
            if(dbComment == null ){
                throw new BJException(BJEnum.COMMENT_NOT_FOUND);
            }
            tbCommentMapper.insert(comment);
            TbComment addCommentCount = new TbComment();
            addCommentCount.setId(dbComment.getId());
            addCommentCount.setCommentCount(1L);
            tbCommentExtMapper.incComment(addCommentCount);
            //通知
            TbNotify notify = getTbNotify(comment,dbComment.getParentId(), dbComment.getCommentator(),dbComment.getContent(),NotifyTypeEnum.REPLY_QUESTION.getType());
            tbNotifyMapper.insert(notify);

        }else {
            tbCommentMapper.insert(comment);
            TbQuestion question = new TbQuestion();
            question.setId(comment.getParentId());
            question.setCommentCount(1);
            tbQuestionExtMapper.incComment(question);
            //通知
            TbNotify notify = getTbNotify(comment,comment.getParentId(), dbQuestion.getCreator(),dbQuestion.getTitle(),NotifyTypeEnum.REPLY_COMMENT.getType());
            tbNotifyMapper.insert(notify);
        }
    }

    private TbNotify getTbNotify(TbComment comment,Long parentId, Long receiver,String title,Integer type) {
        TbNotify notify = new TbNotify();
        notify.setNotifier(comment.getCommentator());
        notify.setReceiver(receiver);
        notify.setNotifyName(title);
        notify.setType(type);
        notify.setStatus(0);
        notify.setQuestionid(parentId);
        notify.setGmtCreate(System.currentTimeMillis());
        return notify;
    }

    //查询问题评论列表
    public List<ResponseCommentDTO> findCommentByParentId(Long parentId,Integer type) {

        TbCommentExample example = new TbCommentExample();
        example.createCriteria()
                .andParentIdEqualTo(parentId)
                .andTypeEqualTo(type);
        example.setOrderByClause("gmt_create desc");
        List<TbComment> tbComments = tbCommentMapper.selectByExample(example);
        if(tbComments == null || tbComments.size() ==0){
            return new ArrayList();
        }
        Set<Long> collect = tbComments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        List<Long> uids = new ArrayList<>();
        uids.addAll(collect);
        TbUserExample userExample = new TbUserExample();
        userExample.createCriteria().andUidIn(uids);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(userExample);
        Map<Long, TbUser> userMap = tbUsers.stream().collect(Collectors.toMap(user -> user.getUid(), user -> user));

        List<ResponseCommentDTO> commentDTOS = tbComments.stream().map(comment -> {
            ResponseCommentDTO responseCommentDTO = new ResponseCommentDTO();
            BeanUtils.copyProperties(comment, responseCommentDTO);
            responseCommentDTO.setTbUser(userMap.get(responseCommentDTO.getCommentator()));
            return responseCommentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }







}
