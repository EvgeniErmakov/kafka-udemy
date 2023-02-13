# kafka-udemy
##
Подробные материалы 
https://www.conduktor.io/kafka/advanced-kafka-consumer-with-java/

## Запустил кафку:
kafka-storage.sh random-uuid

kafka-storage.sh format -t IwKmd6xbR-mJXWgfkoqAFA -c ./config/kraft/server.properties

kafka-server-start.sh ./config/kraft/server.properties

## Создание топиков:
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create

kafka-topics.sh --bootstrap-server localhost:9092 --create --topic second_topic --partitions 5

kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 1

## Получить лист топиков:
kafka-topics.sh --bootstrap-server localhost:9092 --list

## Получить больше информации о конкретном топике:
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe

## Получить лист групп
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

## Получить больше информации о конкретной топпе:
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group group_name

## Удалить топик:
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --delete

## Войти в режим отправки сообщений:
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic

## Войти чтобы принимать сообщения:

### Без группы:
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic

### В группе:
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group third_group

### Создание консъюмера в групее, который получит все невычетанные сообщения благодаря --from-beginning
Если запустить без --from-beginning, тогда мы будем получать сообщения отправленные после старта консъюмера

kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group fourth_group --from-beginning

## Войти, чтобы принимать сообщения зная время, партицию, ключ:
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true --from-beginning


## kafka-config

### Получить конфигурацию топика по имени
kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name 5_topic --describe

### Изменить конфигурацию топика, установить минимальное значение репликации
kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name 5_topic --alter --add-config min.insync.replicas=2

### Удалить конфигурацию 
kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name 5_topic --alter --delete-config min.insync.replicas
