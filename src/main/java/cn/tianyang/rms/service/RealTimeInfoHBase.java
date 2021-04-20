package cn.tianyang.rms.service;

import cn.tianyang.rms.dao.BaseResult;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RealTimeInfoHBase extends HbaseServer {
//    public BaseResult<Map<String, Object>> toResult(Map<String, Object> map) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        BaseResult<Map<String, Object>> baseResult = new BaseResult<>();
//        if (CollectionUtils.isEmpty(map)) {
//            baseResult.setResultEmpty();
//        }else{
//            baseResult = new RealTimeInfoAction().createBillResult(map);
//        }
//        return baseResult;
//    }



    @Override
    public byte[][] createQualifier() {
        byte[][] qualifier={Bytes.toBytes("name"),Bytes.toBytes("age")};
        return qualifier;
    }
}
