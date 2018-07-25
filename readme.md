## MiniMemoData project

#### applications
 - "test producer": publishes to "data" topic some test data
 - "data processor": subscribes to "data" topic executes some aggregating rules and publish results to "results"  topic
 - "printer": subscribes to "results" topic and print all received messages to console

#### subprojects
 - "infra": infrastructure code, all cluster related logic, communication logic, deployment logic.\
    depends on: "core", "vertx", "hazelcast"

 - "core": all business logic, like consumer/producer/processor interfaces, rules\
    depends on: "jackson"

 - "apps": all runnable application\
    depends on: "infra", "core"


#### design notes

there are 2 kind of participant in the system: "consumer" & "subscriber"\
"consumer" follows push semantics, once there data message available, "consumer" will be notified by invoking processData method\
"producer" follows pull semantics, it will be periodically examined if it has data to send by invoking poll method.
It returns null if no data available.\
there is also "processor" participant which is "consumer" and "producer" at the same time

TODO...


#### build & run

build apps fat jar:
```
./gradlew :apps:fatJar
```

run printer app
```
java -cp ./apps/build/libs/apps-0.0.1-SNAPSHOT-fat.jar org.andreyko.mmd.apps.ResultsPrinterApp
```

run test producer app
```
java -cp ./apps/build/libs/apps-0.0.1-SNAPSHOT-fat.jar org.andreyko.mmd.apps.TestProducerApp
```

run data processor app
```
java -cp ./apps/build/libs/apps-0.0.1-SNAPSHOT-fat.jar org.andreyko.mmd.apps.DataProcessorApp
```


#### requirements
java 1.8


