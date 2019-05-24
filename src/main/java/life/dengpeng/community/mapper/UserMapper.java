package life.dengpeng.community.mapper;

import life.dengpeng.community.model.TbUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author dp
 * @create 2019-05-22 13:32
 */
@Mapper
public interface UserMapper {

    @Insert("insert into tb_user (account_id,uname,token,bio,gmt_create,gmt_modifled,avatar_url) values (#{accountId},#{uname},#{token},#{bio},#{gmtCreate},#{gmtModiflde},#{avatarUrl})")
    void insertUser(TbUser tbUser);


    @Select("select uid,account_id,uname,token,bio,gmt_create,gmt_modifled from tb_user where token = #{token}")
    TbUser findUserByToken(@Param("token") String token);

    @Select("select uid,account_id,uname,token,bio,gmt_create,gmt_modifled,avatar_url from tb_user where uid = #{id}")
    TbUser findUserByid(@Param("id") Long id);


}
