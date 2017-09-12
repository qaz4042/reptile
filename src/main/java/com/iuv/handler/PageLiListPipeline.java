package com.iuv.handler;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.iuv.model.PageLi;
import com.iuv.model.PageLiList;
import com.iuv.model.SubType;
import com.iuv.service.SubTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@PipelineName("pageLiListPipeline")
@Service
public class PageLiListPipeline extends BasePipeline<PageLiList> {


    /**
     * 构造url.  获取每个subType小类型,有多少页
     *
     * @param bean
     */
    @Override
    public void doProcess(PageLiList bean) {

        //System.out.println("PageLiList bean=" + bean);

        Integer maxNum = 0;

        List<PageLi> pageLiList = bean.getPageLiList();
        for (int i = pageLiList.size() - 1; i >= 0; i--) {
            String numStr = pageLiList.get(i).getNum();
            if (numStr.matches("-?\\d+")) {
                maxNum = new Integer(numStr);
                break;
            }
        }

        for (int pageNum = 1; pageNum <= maxNum; pageNum++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //http://www.bst2000.com/Index/jewelry/menu/style/class/236/page/2.html
            String nextUrl = "http://www.bst2000.com/Index/jewelry/menu/style/page/" + pageNum + ".html";

            HttpRequest request = bean.getRequest();
            SchedulerContext.into(request.subRequest(nextUrl));
            //break;测试临时停止
        }
        System.out.println("onePageLoadOver|maxNum=" + maxNum);
    }

    private void forAllSubType(PageLiList bean, String bigTypeCode, Integer subTypeId, Integer maxNum) {



        SubType type = SubTypeService.getSubType(bigTypeCode, subTypeId);
        type.setMaxNum(maxNum);

        for (int pageNum = 1; pageNum <= maxNum; pageNum++) {
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //http://www.bst2000.com/Index/jewelry/menu/style/class/236/page/2.html
            String nextUrl = "http://www.bst2000.com/Index/jewelry/menu/style/" + bigTypeCode + "/" + subTypeId + "/page/" + pageNum + ".html";
            HttpRequest request = bean.getRequest();
            SchedulerContext.into(request.subRequest(nextUrl));

            //to do  测试临时停止
            //break;
        }
    }
}