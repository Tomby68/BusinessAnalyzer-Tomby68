public class Data {
	int totalB;
	int closed;
	int opened;
	
	public Data() {
		totalB = 0;
		closed = 0;
		opened = 0;
	}
	// Adds the business to the data object
	public void addB(Business b) {
		totalB++;
		if (b.closed) {
			closed++;
		}
		if (Integer.parseInt(b.openedDate.split("/")[2]) >= 2022) {
			opened++;
		}
	}
}
