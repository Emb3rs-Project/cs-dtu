package com.emb3rs;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.QuerySolution;

public class QueryFuseki {

    public static void main(String[] args) {
        // Define the Fuseki server URL and dataset name
        String fusekiServerUrl = "http://localhost:3030"; // Replace with your Fuseki server URL
        String datasetName = "emb3rs";
        String serviceUri = fusekiServerUrl + "/" + datasetName + "/query";

        // Define the SPARQL query
        String queryString = "PREFIX en: <http://www.semanticweb.org/robertomonaco/ontologies/2021/5/Energy_Framework#> "
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
                + "SELECT ?marketActor "
                + "WHERE { "
                + "?electricMarket en:hasMarketActor ?marketActor . "
                + "?electricMarket rdf:type en:Electricity_market . "
                + "?marketActor en:marketActorRole \"Generator\"^^xsd:string "
                + "}";

        // Execute the query
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceUri, query);
        ResultSet results = qe.execSelect();

        // Print the results
        while (results.hasNext()) {
            QuerySolution solution = results.next();
            System.out.println("Market Actor: " + solution.get("marketActor"));
        }

        // Close the QueryExecution
        qe.close();
    }
}