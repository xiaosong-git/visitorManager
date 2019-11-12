package com.manage.service;

import com.manage.model.Terminal;
import com.manage.model.VisitorRecord;
import com.manage.model.VisitorRecordExcelModel;

import java.util.List;

public interface IVisitorRecordService {

    /**
     * 分页查询访问记录
     * @param startDate
     * @param endDate
     * @param visitorName
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<VisitorRecord> findByPage(
                                          String orgCode,
                                          String startDate,
                                          String endDate,
                                          String companyName,
                                          String visitorName,
                                          int pageNo, int pageSize)throws Exception;

    List<VisitorRecord> findByPage2(String orgCode,
                                    String startDate,
                                    String endDate,
                                    String companyName,
                                    String visitorName) throws Exception;

}
