import java.io.Console;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {

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

    /**
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
            System.out.println("Option must be an number");
            displayMenu();
        }
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
        // Scanner scan = new Scanner(System.in);
        double radius;
        double height;
        double length;
        double width;
        switch (menuOption) {
            case 1:
                // perform menu option operation
                System.out.println("\nYou have selected to calculate the area of a Circle");
                System.out.print("Please input the radius of the circle: ");
                while (!scan.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double.");
                    scan.nextLine();
                }
                radius = scan.nextDouble();

                // Circle circle = new Circle(radius);
                // history.add(circle);
                // System.out.println("The area of the circle is: " + circle.getArea());
                break;
            case 2:
                // perform menu option operation
                System.out.println("You have selected to calculate the area of a Rectangle");
                System.out.print("Please input the length of the rectangle: ");
                while (!scan.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double.");
                    scan.nextLine();
                }
                length = scan.nextDouble();
                System.out.print("\nPlease input the width of the rectangle: ");
                while (!scan.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double.");
                    scan.nextLine();
                }
                width = scan.nextDouble();

                // Rectangle rectangle = new Rectangle(length, width);
                // history.add(rectangle);
                // System.out.println("The area of the rectangle is: " + rectangle.getArea());
                break;
            case 3:
                // perform menu option operation
                System.out.println("You have selected to calculate the volume of a Cylinder");
                System.out.print("Please input the radius of the cylinder: ");
                while (!scan.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double.");
                    scan.nextLine();
                }
                radius = scan.nextDouble();
                System.out.print("\nPlease input the height of the cylinder: ");
                while (!scan.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double.");
                    scan.nextLine();
                }
                height = scan.nextDouble();

                // Cylinder cylinder = new Cylinder(radius, height);
                // history.add(cylinder);
                // System.out.println("The volume of the cylinder is: " + cylinder.getVolume());

                break;
            case 4:
                // perform menu option operation
                System.out.println("You have selected to calculate the volume of a Rectangular Prism");
                System.out.print("Please input the height of the rectangular prism: ");
                while (!scan.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double.");
                    scan.nextLine();
                }
                height = scan.nextDouble();
                System.out.print("\nPlease input the length of the rectangular prism: ");
                while (!scan.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double.");
                    scan.nextLine();
                }
                length = scan.nextDouble();
                System.out.print("\nPlease input the width of the rectangular prism: ");
                while (!scan.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double.");
                    scan.nextLine();
                }
                width = scan.nextDouble();

                // RectangularPrism rectangular_Prism = new RectangularPrism(height, width,
                // length);
                // history.add(rectangular_Prism);
                // System.out.println("The volume of the rectangular prism is: " +
                // rectangular_Prism.getVolume());
                break;
            case 5:
                // // print out history
                // for (MyMath item : history) {
                // System.out.println(item.getHistory());
                // System.out.println("\n");
                // }
                break;
            case 6:
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
            default:
                break;
        }
    }

}