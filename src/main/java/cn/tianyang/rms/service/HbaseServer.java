package cn.tianyang.rms.service;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HbaseServer {
    private final Logger logger = Logger.getLogger(HbaseServer.class);

    private final int cach = 5000;
    private final int batch = 2;

    /**
     * rowKey精确查询habse数据
     * @param rowKeyStr
     * @param familyNameStr
     * @param table
     * @return
     * @throws IOException
     */
    public Map<String, Object> getData(String rowKeyStr, String familyNameStr, Table table) throws IOException{
        logger.info("the table name is "+table.getName()+" and the family is "+familyNameStr);
        Map<String, Object> map = new HashMap<>();
        //列族
        byte[] familyName = Bytes.toBytes(familyNameStr);
        //字段二维数组
        byte[][] qualifier = createQualifier();
        //rowkey
        byte[] rowKey = Bytes.toBytes(rowKeyStr);
        Get get = new Get(rowKey);
        for (byte[] bs : qualifier) {
            //bs为一个列的byte数组，添加列族值和列值
            get.addColumn(familyName, bs);
        }
        //获取查询的数据
        logger.debug("method:HbaseService-getData-get"+" rowKeyStr:"+rowKeyStr+" start");
        Result r = table.get(get);
        logger.debug("method:HbaseService-getData-get"+" rowKeyStr:"+rowKeyStr+" end");
        for (Cell cell : r.rawCells()) {
            //cell为一个列对象的实例，qualifierStr为列名
            String qualifierStr = Bytes.toString(CellUtil.cloneQualifier(cell));
            //value为列值
            Object value = Bytes.toString(CellUtil.cloneValue(cell));
            //将列名-列值放入map
            map.put(qualifierStr, value);
        }
        table.close();
        return map;
    }

    /**
     * md5加密
     * @param password
     * @return
     */
    public String md5PasswordEncoderUtil(String password){
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        return md5PasswordEncoder.encodePassword(password, null);
    }

    /**
     * rowKey范围区间查询habse数据
     * @param startRow
     * @param endRow
     * @param familyNameStr
     * @param table
     * @return
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public List<Map<String, Object>> getData(String startRow, String endRow, String familyNameStr, Table table) throws IOException{
        List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
        Map<String,Map<String, Object>> map = new HashMap<>();

        byte[] familyName = Bytes.toBytes(familyNameStr);//列族名
        byte[][] qualifier = createQualifier();//列名数组
        Scan scan = new Scan();
        scan.setCaching(cach);//setCaching设置的值为每次rpc的请求记录数
        scan.setBatch(batch);//setBatch设置每次取的column size
        if (!StringUtils.isEmpty(startRow)) {
            scan.setStartRow(Bytes.toBytes(startRow));
        }
        if (!StringUtils.isEmpty(endRow)) {
            scan.setStopRow(Bytes.toBytes(endRow));
        }
        for (byte[] bs : qualifier) {
            scan.addColumn(familyName, bs);//列族-列名
        }
        ResultScanner rScanner = table.getScanner(scan);
        for (Result r = rScanner.next(); r != null; r = rScanner.next()) {
            for (Cell cell : r.rawCells()) {
                String rowKet = Bytes.toString(CellUtil.cloneRow(cell));//获取rowkey
                String qualifierStr = Bytes.toString(CellUtil.cloneQualifier(cell));//获取列名
                Object value = Bytes.toString(CellUtil.cloneValue(cell));//获取列值
                if (map.get(rowKet) == null) {
                    Map<String, Object> valueMap = new HashMap<>();
                    valueMap.put(qualifierStr, value);
                    map.put(rowKet, valueMap);
                }else{
                    map.get(rowKet).put(qualifierStr, value);
                }
            }
        }
        table.close();
        for (String key:map.keySet()) {
            Map<String, Object> valueMap = map.get(key);
            datas.add(valueMap);
        }
        return datas;
    }

    public abstract byte[][] createQualifier();
}
