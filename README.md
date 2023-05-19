# DevelopmentBooks

There is a series of books about software development that have been read by a lot of developers who want to improve
their development skills. Let’s say an editor, in a gesture of immense generosity to mankind (and to increase sales as
well), is willing to set up a pricing model where you can get discounts when you buy these books. The available books
are :

1. Clean Code (Robert Martin, 2008)
2. The Clean Coder (Robert Martin, 2011)
3. Clean Architecture (Robert Martin, 2017)
4. Test Driven Development by Example (Kent Beck, 2003)
5. Working Effectively With Legacy Code (Michael C. Feathers, 2004)

# Rules

One copy of the five books costs 50 EUR.

- If, however, you buy two different books from the series, you get a 5% discount on those two books.
- If you buy 3 different books, you get a 10% discount.
- If you buy 4 different books, you get a 20% discount.
- If you go for the whole hog, and buy all 5, you get a huge 25% discount.
- Note that if you buy, say, 4 books, of which 3 are different titles, you get a 10% discount on the 3 that form part of
  a set, but the 4th book still costs 50 EUR.

# Functional case

If the shopping basket contains the below books.

- 2 copies of the “Clean Code” book
- 2 copies of the “Clean Coder” book
- 2 copies of the “Clean Architecture” book
- 1 copy of the “Test Driven Development by Example” book
- 1 copy of the “Working effectively with Legacy Code” book

We can avail the discounts for above shopping basket containing 8 books by grouping [5,3] or [4,4] or [3,3,2], etc.
Output should be **320 EUR** as the best price by applying **[4,4]** as below.

- (4 * 50 EUR) - 20% [first book, second book, third book, fourth book]
- (4 * 50 EUR) - 20% [first book, second book, third book, fifth book]

= (160 EUR + 160 EUR) => **320 EUR**

# Purpose

Develop a application to **calculate the best price** of any conceivable shopping basket by applicable the above
discounts using **Test Driven Development** (TDD).

# Tools used for developing this application

- **Backend** : Java 8, Spring Boot 2.7, Swagger 3.0
- **Build tool**: Maven 3.8.1
- **Reporting** Jacoco 0.8.10

## How to build the application

* Clone this repository

```
https://github.com/<<Your Repo location>>
```

* You can build the project and run the tests by running ```mvnw clean install```

```
Note:- 
If You already have maven in your system same can be archived by running mvn clean install
```

## How to run the application

* By default the application will start on **port 8080**. If you want the application to run on different port 8082, you
  can pass additional parameter **--server.port=8082** while starting the application (or) you can update the *
  *server.port** in **application.properties**
* Once successfully built, you can run the service by one of this commands:

```
	java -jar target\developmentbooks-1.0.0-SNAPSHOT.jar
			
							(or)
							
	java -jar target\developmentbooks-1.0.0-SNAPSHOT.jar --server.port=8082
```

Once the application runs you should see below message in console log

```

2022-08-09 11:53:55.767  INFO 6976 --- [           main] c.b.k.d.DevelopmentBooksApplication      : Started DevelopmentBooksApplication in 112.716 seconds (JVM running for 113.26)
2022-08-09 11:54:08.188  INFO 6976 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2022-08-09 11:54:08.189  INFO 6976 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-08-09 11:54:08.191  INFO 6976 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms

```

## How to access the application

* Once the application started successfully, you can access the application by launching the below url in the browser:

```
	http://localhost:8080/shop/swagger-ui/
	
		(or)
		
	http://localhost:<PORT>/shop/swagger-ui/
```

## **How to access the code coverage report**

Please follow the below-mentioned steps to access code coverage report.

1. Clone the repository ```https://github.com/<Your Repo Location>```
2. Open CMD or Git Bash Terminal and run the below command
    - `mvnw clean verify`
3. After successful build, you can find the report in below path
    - $buildDir/target/site/jacoco/index.html

****