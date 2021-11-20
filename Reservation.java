import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * The Reservation class is inherited from Vehicle class and contains some extra variables with implementation of abstract methods.
 * 
 * @author Fenil
 * 
 */
public class Reservation 
{ 
    private Customer customer;
    private Vehicle vehicle;
    private int reservationPeriod;
    private LocalDateTime reservDateTime;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;
    private String pickupLocation;

    /**
     * Empty constructor of Reservation
     */
    public Reservation() 
    {
    }

    /**
     * Constructor for reservation class
     * 
     * @param customer object of Customer class
     * @param vehicle object of Vehicle class
     * @param resDateTime reservation date and time
     * @param period total time for reservation
     * @param pickupDateTime date and time of pickup
     * @param pickupLocation location of pickup 
     */
    public Reservation(Customer customer,Vehicle vehicle,LocalDateTime resDateTime, int period, LocalDateTime pickupDateTime,String pickupLocation) 
    {
        this.customer=customer;
        this.vehicle=vehicle;
        this.reservDateTime=resDateTime;
        this.reservationPeriod=period;
        this.pickupDateTime=pickupDateTime;
        this.pickupLocation=pickupLocation;
    }

    public static Comparator<Reservation> dateComparator = new Comparator<Reservation>() 
        {

            /* 
             * Method is used to compare two object of Reservation
             * 
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare(Reservation r1, Reservation r2) 
            {
                return r1.pickupDateTime.compareTo(r2.pickupDateTime);
            }
        };

    /**
     * Method is used to return object of Customer 
     * 
     * @return Customer object of Customer 
     */
    public Customer getCustomer() 
    {
        return customer;
    }

    /**
     * Method is used to set the object of Customer 
     * 
     * @param customer object of Customer 
     */
    public void setCustomer(Customer customer) 
    {
        this.customer = customer;
    }

    /**
     * Method is used to return object of Vehicle 
     * 
     * @return Vehicle object of Vehicle 
     */
    public Vehicle getVehicle() 
    {
        return vehicle;
    }

    /**
     * Method is used to set the object of Vehicle 
     * 
     * @param vehicle object of Vehicle 
     */
    public void setVehicle(Vehicle vehicle) 
    {
        this.vehicle = vehicle;
    }

    /**
     * Method is used to return total reservation period in hour
     * 
     * @return int reservation period 
     */
    public int getReservationPeriod() 
    {
        return reservationPeriod;
    }

    /**
     * Method is used to set  total reservation period in hour
     * 
     * @param reservationPeriod reservation period 
     */
    public void setReservationPeriod(int reservationPeriod) 
    {
        this.reservationPeriod = reservationPeriod;
    }

    /**
     * Method is used to return reservation date and time
     * 
     * @return LocalDateTime reservation date and time
     */
    public LocalDateTime getReservDateTime() 
    {
        return reservDateTime;
    }

    /**
     * Method is used to set reservation date and time
     * 
     * @param reservDateTime reservation date and time
     */
    public void setReservDateTime(LocalDateTime reservDateTime) 
    {
        this.reservDateTime = reservDateTime;
    }

    /**
     * Method is used to return pickup data and time
     * 
     * @return LocalDateTime pickup data and time
     */
    public LocalDateTime getPickupDateTime() 
    {
        return pickupDateTime;
    }

    /**
     * Method is used to set pickup data and time
     * 
     * @param pickupDateTime pickup data and time
     */
    public void setPickupDateTime(LocalDateTime pickupDateTime) 
    {
        this.pickupDateTime = pickupDateTime;
    }

    /**
     * Method is used to return returning date and time of vehicle
     * 
     * @return LocalDateTime returning date and time of vehicle
     */
    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    /**
     * Method is used to set returning date and time of vehicle
     * 
     * @param returnDateTime return date and time of vehicle
     */
    public void setReturnDateTime(LocalDateTime returnDateTime) 
    {
        this.returnDateTime = returnDateTime;
    }

    /**
     * Method is used to return pickup location
     * 
     * @return String pickup location
     */
    public String getPickupLocation() 
    {
        return pickupLocation;
    }

    /**
     * Method is used to set pickup location
     * 
     * @param pickupLocation pickup location
     */
    public void setPickupLocation(String pickupLocation) 
    {
        this.pickupLocation = pickupLocation;
    }
}

