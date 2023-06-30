package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
public class DeleteOneProduct {
	
	String baseURI;
	SoftAssert softAssert;
	HashMap<String,String> deletePayload;
	
	public DeleteOneProduct() {
		baseURI="https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
		deletePayload = new HashMap<String,String>();
		
	}
	
	public Map<String,String> deletePayloadMap(){
		deletePayload.put("id", "8312");
		
		
		return deletePayload; 
	}
	
	@Test(priority=1)
	public void deleteOneProduct() {
		
		Response response =
				
		given()
//		.baseUri("https://techfios.com/api-prod/api/product/delete.php")
		.baseUri(baseURI)
		.header("Content-Type", "application/json; charset=UTF-8")
		.header("Authorization","Bearer gjktyureitopab")
		.body(deletePayloadMap()).
		
//		.log().all().
	when()
//	    .log().all()
		.delete("/delete.php").
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
		softAssert.assertEquals(responseHeaderContentType,"application/json; charset=UTF-8" );
	
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		String productMessage = jp.get("message");
		System.out.println("Product Message:"+ productMessage);
//		Assert.assertEquals(productName, "Techfios Student Ruby" );
		softAssert.assertEquals(productMessage, "Product was deleted." );
		
		
		softAssert.assertAll();
		
		}
	@Test(priority=2)
	public void readOneProducts() {
		
		String deleteProductId=deletePayloadMap().get("id");
		
		Response response =
		given()
		.baseUri("https://techfios.com/api-prod/api/product/read_one.php")
		.header("Content-Type", "application/json")
		.header("Authorization","Bearer gjktyureitopab")
		.queryParam("id", deleteProductId).
	when()
		.get("/read_one.php").
		
	then()
	    .extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
//		Assert.assertEquals(statusCode, 200);
		softAssert.assertEquals(statusCode, 404 );
				
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		String productMessage = jp.get("message");
		System.out.println("Product Message:" +  productMessage );
//		Assert.assertEquals(productName, "Techfios Student Ruby" );
		softAssert.assertEquals( productMessage , "Product does not exist.", "product  not matching" );
		
		softAssert.assertAll();
		
		}
		
}

	


	

