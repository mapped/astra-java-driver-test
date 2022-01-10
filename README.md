An application to test DataStax Cassandra Java Driver with AstraDB

1) Compile project: `./gradlew clean build`.
2) Start project with DataStax driver configuration:
```
java -Ddatastax.config.path=/path/to/datastax/config -jar build/libs/astra-java-driver-test-boot.jar
```
