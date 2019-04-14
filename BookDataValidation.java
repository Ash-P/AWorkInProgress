import java.time.Year;
import java.time.format.DateTimeParseException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BookDataValidation {
	
	protected static boolean validBookDataString(String string) {
		if(string.contains(";")) {
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
		if(!validBookDataString(title)) {
			dataInvalid("Book title is invalid.\nIt must not contain the character ;");
			return false;
		}
		if(!validBookDataInteger(String.valueOf(pages))) {
			dataInvalid("Book pages is invalid.\nIt must be a positive integer.");
			return false;
		}
		
		if(author != null && !validBookDataString(author)) {
			dataInvalid("Book author is invalid.\nIt must not contain the character ;");
			return false;
		}
		if(publisher != null && !validBookDataString(publisher)) {
			dataInvalid("Book publisher is invalid.\nIt must not contain the character ;");
			return false;
		}
		if(genre != null && !validBookDataString(genre)) {
			dataInvalid("Book genre is invalid.\nIt must not contain the character ;");
			return false;
		}
		if(description != null && !validBookDataString(description)) {
			dataInvalid("Book description is invalid.\nIt must not contain the character ;");
			return false;
		}
		if(publicationYear != -1 && !validBookDataInteger(String.valueOf(publicationYear))) {
			dataInvalid("Book publication year is invalid.\nIt must be a positive integer.");
			return false;
		}
		
		if(title.equals(null)) {
			dataInvalid("Book title is invalid.\nIt must be specified.");
			return false;
		}
		for(storage.bookData b : alldata.bookStore) {
			if(b.title.equals(title)) {
				dataInvalid("Book title already exists.\nThe title must be unique.");
				return false;
			}
		}
		try {
			if(publicationYear != -1 && Year.now().compareTo(Year.parse(String.valueOf(publicationYear))) < 0) {
				dataInvalid("Book publication year is invalid.\nIt must be an integer from 1000 to the current year.");
				return false; //publicationYear cannot be a future year
			}
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
	
	//Validates fields that apply for new 'previously read' books
	protected static Boolean validateFieldsPrevious(int pages, String pagesReadOnADate) {
		if(AddABook.getTotalPagesReadOnDates(pagesReadOnADate) != pages) {
			System.out.println("pagesReadOnADate: '" + pagesReadOnADate + "'"); //test
			dataInvalid("Invalid dates pages were read.\nManually entered pages read does not match book pages.");
			return false;
		}
		System.out.println("Previous fields successfully validated."); //test
		return true;
	}
	
	//Validates fields that apply for new 'currently reading' books
	protected static Boolean validateFieldsCurrent(int pages, int pagesRead, String pagesReadOnADate) {
		if(pagesRead >= pages) {
			dataInvalid("Pages read invalid.\nCannot be greater than pages.");
			return false;
		}
		if(AddABook.getTotalPagesReadOnDates(pagesReadOnADate) != pagesRead) {
			System.out.println("pagesReadOnADate: '" + pagesReadOnADate + "'"); //test
			dataInvalid("Invalid dates pages were read.\nManually entered pages read does not match pages read.");
			return false;
		}
		System.out.println("Current fields successfully validated."); //test
		return true;
	}
	
	/*
	//Validates fields for manually adding reading progress to new 'previously read' books
	protected static Boolean validateProgressFieldsPast(int pages, int pagesOnDate, String pagesReadOnADate) {
		if(pages < 1) {
			dataInvalid("Invalid pages.\nMust be a positive integer.");
			return false;
		}
		if(pagesOnDate < 1) {
			dataInvalid("Invalid pages read on a date.\nMust be a positive integer.");
			return false;
		}
		if(pagesOnDate + AddABook.getTotalPagesReadOnDates(pagesReadOnADate) > pages) {
			dataInvalid("Invalid pages read on a date.\nTotal pages read on all dates cannot be greater than book pages.");
			return false;
		}
		return true;
	}
	
	//Validates fields for manually adding reading progress to new 'currently reading' books
	protected static Boolean validateProgressFieldsCurrent(int pages, int pagesRead, int pagesOnDate, String pagesReadOnADate) {
		if(pages < 1) {
			dataInvalid("Invalid pages.\nMust be a positive integer.");
			return false;
		}
		if(pagesRead < 1) {
			dataInvalid("Invalid book pages read.\nMust be a positive integer.");
			return false;
		}
		if(pagesOnDate < 1) {
			dataInvalid("Invalid pages read on a date.\nMust be a positive integer.");
			return false;
		}
		if(pagesOnDate + AddABook.getTotalPagesReadOnDates(pagesReadOnADate) > pagesRead) return false;
		return true;
	}
	*/
	
	private static void dataInvalid(String content) {
		MAIN.createAlert(AlertType.ERROR, "Error", "Validation unsuccessful", content);
	}
	
}
