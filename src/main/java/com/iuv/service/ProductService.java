package com.iuv.service;

import com.iuv.dao.ProductDao;
import com.iuv.model.db.ProductDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

    @Autowired
    ProductDao productDao;

    public void save(ProductDb productDb) {
        productDao.save(productDb);
    }
}
