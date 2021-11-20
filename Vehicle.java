import java.util.ArrayList;
/**
 * Vehicle class is abstract class which contains basic details of vehicle.
 * 
 * @author Fenil
 *
 */
public abstract class Vehicle 
{
    private String regNumber;
    private String modelName;
    private String yearOfReg;
    private String fuelType;
    private boolean availability;

    public Vehicle()
    {
    	
    }
    
    /**
     * Constructor of class Vehicle
     * 
     * @param data array of string contains data from text file
     */
    public Vehicle(String[] data)  throws CustomException
    {
        regNumber = data[1];
        modelName = data[2];
        yearOfReg = data[3];
        fuelType = data[4];
        availability = Boolean.parseBoolean(data[5]);
        
        if(regNumber.trim().equals(""))
        {
        	throw new CustomException("Empty registration number is not allowed.");
        }
        else if(modelName.trim().equals(""))
        {
        	throw new CustomException("Empty model name is not allowed.");

        }
        else if(yearOfReg.trim().equals(""))
        {
        	throw new CustomException("Empty year of registration is not allowed.");
        }
        else if(fuelType.trim().equals(""))
        {
        	throw new CustomException("Empty fuel type is not allowed.");
        }
       
    }

    /**
     * Method is used to return registration number of vehicle
     * 
     * @return String registration number of vehicle
     */
    public String getRegNumber() 
    {
        return regNumber;
    }

    /**
     * Method is used to set registration number of vehicle
     * 
     * @param regNumber registration number of vehicle
     */
    public void setRegNumber(String regNumber) 
    {
        this.regNumber = regNumber;
    }

    /**
     * Method is used to return model name of vehicle 
     * 
     * @return String model name of vehicle 
     */
    public String getModelName() 
    {
        return modelName;
    }

    /**
     * Method is used to set model name of vehicle 
     * 
     * @param modelName model name of vehicle 
     */
    public void setModelName(String modelName) 
    {
        this.modelName = modelName;
    }

    /**
     * Method is used to return registration year of vehicle
     * 
     * @return String registration year of vehicle
     */
    public String getYearOfReg() {
        return yearOfReg;
    }

    /**
     * Method is used to set registration year of vehicle
     * 
     * @param yearOfReg registration year of vehicle
     */
    public void setYearOfReg(String yearOfReg) 
    {
        this.yearOfReg = yearOfReg;
    }

    /**
     * Method is used to return fuel type 
     * 
     * @return String fuel type
     */
    public String getFuelType() 
    {
        return fuelType;
    }

    /**
     * Method is used to set fuel type
     * 
     * @param fueltype fuel type
     */
    public void setFuelType(String fuelType) 
    {
        this.fuelType = fuelType;
    }

    /**
     * Method is used to return availability of vehicle
     * 
     * @return boolean availability of vehicle
     */
    public boolean isAvailability() 
    {
        return availability;
    }

    /**
     * Method is used to set availability of vehicle
     * 
     * @param availability availability of vehicle
     */
    public void setAvailability(boolean availability) 
    {
        this.availability = availability;
    }

    /**
     * Abstract method to calculate late charges of reservation
     * 
     * @param hours total late returning hours
     * @return int penulty charge
     */
    public abstract int calculateLateCharges(int hours);

    /**
     * Abstract method to display details of reservation
     * @return 
     */
    public abstract ArrayList<String> details();

}
