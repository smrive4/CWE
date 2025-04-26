import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.logging.Logger;


public class FlashcardDriver {
    // File path to get history
    private static final String flashCardSetsFileName = "flashcardsets.ser";
    // A list of topics the user can choose from
    static HashMap<String, Integer> topics = new HashMap<>();
    // An ArrayList of all flashcard sets
    static ArrayList<FlashCardSet> sets = new ArrayList<>();
    // A list of allowed inputs (updated based on user role)
    private static ArrayList<String> allowedInputs;
    private static Scanner scan = new Scanner(System.in);
    // Store the user's role
    private static String userRole;
    // create the logging class
    static Logger logger = Logger.getLogger(FlashcardDriver.class.getName());
    // shows if system is currently in the process of adding a flashcard
    private static boolean isAddingCard = false;

    public static void main(String[] args) {
        // Get any previous FlashCard Sets first
        sets = retrieveFlashCards();
        retrieveTopics();

        // Prompt user to select role
        selectUserRole();

        // Initialize allowed inputs based on role
        updateAllowedInputs();

        System.out.println("Welcome to the flashcard app");

        int menuOption;

        // Display the menu and let user select option
        do {
            displayMenu();

            menuOption = getValidMenuOption();

            performSelectedOption(menuOption);
        } while (true); //run until user exits

    }
    //uses proper input validation here - CWE20
    private static void selectUserRole() {
        System.out.println("Select your role:");
        System.out.println("1. Simple User (Quiz and Retrieve only)");
        System.out.println("2. Editor (All actions)");
        System.out.print("Enter option (1 or 2): ");

        String roleInput;
        while (true) {
            roleInput = scan.nextLine().trim();
            if (roleInput.equals("1")) {
                userRole = "simple";
                break;
            } else if (roleInput.equals("2")) {
                userRole = "editor";
                break;
            } else {
                System.out.println("Invalid input. Please enter 1 or 2.");
                System.out.print("Enter option (1 or 2): ");
            }
        }
    }

    private static void updateAllowedInputs() {
        if (userRole.equals("simple")) {
            allowedInputs = new ArrayList<>(Arrays.asList("1", "2", "5"));
        } else {
            allowedInputs = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        }
    }

    private static void displayMenu() {
        System.out.println("\n--------------------");
        System.out.println("\tMENU");
        System.out.println("--------------------");
        System.out.println("1. Retrieve a file of flashcards.");
        System.out.println("2. Flashcard quiz.");
        //Compliant with CWE-272
        if (userRole.equals("editor")) {
            System.out.println("3. Create a new flashcard file.");
            System.out.println("4. Add a new flashcard to the pile.");
        }
        System.out.println("5. Quit");
        System.out.println("--------------------");
        System.out.println("Enter option (#): ");
    }

    /*
     * Get a Valid Menu Option
     *
     * @return an integer representing a valid menu option
     */
    private static int getValidMenuOption() {
        String userInput = scan.nextLine();

        // Let user re-enter until they give a valid menu option
        while (!allowedInputs.contains(userInput)) {
            System.out.println("Option must be a valid number for your role.");
            displayMenu();
            userInput = scan.nextLine();
        }

        int menuOption;

        try {
            menuOption = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            // AllowedInputs list has given a faulty number - redisplay the menu
            System.out.println("Error has occurred. Closing flashcard app");
            logger.info("AllowedInputs list has given a faulty number");
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
                //Compliant with CWE-279
                if (userRole.equals("editor")) {
                    createNewFlashCardFile();
                } else {
                    System.out.println("Access denied: Simple users cannot create flashcard files.");
                }
                break;
            case 4:
                if (userRole.equals("editor")) {
                    addNewFlashCard();
                } else {
                    System.out.println("Access denied: Simple users cannot add flashcards.");
                }
                break;
            case 5:
                if(userRole.equals("editor")) {
                    quit();
                }
                else{
                    System.out.println("Finished");
                }
                break;
            default:
                break;
        }
    }

    private static void retrieveFile() {
        FlashCardSet set;
        String userInput;

        // Prompt User to Select Topic
        if (sets == null || sets.isEmpty()) {
            // No Topics to choose from
            System.out.println("No Flash Card Topics to Choose From");
            return;
        }

        System.out.println("Select from the following topics: ");
        // Print out topics the user can choose from
        for (int i = 0; i < sets.size(); i++) {
            System.out.println(sets.get(i).getTopic());
        }
        System.out.print("Enter desired topic: ");
        userInput = scan.nextLine();

        // Check if topic is a valid option
        if (topics.containsKey(userInput)) {
            set = sets.get(topics.get(userInput));
        } else {
            System.out.println("Invalid Topic");
            return;
        }

        //list all of the terms and definitions
        for (int i = 0; i < set.getNumOfCards(); i++) {
            FlashCard currCard = set.getCard(i);

            // Compliant with CWE-252, check return value for null
            if (currCard == null) {
                return;
            }

            // Display Term
            System.out.println("Term: " + currCard.getTerm());
            System.out.println("Definition: " + currCard.getDef());

            System.out.println();
        }
    }

    private static void quiz() {
        FlashCardSet set;
        String userInput;

        // Prompt User to Select Topic
        if (sets == null || sets.isEmpty()) {
            // No Topics to choose from
            System.out.println("No Flash Card Topics to Choose From");
            return;
        }

        System.out.println("Select from the following topics: ");
        // Print out topics the user can choose from
        for (int i = 0; i < sets.size(); i++) {
            System.out.println(sets.get(i).getTopic());
        }
        System.out.print("Enter desired topic: ");
        userInput = scan.nextLine();

        // Check if topic is a valid option
        if (topics.containsKey(userInput)) {
            set = sets.get(topics.get(userInput));
        } else {
            System.out.println("Invalid Topic");
            return;
        }

        // Quiz user on all the flashcards in the set
        for (int i = 0; i < set.getNumOfCards(); i++) {
            FlashCard currCard = set.getCard(i);

            // Compliant with CWE-252, check return value for null
            if (currCard == null) {
                return;
            }

            // Display Term
            System.out.println("Term: " + currCard.getTerm());

            // Get user's answer
            System.out.print("Enter the definition: ");
            userInput = scan.nextLine();

            // Check user's answer
            if (userInput.equalsIgnoreCase(currCard.getDef())) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect");
                System.out.println("Correct Definition is: " + currCard.getDef());
            }
        }
    }
    //proper input validaiton - CWE-20
    private static void createNewFlashCardFile() {
        //Complies with CWE-279
        if(userRole.equals("editor")) {
            // Get and validate topic name
            System.out.println("Enter new topic name:");
            String topicName = "";

            while (true) {
                topicName = scan.nextLine().trim();

                // Check if input is valid
                // The Topic must be alphanumeric between one and twenty characters
                //complies with CWE-625
                if (topicName.matches("^[a-zA-Z0-9]{1,20}$")) {
                    break; // valid input
                } else {
                    System.out.println("Invalid topic name. Please use only letters and numbers, max 20 characters.");
                }
            }

            // Create new flashcard set and add to sets
            FlashCardSet newSet = new FlashCardSet(topicName, new ArrayList<FlashCard>());
            sets.add(newSet);
            topics.put(topicName, sets.size() - 1);

            // Get valid user input for the first flashcard
            String term = getValidFlashcardTextFromUser("Enter term side:");
            String definition = getValidFlashcardTextFromUser("Enter definition side");
            // Validate parameters before creating flashcard - CWE233
            if (term == null || term.trim().isEmpty() || definition == null || definition.trim().isEmpty()) {
                System.out.println("Error: Term and Definition cannot be empty.");
                return; 
            }
            // Create and add the flashcard
            FlashCard newCard = new FlashCard(term, definition);
            newSet.addCard(newCard);
            System.out.println("Flashcard added successfully.");
            logger.info("new card file added");
        }
        else{
            logger.info("user does not have access to this");
        }
    }

    // Gets and validates user input for one side of a flashcard
    //Ensures proper inputs - using input validation - CWE20
    private static String getValidFlashcardTextFromUser(String prompt) {
        System.out.println(prompt);
        String userInput = "";
        while (true) {
            userInput = scan.nextLine().trim();

            // Check if input is valid
            // Must be between 1 and 200 characters and cannot contain newlines
            if (userInput.matches("^.{1,200}$")) {
                break; // valid input
            } else {
                System.out.println("Invalid input. Card side must be between 1 and 200 characters and cannot contain newlines.");
            }
        }

        return userInput;
    }

    private static void addNewFlashCard() {
        if (isAddingCard) {
            System.out.println("Flashcard creation already in progress. Please wait...");
            return;
        }
        isAddingCard = true;
        try{
        if(userRole.equals("editor")) {
            FlashCardSet set = null;
            String userInput;
            String term;
            String definition;
            String topic = "";
            ArrayList<FlashCard> cards = new ArrayList<>();

            System.out.println("Select which topic to add to from the following topics or create a new topic: ");
            // Print out topics the user can choose from
            for (int i = 0; i < sets.size(); i++) {
                System.out.println(sets.get(i).getTopic());
            }
            System.out.print("Enter desired topic or a new topic: ");
            userInput = scan.nextLine();

            // Validate new topic name if it doesn't exist
            if (!topics.containsKey(userInput)) {
                topic = userInput;
                while (!topic.matches("^[a-zA-Z0-9]{1,20}$")) {
                    System.out.println("Invalid topic name. Please use only letters and numbers, max 20 characters.");
                    System.out.print("Enter desired topic or a new topic: ");
                    topic = scan.nextLine();
                }
            }

            // Check if topic is a valid option
            if (topics.containsKey(userInput)) {
                set = sets.get(topics.get(userInput));
            }

            // Get FlashCard Term from User
            term = getValidFlashcardTextFromUser("Enter Term");

            // Get FlashCard Definition from User
            definition = getValidFlashcardTextFromUser("Enter Definition");

            // Validate parameters before adding flashcard - CWE-233
            if (term == null || term.trim().isEmpty() || definition == null || definition.trim().isEmpty()) {
                System.out.println("Error: Term and Definition cannot be empty.");
                return; // abort flashcard creation if invalid
            }
            // Add FlashCard to either Existing Set or a new Set
            if (set != null) {
                set.addCard(new FlashCard(term, definition));
            } else {
                cards.add(new FlashCard(term, definition));
                sets.add(new FlashCardSet(topic, cards));
                topics.put(topic, sets.size() - 1);
            }
            System.out.println("Flashcard added successfully.");
            logger.info("New flashcard has been added");
        }
        else{
            logger.info("user does not have access to this");
        }
        isAddingCard=false;}
        finally {
            isAddingCard = false; // always reset to false
        }
    }
    private static boolean quit() {
        System.out.print("Are you sure you want to quit and save your flashcards? (\nThe file will be locally stored and this could be dangerous if you don't trust this device.\n(yes/no): ");
        String confirmQuit = scan.nextLine();
    
        if (!confirmQuit.equalsIgnoreCase("yes")) {
            System.out.println("Quit cancelled. Returning to menu...");
            return false;
        }
    
        System.out.println("Saving...\n");
        FileOutputStream outStream = null;
        ObjectOutputStream objStream = null;
        try {
            outStream = new FileOutputStream(flashCardSetsFileName);
            objStream = new ObjectOutputStream(outStream);
            objStream.writeObject(sets);
            logger.info("saving data to file");
        } catch (IOException e) {
            logger.info("Error: " + e.getMessage());
        } finally {
            try {
                if (objStream != null) {
                    objStream.close();
                }
            } catch (IOException e) {
                logger.info("Error saving to file: " + e);
            }
            try {
                if (scan != null) {
                    scan.close();
                }
            } catch (Exception e) {
                logger.info("Error closing scanner: " + e);
            }
        }
        System.out.println("Finished. End of program.");
        return true;
    }
    

    /**
     * Gets the history from the flashcardsets.ser file
     *
     * @return an array list of the user's history
     */
    @SuppressWarnings("unchecked")
    private static ArrayList<FlashCardSet> retrieveFlashCards() {
        ArrayList<FlashCardSet> deserializedObj = new ArrayList<>();

        // Open flashcardsets.ser and get the file
        try (FileInputStream inStream = new FileInputStream(flashCardSetsFileName);
             ObjectInputStream objStream = new ObjectInputStream(inStream)) {
            // Get the object and cast it
            deserializedObj = (ArrayList<FlashCardSet>) objStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //System.err.println("Error retrieving history: " + e.getMessage());
            logger.info("Error retrieving history: " + e.getMessage());
            System.out.println("The program was terminated because no serialization file was found");
            //Complies with CWE-390
            System.out.println("A new serialization file will be created, and the program will be able to be used");
            quit();
            System.exit(1);
        }

        return deserializedObj;
    }

    private static void retrieveTopics() {
        for (int i = 0; i < sets.size(); i++) {
            String topic = sets.get(i).getTopic();
            topics.put(topic, i);
        }
    }
}