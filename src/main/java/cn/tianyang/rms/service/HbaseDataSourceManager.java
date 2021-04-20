package cn.tianyang.rms.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HbaseDataSourceManager {
    @Value("${hbase.zkPort}")
    private String zkPort;
    @Value("${hbase.zkHost}")
    private String zkHost;
    @Value("${hbase.master}")
    private String master;
    @Value("${kerberos.tes.krb5}")
    public String tes_krb5;
    @Value("${kerberos.tes.keytab}")
    public String tes_keytab;
    private Configuration conf;
    private Connection conn;


    @PostConstruct
    public void init() throws Exception {
        if (conn == null) {
            System.setProperty("java.security.krb5.conf",tes_krb5);
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.property.clientPort", zkPort);
            conf.set("hbase.zookeeper.quorum", zkHost);
            conf.set("hbase.master", master);
            conf.set("hbase.cluster.distributed", "true");
            conf.set("hadoop.security.authentication", "kerberos");
            conf.set("hbase.security.authentication", "kerberos");
            conf.set("hbase.rpc.protection", "authentication");
            conf.set("hbase.master.kerberos.principal", "hbase/_HOST@PICC.COM"); // this is needed even if you connect over rpc/zookeeper
            conf.set("hbase.regionserver.kerberos.principal", "hbase/_HOST@PICC.COM"); //what principal the master/region. servers use.

            String principal = System.getProperty("kerberosPrincipal", "app_cust@PICC.COM");
            String keytabLocation = System.getProperty("kerberosKeytab",tes_keytab);

            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab(principal, keytabLocation);
            UserGroupInformation.loginUserFromKeytabAndReturnUGI(principal, keytabLocation);


            conn = ConnectionFactory.createConnection(conf);
        }
    }

    @PreDestroy
    public void destroy() throws IOException {
        if(conn != null) {
            conn.close();
        }
    }

    public Table getTableByName(String tableNameStr) throws IOException{
        TableName tableName = TableName.valueOf(tableNameStr);
        return conn.getTable(tableName);
    }

    public Admin createAdmin() throws IOException{
        Admin admin = conn.getAdmin();
        return admin;
    }
}
