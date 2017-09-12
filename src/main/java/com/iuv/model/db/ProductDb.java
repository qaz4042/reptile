package com.iuv.model.db;

import com.zyll.baseweb.util.java.BaseValue;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductDb extends BaseValue {

    //从页面信息中的idHref,中抽取id  30442
    @Id
    private Integer id;

    //NV045
    private String name;

    //一般与Id保持一致
    private String imageName;

    //款式大类 class 空托
    private String clazz;
    //款式品名 女戒
    private String kind;
    //款式系列 BST
    private String serial;

    //Au750：重量
    private String weigh;
    //规格参数  0.950-1.050    [规格：0.950-1.050]
    private String spec;
    //联系电话：13606080186
    private String tel;
}
