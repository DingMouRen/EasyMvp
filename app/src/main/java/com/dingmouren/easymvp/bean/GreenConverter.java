package com.dingmouren.easymvp.bean;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dingmouren on 2017/1/7.
 */

public class GreenConverter implements PropertyConverter<List,String>{
    @Override
    public List convertToEntityProperty(String databaseValue) {
        if (null == databaseValue){
            return null;
        }else {
            List<String> list = Arrays.asList(databaseValue.split(","));
            return list;
        }
    }

    @Override
    public String convertToDatabaseValue(List entityProperty) {
        if (null == entityProperty){
            return null;
        }else {
            StringBuilder sb = new StringBuilder();
            for(String link : (List<String>)entityProperty){
                sb.append(link);
                sb.append(",");
            }
            return sb.toString();
        }
    }
}
