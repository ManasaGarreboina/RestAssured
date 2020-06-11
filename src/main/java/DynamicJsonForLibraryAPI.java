import FilesForBasicRestAssured.Payload;
import FilesForBasicRestAssured.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DynamicJsonForLibraryAPI {


    String id;
    @Test(dataProvider = "BooksData")

    public void addBook(String isbn,String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(Payload.AddBook(isbn, aisle))
                .when()
                .post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = ReUsableMethods.rawToJson(response);
        id = js.get("ID");
        System.out.println(id);
        //delete
        String deleteResponse = given().header("Content-Type","application/json")
                .body(Payload.deleteBook(id))
                .when()
                .delete("/Library/DeleteBook.php")
                .then().log().all().statusCode(200).extract().response().asString();
        System.out.println(deleteResponse);
        System.out.println(id);

    //    return id;
    }
    @DataProvider(name="BooksData")
    public  Object[][] getData(){
        return new Object[][] {{"pfi","823"},{"sfs","943"},{"hfve","227"}};
    }
    /*
    //delete Book
    @Test(dataProvider = "DeleteBooks")
    public void deleteBook() {

        RestAssured.baseURI = "http://216.10.245.166";
        String deleteResponse = given().header("Content-Type","application/json")
                .body(Payload.deleteBook(id))
                .when()
                .delete("/Library/DeleteBook.php")
                .then().log().all().statusCode(200).extract().response().asString();
        System.out.println(deleteResponse);
        System.out.println(id);


    }

    @DataProvider(name="DeleteBooks")
    public Object[] delData(){
        return new Object[]{id};
    }
    */


}
