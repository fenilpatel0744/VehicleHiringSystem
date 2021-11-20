/**
 * Customer class is used to store customer details and perform operation on it.
 * 
 * @author Fenil
 * @version 24 May 2021
 */
public class Customer 
{
    private int customerId;
    private String customerName;
    private String customerType;

    /**
     * Constructor of object of class Customer
     * 
     * @param CustomerDetails details of customers
     * @throws CustomException 
     */
    public Customer(String CustomerDetails) throws CustomException 
    {
        if(CustomerDetails.trim().length()==0)
        {
            throw new CustomException("Empty customer Details are not allowed.");
        }

        String[] CustomerDetail = CustomerDetails.split(", ");
        setCustomerId(Integer.parseInt(CustomerDetail[0]));
        setCustomerName(CustomerDetail[1]);
        setCustomerType(CustomerDetail[2]);
    }

    /**
     * Empty constructor of class
     */
    public Customer()
    {

    }

    /**
     * Method is used to set customerId
     * 
     * @param customerId id of customer
     */
    public void setCustomerId(int customerId) 
    {
        this.customerId = customerId;
    }

    /**
     * Method is used to return customerId
     * 
     * @return int id of customer
     */
    public int getCustomerId() 
    {
        return customerId;
    }

    /**
     * Method is used to set name of customer
     * 
     * @param customerName name of customer
     */
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    /**
     * Method is used to return name of customer
     * 
     * @return String name of customer
     */
    public String getCustomerName() 
    {
        return customerName;
    }

    /**
     * Method is used to set type of customer
     * 
     * @param customerType type of customer
     */
    public void setCustomerType(String customerType) 
    {
        this.customerType = customerType;
    }

    /**
     * Method is used to return type of customer
     * 
     * @return String type of customer
     */
    public String getCustomerType() 
    {
        return customerType;
    }
}
