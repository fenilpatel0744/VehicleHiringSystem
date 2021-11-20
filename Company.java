import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Company class is used to perform specific task related to business logic based on customer requirement.
 * 
 * @author Fenil
 * @version 24 May 2021
 */
public class Company 
{
    private String companyName;
    private ArrayList<Customer> customerList;
    private ArrayList<Vehicle> vehicleList;
    private ArrayList<Reservation> reservationList;
    private ArrayList<String> read;

    /**
     * Constructor of class Company
     * 
     * @param companyName name of company
     * @throws IOException
     */
    public Company(String companyName) throws IOException 
    {
        this.companyName = companyName;
        vehicleList = new ArrayList<Vehicle>();
        customerList = new ArrayList<Customer>();
        reservationList = new ArrayList<Reservation>();

    }

    /**
     * Method is used to set name of company
     * 
     * @param companyName name of compaany
     */
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    /**
     * Method is used to return name of company
     * 
     * @return String name of Company
     */
    public String getCompanyName() 
    {
        return companyName;
    }

    /**
     * Method is used to return list of reservation
     * 
     * @return ArrayList<Reservation> list of reservation
     */ 
    public ArrayList<Reservation> getReservationList() 
    {
        return reservationList;
    }

    /**
     * Method is used to set list of reservation
     * 
     * @param reservationList list of reservation
     */
    public void setReservationList(ArrayList<Reservation> reservationList) 
    {
        this.reservationList = reservationList;
    }

    /**
     * Method is used to return list of customer
     * 
     * @return ArrayList<Customer> list of customer
     */
    public ArrayList<Customer> getCustomerList() 
    {
        return customerList;
    }

    /**
     * Method is used to set list of customer
     * 
     * @param customerList list of customer
     */
    public void setCustomerList(ArrayList<Customer> customerList) 
    {
        this.customerList = customerList;
    }

    /**
     * Method is used to return list of vehicle
     * 
     * @return ArrayList<Vehicle> list of customer
     */
    public ArrayList<Vehicle> getVehicleList() 
    {
        return vehicleList;
    }

    /**
     * Method is used to set list of vehicle
     * 
     * @param vehicleList list of vehicle
     */
    public void setVehicleList(ArrayList<Vehicle> vehicleList) 
    {
        this.vehicleList = vehicleList;
    }

    /**
     * Method is used to fetch all customer details when program execution  start.
     * 
     * @throws IOException
     * @throws CustomException 
     */
    public void loadCustomer(String file) throws IOException, CustomException 
    {
        Customer customer;
        customerList.clear();
        read = FileUtility.readFromFile(file);

        for (String readFromList : read) 
        {
            customer = new Customer(readFromList);
            customerList.add(customer);
        }
    }


    /**
     * Method is used to fetch all vehicle details when program execution start.
     * 
     * @throws IOException
     * @throws CustomException 
     */
    public void loadVehicle(String file) throws IOException, CustomException 
    {
        Vehicle vehicle;
        String[] data;
        vehicleList.clear();
        read = FileUtility.readFromFile(file);

        for (String readFromList : read) 
        {
            data = readFromList.split(",");
            if (data[0].compareTo("car") == 0) {
                vehicle = new Car(data);
            } else {
                vehicle = new Van(data);
            }
            vehicleList.add(vehicle);
        }

    }

    /**
     * Method is used to fetch all reservation details when program executionstart.
     * 
     * @throws IOException
     */
    public void loadReservation(String file) throws IOException 
    {
        Reservation reservation;
        String[] data;
        reservationList.clear();
        read = FileUtility.readFromFile(file);

        if (!read.isEmpty() && !read.equals("") && !(read.size() == 0)) 
        {
            for (String readfFromList : read) 
            {
                data = readfFromList.split(",");
                reservation = new Reservation();
                for (Customer cust : customerList)
                {
                    if (cust.getCustomerId() == Integer.parseInt(data[0]))
                    {
                        reservation.setCustomer(cust);
                        break;
                    }
                }
                for (Vehicle veh : vehicleList) 
                {
                    if (veh.getRegNumber().compareTo(data[1]) == 0) 
                    {
                        reservation.setVehicle(veh);
                        break;
                    }
                }

                reservation.setReservDateTime(LocalDateTime.parse(data[2]));
                reservation.setReservationPeriod(Integer.parseInt(data[3]));
                reservation.setPickupLocation(data[4]);
                reservation.setPickupDateTime(LocalDateTime.parse(data[5]));

                if (data[6].compareTo("No") == 0) {
                    reservation.setReturnDateTime(null);
                } else {
                    reservation.setReturnDateTime(LocalDateTime.parse(data[6]));
                }
                reservationList.add(reservation);
            }
        }
    }

    /**
     * Method is used to fetch all available vehicles
     * 
     * @return ArrayList<Vehicle> list of available vehicles
     */
    public ArrayList<Vehicle> showAvailableVehicles() 
    {
        ArrayList<Vehicle> availableVehicles = new ArrayList<Vehicle>();
        for (Vehicle vehicle : vehicleList) 
        {
            if (vehicle.isAvailability()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    /**
     * Method is used to reserve vehicle based on user input.
     * 
     * @throws CustomException
     */
    public boolean reserveVehicle(Customer customer, Vehicle vehicle,LocalDateTime pickupDateTime, int period, String pickupLocation, LocalDateTime reserDateTime) throws CustomException 
    {
        boolean flag = false;
        Reservation reservation = new Reservation(customer, vehicle,reserDateTime, period, pickupDateTime, pickupLocation);

        if (reservation == null) 
        {
            throw new CustomException("You can not enter empty value while reservation.");
        }
        else
        {
            boolean flagforReservatiom = reservationList.add(reservation);
            if (flagforReservatiom) 
            {
                flag = true;
            }
            return flag;
        }

    }

    /**
     * Method is used to find list of not returned vehicle on time
     * 
     * @return ArrayList<Reservation> list of not returned vehicle on time
     */
    public ArrayList<Reservation> findNotReturnVehicle() 
    {
        ArrayList<Reservation> notReturnedVehicleList = new ArrayList<Reservation>();

        for (Reservation reservation : reservationList) {
            if (LocalDateTime.now().isAfter(
                reservation.getPickupDateTime().plusHours(
                    reservation.getReservationPeriod()))) {
                notReturnedVehicleList.add(reservation);
            }
        }
        return notReturnedVehicleList;
    }

    /**
     * Method is used to return vehicle with total reservation charges based on
     * customer reservation detail
     * 
     * @param customer_id ID of customer
     * @param vehicle_id  reservation number of vehicle
     * @param reservationDateTime date and time of reservation
     * @return int total charge for reservation
     */
    public int returnVehicle(int customer_id, String vehicle_id,LocalDateTime reservationDateTime) 
    {
        int status = 0;
        int totalChargeWithPanelty = 0;
        int totalCharge = 0;
        int latePanelty = 0;

        for (Reservation reservation : reservationList) 
        {
            if (reservation.getCustomer().getCustomerId() == customer_id && reservation.getVehicle().getRegNumber().compareTo(vehicle_id) == 0
            && reservationDateTime.isEqual(reservation.getPickupDateTime())) 
            {
                reservation.setReturnDateTime(LocalDateTime.now());
                if (reservation.getVehicle() instanceof Van) 
                {
                    totalCharge = reservation.getReservationPeriod() * 15;
                }
                if (reservation.getVehicle() instanceof Car) 
                {
                    totalCharge = reservation.getReservationPeriod() * 10;
                }

                if (Duration.between(reservation.getPickupDateTime().plusHours(reservation.getReservationPeriod()),reservationDateTime).toHours() >= 1) 
                {
                    latePanelty = reservation.getVehicle().calculateLateCharges((int) Duration
                        .between(reservation.getPickupDateTime().plusHours(reservation.getReservationPeriod()), reservationDateTime).toHours());
                }
                totalChargeWithPanelty = totalCharge + latePanelty;
                status = 1;
            }
        }
        if (status == 0) {
            return 0;
        } else {
            return totalChargeWithPanelty;
        }

    }

    /**
     * This method is used to find allowed hour for reservation for given customer
     * 
     * @param customer object of Customer
     * @return int allowed hours
     */
    public int findAllowedHours(Customer customer) 
    {
        int hoursAllow;

        if (customer.getCustomerType().equals("Budget")) 
        {
            hoursAllow = 1;
        } else {
            hoursAllow = 5;
        }
        return hoursAllow;
    }

    /**
     * Method is used to check validation for pickup date and time like vehicle is available or not and reservation can be done before 30 days or not
     * 
     * @param pickupDateTime pickup date and time of vehicle
     * @param hoursAllow allowed hours for reservation
     * @param reserDateTime date and time of reservation
     * @return boolean flag
     */
    public boolean checkForPickUpDateTime(LocalDateTime pickupDateTime,int hoursAllow, LocalDateTime reserDateTime) 
    {
        boolean flag;

        if (pickupDateTime.isAfter(reserDateTime)&& pickupDateTime.isBefore(reserDateTime.plusDays(31))&& !(pickupDateTime.isBefore(reserDateTime.plusHours(hoursAllow)))) 
        {
            flag = true;
        } 
        else {
            flag = false;
        }
        return flag;
    }

    /**
     * Method is used to check vehicle is available for not while reservation
     * 
     * @param vehicle object of Vehicle
     * @param pickupDateTime pickup date and time of vehicle
     * @param period period of reservation
     * @return boolean flag
     */
    public boolean checkForVehicleAvailability(Vehicle vehicle,LocalDateTime pickupDateTime, int period) 
    {
        boolean flag = true;

        for (Reservation reservation : reservationList) 
        {
            if (reservation.getVehicle().getRegNumber().compareTo(vehicle.getRegNumber()) == 0) 
            {
                if (!(pickupDateTime.isAfter(reservation.getPickupDateTime().plusHours(reservation.getReservationPeriod())) || pickupDateTime
                    .plusHours(period).isBefore(reservation.getPickupDateTime()))) 
                    {
                    flag = false;
                } 
                else 
                {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * Method is used to velidate reservation period
     * 
     * @param customer object of Customer
     * @param reservationPeriod reservation period in hours
     * @return boolean flag
     */
    public boolean checkForReservationPeriod(Customer customer,int reservationPeriod) 
    {
        boolean flag = false;

        if (reservationPeriod > 15) 
        {
            if (customer.getCustomerType().compareTo("Premium") == 0&& reservationPeriod <= 20) 
            {
                flag = true;
            } 
            else 
            {
                flag = false;
            }
        }
        else 
        {
            flag = true;
        }
        return flag;
    }

    /**
     * Method is used to select customer object based on customer ID
     * 
     * @return Customer object of Customer class
     */
    public Customer selectCustomer(int cust_id) 
    {
        while (true)
        {
            for (Customer customer : customerList) 
            {
                if (customer.getCustomerId() == cust_id) 
                {
                    return customer;
                }
            }
            return null;
        }
    }

    /** 
     * Method is used to select vehicle object based on vehicle registration number
     * 
     * @return Vehicle object of Vehicle class
     */
    public Vehicle selectVehicle(String VehicleRegNo) 
    {
        while (true) 
        {
            for (Vehicle vehicle : vehicleList) 
            {
                if (vehicle.getRegNumber().compareTo(VehicleRegNo) == 0) 
                {
                    return vehicle;
                }
            }
            return null;
        }
    }

    /**
     * Method is used find hired vehicle list for given customer.
     * 
     * @param customer_id id of customer
     * @return ArrayList<Reservation> hired vehicle list for perticular customer.
     */
    public ArrayList<Reservation> findHiredListForCustomer(int customer_id) 
    {
        ArrayList<Reservation> hireListForCustomer = new ArrayList<Reservation>();
        for (Reservation reservation : reservationList) 
        {
            if (reservation.getCustomer().getCustomerId() == customer_id) 
            {
                hireListForCustomer.add(reservation);
            }
        }
        return hireListForCustomer;
    }

    /**
     * Method is used find hired vehicle list for given vehicle.
     * 
     * @param regNo registration number of vehicle
     * @return ArrayList<Reservation> hired vehicle list for perticular vehicle.
     */
    public ArrayList<Reservation> findHiredListForVehicle(String regNo)
    {
        ArrayList<Reservation> hiredVehicleList = new ArrayList<Reservation>();
        reservationList.sort(Reservation.dateComparator);
        for (Reservation reservation : reservationList) 
        {
            if ((reservation.getVehicle().getRegNumber()).equals(regNo)) 
            {
                hiredVehicleList.add(reservation);
            }
        }
        return hiredVehicleList;
    }

    /**
     * Method is used to write reservation details in text file.
     * 
     * @throws IOException
     */
    public void writeReservationList(String filename) throws IOException 
    {
        ArrayList<String> data = new ArrayList<String>();
        // int i = 0;
        for (Reservation reservation : reservationList) 
        {
            if (reservation.getReturnDateTime() == null) 
            {
                data.add(reservation.getCustomer().getCustomerId() + ","
                    + reservation.getVehicle().getRegNumber() + ","
                    + reservation.getReservDateTime() + ","
                    + reservation.getReservationPeriod() + ","
                    + reservation.getPickupLocation() + ","
                    + reservation.getPickupDateTime() + ",No");
            } 
            else 
            {
                data.add(reservation.getCustomer().getCustomerId() + ","
                    + reservation.getVehicle().getRegNumber() + ","
                    + reservation.getReservDateTime() + ","
                    + reservation.getReservationPeriod() + ","
                    + reservation.getPickupLocation() + ","
                    + reservation.getPickupDateTime() + ","
                    + reservation.getReturnDateTime());
            }
        }
        FileUtility.writeToFile(filename, data);
    }

    /*
     * Method is used to print company name.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() 
    {
        return companyName;
    }
}
