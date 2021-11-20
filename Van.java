import java.util.ArrayList;
/**
 * The Van class is inherited from Vehicle class and contains some extra variables with implementation of abstract methods.
 * 
 * @author Fenil
 * 
 */
public class Van extends Vehicle
{
    private String loadCapacity;
    
    /**
     * Constructor of class Van
     * 
     * @param data array of string contains data from text file
     * @throws CustomException 
     */
    public Van(String[] data) throws CustomException 
    {
        super(data);
        loadCapacity=data[6];
        
         if(loadCapacity.trim().equals(""))
         {
        	throw new CustomException("Empty load capacity is not allowed.");
        }
    }

    /**
     * Method is used to return load capacity
     * 
     * @return String load Capacity
     */
    public String getLoadCapacity() 
    {
        return loadCapacity;
    }

    /**
     * Method is used to set load Capacity
     * @param loadCapacity load Capacity
     */
    public void setLoadCapacity(String loadCapacity) 
    {
        this.loadCapacity = loadCapacity;
    }

    /* 
     * Method is used to calculate late charges of van type reservation.
     * 
     * @see Vehicle#calculateLateCharges(int)
     */
    @Override
    public int calculateLateCharges(int hours) 
    {
        return  hours*15;  
    }

    /* 
     * Method is used to display details of object of Van
     * 
     * @see Vehicle#display()
     */
    @Override
    public ArrayList<String> details()
    { 
    	ArrayList<String> list = new ArrayList<String>();
    	
    	list.add("Van");
    	list.add(getRegNumber());
    	list.add(getModelName());
    	list.add(getYearOfReg());
    	list.add(getFuelType());
    	list.add(loadCapacity);
    	
       return list;
    }
}
