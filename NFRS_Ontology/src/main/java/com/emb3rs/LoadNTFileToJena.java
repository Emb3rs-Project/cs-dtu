package com.emb3rs;

import org.apache.jena.atlas.web.HttpException;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadNTFileToJena {

    public static void main(String[] args) {
        // Replace with your Fuseki server URL and dataset name
        String fusekiServerURL = "http://localhost:3030";
        String datasetName = "emb3rs";

        // Load the output.nt file into a Jena Model
        Model model = ModelFactory.createDefaultModel();
        File inputFile = new File("src/resources/data.nt");
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            RDFDataMgr.read(model, inputStream, null, RDFDataMgr.defaultSerializationNTriples);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputFile.getAbsolutePath());
            return;
        } catch (IOException e) {
            System.err.println("Error reading file: " + inputFile.getAbsolutePath());
            return;
        }

        // Connect to the Fuseki server and load the model into the specified dataset
        String datasetURL = fusekiServerURL + "/" + datasetName + "/data";
        DatasetAccessor datasetAccessor = DatasetAccessorFactory.createHTTP(datasetURL);
        try {
            datasetAccessor.putModel(model);
            System.out.println("Model successfully loaded into the Fuseki dataset: " + datasetName);
        } catch (HttpException e) {
            System.err.println("Error loading model into Fuseki: " + e.getMessage());
        }
    }
}
