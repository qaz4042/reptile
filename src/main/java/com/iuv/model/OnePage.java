package com.iuv.model;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Gecco(matchUrl = "http://www.bst2000.com/Index/jewelry/menu/style/page/{pageNum}.html", pipelines = {"consolePipeline", "onePagePipeline"})
public class OnePage implements HtmlBean {

    @RequestParameter
    private Integer pageNum;

    @Request
    private HttpRequest request;

    @HtmlField(cssPath = ".box-style-body-main > .row > .col-sm-4")
    private List<ProductOri> pageLiList;
}
