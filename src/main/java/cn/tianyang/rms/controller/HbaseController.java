package cn.tianyang.rms.controller;

import cn.tianyang.rms.dao.BaseResult;
import cn.tianyang.rms.dao.RealTimeResult;
import cn.tianyang.rms.kafka.KafkaTest;
import cn.tianyang.rms.service.CreateTableService;
import cn.tianyang.rms.service.RealTimeInfoQueryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/outInterface")
public class HbaseController {
    private final Logger logger = Logger.getLogger(HbaseController.class);
    private final int reNums = 3;
    private final long sleep = 1000;


    @Autowired
    private RealTimeInfoQueryService realTimeInfoQueryService;
    @Autowired
    private CreateTableService createTableService;
    @Autowired
    private KafkaTest kafkaTest;

    /* 业务员工号agent_code
     * 通过rowkey实时查询Flink更新的Hbase数据
     *
     */
    @RequestMapping(value="/queryRealTimeInfo",method = RequestMethod.GET)
    @ResponseBody
    public RealTimeResult findRealTimeInfo(String appId) throws Exception {
        RealTimeResult result = new RealTimeResult();
        int num = 0;
        boolean isSuccess = false;
        do {
            num++;
            try {
                result = realTimeInfoQueryService.findRealTimeInfo(appId);
                isSuccess = true;
            } catch (Exception e) {
                logger.error(e);
                isSuccess = false;
                try {
                    TimeUnit.MILLISECONDS.sleep(sleep);
                } catch (InterruptedException e1) {}
            }
        } while (num<=reNums && !isSuccess);
        if (!isSuccess) {
            throw new Exception("findRealTimeInfo重试次数超过限制");
        }
        return result;
    }

    /**
     * 创建hbase表的接口
     */
    @RequestMapping(value="/createTable",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult createTable(String tableName) throws IOException {
        String table = tableName;
        String family [] = new String[] { "information", "contact" };
        BaseResult baseResult = createTableService.createTable(table, family);
        return baseResult;
    }

    /**
     * 测试Kafka写入接口
     */
    @RequestMapping(value="/kafkaWrite",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult kafkaWrite(){
        BaseResult result = kafkaTest.testReportProducer();
        return result;
    }
}
