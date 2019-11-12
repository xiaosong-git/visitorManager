package com.manage.service;

import com.manage.model.Key;

/**
 * Created by L on 2017/8/15.
 */
public interface IKeyService {

    public Key findByStatus(String status) throws Exception;

    public String findByStatusString(String status) throws Exception;

}
