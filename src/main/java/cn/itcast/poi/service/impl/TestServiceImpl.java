package cn.itcast.poi.service.impl;

import cn.itcast.poi.dao.TestDao;
import cn.itcast.poi.entity.TestEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 23:35
 */
@Service
public class TestServiceImpl{

    private final TestDao testDao;

    public TestServiceImpl(TestDao testDao) {
        this.testDao = testDao;
    }



    public void saveAll(List<TestEntity> testEntity) {
        testDao.saveAll(testEntity);
    }
}
