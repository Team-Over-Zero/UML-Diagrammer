# Changelog

## [1.0.0] - 2022-04-24
### Added
 - User login with username and password for both database and UI.
 - Sharing documents with other users by adding someone to a page.
 - Minor UI tweaks to improve user experience.
 - UIUser and UIPage objects for creation of the UI.
 - Loading a document that was previously worked on.
 - User registration with a new username and password.
 - More tests for the backend, webserver, and UI.
 - Minor refactoring of the factory classes to work with ActiveJDBC.
 - Full connection of app UI and web UI to communicate with the backend to allow for saving, loading, and sharing of pages.
 - Unhappy path testing.

## [0.3.0] - 2022-04-10
### Added
 - Refactored database backend with working post, put, and get requests 
   - Added Database_Client and RequestController. Database_Client is a javalin
   server that listens to get and post requests and initiates and manages database connections 
   and allows frontends to save and load data from our hardcoded project database. RequestController
   is a helper class that does the grunt work of the listener logic. These will soon
   be spun out into their own package (or used by a distinct service) as this pair of classes creates 
   a distinct microservice. 
   - Added HTTP_Client. This class essentially wraps the HttpClient apache library and creates specific
   methods that our frontends can use for their purposes. This includes object instantiation, fetch, and 
   deletion, and will eventually including object updating request as well. Frontends can call methods like
   - "createNodeOnPage()" and send in their page ids and node json strings as parameters and HTTP_Client
   will deal with the connections and return a response string to the fronted for it to process.
   
 - JavaFx now has UI edge connections, updated SVGs, editable nodes, moving edges

 - Various tests for the backend, sever, and UI were implemented. Initial testing of microservice 
connections was done, and javafx now has UI tests. 

 - Wiki documentation refactored to reflect system.
   - Class Diagram Updated
   - Wiki Updated

 - Refactored web app to use a different library
 - Reworked Objects to have a frontend "UI" Copy. Since we do not want to instantiate ActiveJDBC objects
on the frontend, we store details of nodes and edges in UI objects that hold data and have json functions.
For every Object in our backend, there is now a UI copy of it that doesn't implement ActiveJDBC. 
 - Added A multitude of backend helper objects
   - Created CustomJsonDeserializer. This class helps with the parsing and hydration of json objects
   and is heavily used by RequestController. 
   - Created TypeRegistry, EdgeTypeDeserializer, and NodeTypeDeserializer. These are not really used at 
   the moment but if refactored could be a different way to approach deserialization.

## [0.2.0] - 2022-03-21
### Added
 - Working UI with object interation for both web and app

 - High quality SVGs for diffrent UML shapes

 - An working AWS database to save objects

 - 70% backend and Javafx testing

 - Updates to backend object instation(factory)

 - Use cases for UI 
## [0.1.0] - 2022-03-06
### Added 
-Basic JavaFX class

-Basic Vue webserver with a Javalin backend

-Various backend objects needed to store information about a UML diagram

-Factory Classes for the creation of said objects

-Observer Pattern

-Proof of concept HTTP_Client

-Testing with Jacoco and JUNIT

-Class Diagram and basic use cases
