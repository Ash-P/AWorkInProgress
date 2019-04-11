public abstract class TestData implements storage{

    public static void main(String[] args){
        userData userData = new userData();
        bookData bookData = new bookData();
        targetData targetData = new targetData();

        userData.userID = 1;
        userData.username = "will_05";
        userData.totalPagesRead = 1039;
        userData.totalBooksRead = 2;
        userData.booksCompletedOnADate = "04-04-2019,2";


        bookData.bookID = 1;
        bookData.status = 0;
        bookData.title = "Harry Potter: The Philosopher's Stone";
        bookData.author = "J. K. Rowling";
        bookData.publisher = "Bloomsbury Publishing";
        bookData.publicationYear = "1997";
        bookData.pages = 223;
        bookData.genre = "Fantasy";
        bookData.dateAdded = "27 March 2019";
        bookData.dateStarted = "27 March 2019";
        bookData.dateCompleted = "04 April 2019";
        bookData.description = "It is a story about q boy whose life changed after he was accepted into magic school";
        bookData.pagesRead = 223;
        bookData.pagesReadOnADate = "27-03-2019,100\" \"04-04-2019,123";

        targetData.targetID = 1;
        targetData.targetType = 1;
        targetData.isComplete = true;
        targetData.deadlineDate = "04 April 2019";
        targetData.bookID = 1;// what is our id number convention
        targetData.targetValue = 223;
        targetData.valueRemaining = 0;
       

    }

}
