package cn.itcast.poi;

import cn.itcast.poi.handler.SheetHandler;
import cn.itcast.poi.utils.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 19:24
 */
@SpringBootApplication
@EntityScan(basePackages = "cn.itcast.poi.entity")
@Import(SpringUtil.class)
public class PoiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoiApplication.class, args);
    }

}
