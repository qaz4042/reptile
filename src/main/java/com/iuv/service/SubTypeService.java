package com.iuv.service;

import com.iuv.model.PageLiList;
import com.iuv.model.SubType;
import com.zyll.baseweb.util.java.Validate;

import java.util.HashMap;
import java.util.Map;

public class SubTypeService {
    //Map<bigTypeCode,Map<subbigTypeCode,SubType>
    private static Map<String, Map<Integer, SubType>> pageMap = new HashMap<>();

    public static void putSubType(String bigTypeCode, Integer subTypeCode, SubType o) {
        if (null == bigTypeCode || null == subTypeCode || null == o) {
            return;
        }
        Map<Integer, SubType> subYypeIdObjMap = pageMap.get(bigTypeCode);
        if (Validate.isEmpty(subYypeIdObjMap)) {
            subYypeIdObjMap = new HashMap<>();
            pageMap.put(bigTypeCode, subYypeIdObjMap);
        }
        subYypeIdObjMap.put(subTypeCode, o);
    }

    public static SubType getSubType(String bigTypeCode, Integer subTypeId) {

        if (null == bigTypeCode || null == subTypeId) {
            return null;
        }

        Map<Integer, SubType> subYypeIdObjMap = pageMap.get(bigTypeCode);
        if (null == subYypeIdObjMap) {
            return null;
        }
        return subYypeIdObjMap.get(subTypeId);
    }
}
