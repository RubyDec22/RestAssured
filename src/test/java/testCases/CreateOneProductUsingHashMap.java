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
public class CreateOneProductUsingHashMap {
	
	String baseURI;
	SoftAssert softAssert;
	String firstProductId;
	String createPayloadFilePath;
	HashMap<String,String> createPayload;
	
	public CreateOneProductUsingHashMap() {
		
		baseURI="https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
		createPayloadFilePath="src\\main\\java\\data\\CreatePayload.json";
		createPayload = new HashMap<String,String>();
	}
	
		
		public Map<String,String> createPayloadMap(){
			createPayload.put("name", "Ruby Kwatra Techfios Dec22QA stu");
			createPayload.put("price", "27");
			createPayload.put("description", "learning API/Postman");
			createPayload.put("category_id", "2");
			createPayload.put("category_name", "Electronics");
			
			return createPayload; 
			
		}
	
		@Test(priority=1)
		public void createOneProduct() {
		Response response =
				
		given()
//		.baseUri("https://techfios.com/api-prod/api/product/create.php")
		.baseUri(baseURI)
		.header("Content-Type", "application/json; charset=UTF-8")
		.header("Authorization","Bearer gjktyureitopab")
		.body(createPayloadMap()).
		
//		.log().all().
	when()
//	    .log().all()
		.post("/create.php").
	then()
	    .extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
//		Assert.assertEquals(statusCode, 200);
		softAssert.assertEquals(statusCode, 201);
		
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
		softAssert.assertEquals(responseHeaderContentType, "application/json; charset=UTF-8");
		/* responseBody
{
    "message": "Product was created."
}

*/
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		String productMessage = jp.get("message");
		System.out.println("Product Message:" + productMessage);
//		Assert.assertEquals(productName, "Techfios Student Ruby" );
		softAssert.assertEquals(productMessage, "Product was created." );
	
		softAssert.assertAll();
		
		}
		
	@Test(priority=2)
	public void readAllProducts() {
		
		Response response =
				
		given()
		.baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type", "application/json; charset=UTF-8")
		.auth().preemptive().basic("demo@techfios.com", "558566").
//		.log().all().
	when()
//	    .log().all()
		.get("/read.php").
	then()
	    .extract().response();
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		firstProductId = jp.get("records[0].id");
		System.out.println("First Product Id:" + firstProductId);
		
		
		}
	@Test(priority=3)
	public void readOneProducts() {
		
		Response response =
		given()
		.baseUri("https://techfios.com/api-prod/api/product/read_one.php")
		.header("Content-Type", "application/json")
		.header("Authorization","Bearer gjktyureitopab")
		.queryParam("id", firstProductId).
	when()
		.get("/read_one.php").
	then()
	    .extract().response();
		
//		JsonPath jp2 = new JsonPath(new File(createPayloadFilePath));
		String expectedProductName = createPayloadMap().get("name");
		String expectedProductDescription = createPayloadMap().get("description");
		String expectedProductPrice = createPayloadMap().get("price");
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		String actualProductName = jp.get("name");
		System.out.println("Actual Product Name:" + actualProductName);
//		Assert.assertEquals(productName, "Techfios Student Ruby" );
		softAssert.assertEquals(actualProductName, expectedProductName, "product name not matching" );
		
		String actualProductDescription = jp.get("description");
		System.out.println("Actual Product Description:" + actualProductDescription);
//		Assert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		softAssert.assertEquals(actualProductDescription, expectedProductDescription, "product description not matching!");
		
		
		
		String actualProductPrice = jp.get("price");
//		Assert.assertEquals(productPrice, "27");
		System.out.println("Actual Product price:"+ actualProductPrice);
		softAssert.assertEquals(actualProductPrice, expectedProductPrice, "product prices not matching!");
		
		softAssert.assertAll();
		
		}
		
}

	


	

	




	

