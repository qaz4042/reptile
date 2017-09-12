package com.iuv.model;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductOri implements HtmlBean {

    //<a href="/Home/Index/show/menu/style/Stone/0/id/70510">
    @Href
    @HtmlField(cssPath = ".thumbnail > a")
    private String idHref;

    @HtmlField(cssPath = ".thumbnail > .caption > p")
    @Text
    private List<Pdiv> pdivList;

    @Text
    @HtmlField(cssPath = ".thumbnail > .caption > h4")
    private String name;


    @Image
    @HtmlField(cssPath = ".thumbnail > a > img")
    private String imageUrl;
}
