package com.iuv.model;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubType implements HtmlBean {

    @Text
    @HtmlField(cssPath = "span")
    private String name;

    @Attr("tag")
    @HtmlField(cssPath = "span")
    private Integer id;

    private Integer maxNum;
}