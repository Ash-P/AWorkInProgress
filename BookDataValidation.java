import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class BookDataValidation {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	protected static boolean validBookDataString(String string) {
		if(string.contains("@") || string.contains(";")) {
			return false;
		}
		return true;
	}
	
	protected static boolean validBookDataInteger(String string) {
		if(string.matches("[0]+")) return false;
		else if(string.matches("[0-9]+")) return true;
		return false;
	}
	
	//Validates fields that apply for all new books
	protected static Boolean validateFieldsBasic(String title, int pages, String author, String publisher, int publicationYear, String genre, String description) {
		if(!validBookDataString(title)) return false;
		if(!validBookDataInteger(String.valueOf(pages))) return false;
		
		if(author != null && !validBookDataString(author)) return false;
		if(publisher != null && !validBookDataString(publisher)) return false;
		if(genre != null && !validBookDataString(genre)) return false;
		if(description != null && !validBookDataString(description)) return false;
		if(publicationYear != -1 && !validBookDataInteger(String.valueOf(publicationYear))) return false;
		
		if(title.equals(null)) return false;
		for(storage.bookData b : alldata.bookStore) {
			if(b.title == title) return false;
		}
		if(pages < 1) return false;
		try {
			if(publicationYear != -1 && Year.now().compareTo(Year.parse(String.valueOf(publicationYear))) < 0) return false; //publicationYear cannot be a future year
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
	
	//Validates fields that apply for new 'previously read' books
	protected static Boolean validateFieldsPrevious(int pages, String dateStarted, String dateCompleted, String pagesReadOnADate) {
		try {
			if(SIMPLE_DATE_FORMAT.parse(dateStarted).compareTo(SIMPLE_DATE_FORMAT.parse(dateCompleted)) > 0) {
				System.out.println("Date completed cannot be before date started.");
				return false; //prevent dateStarted being after dateCompleted
			}
		} catch (ParseException e) {
		}
		if(getTotalPagesReadOnDates(pagesReadOnADate) != pages) {
			System.out.println("Manually entered pages read does not match the number of pages of the book.");
			return false;
		}
		System.out.println("Previous fields successfully validated.");
		return true;
	}
	
	//Validates fields that apply for new 'currently reading' books
	protected static Boolean validateFieldsCurrent(int pages, int pagesRead, String pagesReadOnADate) {
		if(pagesRead >= pages) {
			System.out.println("Pages read cannnot be greater than pages.");
			return false;
		}
		if(getTotalPagesReadOnDates(pagesReadOnADate) != pagesRead) {
			System.out.println("Manually entered pages read does not match the entered number of pages read.");
			return false;
		}
		System.out.println("Current fields successfully validated.");
		return true;
	}
	
	//Validates fields for manually adding reading progress to new 'previously read' books
	protected static Boolean validateProgressFieldsPast(int pages, String dateStarted, String dateCompleted, String dateRead, int pagesOnDate, String pagesReadOnADate) {
		if(pages < 1 || pagesOnDate < 1) return false;
		try {
			if(SIMPLE_DATE_FORMAT.parse(dateStarted).compareTo(SIMPLE_DATE_FORMAT.parse(dateRead)) > 0) return false; //prevent dateStarted being after dateRead
			if(SIMPLE_DATE_FORMAT.parse(dateRead).compareTo(SIMPLE_DATE_FORMAT.parse(dateCompleted)) > 0) return false; //prevent dateRead being after dateCompleted
		} catch (ParseException e) {
		}
		if(pagesOnDate + getTotalPagesReadOnDates(pagesReadOnADate) > pages) return false;
		return true;
	}
	
	//Validates fields for manually adding reading progress to new 'currently reading' books
	protected static Boolean validateProgressFieldsCurrent(int pages, String dateStarted, int pagesRead, String dateRead, int pagesOnDate, String pagesReadOnADate) {
		if(pages < 1 || pagesRead < 1 || pagesOnDate < 1) return false;
		try {
			if(DATE_FORMAT.parse(dateStarted).compareTo(DATE_FORMAT.parse(dateRead)) > 0) return false; //prevent dateStarted being after dateRead
		} catch (ParseException e) {
		}
		if(pagesOnDate + getTotalPagesReadOnDates(pagesReadOnADate) > pagesRead) return false;
		return true;
	}
	
	//Returns the sum of the int parts of pagesReadOnADate
	private static int getTotalPagesReadOnDates(String pagesReadOnADate) {
		int counter = 0;
		if(!pagesReadOnADate.equals("")) {
			String pagesReadOnADateObjs[] = pagesReadOnADate.split(" ");
			for(String s : pagesReadOnADateObjs) {
				String dateValuePairObj[] = s.split(",");
				try {
					counter += Integer.parseInt(dateValuePairObj[1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					return -1;
				}
			}
		}
		return counter;
	}
	
}
