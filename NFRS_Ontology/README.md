# Ontology supporting the development of industrial energy management systems


This project demonstrates how to use Apache Jena Fuseki Server to work with the ontology that supports the development of industrial energy management systems. The project consists of several parts, including loading data into the Jena Fuseki server, validating an ontology against the data, and querying triples using a Java program.

## Project Description

This project is divided into several parts:

1. Generating N-triples data
2. Installing Jena Fuseki Server
3. Loading data into Jena Fuseki Server
4. Validating the ontology
5. Querying triples using a Java program

## How to Generate N-triples

1. Make sure you have the Java Development Kit (JDK) installed on your system.
2. Compile and run the `NTripleDataGenerator.java` file in the `src/main/java` directory of the project. This program generates N-triple data based on the `ontology.owl` file and saves the output in the `resources` directory as `data.nt`.

## How to Install Jena Fuseki Server

1. Download the latest Jena Fuseki Server release from the [official website](https://jena.apache.org/download/index.cgi).
2. Extract the downloaded archive to a directory of your choice.
3. Open a terminal and navigate to the extracted directory.
4. Run the following command to start the Jena Fuseki Server: `./fuseki-server`

## How to Load Data into Jena Fuseki Server

1. Access the Jena Fuseki web interface by opening a browser and navigating to `http://localhost:3030`.
2. Click on "Manage Datasets" and then on "Add new dataset".
3. Enter the dataset name (e.g., `emb3rs`) and click on "Create Dataset".
4. Click on the "Upload data" button, then choose and upload the `data.nt` file (under src/resources directory).

## How to Validate the Ontology

1. Make sure you have the Java Development Kit (JDK) installed on your system.
2. Compile and run the `OntologyValidation.java` file in the `src/main/java` directory of the project. This program reads the ontology and data files, validates the data against the ontology, and prints the results to the console.

## How to Query Triples Using the Java Program

1. Compile and run the `QueryFuseki.java` file in the `src/main/java` directory of the project. This program sends a SPARQL query to the Jena Fuseki Server, retrieves the results, and prints them to the console.

## Usage

1. Install Jena Fuseki Server and load the data into it following the instructions above.
2. Compile and run the `OntologyValidation.java` program to validate the ontology.
3. Compile and run the `QueryFuseki.java` program to query the triples from Jena Fuseki Server.

## License

This project is licensed under the terms of the Apache License.

