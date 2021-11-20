import java.awt.Color; 
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
 * This class is used to create GUI
 * 
 * @author Fenil
 * @version 24 May 2021
 *
 */
public class GUI 
{

    private Company company;
    private Customer customer;
    private Vehicle vehicle;
    private int flag;

    /**
     * Constructor of GUI class
     */
    public GUI(Company pCompany)
    {
        this.company = pCompany;
        JFrame frame = new JFrame("Vehicle Hiring System");
		homePage(frame);
        loadVehicles(frame);
        loadCustomers(frame);
        loadReservations(frame);
    }

    /**
     * Method is used to create UI with its functionality
     * 
     * @param frame JFrame
     */
    public void homePage(JFrame frame) 
    {

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);

        JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

        JMenuItem save = new JMenuItem("Save");
		JMenuItem exit = new JMenuItem("Exit");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));

        fileMenu.add(save);
        fileMenu.add(exit);

        frame.setJMenuBar(menuBar);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(0.015);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);

        JMenuItem menu1;
        JMenuItem menu2;
        JMenuItem menu3;
        JMenuItem menu4;
        JMenuItem menu5;
        JMenuItem menu6;
        JMenuItem menu7;

        JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
        leftPanel.setSize(800, 200);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setLayout(new GridLayout(7, 1, 0, 10));
		
        menu1 = new JMenuItem("Available vehicles");
        menu1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,ActionEvent.ALT_MASK));
		menu1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
        menu2 = new JMenuItem("Reserve a vehicle");
        menu2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,ActionEvent.ALT_MASK));
        menu2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        menu3 = new JMenuItem("Show Hires for a customer");
        menu3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,ActionEvent.ALT_MASK));
        menu3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        menu4 = new JMenuItem("Show Hires for a vehicle");
        menu4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,ActionEvent.ALT_MASK));
        menu4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        menu5 = new JMenuItem("Show Not returned vehicles");
        menu5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,ActionEvent.ALT_MASK));
        menu5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menu5.setToolTipText("It will display only that vehicles which are not returned on time");
		
        menu6 = new JMenuItem("Return vehicle");
        menu6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6,ActionEvent.ALT_MASK));
        menu6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        menu7 = new JMenuItem("Exit");
        menu7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7,ActionEvent.ALT_MASK));
        menu7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menu7.setToolTipText("To save data into file with close the window");

        menuPanel.add(menu1);
        menuPanel.add(menu2);
        menuPanel.add(menu3);
        menuPanel.add(menu4);
        menuPanel.add(menu5);
        menuPanel.add(menu6);
        menuPanel.add(menu7);

        leftPanel.add(menuPanel);
        splitPane.setLeftComponent(leftPanel);

        JPanel rightPanel = new JPanel();
        splitPane.setRightComponent(rightPanel);

        frame.getContentPane().add(splitPane);

        menu1.addActionListener(new ActionListener() 
            {
				@Override
                public void actionPerformed(ActionEvent e) 
                {
                    showAvailableVehicles(splitPane);
                }
            });
        menu2.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    reserveVehicle(splitPane, rightPanel);
                }
            });
        menu3.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    showHireListForCustomer(splitPane, rightPanel);
                }
            });
        menu4.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    showHireListForVehicle(splitPane, rightPanel);
                }
            });
        menu5.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    showNotReturnedVehicle(splitPane, rightPanel);
                }
            });
        menu6.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    returnVehicle(splitPane, rightPanel);
                }
            });
        menu7.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    flag = 2;
                    saveDetails(frame, rightPanel, flag);
                }
            });

        save.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    flag = 1;
                    saveDetails(frame, rightPanel, flag);
                }
            });

        exit.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    flag = 2;
                    saveDetails(frame, rightPanel, flag);

                }
            });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.getContentPane().setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        frame.pack();
		frame.setVisible(true);
	}

    /**
     * Method is used to display available vehicles
     * 
     * @param splitPane split pane to insert components
     */
    public void showAvailableVehicles(JSplitPane splitPane)
    {
        JPanel panelForOption1 = new JPanel();
        panelForOption1.setLayout(new GridLayout(1, 1, 50, 2));

        JScrollPane scrollForTable = new JScrollPane();
		JScrollPane scrollForPanel = new JScrollPane(panelForOption1);

        Object[][] vehicleDetails = {};
        String[] columnHeader = { "Vehicle Type", "Registration No","Model No", "Year of Registration", "Fuel Type","Load Capacity / Engine Capacity" };

        DefaultTableModel tableModelForVehicleDetails;
		
        tableModelForVehicleDetails = new DefaultTableModel(vehicleDetails,columnHeader);
        ArrayList<Vehicle> availableVehicles = company.showAvailableVehicles();

        for (Vehicle vehicle : availableVehicles) 
        {
            ArrayList<String> details = vehicle.details();

            tableModelForVehicleDetails.addRow(new Object[] { details.get(0),details.get(1), details.get(2), details.get(3),details.get(4), details.get(5) });
        }
        JTable table = new JTable(tableModelForVehicleDetails);
		scrollForTable.getViewport().add(table);
		
        panelForOption1.setBorder(new TitledBorder("Available Vehicle List"));
        panelForOption1.add(scrollForTable);

        splitPane.setRightComponent(scrollForPanel);
    }

    /**
     * Method is used to reserve vehicles functionalities with UI
     * 
     * @param splitPane split pane to insert components
     * @param rightPanel  JPanel
     */
    public void reserveVehicle(JSplitPane splitPane, JPanel rightPanel) 
    {

        JPanel panelForOption2 = new JPanel();

        JPanel borderPanel = new JPanel();
        borderPanel.setPreferredSize(new Dimension(500, 300));
        borderPanel.setBorder(new TitledBorder("Reserve Vehicle"));

        JPanel panelOfFields = new JPanel();

        panelOfFields.setLayout(new GridLayout(6, 2, 20, 20));

        JLabel customerIdLabel = new JLabel("Customer ID");
        JTextField customerIdField = new JTextField();
        customerIdField.setToolTipText("enter only numeric value.");

        JLabel vehicleNoLabel = new JLabel("Vehicle No");
        JTextField vehicleNoField = new JTextField();

        JLabel pickupDateTimeLabel = new JLabel("Pick up Date and Time");
        JTextField pickupDateTimeField = new JTextField();
        pickupDateTimeField.setToolTipText("It allows only \"dd-mm-yyyy hh:mm\" format.");

        JLabel reservationPeriodLabel = new JLabel("Reservation Period(hours)");
        JTextField reservationPeriodField = new JTextField();

        JLabel pickupLocationLabel = new JLabel("Pickup Location");
        JTextField pickupLocationField = new JTextField();

        JButton submit = new JButton("Reserve");
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JButton clear = new JButton("Clear");
        clear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelOfFields.add(customerIdLabel);
        panelOfFields.add(customerIdField);
        panelOfFields.add(vehicleNoLabel);
        panelOfFields.add(vehicleNoField);
        panelOfFields.add(pickupDateTimeLabel);
        panelOfFields.add(pickupDateTimeField);
        panelOfFields.add(reservationPeriodLabel);
        panelOfFields.add(reservationPeriodField);
        panelOfFields.add(pickupLocationLabel);
        panelOfFields.add(pickupLocationField);
        panelOfFields.add(submit);
        panelOfFields.add(clear);

        borderPanel.add(panelOfFields);
        panelForOption2.add(borderPanel);

        splitPane.setRightComponent(panelForOption2);

        submit.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    String customerId = customerIdField.getText();
                    String vehicleNo = vehicleNoField.getText();
                    String pickupDateTimeString = pickupDateTimeField.getText();
                    String reservationPeriod = reservationPeriodField.getText();
                    String pickupLocation = pickupLocationField.getText();
                    if (!(LocalDateTime.now().getHour() < 4 && (LocalDateTime.now().getHour()) >= 0)) {
                        if (customerId.trim().isEmpty()|| vehicleNo.trim().isEmpty() || pickupDateTimeString.trim().isEmpty()|| reservationPeriod.trim().isEmpty()
                        || pickupLocation.trim().isEmpty()) 
                        {
                            JOptionPane.showMessageDialog(rightPanel, "Please enter all Fields", "Empty Input",JOptionPane.ERROR_MESSAGE);
                        }
                        else 
                        {
                            try 
                            {
                                customer = company.selectCustomer(Integer.parseInt(customerId));
                            } 
                            catch (NumberFormatException e1) 
                            {
                                FileUtility.errorLogger(e.toString() + "(GUI)");
                            }

                            if (customer == null) 
                            {
                                messageDialogue( rightPanel,"You entered wrong Customer ID(only numeric value allowed)","Wrong Input", 1);
                                customerIdField.requestFocus();
                            } 
                            else
                            {
                                String vehicleRegNo = vehicleNoField.getText();
                                vehicle = company.selectVehicle(vehicleRegNo);
                                if (vehicle == null) 
                                {
                                    messageDialogue(rightPanel, "You entered wrong Vehicle Registration Number","Wrong Input", 1);
                                    vehicleNoField.requestFocus();
                                } 
                                else
                                {
                                    int allowedHoursForReservation = 0;
                                    allowedHoursForReservation = company.findAllowedHours(customer);
                                    LocalDateTime reserDateTime = LocalDateTime.now();
                                    boolean flagForPickUpDateTime = false;
                                    LocalDateTime pickupDateTime = null;

                                    try{pickupDateTime = IOUtility.getLocalDateTimeForUI(pickupDateTimeString);}
                                    catch(Exception ex){

                                        messageDialogue(rightPanel,
                                            "You entered date in wrong format. Please enter date in \"dd-mm-yyyy hh:mm\" format","Wrong Format", 1);

                                        pickupDateTimeField.requestFocus();}

                                    if (pickupDateTime != null) 
                                    {
                                        flagForPickUpDateTime = company.checkForPickUpDateTime(pickupDateTime,allowedHoursForReservation,reserDateTime);

                                        if (!flagForPickUpDateTime) 
                                        {
                                            messageDialogue(rightPanel,
                                                "Reservation can be made up to 30 days in advance and reservation must be for future dates.","Wrong Input", 1);

                                            pickupDateTimeField.requestFocus();
                                        } 
                                        else 
                                        {
                                            boolean flagForReservationPeriod = false;
                                            int period = 0;
                                            try 
                                            {
                                                period = Integer.parseInt(reservationPeriod);
                                            }
                                            catch (Exception e5) 
                                            {
                                                FileUtility.errorLogger(e.toString() + "(GUI)");
                                                messageDialogue(rightPanel,"Please enter valid reservation period.","Wrong Input", 1);
                                                reservationPeriodField.requestFocus();
                                            }

                                            if (period != 0) 
                                            {
                                                flagForReservationPeriod = company.checkForReservationPeriod(customer, period);

                                                if (!flagForReservationPeriod) 
                                                {
                                                    messageDialogue(rightPanel,
                                                        "You can enter reservation period upto 15 hours. If you are Premium and Standard customer than it is for 20 hours.",
                                                        "Wrong Input", 2);
                                                } 
                                                else 
                                                {
                                                    boolean flagForVehicleAvailability = company.checkForVehicleAvailability(vehicle,pickupDateTime,period);

                                                    if (flagForVehicleAvailability) 
                                                    {
                                                        try 
                                                        {
                                                            company.reserveVehicle(customer,vehicle,pickupDateTime,period, pickupLocation,reserDateTime);
                                                            messageDialogue(rightPanel,"Reservation has been successfull.","Success", 2);
                                                            customerIdField.setText("");
                                                            vehicleNoField.setText("");
                                                            pickupDateTimeField.setText("");
                                                            reservationPeriodField.setText("");
                                                            pickupLocationField.setText("");
                                                        } 
                                                        catch (CustomException e1) 
                                                        {
                                                            messageDialogue(rightPanel,e1.toString(), "Error", 1);
                                                            FileUtility.errorLogger(e.toString()+ "(GUI)");
                                                        }
                                                    } else 
                                                    {
                                                        messageDialogue(rightPanel,
                                                            "Reservation failed. Entered vehicle is not available on this date and time.","Error", 1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } 
                    else 
                    {
                        messageDialogue(rightPanel,"Reservations can not be made between 4.00 am to 12 midnight","Error", 1);
                    }

                }
            });

        clear.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {

                    customerIdField.setText("");
                    vehicleNoField.setText("");
                    pickupDateTimeField.setText("");
                    reservationPeriodField.setText("");
                    pickupLocationField.setText("");
                }
            });
    }

    /**
     * Method is used to show hire list of customer
     * 
     * @param splitPane split pane to insert components
     * @param rightPanel JPanel
     */
    public void showHireListForCustomer(JSplitPane splitPane, JPanel rightPanel) 
    {

        JPanel panelForOption3 = new JPanel();

        panelForOption3.setLayout(new GridLayout(3, 1, 10, 30));

        JPanel borderPanel = new JPanel();
        borderPanel.setPreferredSize(new Dimension(400, 100));
        borderPanel.setBorder(new TitledBorder("Enter Required Details to Search"));

        JPanel panelForFields = new JPanel();

        panelForFields.setLayout(new GridLayout(1, 2, 50, 50));

        JLabel customerIdLabel = new JLabel("Customer ID");
        JTextField customerIdField = new JTextField();
        customerIdField.setToolTipText("It allows only numeric value.");

        JButton submit = new JButton("Search");
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelForFields.add(customerIdLabel);
        panelForFields.add(customerIdField);
        panelForFields.add(submit);

        borderPanel.add(panelForFields);
        panelForOption3.add(borderPanel);

        splitPane.setRightComponent(panelForOption3);

        JPanel panelForTable = new JPanel();
        JScrollPane scrollForTable = new JScrollPane();
        JScrollPane scrollForPanel = new JScrollPane(panelForTable);

        submit.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {

                    if (!customerIdField.getText().trim().isEmpty()) 
                    {

                        ArrayList<Reservation> hiredVehicleListForCustomer = company.findHiredListForCustomer(Integer.parseInt(customerIdField.getText()));

                        if (hiredVehicleListForCustomer.size() != 0) 
                        {
                            scrollForTable.setPreferredSize(new Dimension(1500, 100));

                            Object[][] rowData = {};
                            String[] columnNames = { "Customer ID","Customer Name", "Customer Category","Registration No", "Model No",
                                    "Reservation Date", "Pick Up Date","Pick Up Location","Reservation Period(hours)", "Return Status" };

                            DefaultTableModel tableModelForVehicleDetails;

                            tableModelForVehicleDetails = new DefaultTableModel(rowData, columnNames);
                            hiredVehicleListForCustomer.sort(Reservation.dateComparator);

                            for (Reservation reservation : hiredVehicleListForCustomer) 
                            {
                                if (reservation.getReturnDateTime() == null) 
                                {
                                    tableModelForVehicleDetails.addRow(new Object[] {
                                            reservation.getCustomer().getCustomerId(),
                                            reservation.getCustomer().getCustomerName(),
                                            reservation.getCustomer().getCustomerType(),
                                            reservation.getVehicle().getRegNumber(),
                                            reservation.getVehicle().getModelName(),
                                            reservation.getReservDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                            reservation.getPickupDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                            reservation.getPickupLocation(),
                                            reservation.getReservationPeriod(),"Not Returned" });
                                } 
                                else 
                                {
                                    tableModelForVehicleDetails.addRow(new Object[] {
                                            reservation.getCustomer().getCustomerId(),
                                            reservation.getCustomer().getCustomerName(),
                                            reservation.getCustomer().getCustomerType(),
                                            reservation.getVehicle().getRegNumber(),
                                            reservation.getVehicle().getModelName(),
                                            reservation.getReservDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                            reservation.getPickupDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                            reservation.getPickupLocation(),
                                            reservation.getReservationPeriod(),
                                            reservation.getReturnDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) });
                                }
                            }
                            JTable table = new JTable(tableModelForVehicleDetails);

                            scrollForTable.getViewport().add(table);

                            panelForTable.setBorder(new TitledBorder("Hire List for Customer"));
                            panelForTable.add(scrollForTable);

                            panelForOption3.add(scrollForPanel);

                            splitPane.setRightComponent(panelForOption3);

                        }
                        else 
                        {
                            messageDialogue(rightPanel,"This Customer ID has not any reserved vehicle.", "Wrong Input", 1);
                        }
                    } 
                    else 
                    {
                        messageDialogue(rightPanel, "Blanks field not allowed.", "Empty Input", 1);
                    }

                }
            });
    }

    /**
     * Method is used to show hire list of particular vehicle
     * 
     * @param splitPane split pane to insert components
     * @param rightPanel JPanel
     */
    public void showHireListForVehicle(JSplitPane splitPane, JPanel rightPanel) {

        JPanel panelForOption4 = new JPanel();
        panelForOption4.setLayout(new GridLayout(3, 1, 10, 30));

        JPanel borderPanel = new JPanel();
        borderPanel.setPreferredSize(new Dimension(400, 100));
        borderPanel.setBorder(new TitledBorder("Enter Required Details to Search"));

        JPanel panelForFields = new JPanel();

        panelForFields.setLayout(new GridLayout(1, 2, 50, 50));

        JLabel vehicleNoLabel = new JLabel("Vehicle No");
        JTextField vehicleNoField = new JTextField();
        JButton submit = new JButton("Search");
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelForFields.add(vehicleNoLabel);
        panelForFields.add(vehicleNoField);
        panelForFields.add(submit);

        borderPanel.add(panelForFields);
        panelForOption4.add(borderPanel);

        splitPane.setRightComponent(panelForOption4);
        JPanel panelForTable = new JPanel();
        JScrollPane scrollForTable = new JScrollPane();
        JScrollPane scrollForPanel = new JScrollPane(panelForTable);

        submit.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {

                    if (!vehicleNoField.getText().trim().isEmpty()) 
                    {

                        ArrayList<Reservation> HiredListForVehicle = company.findHiredListForVehicle(vehicleNoField.getText());

                        System.out.println(HiredListForVehicle.size());
                        if (HiredListForVehicle.size() != 0) 
                        {

                            scrollForTable.setPreferredSize(new Dimension(1500, 100));

                            Object[][] rowData = {};
                            String[] columnNames = { "Customer ID","Customer Name", "Customer Category","Registration No", "Model No",
                                    "Reservation Date", "Pick Up Date","Pick Up Location","Reservation Period(hours)", "Return Status" };

                            DefaultTableModel tableModelForVehicleDetails;

                            tableModelForVehicleDetails = new DefaultTableModel(rowData, columnNames);
                            HiredListForVehicle.sort(Reservation.dateComparator);

                            for (Reservation reservation : HiredListForVehicle) 
                            {
                                if (reservation.getReturnDateTime() == null) 
                                {
                                    tableModelForVehicleDetails.addRow(new Object[] {
                                            reservation.getCustomer().getCustomerId(),
                                            reservation.getCustomer().getCustomerName(),
                                            reservation.getCustomer().getCustomerType(),
                                            reservation.getVehicle().getRegNumber(),
                                            reservation.getVehicle().getModelName(),
                                            reservation.getReservDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                            reservation.getPickupDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                            reservation.getPickupLocation(),
                                            reservation.getReservationPeriod(),"Not Returned" });
                                } else {
                                    tableModelForVehicleDetails.addRow(new Object[] { reservation.getCustomer().getCustomerId(),
                                            reservation.getCustomer().getCustomerName(),
                                            reservation.getCustomer().getCustomerType(),
                                            reservation.getVehicle().getRegNumber(),
                                            reservation.getVehicle().getModelName(),
                                            reservation.getReservDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                            reservation.getPickupDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                            reservation.getPickupLocation(),
                                            reservation.getReservationPeriod(),
                                            reservation.getReturnDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) });
                                }
                            }
                            JTable table = new JTable(tableModelForVehicleDetails);

                            scrollForTable.getViewport().add(table);

                            panelForTable.setBorder(new TitledBorder("Hire List for Vehicle"));
                            panelForTable.add(scrollForTable);
                            panelForOption4.add(scrollForPanel);

                            splitPane.setRightComponent(panelForOption4);

                        } 
                        else
                        {
                            messageDialogue(rightPanel, "No Vehicle list to display","Alert", 2);
                        }
                    }
                    else 
                    {
                        messageDialogue(rightPanel, "Blank Field not allowed", "Empty Input", 1);
                    }
                }
            });
    }

    /**
     * Method is used to display not returned vehicle details
     * 
     * @param splitPane split pane to insert component
     * @param rightPanel JPanel
     */
    public void showNotReturnedVehicle(JSplitPane splitPane, JPanel rightPanel) 
    {
        JPanel panelForOption5 = new JPanel();
        panelForOption5.setLayout(new GridLayout(2, 1, 10, 30));

        splitPane.setRightComponent(panelForOption5);
        JPanel panelForTable = new JPanel();
        JScrollPane scrollForTable = new JScrollPane();
        JScrollPane scrollForPanel = new JScrollPane(panelForTable);

        ArrayList<Reservation> notReturnedVehicleList = company.findNotReturnVehicle();

        if (notReturnedVehicleList.size() != 0) 
        {

            scrollForTable.setPreferredSize(new Dimension(1500, 100));

            Object[][] rowData = {};
            String[] columnNames = { "Customer ID", "Customer Name",
                    "Customer Category", "Registration No", "Model No","Reservation Date", "Pick Up Date", "Pick Up Location",
                    "Reservation Period(hours)", "Return Status" };

            DefaultTableModel tableModelForVehicleDetails;

            tableModelForVehicleDetails = new DefaultTableModel(rowData,columnNames);
            notReturnedVehicleList.sort(Reservation.dateComparator);

            for (Reservation reservation : notReturnedVehicleList) 
            {
                if (reservation.getReturnDateTime() == null) 
                {
                    tableModelForVehicleDetails.addRow(new Object[] {
                            reservation.getCustomer().getCustomerId(),
                            reservation.getCustomer().getCustomerName(),
                            reservation.getCustomer().getCustomerType(),
                            reservation.getVehicle().getRegNumber(),
                            reservation.getVehicle().getModelName(),
                            reservation.getReservDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                            reservation.getPickupDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                            reservation.getPickupLocation(),
                            reservation.getReservationPeriod(),
                            "Not Returned" });
                } else {
                    tableModelForVehicleDetails.addRow(new Object[] {
                            reservation.getCustomer().getCustomerId(),
                            reservation.getCustomer().getCustomerName(),
                            reservation.getCustomer().getCustomerType(),
                            reservation.getVehicle().getRegNumber(),
                            reservation.getVehicle().getModelName(),
                            reservation.getReservDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                            reservation.getPickupDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                            reservation.getPickupLocation(),
                            reservation.getReservationPeriod(),
                            reservation.getReturnDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) });
                }
            }
            JTable table = new JTable(tableModelForVehicleDetails);

            scrollForTable.getViewport().add(table);

            panelForTable.setBorder(new TitledBorder("Not returned vehicle list"));
            panelForTable.add(scrollForTable);

            panelForOption5.add(scrollForPanel);

            splitPane.setRightComponent(panelForOption5);

        } else 
        {
            splitPane.setRightComponent(rightPanel);
            messageDialogue(rightPanel,"There is no vehicles which are not returned ontime.", "Alert", 2);
        }
    }

    /**
     * Method is used to return vehicle with proper UI
     * 
     * @param splitPane split pane to insert component
     * @param rightPanel JPanel
     */
    public void returnVehicle(JSplitPane splitPane, JPanel rightPanel) 
    {

        JPanel panelForOption6 = new JPanel();

        JPanel borderPanel = new JPanel();
        borderPanel.setPreferredSize(new Dimension(500, 300));
        borderPanel.setBorder(new TitledBorder("Return Vehicle"));

        JPanel panelForFields = new JPanel();

        panelForFields.setLayout(new GridLayout(4, 2, 20, 20));

        JLabel customerIdLabel = new JLabel("Customer ID");
        JTextField customerIdField = new JTextField();
        customerIdField.setToolTipText("It allows only numeric value.");

        JLabel vehicleNoLabel = new JLabel("Vehicle No");
        JTextField vehicleNoField = new JTextField();

        JLabel pickupDateTimeLabel = new JLabel("Pick up Date and Time");
        JTextField pickupDateTimeField = new JTextField();
        pickupDateTimeField.setToolTipText("It allows only \"dd-mm-yyyy hh:mm\" format.");

        JButton submit = new JButton("Return");
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton clear = new JButton("Clear");
        clear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelForFields.add(customerIdLabel);
        panelForFields.add(customerIdField);
        panelForFields.add(vehicleNoLabel);
        panelForFields.add(vehicleNoField);
        panelForFields.add(pickupDateTimeLabel);
        panelForFields.add(pickupDateTimeField);
        panelForFields.add(submit);
        panelForFields.add(clear);

        borderPanel.add(panelForFields);
        panelForOption6.add(borderPanel);

        splitPane.setRightComponent(panelForOption6);

        submit.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    String customerId = customerIdField.getText();
                    String vehicleNo = vehicleNoField.getText();
                    String pickupDateTimeString = pickupDateTimeField.getText();

                    if (customerId.trim().isEmpty() || vehicleNo.trim().isEmpty()|| pickupDateTimeString.trim().isEmpty()) 
                    {
                        messageDialogue(rightPanel, "Please fill all Fields","Empty Input", 1);
                    } 
                    else 
                    {
                        try 
                        {
                            customer = company.selectCustomer(Integer.parseInt(customerId));
                        } 
                        catch (NumberFormatException e1) 
                        {
                            FileUtility.errorLogger(e1.toString() + "(GUI)");
                        }

                        if (customer == null) 
                        {
                            messageDialogue(rightPanel,"You entered wrong Customer ID(only numeric value allowed)","Wrong Input", 1);
                            customerIdField.requestFocus();
                        } 
                        else
                        {
                            String vehicleRegNo = vehicleNoField.getText();
                            Vehicle vehicle = company.selectVehicle(vehicleRegNo);
                            if (vehicle == null) 
                            {
                                messageDialogue(rightPanel, "You entered wrong Vehicle Registration Number","Wrong Input", 1);
                                vehicleNoField.requestFocus();
                            }
                            else 
                            {
                                LocalDateTime pickupDateTime = null;
                                try 
                                {
                                    pickupDateTime = IOUtility.getLocalDateTimeForUI(pickupDateTimeString);
                                }
                                catch (Exception e5) 
                                {
                                    messageDialogue(rightPanel,"You entered date in wrong format. Please enter date in \"dd-mm-yyyy hh:mm\" format","Wrong Format", 1);

                                    pickupDateTimeField.requestFocus();
                                }
                                if (pickupDateTime != null) 
                                {
                                    int totalCharge = company.returnVehicle(Integer.parseInt(customerId),vehicleRegNo, pickupDateTime);
                                    if (totalCharge == 0) 
                                    {
                                        messageDialogue(rightPanel,"No vehicle registered for this details.","Alert", 2);
                                    } 
                                    else 
                                    {
                                        JOptionPane.showMessageDialog(rightPanel,
                                            "You have successfully returned this vehicle. "+ "Your total charge for reservation is : " + totalCharge, "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                                        customerIdField.setText("");
                                        vehicleNoField.setText("");
                                        pickupDateTimeField.setText("");
                                    }
                                }
                            }
                        }
                    }
                }
            });

        clear.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    customerIdField.setText("");
                    vehicleNoField.setText("");
                    pickupDateTimeField.setText("");
                }
            });
    }

    /**
     * Method is used to save data into file
     * 
     * @param rightPanel JPanel
     */
    public void saveDetails(JFrame frame, JPanel rightPanel, int flag) 
    {
        int result;
        String filename = "Reservation.txt";
        if (flag == 1) 
        {
            result = JOptionPane.showConfirmDialog(rightPanel,"Do you want to save data in file?", "alert",JOptionPane.OK_CANCEL_OPTION);
        } 
        else 
        {
            result = JOptionPane.showConfirmDialog(rightPanel,"Do you want to exit window with save data in file?","alert", JOptionPane.OK_CANCEL_OPTION);
        }

        if (result == 0) 
        {
            try 
            {
                company.writeReservationList(filename);
            } 
            catch (IOException e) 
            {
                FileUtility.errorLogger(e.toString() + "(GUI)");
                int a = JOptionPane.showConfirmDialog(frame, "File for save data is not found. Do you want to save in another file?","alert", JOptionPane.OK_CANCEL_OPTION);

                if (a == 0) 
                {
                    filename = openFile();
                    try 
                    {
                        company.writeReservationList(filename);
                    } 
                    catch (IOException e1) 
                    {
                        JOptionPane.showMessageDialog(frame,"File not found to save.", "alert",JOptionPane.ERROR_MESSAGE);

                        if (flag == 2) 
                        {
                            frame.dispose();
                        }
                    }
                }
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(frame, "File not found to save.","alert", JOptionPane.ERROR_MESSAGE);
                frame.dispose();

            }

            if (flag == 2) 
            {
                frame.dispose();
            }
        }
    }

    /**
     * Method is used to display dialogue box
     * 
     * @param rightPanelJPanel
     * @param message message to display
     * @param header  message heading
     * @param a integer flag
     */
    public void messageDialogue(JPanel rightPanel, String message,String header, int a) {

        if (a == 1) 
        {
            JOptionPane.showMessageDialog(rightPanel, message, header,JOptionPane.ERROR_MESSAGE);
        } 
        else 
        {
            JOptionPane.showMessageDialog(rightPanel, message, header, JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Method is used to open the file from computer
     * 
     * @return String filepath of selected file
     */
    public String openFile() 
    {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int value = jFileChooser.showOpenDialog(null);
        String filePath;

        File selectedFile = null;
        if (value == JFileChooser.APPROVE_OPTION) 
        {
            selectedFile = jFileChooser.getSelectedFile();
        }
        filePath = selectedFile.getAbsolutePath();
        return filePath;

    }

    /**
     * Method is used to load Customer detail from text file, if file is not available than user can select file from computer. 
     * @param frame JFrame
     */
    public void loadCustomers(JFrame frame) 
    {
        String customerDetails = "Customers.txt";
        try 
        {
            this.company.loadCustomer(customerDetails);
        } 
        catch (IOException | CustomException e) 
        {
            FileUtility.errorLogger(e.toString() + "(GUI)");
            int result = JOptionPane.showConfirmDialog(frame,
                    "File which contains Customer detail is not found. Do you want to open another file?", "alert", JOptionPane.OK_CANCEL_OPTION);

            if (result == 0) 
            {
                customerDetails = openFile();
                try 
                {
                    this.company.loadCustomer(customerDetails);
                } 
                catch (IOException | CustomException e1) 
                {
                    FileUtility.errorLogger(e.toString() + "(GUI)");
                    JOptionPane.showMessageDialog(frame, "Customer detais not found.", "Alert",JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) 
        {
            FileUtility.errorLogger(e.toString() + "(GUI)");

            JOptionPane.showMessageDialog(frame, "Customer detais not found.","Alert", JOptionPane.ERROR_MESSAGE);

        }

    }

    /**
     * Method is used to load Vehicles detail from text file, if file is not available than user can select file from computer. 
     * @param frame JFrame
     */
    public void loadVehicles(JFrame frame) 
    {
        String vehicleDetails = "Vehicles.txt";
        try 
        {
            this.company.loadVehicle(vehicleDetails);

        } 
        catch (IOException | CustomException e) 
        {
            FileUtility.errorLogger(e.toString() + "(GUI)");
            int result = JOptionPane.showConfirmDialog(frame,
                    "File which contains Vehicle detail is not found. Do you want to open another file?","Alert", JOptionPane.OK_CANCEL_OPTION);

            if (result == 0) 
            {
                vehicleDetails = openFile();
                try 
                {
                    this.company.loadVehicle(vehicleDetails);
                } 
                catch (Exception e1) 
                {
                    FileUtility.errorLogger(e.toString() + "(GUI)");
                    JOptionPane.showMessageDialog(frame,"vehicle detais not found.", "Alert",JOptionPane.ERROR_MESSAGE);
                }
            }
        } 
        catch (Exception e) 
        {
            FileUtility.errorLogger(e.toString() + "(GUI)");
            JOptionPane.showMessageDialog(frame, "vehicle detais not found.","Alert", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Method is used to load reservations detail from text file, if file is not available than user can select file from computer. 
     * @param frame JFrame
     */
    public void loadReservations(JFrame frame) 
    {
        String reservationDetails = "Reservation.txt";

        try 
        {
            this.company.loadReservation(reservationDetails);
        } 
        catch (IOException e) 
        {
            FileUtility.errorLogger(e.toString() + "(GUI)");
            int result = JOptionPane.showConfirmDialog(frame,
                    "File which contains Reservation detail is not found. Do you want to open another file?","Alert", JOptionPane.OK_CANCEL_OPTION);

            if (result == 0) 
            {
                reservationDetails = openFile();
                try 
                {
                    this.company.loadReservation(reservationDetails);

                } 
                catch (IOException e1) 
                {
                    JOptionPane.showMessageDialog(frame,"Reservation detais not found. File will create automatically when reservation will be done.",
                        "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(frame,"Reservation detais not found. File will create automatically when reservation will be done.",
                "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
}