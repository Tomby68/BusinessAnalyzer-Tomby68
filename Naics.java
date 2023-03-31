package for_assignment2;

public class Naics {
	String naics;
	int size;
	ArrayList<String> zips;
	ArrayList<String> neighborhoods;
	
	public Naics(String code, String zip, String neighborhood) {
		naics = code;
		size = 1;
		zips = new ArrayList<String>();
		if (!zip.equals("")) {
			zips.add(zip);
		}
		neighborhoods = new ArrayList<String>();
		if (!neighborhood.equals("")) {
			neighborhoods.add(neighborhood);
		}
	}
	
	public int size() {
		return size;
	}
	
	public int zips() {
		return zips.size();
	}
	
	public int neighborhoods() {
		return neighborhoods.size();
	}
	// Adds the business to the naics object
	public void addB(Business b) throws Exception {
		size++;
		boolean inZip = false;
		boolean inNeighborhoods = false;
		for (int i = 0; i < zips.size(); i++) {
			if (zips.get(i).equals(b.zip)) {
				inZip = true;
			}
		}
		if (!inZip && !b.zip.equals("")) {
			zips.add(b.zip);
		}
		for (int i = 0; i < neighborhoods.size(); i++) {
			if (neighborhoods.get(i).equals(b.location)) {
				inNeighborhoods = true;
			}
		}
		if (!inNeighborhoods && !b.location.equals("")) {
			neighborhoods.add(b.location);
		}
	}
}
