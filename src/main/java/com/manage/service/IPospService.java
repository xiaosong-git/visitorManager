package com.manage.service;

import com.manage.model.Posp;

import java.util.List;

public interface IPospService {

    /**
     * 分页查询上位机
     * @param relation_no
     * @param orgName
     * @param pospName
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Posp> findByPage(String relation_no,String orgName, String pospName, int pageNo, int pageSize)throws Exception;

    /**
     * 插入数据
     * @param posp
     * @return
     * @throws Exception
     */
    public Integer add(Posp posp)throws Exception;

    /**
     * 修改数据
     * @param posp
     * @return
     * @throws Exception
     */
    public Integer update(Posp posp)throws Exception;

    /**
     * 删除数据
     * @param ids
     * @return
     * @throws Exception
     */
    public Integer del(List<Long> ids)throws Exception;

    /**
     * 修改状态
     * @param posp
     * @return
     * @throws Exception
     */
    public Integer updateStatus(Posp posp)throws Exception;

    /**
     * 根据单个参数名查找
     * @param paramName
     * @param paramText
     * @return
     */
    public Posp findByParam(String paramName, String paramText) throws Exception;
}
