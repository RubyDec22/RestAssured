package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
public class UpdateOneProduct {
	
	String baseURI;
	SoftAssert softAssert;
	String updatePayloadFilePath;
	
	public UpdateOneProduct() {
		baseURI="https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
		updatePayloadFilePath="src\\main\\java\\data\\UpdatePayload.json";
	}
	
	
	@Test
	public void updateOneProducts() {
		
		Response response =
				
		given()
//		.baseUri("https://techfios.com/api-prod/api/product/update.php")
		.baseUri(baseURI)
		.header("Content-Type", "application/json; charset=UTF-8")
		.header("Authorization","Bearer gjktyureitopab")
		.body(new File(updatePayloadFilePath)).
		
//		.log().all().
	when()
//	    .log().all()
		.put("/update.php").
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
		softAssert.assertEquals(productMessage, "Product was updated." );
		
		
		softAssert.assertAll();
		
		}
	@Test(priority=3)
	public void readOneProducts() {
		JsonPath jp2 = new JsonPath(new File(updatePayloadFilePath));
		String updateProductId=jp2.get("id");
		
		Response response =
		given()
//		.baseUri("https://techfios.com/api-prod/api/product/read_one.php")
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.header("Authorization","Bearer gjktyureitopab")
		.queryParam("id", updateProductId).
	when()
		.get("/read_one.php").
		
	then()
	    .extract().response();
		
//		JsonPath jp2 = new JsonPath(new File(createPayloadFilePath));
		String expectedProductName = jp2.get("name");
		String expectedProductDescription = jp2.get("description");
		String expectedProductPrice =jp2.get ("price");
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		String updatedProductName = jp.get("name");
		System.out.println("Actual Product Name:" + updatedProductName);
//		Assert.assertEquals(productName, "Techfios Student Ruby" );
		softAssert.assertEquals(updatedProductName, expectedProductName, "product name not matching" );
		
		String updatedProductDescription = jp.get("description");
		System.out.println("updated Product Description:" + updatedProductDescription);
//		Assert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		softAssert.assertEquals(updatedProductDescription, expectedProductDescription, "product description not matching!");
		
		
		
		String updatedProductPrice = jp.get("price");
//		Assert.assertEquals(productPrice, "27");
		System.out.println("updated Product price:"+ updatedProductPrice);
		softAssert.assertEquals(updatedProductPrice, expectedProductPrice, "product prices not matching!");
		
		softAssert.assertAll();
		
		}
		
}

	


	

