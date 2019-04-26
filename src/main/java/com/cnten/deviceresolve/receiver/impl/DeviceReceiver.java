package com.cnten.deviceresolve.receiver.impl;

import com.cnten.deviceresolve.adapter.ResolveAdapter;
import com.cnten.deviceresolve.receiver.AbstractReceiver;
import com.cnten.deviceresolve.utils.AdapterEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${queue.name}")
public class DeviceReceiver implements AbstractReceiver {

    private static final Logger log = LoggerFactory.getLogger(DeviceReceiver.class);
    @RabbitHandler
    public void process(String data) {
        try {
            if (data.contains(",")){
                String messageTop = data.split(",")[0];
                messageTop = messageTop.substring(1,messageTop.length());
                AdapterEnum enumByKey = AdapterEnum.getEnumByKey(messageTop);
                if (enumByKey != null) {
                    ResolveAdapter resolveAdapter = (ResolveAdapter) Class.forName(enumByKey.getValue()).newInstance();
                    resolveAdapter.excute(data);
                }
            }
        } catch (Exception e) {
            log.info("-----------data---------:"+data);
            e.printStackTrace();
        } finally {

        }
    }
}
