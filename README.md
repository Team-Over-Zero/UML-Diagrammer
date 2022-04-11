# UML-Diagrammer
## Functionality
A basic drawing program to create basic UML Diagrams.

This includes functionality for: 
 - Viewing and editing drawing in accordance with UML 2.5.
 - Login for users to create and save drawing.
 - Saving diagrams into directories based on user.
 - Support for future collaborative editing.
 - Exporting diagrams in PNG and SVG formats.
 - Functionality for Web and Desktop UI.

## Installation
To use the project:

First make sure that you have gradle installed on your machine

1. Clone the github repository to a directory on your machine
2. Navigate to the directory and run `gradle build` to ensure it builds
3. Launch the backend sever to communicate with the database with `gradle webserver:run`
4. To launch the app use `gradle app:run` while the server is running in a different window/terminal.
4b. To use the web app enter `http://localhost:7777/` into your chosen browser, the sever needs to be running for this to work(step 3). 

## Usage
There are two main applications in the project currently, the first is a JavaFX client, and the other is a Vue webserver.
First be sure to have the server running with `gradle webserver:run`

To run the JavaFX, run the command: `gradle app:run` while the aforementioned server is running

To run the webserver, navigate to: `http://localhost:7777/` in your preferred webbrowser

## Credit
This project is created by:
 - Alex Diviney
 - David Hellwig
 - Ekow Barlow
 - Michael Collier
 - Show Pratoomratana

This was created for a project in CS 3321: Intro to Software Engineering with Issac Griffith
