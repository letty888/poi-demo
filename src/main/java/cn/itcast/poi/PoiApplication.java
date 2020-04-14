package cn.itcast.poi;

import cn.itcast.poi.handler.SheetHandler;
import cn.itcast.poi.service.impl.TestServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 19:24
 */
@SpringBootApplication
@EntityScan(basePackages = "cn.itcast.poi.entity")
public class PoiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoiApplication.class, args);
    }

}
