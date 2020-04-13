package cn.itcast.poi.entity;

import cn.itcast.poi.annotation.ExcelAttribute;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/13 19:11
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "bas_area")
public class BasAera implements Serializable {
    private static final long serialVersionUID = -4990810027542971547L;
    @Id
    @ExcelAttribute(sort = 0)
    private Integer id;
    @ExcelAttribute(sort = 1)
    private Integer areaLevel;
    @ExcelAttribute(sort = 3)
    private Integer areaNo;
    @ExcelAttribute(sort = 4)
    private Integer parentNo;
    @ExcelAttribute(sort = 2)
    private String areaName;
    @ExcelAttribute(sort = 5)
    private String typeName;
}
