import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Driver {

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

  /**
     * Get a Valid Menu Option
     * 
     * @return an integer representing a valid menu option
     */
    private static int getValidMenuOption() {
        Scanner scan = new Scanner(System.in);
        int menuOption;
        String trash;

        // Ensure menuOption is a integer
        while (!scan.hasNextInt()) {
            System.out.println("Option must be an number");
            trash = scan.nextLine();
            displayMenu();
        }
        menuOption = scan.nextInt();
        // Ensure menuOption falls within the valid range of options
        while (menuOption > 5 || menuOption < 1) {
            System.out.println("Option must be one of the listed options");
            displayMenu();
            // Ensure menuOption is a integer
            while (!scan.hasNextInt()) {
                System.out.println("Option must be an number");
                trash = scan.nextLine();
                displayMenu();
            }
            menuOption = scan.nextInt();
        }
        trash = scan.nextLine();

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

    }

    private static void quiz()
    {

    }

    private static void createNewFlashCardFile(){

    }

    private static void addNewFlashCard(){

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