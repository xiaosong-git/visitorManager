package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.TerminalMapping;
import com.manage.model.Terminal;
import com.manage.service.ITerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService implements ITerminalService {

    @Autowired
    private TerminalMapping terminalMapping;

    @Override
    public List<Terminal> findByPage(String relation_no,String orgName, String terminalNo, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return terminalMapping.findByPage(relation_no,orgName,terminalNo);
    }

    @Override
    public Integer add(Terminal terminal) throws Exception {
        return terminalMapping.add(terminal);
    }

    @Override
    public Integer update(Terminal terminal) throws Exception {
        return terminalMapping.update(terminal);
    }

    @Override
    public Integer del(List<Long> ids) throws Exception {
        return terminalMapping.del(ids);
    }

    @Override
    public Integer updateStatus(Terminal terminal) throws Exception {
        return terminalMapping.updateStatus(terminal);
    }
}
