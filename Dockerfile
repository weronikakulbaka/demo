FROM openjdk:8
add demo-mysql.jar demo-mysql.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","demo-mysql.jar"]