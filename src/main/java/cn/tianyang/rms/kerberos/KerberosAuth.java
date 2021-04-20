package cn.tianyang.rms.kerberos;

import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KerberosAuth {
    private static final Logger logger = LoggerFactory.getLogger(KerberosAuth.class);
    @Autowired
    private KerberosConfig kerberosConfig;

    public void kerberosAuth(){
        if(kerberosConfig.envType.equalsIgnoreCase("2")){
            kerberosAuthTes();
        }else{
            logger.error("请确定运行环境...");
        }
    }

    /*
     * 测试配置
     * */
    public void kerberosAuthTes(){
        System.setProperty("java.security.auth.login.config", kerberosConfig.tes_jaas);
        System.setProperty("java.security.krb5.conf", kerberosConfig.tes_krb5);
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        System.setProperty("sun.security.krb5.debug", kerberosConfig.tes_debug);
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        try {
            configuration.addResource(KerberosAuth.class.getClassLoader().getResourceAsStream("testenv/core-site.xml"));
            configuration.addResource(KerberosAuth.class.getClassLoader().getResourceAsStream("testenv/hdfs-site.xml"));
            logger.info("开始配置查询用户kerberos密文......");
            UserGroupInformation.setConfiguration(configuration);
            UserGroupInformation.loginUserFromKeytab(kerberosConfig.tes_user, kerberosConfig.tes_keytab);
            logger.info("完成配置查询用户kerberos密文......");
            logger.info("kerberos-user>>>>>>" + UserGroupInformation.getCurrentUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
