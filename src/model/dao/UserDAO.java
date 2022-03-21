package model.dao;

import model.MybatisUtil;
import model.entity.User;
import model.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import util.MD5Util;
import view.Login;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

public class UserDAO implements Closeable {
    SqlSession sqlSession = null;
    UserMapper userMapper = null;

    private UserDAO(boolean autoCommit) {
        sqlSession = MybatisUtil.getSession(autoCommit);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    static public UserDAO getInstance(boolean autoCommit) {
        return new UserDAO(autoCommit);
    }

    public int isUserExist(User user) {
        return userMapper.isUserExist(user);
    }

    public int modifyPassword(String password) throws SQLException {
        String username = Login.name_cache;
        String s = MD5Util.stringToMD5(password);
        return userMapper.modifyPassword(username, s);
    }

    @Override
    public void close() throws IOException {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
