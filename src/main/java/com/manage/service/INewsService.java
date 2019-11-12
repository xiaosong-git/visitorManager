package com.manage.service;

import com.manage.model.News;

import java.util.List;

public interface INewsService {

    /**
     * 分页查询新闻
     * @param newsName
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<News> findByPage(String newsName, int pageNo, int pageSize)throws Exception;

    /**
     * 插入新闻数据
     * @param news
     * @return
     * @throws Exception
     */
    public Integer addNews(News news)throws Exception;

    /**
     * 修改新闻数据
     * @param news
     * @return
     * @throws Exception
     */
    public Integer updateNews(News news)throws Exception;

    /**
     * 删除新闻数据
     * @param ids
     * @return
     * @throws Exception
     */
    public Integer deleteNews(List<Long> ids)throws Exception;

    /**
     * 修改新闻状态
     * @param news
     * @return
     * @throws Exception
     */
    public Integer updateNewsStatus(News news)throws Exception;
}
