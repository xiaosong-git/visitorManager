package com.manage.service;

import com.manage.model.Notice;
import com.manage.utils.result.Result;

import java.util.List;

/**
 * Created by Administrator on 2018/6/9.
 */
public interface INoticeService {

    /**
     * 获取所有公告
     * @return
     * @throws Exception
     */
    List<Notice> findAllList() throws Exception;

    /**
     * 获取最新公告
     * @return
     * @throws Exception
     */
    Long findMaxId() throws Exception;

    public List<Notice> findByIdMixAndMin(Long orgNoticeMaxId) throws Exception;

    public List<Notice> findAllOrderBy() throws Exception;

    public List<Notice> findByPager(String startDate, String endDate, String noticeTitle, int page, int pageSize) throws Exception;

    public Integer insert(Notice Notice)throws Exception;

    public Integer updateById(Notice Notice)throws Exception;

    public Integer deleteById(List<Long> ids)throws Exception;

    public Notice findById(Long id)throws Exception;
}
