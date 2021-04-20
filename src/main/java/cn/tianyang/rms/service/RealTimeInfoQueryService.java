package cn.tianyang.rms.service;

import cn.tianyang.rms.dao.RealTimeResult;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class RealTimeInfoQueryService extends RealTimeInfoHBase {
    @Value("${hbase.realTimeInfo.table}")
    private String tableNameStr;
    @Value("${hbase.realTimeInfo.familyName}")
    private String familyNameStr;
    @Autowired
    private HbaseDataSourceManager hbaseManager;


    public RealTimeResult findRealTimeInfo(String appId) throws IOException {
//        String rowKeyString = "123";
        Table table = hbaseManager.getTableByName(tableNameStr);
        Map<String, Object> map = getData(appId, familyNameStr,table);
        RealTimeResult result = new RealTimeResult();
        if (CollectionUtils.isEmpty(map)) {
            result.setName("null");
            result.setAge("0");
        }else{
            result.setName(map.get("name").toString());
            result.setAge(map.get("age").toString());
        }
        return result;
    }
}
