package com.emb3rs;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.OWLFBRuleReasonerFactory;
import org.apache.jena.reasoner.InfModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.QuerySolution;

public class JenaInference {
    public static void main(String[] args) {
        // Create an empty ontology model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        // Read the ontology into the model
        model.read("file:src/resources/ontology.owl");

        // Create an inference model using Jena's OWL reasoner
        Reasoner reasoner = OWLFBRuleReasonerFactory.theInstance().create(null);
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        // Perform the inference
        infModel.prepare();

        // Query the inferred triples
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX ef: <http://www.semanticweb.org/robertomonaco/ontologies/2021/5/Energy_Framework#> " +
                "SELECT ?x WHERE {?x rdf:type ef:Markets}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, infModel);
        ResultSet results = qe.execSelect();

        // Print out the results
        while (results.hasNext()) {
            QuerySolution solution = results.next();
            System.out.println(solution.get("x"));
        }

        qe.close();
    }
}
