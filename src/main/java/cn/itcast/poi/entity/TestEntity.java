package cn.itcast.poi.entity;

import cn.itcast.poi.annotation.ExcelAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author zhang
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "test_entity")
public class TestEntity {

    @Id
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBreast() {
        return breast;
    }

    public void setBreast(String breast) {
        this.breast = breast;
    }

    public String getAdipocytes() {
        return adipocytes;
    }

    public void setAdipocytes(String adipocytes) {
        this.adipocytes = adipocytes;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public String getStaining() {
        return staining;
    }

    public void setStaining(String staining) {
        this.staining = staining;
    }

    public String getSupportive() {
        return supportive;
    }

    public void setSupportive(String supportive) {
        this.supportive = supportive;
    }

    @Override
    public String toString() {
        return "PoiEntity{" +
                "id='" + id + '\'' +
                ", breast='" + breast + '\'' +
                ", adipocytes='" + adipocytes + '\'' +
                ", negative='" + negative + '\'' +
                ", staining='" + staining + '\'' +
                ", supportive='" + supportive + '\'' +
                '}';
    }
}
