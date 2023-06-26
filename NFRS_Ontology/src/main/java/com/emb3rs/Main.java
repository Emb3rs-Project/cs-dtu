package com.emb3rs;

public class Main {

    public static void main(String[] args) {
        // Call the ValidateOntology class to validate the ontology
        ValidateOntology validateOntology = new ValidateOntology();
        validateOntology.validate();

        // Call the LoadDataToFuseki class to load the data into Fuseki
        LoadDataToFuseki loadDataToFuseki = new LoadDataToFuseki();
        loadDataToFuseki.loadData();

        // Call the QueryInference class to perform inference and query the data
        QueryInference queryInference = new QueryInference();
        queryInference.performInferenceAndQuery();
    }
}