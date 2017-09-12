package com.iuv.handler;

import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;

/**
 * 添加打印异常
 */
public abstract class BasePipeline<T extends SpiderBean> implements Pipeline<T> {

    @Override
    public void process(T spiderBean) {
        try {
            doProcess(spiderBean);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    abstract void doProcess(T spiderBean);
}
