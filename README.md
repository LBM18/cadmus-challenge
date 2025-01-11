# CADMUS CHALLENGE APP PROJECT - LUCAS B. MORAES
___________________________________________________________________________________________________________________________________________________
## Introduction:
This project aims to develop an end-to-end Rest API application using technological tools like Java, Spring Boot, Maven, JPA, PostgreSQL and Docker.

The following system is a complete backend Swagger API for artists, albums and musics registrations using a clean layered architecture.

This challenge was passed on by technology company Cadmus as a test for the company's hiring process and has the following specifications:
* Each Artist has an ID, name, nationality, website address and profile image.
* A music has an ID, title, duration (minutes and seconds).
* An album has an ID, title, release year and cover image.
* Every music is related to an album and has a number identifying the track in that Album.
* Every album has an Artist.
* All fields are mandatory.
* It must be possible to insert, list, update and delete artists, albums and musics.
* All listings must be single (by ID) or paged (all records).
* It must be possible to list all musics by a given Artist with information about the album.
* It must be possible to list all musics on an album sorted by track number or alphabetically by title.
* The release year of an album cannot be a date after the current one.
* Track numbers must be greater than zero.
* Music durations must be valid minutes and seconds.
* The artist's website address must be a valid URL.
* Artist profile pictures and album art must be stored by the API (using a CDN, self-hosted storage, or some other alternative).
* Data must be persisted using a relational database and remain consistent at all times.

API technical requirements:
* Unit tests must be created.
* Publish the code in a public repository.
* Object-relational mapping must be used.
* Use Docker for database and API.

### Technologies:
* Java 17.
* Apache Maven 3.9.8.
* Spring Boot Starter Tools 3.4.1: Web, Data JPA, Validation, WebFlux, Devtools, JUnit and Mockito.
* SpringDoc OpenAPI UI 2.7.0 (port 8080).
* Lombok 1.18.36.
* Docker 3.9 (docker-compose).
* Database PostgreSQL 15 (port 5432).
* PgAdmin (port 8000), optional for database tests.
* Database H2 (integrated tests only).
* Git 2.47.1.windows.1.
* IntelliJ IDEA Community Edition 2021.3.3.

### Adopted Practices:
* Object-oriented programming practices (inheritance, polymorphism, encapsulation, abstraction).
* Handling error messages using Java exceptions.
* API REST.
* Spring MVC.
* Dependency injection.
* SOLID.
* Clean architecture.
* Layered architecture.
* Object relational mapping (ORM) with JPA.
* Swagger 3 automatic API documentation generation.
* Application containerization (Docker).
* Automatic unit and integration tests.

### Build and Run:
1. Install Java 17, Apache Maven and Docker if you do not have it yet.
2. Download/Clone this project Git repository to your local machine and go to the root folder (cadmus-challenge).
3. Build the application:
    ```bash
    $ mvn clean package
    ```
4. Run application on Docker:
    ```bash
    $ docker-compose up --build -d
    ```

### Access URLs:
* Swagger UI Api: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* PgAdmin database client (optional): [http://localhost:8000](http://localhost:8000)
   * Login:
      * E-mail: admin@admin.com
      * Password: admin
   * Register new server:
      * Host: db
      * Port: 5432
      * Database: cadmusdb
      * Username: postgres
      * Password: qwe123

### Tests:
To run all automated tests:
```bash
$ mvn test
```

### Stop:
To end the application on Docker:
```bash
$ docker-compose down
```

### Next Steps:
* Implement additional demo entities and functionalities in the system.
* Configure SonarQube and increase test coverage for improve overall code quality of the project.
* Add Javadoc and increase Swagger API documentation, for better documentation.
* Enable database migration tool on the project.
* Integrate with authentication and authorization services, such as OAuth, for more robust user and permission management.
* Implement caching of frequently accessed data to reduce the load on the database.
* Configure CI/CD (Continuous Integration/Continuous Delivery) pipelines for test and deployment automation.

### Contribute:
For any issues, comments or feedback: [Here](https://github.com/LBM18/cadmus-challenge/issues)
___________________________________________________________________________________________________________________________________________________
### Reference Documentation:
For further reference, please consider the following sections:
* [Java 17 JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Official Apache Maven Documentation](https://maven.apache.org/guides/index.html)
* [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
* [Spring Initializer](https://start.spring.io)
* [Docker Hub](https://hub.docker.com/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
