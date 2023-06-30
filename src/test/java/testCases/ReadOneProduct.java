package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
public class ReadOneProduct {
	
	String baseURI;
	SoftAssert softAssert;
	
	public ReadOneProduct() {
		baseURI="https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
	}
	
	
	@Test
	public void readOneProducts() {
		/*
		given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
		when:  submit api requests(Http method,Endpoint/Resource)
		then:  validate response(status code, Headers, responseTime, Payload/Body)
		02.ReadOneProduct
HTTP Method: GET
EndpointUrl: https://techfios.com/api-prod/api/product/read_one.php
Authorization:
Basic Auth/ Bearer Token
Header:
"Content-Type" : "application/json"
QueryParam: 
"id":"8299"
Status Code: 200

		*/
		Response response =
				
		given()
//		.baseUri("https://techfios.com/api-prod/api/product/read_one.php")
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.header("Authorization","Bearer gjktyureitopab")
		.queryParam("id", "8299").
		
//		.log().all().
	when()
//	    .log().all()
		.get("/read_one.php").
	then()
	    .extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
//		Assert.assertEquals(statusCode, 200);
		softAssert.assertEquals(statusCode, 200 );
		
		long responseTime = response.getTime();
		System.out.println("Response time:" + responseTime);
		if(responseTime <=2000) {
			System.out.println("Response time is within range.");
		}else {
			System.out.println("Response time is out of range!");
		}
		
		String responseHeaderContentType = response.getHeader("Content-Type");
		System.out.println("Response Header Content-Type:"+ responseHeaderContentType);
//		Assert.assertEquals(responseHeaderContentType,"application/json" );
		softAssert.assertEquals(responseHeaderContentType,"application/json" );
		/*{
    "id": "8299",
    "name": "Techfios Student Ruby",
    "description": "The best pillow for amazing programmers.",
    "price": "13",
    "category_id": "2",
    "category_name": "Electronics"
}
*/
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		String productName = jp.get("name");
		System.out.println("Product Name:"+ productName);
//		Assert.assertEquals(productName, "Techfios Student Ruby" );
		softAssert.assertEquals(productName, "Techfios Student Ruby" );
		
		String productDescription = jp.get("description");
		System.out.println("Product Description:" + productDescription);
//		Assert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		softAssert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		
		
		
		String productPrice = jp.get("price");
//		System.out.println("Product price:"+ productPrice);
//		Assert.assertEquals(productPrice, "13");
		softAssert.assertEquals(productPrice, "13");
		System.out.println("Product price:"+ productPrice);
		
		
		softAssert.assertAll();
		
		}
		
}

	

