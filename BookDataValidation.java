
public class BookDataValidation {
	
	protected static boolean validBookDataString(String string) {
		if(string.contains("@") || string.contains(";")) {
			return true;
		}
		return false;
	}
	
	protected static boolean validBookDataInteger(String string) {
		if(string.matches("[0-9]+")) return false;
		return true;
	}
	
}
