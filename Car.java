import java.util.ArrayList;

/**
 * The Car class is inherited from Vehicle class and contains some extra variables with implementation of abstract methods.
 * 
 * @author Fenil
 * @version 24 May 2021
 */
public class Car extends Vehicle 
{

    private String engineCapacity;

    /**
     * Constructor of class Car
     * 
     * @param data array of string contains data from text file
     * @throws CustomException 
     */
    public Car(String[] data) throws CustomException 
    {
        super(data);
        engineCapacity = data[6];

        if(engineCapacity.trim().equals(""))
        {
            throw new CustomException("Empty engine capacity is not allowed.");
        }
    }

    /**
     * Method is used to return capacity of engine
     * 
     * @return String return capacity of engine
     */
    public String getEngineCapacity() 
    {
        return engineCapacity;
    }

    /**
     * Method is used to set capacity of engine
     * 
     * @param engineCapacity capacity of engine
     */
    public void setEngineCapacity(String engineCapacity) 
    {
        this.engineCapacity = engineCapacity;
    }

    /*
     * Method is used to calculate late charges of car type reservation.
     * 
     * @see Vehicle#calculateLateCharges(int)
     */
    @Override
    public int calculateLateCharges(int hours) 
    {
        return hours * 10;
    }

    /*
     * Method is used to display details of object of this car.
     * 
     * @see Vehicle#display()
     */
    @Override
    public ArrayList<String> details() 
    {
        ArrayList<String> list = new ArrayList<String>();

        list.add("Car");
        list.add(getRegNumber());
        list.add(getModelName());
        list.add(getYearOfReg());
        list.add(getFuelType());
        list.add(engineCapacity);

        return list;
    }
}
