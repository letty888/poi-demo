package cn.itcast.poi.controller;

import cn.itcast.poi.entity.BasAera;
import cn.itcast.poi.entity.TestEntity;
import cn.itcast.poi.service.BasAeraService;
import cn.itcast.poi.service.TestService;
import cn.itcast.poi.utils.export.ExcelExportWithoutTemplateUtil;
import cn.itcast.poi.utils.export.ExcelWithTemplateExportUtil;
import cn.itcast.poi.utils.importData.ExcelImportBigDataUtils;
import cn.itcast.poi.utils.importData.ExcelImportUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final TestService testService;

    public BasAeraController(BasAeraService basAeraService, TestService testService) {
        this.basAeraService = basAeraService;
        this.testService = testService;
    }

    /**
     * 基于XSSF+模板导出数据
     *
     * @param response 响应对象
     * @throws Exception 异常
     */
    @GetMapping("/export/withTemplate")
    public void withTemplateExport(HttpServletResponse response) throws Exception {
        String fileName = "模板导出数据测试表.xlsx";
        List<BasAera> basAeraList = basAeraService.findAll();
        Resource resource = new ClassPathResource("excel-template/poiDemo.xlsx");
        InputStream is = new FileInputStream(resource.getFile());
        List<Object> objects1 = Arrays.asList(basAeraList.toArray());
        new ExcelWithTemplateExportUtil<>(BasAera.class, 2, 2).export(response, is, objects1, fileName);
    }

    /**
     * 基于SXSSF进行百万条级别的数据导出
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/export/withoutTemplate")
    public void withoutTemplateExport(HttpServletResponse response) throws Exception {
        String fileName = "模板导出数据测试表.xlsx";
        //标题
        String[] titles =
                "编号,地区级别,地区名称,地区编号,上级地区编号,级别名称".split(",");
        List<BasAera> basAeraList = basAeraService.findAll();
        List<Object> objects1 = Arrays.asList(basAeraList.toArray());
        new ExcelExportWithoutTemplateUtil<>(BasAera.class, 2).export(response, objects1, titles, fileName);
    }

    /**
     * 使用报表导入工具类解析普通级别的数据excel报表
     *
     * @param file 文件
     */
    @PostMapping("/import/importGeneralData")
    public void importGeneralData(@RequestParam(name = "file") MultipartFile file) throws Exception {
        if (file != null) {
            List<TestEntity> list = new ExcelImportUtil(TestEntity.class).readExcel(file.getInputStream(), 1, 0);
            testService.saveAll(list);
        }

    }

    /**
     * 使用事件模型解析百万数据excel报表
     * (这个方法目前没有测试通,原因:  事件驱动器中注入的service层一直为null)
     *
     * @param file 文件
     */
    @PostMapping("/import/bigData")
    public void importBigData(@RequestParam(name = "file") MultipartFile file) throws Exception {
        if (file != null) {
            ExcelImportBigDataUtils.importBigData(file);
        }

    }
}
