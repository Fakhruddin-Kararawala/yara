# bring maven and java image for building and running our jar on docker

FROM maven:3-openjdk-11

# fetch maven dependencies first so it won't do it
# on every build, unless there's a change in pom.xml

WORKDIR /app
ADD pom.xml /app
RUN mvn verify clean --fail-never


# build jar
COPY . /app
RUN mvn -v
RUN mvn clean install


# expose port for access
EXPOSE 8082


# run jar, * finds the approriate file
ENTRYPOINT ["java", "-Djava.security.egd=/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "target/*.jar"]
