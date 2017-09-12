package com.iuv.model;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageLi implements HtmlBean {

    @HtmlField(cssPath = "li > a")
    @Text
    private String num;
}
