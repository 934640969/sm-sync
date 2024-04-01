package com.eetrust.mq;

import com.ctg.mq.api.CTGMQFactory;
import com.ctg.mq.api.IMQPushConsumer;
import com.ctg.mq.api.PropertyKeyConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MqConsumerRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MqConsumerRunner.class);
	
    @Autowired
    private EmpConsumerQueueListenerImpl empConsumerQueueListener;
	
    @Autowired
    private MssConsumerQueueListenerImpl mssConsumerQueueListener;
	
    @Value("${com.xxx.ctgmq.consumer.mss.groupName}")
    private String mssConsumerGroupName;
	
	@Value("${com.xxx.ctgmq.consumer.emp.groupName}")
    private String empConsumerGroupName;
	
	@Value("${com.xxx.ctgmq.emp.topic}")
    private String empTopic;
	
	@Value("${com.xxx.ctgmq.mss.topic}")
    private String mssTopic;

    @Value("${com.xxx.ctgmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${com.xxx.ctgmq.namesrvAuthID}")
    private String namesrvAuthID;

    @Value("${com.xxx.ctgmq.namesrvAuthPwd}")
    private String namesrvAuthPwd;

    @Value("${com.xxx.ctgmq.consumer.clusterName}")
    private String clusterName;

    @Value("${com.xxx.ctgmq.consumer.tenantID}")
    private String tenantID;

    public IMQPushConsumer getConsumer(String consumerGroupName){
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.ConsumerGroupName, consumerGroupName);
        properties.setProperty(PropertyKeyConst.NamesrvAddr, namesrvAddr);
        properties.setProperty(PropertyKeyConst.NamesrvAuthID, namesrvAuthID);
        properties.setProperty(PropertyKeyConst.NamesrvAuthPwd, namesrvAuthPwd);
        properties.setProperty(PropertyKeyConst.ClusterName, clusterName);
        properties.setProperty(PropertyKeyConst.TenantID, tenantID);
        return CTGMQFactory.createPushConsumer(properties);
    }

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        log.info("注册监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
         //注册人员监听
        log.info("注册人员监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        IMQPushConsumer empPushConsumer = this.getConsumer(empConsumerGroupName);
        int result1 = empPushConsumer.connect();
        log.info("人员-MqConsumerRunner executing! connect result:" + result1);
        empPushConsumer.listenQueue(empTopic, null, empConsumerQueueListener);

        //注册mss监听
        log.info("注册mss监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        IMQPushConsumer mssPushConsumer = this.getConsumer(mssConsumerGroupName);
        int result2 = mssPushConsumer.connect();
        log.info("mss-MqConsumerRunner executing! connect result:" + result2);
        mssPushConsumer.listenQueue(mssTopic, null, mssConsumerQueueListener);

    }
}
