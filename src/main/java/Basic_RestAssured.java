import FilesForBasicRestAssured.Payload;
import FilesForBasicRestAssured.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Basic_RestAssured {
    public static void main(String[] args) {
        //Add place
        RestAssured.baseURI ="https://rahulshettyacademy.com";
        String response=given().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body(Payload.AddPlace())//retrieving  json as a string from AddPlace method
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();//Extracting response as a String
        System.out.println(response);
        JsonPath js =new JsonPath(response);//for parsing json
        String placeId =js.getString("place_id");
        System.out.println(placeId);


        String newAddress="peoples plaza,India";

        //Update place
        given().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n" +
                        "\n" +
                        "\n")
                .when().put("/maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));

        // Get Place
        String getPlaceResponse=given().queryParam("key", "qaclick123")
                .queryParam("place_id",placeId)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js_get= ReUsableMethods.rawToJson(getPlaceResponse);// calling reusable class for avoiding redundancy's
        String actualAddress=js_get.getString("address");

        Assert.assertEquals(actualAddress,newAddress);//testNg assertion

        //Delete

        String deleteResponse=given().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "    \"place_id\":\""+placeId+"\"\n" +
                        "}\n")
                .when().delete("/maps/api/place/delete/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(deleteResponse);



    }
}
