import FilesForBasicRestAssured.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(Payload.CoursePrice());
        //Count of courses
        int count =js.getInt("courses.size()");
        System.out.println(count);
        System.out.println("--------------------");
        //print purchase ammount
        int Ammount= js.getInt("dashboard.purchaseAmount");
        System.out.println(Ammount);
        System.out.println("--------------------");
        //printing title of course
        String titleFirstCourse=js.get("courses[0].title");
        System.out.println(titleFirstCourse);
        System.out.println("--------------------");
        //courses titles with respective prices
        for(int i=0;i<count;i++){
            String courseTitles=js.get("courses["+i+"].title");
            System.out.println(courseTitles);
            System.out.println(js.get("courses["+i+"].price").toString());

        }
        //copies sold by a particular course
        System.out.println("--------------------");
        for(int i=0;i<count;i++) {
            String courseTitles = js.get("courses[" + i + "].title");
            if(courseTitles.equalsIgnoreCase("RPA")){
                int copies= js.get("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }


    }
}
