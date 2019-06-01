package life.dengpeng.community.service;

import life.dengpeng.community.mapper.TbUserMapper;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.model.TbUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;


    /**
     * 增加和修改用户信息
     * @param tbUser
     */
    public void saveOrUpdate(TbUser tbUser) {

        TbUserExample example = new TbUserExample();
        example.createCriteria().andAccountIdEqualTo(tbUser.getAccountId());
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if(tbUsers != null && tbUsers.size()>0){
            TbUser user = new TbUser();
            user.setUid(tbUsers.get(0).getUid());
            user.setAvatarUrl(tbUser.getAvatarUrl());
            user.setToken(tbUser.getToken());
            user.setBio(tbUser.getBio());
            user.setUname(tbUser.getUname());
            user.setGmtModifled(System.currentTimeMillis());
            tbUserMapper.updateByPrimaryKeySelective(user);
        }else {
            tbUserMapper.insert(tbUser);
        }


    }
}
