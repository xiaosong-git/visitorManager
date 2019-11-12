package com.manage.dao;

import com.manage.model.Notice;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/6/9.
 */
@Mapper
public interface NoticeMapping  extends MyMapper<Notice> {
    /**
     * @return
     */
    Long findMaxId();

    List<Notice> findByIdMixAndMin(Long orgNoticeMaxId);

    List<Notice> findAllOrderBy();

    List<Notice> findByPager(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("noticeTitle") String noticeTitle) throws Exception;

    Integer addNotice(@Param("notice") Notice notice);

    Integer updateById(@Param("sysNotice") Notice notice);

    Integer deleteById(@Param("ids") List<Long> ids);

    Notice findById(@Param("id") Long id)throws Exception;
}
