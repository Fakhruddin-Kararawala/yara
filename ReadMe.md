
Yara : Senior Software Engineer

==

 

# The assignment

As an API consumer, I’d like to be able to maintain my field through a REST API endpoint so that I can Create,
Retrieve, Update and Delete a field.

As an API consumer, I’d like to be able to retrieve weather history for a given field boundary so that I can assess
whether or not my soil needs some action, such as irrigation.

 

## Requirements: 

- Manage a Field

     - The API should accept all the operations through a single endpoint. i.e: /fields;
           /fields/{fieldId}
     - The API should not require any authentication
     - The API should not do any validation of any sort regarding the field boundary
     - The API should not accept any partial updates

- Retrieve weather history for a given field boundary

     - The API should fetch the weather history for a given polygon from OpenWeather Agro Monitoring

            o https://agromonitoring.com/api/polygons
            o https://agromonitoring.com/api/history-weather

     - The API should retrieve temperature, temperature min and max as well as humidity for the last 7 days
     - The weather data should be retrieved through a GET request towards /fields/{fieldId}/weather
     - The performance of the query does not matter for the moment
     - The API should not require any authentication

 

# Stack

 

* Java 11

* Spring Boot 2

* Lombok

* Swagger 2

* Embedded MongoDB

* Docker

* Embedded Cache

* MongoDB

* Maven3

 

# Configuring

 

* This project uses the [Agro Monitoring](https://agromonitoring.com/api/history-weather) service to fetch weather data.

* Embedded MongoDB in memory database to run the tests.

 

 

# Building

This project uses Maven for project management tool. Also Docker file is also attached for containerization.  All the commands should be run from the root folder.

 

To run the test cases, the following command must be executed:

 

```

$ mvn test

```

The following command will package the project.

 

```

$ mvn clean package

```

 

# Running

 

The project can be directly run from below command:

 

```

java -jar target/fieldService-0.0.1-SNAPSHOT.jar

```

or

 

```

mvn spring-boot:run

```

 

or

 

```

docker-compose up -d

```

 

# Note

I am using embedded mongo while running test cases but to deploy fieldservice app on docker application.properties

needs to be modified. change the hostname from localhost to mongo now it will point the mongoDB instance inside docker. 

 

# Testing

 

* Hit the url - `http://localhost:8082

* This will open swagger UI with Api listing.

