package cn.tianyang.rms.service;

import cn.tianyang.rms.dao.BaseResult;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CreateTableService {
    @Autowired
    private HbaseDataSourceManager hbaseManager;

    public BaseResult createTable(String tableNmae, String[] cols) throws IOException {
        TableName tableName = TableName.valueOf(tableNmae);
        Admin admin = hbaseManager.createAdmin();
        BaseResult<Object> result = new BaseResult<>();
        if (admin.tableExists(tableName)) {
            System.out.println("表已存在！");
            result.setParamError("hbase表已存在");
            return result;
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String col : cols) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
            result.setSuccessInfo("hbase表创建成功");
            return result;
        }
    }
}
