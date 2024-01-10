# README

## Application description
Very simple RESTful API application for a simple online bookstore using Spring and Hibernate. 
The API allows users to perform CRUD operations on books, authors, and genres. 
Books have a title, author, genre, price, and quantity available. 
Users are able to search for books by title, author, or genre. 
Hibernate ORM is used to persist data to a relational database.

## How to run

### 1. Define following environment variables: 

### For db access:
* MY_SQL_DB_HOST (example: //localhost);
* MY_SQL_DB_PORT (example: 3063);
* MY_SQL_DB_USERNAME (example: user);
* MY_SQL_DB_PASSWORD (example: password);

### For SonarQube quality check:
* MAVEN_SONAR_LOGIN (example: user);
* MAVEN_SONAR_PASSWORD (example: password);

### 2. Run *initial_script.sql* file in your database control tool;
### 3. In the source code directory run command *"mvn clean install"* in the command terminal;
### 4. Run the following command to start the Spring Boot application: *java -jar generativeAiTask2-0.0.1-SNAPSHOT.jar*.

## How to use
### Endpoints:
1. GET: localhost:8080/api/v1/books/
* Function: show list of existing books.
* Returns: JSON with list of existing items, Http status 200 (OK). 
2. GET: localhost:8080/api/v1/books/findByAuthorId/
* Path variable: localhost:8080/api/v1/books/findByAuthorId/{id}
* Function: show list of books of the concrete author id.
* Returns: JSON with list of existing items, Http status 200 (OK).
3. GET: localhost:8080/api/v1/books/findByGenreId/{id}
* Path variable: {id}
* Function: show list of books of the concrete genre id.
* Returns: JSON with list of existing items, Http status 200 (OK).
4. GET: localhost:8080/api/v1/books/findByTitle
* Function: return a book with a concrete title.
* Parameters:
  * Title (String);
* Returns: Http status 200 (OK);
5. GET: localhost:8080/api/v1/books/{id}
* Path variable: {id}
* Function: show book with a concrete id.
* Returns: JSON with list of existing items, Http status 200 (OK).
6. POST: localhost:8080/api/v1/items/
* Function: add new book in the table.
* Parameters:
  * title (Varchar 200);
  * authorId (int);
  * genreId (int)
  * price (Decimal);
  * quantity (Int).
* Returns: Http status 201 (CREATED);
7. PUT: localhost:8080/api/v1/books/
   Function: modify existing book in the table.
* Parameters:
  * id (int);
  * newTitle (Varchar 200);
  * newPrice (Decimal);
  * newQuantity (Int).
* Returns: Http status 200 (OK);
8. DELETE: localhost:8080/api/v1/books/{id}
* Path variable: {id}
* Function: delete book with a concrete id.
* Returns: http status 204 (NO_CONTENT).
## Short feedback
* Was it easy to complete the task using AI?
  * A little bit easier than usual.
* How long did task take you to complete?
  * 3 hours.
* Was the code ready to run after generation? What did you have to change to make it usable?
  * Unit tests almost were ready. It was necessary to import new classes.
* Which challenges did you face during completion of the task?
  * How to formulate a good response to ChatGPT.
* Which specific prompts you learned as a good practice to complete the task?
  * Request to writing unit tests and sql script.
