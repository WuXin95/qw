package org.news.dao.impl;

import org.news.dao.BaseDao;
import org.news.dao.TopicsDao;
import org.news.entity.Topic;
import org.news.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicsDaoImpl extends BaseDao implements TopicsDao {

    public TopicsDaoImpl(Connection conn) {
        super(conn);
    }

    public List<Topic> getAllTopics() throws SQLException {
        List<Topic> list = new ArrayList<Topic>();
        ResultSet rs = null;
        // 获取所有主题
        String sql = "select * from topic";
        try {
            rs = executeQuery(sql,null);
            Topic topic = null;
            while (rs.next()) {
                topic = new Topic();
                topic.setTid(rs.getInt("tid"));
                topic.setTname(rs.getString("tname"));
                list.add(topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return list;
    }

    public int deleteTopic(int tid) throws SQLException {
        String sql = "DELETE FROM `TOPIC` WHERE `tid` = ?";
        int result = 0;
        try {
            //通过数组的方式把tid传入executeQuery(sql, params)方法中;
            Object[] params = new Object[] {tid};
            result = executeUpdate(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public int updateTopic(Topic topic) throws SQLException {
        String sql = "UPDATE `TOPIC` SET `tname` = ? WHERE `tid` = ?";
        int result = 0;
        try {
            //通过数组的方式把topic.getTname(), topic.getTid()传入executeQuery(sql, params)方法中;
            Object[] params = new Object[] {topic.getTname(), topic.getTid()};
            result = executeUpdate(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public Topic findTopicByName(String name) throws SQLException {
        String sql = "select * from topic where tname=?";
        ResultSet rs = null;
        Topic topic = null;
        try {
            //通过数组的方式把name传入executeQuery(sql, params)方法中;
            Object[] params = new Object[] {name};
            rs = executeQuery(sql, params);
            if (rs.next()) {
                topic = new Topic();
                topic.setTid(rs.getInt("tid"));
                topic.setTname(rs.getString("tname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return topic;
    }

    public int addTopic(String name) throws SQLException {
        String sql = "insert into topic(TNAME) values(?)";
        int result = 0;
        //通过数组的方式把name传入executeQuery(sql, params)方法中;
        Object[] params = new Object[] {name};
        try {

            result = executeUpdate(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }
}
