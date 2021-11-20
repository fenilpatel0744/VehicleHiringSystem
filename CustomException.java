/**
 * CustomException is used to handle custom exception
 * 
 * @author Fenil
 * @version 24 May 2021
 */
public class CustomException extends Exception 
{
    /**
     * Constructor of the class
     * 
     * @param errorMessage message of error
     */
    CustomException(String errorMessage)
    {
        super(errorMessage);
    }
}
