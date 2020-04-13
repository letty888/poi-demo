package cn.itcast.poi.dao;

import cn.itcast.poi.entity.BasAera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 19:35
 */
public interface BasAeraDao extends JpaRepository<BasAera, Integer>, JpaSpecificationExecutor<BasAera> {
}
