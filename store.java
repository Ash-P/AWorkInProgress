import java.io.*;
import java.util.*;

public class store implements storage {
	// store names of files to be used, user names, books and targets
	private String bookStoreFileName = "";
	private String userStoreFileName = "userStore.txt";
	private String targetStoreFileName = "";

	// store the files themselves
	private File userStoreFile;
	private File bookStoreFile;
	private File targetStoreFile;

	// store readers and writers for each file
	private BufferedReader userFileReader;
	private BufferedWriter userFileWriter;
	private BufferedReader bookStoreReader;
	private BufferedWriter bookStoreWriter;
	private BufferedReader targetStoreReader;
	private BufferedWriter targetStoreWriter;

	public store(String username) {
		bookStoreFileName = username + "BookStore.txt";
		targetStoreFileName = username + "TargetStore.txt";

		// retrieve the file if it exists...
		userStoreFile = new File(userStoreFileName);
		bookStoreFile = new File(bookStoreFileName);
		targetStoreFile = new File(targetStoreFileName);

		// ...or create it if it doesn't exist (does nothing if it doesn't exist)

		try {
			targetStoreFile.createNewFile();
			userStoreFile.createNewFile();
			bookStoreFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// assign reader and writer for each file

	}

	public static void main(String[] args) {
		store s = new store("username");

	}

	// function that returns a users data
	@Override
	public ArrayList<userData> retrieveUserData() throws IOException {
		ArrayList<userData> userDataReturn = new ArrayList<>();
		File myFile = new File("userStore.txt");
		FileReader fileIn = new FileReader(myFile);
		BufferedReader input = new BufferedReader(fileIn);

		String s = input.readLine();
		// for(String s = userFileReader.readLine(); s != null; s =
		// userFileReader.readLine()){
		while (s != null) {
			String[] properties = s.split(" ; ");
			userData userdata = new userData();
			userdata.userID = Integer.parseInt(properties[0]);
			userdata.username = properties[1];
			userdata.totalPagesRead = Integer.parseInt(properties[2]);
			userdata.totalBooksRead = Integer.parseInt(properties[3]);
			userdata.pageAchievsUnlocked = Integer.parseInt(properties[4]);
			userdata.bookAchievsUnlocked = Integer.parseInt(properties[5]);
			try {
				userdata.booksCompletedOnADate = properties[6];
			} catch (Exception e) {
				userdata.booksCompletedOnADate = "";
			}
			;
			userDataReturn.add(userdata);
			s = input.readLine();
		}
		return userDataReturn;
	}


	
	
	
	
	// returns book data
	@Override
	public ArrayList<bookData> retrieveBookData() throws IOException {
		ArrayList<bookData> bookDataReturn = new ArrayList<>();
		// File myFile = new File(bookStoreFileName);
		File myFile = new File(bookStoreFileName);
		FileReader fileIn = null;
		fileIn = new FileReader(myFile);
		BufferedReader input = new BufferedReader(fileIn);
		// for(String s = bookStoreReader.readLine(); s != null; s =
		// bookStoreReader.readLine()){
		String s = input.readLine();
		while (s != null) {
			String[] properties = s.split(" ; ");
			bookData bookdata = new bookData();

			if (properties[0] == " ") {
			} else {
				bookdata.bookID = Integer.parseInt(properties[0]);
			}

			if (properties[0] == " ") {
			} else {
				bookdata.status = Integer.parseInt(properties[1]);
			}

			bookdata.title = properties[2];

			if (properties[0] == " ") {
			} else {
				bookdata.pages = Integer.parseInt(properties[3]);
			}

			bookdata.dateAdded = properties[4];
			bookdata.dateStarted = properties[5];
			bookdata.dateCompleted = properties[6];
			bookdata.author = properties[7];
			bookdata.publisher = properties[8];
			bookdata.publicationYear = properties[9];
			bookdata.genre = properties[10];
			bookdata.description = properties[11];
			bookdata.pagesRead = Integer.parseInt(properties[12]);
			try {
				bookdata.pagesReadOnADate = properties[13];
			} catch (Exception e) {
				bookdata.pagesReadOnADate = "";
			}
			bookDataReturn.add(bookdata);
			s = input.readLine();
		}
		fileIn.close();
		input.close();
		return bookDataReturn;
	}

	// returns a list of targets
	@Override
	public ArrayList<targetData> retrieveTargetData() throws IOException {
		ArrayList<targetData> targetdata = new ArrayList<>();
		File myFile = new File(targetStoreFileName);
		FileReader fileIn = null;
		fileIn = new FileReader(myFile);
		BufferedReader input = new BufferedReader(fileIn);
		String s = input.readLine();
		while (s != null) {

			String[] properties = s.split(" ; ");
			targetData t = new targetData();

			try {
				t.targetID = Integer.parseInt(properties[0]);
			} catch (Exception e) {
				t.targetID = -1;
			}

			try {
				t.targetType = Integer.parseInt(properties[1]);
			} catch (Exception e) {
				t.targetType = -1;
			}

			try {
				t.isComplete = Boolean.parseBoolean(properties[2]);
			} catch (Exception e) {
				t.isComplete = false;
			}

			try {
				t.deadlineDate = properties[3];
			} catch (Exception e) {
				t.deadlineDate = "";
			}

			try {
				t.bookID = Integer.parseInt(properties[4]);
			} catch (Exception e) {
				t.bookID = -1;
			}

			try {
				t.targetValue = Integer.parseInt(properties[5]);
			} catch (Exception e) {
				t.targetValue = -1;
			}

			try {
				t.valueRemaining = Integer.parseInt(properties[6]);
			} catch (Exception e) {
				t.valueRemaining = -1;
			}

			targetdata.add(t);
			s = input.readLine();
		}

		// targetStoreReader.close();
		return targetdata;
	}

	@Override
	public void storeUserData(ArrayList<userData> newUserData) {
		try {
			FileWriter updateFile = new FileWriter(userStoreFile, false);
			for (userData u : newUserData) {
				updateFile.append(u.userID + " ; " + u.username + " ; " + u.totalPagesRead + " ; " + u.totalBooksRead
						+ " ; " + u.pageAchievsUnlocked + " ; " + u.bookAchievsUnlocked + " ; "
						+ u.booksCompletedOnADate + "\n");
			}
			updateFile.close();
			userFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(userStoreFile)));
		} catch (IOException e) {
			System.out.println("Something went wrong");
		}
	}

	public static void storeSingleUser(userData u) {
		try {
			FileWriter fwUpdateFile = new FileWriter("userStore.txt", false);
			BufferedWriter updateFile = new BufferedWriter(fwUpdateFile);
			
			updateFile.newLine();
			updateFile.append(u.userID + " ; " + u.username + " ; " + u.totalPagesRead + " ; " + u.totalBooksRead
					+ " ; " + u.pageAchievsUnlocked + " ; " + u.bookAchievsUnlocked + " ; " + u.booksCompletedOnADate
					+ "\n");

			updateFile.close();
	
		} catch (IOException e) {
			System.out.println("Something went wrong");
		}

	}

	@Override
	public void storeBookData(ArrayList<bookData> newBookData) {
		try {
			FileWriter updateFile = new FileWriter(bookStoreFile, false);
			for (bookData b : newBookData) {
				updateFile.append(b.bookID + " ; " + b.status + " ; " + b.title + " ; " + b.pages + " ; " + b.dateAdded
						+ " ; " + b.dateStarted + " ; " + b.dateCompleted + " ; " + b.author + " ; " + b.publisher
						+ " ; " + b.publicationYear + " ; " + b.genre + " ; " + b.description + " ; " + b.pagesRead
						+ " ; " + b.pagesReadOnADate + "\n");
			}
			updateFile.close();
			bookStoreReader = new BufferedReader(new InputStreamReader(new FileInputStream(bookStoreFile)));
		} catch (IOException e) {
			System.out.println("Something went wrong");
		}

	}

	@Override
	public void storeTargetData(ArrayList<targetData> newTargetData) {
		try {
			FileWriter updateFile = new FileWriter(targetStoreFile, false);
			for (targetData t : newTargetData) {
				updateFile.append(t.targetID + " ; " + t.targetType + " ; " + t.isComplete + " ; " + t.deadlineDate
						+ " ; " + t.bookID + " ; " + t.targetValue + " ; " + t.valueRemaining + "\n");
			}
			updateFile.close();
			targetStoreReader = new BufferedReader(new InputStreamReader(new FileInputStream(targetStoreFile)));
		} catch (IOException e) {
			System.out.println("Something went wrong");
		}

	}
}
