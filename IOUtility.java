import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * This class is a utility class which defines methods to read different forms of user input.
 * 
 * @author Fenil
 * 
 */

public class IOUtility 
{

    private static final Scanner in = new Scanner(System.in);
    
    private IOUtility(){}

    /**
     * getString(String prompt) prompts the user for an input, reads it as a String object and returns it.
     * 
     * @param prompt A String which promts for user input
     * @return String An object which holds the user input
     */
    public static String getString(String prompt) 
    {
        System.out.print(prompt + " ");
        return in.nextLine();
    }

    /**
     * getDouble(String prompt) prompts the user for an input and parses the input to Double object.
     * 
     * @param prompt A String which promts for user input
     * @return Double An object which holds the user input
     */
    public static Double getDouble(String prompt) 
    {
        Double d = 0.00;
        while (true) 
        {
            try 
            {
                System.out.print(prompt + " ");
                d = Double.parseDouble(in.nextLine());
                break;
            } 
            catch (Exception e) 
            {
                System.out.println("Not a valid Double");
            }
        }
        return d;
    }

    /**
     * getLocalDateTime(String prompt) prompts the user for an input and parses the input to LocalDateTime object.
     * 
     * @param prompt A String which promts for user input
     * @return LocalDateTime An object which holds the user input
     */
    public static LocalDateTime getLocalDateTime(String prompt) 
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        while (true) 
        {
            try
            {
                System.out.print(prompt + "(dd-MM-yyyy HH:mm) ");
                localDateTime = LocalDateTime.parse(in.nextLine(),DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                return localDateTime;
            } 
            catch (Exception e) 
            {
                System.out.println("Invalid Date Format");
            }
        }
    }

    /**
     * getInteger(String prompt) prompts the user for an input and parses the input to Integer object.
     * 
     * @param prompt A String which promts for user input
     * @return Integer An object which holds the user input
     */
    public static Integer getInteger(String prompt) 
    {
        Integer i = 0;
        while (true) 
        {
            try 
            {
                System.out.print(prompt + " ");
                i = Integer.parseInt(in.nextLine());
                break;
            } catch (Exception e) 
            {
                System.out.println("Not a valid Integer");
            }
        }
        return i;
    }

    /**
     * Method is used to display given string on console
     * 
     * @param toPrint
     *            any string
     */
    public static void println(String toPrint) 
    {
        System.out.println(toPrint);
    }

    /**
     * Method is used to print a blank line.
     */
    public static void newLine() 
    {
        System.out.println();
    }

    /**
     * Method is used to convert string to LocalDateTime
     * 
     * @param prompt date in string format
     * @return localDateTime LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeForUI(String prompt) 
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        while (true) 
        {

            localDateTime = LocalDateTime.parse(prompt,DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            return localDateTime;

        }
    }
}
