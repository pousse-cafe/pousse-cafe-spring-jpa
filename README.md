![Travis build status](https://travis-ci.org/pousse-cafe/pousse-cafe-spring-jpa.svg?branch=master)
![Maven status](https://maven-badges.herokuapp.com/maven-central/org.pousse-cafe-framework/pousse-cafe-spring-jpa/badge.svg)

# Pousse-Café Spring JPA

This storage plugin uses [Spring Data JPA](https://spring.io/projects/spring-data-jpa) as its backend and is
therefore able to store data into any JPA-supported database.

`EntityAttributes` [implementations](http://www.pousse-cafe-framework.org/doc/reference-guide/#implement-aggregates)
should be annotated using Java persistence annotations (i.e. `@Entity`, `@Id`, etc.).

`JpaDataAccess` expects subclasses to define `jpaRepository` method which must return a Spring Data
`JpaRepository`. The `convertId` method must be defined in order to convert an aggregate's ID into a serializable
object. In order to access the `JpaRepository`, simply autowire it in your data access.

Here is an example of data access implementation:

    public class ExampleJpaDataAccess extends JpaDataAccess<ExampleId, ExampleData, String> implements ExampleDataAccess<ProductData> {
    
        @Override
        protected String convertId(ExampleId id) {
            return ...;
        }
    
        @Override
        protected JpaRepository<ExampleData, String> jpaRepository() {
            return repository;
        }
    
        @Autowired
        private ExampleDataMongoRepository repository;
    
        ...
    }

Your Spring configuration class then looks like this (do not forget to include `poussecafe.spring` in your
package scan):

    @Configuration
    @ComponentScan(basePackages = { "poussecafe.spring" })
    public class AppConfiguration {
    
        @Bean
        public Bundles bundles(
                Messaging messaging,
                SpringJpaStorage storage) {
            MessagingAndStorage messagingAndStorage = new MessagingAndStorage(messaging, storage);
            return new Bundles.Builder()
                // Register your bundles here using withBundle and use messagingAndStorage
                // when building them
                .build();
        }
    }

## Configure your Spring Boot Maven project

Add the following snippet to your POM:

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.pousse-cafe-framework</groupId>
        <artifactId>pousse-cafe-spring-jpa</artifactId>
        <version>${poussecafe.spring.jpa.version}</version>
    </dependency>
    <dependency>
        <groupId>org.pousse-cafe-framework</groupId>
        <artifactId>pousse-cafe-spring</artifactId>
        <version>${poussecafe.spring.version}</version>
    </dependency>
    <!--
        Put here the dependency to your preferred JDBC driver
    -->
