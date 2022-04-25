# Introduction
This app uses the Java Database Connection API to connect to a query a PostgreSQL database filled with customer and order data for a simulated beverage company. The dependencies required for the project as well as the actual building of the project itself are managed using Apache Maven. In addition, DBeaver was used to analyze the structure of the tables within the database and to genereate Entity Relationship diagrams. A Docker image made to quickly spin up a container running PostgreSQL was used so that we could also query the database directly from the command line and store the information on a persistent volume. So far the application can only perform simple CRUD (Create, Read, Update, and Delete) operations on the customers stored in the database and show information about specific orders (such as who ordered the drinks, who sold it to them, the total price, etc.).

# Implementaiton
## ER Diagram
ER diagram

## Design Patterns
The Data Access Object pattern provides an abstraction of data persistence and allows us to separate the actual accessing of information from the "business logic" of the rest of the application. The Repository pattern has a similar goal, but it functions primarily as an abstraction of a collection rather than as an abstraction of the data store and as such is more closely related to the business logic of the application. Here, interacting with data from the data store is much like interacting with a collection of objects. 

# Test
How you test your app against the database? (e.g. database setup, test data set up, query result)
The database exists within a Docker container running a PostgreSQL image. The data that populates the database was added via several SQL files for each table. `psql` was used to get the expected results of queries which were then manually compared to the output of the JDBCExecutor class that was tasked creating all the DAOs and DTOs required to test the application's functionality as well as execute the CRUD methods themselves. 
