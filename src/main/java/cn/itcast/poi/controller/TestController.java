package cn.itcast.poi.controller;

import cn.itcast.poi.entity.TestEntity;
import cn.itcast.poi.handler.SheetHandler;
import cn.itcast.poi.service.TestService;
import cn.itcast.poi.utils.export.ExcelExportWithoutTemplateUtil;
import cn.itcast.poi.utils.importData.ExcelImportBigDataUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/14 19:18
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }


    /**
     * 测试
     *
     * @return String 操作结果反馈
     */
    @GetMapping("/saveAll")
    public String saveAll() {
        List<TestEntity> list = new ArrayList<>();
        TestEntity testEntity = new TestEntity();
        testEntity.setId("1");
        testEntity.setAdipocytes("555");
        list.add(testEntity);
        testService.saveAll(list);
        return "执行成功!";
    }

    /**
     * 读取本地文件形式导入大数据量的excel报表
     *
     * @throws Exception
     */
    @GetMapping
    public void importData() throws Exception {
        String path = "D:\\BaiduNetdiskDownload\\JAVA\\softstudy\\26-传统行业SaaS解决方案\\08-员工管理及POI\\02-POI报表的高级应用\\资源\\百万数据报表\\demo.xlsx";
        FileInputStream fis = new FileInputStream(path);
        //1.根据excel报表获取OPCPackage
        OPCPackage opcPackage = OPCPackage.open(fis);
        //2.创建XSSFReader
        XSSFReader reader = new XSSFReader(opcPackage);
        //3.获取SharedStringTable对象
        SharedStringsTable table = reader.getSharedStringsTable();
        //4.获取styleTable对象
        StylesTable stylesTable = reader.getStylesTable();
        //5.创建Sax的xmlReader对象
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        //6.注册事件处理器
        XSSFSheetXMLHandler xmlHandler = new XSSFSheetXMLHandler(stylesTable, table, new SheetHandler(), false);
        xmlReader.setContentHandler(xmlHandler);
        //7.拆分出每个sheet表单并且逐行读取
        XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) reader.getSheetsData();
        InputStream stream = null;
        while (sheetIterator.hasNext()) {
            //每一个sheet的流数据
            stream = sheetIterator.next();
            InputSource is = new InputSource(stream);
            xmlReader.parse(is);
        }
        if (stream != null) {
            stream.close();
        }
        if (opcPackage != null) {
            opcPackage.close();
        }
    }

    /**
     * 使用工具类导入百万条数据
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file != null) {
            ExcelImportBigDataUtils.importBigData(file);
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        List<TestEntity> entityList = testService.findAll();
        String[] titles = "序号,姓名,年龄,公司,公司地址,职位级别".split(",");
        List<Object> objects = Arrays.asList(entityList.toArray());
        new ExcelExportWithoutTemplateUtil(TestEntity.class,2).export(response, objects, titles,"百万数据导出测试表.xlsx");
    }
}

