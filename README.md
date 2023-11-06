# GD2Spring
GeometryDash server emulator written in Java with using the Spring Framework.

Supported version of Geometry Dash: 2.11


## Features

- More vanilla server behavior than GMDPrivateServer.
- A stateless server with the ability to scale horizontally.
- RabbitMQ integration allows you to handle server events.


## Build and Run
For run this project you need Java 17.

### Clone repository
```shell
git clone https://github.com/ScarletRedMan/GD2Spring.git

cd ./GD2Spring/
```

### Build
Windows:
```shell
gradlew assemble
```

Linux:
```shell
./gradlew assemble
```

Complied file located in `./build/libs/GD2Spring.jar`


### Run
Run Postgres and RabbitMQ servers.

Set environments for GD2Spring:

| Enviroment name             | Description           |
|-----------------------------|-----------------------|
| GD2SPRING_DATABASE_URL      | Postgres database URL |
| GD2SPRING_DATABASE_USER     | Database user         |
| GD2SPRING_DATABASE_PASSWORD | Database password     |
| GD2SPRING_RABBITMQ_HOST     | RabbitMQ host         |
| GD2SPRING_RABBITMQ_USER     | RabbitMQ user         |
| GD2SPRING_RABBITMQ_PASSWORD | RabbitMQ password     |

All these environments are required for start application.

Run application:
```shell
java -jar GD2Spring.jar
```

Run with dev mode:
```shell
java -jar -Dspring.profiles.active=test GD2Spring.jar
```


## Useful links

[GD-ServerSelector](https://github.com/ScarletRedMan/GD-ServerSelector) - The script for switching between servers (Windows Only)

[GMDPrivateServer](https://github.com/Cvolton/GMDprivateServer) - Basically a Geometry Dash Server Emulator written in PHP
