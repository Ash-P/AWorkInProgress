public abstract class TestData implements storage{

    public static void main(String[] args){
        userData userData = new userData();
        bookData bookData = new bookData();
        targetData targetData = new targetData();

        userData.userID = 10321;// what is our id number convention
        userData.username = "will_05";
        userData.totalPagesRead = 1039;
        userData.totalBooksRead = 2;
        userData.booksCompletedOnADate = "";


        bookData.bookID = 00011;// what is our id number convention
        bookData.status = 1;// need to check status number
        bookData.title = "Harry Potter: The Philosopher's Stone";
        bookData.author = "J. K. Rowling";
        bookData.publisher = "Bloomsbury Publishing";
        bookData.publicationYear = "26 June 1997";
        bookData.pages = 223;
        bookData.genre = "Fantasy";
        bookData.dateAdded = "27 March 2019";
        bookData.dateStarted = "27 March 2019";
        bookData.dateCompleted = "04 April 2019";
        bookData.description = "It is a story about q boy whose life changed after he was accepted into magic school";
        bookData.pagesRead = "223";//shouldn't this be ints
        bookData.pagesReadOnADate = "24";

        targetData.targetID = 10011;// what is our id number convention
        targetData.targetType = 1;//need the target type codes
        targetData.isComplete = true;
        targetData.deadlineDate = "04 April 2019";
        targetData.bookID = 00011;// what is our id number convention
        targetData.targetValue = 223;
        targetData.valueRemaining = 0;
       

    }

}
