FROM java:8
WORKDIR /
COPY testDocker.jar /HelloWorld.jar
#CMD java -jar HelloWorld.jar blandon
CMD ["java", "-jar", "HelloWorld.jar", "nedlon"]