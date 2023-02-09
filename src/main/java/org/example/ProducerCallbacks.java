package org.example;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerCallbacks {
    private static final Logger log = LoggerFactory.getLogger(ProducerCallbacks.class);

    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("batch.size","400");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("second_topic", "hello kafka");
        /*
        // при каждом запуске будет каждый раз Partition
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                log.info("Topic: " + metadata.topic() + "\n" +
                        " Partition: " + metadata.partition() + "\n" +
                        " Offset: " + metadata.offset() + "\n" +
                        " Timestamp: " + metadata.timestamp());
            }
        });
         */

        for(int x = 0; x < 10; x++) {
            for (int i = 0; i < 10; i++) {
                // попадут все в один Partition, запускаем в batch
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        log.info("Topic: " + metadata.topic() + "\n" +
                                " Partition: " + metadata.partition() + "\n" +
                                " Offset: " + metadata.offset() + "\n" +
                                " Timestamp: " + metadata.timestamp());
                    }
                });
            }
            log.info("-------------------------------------------------------------------");
            Thread.sleep(1000);
        }
        producer.flush();

        producer.close();
    }
}
