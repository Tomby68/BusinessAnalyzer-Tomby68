package for_assignment2;

/*
 * @author Thomas Sorkin
 */

import java.io.*;
import java.util.Scanner;

/*
 * Assignment_2: Takes in a .csv file name and a flag as arguments. Reads in businesses from a .csv file and 
 * analyzes the data based on NAICS codes and zip codes. The codes are stored in either a linked list or an array list, based on the flag
 * Then, the program takes in certain user commands and provides a summary of either a given zip code, naics code, all businesses,
 * or prints all the commands the user has input.
 */
public class Assignment_2 {
	public static void main(String[] args) {
		ListInterface<Naics> naicsList = null;
		ListInterface<Zip> zipList = null;
		Data data = new Data();
		
		if (args[1].equals("AL")) {				// Check the flag
			naicsList = new ArrayList<Naics>();
			zipList = new ArrayList<Zip>();
		} else if (args[1].equals("LL")) {
			naicsList = new LinkedList<Naics>();
			zipList = new LinkedList<Zip>();
		} else {
			System.out.println("Argument order: fileName flag");
			System.exit(-1);
		}
		
		System.out.println("Reading file...");
		try {
			readFile(args[0], args[1], naicsList, zipList, data);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Done");
		
		try {
			userInput(naicsList, zipList, data);
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	/*
	 * readFile reads from the given file and stores the data in naicsList, zipList, and d
	 * @param String fileName: The name of the file to read from
	 * @param String flag: LL or AL flag
	 * @param ListInterface<Naics> naicsList: A list to store the naics codes
	 * @param ListInterface<Zip> zipList: A list to store the zip codes
	 * @param Data d: An object to store the total businesses, closed businesses, and businesses open in the last year
	 */
	public static void readFile(String fileName, String flag, ListInterface<Naics> naicsList, ListInterface<Zip> zipList, Data d) throws Exception {
		try {
			Reader f = new FileReader(fileName);
			BufferedReader next = new BufferedReader(f);
			next.readLine();
			
			String line = next.readLine();
			
			while (line != null) {
				String[] s = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
				Business b = new Business(s[8], s[9], s[14], s[16], s[17], s[23]);	// account num, opened date, end date, zip code, naics code, naics description, neighborhood
				d.addB(b);
				if (!b.naics.equals("")) {
					addNaics(b, naicsList, flag);
				}
				if (!b.zip.equals("")) {
					addZip(b, zipList, flag);
				}
				line = next.readLine();
			}
			next.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	/*
	 * userInput takes in user commands and prints out code summaries or a command history
	 * @param ListInterface<Naics> naicsList: A list to store the naics codes
	 * @param ListInterface<Zip> zipList: A list to store the zip codes
	 * @param Data d: An object to store the total businesses, closed businesses, and businesses open in the last year
	 */
	public static void userInput(ListInterface<Naics> naicsList, ListInterface<Zip> zipList, Data d) throws Exception{
		Scanner scnr = new Scanner(System.in);
		String line = "";
		Queue<String> q = new Queue<String>();
		
		while (!line.toLowerCase().equals("quit")) {
			System.out.println("Please enter one of the following commands: ");
			System.out.println("Zip [zip code] Summary: Provides a summary of the given zip code");
			System.out.println("NAICS [naics code] Summary: Provides a summary of the given NAICS code");
			System.out.println("Summary: Provides a summary of all businesses");
			System.out.println("History: Provides the command history");
			System.out.println("Quit: Exits the program");
			line = scnr.nextLine();
			String[] input = line.split(" ");
			boolean invalid = false;
			
			if (input[0].equals("Zip")) {
				zipSum(zipList, input[1]);
			} else if (input[0].equals("NAICS")) {
				naicsSum(naicsList, input[1]);
			} else if (input[0].equals("Summary")) {
				System.out.println("Total Businesses: " + d.totalB);
				System.out.println("Closed Businesses: " + d.closed);
				System.out.println("New Businesses in Last Year: " + d.opened);
			} else if (input[0].equals("History")) {
				Queue<String> tmp = new Queue<String>();
				while (!q.empty()) {
					String command = q.dequeue();
					tmp.enqueue(command);
					System.out.println(command);
				}
				q = tmp;
			} else if (input[0].toLowerCase().equals("quit")) {
				break;
			} else {
				System.out.println("Invalid input");
				invalid = true;
			}
			if (!invalid) {
				q.enqueue(line);
			}
		}
		scnr.close();
		
	}
	
	/*
	 * zipSum gives a summary of the given zip code
	 * @param ListInterface<Zip> list: A list to store the zip codes
	 * @param String zip: The requested zip code
	 */
	public static void zipSum(ListInterface<Zip> list, String zip) {
		Iterator<Zip> iterator = list.iterator();
		
		while (iterator.hasNext()) {
			Zip z = iterator.next();
			if (z.zip.equals(zip)) {
				System.out.println(zip + " Business Summary");
				System.out.println("Total Businesses: " + z.size());
				System.out.println("Business Types: " + z.types());
				System.out.println("Neighborhoods: " + z.neighborhoods());
				return;
			}
		}
		System.out.println("Zip code " + zip + " not found");
	}
	
	/*
	 * naicsSum gives a summary of the given naics code
	 * @param ListInterface<Naics> list: A list to store the naics codes
	 * @param String naics: The requested naics code
	 */
	public static void naicsSum(ListInterface<Naics> list, String naics) {
		Iterator<Naics> iterator = list.iterator();
		
		while (iterator.hasNext()) {
			Naics n = iterator.next();
			String[] naicsBounds = n.naics.split("-");
			if (Integer.parseInt(naics) >= Integer.parseInt(naicsBounds[0]) && Integer.parseInt(naics) <= Integer.parseInt(naicsBounds[1])) {
				System.out.println("Total Businesses: " + n.size());
				System.out.println("Zip Codes: " + n.zips());
				System.out.println("Neighborhoods: " + n.neighborhoods());
				return;
			}
		}
		System.out.println("NAICS code " + naics + " not found");
	}
	
	/*
	 * addNaics adds the naics code or codes from the business into the naics list
	 * @param Business b: Current business
	 * @param ListInterface<Naics> list: A list to store the naics codes
	 * @param String flag: The AL or LL flag
	 */
	public static void addNaics(Business b, ListInterface<Naics> list, String flag) throws Exception {
		String[] difNaics = b.naics.split(" ");
		for (int i = 0; i < difNaics.length; i++) {
			boolean added = false;
			Iterator<Naics> iterator = list.iterator();
			if (flag.equals("LL") && list.size() == 1) {
				Naics tmp = iterator.next();
				if (difNaics[i].equals(tmp.naics)) {
					tmp.addB(b);
					break;
				}
			}
			
			while (iterator.hasNext()) {
				Naics tmp = iterator.next();
				if (difNaics[i].equals(tmp.naics)) {
					tmp.addB(b);
					added = true;
					break;
				}
			}
			if (!added) {
				list.add(new Naics(difNaics[i], b.zip, b.location));
			}
		}
	}
	
	/*
	 * addZip adds the zip code from the business into the zip list
	 * @param Business b: Current business
	 * @param ListInterface<Zip> list: A list to store the zip codes
	 * @param String flag: The AL or LL flag
	 */
	public static void addZip(Business b, ListInterface<Zip> list, String flag) throws Exception {
		Iterator<Zip> iterator = list.iterator();
		if (flag.equals("LL") && list.size() == 1) {
			Zip tmp = iterator.next();
			if (b.zip.equals(tmp.zip)) {
				tmp.addB(b);
				return;
			}
		}
		
		while (iterator.hasNext()) {
			Zip tmp = iterator.next();
			if (b.zip.equals(tmp.zip)) {
				tmp.addB(b);
				return;
			}
		}
		list.add(new Zip(b.zip, b.desc, b.location));
	}
	
}
