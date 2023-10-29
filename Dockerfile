FROM openjdk:17-alpine
LABEL authors="ScarletRedMan"

RUN apk add git

RUN mkdir /app
RUN mkdir /app/install

WORKDIR /app/install
RUN git clone https://github.com/ScarletRedMan/GD2Spring.git
WORKDIR GD2Spring
RUN ./gradlew assemble
COPY ./build/libs/GD2Spring-0.0.1-SNAPSHOT.jar /app/server.jar
WORKDIR /app
RUN rm -rf install

EXPOSE 8080

CMD java -jar server.jar
