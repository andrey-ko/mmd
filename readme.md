## MiniMemoData project

#### subprojects
 - "infra": infrastructure code, all cluster related logic, communication logic, deployment logic.
    depends on: "core", "vertx", "hazelcast"

 - "core": all business logic, like consumer/producer/processor interfaces, rules
    depends on: "jackson"

 - "apps": all runnable application
    depends on: "infra", "core"

#### design
TODO...

#### how to run
TODO...

#### requirements
java 1.8


