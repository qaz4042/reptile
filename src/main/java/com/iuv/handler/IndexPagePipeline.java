package com.iuv.handler;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.iuv.model.BigType;
import com.iuv.model.IndexPage;
import com.iuv.model.SubType;
import com.iuv.service.SubTypeService;
import com.zyll.baseweb.util.java.Validate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PipelineName("indexPagePipeline")
@Service
public class IndexPagePipeline extends BasePipeline<IndexPage> {

    private static Map<String, String> typetypeNameCodeMap = new HashMap<>();

    static {
        typetypeNameCodeMap.put("款式大类", "class");
        typetypeNameCodeMap.put("款式品名", "kind");
        typetypeNameCodeMap.put("款式系列", "serial");
    }

    @Override
    void doProcess(IndexPage spiderBean) {
        forAllBigType(spiderBean);
    }

    private void forAllBigType(IndexPage bean) {
        //System.out.println("IndexPage=  " + bean);

        List<BigType> subTypeList = bean.getBigTypeList();

        if (Validate.isNotEmpty(subTypeList)) {

            //遍历里一个大类就可以获得全部商品
            BigType bigType = subTypeList.get(0);

            String tempName = bigType.getName();
            //款式大类 款式品名 款式系列
            String typeName = tempName.substring(0, tempName.indexOf("："));
            String bigTypeCode = typetypeNameCodeMap.get(typeName);

            List<SubType> categoryTypes = bigType.getSubTypeList();

            if (Validate.isEmpty(categoryTypes)) {
                return;
            }

            for (SubType subType : categoryTypes) {
                Integer subTypeId = subType.getId();
                String name = subType.getName();

                //1.入库配置表
                save(subTypeId, name);

                //保存到缓存
                SubTypeService.putSubType(bigTypeCode, subTypeId, subType);

                /*
                //0表示 所有小类,不计算所有小类的页数
                if (0 == subTypeId) {
                    continue;
                }

                http://www.bst2000.com/Home/Index/jewelry/menu/style/class/221/kind/240/     serial/246  class/223
                String nextUrl = "http://www.bst2000.com/Home/Index/jewelry/menu/style/" + bigTypeCode + "/" + subTypeId;
                HttpRequest request = bean.getRequest();
                SchedulerContext.into(request.subRequest(nextUrl));

                System.out.println("nextUrl=" + nextUrl);
                */
            }
            //todo  测试临时停止
        }
    }

    private void save(Integer id, String name) {
        //todo
    }


}
