# kafka-udemy
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
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic

## Войти, чтобы принимать сообщения зная время, партицию, ключ:
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true --from-beginning
