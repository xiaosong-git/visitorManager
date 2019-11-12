package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.PospMapping;
import com.manage.model.Posp;
import com.manage.service.IPospService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PospService implements IPospService {

    @Autowired
    private PospMapping pospMapping;

    @Override
    public List<Posp> findByPage(String relation_no, String orgName, String pospName, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return pospMapping.findByPage(relation_no,orgName,pospName);
    }

    @Override
    public Integer add(Posp posp) throws Exception {
        return pospMapping.add(posp);
    }

    @Override
    public Integer update(Posp posp) throws Exception {
        return pospMapping.update(posp);
    }

    @Override
    public Integer del(List<Long> ids) throws Exception {
        return pospMapping.del(ids);
    }

    @Override
    public Integer updateStatus(Posp posp) throws Exception {
        return pospMapping.updateStatus(posp);
    }

    @Override
    public Posp findByParam(String paramName, String paramText) throws Exception {
        return pospMapping.findByParam(paramName,paramText);
    }
}
