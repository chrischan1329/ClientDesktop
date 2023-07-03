package Clientdesktop;

import javax.swing.*;

import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookingListUI extends JFrame {

    private JTextField idField;
    private JTextField usernameField;
    private JTextField jenis_mobilField;
    private JTextField jenis_layananField;;
    private JTextField lokasiField;
    private JTextField tanggalField;
    private JTextField waktuField;
    public BookingListUI() {
        setTitle("Booking List");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));
        JPanel buttonPanel = new JPanel();

        JLabel idLabel = new JLabel("ID Booking:");
        idField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel jenismobilLabel = new JLabel("Jenis Mobil:");
        jenis_mobilField = new JTextField();
        JLabel jenislayananLabel = new JLabel("Jenis Layanan:");
        jenis_layananField = new JTextField();
        JLabel tanggalLabel = new JLabel("Tanggal:");
        tanggalField = new JTextField();
        JLabel waktuLabel = new JLabel("Waktu:");
        waktuField = new JTextField();
        JLabel lokasiLabel = new JLabel("Lokasi:");
        lokasiField = new JTextField();
        JButton bookButton = new JButton("Booking");
        JButton backButton = new JButton("Back");

        panel.add(idLabel);
        panel.add(idField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(jenismobilLabel);
        panel.add(jenis_mobilField);
        panel.add(jenislayananLabel);
        panel.add(jenis_layananField);
        panel.add(tanggalLabel);
        panel.add(tanggalField);
        panel.add(waktuLabel);
        panel.add(waktuField);
        panel.add(lokasiLabel);
        panel.add(lokasiField);
        panel.add(new JLabel());
        buttonPanel.add(backButton);
        buttonPanel.add(bookButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String idbooking = idField.getText();
                String jenismobil = jenis_mobilField.getText();
                String jenislayanan = jenis_layananField.getText();
                String tanggal = tanggalField.getText();
                String waktu = waktuField.getText();
                String lokasi = lokasiField.getText();
                if (username.isEmpty() || jenismobil.isEmpty() || jenislayanan.isEmpty() || tanggal.isEmpty() || waktu.isEmpty() || lokasi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
                else {
                    try {
                        // Create the JSON object for booking data
                        JSONObject bookingData = new JSONObject();
                        bookingData.put("idbooking", idbooking);
                        bookingData.put("username", username);
                        bookingData.put("jenis_mobil", jenismobil);
                        bookingData.put("jenis_layanan", jenislayanan);
                        bookingData.put("tanggal", tanggal);
                        bookingData.put("waktu", waktu);
                        bookingData.put("lokasi", lokasi);

                        // Create the URL for booking endpoint
                        URL url = new URL("http://192.168.146.73:7000/booking");

                        // Create the HttpURLConnection
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoOutput(true);

                        // Send the booking data
                        OutputStream os = connection.getOutputStream();
                        os.write(bookingData.toString().getBytes());
                        os.flush();

                        // Check the response code
                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            StringBuilder responseBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                responseBuilder.append(line);
                            }
                            String response = responseBuilder.toString();
                            JOptionPane.showMessageDialog(null, "Berhasil booking! Booking ID: " + response);
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal booking. Response code: " + responseCode);
                        }

                        connection.disconnect();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error occurred while connecting to the JSON server.");
                        ex.printStackTrace();
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            dispose(); // Close the BookingUI window
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BookingListUI().setVisible(true);
            }
        });
    }
}