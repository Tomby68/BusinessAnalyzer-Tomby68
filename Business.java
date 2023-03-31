package for_assignment2;

public class Business {
	String openedDate;
	boolean closed;
	String zip;
	String naics;
	String desc;
	String location;
	
	public Business(String newOpened, String newClose, String newZip, String newNaics, String newDesc, String newLocation) {
		openedDate = newOpened;
		closed = !newClose.equals("");
		zip = newZip;
		naics = newNaics;
		desc = newDesc;
		location = newLocation;
	}
}
