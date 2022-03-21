package model.mapper;

import model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {
    @Select("select count(*) from user where " +
            "userName=#{userName} and password=#{password}")
    int isUserExist(User user);

    @Update("update user set password=#{password} where userName=#{userName}")
    int modifyPassword(@Param("userName") String userName, @Param("password") String password);
}
