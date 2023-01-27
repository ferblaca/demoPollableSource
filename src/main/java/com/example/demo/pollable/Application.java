package com.example.demo.pollable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Application {

    private Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    @Qualifier("pollableConsumer-in-0")
    private PollableMessageSource pollableConsumer;

    @Bean
    public ApplicationRunner runnerBatchSend(StreamBridge bridge) {
        return args -> {
            for (int i = 0; i < 10; i++) {
                bridge.send("producer-out-0", MessageBuilder.withPayload(new Foo("foo-" + i)).build());
            }
        };
    }

    @Scheduled(fixedDelay = 5_000)
    public void poll() {
        LOG.info("Polling...");
        this.pollableConsumer.poll(m -> {
            LOG.info(m.getPayload().toString());
        }, new ParameterizedTypeReference<Foo>() {
        });
    }

}
