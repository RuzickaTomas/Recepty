FROM 3.8.5-openjdk-11

WORKDIR /app

COPY . /app

EXPOSE 8080

RUN mvn clean install

FROM tomee

LABEL maintainer="ruzicka.tomas93@gmail.com"

COPY ./target/Recepty-0.0.1-SNAPSHOT.war /usr/local/tomee/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]