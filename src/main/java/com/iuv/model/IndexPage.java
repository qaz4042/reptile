package com.iuv.model;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

/**
 * 只为获得 每个品名url
 * 暂时无用
 */
//@Gecco(matchUrl = "http://www.bst2000.com/Home/Index/jewelry/menu/style/", pipelines = {"consolepipeline", "indexPagePipeline"})
public class IndexPage implements HtmlBean {

    @Request
    private HttpRequest request;

    @HtmlField(cssPath = "#form1 > div")
    private List<BigType> bigTypeList;
}
