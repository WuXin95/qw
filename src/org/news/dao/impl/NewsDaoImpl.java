package org.news.dao.impl;

import org.news.dao.BaseDao;
import org.news.dao.NewsDao;
import org.news.entity.News;
import org.news.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl extends BaseDao implements NewsDao {

    public NewsDaoImpl(Connection conn) {
        super(conn);
    }

    // 获取所有新闻
    public List<News> getAllnews() throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        String sql = "SELECT `nid`, `ntid`, `ntitle`, `nauthor`,"
                + " `ncreateDate`, `nsummary`, `tname` FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid`"
                + " ORDER BY `ncreateDate` DESC";
        try {
            rs = this.executeQuery(sql,null);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return list;
    }

    // 获取某主题下的所有新闻
    public List<News> getAllnewsByTID(int tid) throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        String sql = "SELECT `nid`, `ntid`, `ntitle`, `nauthor`,"
                + " `ncreateDate`, `nsummary`, `tname` FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid` AND `NEWS`.`ntid` = ?"
                + " ORDER BY `ncreateDate` DESC";
        try {
            //通过数组的方式把tid传入executeQuery(sql, params)方法中;
            Object[] params = new Object[] {tid};
            rs = this.executeQuery(sql, params);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return list;
    }

    // 获取某主题下的最新新闻
    public List<News> getLatestNewsByTID(int tid, int limit) throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        String sql = "SELECT `nid`, `ntid`, `ntitle` FROM `NEWS` WHERE"
                + " `ntid` = ? ORDER BY `ncreatedate` DESC LIMIT ?";
        //通过数组的方式把tid,limit传入executeQuery(sql, params)方法中;
        Object[] params = new Object[] { tid,limit };
        try {
            rs = this.executeQuery(sql, params);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return list;
    }

    // 获取某主题下的新闻数量
    public int getNewsCountByTID(int tid) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT COUNT(`ntid`) FROM `news` WHERE `ntid` = ?";
        int count = -1;
        //通过数组的方式把tid传入executeQuery(sql, params)方法中;
        Object[] params = { tid};
        try {
            rs = this.executeQuery(sql, params);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return count;
    }

    // 获取某条新闻
    public News getNewsByNID(int nid) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid` AND `NEWS`.`nid` = ?"
                + " ORDER BY `ncreateDate` DESC";
        News news = null;
        //通过数组的方式把nid传入executeQuery(sql, params)方法中;
        Object[] params = { nid};
        try {
            rs = this.executeQuery(sql, params);
            if (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNpicpath(rs.getString("npicPath"));
                news.setNcontent(rs.getString("ncontent"));
                news.setNmodifydate(rs.getTimestamp("nmodifyDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return news;
    }

    public List<News> getAllnewsByTname(String tname) throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        // 获取某主题下的所有新闻
        String sql = "SELECT `nid`, `ntid`, `ntitle`, `nauthor`,"
                + " `ncreateDate`, `nsummary`, `tname` FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid` AND `TOPIC`.`tname` = ?"
                + " ORDER BY `ncreateDate` DESC";
        //通过数组的方式把nid传入executeQuery(sql, params)方法中;
        Object[] params = { tname};
        try {
            rs = this.executeQuery(sql, params);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return list;
    }

    // 根据某个主题的id获得该主题所有新闻的数量
    public int getTotalCount(Integer tid) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT COUNT(`nid`) FROM `news`";
        if (tid != null)
            sql += " WHERE `ntid` = ?";
        int count = -1;

        try {
            if (tid == null) {
                rs = this.executeQuery(sql, null);
            }
            else {
                //通过数组的方式把tid传入executeQuery(sql, params)方法中;
                Object[] params = {tid};
                rs = this.executeQuery(sql, params);
            }
            //判断rs是否有值
            while (rs.next()) {

                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return count;
    }

    // 分页获得新闻
    public List<News> getPageNewsList(Integer tid, int pageNo, int pageSize)
            throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        String sql = "SELECT `nid`, `ntid`, `ntitle`, `nauthor`,"
                + " `ncreateDate`, `nsummary`, `tname` FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid`";
        if (tid != null)
            sql += " AND `NEWS`.`ntid` = ?";
        sql += " ORDER BY `ncreateDate` DESC LIMIT ?, ?";
        try {
            if (tid == null) {
                //通过数组的方式把pageNo,pageSize传入executeQuery(sql, params)方法中;
                Object[] params = {(pageNo - 1) * pageSize, pageSize};
                rs = this.executeQuery(sql, params);
            }
            else {
                //通过数组的方式把tid,pageNo,pageSize传入executeQuery(sql, params)方法中;
                Object[] params = {tid,(pageNo - 1) * pageSize, pageSize};
                rs = this.executeQuery(sql, params);
            }
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
        return list;
    }
}
