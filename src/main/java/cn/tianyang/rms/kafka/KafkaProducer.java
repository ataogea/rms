package cn.tianyang.rms.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, String> KAFKA_TEMPLATE;

    private Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.KAFKA_TEMPLATE = kafkaTemplate;
    }
    public void sendMessages(String topic, String message){
        ListenableFuture<SendResult<String, String>> sender = KAFKA_TEMPLATE.send(new ProducerRecord<>(topic, message));
        //发送成功后回调
        SuccessCallback successCallback = new SuccessCallback() {
            @Override
            public void onSuccess(Object result) {
                LOG.info("发送成功：topic = {},message = {}",topic,message);
            }
        };
        //发送失败回调
        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                LOG.info("发送失败：topic = {},message = {}",topic,message);
            }
        };
        sender.addCallback(successCallback,failureCallback);

    }
}
