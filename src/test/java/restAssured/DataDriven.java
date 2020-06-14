package restAssured;


import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {
	public ArrayList<String> getData(String sheetName,String rowName,String colName) throws IOException {

		ArrayList<String> string =new ArrayList<String>();
		// file input argument
		FileInputStream fin =new FileInputStream("C:\\Users\\user\\Desktop\\Book1.xlsx");
		XSSFWorkbook wb= new XSSFWorkbook(fin);
		int count_sheets=wb.getNumberOfSheets();
		for(int i=0;i<count_sheets;i++){
			if(wb.getSheetName(i).equalsIgnoreCase(sheetName)){

				XSSFSheet xs=wb.getSheetAt(i);
				//Identifying TestCases column by scanning the entire first row
				Iterator<Row> rows=xs.iterator();//sheet is collection of rows
				Row firstRow=rows.next();
				Iterator<Cell> cell=firstRow.cellIterator();//row is collection of cells
				int count=0;
				int column = 0;
				while(cell.hasNext()){
					Cell value =cell.next();
					if(value.getStringCellValue().equalsIgnoreCase(rowName)){
						//desired column
						column=count;

					}
					count++;
				}
				System.out.println(column);
				//identifying Purchase row from the entire TestCaseColumn
				while(rows.hasNext()){
					Row r= rows.next();
					if(r.getCell(column).getStringCellValue().equalsIgnoreCase(colName)){
						//Entering into Purchase row and grabing the cell  containing data
						Iterator<Cell> cell_value=r.cellIterator();
						while(cell_value.hasNext()){
							//checking if the cell value is string or not
							Cell c= cell_value.next();
							if(c.getCellTypeEnum()==CellType.STRING){
								string.add(c.getStringCellValue());
							}
							else{
								
								string.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
							
							
						}
					}


				}

			}

		}
		return string;

	}

	public static void main(String[] args) throws IOException {


	}

}
