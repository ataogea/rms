package cn.tianyang.rms.service;

import cn.tianyang.rms.dao.BaseResult;
import cn.tianyang.rms.dao.RealTimeInfo;
import cn.tianyang.rms.utils.ReflectObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class RealTimeInfoAction {
    public BaseResult<Map<String, Object>> createBillResult(Map<String, Object> map) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BaseResult<Map<String, Object>> baseResult = new BaseResult<>();
        Map<String, Object> result = new HashMap<String, Object>();

        RealTimeInfo realTimeInfo = new RealTimeInfo();
        ReflectObject.objectByMap(realTimeInfo, map);
        result.put("realTimeInfo", realTimeInfo);

        baseResult.setSuccessInfo(result);
        return baseResult;

    }
}
