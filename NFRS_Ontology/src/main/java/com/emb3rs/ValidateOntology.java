package com.emb3rs;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ValidateOntology {

    public static void main(String[] args) {
        // Create an ontology model
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        // Read the ontology file
        File ontologyFile = new File("src/resources/ontology.owl");
        try (FileInputStream ontologyInputStream = new FileInputStream(ontologyFile)) {
            ontModel.read(ontologyInputStream, null, "RDF/XML");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + ontologyFile.getAbsolutePath());
            return;
        } catch (IOException e) {
            System.err.println("Error reading file: " + ontologyFile.getAbsolutePath());
            return;
        } catch (JenaException e) {
            System.err.println("Error parsing ontology: " + e.getMessage());
            return;
        }

        // Create a data model
        Model dataModel = ModelFactory.createDefaultModel();

        // Read the data file
        File dataFile = new File("src/resources/data.nt");
        try (FileInputStream dataInputStream = new FileInputStream(dataFile)) {
            dataModel.read(dataInputStream, null, "RDF/XML");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + dataFile.getAbsolutePath());
            return;
        } catch (IOException e) {
            System.err.println("Error reading file: " + dataFile.getAbsolutePath());
            return;
        } catch (JenaException e) {
            System.err.println("Error parsing data: " + e.getMessage());
            return;
        }

        // Bind the data model to the ontology
        ontModel.addSubModel(dataModel);

        // Validate the data against the ontology
        OntModelSpec.ValidityReport report = ontModel.validate();
        if (report.isValid()) {
            System.out.println("Data is valid according to the ontology.");
        } else {
            System.out.println("Data is not valid according to the ontology:");
            for (ExtendedIterator<ValidityReport.Report> i = report.getReports(); i.hasNext(); ) {
                System.out.println(" - " + i.next());
            }
        }
    }
}
