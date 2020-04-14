package cn.itcast.poi.dao;

import cn.itcast.poi.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 19:35
 */
public interface TestDao extends JpaRepository<TestEntity, Integer>, JpaSpecificationExecutor<TestEntity> {
}
