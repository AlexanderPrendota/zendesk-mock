FROM openjdk:8-jdk as build
RUN mkdir -p /opt/zendesk-mock
WORKDIR /opt/zendesk-mock
ADD . .
RUN ./gradlew :bootJar

FROM openjdk:8-jre
WORKDIR /opt/zendesk-mock
RUN chmod -R 755 /opt/
RUN groupadd -g 999 mock
RUN useradd -d /opt/zendesk-mock -m -r -u 999 -g mock mock
USER mock
COPY --from=build /opt/zendesk-mock/build/libs /opt/zendesk-mock/

EXPOSE 8080
HEALTHCHECK --interval=1m --timeout=3s --retries=15 CMD curl -f http://127.0.0.1:8080/health || exit 1
ENV JAVA_OPTS -Xmx256m -Xms128m
ENTRYPOINT java ${JAVA_OPTS} -jar /opt/zendesk-mock/zendesk-0.0.1-SNAPSHOT.jar
