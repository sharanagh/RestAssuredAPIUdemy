package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resource.APIResources;
import resource.TestDataBuild;
import resource.Utils;

public class StepDefination extends Utils{
	RequestSpecification res;
	ResponseSpecification responseSpec;
	Response response;
	static String place_id;
	TestDataBuild data = new TestDataBuild();
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		res= given().spec(requestSpecification()).
			body(data.addPlacePayLoad(name, language, address));
	}
	@When("user calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource, String method) {
	    // Write code here that turns the phrase above into concrete actions
		// Constructor will be called with the value of resources which you pass
		APIResources resourceAPI=	APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		responseSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if(method.equalsIgnoreCase("POST")) 
			response=res.when().
					post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response=res.when().
				 	get(resourceAPI.getResource());
			
		
	}
	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(response.getStatusCode(),200);
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(getJsonPath(response, keyValue), expectedValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    // requestSpec
		place_id= getJsonPath(response, "place_id");
		res= given().spec(requestSpecification()).queryParam("place_id", place_id);
		
		user_calls_with_post_http_request(resource, "GET");
		String actualName= getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
	    
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	   res= given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}
}