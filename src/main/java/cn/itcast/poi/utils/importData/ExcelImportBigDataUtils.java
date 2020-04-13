package cn.itcast.poi.utils.importData;

import cn.itcast.poi.handler.SheetHandler;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 注意:这个工具类需要和 自定义的事件驱动器一起使用
 *
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 23:25
 */
public class ExcelImportBigDataUtils {
    public static void importBigData(MultipartFile file) throws Exception {
        //1.根据excel报表获取OPCPackage
        InputStream inputStream = file.getInputStream();
        OPCPackage opcPackage = OPCPackage.open(inputStream);
        //2.创建XSSFReader
        XSSFReader reader = new XSSFReader(opcPackage);
        //3.获取SharedStringTable对象
        SharedStringsTable table = reader.getSharedStringsTable();
        //4.获取styleTable对象
        StylesTable stylesTable = reader.getStylesTable();
        //5.创建Sax的xmlReader对象
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        //6.注册事件处理器(SheetHandler 是自己自定义的时间驱动类)
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
}


