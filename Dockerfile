FROM tomcat:jre21-temurin-jammy
LABEL authors="chskela"

# We remove any unwanted default webapp
RUN rm -fr "$CATALINA_HOME"/webapps/*

# We copy our WAR application inside tomcat webapps
COPY ./build/libs/tennis_match_scoreboard-1.0.war $CATALINA_HOME/webapps/

COPY ./server.xml /usr/local/tomcat/conf

EXPOSE 8080
CMD ["catalina.sh", "run"]