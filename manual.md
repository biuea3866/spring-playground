### 토픽 생성
kafka-topics --create --topic test-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

### 메시지 전송
kafka-console-producer --topic test-topic --bootstrap-server localhost:9092

### 메시지 수신
kafka-console-consumer --topic test-topic --bootstrap-server localhost:9092 --from-beginning