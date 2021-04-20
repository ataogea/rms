package cn.tianyang.rms.kafka;

import cn.tianyang.rms.dao.BaseResult;
import cn.tianyang.rms.dao.Constants;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KafkaTest {

    @Value("#{'${kafka.listener.topics}'.split(',')}")
    private List<String> topics;

    @Autowired
    private  KafkaProducer producer;
    public  BaseResult  testReportProducer(){
        BaseResult result = new BaseResult();
        for(int i = 0;i < 10;i++){
            Map<String,String>  msgMap = new HashMap();
            msgMap.put(Constants.TASKID,"Sarah001");
            msgMap.put(Constants.REQUEST_TIME, String.valueOf(System.currentTimeMillis()));
            msgMap.put(Constants.REPORT_NAME,"个单承保清单");
            msgMap.put(Constants.REPORT_QUERY,"asdfsa");
            msgMap.put(Constants.QUERYID,"asdfa");
            msgMap.put(Constants.USERID,"asdfa");
            msgMap.put(Constants.OPERTYPE,"safasd" + i);
            producer.sendMessages(topics.get(0), JSONObject.toJSONString(msgMap));
        }
        return result;
    }
}
