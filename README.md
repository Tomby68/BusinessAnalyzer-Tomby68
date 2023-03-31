# CS245-Assignment02
Assignment_2: Takes in a .csv file name and a flag as arguments. Reads in businesses from a .csv file and 
analyzes the data based on NAICS codes and zip codes. The codes are stored in either a linked list or an array list, based on the flag
Then, the program takes in certain user commands and provides a summary of either a given zip code, naics code, all businesses,
or prints all the commands the user has input.

@param args[0]: The name of the .csv file
@param args[1]: The flag denoting ArrayList or LinkedList implementation

readFile: Uses BufferedReader to read each line of the .csv file. For each line, readFile creates a Business object, then adds to either the naicsList or the zipList
  @param String fileName: The name of the file to read from
  @param String flag: LL or AL flag
  @param ListInterface<Naics> naicsList: A list to store the naics codes
  @param ListInterface<Zip> zipList: A list to store the zip codes
  @param Data d: An object to store the total businesses, closed businesses, and businesses open in the last year
  addNaics:
    @param: Business b: Current business
    @param: ListInterface<Naics> list: The list of unique naics code objects
    @param: String flag: The AL or LL flag
    First, addNaics splits the businesses NAICS codes (which is most often just 1 NAICS code). Then, for each NAICS code, addNaics loops through the naicsList. If the current business NAICS code is in the naicsList, then it adds that business to the existing NAICS object. Otherwise, addNaics creates a new NAICS object and adds that to the naicsList
    Runtime: O(nm) ~ O(n), where n is the length of the naicsList and m is the number of NAICS codes for the business.
  addZip:
    @param: Business b: Current business
    @param: ListInterface<Zip> list: The list of unique zip code objects
    @param: String flag: The AL or LL flag
    addZip looops through the zipList and checks the current business zip code with each zip code in the zipList. If the current business zip code is in the zipList, then addZip adds that business to the Zip object. Otherwise, addZip creates a new Zip object and adds it to the zipList.
    Runtime: O(n), where n is the length of the zipList

userInput: Takes in user input commands and executes those commands.
  @param ListInterface<Naics> naicsList: A list to store the naics codes
  @param ListInterface<Zip> zipList: A list to store the zip codes
  @param Data d: An object to store the total businesses, closed businesses, and businesses open in the last year
  Runtime: O(n), where n is the length of the naicsList OR the zipList
