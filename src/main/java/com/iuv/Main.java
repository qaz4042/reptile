package com.iuv;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.iuv.handler.OnePagePipeline;
import com.iuv.model.db.ProductDb;
import com.iuv.service.ProductService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {


    /**
     * gecco爬虫最简短的demo
     * <p>
     * 猜测核心流程
     * 1.main方法,GeccoEngine.run()启动gecco
     * 2.gecco加载spring[默认applicationContext.xml]
     * 3.gecco解析classpath这个包下面所有@Gecco注解  [GeccoEngine.create().classpath("com.iuv")]
     * 比如:
     *
     * @ Gecco(matchUrl = "http://www.meizitu.com/", pipelines = {"consolePipeline", "saveCategoryPipeline"})
     * public class IndexPage{
     * ...
     * }
     * 解析含义:
     * ①匹配这个url  http://www.meizitu.com/,    把 该网页信息,填充到,new IndexPage()这个对象中.[通过注解去分析网页]
     * ②每个IndexPage对象,都调用consolePipeline.process(indexPage)和saveCategoryPipeline.process(indexPage)方法处理一下.[转化计算入库上传保存....]
     * <p>
     * 4.process中还可以读取url地址.并塞到url队列中,递归,123步骤,来处理数据.
     * <p>
     * <p>
     * 一个大bug:
     * 变量名一定要 开头两个或者以上的小写字母,否则无法解析
     */
    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();

        //下载图片才需要
        //preDoImgFold();

        doReptile();

        context.close();

        System.out.println("分析结束");
    }


    private static void preDoImgFold() {

        File fileFolder = new File(OnePagePipeline.ImgFolder);

        if (fileFolder.exists()) {
            Boolean flag = fileFolder.renameTo(new File("D:/pic_" + System.currentTimeMillis() + "/"));
            System.out.println("renameTo flag=" + flag);
        } else {
            Boolean flag = fileFolder.mkdirs();
            System.out.println("mkdirs flag=" + flag);
        }
    }

    private static void testService(ClassPathXmlApplicationContext context) {
        ProductDb productDb = new ProductDb();
        productDb.setId(1);
        productDb.setName("12");
        ProductService service = context.getBean(ProductService.class);
        service.save(productDb);
    }

    private static void doReptile() {
        String url = "http://www.bst2000.com/Home/Index/jewelry/menu/style/";

        HttpGetRequest start = new HttpGetRequest(url);

        Map<String, String> map = getLoginCookies();
        for (String key : map.keySet()) {
            String value = map.get(key);
            start.addCookie(key, value);
            //System.out.println("addCookie|key" + key + ",value=" + value);
        }

        start.setCharset("GBK");

        GeccoEngine.create()
                .classpath("com.iuv")
                .start(start)
                .run();
    }

    private static Map<String, String> getLoginCookies() {

        Map<String, String> cookies = new HashMap<>();

        String userName = "huitongjin";
        String userNamePostKey = "username";
        String password = "13606080186";
        String passwordPostKey = "password";
        String url = "http://www.bst2000.com/Home/Public/checkLogin";

        //进入登录界面
        Connection conn = Jsoup.connect("http://www.bst2000.com/Public/login.html");
        conn.header("Host", "jwcnew.nefu.edu.cn");
        conn.header("Referer", "http://jwcnew.nefu.edu.cn");
        conn.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)");
        Connection.Response response;
        Map<String, String> cookies2 = new HashMap<>();
        try {
            response = conn.ignoreContentType(true).method(Connection.Method.GET).execute();

            cookies2 = response.cookies();

            Set<String> keys2 = cookies2.keySet();
            for (String key : keys2) {
                //System.out.println("cookies|" + key + ":" + cookies2.get(key));
            }
            cookies.putAll(cookies2);

        } catch (IOException e) {
            e.printStackTrace();
        }


        //登录
        Map<String, String> map = new HashMap<>();
        map.put(userNamePostKey, userName);
        map.put(passwordPostKey, password);

        Connection conn2 = Jsoup.connect(url);
        conn.header("Host", "jwcnew.nefu.edu.cn");
        conn.header("Referer", "http://jwcnew.nefu.edu.cn");
        conn.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)");
        Connection.Response response2;
        try {
            response2 = conn2.ignoreContentType(true).method(Connection.Method.POST).data(map).cookies(cookies2).execute();
            cookies.putAll(response2.cookies());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cookies;
    }
}
