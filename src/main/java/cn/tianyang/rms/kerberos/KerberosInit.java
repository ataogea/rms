package cn.tianyang.rms.kerberos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class KerberosInit implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(KerberosAuth.class);
    @Autowired
    private KerberosAuth kerberosAuth;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Kerberos初始认证开始...");
        kerberosAuth.kerberosAuth();
        logger.info("Kerberos初始认证成功");
    }
}
