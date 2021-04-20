package cn.tianyang.rms.kerberos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kerberos")
public class KerberosConfig {
    @Value("${kerberos.envType}")
    public String envType;

    /*测试环境*/
    @Value("${kerberos.tes.jaas}")
    public String tes_jaas;

    @Value("${kerberos.tes.krb5}")
    public String tes_krb5;

    @Value("${kerberos.tes.keytab}")
    public String tes_keytab;

    @Value("${kerberos.tes.user}")
    public String tes_user;

    @Value("${kerberos.tes.debug}")
    public String tes_debug;
}
