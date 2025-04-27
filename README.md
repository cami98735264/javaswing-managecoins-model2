# Java Swing - Manage Coins (CRUD) Application

A Java Swing application for managing coin collections with full CRUD (Create, Read, Update, Delete) functionality.

## Project Structure

```
database_conexion/
├── src/
│   └── itfip/
│       └── edu/
│           ├── database_conexion/
│           │   └── Coneccion.java
│           ├── interfazGrafica/
│           │   ├── AdministradorMonedas.java
│           │   ├── AdministrarMoneda.java
│           │   └── ConsultarMoneda.java
│           └── logicaInterfazGrafica/
│               └── LogicaInterfazGrafica.java
├── bin/
├── .settings/
└── .project
```

## Components

### Database Connection
- `Coneccion.java`: Handles database connectivity and operations

### User Interface
- `AdministradorMonedas.java`: Main application window
- `AdministrarMoneda.java`: Form for managing coin entries
- `ConsultarMoneda.java`: Interface for viewing coin information

### Business Logic
- `LogicaInterfazGrafica.java`: Contains the application's business logic

## Features
- Create new coin entries
- Read/View existing coin information
- Update coin details
- Delete coin entries
- Database persistence
- User-friendly Swing interface

## Requirements
- Java JDK 8 or higher
- MySQL Database
- Eclipse IDE (recommended)

## Setup
1. Clone the repository
2. Import the project into Eclipse
3. Configure the database connection in `Coneccion.java`
4. Build and run the project

## Usage
1. Launch the application
2. Use the main interface to navigate between different operations
3. Follow the on-screen instructions for managing coin entries

## Screenshot (GIF) Sample
![](https://github.com/cami98735264/javaswing-managecoins-model2/blob/main/sample-gif2-compressed.gif)
