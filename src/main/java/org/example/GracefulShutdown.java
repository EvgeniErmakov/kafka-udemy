package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class GracefulShutdown {

    private static final Logger log = LoggerFactory.getLogger(GracefulShutdown.class);

    public static void main(String[] args) throws InterruptedException {
        String groupId = "first_group_id";
        String topic = "third_topic";

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        //    properties.setProperty("security.protocol", "SASL_SSL");
        //    properties.setProperty("sasl.mechanism", "PLAIN");

        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());

        properties.setProperty("group.id", groupId);

        properties.setProperty("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Определили что приложение останавливает свою работу, "
                        + "давайте вызовим метод на консъюмере, чтобы он больше не принимал сообщения.");
                consumer.wakeup();
                // join the main thread to allow the execution of the code in the main thread
                 try {
                     mainThread.join();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
            }
        });

        try {
            consumer.subscribe(Arrays.asList(topic));
            while (true) {
           //     log.info("polling");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                records.forEach(record -> {
                    log.info(" Key: " + record.key() +
                            " Value: " + record.value() +
                            " Partition: " + records.partitions() +
                            " Offset : " + record.offset());
                });
            }
        } catch (WakeupException e) {
            log.info("Consumer прикращает свою работую.");
        } catch (Exception e) {
            log.info("Unexpected exception the in consumer", e);
        } finally {
            consumer.close();
            log.info("Consumer прикратил свою работу!");
        }
    }
}
