package com.emb3rs;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class NTripleDataGenerator {

    public static void main(String[] args) {
        String ontologyFilePath = "src/resources/ontology.owl";
        String outputFilePath = "src/resources/data.nt";

        Model model = ModelFactory.createDefaultModel();
        model.read(FileManager.get().open(ontologyFilePath), null, "RDF/XML");

        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);

        generateNTripleData(ontModel, outputFilePath);
    }

    private static void generateNTripleData(OntModel ontModel, String outputFilePath) {
        // Generate N-triple data based on the ontology model

        Random random = new Random();

        // Iterate through the ontology classes and create instances
        for (OntClass ontClass : ontModel.listClasses().toList()) {
            if (!ontClass.isAnon()) {
                // Create a number of instances for each class
                int instanceCount = random.nextInt(10) + 1;
                for (int i = 0; i < instanceCount; i++) {
                    String instanceUri = ontClass.getURI() + "instance" + i;
                    ontModel.createIndividual(instanceUri, ontClass);
                }
            }
        }
    // Save the generated data to a file
    saveNTripleDataToFile(ontModel, outputFilePath);
    }

private static void saveNTripleDataToFile(Model model, String outputFilePath) {
    try (OutputStream outputStream = new FileOutputStream(new File(outputFilePath))) {
        model.write(outputStream, "N-TRIPLE");
        System.out.println("N-triple data has been generated and saved to " + outputFilePath);
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Failed to save N-triple data to file.");
    }
}
