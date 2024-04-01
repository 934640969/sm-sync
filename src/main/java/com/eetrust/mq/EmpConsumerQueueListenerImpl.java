package com.eetrust.mq;

import cn.hutool.http.HttpUtil;
import com.ctg.mq.api.bean.MQResult;
import com.ctg.mq.api.listener.ConsumerQueueListener;
import com.ctg.mq.api.listener.ConsumerQueueStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpConsumerQueueListenerImpl implements ConsumerQueueListener {
    private Logger log = LoggerFactory.getLogger(EmpConsumerQueueListenerImpl.class);

    @Value("${eim.url}")
    private String eimUrl;

    @Override
    public ConsumerQueueStatus onMessage(List<MQResult> mqResultList) {
        log.info("************ emp-mq 消息消费开始 ************");
        if (mqResultList.size() == 0) {
            return ConsumerQueueStatus.SUCCESS;
        }

        mqResultList.forEach(mqObject -> {
            MQResult mqResult = (MQResult) mqObject;
            String jsonStr = new String(mqResult.getMessage().getBody());
            log.info("emp-mq MQResult数据：" + jsonStr);
            //TODO 业务逻辑实现
            HttpUtil.post(eimUrl,jsonStr);
        });
        log.info("************ emp-mq 消息消费结束 ************");
        return ConsumerQueueStatus.SUCCESS;
    }
}
