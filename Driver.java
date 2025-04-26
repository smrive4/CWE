import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;

public class Driver {
    // File path to get history
    private static final String flashCardSetsFileName = "flashcardsets.ser";
    // A list of topics the user can choose from
    static HashMap<String, Integer> topics = new HashMap<>(); 
    // An ArrayList of all flashcard sets 
    static ArrayList<FlashCardSet> sets = new ArrayList<>();
    // a list of allowed inputs
    private static ArrayList<String> allowedInputs = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5"));
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        // Get any previous FlashCard Sets first
        sets = retrieveFlashCards();
        retrieveTopics();

        System.out.println("Welcome to the flashcard app");

        int menuOption;

        // display the menu and let user select option
        do {
            displayMenu();

            menuOption = getValidMenuOption();

            performSelectedOption(menuOption);
        } while (true); // Stay in loop unless user exits
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
            FlashCard currCard = set.getCard(i);

            // Compliant with CWE-252, check return value for null
            if(currCard == null)
                return;

            // Display Term
            System.out.println("Term: " + currCard.getTerm());

            // Get user's anwser
            System.out.print("Enter the definition: ");
            userInput = scan.nextLine();

            // Check user's anwser
            if(userInput.equalsIgnoreCase(currCard.getDef()))
                System.out.println("Correct!");
            else
            {
                System.out.println("Incorrect");
                System.out.println("Correct Defintion is : " + currCard.getDef());
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
        //Creat empty array list to ensure non null values in cloning
        FlashCardSet newSet = new FlashCardSet(topicName, new ArrayList<>());
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
        FlashCardSet set = null;
        String userInput;
        String term;
        String defintion;
        String topic = "";
        ArrayList<FlashCard> cards = new ArrayList<>();

        System.out.println("Select which topic to add to from the following topics or create a new topic: ");
        // Print out topics the user can choose from
        for(int i = 0; i < sets.size(); i++)
        {
            System.out.println(sets.get(i).getTopic());
        }
        System.out.print("Enter desired topic or a new topic: ");
        userInput = scan.nextLine();

        // Check if topic is a valid option
        if(topics.containsKey(userInput))
            set = sets.get(topics.get(userInput));
        else{
            // Else, create a new topic
            topic = userInput;
        }

        // Get FlashCard Term from User
        term = getValidFlashcardTextFromUser("Enter Term");

        // Get FlashCard Definition from User
        defintion = getValidFlashcardTextFromUser("Enter Definition");

        // Add FlashCard to either Existing Set or a new Set
        if(set != null)
        {
            set.addCard(new FlashCard(term, defintion));
        }
        else
        {
            cards.add(new FlashCard(term, defintion));
            sets.add(new FlashCardSet(topic, cards));
            topics.put(topic, sets.size() - 1);
        }
    }

    private static boolean quit(){
            // CWE-356
            System.out.print("Are you sure you want to quit and save your flashcards? (\nThe file will be locally stored and this could be dangerous if you don't trust this device.\n(yes/no): ");
            String confirmQuit = scan.nextLine();
            
            if (!confirmQuit.equalsIgnoreCase("yes")) {
                System.out.println("Quit cancelled. Returning to menu...");
                return false; 
            }

        System.out.println("Saving...\n");
        // save history to file and then exit
        // serialize obj and write to file
        FileOutputStream outStream = null;
        ObjectOutputStream objStream = null;
        try {
            outStream = new FileOutputStream(flashCardSetsFileName);
            objStream = new ObjectOutputStream(outStream);
            objStream.writeObject(sets);
        } catch (IOException e) { 
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (objStream != null)
                    objStream.close();
            } catch (IOException e) {
                System.err.println("Error saving to file: " + e);
            }
        }
        System.out.println("Finished.  End of program.");
        return true;
    }

    /**
     * Gets the history from the history.ser file
     * 
     * @return an array list of the user's history
     */
    @SuppressWarnings("unchecked")
    private static ArrayList<FlashCardSet> retrieveFlashCards() {
        ArrayList<FlashCardSet> deserializedObj = new ArrayList<>();

        // open history.ser and get the file
        try (FileInputStream inStream = new FileInputStream(flashCardSetsFileName);
                ObjectInputStream objStream = new ObjectInputStream(inStream)) {
            // get the object and cast it
            deserializedObj = (ArrayList<FlashCardSet>) objStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: retrieving history: " + e.getMessage());
        }

        return deserializedObj;
    }

    private static void retrieveTopics()
    {
        for(int i = 0; i < sets.size(); i++)
        {
            String topic = sets.get(i).getTopic();
            topics.put(topic, i);
        }
    }
}