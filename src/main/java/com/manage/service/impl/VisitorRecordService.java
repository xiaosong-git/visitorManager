package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.VisitorRecordMapping;
import com.manage.model.VisitorRecord;
import com.manage.model.VisitorRecordExcelModel;
import com.manage.service.IVisitorRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorRecordService implements IVisitorRecordService {

    @Autowired
    private VisitorRecordMapping visitorRecordMapping;

    @Override
    public List<VisitorRecord> findByPage(
                                          String orgCode,
                                          String startDate,
                                          String endDate,
                                          String companyName,
                                          String visitorName,
                                          int pageNo, int pageSize)throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return visitorRecordMapping.findByPage(orgCode,startDate, endDate, companyName,visitorName);
    }

    @Override
    public List<VisitorRecord> findByPage2(String orgCode, String startDate, String endDate, String companyName, String visitorName) throws Exception {
        return visitorRecordMapping.findByPage(orgCode,startDate, endDate, companyName,visitorName);
    }

}
