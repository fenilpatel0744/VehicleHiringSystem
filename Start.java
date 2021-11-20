import java.io.IOException;
/**
 * Start class is useful to run this project
 * 
 * @author Fenil
 * 
 */
public class Start 
{
	/**
	 * Program execution is start from this method.
	 * 
	 * @param args Unused
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{
		Company company = new Company("Demo");
		while (true) 
		{
			try 
			{
				switch (menu()) 
				{
				case 1:
					UserInterface consoleApp = new UserInterface(company);
					consoleApp.run();
					break;
				case 2:
					new GUI(company);
					break;
				case 3:
					return;
				default:
					System.out.println("Invalid option");
					break;
				}
			} 
			catch (Exception exception) 
			{
				System.out.println("You have entered wrong input");
			}
		}
	}

	public static int menu() 
	{
		System.out.println("1. Console based Application");
		System.out.println("2. GUI based Application");
		System.out.println("3. Exit");
		IOUtility.newLine();
		return IOUtility.getInteger("Select option:");
	}

}
