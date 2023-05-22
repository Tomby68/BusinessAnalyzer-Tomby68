/*
 * @author Thomas Sorkin
 */
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * Email Analyzer: Takes in a file as an argument and reads through all files within the given directory. The program reads
 * through all valid mail files and saves valid email addresses in a HashMap. The program also saves information
 * about each valid mail file. Then, the program asks the user to input a valid email address. If the email is in
 * the list of emails, the program prints out the number of emails that address has sent and received, as well as the
 * number of other members in that addresses team.
 */
public class Assignment03 {
	public static void main(String[] args) {
		
		HashMap<String, TeamMember> emails = new HashMap<String, TeamMember>();
		Data d = new Data();
		File file = new File(args[0]);
		listFilesinDirectory(file, emails, d);
		
		DisjointSet djs = new DisjointSet();
		djs.createSets(emails.size());
		
		createTeams(emails, djs);
		
		takeUserInput(emails, d, djs);
	}
	
	/*
	 * Reads through the given file recursively.
	 * @param f: A file
	 * @param emails: A HashMap of email addresses
	 * @param d: A variable of the type Data which stores useful global information about the emails
	 */
	public static void listFilesinDirectory(File f, HashMap<String, TeamMember> emails, Data d) {
		for (File entry : f.listFiles()) {
			if (entry.isDirectory()) {
				listFilesinDirectory(entry, emails, d);
			} else {
				readFile(entry, emails, d);
			}
		}
	}
	
	/*
	 * Reads the given file using BufferedReader. Adds valid mail files to the emails HashMap and updates the address' 
	 * information
	 * @param fileName: The name of the file
	 * @param emails: A HashMap of email addresses
	 * @param d: A variable of the type Data which stores useful global information about the meails
	 */
	private static void readFile(File fileName, HashMap<String, TeamMember> emails, Data d) {
		try {
			Reader r = new FileReader(fileName);
			BufferedReader br = new BufferedReader(r);
			String line = br.readLine();
			boolean hasFrom = false;
			boolean hasTo = false;
			
			while (line != null) {
				String[] next = line.split(" ");
				if (next.length > 0) {
					if (next[0].equals("From:")) {
						hasFrom = true;
						String email = extractEmail(line);
						if (email != null) {
							TeamMember individual;
							if (emails.get(email) == null) {
								emails.put(email, new TeamMember(email, d.currIndex++));
							} 
							individual = emails.get(email);
							line = br.readLine();
							next = line.split(" ");
							
							if (next[0].equals("To:")) {
								hasTo = true;
								boolean moreEmails = true;
								while (moreEmails) {
									for (int i = 0; i < next.length; i++) {
										email = extractEmail(next[i]);
										if (email != null) {
											if (emails.get(email) == null) {
												emails.put(email, new TeamMember(email, d.currIndex++));
											}
											emails.get(email).received++;
											individual.adjacency.add(email);
										}
									}
									
									if (next[next.length - 1].charAt(next[next.length - 1].length() - 1) != ',') {
										moreEmails = false;
									} else {
										line = br.readLine();
										next = line.split(" ");
									}
								}
							}
						}
					}
				}
				line = br.readLine();
			}
			br.close();
			if (hasFrom && hasTo) {
				d.numEmails++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/*
	 * Uses regex to search for a valid email address. Taken from David in the cs slack.
	 * @param input: The string in which to search for an email address
	 */
	private static String extractEmail(String input) {
	    Matcher matcher = Pattern.compile("([a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9._-]+)").matcher(input);
	    if (matcher.find()) {
	        return matcher.group(1);
	    }
	    return null;
	}
	
	/*
	 * Using a disjoint set, unions any email addresses that send to each other.
	 * @param emails: A HashMap of email addresses
	 * @param djs: A disjoint set storing email addresses
	 */
	public static void createTeams(HashMap<String, TeamMember> emails, DisjointSet djs) {
		Set<String> set = emails.keySet();
		
		for (String email : set) {
			LinkedList<String> adjacency = emails.get(email).adjacency;
			for (int i = 0; i < adjacency.size(); i++) {
				if (djs.find(emails.get(email).index) != djs.find(emails.get(adjacency.get(i)).index)) {
					djs.union(emails.get(email).index, emails.get(adjacency.get(i)).index);
				}
			}
		}
	}
	
	/*
	 * Takes in and evaluates user input.
	 * @param emails: A HashMap of email addresses
	 * @param d: A variable of the Data type which stores useful global information about the emails
	 * @param djs: A disjoint set storing email addresses
	 */
	public static void takeUserInput(HashMap<String, TeamMember> emails, Data d, DisjointSet djs) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Email address of the individual (or EXIT to quit) ");
		String input = scnr.nextLine();
		Object[] addresses = emails.keySet().toArray();
		while (!input.equals("EXIT")) {
			boolean found = false;
			for (int i = 0; i < addresses.length; i++) {
				if (addresses[i].equals(input)) {
					found = true;
					System.out.println(input + " has sent messages to " + emails.get(addresses[i]).adjacency.size() + " others");
					System.out.println(input + " has received messages from " + emails.get(addresses[i]).received + " others");
					System.out.println(input + " is in a team with " + djs.getTeamSize(emails.get(input).index) + " individuals" );
				}
			}
			
			if (!found) {
				System.out.println("Email address (" + input + ") not found in the dataset");
			}
			System.out.println("Email address of the individual (or EXIT to quit)");
			input = scnr.nextLine();
		}
		scnr.close();
	}
}
