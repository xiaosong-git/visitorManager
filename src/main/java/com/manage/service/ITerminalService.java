package com.manage.service;

import com.manage.model.Terminal;

import java.util.List;

public interface ITerminalService {

    /**
     * 分页查询设备
     * @param orgName
     * @param terminalNo
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Terminal> findByPage(String relation_no,String orgName, String terminalNo, int pageNo, int pageSize)throws Exception;

    /**
     * 插入设备数据
     * @param terminal
     * @return
     * @throws Exception
     */
    public Integer add(Terminal terminal)throws Exception;

    /**
     * 修改设备数据
     * @param terminal
     * @return
     * @throws Exception
     */
    public Integer update(Terminal terminal)throws Exception;

    /**
     * 删除设备数据
     * @param ids
     * @return
     * @throws Exception
     */
    public Integer del(List<Long> ids)throws Exception;

    /**
     * 修改设备状态
     * @param terminal
     * @return
     * @throws Exception
     */
    public Integer updateStatus(Terminal terminal)throws Exception;
}
