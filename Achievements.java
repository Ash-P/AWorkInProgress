import javax.swing.*;

public class Achievements extends JFrame {
	
	int[] pageAchievs = new int[8];
	pageAchievs = {1, 10, 50, 200, 500, 1000, 5000, 10000}; // Number of pages read
	
	int[] bookAchievs = new int[8];
	bookAchievs = {1, 5, 10, 20, 50, 100, 250, 500}; // Number of books read
	
	int pageAchievsUnlocked = -1;
	int bookAchievsUnlocked = -1;

	public int getPagesRead() {
		return userStore.getPagesRead();
	}
	
	public int getBooksRead() {
		return userStore.getBooksRead();
	}
	
	// Keep iterating through the array until we reach an achievement higher than the current pages read
	public int getPagesRemaining() {
		while(getPagesRead() >= pageAchievs[pageAchievsUnlocked+1]) {
			pageAchievsUnlocked++;
		}
		
		return (pageAchievs[pageAchievsUnlocked+1] - getPagesRead());
	}
	
	public int getBooksRemaining() {
		while(getBooksRead() >= bookAchievs[bookAchievsUnlocked+1]) {
			bookAchievsUnlocked++;
		}
		
		return (bookAchievs[bookAchievsUnlocked+1] - getBooksRead())
	}
	
	public void checkIfNewAchiev(int pagesRead, int booksRead) {
		if(pagesRead >= pageAchievs[pageAchievsUnlocked+1]) {
			pageAchievsUnlocked++;
			popUpAlert();
		}
		
		if(booksRead >= bookAchievs[bookAchievsUnlocked+1]) {
			bookAchievsUnlocked++;
			popUpAlert();
		}
	}
	
	public void displayAllAchievs() {
		JFrame achievsWindow = new JFrame("Achievements");
		achievsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		achievsWindow.setLayout(new FlowLayout());
		achievsWindow.setLocationRelativeTo(null);
		achievsWindow.setVisible(TRUE);
		
		JLabel allPageAchievs = new JLabel("Achievements for total pages read: " + pageAchievs[0] + ", " + pageAchievs[1] + ", "
				 + pageAchievs[2] + ", " + pageAchievs[3] + ", " + pageAchievs[4] + ", " + pageAchievs[5] + ", "
				 + pageAchievs[6] + ", " + pageAchievs[7] + ".");
		
		JLabel allBookAchievs = new JLabel("Achievements for total books read: " + bookAchievs[0] + ", " + bookAchievs[1] + ", "
				 + bookAchievs[2] + ", " + bookAchievs[3] + ", " + bookAchievs[4] + ", " + bookAchievs[5] + ", "
				 + bookAchievs[6] + ", " + bookAchievs[7] + ".");
		
		achievsWindow.add(allPageAchievs);
		achievsWindow.add(allBookAchievs);
	}
	
	public void displayUsersAchievs() {
		JFrame achievsWindow = new JFrame("Achievements");
		achievsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		achievsWindow.setLayout(new FlowLayout());
		achievsWindow.setLocationRelativeTo(null);
		achievsWindow.setVisible(TRUE);
		
		String unlockedPages = "";
		for(int i = 0; i <= pageAchievsUnlocked; i++) {
			unlockedPages = unlockedPages + Integer.ToString(pageAchievs[i] + ", ");
		}
		
		String unlockedBooks = "";
		for(int i = 0; i <= bookAchievsUnlocked; i++) {
			unlockedBooks = unlockedBooks + Integer.ToString(bookAchievs[i] + ", ");
		}
		
		JLabel allPageAchievsUnlocked = new JLabel("You have unlocked the following page achievements: " + unlockedPages + "\nYou have read " + getPagesRead() + " pages.");
		JLabel allBooksAchievsUnlocked = new JLabel("You have unlocked the following book achievements: " + unlockedBooks + "\nYou have read " + getBooksRead() + " books.");
		
		achievsWindow.add(allPageAchievsUnlocked);
		achievsWindow.add(allBooksAchievsUnlocked);
	}
	
	public void popUpAlert() {
		JFrame window = new JFrame("Achievements");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(GridLayout(4,4));
		window.setLocationRelativeTo(null);
		winow.setVisible(TRUE);
		
		JLabel achievPagesRead = new JLabel("You have " + getPagesRead() + " pages read at the moment.");
		JLabel achievPagesLeft = new JLabel("You have " + getPagesRemaining() + " pages to go until the next achievement.");
		
		JLabel achievBooksRead = new JLabel("You have " + getBooksRead() + " books read at the moment.");
		JLabel achievBooksLeft = new JLabel("You have " + getRemaining() + " books to go until the next achievement.");
		
		window.add(achievPagesRead);
		window.add(achievPagesLeft);
		window.add(achievBooksRead);
		window.add(achievBooksLeft);
	}
}