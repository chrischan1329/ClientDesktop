package Clientdesktop;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeUI extends JFrame {

    public HomeUI() {
        setTitle("Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        // Create the menu buttons
        JButton bookingButton = new JButton("Booking");  //Booking
        JButton cancelBookingButton = new JButton("Cancel Booking"); //Cancel Booking
        JButton listcustomerButton = new JButton("List Customer"); //List Customer

        // Add the buttons to the main menu frame
        add(bookingButton);
        add(cancelBookingButton);
        add(listcustomerButton);

        // Trek Record button action listener
        listcustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListcustomerUI ListcustomerUI = new ListcustomerUI();
                ListcustomerUI.setVisible(true);
            }
        });

        // Booking button action listener
        bookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the Booking button
                BookingListUI bookingUI = new BookingListUI();
                bookingUI.setVisible(true);
            }
        });

        // Cancel Booking button action listener
        cancelBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the Cancel Booking button
                CancelBookingUI cancelBooking = new CancelBookingUI();
                cancelBooking.setVisible(true);
            }
        });


        // Display the main menu frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeUI();
            }
        });
    }
}
