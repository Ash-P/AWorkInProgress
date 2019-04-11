import java.io.IOException;
import java.util.ArrayList;
public interface storage {

	
	public ArrayList<userData> retrieveUserData() throws IOException;
	public ArrayList<bookData> retrieveBookData() throws IOException;
	public ArrayList<targetData> retrieveTargetData() throws IOException;
	public void storeUserData( ArrayList<userData> newUserData );
	public void storeBookData( ArrayList<bookData> newBookData );
	public void storeTargetData( ArrayList<targetData> newTargetData );
	
	
	public class userData{
		int userID;
		String username;
		int totalPagesRead;
		int totalBooksRead;
		int pageAchievsUnlocked;
		int bookAchievsUnlocked;
		String booksCompletedOnADate;
	}
	
	public class bookData{
		int bookID;
		int status;
		String title;
		String author;
		String publisher;
		String publicationYear;
		int pages;
		String genre;
		String dateAdded;
		String dateStarted;
		String dateCompleted;
		String description;
		int pagesRead;
		String pagesReadOnADate;
		
		public int getStatus() {
			return status;
		}
		public String getTitle() {
			return title;
		}
		public String getAuthor() {
			return author;
		}
		public String getPublisher() {
			return publisher;
		}
		public String getPublicationYear() {
			return publicationYear;
		}
		public int getPages() {
			return pages;
		}
		public String getGenre() {
			return genre;
		}
		public String getDateAdded() {
			return dateAdded;
		}
		public String getDateStarted() {
			return dateStarted;
		}
		public String getDateCompleted() {
			return dateCompleted;
		}
		public String getDescription() {
			return description;
		}
		public int getPagesRead() {
			return pagesRead;
		}
		
	}
	
	public class targetData{
		int targetID;
		int targetType;
		boolean isComplete;
		String deadlineDate;
		int bookID;
		int targetValue;
		int valueRemaining;
		
		public String getTargetType() {
			if(targetType == 1) return "Pages of the book " + MAIN.getBookTitle(bookID);
			if(targetType == 2) return "Pages across all books";
			if(targetType == 3) return "Books";
			return null;
		}
		public String getIsComplete() {
			if(isComplete) return "Yes";
			else return "No";
		}
		public String getDeadlineDate() {
			return deadlineDate;
		}
		public int getTargetValue() {
			return targetValue;
		}
		public int getValueRemaining() {
			return valueRemaining;
		}
		
	}
}
