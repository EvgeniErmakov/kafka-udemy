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
