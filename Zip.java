public class Zip {
	String zip;
	int size;
	ArrayList<String> bTypes;
	ArrayList<String> neighborhoods;
	
	public Zip(String theZip, String type, String neighborhood) {
		zip = theZip;
		size = 1;
		bTypes = new ArrayList<String>();
		if (!type.equals("")) {
			bTypes.add(type);
		}
		neighborhoods = new ArrayList<String>();
		if (!neighborhood.equals("")) {
			neighborhoods.add(neighborhood);
		}
	}
	
	public int size() {
		return size;
	}
	
	public int types() {
		return bTypes.size();
	}
	
	public int neighborhoods() {
		return neighborhoods.size();
	}
	// Adds the business to the zip object
	public void addB(Business b) throws Exception {
		size++;
		boolean inType = false;
		boolean inNeighborhoods = false;
		for (int i = 0; i < bTypes.size(); i++) {
			if (bTypes.get(i).equals(b.desc)) {
				inType = true;
			}
		}
		if (!inType && !b.desc.equals("")) {
			bTypes.add(b.desc);
		}
		
		for (int j = 0; j < neighborhoods.size(); j++) {
			if (neighborhoods.get(j).equals(b.location)) {
				inNeighborhoods = true;
			}
		}
		if (!inNeighborhoods && !b.location.equals("")) {
			neighborhoods.add(b.location);
		}
	}
}
