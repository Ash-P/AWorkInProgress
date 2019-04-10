


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateCSV {
	public static Map<Date, Integer> target = new HashMap<>();
	
	
	
	public static void createPagesCSV(storage.targetData targetObj) {
		String title = "Reading target";
		String bookTitle = "";
		
		for(storage.bookData item: alldata.bookStore) {
			if(targetObj.bookID == item.bookID) {
				bookTitle = item.title;
			}
		}
		
		storage.bookData book = MAIN.getSpecificBook(bookTitle);
		int readToPage;
		
		if(Integer.parseInt(book.pagesRead+targetObj.targetValue) > book.pages){
			readToPage = book.pages;
		}else{
			readToPage = Integer.parseInt(book.pagesRead) + targetObj.targetValue;
		}
		
		
		BufferedWriter writer = null;
		FileWriter fw = null;
		
		try {
			fw = new FileWriter("target.csv");
			writer = new BufferedWriter(fw);
			writer.write("Subject,Start Date,Description\n");
			writer.write(bookTitle + "," + targetObj.deadlineDate + ", Read to page " + readToPage + " of " + book.title);
			writer.close();
		} catch(IOException e) {
			System.out.println("fail");
		}
		
		
		
	} 
	public static void createBooksCSV(storage.targetData targetObj) {
		
		String title = "Reading target";
		String bookTitle = "";
		
		BufferedWriter writer = null;
		FileWriter fw = null;
		
		int val = alldata.userStore.totalBooksRead + targetObj.targetValue;
		
		for(storage.bookData item: alldata.bookStore) {
			if(targetObj.bookID == item.bookID) {
				bookTitle = item.title;
			}
		}
		
		try {
			
			fw = new FileWriter("target.csv");
			writer = new BufferedWriter(fw);
			writer.write("Subject,Start Date,Description\n");
			writer.write(bookTitle + "," + targetObj.deadlineDate + ", Have read " + val + "books");
			writer.close();
			
			
		} catch(IOException e) {
			System.out.println("fail");
			
		}
	} 
	
	
	public static void createGeneralPagesCSV(storage.targetData targetObj) {
		String title = "Pages target";
		
		String bookTitle = "";
		BufferedWriter writer = null;
		FileWriter fw = null;
		
		for(storage.bookData item: alldata.bookStore) {
			if(targetObj.bookID == item.bookID) {
				bookTitle = item.title;
			}
		}
		int val = alldata.userStore.totalPagesRead + targetObj.targetValue;
		
		try {
			
			fw = new FileWriter("target.csv");
			writer = new BufferedWriter(fw);
			writer.write("Subject,Start Date,Description\n");
			writer.write(bookTitle + "," + targetObj.deadlineDate + ", Have read " + val + "pages");
			writer.close();
			
			
		} catch(IOException e) {
			System.out.println("fail");
		}
		
		
		
	} 
	
	public static void main(String[] args)  {
		storage.targetData test = new storage.targetData();
		
		test.deadlineDate = "05/04/2020";
		test.targetValue = 43;
		//CreateCSV.createCSV(test);
		
		
	}
	
}
