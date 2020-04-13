package cn.itcast.poi.controller;

import cn.itcast.poi.entity.BasAera;
import cn.itcast.poi.service.BasAeraService;
import cn.itcast.poi.utils.ExcelExportWithoutTemplateUtil;
import cn.itcast.poi.utils.ExcelWithTemplateExportUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 19:32
 */
@RestController
@RequestMapping("/poi")
public class BasAeraController {

    private final BasAeraService basAeraService;

    public BasAeraController(BasAeraService basAeraService) {
        this.basAeraService = basAeraService;
    }

    /**
     * 基于XSSF+模板导出数据
     * @param response 响应对象
     * @throws Exception 异常
     */
    @GetMapping("/withTemplate")
    public void bigDataExport1(HttpServletResponse response) throws Exception {
        String fileName = "模板导出数据测试表.xlsx";
        List<BasAera> basAeraList = basAeraService.findAll();
        Resource resource = new ClassPathResource("excel-template/poiDemo.xlsx");
        InputStream is = new FileInputStream(resource.getFile());
        List<Object> objects1 = Arrays.asList( basAeraList.toArray() ) ;
        new ExcelWithTemplateExportUtil<>(BasAera.class, 2, 2).export(response, is,objects1 , fileName);
    }

    @GetMapping("/withoutTemplate")
    public void bigDataExport2(HttpServletResponse response) throws Exception {
        String fileName = "模板导出数据测试表.xlsx";
        //标题
        String[] titles =
          "编号,地区级别,地区名称,地区编号,上级地区编号,级别名称".split(",");
        List<BasAera> basAeraList = basAeraService.findAll();
        List<Object> objects1 = Arrays.asList( basAeraList.toArray() ) ;
        new ExcelExportWithoutTemplateUtil<>(BasAera.class,2).export(response,objects1, titles,fileName);
    }
}
