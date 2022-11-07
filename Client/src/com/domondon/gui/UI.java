/*
    Document   : UI.java
    Package	   : Client.com.domondon.gui;
    Created on : July 25, 2022
    Authors    : 
    			Philip Domondon
    			Ron Relayo
    			Cloyd Secuya
    Description:
    	This is the accessible GUI component and window of the entire Application.
*/

// PACKAGE SECTION
package com.domondon.gui;

// IMPORT SECTION
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Toolkit;

import java.awt.color.*;
import java.awt.Font;
import javax.swing.JTextField;

// Proxy.Driver.java
import com.db.driver.Driver;

import oauth.AuthenticationService;
import resource.User;


public class UI {

	private JFrame frame;
	private JTextField usernameTextfield;
	private JTextField passwordTextfield;
	
	Main newMain;
	
	Driver driver = new Driver();
	User user; 
	private boolean GRANT_ACCESS;
	private static String baseURI = "http://localhost:8080/WebServices/rest/products";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// creating login frame
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 468, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// setting username place holder
		usernameTextfield = new JTextField();
		usernameTextfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (usernameTextfield.getText().equals("username")) {
					usernameTextfield.setText("");
					usernameTextfield.setForeground(new Color(0, 0, 0));
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (usernameTextfield.getText().equals("")) {
					usernameTextfield.setText("username");
					usernameTextfield.setForeground(new Color(153, 153, 153));
				}
			}

		});
		usernameTextfield.setForeground(Color.LIGHT_GRAY);
		usernameTextfield.setBounds(226, 116, 194, 20);
		frame.getContentPane().add(usernameTextfield);
		usernameTextfield.setColumns(10);

		// setting password place holder
		passwordTextfield = new JTextField();
		passwordTextfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (passwordTextfield.getText().equals("password")) {
					passwordTextfield.setText("");
					passwordTextfield.setForeground(new Color(0, 0, 0));
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (passwordTextfield.getText().equals("")) {
					passwordTextfield.setText("password");
					passwordTextfield.setForeground(new Color(153, 153, 153));
				}
			}

		});

		passwordTextfield.setForeground(Color.BLACK);
		passwordTextfield.setColumns(10);
		passwordTextfield.setBounds(226, 153, 194, 20);
		frame.getContentPane().add(passwordTextfield);

		JLabel loginLabel = new JLabel("Login to Continue");
		loginLabel.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 18));
		loginLabel.setBounds(226, 85, 172, 20);
		frame.getContentPane().add(loginLabel);

		// login button
		JButton loginButton = new JButton("Log In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AuthenticationService auth;
				auth = new AuthenticationService(usernameTextfield.getText(), passwordTextfield.getText());
				
				GRANT_ACCESS = auth.checkCredsValidity(); 
				System.out.println(">>>>>>>>> AUTH STATUS: " + GRANT_ACCESS);
				
				if(GRANT_ACCESS) {
					System.out.println("OPENING APPLICATION!");
					try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
					newMain = new Main();
					newMain.mainPage();
				}
				
				else {
					
				}
				
				 frame.dispose();
			}
		});

		loginButton.setForeground(Color.WHITE);
		loginButton.setBorder(null);
		loginButton.setFocusPainted(false);
		loginButton.setBackground(new Color(105, 105, 105));
		loginButton.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
		loginButton.setBounds(226, 184, 194, 23);
		frame.getContentPane().add(loginButton);

		// burger background
		JLabel burgerBG = new JLabel("");
		Image logBurger = new ImageIcon(this.getClass().getResource("/loginBG.jpg")).getImage();
		burgerBG.setIcon(new ImageIcon(logBurger));
		burgerBG.setBounds(-28, 0, 234, 386);
		frame.getContentPane().add(burgerBG);

		JLabel label = new JLabel("New label");
		label.setBounds(99, 291, 46, 14);
		frame.getContentPane().add(label);

		// partition between login and sign up buttons
		JLabel lblNewLabel = new JLabel("----------------------or----------------------");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(226, 218, 194, 14);
		frame.getContentPane().add(lblNewLabel);

		// THIS IS WHERE USER CREATION JFRAME OPENS
		// Sign Up button
		JButton signUpButton = new JButton("Sign Up");
		// Sign up event or registration 
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// CREATING USER CREATION JFRAME
				JFrame userCreation = new JFrame();
				userCreation.setBounds(100, 100, 468, 425);
				userCreation.getContentPane().setBackground(Color.WHITE);
				userCreation.getContentPane().setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
				userCreation.getContentPane().setLayout(null);

				// SETTING FRIES BACKGROUND
				JLabel friesBG = new JLabel("");
				Image fries = new ImageIcon(this.getClass().getResource("/fries.jpg")).getImage();
				friesBG.setIcon(new ImageIcon(fries));
				friesBG.setBounds(-516, 0, 722, 386);
				userCreation.getContentPane().add(friesBG);

				// YOUR DETAILS LABEL
				JLabel yourDetailsLabel = new JLabel("Your details");
				yourDetailsLabel.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 18));
				yourDetailsLabel.setBounds(216, 63, 106, 21);
				userCreation.getContentPane().add(yourDetailsLabel);

				// setting first name place holder
				JTextField firstNameTextfield = new JTextField();
				firstNameTextfield.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (firstNameTextfield.getText().equals("First Name")) {
							firstNameTextfield.setText("");
							firstNameTextfield.setForeground(new Color(0, 0, 0));
						}
					}

					public void focusLost(FocusEvent arg0) {
						if (firstNameTextfield.getText().equals("")) {
							firstNameTextfield.setText("First Name");
							firstNameTextfield.setForeground(new Color(153, 153, 153));
						}
					}

				});
				firstNameTextfield.setForeground(Color.LIGHT_GRAY);
				firstNameTextfield.setColumns(10);
				firstNameTextfield.setBounds(216, 95, 106, 20);
				userCreation.getContentPane().add(firstNameTextfield);

				// setting last name placeholder
				JTextField lastNameTextfield = new JTextField();
				lastNameTextfield.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (lastNameTextfield.getText().equals("Last Name")) {
							lastNameTextfield.setText("");
							lastNameTextfield.setForeground(new Color(0, 0, 0));
						}
					}

					public void focusLost(FocusEvent arg0) {
						if (lastNameTextfield.getText().equals("")) {
							lastNameTextfield.setText("Last Name");
							lastNameTextfield.setForeground(new Color(153, 153, 153));
						}
					}
				});
				lastNameTextfield.setForeground(Color.LIGHT_GRAY);
				lastNameTextfield.setColumns(10);
				lastNameTextfield.setBounds(332, 95, 106, 20);
				userCreation.getContentPane().add(lastNameTextfield);

				// setting email placeholder
				JTextField emailTextfield = new JTextField();
				emailTextfield.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (emailTextfield.getText().equals("Email")) {
							emailTextfield.setText("");
							emailTextfield.setForeground(new Color(0, 0, 0));
						}
					}

					public void focusLost(FocusEvent arg0) {
						if (emailTextfield.getText().equals("")) {
							emailTextfield.setText("Email");
							emailTextfield.setForeground(new Color(153, 153, 153));
						}
					}
				});
				emailTextfield.setForeground(Color.LIGHT_GRAY);
				emailTextfield.setColumns(10);
				emailTextfield.setBounds(216, 126, 222, 20);
				userCreation.getContentPane().add(emailTextfield);

				// USERNAME AND PASSWORD LABEL
				JLabel lblUsernamePassword = new JLabel("Username & Password");
				lblUsernamePassword.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 16));
				lblUsernamePassword.setBounds(216, 157, 187, 21);
				userCreation.getContentPane().add(lblUsernamePassword);

				// setting username place holder
				JTextField userInput = new JTextField();
				userInput.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (userInput.getText().equals("username")) {
							userInput.setText("");
							userInput.setForeground(new Color(0, 0, 0));
						}
					}

					public void focusLost(FocusEvent arg0) {
						if (userInput.getText().equals("")) {
							userInput.setText("username");
							userInput.setForeground(new Color(153, 153, 153));
						}
					}
				});
				
				userInput.setForeground(Color.LIGHT_GRAY);
				userInput.setColumns(10);
				userInput.setBounds(216, 189, 222, 20);
				userCreation.getContentPane().add(userInput);

				// setting password placeholder
				passwordTextfield = new JTextField();
				passwordTextfield.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (passwordTextfield.getText().equals("password")) {
							passwordTextfield.setText("");
							passwordTextfield.setForeground(new Color(0, 0, 0));
						}
					}

					public void focusLost(FocusEvent arg0) {
						if (passwordTextfield.getText().equals("")) {
							passwordTextfield.setText("password");
							passwordTextfield.setForeground(new Color(153, 153, 153));
						}
					}
				});
				passwordTextfield.setForeground(Color.LIGHT_GRAY);
				passwordTextfield.setColumns(10);
				passwordTextfield.setBounds(216, 220, 222, 20);
				userCreation.getContentPane().add(passwordTextfield);

				JLabel partitionLabel = new JLabel("--------------------------------------------------");
				partitionLabel.setHorizontalAlignment(SwingConstants.CENTER);
				partitionLabel.setForeground(Color.GRAY);
				partitionLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
				partitionLabel.setBounds(216, 251, 222, 14);
				userCreation.getContentPane().add(partitionLabel);

				// CREATE ACCOUNT BUTTION
				JButton btnCreateAccount = new JButton("create account");
				btnCreateAccount.setForeground(Color.WHITE);
				btnCreateAccount.setBorder(null);
				btnCreateAccount.setFocusPainted(false);
				btnCreateAccount.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
				btnCreateAccount.setBackground(SystemColor.controlDkShadow);
				btnCreateAccount.setBounds(216, 276, 222, 23);	
				btnCreateAccount.addActionListener(new ActionListener() {
					/**
					 * @TODO Create account with sign-up form
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						user = new User( userInput.getText(),
										      emailTextfield.getText(), 
										      passwordTextfield.getText(), 
										      firstNameTextfield.getText(), 
										      lastNameTextfield.getText()
										    ); 
						// Verbose and print the entries to terminal
						System.out.println("Entered Username: " + userInput.getText()); 
						System.out.println("Entered Email: " + emailTextfield.getText()); 
						System.out.println("Entered Password: " + passwordTextfield.getText()); 
						System.out.println("Entered First Name: " + firstNameTextfield.getText()); 
						System.out.println("Entered Last Name: " + lastNameTextfield.getText()); 
						
						System.out.println(">>> REGISTERING USER TO DATABASE <<< ");
						driver.user_insert_newData(user);
						
						// Create a pop-up alert after inserting to database
						try {Thread.sleep(500);} catch (InterruptedException e1) {e1.printStackTrace();}
						JOptionPane.showMessageDialog(null, "Sign up, complete!", "CONGRATULATIONS!", JOptionPane.PLAIN_MESSAGE);
					
					}
					
				});
				
				userCreation.getContentPane().add(btnCreateAccount);
				userCreation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				userCreation.setVisible(true);
				

				// back button
				JButton back = new JButton("BACK");
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						userCreation.dispose();
					}
				});
				back.setForeground(Color.WHITE);
				back.setBorder(null);
				back.setFocusPainted(false);
				back.setBackground(new Color(105, 105, 105));
				back.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
				back.setBounds(353, 352, 89, 23);
				userCreation.getContentPane().add(back);

			}
		});
		signUpButton.setForeground(Color.WHITE);
		signUpButton.setBorder(null);
		signUpButton.setFocusPainted(false);
		signUpButton.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
		signUpButton.setBackground(SystemColor.controlDkShadow);
		signUpButton.setBounds(226, 243, 194, 23);
		frame.getContentPane().add(signUpButton);
	}

	public static String getBaseURI() {
		return baseURI;
	}

	public static void setBaseURI(String baseURI) {
		UI.baseURI = baseURI;
	}

}