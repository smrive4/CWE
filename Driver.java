import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;

public class Driver {
  
    // A list of topics the user can choose from
    static HashMap<String, Integer> topics = new HashMap<>(); 
    // An ArrayList of all flashcard sets 
    static ArrayList<FlashCardSet> sets = new ArrayList<>();
    // a list of allowed inputs
    private static ArrayList<String> allowedInputs = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5"));
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the flashcard app");

        int menuOption;

        // display the menu and let user select option
        do {
            displayMenu();

            menuOption = getValidMenuOption();

            performSelectedOption(menuOption);
        } while (menuOption != 5);

        System.out.println("End of Program");
    }

    private static void displayMenu() {
        System.out.println("\n--------------------");
        System.out.println("\tMENU");
        System.out.println("--------------------");
        System.out.println("1. Retrieve a file of flashcards.");
        System.out.println("2. Flashcard quiz.");
        System.out.println("3. Create a new flashcard file.");
        System.out.println("4. Add a new flashcard to the pile.");
        System.out.println("5. Quit");
        System.out.println("--------------------");
        System.out.println("Enter option (#-#): ");
    }

    /*
     * Get a Valid Menu Option
     * 
     * @return an integer representing a valid menu option
     */
    private static int getValidMenuOption() {

        String userInput = scan.nextLine();
        String trash = "";

        // let user re-enter until they give a valid menu option
        while (!allowedInputs.contains(userInput)) {
            System.out.println("Option must be an number");
            userInput = scan.nextLine();
            displayMenu();
        }

        int menuOption;

        try {
            menuOption = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {

            // allowedInputs list has given a faulty number - redisplay the display menu
            System.out.println("Error has occurred. Closing flashcard app");
            return 5;
        }
        return menuOption;
    }

    /**
     * Perform the option selected by the user.
     */
    private static void performSelectedOption(int menuOption) {
        switch (menuOption) {
            case 1:
                retrieveFile();
                break;
            case 2:
                quiz();
                break;
            case 3:
                createNewFlashCardFile();
                break;
            case 4:
                addNewFlashCard();
                break;
            case 5:
                quit();
                break;
            case 6:
                
            default:
                break;
        }
    }

    private static void retrieveFile()
    {
        System.out.println("retrieving file...");
    }

    private static void quiz()
    {
        FlashCardSet set;
        String userInput;

        // Prompt User to Select Topic
        if(sets == null || sets.isEmpty())
        {
            // No Topics to choose from
            System.out.println("No Flash Card Topics to Choose From");
            return;
        }
        
        System.out.println("Select from the following topics: ");
        // Print out topics the user can choose from
        for(int i = 0; i < sets.size(); i++)
        {
            System.out.println(sets.get(i).getTopic());
        }
        System.out.print("Enter desired topic: ");
        userInput = scan.nextLine();

        // Check if topic is a valid option
        if(topics.containsKey(userInput))
            set = sets.get(topics.get(userInput));
        else{
            System.out.println("Invalid Topic");
            return;
        }

        // Quiz user on all the flashcards in the set
        for(int i = 0; i < set.getNumOfCards(); i++)
        {
            // Display Term
            System.out.println("Term: " + set.getCard(i).getTerm());

            // Get user's anwser
            System.out.print("Enter the definition: ");
            userInput = scan.nextLine();

            // Check user's anwser
            if(userInput.equalsIgnoreCase(set.getCard(i).getDef()))
                System.out.println("Correct!");
            else
            {
                System.out.println("Incorrect");
                System.out.println("Correct Defintion is : " + set.getCard(i).getDef());
            }
        }
    }

    private static void createNewFlashCardFile(){

        // get and validate topic name
        System.out.println("Enter new topic name:");
        String topicName = "";

        while (true) {
            topicName = scan.nextLine().trim();
    
            // Check if input is valid
            // The Topic must be an alphanumeric between one and twenty characters
            if (topicName.matches("^[a-zA-Z0-9]{1,20}$")) {
                break; // valid input
            } else {
                System.out.println("Invalid topic name. Please use only letters and numbers, max 20 characters.");
            }
        }

        //create new flashcard and add to set
        FlashCardSet newSet = new FlashCardSet(topicName, null);
        sets.add(newSet);

        // get valid user input
        String term = getValidFlashcardTextFromUser("Enter term side:");
        String definition = getValidFlashcardTextFromUser("Enter definition side");
        
        // FlashCard
    

    }

    // gets and validates user input for one side of a flashcard
    private static String getValidFlashcardTextFromUser(String prompt) {
        System.out.println(prompt);
        String userInput = "";
        while (true) {
            userInput = scan.nextLine().trim();
    
            // Check if input is valid
            // The Topic must be an alphanumeric between one and twenty characters
            if (userInput.matches("^.{1,200}$")) {
                break; // valid input
            } else {
                System.out.println("Invalid input. Card side must be between 1 and 200 characters and cannot contain any newlines");
            }
        }

        return userInput;

    }

    private static void addNewFlashCard(){
        System.out.println("Adding new flashcards");
    }

    private static void quit(){
        System.out.println("Saving...\n");
                // save history to file and then exit
                // serialize obj and write to file
                FileOutputStream outStream = null;
                ObjectOutputStream objStream = null;
                try {
                    // outStream = new FileOutputStream(historyFileName);
                    objStream = new ObjectOutputStream(outStream);
                    // objStream.writeObject(history);

                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());

                    // FIO14-J: Perform proper cleanup at program termination
                    // Err04-J: Do not complete abruptly from a finally block
                } finally {
                    try {
                        // EXP01-J: Do not use null in a case where an object is required
                        if (objStream != null)
                            objStream.close();
                    } catch (IOException e) {
                        System.err.println("Error saving to file: " + e);
                    }
                }
                System.out.println("Finished");
    }

    

}