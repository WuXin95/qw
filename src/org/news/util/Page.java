package org.news.util;

import org.news.entity.News;

import java.util.List;

public class Page {
    // 总页数
    private int totalPageCount = 0;
    // 页面大小，即每页显示记录数
    private int pageSize = 5;
    // 记录总数
    private int totalCount;
    // 当前页码
    private int currPageNo = 1;
    // 每页新闻集合
    private List<News> newsList;

    //获得当前当前页码
    public int getCurrPageNo() {
        if (totalPageCount == 0) {
            return 0;
        }
        return currPageNo;
    }

    //设置当前页码
    public void setCurrPageNo(int currPageNo) {
        if (currPageNo > 0) {
            this.currPageNo = currPageNo;
        }
    }

    //获得每页显示的新闻条数
    public int getPageSize() {
        return pageSize;
    }

    //设置每页显示的新闻条数
    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    //获得新闻总条数
    public int getTotalCount() {
        return totalCount;
    }

    //设置新闻总条数
    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            // 计算总页数
            totalPageCount = this.totalCount % pageSize == 0 ? (this.totalCount / pageSize)
                    : (this.totalCount / pageSize + 1);
        }
    }

    //获得新闻总页数
    public int getTotalPageCount() {
        return totalPageCount;
    }

    //设置新闻总页数
    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    //获得当前页新闻集合
    public List<News> getNewsList() {
        return newsList;
    }

    //设置当前页新闻集合
    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
