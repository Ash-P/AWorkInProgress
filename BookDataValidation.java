
public class BookDataValidation {

	
	protected static boolean validBookDataString(String string) {
		if(string.contains("@") || string.contains(";")) {
			return true;
		}
		return false;
	
		
	}
	
	
	
}
