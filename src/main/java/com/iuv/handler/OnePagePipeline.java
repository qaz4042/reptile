package com.iuv.handler;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.iuv.model.OnePage;
import com.iuv.model.Pdiv;
import com.iuv.model.ProductOri;
import com.iuv.model.db.ProductDb;
import com.iuv.service.ProductService;
import com.iuv.util.SpringContextUtil;
import com.zyll.baseweb.util.java.Validate;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@SuppressWarnings("Duplicates")
@PipelineName("onePagePipeline")
@Component
public class OnePagePipeline extends BasePipeline<OnePage> {

    public static final String ImgFolder = "D:/pic/";


    private final static Logger Log = LoggerFactory.getLogger(OnePagePipeline.class);

    ProductService productService;

    @Override
    public void doProcess(OnePage bean) {

        // List<ProductDb> list = new ArrayList<>();
        //原始的商品信息
        List<ProductOri> pageLiList = bean.getPageLiList();

        if (Validate.isNotEmpty(pageLiList)) {
            for (ProductOri product : pageLiList) {
                ProductDb productDb = buildProductInfo(product);

                save(productDb);

                //list.add(productDb);
            }
        }
        //System.out.println(" List<ProductDb>=" + list);
    }

    private void save(ProductDb productDb) {
        System.out.println("productDb=" + productDb);
        if (null == productService) {
            productService = SpringContextUtil.getBean(ProductService.class);
        }
        productService.save(productDb);
    }

    private CloseableHttpClient httpClient;

    {
        RequestConfig clientConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
        PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager();
        syncConnectionManager.setMaxTotal(1000);
        syncConnectionManager.setDefaultMaxPerRoute(50);
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(clientConfig).setConnectionManager(syncConnectionManager).build();
    }

    private String downLoadImg(String url, Integer imageName) {
        HttpRequestBase request = new HttpGet(url);
        try {
            HttpClientContext context = HttpClientContext.create();
            org.apache.http.HttpResponse response = httpClient.execute(request, context);
            String newImageName = ImgFolder + imageName + ".jpg";
            File uploadFile = new File(newImageName);
            if (uploadFile.exists()) {
                newImageName = ImgFolder + imageName + "_" + System.currentTimeMillis() + ".jpg";
                uploadFile = new File(newImageName);
            }
            FileUtils.copyInputStreamToFile(response.getEntity().getContent(), uploadFile);

            return newImageName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            request.releaseConnection();
        }
    }


    private ProductDb buildProductInfo(ProductOri product) {

        ProductDb productDb = new ProductDb();

        String idHref = product.getIdHref();
        Integer id = new Integer(idHref.substring(idHref.lastIndexOf("/") + 1));
        productDb.setId(id);

        productDb.setName(product.getName());

        //todo 临时注释掉
        String newImgName = downLoadImg(product.getImageUrl(), id);
        productDb.setImageName(newImgName);
        setValuesFromP(product.getPdivList(), productDb);

        return productDb;
    }

    private void setValuesFromP(List<Pdiv> pList, ProductDb productDb) {
        Log.info("setValuesFromP|pList={}", pList);
        if (Validate.isEmpty(pList)) {
            Log.warn("pList.isEmpty|productDb=", productDb);
            return;
        }

        //成品库存- 女戒	- BST
        String[] types = getContent(pList.get(0)).split("-");
        if (types.length == 3) {
            productDb.setClazz(types[0]);
            productDb.setKind(types[1]);
            productDb.setSerial(types[2]);
        }


        for (Pdiv p : pList) {
            String content = getContent(p);
            //所有的都是数字
            if (content.contains("联系电话：")) {
                String tel = content.replace("联系电话：", "");
                productDb.setTel(tel);
            }
            //Au750：6.200g 规格：0.010-0.010
            if (content.contains("规格：")) {
                String[] strs = content.split("规格：");
                if (strs.length > 0) {
                    String weigh = strs[0].replace("Au750：", "");
                    productDb.setWeigh(weigh);
                }
                if (strs.length > 1) {
                    String spec = strs[1];
                    productDb.setSpec(spec);
                }
            }
            /*if (content.contains("Au750：")) {
                String weigh = content.replace("Au750：", "");
                productDb.setWeigh(weigh);
            }*/
        }
    }

    private String getContent(Pdiv p) {
        return p.getContent().replaceAll("\\s*", "").replace("<br>", "");
    }
}
