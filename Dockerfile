FROM tomcat:jre21-temurin-jammy
LABEL authors="chskela"

#COPY ./web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF
#COPY ./tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY ./build/libs/tennis_match_scoreboard-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]