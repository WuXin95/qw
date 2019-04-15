package org.news.dao.impl;

import org.news.dao.BaseDao;
import org.news.dao.UserDao;
import org.news.entity.User;
import org.news.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends BaseDao implements UserDao {

    public UserDaoImpl(Connection conn) {
        super(conn);
    }

    public User findUser(String uname, String password) throws SQLException {
        ResultSet rs = null;
        User user = null;
        // 根据用户名密码查找匹配的用户
        String sql = "select * from news_users where uname=? and upwd=?";
        //通过数组的方式把uname,password传入executeQuery(sql, params)方法中;
        Object[] params = new Object[] { uname, password};
        try {
            rs = this.executeQuery(sql, params);
            if (rs.next()) {
                user = new User();
                user.setUid(rs.getInt("uid"));
                user.setUname(uname);
                user.setUpwd(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return user;
    }
}
