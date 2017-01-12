package com.dingmouren.easymvp.util;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dingmouren on 2017/1/7.
 * greendao保存数据时，出现的List<String> 这种类型的转换，使用之后就可以保存到greendao的数据库了
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
