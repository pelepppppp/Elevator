/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author bautistaph_sd2021
 */
public class Server extends JFrame {

    private JTextField enterField;
    private JTextArea displayArea;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private int counter = 1;

    @SuppressWarnings("Convert2Lambda")
    public Server() {
        super("Server");

        enterField = new JTextField();
        enterField.setEditable(false);
        enterField.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData(e.getActionCommand());
                enterField.setText("");
            }
        }
        );
        add(enterField, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        setSize(300, 400);
        setVisible(true);
        setResizable(false);
    }

    public void runServer() {
        try {
            server = new ServerSocket(12345, 100);
            while (true) {
                try {
                    waitForConnection();
                    getStream();
                    processConnection();
                } catch (EOFException eof) {
                    displayMessage("\nServer terminated connection.");
                } finally {
                    closeConnection();
                    ++counter;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendData(String message) {
        try {
            output.writeObject("SERVER>>> " + message);
            output.flush();
            displayMessage("\nSERVER>>> " + message);
        } catch (IOException ex) {
            displayArea.append("\nError writing object.");
        }

    }

    private void waitForConnection() throws IOException {
        displayMessage("Waiting for connection.\n");
        connection = server.accept();
        displayMessage(String.format("Connection %d received from: %s",
                counter, connection.getInetAddress().getHostName()));
    }

    private void getStream() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        displayMessage("\nGot I/O streams\n");
    }

    private void processConnection() throws IOException {
        String message = "Connection successful";
        sendData(message);

        setTextFieldEditable(true);
        do {
            try {
                message = (String) input.readObject();
                displayMessage("\n" + message);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        } while (!message.equals("CLIENT>>> TERMINATE"));
    }

    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                displayArea.append(messageToDisplay);
            }
        });
    }

    private void closeConnection() {
        try {
            displayMessage("\nTerminating connection.\n");
            setTextFieldEditable(false);

            output.close();
            input.close();
            connection.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setTextFieldEditable(final boolean editable) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                enterField.setEditable(editable);
            }
        });
    }

    public static void main(String[] args) {
        Server app = new Server();

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.runServer();
    }
}
