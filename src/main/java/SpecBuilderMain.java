
import FilesForBasicRestAssured.Payload;
import FilesForBasicRestAssured.ReUsableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.Assert;



import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SpecBuilderMain 
{
	public static void main( String[] args )
	{
		//SpecBUILD
		RequestSpecification reqSpec =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();

		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		//Add place
		// RestAssured.baseURI ="https://rahulshettyacademy.com";
		RequestSpecification add_Spec_response=given().spec(reqSpec)
				.body(Payload.AddPlace());

		Response add_response= add_Spec_response.when().post("/maps/api/place/add/json")
				.then().spec(resSpec).extract().response();



		System.out.println(add_response.asString());

		JsonPath js =new JsonPath(add_response.asString());//for parsing json
		String placeId =js.getString("place_id");
		//System.out.println(placeId);


		String newAddress="peoples plaza,India";

		//Update place


		RequestSpecification put_Spec_response=given().spec(reqSpec)
				.body("{\n" +
						"\"place_id\":\""+placeId+"\",\n" +
						"\"address\":\""+newAddress+"\",\n" +
						"\"key\":\"qaclick123\"\n" +
						"}\n" +
						"\n" +
						"\n");
		Response put_response= put_Spec_response.when().put("/maps/api/place/update/json")
				.then().spec(resSpec).body("msg",equalTo("Address successfully updated")).extract().response();
		

		System.out.println(put_response.asString());

		// Get Place
		
		RequestSpecification get_Spec_response=given().spec(reqSpec).queryParam("place_id",placeId);

		Response get_response= get_Spec_response.when().get("/maps/api/place/get/json")
				.then().spec(resSpec).extract().response();
		System.out.println(get_response.asString());

		JsonPath js_get= ReUsableMethods.rawToJson(get_response.asString());// calling reusable class for avoiding redundancy's
		String actualAddress=js_get.getString("address");

		Assert.assertEquals(actualAddress,newAddress);//testNg assertion


		//Delete
		
		RequestSpecification del_Spec_response=given().spec(reqSpec)
				.body("{\n" +
						"    \"place_id\":\""+placeId+"\"\n" +
						"}\n");
		Response del_response=  del_Spec_response.when().delete("/maps/api/place/delete/json")
				.then().spec(resSpec).extract().response();
		System.out.println(del_response.asString());

	}
}
