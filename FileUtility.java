import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is useful to read data from file and write data into file.
 * 
 * @author Fenil
 * @version 24 May 2021
 */
public class FileUtility 
{
    /**
     * Method is used to read data from file
     * 
     * @param fileName name of file
     * @return ArrayList<String> List of data from file
     * @throws IOException
     */
    public static ArrayList<String> readFromFile(String fileName)throws IOException 
    {
        ArrayList<String> fileDataList = new ArrayList<>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        while ((line = br.readLine()) != null) 
        {
            fileDataList.add(line);
        }
        return fileDataList;
    }

    /**
     * Method is used to writedata into the file.
     * 
     * @param fileName name of file
     * @param datadetails
     * @throws IOException
     */
    public static void writeToFile(String fileName, ArrayList<String> data) throws IOException 
    {

        FileWriter f = new FileWriter(new File(fileName), false);
        for (String line : data) 
        {
            f.write(line + "\n");
        }
        f.close();
    }

    /**
     * Method is used to write error message in file
     * 
     * @param errorMessage error message
     */
    public static void errorLogger(String errorMessage) 
    {
        String fileName = "LoggedErrors.txt";
        try 
        {
            FileWriter f = new FileWriter(new File(fileName), true);
            f.write(errorMessage + "\n");
            f.close();
        } 
        catch (IOException ex) 
        {
            System.out.println("File Not Found");
        }
    }
}
