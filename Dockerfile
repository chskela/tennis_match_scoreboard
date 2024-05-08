FROM tomcat:jre21-temurin-jammy
LABEL authors="chskela"

# We remove any unwanted default webapp
RUN rm -fr /usr/local/tomcat/webapps/*

# We copy our server.xml file
COPY ./server.xml /usr/local/tomcat/conf

# We copy our WAR application inside tomcat webapps
COPY ./build/libs/tennis_match_scoreboard-1.0-SNAPSHOT.war /usr/local/tomcat/webapps

EXPOSE 8080
CMD ["catalina.sh", "run"]