package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.NoticeMapping;
import com.manage.model.Notice;
import com.manage.service.INoticeService;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/6/9.
 */
@Service
public class NoticeService implements INoticeService {

    @Autowired
    private NoticeMapping nticeMapping;

    /**
     * 获取所有公告
     * @return
     * @throws Exception
     */
    @Override
    public List<Notice> findAllList() throws Exception {
        return nticeMapping.selectAll();
    }

    /**
     * 获取最新公告的Id
     * @return
     * @throws Exception
     */
    @Override
    public Long findMaxId() throws Exception {
        return nticeMapping.findMaxId();
    }

    @Override
    public List<Notice> findByIdMixAndMin(Long orgNoticeMaxId) throws Exception {
        return nticeMapping.findByIdMixAndMin(orgNoticeMaxId);
    }

    @Override
    public List<Notice> findAllOrderBy() throws Exception {
        return nticeMapping.findAllOrderBy();
    }
    public List<Notice> findByPager(String startDate,  String endDate , String noticeTitle,int page,int pageSize) throws Exception{
        PageHelper.startPage(page, pageSize);
        return    nticeMapping.findByPager(startDate,endDate,noticeTitle);
    }
    @Override
    public Integer insert(Notice notice)throws Exception{
        return  nticeMapping.addNotice(notice);
    }
    public Integer updateById(Notice notice)throws Exception{
        return  nticeMapping.updateById(notice);
    }

    public Integer deleteById(List<Long> ids)throws Exception{
        return  nticeMapping.deleteById(ids);

    }
    public Notice findById(Long id)throws Exception{
        return nticeMapping.findById(id);
    }
}
