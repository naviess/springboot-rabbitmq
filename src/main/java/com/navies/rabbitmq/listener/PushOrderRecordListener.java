package com.navies.rabbitmq.listener;

import com.navies.rabbitmq.entity.OrderRecord;
import com.navies.rabbitmq.listener.event.PushOrderRecordEvent;
import com.navies.rabbitmq.mapper.OrderRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 这就是监听器-跟RabbitMQ的Listener几乎是一个理念
 */
@Component
public class PushOrderRecordListener implements ApplicationListener<PushOrderRecordEvent>{

    private static final Logger log= LoggerFactory.getLogger(PushOrderRecordListener.class);

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Override
    public void onApplicationEvent(PushOrderRecordEvent event) {
        log.info("监听到的下单记录： {} ",event);

        try {
            if (event!=null){
                OrderRecord entity=new OrderRecord();
                BeanUtils.copyProperties(event,entity);
                orderRecordMapper.insertSelective(entity);
            }
        }catch (Exception e){
            log.error("监听下单记录发生异常：{} ",event,e.fillInStackTrace());
        }
    }
}
