package cn.itcast.poi.service.impl;

import cn.itcast.poi.dao.BasAeraDao;
import cn.itcast.poi.entity.BasAera;
import cn.itcast.poi.service.BasAeraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 19:34
 */
@Service
public class BasAeraServiceImpl implements BasAeraService {

    private final BasAeraDao basAeraDao;

    public BasAeraServiceImpl(BasAeraDao basAeraDao) {
        this.basAeraDao = basAeraDao;
    }

    @Override
    public List<BasAera> findAll() {
        return basAeraDao.findAll();
    }
}
