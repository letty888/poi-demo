package cn.itcast.poi.handler;

import cn.itcast.poi.entity.TestEntity;
import cn.itcast.poi.service.impl.TestServiceImpl;
import cn.itcast.poi.utils.spring.SpringUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的事件处理器
 * 处理每一行数据读取
 * 实现接口
 *
 * @author zhang
 */

@Component
public class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    @Autowired
    private TestServiceImpl testServiceImpl;

    private TestEntity entity;
    private List<TestEntity> testEntityList = new ArrayList<>();
    public static SheetHandler  sheetHandler;


    public SheetHandler() {
    }

    /**
     * 通过@PostConstruct实现初始化bean之前进行的操作
     */
    @PostConstruct
    public void init() {
        sheetHandler = this;
        sheetHandler.testServiceImpl = this.testServiceImpl;
        // 初使化时将已静态化的testService实例化
    }



    /**
     * 当开始解析某一行的时候触发
     * i:行索引
     */
    @Override
    public void startRow(int i) {
        //实例化对象
        if (i > 0) {
            entity = new TestEntity();
        }
    }

    /**
     * 当结束解析某一行的时候触发
     * i:行索引
     */
    @Override
    public void endRow(int i) {
        System.out.println(entity);
        //使用对象进行业务操作
      //  testEntityList.add(entity);
        //不清楚什么原因,这里service层注入一直为null,尝试了网上各种方法依然不行
      /*  if (testEntityList.size() > 100000) {
            testServiceImpl.saveAll(testEntityList);
            testEntityList.clear();
        }*/
    }

    /**
     * 对行中的每一个表格进行处理
     * cellReference: 单元格名称
     * 注意:单元格名称指的是:  excel表格最上面的 A B C D E F G...,实体类有几个属性,这里就有多少列
     * value：数据
     * xssfComment：批注
     */
    @Override
    public void cell(String cellReference, String value, XSSFComment xssfComment) {
        //给对象属性赋值
        if (entity != null) {
            String pix = cellReference.substring(0, 1);
            switch (pix) {
                case "A":
                    entity.setId(value);
                    break;
                case "B":
                    entity.setBreast(value);
                    break;
                case "C":
                    entity.setAdipocytes(value);
                    break;
                case "D":
                    entity.setNegative(value);
                    break;
                case "E":
                    entity.setStaining(value);
                    break;
                case "F":
                    entity.setSupportive(value);
                    break;
                default:
                    break;
            }
        }
    }
}
