package cn.itcast.poi.entity;

import cn.itcast.poi.annotation.ExcelAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhang
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "test_entity")
public class TestEntity implements Serializable {

    private static final long serialVersionUID = -4990810027542971548L;

    @Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @ExcelAttribute(sort = 0)
    private String id;
    @ExcelAttribute(sort = 1)
    private String breast;
    @ExcelAttribute(sort = 2)
    private String adipocytes;
    @ExcelAttribute(sort = 3)
    private String negative;
    @ExcelAttribute(sort = 4)
    private String staining;
    @ExcelAttribute(sort = 5)
    private String supportive;
}
