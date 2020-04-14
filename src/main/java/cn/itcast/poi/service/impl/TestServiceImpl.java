package cn.itcast.poi.service.impl;

import cn.itcast.poi.dao.TestDao;
import cn.itcast.poi.entity.TestEntity;
import cn.itcast.poi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 23:35
 */
@Service
public class TestServiceImpl implements TestService {

    private final TestDao testDao;


    public TestServiceImpl(TestDao testDao) {
        this.testDao = testDao;
    }


    @Override
    public void saveAll(List<TestEntity> testEntity) {
        testDao.saveAll(testEntity);
    }
}
