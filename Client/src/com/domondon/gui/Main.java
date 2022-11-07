/*
    Document   : Main.java
    Package	   : Client.com.domondon.gui;
    Created on : July 25, 2022
    Authors    : 
    			Philip Domondon
    			Ron Relayo
    			Cloyd Secuya
    Description:
    	This class source file acts as a Main page where the UI.java derives its content from here.
    	Furthermore, this is where most of the event handling takes place.
*/

// PACKAGE SECTION
package com.domondon.gui;


// IMPORT SECTION
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

//Proxy.Driver.java
import com.db.driver.Driver;

import resource.Cart;
import resource.Product;


public class Main {

    private static final Border EmptyBorder = null;

    // Instantiating colors so that it will not get repetitive.
    Color sideBarColor = new Color(153, 125, 88, 255);
    Color appetizerColor = new Color(168, 113, 82, 255);
    Color mainDishColor = new Color(90, 67, 53, 255);
    Color sidesColor = new Color(72, 121, 55, 255);
    Color dessertsColor = new Color(158, 51, 33, 255);
    private JTable table;
    
    Driver driver = new Driver(); 
    Product[] inventory_ls; 
    Product item; 
    Product[] items_selected = new Product[] {new Product()}; 
    ArrayList<Product> items_ls_sel = new ArrayList<Product>(); 
    Cart cart;
    
    String[] name_item = new String[500];
	double[] price = new double[500];
    
    double total = 0;
    String new_total_lbl; 
    private static String baseURI = "http://localhost:8080/WebServices/rest/products";

    /**
     * @wbp.parser.entryPoint
     */
    void mainPage() {
        // Create and set up the window.
        JFrame frame = new JFrame("Food Ordering System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
         * Declaration of the panels that will be placed inside the card layout
         * and navigated through based on user interaction.
         */
        CardLayout cl = new CardLayout();

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(cl);

        // Panels to be navigated through
        // Uses GridLayout to make the menu easier to build
        JPanel appetizerPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        JPanel mainDishPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        JPanel sidesPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        JPanel dessertsPanel = new JPanel(new GridLayout(2, 3, 20, 20));

        // adding the panels to the card panel
        cardPanel.add(appetizerPanel, "Appetizers");
        cardPanel.add(mainDishPanel, "Main Dish");
        cardPanel.add(sidesPanel, "Sides");
        cardPanel.add(dessertsPanel, "Desserts");

        // Background color of all the panels in the cardpanel. These colors correspond
        // to each of their respective buttons
        appetizerPanel.setBackground(appetizerColor);
        mainDishPanel.setBackground(mainDishColor);
        sidesPanel.setBackground(sidesColor);
        dessertsPanel.setBackground(dessertsColor);

        // Create a panel to put inside the frame
        JPanel p = new JPanel();
        p.setOpaque(true);
        p.setLayout(new BorderLayout());
        p.setBorder(EmptyBorder);
        
        
        // Checkout Panel
        JPanel checkOut = new JPanel();
        checkOut.setBackground(Color.WHITE);
        checkOut.setPreferredSize(new Dimension(325, 400));
        checkOut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        p.add(checkOut, BorderLayout.LINE_END);
        checkOut.setLayout(null);
        
        //CHECKOUT TABLE CREATION
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 78, 305, 582);
        checkOut.add(scrollPane);
        
        DefaultTableModel checkoutTable;
        table = new JTable();
        scrollPane.setViewportView(table);
        table.setBackground(Color.WHITE);							//setting table bg color
        table.setGridColor(Color.WHITE);							//setting table grid color
        checkoutTable = new DefaultTableModel();
        // Index 0, 1
        // 0 = Item
        // 1 = Prices
        Object[] columns = {"Item", "Price"};						//column creation for table
        Object[] rows = new Object[0];								//initial row creation
        checkoutTable.setColumnIdentifiers(columns);				//assigning column values to the checkout table
        table.setModel(checkoutTable);								//assigning checkout table to the table displayed in the UI
        
        
        //FROM HERE ONWARDS CHECKOUT FORMATTING
        JLabel lblNewLabel = new JLabel("===================");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        lblNewLabel.setBounds(10, 11, 305, 31);
        checkOut.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("CHECKOUT FORM\r\n");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Monospaced", Font.BOLD, 22));
        lblNewLabel_1.setBounds(20, 23, 295, 44);
        checkOut.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("___________________");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("Monospaced", Font.PLAIN, 25));
        lblNewLabel_2.setBounds(10, 33, 305, 31);
        checkOut.add(lblNewLabel_2);
        
        JLabel lblNewLabel_2_1 = new JLabel("___________________");
        lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2_1.setFont(new Font("Monospaced", Font.PLAIN, 25));
        lblNewLabel_2_1.setBounds(10, 645, 305, 31);
        checkOut.add(lblNewLabel_2_1);
        
        JLabel lblNewLabel_3 = new JLabel("===================");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setFont(new Font("Monospaced", Font.PLAIN, 25));
        lblNewLabel_3.setBounds(10, 713, 305, 31);
        checkOut.add(lblNewLabel_3);
        
        JButton checkOutConfirm = new JButton("CONFIRM");
        checkOutConfirm.setForeground(Color.WHITE);
        checkOutConfirm.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 18));
        checkOutConfirm.setFocusPainted(false);
        checkOutConfirm.setBorder(EmptyBorder);
        checkOutConfirm.setBackground(SystemColor.controlDkShadow);
        checkOutConfirm.setBounds(100, 746, 120, 31);				// Confirm button positioning
        checkOut.add(checkOutConfirm);
        
        JLabel lblNewLabel_1_1 = new JLabel("TOTAL: ");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_1.setFont(new Font("Monospaced", Font.BOLD, 22));
        lblNewLabel_1_1.setBounds(20, 681, 295, 44);
        checkOut.add(lblNewLabel_1_1);
        
        checkOutConfirm.addActionListener(new ActionListener() {
        	/**
        	 * @TODO Confirm checkout
        	 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// Pop-up confirmation dialog
				JOptionPane.showMessageDialog(frame, "Your order has been confirmed", "Order Confirmed",JOptionPane.INFORMATION_MESSAGE);
				
				/**
				 * @note wrapping the items_selected as an object of Product[] with 
				 * the help of an ArrayList<Product>
				 * 
				 * (Product []) product_ls.toArray(new Product[product_ls.size()]);  
				 * // Reference from my Proxy.Driver on how I wrap ArrayList to a Product Object
				 */
				items_selected = (Product []) items_ls_sel.toArray(new Product[items_ls_sel.size()]);
				for (int j = 0; j < items_ls_sel.size(); j++) {	
					items_selected[j].setProduct_code(items_ls_sel.get(j).getProduct_code());
					items_selected[j].setProduct_name(items_ls_sel.get(j).getProduct_name());
					items_selected[j].setProduct_price(items_ls_sel.get(j).getProduct_price());
				}
				System.out.println("VIEWING CART INVENTORY WITH INSTANCES OF PRODUCT[] ");
				cart = new Cart(items_selected); 
				cart.viewStoredCartItems(); 
				
				// Begin insertion to the Database to the Cart_tbl
				driver.cart_insert_newData(cart, items_selected);
				
				// Compute for the prices_tot
		        /* Go to the Prices Column */
		        int rowCount = table.getRowCount();		        
		        for (int i = 0; i < table.getRowCount(); i++){
		        	// getting price values
		        	double amount = (double) table.getValueAt(i, table.getColumn("Price").getModelIndex());
		        	// computing for total
		            total += amount;
		            new_total_lbl = " " + String.valueOf(total);
		            System.out.println(total);
		            System.out.println("Set up new Total Label >> " + new_total_lbl); 
		        }
		        lblNewLabel_1_1.setText("TOTAL: " + new_total_lbl);
			}
        }); 
        //CHECKOUT FORMATTING ENDS HERE
        
        

        // Main Content for Appetizers
        // NOTE: The top part is filled first before the bottom part
        // Appetizer panels container of the food info, image, and buttons.
        JPanel appetizerP1 = new JPanel();
        JPanel appetizerP2 = new JPanel();
        JPanel appetizerP3 = new JPanel();
        JPanel appetizerP4 = new JPanel();
        JPanel appetizerP5 = new JPanel();
        JPanel appetizerP6 = new JPanel();

        // This is where the image will be placed
        JLabel calamari = new JLabel();
        calamari.setBounds(45, 40, 225, 235);
        JLabel chips = new JLabel();
        chips.setBounds(45, 40, 225, 225);
        JLabel lyonnaiseSalad = new JLabel();
        lyonnaiseSalad.setBounds(45, 40, 225, 225);
        JLabel nuts = new JLabel();
        nuts.setBounds(45, 40, 225, 225);
        JLabel potatoSalad = new JLabel();
        potatoSalad.setBounds(45, 40, 225, 225);
        JLabel salmonCanape = new JLabel();
        salmonCanape.setBounds(45, 40, 225, 225);

        // Gets image of the food
        Image calamariImage = new ImageIcon(this.getClass().getResource("/appetizers/calamari.png")).getImage();
        Image chipsImage = new ImageIcon(this.getClass().getResource("/appetizers/chips.png")).getImage();
        Image lyonnaiseSaladImage = new ImageIcon(this.getClass().getResource("/appetizers/lyonnaise salad.png"))
                .getImage();
        Image nutsImage = new ImageIcon(this.getClass().getResource("/appetizers/nuts.png")).getImage();
        Image potatoSaladImage = new ImageIcon(this.getClass().getResource("/appetizers/potato salad.png")).getImage();
        Image salmonCanapeImage = new ImageIcon(this.getClass().getResource("/appetizers/salmon canape.png"))
                .getImage();

        // Setting the image into the JLabel
        calamari.setIcon(new ImageIcon(calamariImage));
        chips.setIcon(new ImageIcon(chipsImage));
        lyonnaiseSalad.setIcon(new ImageIcon(lyonnaiseSaladImage));
        nuts.setIcon(new ImageIcon(nutsImage));
        potatoSalad.setIcon(new ImageIcon(potatoSaladImage));
        salmonCanape.setIcon(new ImageIcon(salmonCanapeImage));

        JButton minusCalamari = new JButton("-");
        minusCalamari.setBorder(EmptyBorder);
        minusCalamari.setFocusPainted(false);
        minusCalamari.setForeground(Color.WHITE);
        minusCalamari.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusCalamari.setBackground(SystemColor.controlDkShadow);
        minusCalamari.setBounds(60, 321, 87, 23);
        minusCalamari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * @TODO Remove rows: Calamari
                 */
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Calamari")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }          	
            }
        });
        appetizerP1.add(minusCalamari);

        JButton addCalamari = new JButton("+");
        addCalamari.setBorder(EmptyBorder);
        addCalamari.setFocusPainted(false);
        addCalamari.setForeground(Color.WHITE);
        addCalamari.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addCalamari.setBackground(SystemColor.controlDkShadow);
        addCalamari.setBounds(157, 321, 87, 23);
        addCalamari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * @TODO Adding rows: Calamari
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		
            		temp1 = driver.get_names("Calamari");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Calamari"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Calamari");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item); 
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            	//try lg kung gumagana add button na mag dadagdag s rows hehe
            	// checkoutTable.addRow(rows);									//gumagana nmn, pero etong button p lg gumagana since nag try lg aq
            	
            }
        });
        appetizerP1.add(addCalamari);

        JButton minusChips = new JButton("-");
        minusChips.setBorder(EmptyBorder);
        minusChips.setFocusPainted(false);
        minusChips.setForeground(Color.WHITE);
        minusChips.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusChips.setBackground(SystemColor.controlDkShadow);
        minusChips.setBounds(60, 321, 87, 23);
        minusChips.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Chips
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Chips")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }     
            	
            	
            }
        });
        appetizerP2.add(minusChips);

        JButton addChips = new JButton("+");
        addChips.setBorder(EmptyBorder);
        addChips.setFocusPainted(false);
        addChips.setForeground(Color.WHITE);
        addChips.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addChips.setBackground(SystemColor.controlDkShadow);
        addChips.setBounds(157, 321, 87, 23);
        addChips.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Chips
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		
            		temp1 = driver.get_names("Chips");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Chips"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	}
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Chips");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            }
        });
        appetizerP2.add(addChips);

        JButton minusLyonnaise = new JButton("-");
        minusLyonnaise.setBorder(EmptyBorder);
        minusLyonnaise.setFocusPainted(false);
        minusLyonnaise.setForeground(Color.WHITE);
        minusLyonnaise.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusLyonnaise.setBackground(SystemColor.controlDkShadow);
        minusLyonnaise.setBounds(60, 321, 87, 23);
        minusLyonnaise.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Layonnaise
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Layonnaise")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }     
            }
        });
        appetizerP3.add(minusLyonnaise);

        JButton addLyonnaise = new JButton("+");
        addLyonnaise.setBorder(EmptyBorder);
        addLyonnaise.setFocusPainted(false);
        addLyonnaise.setForeground(Color.WHITE);
        addLyonnaise.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addLyonnaise.setBackground(SystemColor.controlDkShadow);
        addLyonnaise.setBounds(157, 321, 87, 23);
        addLyonnaise.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Layonnaise
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Layonnaise");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Layonnaise"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Layonnaise");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        		
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            }
        });
        appetizerP3.add(addLyonnaise);

        JButton minusNuts = new JButton("-");
        minusNuts.setBorder(EmptyBorder);
        minusNuts.setFocusPainted(false);
        minusNuts.setForeground(Color.WHITE);
        minusNuts.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusNuts.setBackground(SystemColor.controlDkShadow);
        minusNuts.setBounds(60, 321, 87, 23);
        minusNuts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Nuts
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Nuts")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }     
            	
            	
            }
        });
        appetizerP4.add(minusNuts);

        JButton addNuts = new JButton("+");
        addNuts.setBorder(EmptyBorder);
        addNuts.setFocusPainted(false);
        addNuts.setForeground(Color.WHITE);
        addNuts.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addNuts.setBackground(SystemColor.controlDkShadow);
        addNuts.setBounds(157, 321, 87, 23);
        addNuts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Nuts
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Nuts");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Nuts"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Nuts");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            }
        });
        appetizerP4.add(addNuts);

        JButton minusPotato = new JButton("-");
        minusPotato.setBorder(EmptyBorder);
        minusPotato.setFocusPainted(false);
        minusPotato.setForeground(Color.WHITE);
        minusPotato.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusPotato.setBackground(SystemColor.controlDkShadow);
        minusPotato.setBounds(60, 321, 87, 23);
        minusPotato.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Potato Salad
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Potato Salad")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }     

            }
        });
        appetizerP5.add(minusPotato);

        JButton addPotato = new JButton("+");
        addPotato.setBorder(EmptyBorder);
        addPotato.setFocusPainted(false);
        addPotato.setForeground(Color.WHITE);
        addPotato.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addPotato.setBackground(SystemColor.controlDkShadow);
        addPotato.setBounds(157, 321, 87, 23);
        addPotato.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.s
            	/**
                 * @TODO Adding rows: Potato Salad
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Potato Salad");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Potato Salad"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Potato Salad");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            }
        });
        appetizerP5.add(addPotato);

        JButton minusSalmon = new JButton("-");
        minusSalmon.setBorder(EmptyBorder);
        minusSalmon.setFocusPainted(false);
        minusSalmon.setForeground(Color.WHITE);
        minusSalmon.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusSalmon.setBackground(SystemColor.controlDkShadow);
        minusSalmon.setBounds(60, 321, 87, 23);
        minusSalmon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Salmon Canape
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Salmon Canape")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }     

            }
        });
        appetizerP6.add(minusSalmon);

        JButton addSalmon = new JButton("+");
        addSalmon.setBorder(EmptyBorder);
        addSalmon.setFocusPainted(false);
        addSalmon.setForeground(Color.WHITE);
        addSalmon.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addSalmon.setBackground(SystemColor.controlDkShadow);
        addSalmon.setBounds(157, 321, 87, 23);
        addSalmon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Salmon Canape
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Salmon Canape");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Salmon Canape"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Salmon Canape");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            }
        });
        appetizerP6.add(addSalmon);

        JLabel calamariText = new JLabel("Calamari - PHP 199");
        calamariText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        calamariText.setBounds(92, 284, 152, 26);

        JLabel chipsText = new JLabel("Chips - PHP 99");
        chipsText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        chipsText.setBounds(100, 284, 137, 26);

        JLabel lyonnaiseSaladText = new JLabel("Lyonnaise Salad - PHP 249");
        lyonnaiseSaladText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        lyonnaiseSaladText.setBounds(60, 284, 192, 26);

        JLabel nutsText = new JLabel("Nuts - PHP 49");
        nutsText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        nutsText.setBounds(100, 284, 131, 26);

        JLabel potatoSaladText = new JLabel("Potato Salad - PHP 174");
        potatoSaladText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        potatoSaladText.setBounds(70, 284, 180, 26);

        JLabel salmonCanapeText = new JLabel("Salmon Canape - PHP 169");
        salmonCanapeText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        salmonCanapeText.setBounds(60, 284, 200, 26);

        appetizerP1.add(calamariText);
        appetizerP2.add(chipsText);
        appetizerP3.add(lyonnaiseSaladText);
        appetizerP4.add(nutsText);
        appetizerP5.add(potatoSaladText);
        appetizerP6.add(salmonCanapeText);

        appetizerP1.setLayout(null);
        appetizerP1.add(calamari);
        appetizerP2.setLayout(null);
        appetizerP2.add(chips);
        appetizerP3.setLayout(null);
        appetizerP3.add(lyonnaiseSalad);
        appetizerP4.setLayout(null);
        appetizerP4.add(nuts);
        appetizerP5.setLayout(null);
        appetizerP5.add(potatoSalad);
        appetizerP6.setLayout(null);
        appetizerP6.add(salmonCanape);

        // sets background color so that they blend with their respective
        appetizerP1.setBackground(appetizerColor);
        appetizerP2.setBackground(appetizerColor);
        appetizerP3.setBackground(appetizerColor);
        appetizerP4.setBackground(appetizerColor);
        appetizerP5.setBackground(appetizerColor);
        appetizerP6.setBackground(appetizerColor);

        // Adding each sub panel into the appetizer panel.

        JLabel calamariBGColor = new JLabel();
        calamariBGColor.setBackground(Color.WHITE);
        calamariBGColor.setBounds(20, 23, 264, 334);
        calamariBGColor.setOpaque(true);

        JLabel chipsBGColor = new JLabel();
        chipsBGColor.setBackground(Color.WHITE);
        chipsBGColor.setBounds(25, 23, 264, 334);
        chipsBGColor.setOpaque(true);

        JLabel lyonnaiseSaladBGColor = new JLabel();
        lyonnaiseSaladBGColor.setBackground(Color.WHITE);
        lyonnaiseSaladBGColor.setBounds(25, 23, 264, 334);
        lyonnaiseSaladBGColor.setOpaque(true);

        JLabel nutsBGColor = new JLabel();
        nutsBGColor.setBackground(Color.WHITE);
        nutsBGColor.setBounds(25, 23, 264, 334);
        nutsBGColor.setOpaque(true);

        JLabel potatoSaladBGColor = new JLabel();
        potatoSaladBGColor.setBackground(Color.WHITE);
        potatoSaladBGColor.setBounds(25, 23, 264, 334);
        potatoSaladBGColor.setOpaque(true);

        JLabel salmonCanapeBGColor = new JLabel();
        salmonCanapeBGColor.setBackground(Color.WHITE);
        salmonCanapeBGColor.setBounds(25, 23, 264, 334);
        salmonCanapeBGColor.setOpaque(true);

        appetizerP1.add(calamariBGColor);
        appetizerP2.add(chipsBGColor);
        appetizerP3.add(lyonnaiseSaladBGColor);
        appetizerP4.add(nutsBGColor);
        appetizerP5.add(potatoSaladBGColor);
        appetizerP6.add(salmonCanapeBGColor);

        appetizerPanel.add(appetizerP1);
        appetizerPanel.add(appetizerP2);
        appetizerPanel.add(appetizerP3);
        appetizerPanel.add(appetizerP4);
        appetizerPanel.add(appetizerP5);
        appetizerPanel.add(appetizerP6);

        // Main Content for Main Dish
        JPanel mainDishP1 = new JPanel();
        JPanel mainDishP2 = new JPanel();
        JPanel mainDishP3 = new JPanel();
        JPanel mainDishP4 = new JPanel();
        JPanel mainDishP5 = new JPanel();
        JPanel mainDishP6 = new JPanel();

        // This is where the image will be placed
        JLabel adobo = new JLabel();
        adobo.setBounds(45, 40, 225, 235);
        JLabel lasagna = new JLabel();
        lasagna.setBounds(45, 40, 225, 225);
        JLabel risotto = new JLabel();
        risotto.setBounds(45, 40, 225, 225);
        JLabel salmon = new JLabel();
        salmon.setBounds(45, 40, 225, 225);
        JLabel seaFoodPlatter = new JLabel();
        seaFoodPlatter.setBounds(45, 40, 225, 225);
        JLabel steak = new JLabel();
        steak.setBounds(45, 40, 225, 225);

        // Gets image of the food
        Image adoboImage = new ImageIcon(this.getClass().getResource("/mainDish/adobo.png")).getImage();
        Image lasagnaImage = new ImageIcon(this.getClass().getResource("/mainDish/lasagna.png")).getImage();
        Image risottoImage = new ImageIcon(this.getClass().getResource("/mainDish/risotto.png"))
                .getImage();
        Image salmonImage = new ImageIcon(this.getClass().getResource("/mainDish/salmon.png")).getImage();
        Image seaFoodPlatterImage = new ImageIcon(this.getClass().getResource("/mainDish/seafoodplatter.png"))
                .getImage();
        Image steakImage = new ImageIcon(this.getClass().getResource("/mainDish/steak.png"))
                .getImage();

        // Setting the image into the JLabel
        adobo.setIcon(new ImageIcon(adoboImage));
        lasagna.setIcon(new ImageIcon(lasagnaImage));
        risotto.setIcon(new ImageIcon(risottoImage));
        salmon.setIcon(new ImageIcon(salmonImage));
        seaFoodPlatter.setIcon(new ImageIcon(seaFoodPlatterImage));
        steak.setIcon(new ImageIcon(steakImage));

        JButton minusAdobo = new JButton("-");
        minusAdobo.setBorder(EmptyBorder);
        minusAdobo.setFocusPainted(false);
        minusAdobo.setForeground(Color.WHITE);
        minusAdobo.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusAdobo.setBackground(SystemColor.controlDkShadow);
        minusAdobo.setBounds(60, 321, 87, 23);
        minusAdobo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Adobo
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Adobo")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }
            }
        });
        mainDishP1.add(minusAdobo);

        JButton addAdobo = new JButton("+");
        addAdobo.setBorder(EmptyBorder);
        addAdobo.setFocusPainted(false);
        addAdobo.setForeground(Color.WHITE);
        addAdobo.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addAdobo.setBackground(SystemColor.controlDkShadow);
        addAdobo.setBounds(157, 321, 87, 23);
        addAdobo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Adobo
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Adobo");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Adobo"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Adobo");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        		
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            }
        });
        mainDishP1.add(addAdobo);

        JButton minusLasagna = new JButton("-");
        minusLasagna.setBorder(EmptyBorder);
        minusLasagna.setFocusPainted(false);
        minusLasagna.setForeground(Color.WHITE);
        minusLasagna.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusLasagna.setBackground(SystemColor.controlDkShadow);
        minusLasagna.setBounds(60, 321, 87, 23);
        minusLasagna.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Lasagna
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Lasagna")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            	
            }
        });
        mainDishP2.add(minusLasagna);

        JButton addLasagna = new JButton("+");
        addLasagna.setBorder(EmptyBorder);
        addLasagna.setFocusPainted(false);
        addLasagna.setForeground(Color.WHITE);
        addLasagna.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addLasagna.setBackground(SystemColor.controlDkShadow);
        addLasagna.setBounds(157, 321, 87, 23);
        addLasagna.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Lasagna
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Lasagna");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Lasagna"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Lasagna");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            }
        });
        mainDishP2.add(addLasagna);

        JButton minusRisotto = new JButton("-");
        minusRisotto.setBorder(EmptyBorder);
        minusRisotto.setFocusPainted(false);
        minusRisotto.setForeground(Color.WHITE);
        minusRisotto.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusRisotto.setBackground(SystemColor.controlDkShadow);
        minusRisotto.setBounds(60, 321, 87, 23);
        minusRisotto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Risotto
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Risotto")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        mainDishP3.add(minusRisotto);

        JButton addRisotto = new JButton("+");
        addRisotto.setBorder(EmptyBorder);
        addRisotto.setFocusPainted(false);
        addRisotto.setForeground(Color.WHITE);
        addRisotto.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addRisotto.setBackground(SystemColor.controlDkShadow);
        addRisotto.setBounds(157, 321, 87, 23);
        addRisotto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Risotto
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Risotto");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Risotto"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Risotto");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        mainDishP3.add(addRisotto);

        JButton minusSalmonDish = new JButton("-");
        minusSalmonDish.setBorder(EmptyBorder);
        minusSalmonDish.setFocusPainted(false);
        minusSalmonDish.setForeground(Color.WHITE);
        minusSalmonDish.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusSalmonDish.setBackground(SystemColor.controlDkShadow);
        minusSalmonDish.setBounds(60, 321, 87, 23);
        minusSalmonDish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Salmon
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Salmon")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        mainDishP4.add(minusSalmonDish);

        JButton addSalmonDish = new JButton("+");
        addSalmonDish.setBorder(EmptyBorder);
        addSalmonDish.setFocusPainted(false);
        addSalmonDish.setForeground(Color.WHITE);
        addSalmonDish.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addSalmonDish.setBackground(SystemColor.controlDkShadow);
        addSalmonDish.setBounds(157, 321, 87, 23);
        addSalmonDish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Salmon
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Salmon");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Salmon"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Salmon");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        mainDishP4.add(addSalmonDish);

        JButton minusSeaFoodPlatter = new JButton("-");
        minusSeaFoodPlatter.setBorder(EmptyBorder);
        minusSeaFoodPlatter.setFocusPainted(false);
        minusSeaFoodPlatter.setForeground(Color.WHITE);
        minusSeaFoodPlatter.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusSeaFoodPlatter.setBackground(SystemColor.controlDkShadow);
        minusSeaFoodPlatter.setBounds(60, 321, 87, 23);
        minusSeaFoodPlatter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Sea Food Platter
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Sea Food Platter")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        mainDishP5.add(minusSeaFoodPlatter);

        JButton addSeaFoodPlatter = new JButton("+");
        addSeaFoodPlatter.setBorder(EmptyBorder);
        addSeaFoodPlatter.setFocusPainted(false);
        addSeaFoodPlatter.setForeground(Color.WHITE);
        addSeaFoodPlatter.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addSeaFoodPlatter.setBackground(SystemColor.controlDkShadow);
        addSeaFoodPlatter.setBounds(157, 321, 87, 23);
        addSeaFoodPlatter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Sea Food Platter
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Sea Food Platter");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Sea Food Platter"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Sea Food Platter");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        mainDishP5.add(addSeaFoodPlatter);

        JButton minusSteak = new JButton("-");
        minusSteak.setBorder(EmptyBorder);
        minusSteak.setFocusPainted(false);
        minusSteak.setForeground(Color.WHITE);
        minusSteak.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusSteak.setBackground(SystemColor.controlDkShadow);
        minusSteak.setBounds(60, 321, 87, 23);
        minusSteak.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Steak
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Steak")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            	
            }
        });
        mainDishP6.add(minusSteak);

        JButton addSteak = new JButton("+");
        addSteak.setBorder(EmptyBorder);
        addSteak.setFocusPainted(false);
        addSteak.setForeground(Color.WHITE);
        addSteak.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addSteak.setBackground(SystemColor.controlDkShadow);
        addSteak.setBounds(157, 321, 87, 23);
        addSteak.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Steak
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Steak");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Steak"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Steak");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        mainDishP6.add(addSteak);

        JLabel adoboText = new JLabel("Adobo - PHP 299");
        adoboText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        adoboText.setBounds(92, 284, 131, 26);

        JLabel lasagnaText = new JLabel("Lasagna - PHP 289");
        lasagnaText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        lasagnaText.setBounds(92, 284, 144, 26);

        JLabel risottoText = new JLabel("Risotto - PHP 249");
        risottoText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        risottoText.setBounds(92, 284, 180, 26);

        JLabel salmonText = new JLabel("Salmon - PHP 229");
        salmonText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        salmonText.setBounds(92, 284, 131, 26);

        JLabel seaFoodPlatterText = new JLabel("Sea Food Platter - PHP 449");
        seaFoodPlatterText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        seaFoodPlatterText.setBounds(60, 284, 200, 26);

        JLabel steakText = new JLabel("Steak - PHP 499");
        steakText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        steakText.setBounds(100, 284, 180, 26);

        mainDishP1.add(adoboText);
        mainDishP2.add(lasagnaText);
        mainDishP3.add(risottoText);
        mainDishP4.add(salmonText);
        mainDishP5.add(seaFoodPlatterText);
        mainDishP6.add(steakText);

        mainDishP1.setLayout(null);
        mainDishP1.add(adobo);
        mainDishP2.setLayout(null);
        mainDishP2.add(lasagna);
        mainDishP3.setLayout(null);
        mainDishP3.add(risotto);
        mainDishP4.setLayout(null);
        mainDishP4.add(salmon);
        mainDishP5.setLayout(null);
        mainDishP5.add(seaFoodPlatter);
        mainDishP6.setLayout(null);
        mainDishP6.add(steak);

        // sets background color so that they blend with their respective
        mainDishP1.setBackground(mainDishColor);
        mainDishP2.setBackground(mainDishColor);
        mainDishP3.setBackground(mainDishColor);
        mainDishP4.setBackground(mainDishColor);
        mainDishP5.setBackground(mainDishColor);
        mainDishP6.setBackground(mainDishColor);

        // Adding each sub panel into the appetizer panel.

        JLabel adoboBGColor = new JLabel();
        adoboBGColor.setBackground(Color.WHITE);
        adoboBGColor.setBounds(20, 23, 264, 334);
        adoboBGColor.setOpaque(true);

        JLabel lasagnaBGColor = new JLabel();
        lasagnaBGColor.setBackground(Color.WHITE);
        lasagnaBGColor.setBounds(25, 23, 264, 334);
        lasagnaBGColor.setOpaque(true);

        JLabel risottoBGColor = new JLabel();
        risottoBGColor.setBackground(Color.WHITE);
        risottoBGColor.setBounds(25, 23, 264, 334);
        risottoBGColor.setOpaque(true);

        JLabel salmonBGColor = new JLabel();
        salmonBGColor.setBackground(Color.WHITE);
        salmonBGColor.setBounds(25, 23, 264, 334);
        salmonBGColor.setOpaque(true);

        JLabel seaFoodPlatterBGColor = new JLabel();
        seaFoodPlatterBGColor.setBackground(Color.WHITE);
        seaFoodPlatterBGColor.setBounds(25, 23, 264, 334);
        seaFoodPlatterBGColor.setOpaque(true);

        JLabel steakBGColor = new JLabel();
        steakBGColor.setBackground(Color.WHITE);
        steakBGColor.setBounds(25, 23, 264, 334);
        steakBGColor.setOpaque(true);

        mainDishP1.add(adoboBGColor);
        mainDishP2.add(lasagnaBGColor);
        mainDishP3.add(risottoBGColor);
        mainDishP4.add(salmonBGColor);
        mainDishP5.add(seaFoodPlatterBGColor);
        mainDishP6.add(steakBGColor);

        mainDishPanel.add(mainDishP1);
        mainDishPanel.add(mainDishP2);
        mainDishPanel.add(mainDishP3);
        mainDishPanel.add(mainDishP4);
        mainDishPanel.add(mainDishP5);
        mainDishPanel.add(mainDishP6);

        // Main Content for Sides
        JPanel sidesP1 = new JPanel();
        JPanel sidesP2 = new JPanel();
        JPanel sidesP3 = new JPanel();
        JPanel sidesP4 = new JPanel();
        JPanel sidesP5 = new JPanel();
        JPanel sidesP6 = new JPanel();

        // This is where the image will be placed
        JLabel gamjajeon = new JLabel();
        gamjajeon.setBounds(45, 40, 225, 235);
        JLabel gyoza = new JLabel();
        gyoza.setBounds(45, 40, 225, 225);
        JLabel kimchi = new JLabel();
        kimchi.setBounds(45, 40, 225, 225);
        JLabel natto = new JLabel();
        natto.setBounds(45, 40, 225, 225);
        JLabel sushi = new JLabel();
        sushi.setBounds(45, 40, 225, 225);
        JLabel tamagoyaki = new JLabel();
        tamagoyaki.setBounds(45, 40, 225, 225);

        // Gets image of the food
        Image gamjajeonImage = new ImageIcon(this.getClass().getResource("/sideDish/gamjajeon.png")).getImage();
        Image gyozaImage = new ImageIcon(this.getClass().getResource("/sideDish/gyoza.png")).getImage();
        Image kimchiImage = new ImageIcon(this.getClass().getResource("/sideDish/kimchi.png"))
                .getImage();
        Image nattoImage = new ImageIcon(this.getClass().getResource("/sideDish/natto.png")).getImage();
        Image sushiImage = new ImageIcon(this.getClass().getResource("/sideDish/sushi.png"))
                .getImage();
        Image tamagoyakiImage = new ImageIcon(this.getClass().getResource("/sideDish/tamagoyaki.png"))
                .getImage();

        // Setting the image into the JLabel
        gamjajeon.setIcon(new ImageIcon(gamjajeonImage));
        gyoza.setIcon(new ImageIcon(gyozaImage));
        kimchi.setIcon(new ImageIcon(kimchiImage));
        natto.setIcon(new ImageIcon(nattoImage));
        sushi.setIcon(new ImageIcon(sushiImage));
        tamagoyaki.setIcon(new ImageIcon(tamagoyakiImage));

        JButton minusGamjajeon = new JButton("-");
        minusGamjajeon.setBorder(EmptyBorder);
        minusGamjajeon.setFocusPainted(false);
        minusGamjajeon.setForeground(Color.WHITE);
        minusGamjajeon.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusGamjajeon.setBackground(SystemColor.controlDkShadow);
        minusGamjajeon.setBounds(60, 321, 87, 23);
        minusGamjajeon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Gamjajeon
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Gamjajeon")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            	
            }
        });
        sidesP1.add(minusGamjajeon);

        JButton addGamjajeon = new JButton("+");
        addGamjajeon.setBorder(EmptyBorder);
        addGamjajeon.setFocusPainted(false);
        addGamjajeon.setForeground(Color.WHITE);
        addGamjajeon.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addGamjajeon.setBackground(SystemColor.controlDkShadow);
        addGamjajeon.setBounds(157, 321, 87, 23);
        addGamjajeon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Gamjajeon
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Gamjajeon");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Gamjajeon"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Gamjajeon");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        sidesP1.add(addGamjajeon);

        JButton minusGyoza = new JButton("-");
        minusGyoza.setBorder(EmptyBorder);
        minusGyoza.setFocusPainted(false);
        minusGyoza.setForeground(Color.WHITE);
        minusGyoza.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusGyoza.setBackground(SystemColor.controlDkShadow);
        minusGyoza.setBounds(60, 321, 87, 23);
        minusGyoza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Gyoza
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Gyoza")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        sidesP2.add(minusGyoza);

        JButton addGyoza = new JButton("+");
        addGyoza.setBorder(EmptyBorder);
        addGyoza.setFocusPainted(false);
        addGyoza.setForeground(Color.WHITE);
        addGyoza.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addGyoza.setBackground(SystemColor.controlDkShadow);
        addGyoza.setBounds(157, 321, 87, 23);
        addGyoza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "-" button is pressed.
            	/**
                 * @TODO Adding rows: Gyoza
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Gyoza");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Gyoza"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Gyoza");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        sidesP2.add(addGyoza);

        JButton minusKimchi = new JButton("-");
        minusKimchi.setBorder(EmptyBorder);
        minusKimchi.setFocusPainted(false);
        minusKimchi.setForeground(Color.WHITE);
        minusKimchi.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusKimchi.setBackground(SystemColor.controlDkShadow);
        minusKimchi.setBounds(60, 321, 87, 23);
        minusKimchi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Kimchi
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Kimchi")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        sidesP3.add(minusKimchi);

        JButton addKimchi = new JButton("+");
        addKimchi.setBorder(EmptyBorder);
        addKimchi.setFocusPainted(false);
        addKimchi.setForeground(Color.WHITE);
        addKimchi.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addKimchi.setBackground(SystemColor.controlDkShadow);
        addKimchi.setBounds(157, 321, 87, 23);
        addKimchi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Kimchi
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Kimchi");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Kimchi"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Kimchi");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        sidesP3.add(addKimchi);

        JButton minusNatto = new JButton("-");
        minusNatto.setBorder(EmptyBorder);
        minusNatto.setFocusPainted(false);
        minusNatto.setForeground(Color.WHITE);
        minusNatto.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusNatto.setBackground(SystemColor.controlDkShadow);
        minusNatto.setBounds(60, 321, 87, 23);
        minusNatto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Natto
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Natto")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }
            	
            }
        });
        sidesP4.add(minusNatto);

        JButton addNatto = new JButton("+");
        addNatto.setBorder(EmptyBorder);
        addNatto.setFocusPainted(false);
        addNatto.setForeground(Color.WHITE);
        addNatto.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addNatto.setBackground(SystemColor.controlDkShadow);
        addNatto.setBounds(157, 321, 87, 23);
        addNatto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Natto
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Natto");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Natto"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Natto");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        		
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        sidesP4.add(addNatto);

        JButton minusSushi = new JButton("-");
        minusSushi.setBorder(EmptyBorder);
        minusSushi.setFocusPainted(false);
        minusSushi.setForeground(Color.WHITE);
        minusSushi.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusSushi.setBackground(SystemColor.controlDkShadow);
        minusSushi.setBounds(60, 321, 87, 23);
        minusSushi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Sushi
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Sushi")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            	
            }
        });
        sidesP5.add(minusSushi);

        JButton addSushi = new JButton("+");
        addSushi.setBorder(EmptyBorder);
        addSushi.setFocusPainted(false);
        addSushi.setForeground(Color.WHITE);
        addSushi.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addSushi.setBackground(SystemColor.controlDkShadow);
        addSushi.setBounds(157, 321, 87, 23);
        addSushi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Sushi
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Sushi");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Sushi"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Sushi");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        sidesP5.add(addSushi);

        JButton minusTamagoyaki = new JButton("-");
        minusTamagoyaki.setBorder(EmptyBorder);
        minusTamagoyaki.setFocusPainted(false);
        minusTamagoyaki.setForeground(Color.WHITE);
        minusTamagoyaki.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minusTamagoyaki.setBackground(SystemColor.controlDkShadow);
        minusTamagoyaki.setBounds(60, 321, 87, 23);
        minusTamagoyaki.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Tamagoyaki
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Tamagoyaki")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }
            	
            	
            }
        });
        sidesP6.add(minusTamagoyaki);

        JButton addTamagoyaki = new JButton("+");
        addTamagoyaki.setBorder(EmptyBorder);
        addTamagoyaki.setFocusPainted(false);
        addTamagoyaki.setForeground(Color.WHITE);
        addTamagoyaki.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addTamagoyaki.setBackground(SystemColor.controlDkShadow);
        addTamagoyaki.setBounds(157, 321, 87, 23);
        addTamagoyaki.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Tamagoyaki
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Tamagoyaki");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Tamagoyaki"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Tamagoyaki");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        sidesP6.add(addTamagoyaki);

        JLabel gamjajeonText = new JLabel("Gamjajeon - PHP 249");
        gamjajeonText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        gamjajeonText.setBounds(73, 284, 160, 26);

        JLabel gyozaText = new JLabel("Gyoza - PHP 139");
        gyozaText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        gyozaText.setBounds(92, 284, 131, 26);

        JLabel kimchiText = new JLabel("Kimchi - PHP 229");
        kimchiText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        kimchiText.setBounds(92, 284, 180, 26);

        JLabel nattoText = new JLabel("Natto - PHP 69");
        nattoText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        nattoText.setBounds(92, 284, 131, 26);

        JLabel sushiText = new JLabel("Sushi - PHP 89");
        sushiText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        sushiText.setBounds(92, 284, 180, 26);

        JLabel tamagoyakiText = new JLabel("Tamagoyaki - PHP 499");
        tamagoyakiText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        tamagoyakiText.setBounds(87, 284, 180, 26);

        sidesP1.add(gamjajeonText);
        sidesP2.add(gyozaText);
        sidesP3.add(kimchiText);
        sidesP4.add(nattoText);
        sidesP5.add(sushiText);
        sidesP6.add(tamagoyakiText);

        sidesP1.setLayout(null);
        sidesP1.add(gamjajeon);
        sidesP2.setLayout(null);
        sidesP2.add(gyoza);
        sidesP3.setLayout(null);
        sidesP3.add(kimchi);
        sidesP4.setLayout(null);
        sidesP4.add(natto);
        sidesP5.setLayout(null);
        sidesP5.add(sushi);
        sidesP6.setLayout(null);
        sidesP6.add(tamagoyaki);

        // sets background color so that they blend with their respective
        sidesP1.setBackground(sidesColor);
        sidesP2.setBackground(sidesColor);
        sidesP3.setBackground(sidesColor);
        sidesP4.setBackground(sidesColor);
        sidesP5.setBackground(sidesColor);
        sidesP6.setBackground(sidesColor);

        // Adding each sub panel into the appetizer panel.

        JLabel gamjajeonBGColor = new JLabel();
        gamjajeonBGColor.setBackground(Color.WHITE);
        gamjajeonBGColor.setBounds(20, 23, 264, 334);
        gamjajeonBGColor.setOpaque(true);

        JLabel gyozaBGColor = new JLabel();
        gyozaBGColor.setBackground(Color.WHITE);
        gyozaBGColor.setBounds(25, 23, 264, 334);
        gyozaBGColor.setOpaque(true);

        JLabel kimchiBGColor = new JLabel();
        kimchiBGColor.setBackground(Color.WHITE);
        kimchiBGColor.setBounds(25, 23, 264, 334);
        kimchiBGColor.setOpaque(true);

        JLabel nattoBGColor = new JLabel();
        nattoBGColor.setBackground(Color.WHITE);
        nattoBGColor.setBounds(25, 23, 264, 334);
        nattoBGColor.setOpaque(true);

        JLabel sushiBGColor = new JLabel();
        sushiBGColor.setBackground(Color.WHITE);
        sushiBGColor.setBounds(25, 23, 264, 334);
        sushiBGColor.setOpaque(true);

        JLabel tamagoyakiBGColor = new JLabel();
        tamagoyakiBGColor.setBackground(Color.WHITE);
        tamagoyakiBGColor.setBounds(25, 23, 264, 334);
        tamagoyakiBGColor.setOpaque(true);

        sidesP1.add(gamjajeonBGColor);
        sidesP2.add(gyozaBGColor);
        sidesP3.add(kimchiBGColor);
        sidesP4.add(nattoBGColor);
        sidesP5.add(sushiBGColor);
        sidesP6.add(tamagoyakiBGColor);

        sidesPanel.add(sidesP1);
        sidesPanel.add(sidesP2);
        sidesPanel.add(sidesP3);
        sidesPanel.add(sidesP4);
        sidesPanel.add(sidesP5);
        sidesPanel.add(sidesP6);

        // Main Content for Desserts
        JPanel dessertsP1 = new JPanel();
        JPanel dessertsP2 = new JPanel();
        JPanel dessertsP3 = new JPanel();
        JPanel dessertsP4 = new JPanel();
        JPanel dessertsP5 = new JPanel();
        JPanel dessertsP6 = new JPanel();

        // This is where the image will be placed
        JLabel coffeeJelly = new JLabel();
        coffeeJelly.setBounds(45, 40, 225, 235);
        JLabel halohalo = new JLabel();
        halohalo.setBounds(45, 40, 225, 225);
        JLabel lecheflan = new JLabel();
        lecheflan.setBounds(45, 40, 225, 225);
        JLabel panettone = new JLabel();
        panettone.setBounds(45, 40, 225, 225);
        JLabel pannaCotta = new JLabel();
        pannaCotta.setBounds(45, 40, 225, 225);
        JLabel tiramisu = new JLabel();
        tiramisu.setBounds(45, 40, 225, 225);

        // Gets image of the food
        Image coffeeJellyImage = new ImageIcon(this.getClass().getResource("/desserts/coffee jelly.png")).getImage();
        Image halohaloImage = new ImageIcon(this.getClass().getResource("/desserts/halohalo.png")).getImage();
        Image lecheflanImage = new ImageIcon(this.getClass().getResource("/desserts/lecheflan.png"))
                .getImage();
        Image panettoneImage = new ImageIcon(this.getClass().getResource("/desserts/panettone.png")).getImage();
        Image pannaCottaImage = new ImageIcon(this.getClass().getResource("/desserts/panna cotta.png"))
                .getImage();
        Image tiramisuImage = new ImageIcon(this.getClass().getResource("/desserts/tiramisu.png"))
                .getImage();

        // Setting the image into the JLabel
        coffeeJelly.setIcon(new ImageIcon(coffeeJellyImage));
        halohalo.setIcon(new ImageIcon(halohaloImage));
        lecheflan.setIcon(new ImageIcon(lecheflanImage));
        panettone.setIcon(new ImageIcon(panettoneImage));
        pannaCotta.setIcon(new ImageIcon(pannaCottaImage));
        tiramisu.setIcon(new ImageIcon(tiramisuImage));

        JButton minuscoffeeJelly = new JButton("-");
        minuscoffeeJelly.setBorder(EmptyBorder);
        minuscoffeeJelly.setFocusPainted(false);
        minuscoffeeJelly.setForeground(Color.WHITE);
        minuscoffeeJelly.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minuscoffeeJelly.setBackground(SystemColor.controlDkShadow);
        minuscoffeeJelly.setBounds(60, 321, 87, 23);
        minuscoffeeJelly.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Coffee Jelley
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Coffee Jelley")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }
            }
        });
        dessertsP1.add(minuscoffeeJelly);

        JButton addcoffeeJelly = new JButton("+");
        addcoffeeJelly.setBorder(EmptyBorder);
        addcoffeeJelly.setFocusPainted(false);
        addcoffeeJelly.setForeground(Color.WHITE);
        addcoffeeJelly.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addcoffeeJelly.setBackground(SystemColor.controlDkShadow);
        addcoffeeJelly.setBounds(157, 321, 87, 23);
        addcoffeeJelly.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Coffee Jelley
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Coffee Jelley");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Coffee Jelley"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Coffee Jelley");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        dessertsP1.add(addcoffeeJelly);

        JButton minushalohalo = new JButton("-");
        minushalohalo.setBorder(EmptyBorder);
        minushalohalo.setFocusPainted(false);
        minushalohalo.setForeground(Color.WHITE);
        minushalohalo.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minushalohalo.setBackground(SystemColor.controlDkShadow);
        minushalohalo.setBounds(60, 321, 87, 23);
        minushalohalo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Halo-Halo
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Halo-Halo")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        dessertsP2.add(minushalohalo);

        JButton addhalohalo = new JButton("+");
        addhalohalo.setBorder(EmptyBorder);
        addhalohalo.setFocusPainted(false);
        addhalohalo.setForeground(Color.WHITE);
        addhalohalo.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addhalohalo.setBackground(SystemColor.controlDkShadow);
        addhalohalo.setBounds(157, 321, 87, 23);
        addhalohalo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Halo-Halo
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Halo-Halo");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Halo-Halo"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Halo-Halo");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        dessertsP2.add(addhalohalo);

        JButton minuslecheflan = new JButton("-");
        minuslecheflan.setBorder(EmptyBorder);
        minuslecheflan.setFocusPainted(false);
        minuslecheflan.setForeground(Color.WHITE);
        minuslecheflan.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minuslecheflan.setBackground(SystemColor.controlDkShadow);
        minuslecheflan.setBounds(60, 321, 87, 23);
        minuslecheflan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Leche Flan
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Leche Flan")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        dessertsP3.add(minuslecheflan);

        JButton addlecheflan = new JButton("+");
        addlecheflan.setBorder(EmptyBorder);
        addlecheflan.setFocusPainted(false);
        addlecheflan.setForeground(Color.WHITE);
        addlecheflan.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addlecheflan.setBackground(SystemColor.controlDkShadow);
        addlecheflan.setBounds(157, 321, 87, 23);
        addlecheflan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Leche Flan
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Leche Flan");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Leche Flan"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Leche Flan");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        dessertsP3.add(addlecheflan);

        JButton minuspanettone = new JButton("-");
        minuspanettone.setBorder(EmptyBorder);
        minuspanettone.setFocusPainted(false);
        minuspanettone.setForeground(Color.WHITE);
        minuspanettone.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minuspanettone.setBackground(SystemColor.controlDkShadow);
        minuspanettone.setBounds(60, 321, 87, 23);
        minuspanettone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Pannettone
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Pannettone")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        dessertsP4.add(minuspanettone);

        JButton addpanettone = new JButton("+");
        addpanettone.setBorder(EmptyBorder);
        addpanettone.setFocusPainted(false);
        addpanettone.setForeground(Color.WHITE);
        addpanettone.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addpanettone.setBackground(SystemColor.controlDkShadow);
        addpanettone.setBounds(157, 321, 87, 23);
        addpanettone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Pannettone
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Pannettone");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Pannettone"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Pannettone");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            }
        });
        dessertsP4.add(addpanettone);

        JButton minuspannaCotta = new JButton("-");
        minuspannaCotta.setBorder(EmptyBorder);
        minuspannaCotta.setFocusPainted(false);
        minuspannaCotta.setForeground(Color.WHITE);
        minuspannaCotta.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minuspannaCotta.setBackground(SystemColor.controlDkShadow);
        minuspannaCotta.setBounds(60, 321, 87, 23);
        minuspannaCotta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Panna Cotta
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Panna Cotta")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }

            }
        });
        dessertsP5.add(minuspannaCotta);

        JButton addpannaCotta = new JButton("+");
        addpannaCotta.setBorder(EmptyBorder);
        addpannaCotta.setFocusPainted(false);
        addpannaCotta.setForeground(Color.WHITE);
        addpannaCotta.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addpannaCotta.setBackground(SystemColor.controlDkShadow);
        addpannaCotta.setBounds(157, 321, 87, 23);
        addpannaCotta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Panna Cotta
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Panna Cotta");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Panna Cotta"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Panna Cotta");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        		
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        dessertsP5.add(addpannaCotta);

        JButton minustiramisu = new JButton("-");
        minustiramisu.setBorder(EmptyBorder);
        minustiramisu.setFocusPainted(false);
        minustiramisu.setForeground(Color.WHITE);
        minustiramisu.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        minustiramisu.setBackground(SystemColor.controlDkShadow);
        minustiramisu.setBounds(60, 321, 87, 23);
        minustiramisu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * @TODO Removing rows: Tiramisu
            	 */
                // Add here the calling of the function whenever the "-" button is pressed.
            	for (int i = 0; i < 1; i++) {
                    if (((String)table.getValueAt(i, 0)).equals("Tiramisu")) {
                    	table.setRowSelectionAllowed(true);
                    	table.setRowSelectionInterval(i, 0);
                    	System.out.println("hello");
                    	//table.getSelectedRow();
                    	//table.remove(table.getSelectedRow());
                            for (int j = 0; j < 1; j++) {
                            	checkoutTable.removeRow(table.getSelectedRow());
                            }
                    }
                }
            }
        });
        dessertsP6.add(minustiramisu);

        JButton addtiramisu = new JButton("+");
        addtiramisu.setBorder(EmptyBorder);
        addtiramisu.setFocusPainted(false);
        addtiramisu.setForeground(Color.WHITE);
        addtiramisu.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
        addtiramisu.setBackground(SystemColor.controlDkShadow);
        addtiramisu.setBounds(157, 321, 87, 23);
        addtiramisu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add here the calling of the function whenever the "+" button is pressed.
            	/**
                 * @TODO Adding rows: Tiramisu
                 */
            	// Add here the calling of the function whenever the "+" button is pressed.
            	inventory_ls = driver.viewItemInventory(); 
            	String[] name_item = new String[500];
            	double[] price = new double[500]; 
            	
            	for (int i = 0; i < 1; i++) {
            		String temp1; 
            		double temp2; 
            		temp1 = driver.get_names("Tiramisu");  
            		name_item[i] = temp1; 
            		
            		
            		temp2 = driver.get_price("Tiramisu"); 
            		price[i] = temp2;
            		checkoutTable.insertRow(checkoutTable.getRowCount(), new Object[] {temp1, temp2});
            	 }
            	
            	item = new Product(); 
        		item = driver.fetch_product_item("Tiramisu");
	        		for (int j = 0; j < 1; j++) {
	        			items_selected[j].setProduct_code(item.getProduct_code());
	        			items_selected[j].setProduct_name(item.getProduct_name());
	        			items_selected[j].setProduct_price(item.getProduct_price());
	        			System.out.println("\t\t >>> SELECTED");
	        			System.out.println("\t\t\t Selected Name: " + items_selected[j].getProduct_name());
	        			System.out.println("\t\t\t Selected Price: " + items_selected[j].getProduct_price());
	        			System.out.println("\t\t\t Selected Product Code: " + items_selected[j].getProduct_code());
	        			
	        			items_ls_sel.add(item);
	        			System.out.println("-- Size: " + items_ls_sel.size());
	        		}
            	
            }
        });
        dessertsP6.add(addtiramisu);

        JLabel coffeeJellyText = new JLabel("Coffee Jelly - PHP 119");
        coffeeJellyText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        coffeeJellyText.setBounds(75, 284, 160, 26);

        JLabel halohaloText = new JLabel("Halo-Halo - PHP 99");
        halohaloText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        halohaloText.setBounds(92, 284, 131, 26);

        JLabel lecheflanText = new JLabel("Leche flan - PHP 229");
        lecheflanText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        lecheflanText.setBounds(92, 284, 180, 26);

        JLabel panettoneText = new JLabel("Panettone - PHP 169");
        panettoneText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        panettoneText.setBounds(92, 284, 150, 26);

        JLabel pannaCottaText = new JLabel("Panna Cotta - PHP 229");
        pannaCottaText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        pannaCottaText.setBounds(92, 284, 180, 26);

        JLabel tiramisuText = new JLabel("Tiramisu - PHP 209");
        tiramisuText.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
        tiramisuText.setBounds(87, 284, 180, 26);

        dessertsP1.add(coffeeJellyText);
        dessertsP2.add(halohaloText);
        dessertsP3.add(lecheflanText);
        dessertsP4.add(panettoneText);
        dessertsP5.add(pannaCottaText);
        dessertsP6.add(tiramisuText);

        dessertsP1.setLayout(null);
        dessertsP1.add(coffeeJelly);
        dessertsP2.setLayout(null);
        dessertsP2.add(halohalo);
        dessertsP3.setLayout(null);
        dessertsP3.add(lecheflan);
        dessertsP4.setLayout(null);
        dessertsP4.add(panettone);
        dessertsP5.setLayout(null);
        dessertsP5.add(pannaCotta);
        dessertsP6.setLayout(null);
        dessertsP6.add(tiramisu);

        // sets background color so that they blend with their respective
        dessertsP1.setBackground(dessertsColor);
        dessertsP2.setBackground(dessertsColor);
        dessertsP3.setBackground(dessertsColor);
        dessertsP4.setBackground(dessertsColor);
        dessertsP5.setBackground(dessertsColor);
        dessertsP6.setBackground(dessertsColor);

        // Adding each sub panel into the appetizer panel.

        JLabel coffeeJellyBGColor = new JLabel();
        coffeeJellyBGColor.setBackground(Color.WHITE);
        coffeeJellyBGColor.setBounds(20, 23, 264, 334);
        coffeeJellyBGColor.setOpaque(true);

        JLabel halohaloBGColor = new JLabel();
        halohaloBGColor.setBackground(Color.WHITE);
        halohaloBGColor.setBounds(25, 23, 264, 334);
        halohaloBGColor.setOpaque(true);

        JLabel lecheflanBGColor = new JLabel();
        lecheflanBGColor.setBackground(Color.WHITE);
        lecheflanBGColor.setBounds(25, 23, 264, 334);
        lecheflanBGColor.setOpaque(true);

        JLabel panettoneBGColor = new JLabel();
        panettoneBGColor.setBackground(Color.WHITE);
        panettoneBGColor.setBounds(25, 23, 264, 334);
        panettoneBGColor.setOpaque(true);

        JLabel pannaCottaBGColor = new JLabel();
        pannaCottaBGColor.setBackground(Color.WHITE);
        pannaCottaBGColor.setBounds(25, 23, 264, 334);
        pannaCottaBGColor.setOpaque(true);

        JLabel tiramisuBGColor = new JLabel();
        tiramisuBGColor.setBackground(Color.WHITE);
        tiramisuBGColor.setBounds(25, 23, 264, 334);
        tiramisuBGColor.setOpaque(true);

        dessertsP1.add(coffeeJellyBGColor);
        dessertsP2.add(halohaloBGColor);
        dessertsP3.add(lecheflanBGColor);
        dessertsP4.add(panettoneBGColor);
        dessertsP5.add(pannaCottaBGColor);
        dessertsP6.add(tiramisuBGColor);

        dessertsPanel.add(dessertsP1);
        dessertsPanel.add(dessertsP2);
        dessertsPanel.add(dessertsP3);
        dessertsPanel.add(dessertsP4);
        dessertsPanel.add(dessertsP5);
        dessertsPanel.add(dessertsP6);

        // Creates Sidebar w/ border
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new GridLayout(10, 0, 10, 15));
        sideBar.setBackground(sideBarColor);
        sideBar.setBorder(EmptyBorder);

        // Sidebar Button: Appetizers
        JButton appetizers = new JButton("Appetizers");
        appetizers.setPreferredSize(new Dimension(159, 30));
        appetizers.setBackground(appetizerColor);
        appetizers.setFont(new Font("Frank Gothic Medium", Font.BOLD, 14));
        appetizers.setForeground(Color.WHITE);
        appetizers.setBorder(EmptyBorder);
        appetizers.setAlignmentX(Component.LEFT_ALIGNMENT);
        appetizers.setFocusPainted(false);
        appetizers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(cardPanel, "Appetizers");
            }
        });
        sideBar.add(appetizers);

        // Sidebar Button: Main Dish
        JButton mainDish = new JButton("Main Dish");
        mainDish.setPreferredSize(new Dimension(160, 30));
        mainDish.setBackground(mainDishColor);
        mainDish.setFont(new Font("Frank Gothic Medium", Font.BOLD, 14));
        mainDish.setForeground(Color.WHITE);
        mainDish.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainDish.setBorder(EmptyBorder);
        mainDish.setFocusPainted(false);
        mainDish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(cardPanel, "Main Dish");
            }
        });
        sideBar.add(mainDish);

        // Sidebar Button: Sides
        JButton sides = new JButton("Sides");
        sides.setPreferredSize(new Dimension(160, 30));
        sides.setBackground(sidesColor);
        sides.setFont(new Font("Frank Gothic Medium", Font.BOLD, 14));
        sides.setForeground(Color.WHITE);
        sides.setAlignmentX(Component.LEFT_ALIGNMENT);
        sides.setBorder(EmptyBorder);
        sides.setFocusPainted(false);
        sides.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(cardPanel, "Sides");
            }
        });
        sideBar.add(sides);

        // Sidebar Button: Desserts
        JButton desserts = new JButton("Desserts");
        desserts.setPreferredSize(new Dimension(160, 30));
        desserts.setBackground(dessertsColor);
        desserts.setFont(new Font("Frank Gothic Medium", Font.BOLD, 14));
        desserts.setForeground(Color.WHITE);
        desserts.setAlignmentX(Component.LEFT_ALIGNMENT);
        desserts.setBorder(EmptyBorder);
        desserts.setFocusPainted(false);
        desserts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(cardPanel, "Desserts");
            }
        });
        sideBar.add(desserts);

        p.add(cardPanel, BorderLayout.CENTER);

        // Set the menu bar and add the label to the content pane.
        frame.getContentPane().add(sideBar, BorderLayout.LINE_START);
        frame.getContentPane().add(p, BorderLayout.CENTER);

        // Display the window.
        frame.getContentPane().setPreferredSize(new Dimension(1440, 800));
        frame.pack();
        frame.setVisible(true);
    }

	public static String getBaseURI() {
		return baseURI;
	}

	public static void setBaseURI(String baseURI) {
		Main.baseURI = baseURI;
	}
}