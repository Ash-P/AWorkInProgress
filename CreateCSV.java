import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateCSV {
	private static final String FILENAME = "GoogleCalendarEvent.csv";
	private static final String HEADER = "Subject,Start Date,Description\n";
	private static final String SUBJECT = "Face-In-Book Reading Target";
	private static final DateFormat UK_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateFormat US_DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
	
	public static void createCSV(storage.targetData targetObj) {
		String description = "";
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		if(targetObj.targetType == 1) {
			String bookTitle = MAIN.getBookTitle(targetObj.bookID);
			storage.bookData bookObj = MAIN.getSpecificBook(bookTitle);
			int targetValue = bookObj.pagesRead + targetObj.targetValue; //page to read to
			if(targetValue > bookObj.pages) targetValue = bookObj.pages;
			description = "Have read to page " + targetValue + " of " + bookTitle + ".";
		}
		else if(targetObj.targetType == 2) {
			int targetValue = alldata.userStore.totalPagesRead + targetObj.targetValue;
			description = "Have read " + targetValue + " pages in total.";
		}
		else if(targetObj.targetType == 3) {
			int targetValue = alldata.userStore.totalBooksRead + targetObj.targetValue;
			description = "Have read " + targetValue + " books in total.";
		}
		
		try {
			String formattedDate = US_DATE_FORMAT.format(UK_DATE_FORMAT.parse(targetObj.deadlineDate));
			fileWriter = new FileWriter(FILENAME);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(HEADER);
			bufferedWriter.write(SUBJECT + "," + formattedDate + "," + description);
			bufferedWriter.close();
		} catch (ParseException e) {
		} catch(IOException e) {
		}
	}
	
	/*
	public static void main(String[] args)  {
		storage.targetData test = new storage.targetData();
		test.targetType = 3;
		test.deadlineDate = "11-07-2019";
		test.targetValue = 5;
		CreateCSV.createCSV(test);
	}
	*/
	
}
