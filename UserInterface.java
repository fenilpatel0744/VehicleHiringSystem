import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The UserInterface class is used to take user input to perform specific task and call methods of business logic based on user selection.
 * 
 * @author Fenil
 *
 */
public class UserInterface 
{
    private Company company;

    /**
     * Constructor for object of UserInterface class
     */
    public UserInterface(Company pCompany) 
    {
        this.company = pCompany;
        loadVehicles();
        loadCustomers();
        loadReservations();
    }

    /**
     * Method is used to call method based on user selection.
     * 
     */
    public void run() {
        while (true) {
            try {
                switch (menu()) {
                    case 1:
                    showAvailableVehicles();
                    break;
                    case 2:
                    reserveVehicle();
                    break;
                    case 3:
                    showCustomerReservations();
                    break;
                    case 4:
                    showVehicleReservations();
                    break;
                    case 5:
                    showNotReturnedVehicles();
                    break;
                    case 6:
                    returnVehicle();
                    break;
                    case 7:
                    writeReservationList();
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

    /**
     * Method is used to print user menu to user console.
     * 
     * @return int selected user choice
     */
    public int menu() {
        System.out.println("1. Show available vehicles");
        System.out.println("2. Reserve a vehicle for a customer");
        System.out.println("3. Show hires for a customer");
        System.out.println("4. Show hires for a vehicle");
        System.out.println("5. Show not returned (overdue) vehicles (display only that vehicles which are not returned ontime.)");
        System.out.println("6. Return vehicle from hire");
        System.out.println("7. Exit (Exit program with write data into text file)");
        IOUtility.newLine();
        return IOUtility.getInteger("Select option:");
    }

    /**
     * Method is used to fetch available vehicles and to display user console.
     */
    public void showAvailableVehicles() 
    {
        IOUtility.println("Available Vehicle Details:");
        IOUtility.newLine();

        ArrayList<Vehicle> availableVehicles = company.showAvailableVehicles();
        displayAvailableVehicles(availableVehicles);
        IOUtility.newLine();
    }

    /**
     * Method is used to display available vehicles to user console.
     * 
     * @param availableVehicles available vehicles for registration
     */
    public void displayAvailableVehicles(ArrayList<Vehicle> availableVehicles) 
    {
        System.out.printf("%-15s", "Vehicle Type");
        System.out.printf("%-17s", "Registration No");
        System.out.printf("%-25s", "Model No");
        System.out.printf("%-22s", "Year of Registration");
        System.out.printf("%-15s", "Fuel Type");
        System.out.printf("%-20s", "Load Capacity / Engine Capacity");

        IOUtility.newLine();
        IOUtility.println("********************************************************************************************************************************");

        for (Vehicle vehicle : availableVehicles) 
        {
            ArrayList<String> details = vehicle.details();

            System.out.printf("%-15s", details.get(0));
            System.out.printf("%-17s", details.get(1));
            System.out.printf("%-25s", details.get(2));
            System.out.printf("%-22s", details.get(3));
            System.out.printf("%-15s", details.get(4));
            System.out.printf("%-20s", details.get(5));
            System.out.println();
        }
    }

    /**
     * Method is used to reserve specific vehicle for customer
     */
    public void reserveVehicle() 
    {
        if (!(LocalDateTime.now().getHour() < 4 && (LocalDateTime.now().getHour()) >= 0)) 
        {
            showAvailableVehicles();
            int cust_id;
            Customer customer = null;
            try
            { 
                cust_id = IOUtility.getInteger("Enter Customer ID");
                customer = company.selectCustomer(cust_id);}
            catch(NumberFormatException e)
            {
                FileUtility.errorLogger(e.toString()+ "(Console)");
            }
            while (customer == null) 
            {
                try{
                    cust_id = IOUtility.getInteger("Enter Valid Customer ID Again:");
                    customer = company.selectCustomer(cust_id);
                }
                catch(NumberFormatException e)
                {
                    FileUtility.errorLogger(e.toString()+ "(Console)");
                }
            }

            IOUtility.newLine();

            String vehicleRegNo = IOUtility.getString("Enter Vehicle No");

            Vehicle vehicle = company.selectVehicle(vehicleRegNo);
            while (vehicle == null) 
            {
                vehicleRegNo = IOUtility.getString("Enter Valid Customer ID Again:");
                vehicle = company.selectVehicle(vehicleRegNo);
            }

            IOUtility.newLine();

            LocalDateTime pickupDateTime = null;
            int allowedHoursForReservation = company.findAllowedHours(customer);
            LocalDateTime reserDateTime = LocalDateTime.now();
            boolean flagForPickUpDateTime = false;

            while (!flagForPickUpDateTime) 
            {
                while (pickupDateTime == null || !flagForPickUpDateTime) 
                {
                    try 
                    {
                        pickupDateTime = IOUtility.getLocalDateTime("Enter Pick Up Date and Time:");
                    }
                    catch (Exception e)
                    {
                        System.out.println("Invalid date format");
                        pickupDateTime = null;
                        break;
                    }

                    flagForPickUpDateTime = company.checkForPickUpDateTime(pickupDateTime, allowedHoursForReservation,reserDateTime);
                    if (!flagForPickUpDateTime) 
                    {
                        IOUtility.println("Please Enter Valid Date and time...Reservation can be made up to 30 days in advance and reservation must be for future dates.");
                    }
                    IOUtility.newLine();
                }
            }

            boolean flagForReservationPeriod = false;
            int period = 0;

            while (!flagForReservationPeriod) 
            {
                while (period == 0 || !flagForReservationPeriod) 
                {
                    try {
                        period = IOUtility.getInteger("Enter Reservation Period (In hours)");
                    } catch (Exception e) {
                        System.out.println("Not a valid Integer");
                        period = 0;
                        break;
                    }
                    flagForReservationPeriod = company
                    .checkForReservationPeriod(customer, period);

                    if (!flagForReservationPeriod) {
                        IOUtility.println("Invalid Reservation Period...You can enter reservation period upto 15 hours, Premium and Standard customer can do that upto 20 hours.");
                    }
                    IOUtility.newLine();
                }
            }
            String pickupLocation = IOUtility.getString("Enter Pickup Location");
            IOUtility.newLine();

            boolean flagForVehicleAvailability = company.checkForVehicleAvailability(vehicle, pickupDateTime,period);

            if (flagForVehicleAvailability) 
            {
                try 
                {
                    company.reserveVehicle(customer, vehicle, pickupDateTime,period, pickupLocation, reserDateTime);
                    IOUtility.println("Reservation successful.");
                    IOUtility.newLine();
                } 
                catch (CustomException e)
                {
                    IOUtility.println(e.toString());
                    FileUtility.errorLogger(e.toString() + "(Console UI)");
                }
            } 
            else 
            {
                IOUtility.println("Vehicle is not available");
                IOUtility.newLine();
            }
        } 
        else 
        {
            IOUtility.println("Reservations can be made between 4.00 am to 12 midnight");
            IOUtility.newLine();
        }
    }

    /**
     * Method is used to find reservation list for perticular user.
     */
    public void showCustomerReservations() {

        int customer_id = IOUtility.getInteger("Enter Customer ID: ");
        ArrayList<Reservation> hiredVehicleList = company.findHiredListForCustomer(customer_id);
        if (hiredVehicleList.size() != 0) 
        {
            displayReservationDetails(hiredVehicleList);
            IOUtility.newLine();
        } else 
        {
            IOUtility.println("This Customer ID has not any reserved vehicle.");
        }
        IOUtility.newLine();
    }

    /**
     * Method is used to display reservation details to user console.
     * 
     * @param reservationList list of reservation details
     */
    public void displayReservationDetails(ArrayList<Reservation> reservationList) 
    {
        System.out.printf("%-15s", "Customer ID");
        System.out.printf("%-17s", "Customer Name");
        System.out.printf("%-22s", "Customer Category");
        System.out.printf("%-17s", "Registration No");
        System.out.printf("%-25s", "Model No");
        System.out.printf("%-25s", "Reservation Date");
        System.out.printf("%-25s", "Pick Up Date");
        System.out.printf("%-25s", "Pick Up Location");
        System.out.printf("%-25s", "Reservation Period(hours)");
        System.out.printf("%-25s", "Return Status");

        IOUtility.newLine();
        IOUtility.println("********************************************************************************************************************************************************************************************************************");

        reservationList.sort(Reservation.dateComparator);
        for (Reservation reservation : reservationList) 
        {
            if (reservation.getReturnDateTime() == null) 
            {

                System.out.printf("%-15s", reservation.getCustomer().getCustomerId());
                System.out.printf("%-17s", reservation.getCustomer().getCustomerName());
                System.out.printf("%-22s", reservation.getCustomer().getCustomerType());
                System.out.printf("%-17s", reservation.getVehicle().getRegNumber());
                System.out.printf("%-25s", reservation.getVehicle().getModelName());
                System.out.printf("%-25s",reservation.getReservDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                System.out.printf("%-25s", reservation.getPickupDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                System.out.printf("%-25s", reservation.getPickupLocation());
                System.out.printf("%-25s", reservation.getReservationPeriod());
                System.out.printf("%-25s", "Not Returned");

            } else {
                System.out.printf("%-15s", reservation.getCustomer().getCustomerId());
                System.out.printf("%-17s", reservation.getCustomer().getCustomerName());
                System.out.printf("%-22s", reservation.getCustomer().getCustomerType());
                System.out.printf("%-17s", reservation.getVehicle().getRegNumber());
                System.out.printf("%-25s", reservation.getVehicle().getModelName());
                System.out.printf("%-25s",reservation.getReservDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                System.out.printf("%-25s",reservation.getPickupDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                System.out.printf("%-25s", reservation.getPickupLocation());
                System.out.printf("%-25s", reservation.getReservationPeriod());
                System.out.printf("%-25s",reservation.getReturnDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }
            IOUtility.newLine();
        }
    }

    /**
     * Method is used to find all reservation details for particular vehicle.
     */
    public void showVehicleReservations() 
    {
        String regNo = IOUtility.getString("Enter Vehicle Registration Number");
        ArrayList<Reservation> hiredVehicleList = company.findHiredListForVehicle(regNo);

        if (hiredVehicleList.size() != 0) 
        {
            displayReservationDetails(hiredVehicleList);
        } else 
        {
            IOUtility.println("This Vehicle Registration Number has not any reservation.");
        }
        IOUtility.newLine();
    }

    /**
     * Method is used to find details of not returned vehicles on time.
     */
    public void showNotReturnedVehicles() 
    {
        ArrayList<Reservation> notReturnedVehicleList = company.findNotReturnVehicle();

        if (notReturnedVehicleList.size() != 0) {
            displayReservationDetails(notReturnedVehicleList);
        } else {
            IOUtility.println("There is no vehicles which are not returned ontime.");
        }
        IOUtility.newLine();
    }

    /**
     * Method is used to return vehicle based on user reservation details
     */
    public void returnVehicle() {

        int customer_id = IOUtility.getInteger("Enter Customer ID");
        String vehicle_id = IOUtility.getString("Enter Vehicle Registration Number");
        LocalDateTime reservationDateTime = IOUtility.getLocalDateTime("Enter Pickup Date and Time");
        int totalCharge = company.returnVehicle(customer_id, vehicle_id,reservationDateTime);

        if (totalCharge == 0) {
            IOUtility.println("No vehicle registered for this details.");
        } else {
            IOUtility.println("You have successfully returned this vehicle.");
            IOUtility.println("Your total charge for reservation is : "+ totalCharge);
        }
        IOUtility.newLine();
    }

    /**
     * Method is used to write reservation details in text file.
     */
    public void writeReservationList() 
    {

        final int MAX_ATTEMPTS = 3;
        boolean successful = false;
        int attempts = 0;
        String filename = "Reservation.txt";

        do {
            try 
            {
                company.writeReservationList(filename);
                successful = true;
            } catch (IOException e) {
                System.out.println("Unable to save data to " + filename);
                attempts++;
                if (attempts <= MAX_ATTEMPTS) 
                {
                    try {
                        switch (menuForFileHandeling()) {
                            case 1:
                            System.out.print("Please Enter another file name: ");
                            Scanner scanner = new Scanner(System.in);
                            filename = scanner.nextLine();
                            break;

                            case 2:
                            return;

                            default:
                            System.out.println("You have entered wrong input");
                            System.out.println();
                            break;
                        }
                    } catch (Exception exception) 
                    {
                        System.out.println("You have entered wrong input");
                    }
                }
            } 
            catch (NullPointerException e) 
            {
                break;
            }
        } while (!successful && attempts < MAX_ATTEMPTS);
        if (!successful) 
        {
            System.out.println("You can not write data into the text file.");
        }

    }

    /**
     * Method is used to show menu for file handling
     * 
     * @return int user choice
     */
    public int menuForFileHandeling() 
    {
        System.out.println("1. Use another File Name");
        System.out.println("2. Exit ");
        System.out.println();
        System.out.print("Please enter your choice : ");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        System.out.println();
        return choice;

    }

    /**
     * Method is used to load customers detail from text file, if file is not available than user can select another file. 
     * 
     */
    public void loadCustomers() 
    {
        final int MAX_ATTEMPTS = 1;
        boolean successful = false;
        int attempts = 0;
        String filename = "Customers.txt";
        do {
            try 
            {
                this.company.loadCustomer(filename);
                successful = true;
            } 
            catch (IOException | CustomException e) 
            {
                FileUtility.errorLogger(e.toString() + "(console UI)");
                System.out.println("Unable to read data from " + filename);
                attempts++;
                if (attempts <= MAX_ATTEMPTS) 
                {
                    try {
                        switch (menuForFileHandeling()) {
                            case 1:
                            System.out.print("Please Enter another file name: ");
                            Scanner scanner = new Scanner(System.in);
                            filename = scanner.nextLine();
                            break;

                            case 2:
                            return;

                            default:
                            System.out.println("Invalid option");
                            System.out.println();
                            break;
                        }
                    } catch (Exception exception) {
                        System.out.println("You have entered wrong input");
                    }
                }
            } catch (Exception e) {
                FileUtility.errorLogger(e.toString()+ "(Console UI)");
                break;
            }
        } while (!successful && attempts < MAX_ATTEMPTS);
        if (!successful) 
        {
            System.out.println("Sorry, data can not be read.");
        }
    }

    /**
     * Method is used to load vehicles detail from text file, if file is not available than user can select another file. 
     * 
     */
    public void loadVehicles() 
    {
        String filename = "Vehicles.txt";

        final int MAX_ATTEMPTS = 1;
        boolean successful = false;
        int attempts = 0;

        do {
            try 
            {
                this.company.loadVehicle(filename);
                successful = true;
            } 
            catch (IOException | CustomException e) 
            {
                FileUtility.errorLogger(e.toString() + "(console UI)");
                System.out.println("Unable to read data from " + filename);
                attempts++;
                if (attempts <=MAX_ATTEMPTS) {
                    try {
                        switch (menuForFileHandeling()) 
                        {
                            case 1:
                            System.out.print("Please Enter another file name: ");
                            Scanner scanner = new Scanner(System.in);
                            filename = scanner.nextLine();
                            break;

                            case 2:
                            return;

                            default:
                            System.out.println("Invalid option");
                            System.out.println();
                            break;
                        }
                    } 
                    catch (Exception exception) 
                    {
                        System.out.println("You have entered wrong input");
                    }
                }
            } catch (Exception e) {
                FileUtility.errorLogger(e.toString()+ "(Console UI)");
                break;
            }
        } while (!successful && attempts < MAX_ATTEMPTS);
        if (!successful) 
        {
            System.out.println("Sorry, data can not be read.");
        }

    }

    /**
     * Method is used to load reservation detail from text file, if file is not available than user can select another file. 
     * 
     */
    public void loadReservations() 
    {
        String filename = "Reservation.txt";

        final int MAX_ATTEMPTS = 1;
        boolean successful = false;
        int attempts = 0;

        do {
            try 
            {
                this.company.loadReservation(filename);
                successful = true;
            } 
            catch (IOException e) 
            {
                FileUtility.errorLogger(e.toString() + "(console UI)");
                System.out.println("Unable to read data from " + filename);
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    try {
                        switch (menuForFileHandeling()) {
                            case 1:
                            System.out
                            .print("Please Enter another file name: ");
                            Scanner scanner = new Scanner(System.in);
                            filename = scanner.nextLine();
                            break;

                            case 2:
                            return;

                            default:
                            System.out.println("Invalid option");
                            System.out.println();
                            break;
                        }
                    } 
                    catch (Exception exception) 
                    {
                        System.out.println("You have entered wrong input");
                    }
                }
            } catch (Exception e) {
                break;
            }
        } while (!successful && attempts < MAX_ATTEMPTS);
        if (!successful) {
            System.out.println("File data can not be loaded");
        }
    }
}
