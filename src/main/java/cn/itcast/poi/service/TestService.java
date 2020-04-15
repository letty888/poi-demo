package cn.itcast.poi.service;

import cn.itcast.poi.entity.BasAera;
import cn.itcast.poi.entity.TestEntity;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 19:34
 */
public interface TestService {


    public void saveAll(List<TestEntity> testEntity);

    List<TestEntity> findAll();
}
