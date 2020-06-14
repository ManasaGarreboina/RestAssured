package restAssured;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import FilesForBasicRestAssured.Payload;
import FilesForBasicRestAssured.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ExcelDDD {


	@Test
	public void addBook() throws IOException {
		DataDriven data= new DataDriven();
		ArrayList exdata=data.getData("Sheet1","TestCases","RestAddApi");

 
		HashMap<String,Object> hm=new HashMap<>();
		hm.put("name",exdata.get(1));
		hm.put("isbn", exdata.get(2));
		hm.put("aisle", exdata.get(3));
		hm.put("author",exdata.get(4));
		
		
		RestAssured.baseURI = "http://216.10.245.166";
		Response response = given().header("Content-Type", "application/json")
				.body(hm)
				.when()
				.post("Library/Addbook.php")
				.then().assertThat().statusCode(200)
				.extract().response();
		JsonPath js = ReUsableMethods.rawToJson1(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	


}
