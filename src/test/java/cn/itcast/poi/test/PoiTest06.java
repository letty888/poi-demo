package cn.itcast.poi.test;

import cn.itcast.poi.handler.SheetHandler;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 使用事件模型解析百万数据excel报表
 */
public class PoiTest06 {

    public static void main(String[] args) throws Exception {
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
            stream = sheetIterator.next(); //每一个sheet的流数据
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
}
