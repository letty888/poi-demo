package cn.itcast.poi.utils;


import cn.itcast.poi.annotation.ExcelAttribute;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 导出Excel工具类
 * 基于模板打印的方式导出：
 */
@Getter
@Setter
public class ExcelExportWithoutTemplateUtil<T> {

    /**
     * 写入数据的起始行
     */
    private int rowIndex;

    /**
     * 对象的字节码
     */
    private Class clazz;

    /**
     * 对象中的所有属性
     */
    private Field[] fields;

    public ExcelExportWithoutTemplateUtil(Class clazz, int rowIndex) {
        this.rowIndex = rowIndex;
        this.clazz = clazz;
        fields = clazz.getDeclaredFields();
    }

    /**
     * 基于注解导出
     * 参数：
     * response：
     * InputStream:模板的输入流
     * objs：数据
     * titles:首行抬头们
     * fileName：生成的文件名
     */
    public void export(HttpServletResponse response, List<T> objs, String[] titles, String fileName) throws Exception {

        //1.根据模板创建工作簿
        //阈值，内存中对象的最大数量,超出此数量的对象将暂时存储到磁盘中
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        //构造sheet
        Sheet sheet = workbook.createSheet();

        //设置头部
        Row headRow = sheet.createRow(0);
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titles.length - 1));
        Cell headCell = headRow.createCell(0);
        String headContent = fileName.substring(0, fileName.length() - 5);
        headCell.setCellValue(headContent);
        CellStyle style = this.getStyle(workbook);
        //向单元格设置样式
        headCell.setCellStyle(style);

        //处理抬头
        Row titleRow = sheet.createRow(1);
        int titleIndex = 0;
        for (String title : titles) {
            Cell cell = titleRow.createCell(titleIndex++);
            cell.setCellValue(title);
            cell.setCellStyle(style);
        }

        //4.根据数据创建每一行和每一个单元格的数据2
        AtomicInteger datasAi = new AtomicInteger(rowIndex);
        for (T t : objs) {
            Row row = sheet.createRow(datasAi.getAndIncrement());
            for (int i = 0; i < titles.length; i++) {
                Cell cell = row.createCell(i);
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ExcelAttribute.class)) {
                        //暴力反射,即让我们在用反射时可以访问私有变量
                        field.setAccessible(true);
                        ExcelAttribute ea = field.getAnnotation(ExcelAttribute.class);
                        if (i == ea.sort()) {
                            if (field.get(t) != null) {
                                cell.setCellValue(field.get(t).toString());
                            }
                        }
                    }
                }
            }
        }
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("ISO8859-1")));
        response.setHeader("filename", fileName);
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     * 设置样式
     *
     * @param workbook SXSSF工作簿
     * @return CellStyle 样式
     */
    private CellStyle getStyle(SXSSFWorkbook workbook) {
        //样式处理
        //创建样式对象
        CellStyle style = workbook.createCellStyle();
        //上边框
        style.setBorderTop(BorderStyle.THIN);
        //下边框
        style.setBorderBottom(BorderStyle.THIN);
        //左边框
        style.setBorderLeft(BorderStyle.THIN);
        //右边框
        style.setBorderRight(BorderStyle.THIN);

        //创建字体对象
        Font font = workbook.createFont();
        //字体
        font.setFontName("华文行楷");
        //字号
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);

        //居中显示
        //水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

}
