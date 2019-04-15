package org.news.dao;

import org.news.entity.News;

import java.sql.SQLException;
import java.util.List;

public interface NewsDao {
    // 获取所有新闻
    public List<News> getAllnews() throws SQLException;
    // 获取某主题下的所有新闻（根据主题id）
    @Deprecated
    public List<News> getAllnewsByTID(int Tid) throws SQLException;
    // 获取某主题下的所有新闻（根据主题名称）
    public List<News> getAllnewsByTname(String tname) throws SQLException;
    // 获取某主题下的新闻数量
    public int getNewsCountByTID(int Tid) throws SQLException;
    // 获取某主题下的最新新闻
    public List<News> getLatestNewsByTID(int tid, int limit) throws SQLException;
    //获取某条新闻
    public News getNewsByNID(int nid) throws SQLException;
    // 获得新闻总数
    public int getTotalCount(Integer tid) throws SQLException;
    // 分页获得新闻(根据主题id,页码，页显示条数)
    public List<News> getPageNewsList(Integer tid, int pageNo, int pageSize) throws SQLException;
}