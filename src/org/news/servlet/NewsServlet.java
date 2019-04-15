package org.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.news.entity.Comment;
import org.news.entity.News;
import org.news.entity.Topic;
import org.news.service.CommentsService;
import org.news.service.NewsService;
import org.news.service.TopicsService;
import org.news.service.impl.CommentsServiceImpl;
import org.news.service.impl.NewsServiceImpl;
import org.news.service.impl.TopicsServiceImpl;
import org.news.util.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class NewsServlet extends HttpServlet {
    private static final long serialVersionUID = 7679716260193021854L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        String opr = request.getParameter("opr");
        TopicsService topicService = new TopicsServiceImpl();
        NewsService newsService = new NewsServiceImpl();
        CommentsService commentsService = new CommentsServiceImpl();
        try {
            if (opr.equals("addComment")) {// 添加评论
                String cauthor = request.getParameter("cauthor");
                String cnid = request.getParameter("nid");
                String cip = request.getParameter("cip");
                String ccontent = request.getParameter("ccontent");
                Comment comment = new Comment();
                comment.setCnid(Integer.parseInt(cnid));
                comment.setCcontent(ccontent);
                comment.setCdate(new java.util.Date());
                comment.setCip(cip);
                comment.setCauthor(cauthor);
                String result = "";
                try {
                    commentsService.addComment(comment);
                    result = "success";
                    /*
                    out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"评论成功，点击确认返回原来页面\");");
                    out.print("location.href=\"" + contextPath
                            + "/util/news?opr=readNew&nid=" + cnid + "\";");
                    out.print("</script>");
                    */
                } catch (Exception e) {
                    result = "评论添加失败！请联系管理员查找原因";
                    /*
                    out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"评论添加失败！请联系管理员查找原因！点击确认返回原来页面\");");
                    out.print("location.href=\"" + contextPath
                            + "/util/news?opr=readNew&nid=" + cnid + "\";");
                    out.print("</script>");
                    */
                }
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                out.print("[{\"result\":\"" + result + "\", \"cdate\":\""
                        + df.format(comment.getCdate()) + "\"}]");
            } else if ("readNew".equals(opr)) {// 读取某条新闻
                String nid = request.getParameter("nid");
                News news = newsService.findNewsByNid(Integer.parseInt(nid));
                news.setComments(commentsService.findCommentsByNid(Integer
                        .parseInt(nid)));
                Map<Integer, Integer> topics = new HashMap<Integer, Integer>();
                topics.put(1, 5);
                topics.put(2, 5);
                topics.put(5, 5);
                List<List<News>> latests = newsService
                        .findLatestNewsByTid(topics);
                request.setAttribute("news", news);
                request.setAttribute("list1", latests.get(0));// 左侧国内新闻
                request.setAttribute("list2", latests.get(1));// 左侧国际新闻
                request.setAttribute("list3", latests.get(2));// 左侧娱乐新闻
                request.getRequestDispatcher("/newspages/news_read.jsp")
                        .forward(request, response);
            } else if ("topicLatest".equals(opr)) { // 初始化首页侧边栏和主题列表
                Map<Integer, Integer> topics = new HashMap<Integer, Integer>();
                topics.put(1, 5);
                topics.put(2, 5);
                topics.put(5, 5);
                List<List<News>> latests = newsService
                        .findLatestNewsByTid(topics);
                List<Topic> list = topicService.findAllTopics();
                request.setAttribute("list1", latests.get(0));// 左侧国内新闻
                request.setAttribute("list2", latests.get(1));// 左侧国际新闻
                request.setAttribute("list3", latests.get(2));// 左侧娱乐新闻
                request.setAttribute("list", list); // 所有的主题
                request.getRequestDispatcher("/index.jsp").forward(request,
                        response);
            } else if ("topicNews".equals(opr)) { // 分页查询新闻
                String tid = request.getParameter("tid");
                String pageIndex = request.getParameter("pageIndex");// 获得当前页数
                if (pageIndex == null
                        || (pageIndex = pageIndex.trim()).length() == 0) {
                    pageIndex = "1";
                }
                int currPageNo = Integer.parseInt(pageIndex);
                if (currPageNo < 1)
                    currPageNo = 1;
                Page pageObj = new Page();
                pageObj.setCurrPageNo(currPageNo); // 设置当前页码
                pageObj.setPageSize(15); // 设置每页显示条数
                if (tid == null || (tid = tid.trim()).length() == 0)
                    newsService.findPageNews(null, pageObj);
                else
                    newsService.findPageNews(Integer.valueOf(tid), pageObj);
                String newsJSON = JSON.toJSONStringWithDateFormat(pageObj,
                        "yyyy-MM-dd HH:mm:ss",
                        SerializerFeature.WriteMapNullValue);
                out.print("[{\"tid\":\"" + tid + "\"}, " + newsJSON + "]");
            } else if ("list".equals(opr)) {// 编辑新闻时对新闻的查找
                List<News> list = newsService.findAllNews();
                // request.getSession().setAttribute("list", list);
                // response.sendRedirect(contextPath + "/newspages/admin.jsp");
                /*
                News news = null;
                StringBuffer newsJSON = new StringBuffer("[");
                for (int i = 0;;) {
                    news = list.get(i);
                    newsJSON.append("{\"nid\":" + news.getNid() + ",");
                    newsJSON.append("\"ntitle\":\""
                            + news.getNtitle().replace("\"", "&quot;") + "\",");
                    newsJSON.append("\"nauthor\":\""
                            + news.getNauthor().replace("\"", "&quot;") + "\"}");
                    if ((++i) == list.size()) {
                        break;
                    } else {
                        newsJSON.append(",");
                    }
                }
                newsJSON.append("]");
                */
                String newsJSON = JSON.toJSONStringWithDateFormat(list,
                                    "yyyy-MM-dd HH:mm:ss");
                out.print(newsJSON);
            } else if ("listHtml".equals(opr)) {// 编辑新闻时对新闻的查找
                List<News> list = newsService.findAllNews();
                request.setAttribute("list", list);
                request.getRequestDispatcher("/newspages/showNews.jsp")
                        .forward(request, response);
                /*
                News news = null;
                StringBuffer newsList = new StringBuffer("<ul class=\"classlist\">");
                for (int i = 0; i < list.size();) {
                    news = list.get(i);
                    newsList.append("<li>" + news.getNtitle() + "<span> 作者："
                        + news.getNauthor() + " &#160;&#160;&#160;&#160; "
                        + "<a href='#'>修改</a> &#160;&#160;&#160;&#160; "
                        + "<a href='#' onclick='return clickdel()'>删除</a>" 
                        + "</span></li>");
                    if ((++i) % 5 == 0) {
                        newsList.append("<li class='space'></li>");
                    }
                }
                newsList.append("</ul>");
                out.print(newsList);
                */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        this.doGet(request, response);
    }

}
