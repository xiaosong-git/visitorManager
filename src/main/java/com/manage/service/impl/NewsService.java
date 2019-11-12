package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.NewsMapping;
import com.manage.model.News;
import com.manage.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService implements INewsService {

    @Autowired
    private NewsMapping newsMapping;

    @Override
    public List<News> findByPage(String newsName, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return newsMapping.findByPage(newsName+"%");
    }

    @Override
    public Integer addNews(News news) throws Exception {
        return newsMapping.addNews(news);
    }

    @Override
    public Integer updateNews(News news) throws Exception {
        return newsMapping.updateNews(news);
    }

    @Override
    public Integer deleteNews(List<Long> ids) throws Exception {
        return newsMapping.deleteNews(ids);
    }

    @Override
    public Integer updateNewsStatus(News news) throws Exception {
        return newsMapping.updateNewsStatus(news);
    }

}
