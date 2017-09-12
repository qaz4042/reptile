package com.iuv.model;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BigType implements HtmlBean {

    @HtmlField(cssPath = ".layer-screening_left")
    @Text
    private String name;

    @HtmlField(cssPath = ".col-sm-10 > span")
    private List<SubType> subTypeList;
}
