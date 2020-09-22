package org.sid.democlientrest.test;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ClientRest {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		ClientConfig config = new DefaultClientConfig();
		Client client =Client.create();
		URI uri=UriBuilder.fromUri("http://localhost:8080").build();
		WebResource service= client.resource(uri);
		WebResource resource=service.path("comptes");

		/*
		 * Ajouter un compte
		 * POST http://localhost:8080/comptes
		 */
		Compte cp =new Compte();
		cp.solde=8888;
		cp.dateCreation=new Date();
		cp.type="courant";
		ObjectMapper mapper=new ObjectMapper();
		ClientResponse resp1 =resource.accept("application/json").type("application/json").post(ClientResponse.class,mapper.writeValueAsString(cp));
		System.out.println("==================Ajout d'un compte===================");
		System.out.println("Status Code: "+resp1.getStatus());
		String corpResponse =resp1.getEntity(String.class);
		System.out.println("Corp Réponse: "+corpResponse);
		Compte cp1=mapper.readValue(corpResponse, Compte.class);
		System.out.println("Code= "+ cp1.code);
		System.out.println("Solde= "+ cp1.solde);
		System.out.println("Date Création= "+ cp1.dateCreation);
		System.out.println("Type= "+ cp1.type);
		System.out.println("======================================================");
		System.out.println("==================Affichage de tous les comptes===================");
		/*
		 * Afficher tous les comptes
		 * GET http://localhost:8080/comptes
		 */
		String resp2=resource
				.get(String.class);
		System.out.println(resp2);
		Compte[] cptes=mapper.readValue(resp2, Compte[].class);
		for(Compte c:cptes) {
			System.out.println("***********************");
			System.out.println("Code=" + c.code);
			System.out.println("Solde=" + c.solde);
			System.out.println("Date Création=" + c.dateCreation);
			System.out.println("Type=" + c.type);
		}
		System.out.println("======================================================");
		System.out.println("==================Consulter un compte===================");
		/*
		 * Consulter un compte
		 * GET http://localhost:8080/comptes/4
		 */
		String resp3=resource.path("/4").get(String.class);
		System.out.println(resp3);
		Compte compte=mapper.readValue(resp3, Compte.class);
		System.out.println("***********************");
		System.out.println("Code="+compte.code);
		System.out.println("Solde="+compte.solde);
		System.out.println("Date Création="+compte.dateCreation);
		System.out.println("Type=" + compte.type);
		System.out.println("======================================================");
		System.out.println("==================Suprimer un compte===================");
		/*
		 * Suprimer un compte
		 * DELETE http://localhost:8080/comptes/1
		 */
		ClientResponse resp4 =resource.path("/1").accept("application/json").type("application/json").delete(ClientResponse.class);
		System.out.println("Compte avec le code 1 a été suprimer");
		System.out.println("======================================================");
		System.out.println("==================Modifier un compte===================");
		/*
		 * modifier un compte
		 * PUT http://localhost:8080/comptes/2
		 */
		Compte cp2 =new Compte();
		cp2.solde=2222;
		cp2.dateCreation=new Date();
		cp2.type="epargne";
		ClientResponse resp5 =resource.path("/2").accept("application/json").type("application/json").put(ClientResponse.class,mapper.writeValueAsString(cp2));
		System.out.println("Compte avec le code 2 a été modifier");
	}

}
