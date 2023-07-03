package Clientdesktop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class trekrecordUI extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;

    public trekrecordUI() {
        setTitle("trekrecordUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();

        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Hari");
        tableModel.addColumn("Tanggal");
        tableModel.addColumn("Jam");
        tableModel.addColumn("History Kunjungan");
        tableModel.addColumn("Lokasi");

        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            dispose(); // Close the trekrecordUI window
        });

        // Fetch booking data from the server
        try {
            URL url = new URL("http://192.168.56.1:7000/trekrecord");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                String response = responseBuilder.toString();
                System.out.println("Server Response: " + response);

                // Parse the JSON response
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject booking = dataArray.getJSONObject(i);
                    var historykunjungan = booking.getInt("historykunjungan");
                    String username = booking.getString("username");
                    String jenislayanan = booking.getString("jenislayanan");
                    var lokasi = booking.getInt("lokasi");

                    // Add the data to the table model
                    tableModel.addRow(new Object[]{historykunjungan, username, jenislayanan, lokasi, historykunjungan});
                }
            } else {
                System.out.println("Failed to connect to the server. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception ex) {
            System.out.println("Error occurred while connecting to the server.");
            ex.printStackTrace();
        }

        // Create the JTable and set the model
        table = new JTable(tableModel);

        // Create a scroll pane and add the table to it
        scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new trekrecordUI().setVisible(true);
            }
        });
    }
}