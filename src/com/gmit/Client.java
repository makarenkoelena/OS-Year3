package com.gmit;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private static final Integer BUFFER_SIZE = 100;
    private Socket connection;
    // private String message;
    private Scanner console;
    private String ipaddress;
    private int portaddress;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    //Set<User> users = new HashSet<>();

    public Client() {
        console = new Scanner(System.in);
//        System.out.println("Enter the IP Address of the server");
//        ipaddress = console.nextLine();
//
//        System.out.println("Enter the TCP Port");
//        portaddress  = console.nextInt();
        ipaddress = "localhost";
        //ipaddress = "10.12.13.83";
        portaddress = 2006;
        //40.87.137.165
    }


    public void clientapp() {
        try {
            connection = new Socket(ipaddress, portaddress);

            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            System.out.println("Client Side ready to communicate");

            // Client App.
            String message = "";
            do {
                System.out.println("Press 1 to register \n 2 to login\n or -1 to exit:\n");
                message = console.next();

                switch (message) {
                    case "1":
                        register();
                        break;
                    case "2":
                        login();
                        break;
                    default:
                        System.out.println("Invalid option!\n");
                }

            } while (!message.equalsIgnoreCase(Identifiers.EXIT));

            sendMessage(message);
            receiveMessage();

            out.close();
            in.close();
            connection.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void login() {
        String response = "";
        System.out.println("you are in login method");

        do {
            System.out.println("Enter id: ");
            String id = console.next();
            System.out.println("Enter email: ");
            String email = console.next();

            sendMessage(Identifiers.LOGINPAGE + " " + id + " " + email);
            response = receiveMessage();


        } while (response.trim().equalsIgnoreCase(Identifiers.NO));


// here we are logged in
        if (response.trim().equalsIgnoreCase(Identifiers.YES)) {
            enterSystem();
        }

    }

    void enterSystem() {
        String choice = "";

        do {
            System.out.println("Press 1 to add a bug \n 2 to assign a bug\n 3 to view all bug record not assigned to any developer\n 4 to view all the bugs\n or -1 to exit:\n");
            choice = console.next();

            switch (choice) {
                case "1":
                    addBug();
                    break;
                case "2":
                    assignBug();
                    break;
                case "3":
                    viewUnassignedBugs();
                    break;
                case "4":
                    viewBugs();
                    break;
                default:
                    System.out.println("Invalid option!\n");
            }//switch
        } while (choice.equalsIgnoreCase(Identifiers.EXIT));
    }

    private void viewBugs() {
        String response = "";
        do {
            sendMessage(Identifiers.VIEW_ALL_BUGS);
            receiveMessage();
            response = receiveMessage();

        } while (response.equalsIgnoreCase(Identifiers.NO));

        if (response.equalsIgnoreCase(Identifiers.YES)) {
            enterSystem();
        }
    }

    private void viewUnassignedBugs() {
        String response = "";
        do {
            sendMessage(Identifiers.VIEW_UNASSIGNED_BUGS);
            receiveMessage();
            response = receiveMessage();

        } while (response.equalsIgnoreCase(Identifiers.NO));

        if (response.equalsIgnoreCase(Identifiers.YES)) {
            enterSystem();
        }
    }

    private void assignBug() {
        System.out.println("Please select bug to reassign:");
        //System.out.println("Please select a User to assign to:\n");
        String response = "";
        do {
            sendMessage(Identifiers.ASSIGN_BUG);
            receiveMessage();
            response = receiveMessage();

        } while (response.equalsIgnoreCase(Identifiers.NO));

        if (response.equalsIgnoreCase(Identifiers.YES)) {
            enterSystem();
        }
    }

    private void addBug() {
        String response = "";
        do {
            System.out.println("Add bug's id: ");
            String id = console.next();

            System.out.println("Add bug's name: ");
            String name = console.next();

            //

            sendMessage(Identifiers.ADD_BUG + " " + id + " " + name);
            response = receiveMessage();
        } while (response.equalsIgnoreCase(Identifiers.NO));

        if (response.equalsIgnoreCase(Identifiers.YES)) {
            enterSystem();
        }

    }

    private void register() {
        System.out.println("you are in register method");
        String response = "";

        do {//get user details
            System.out.println("Enter name: ");
            String name = console.next();

            System.out.println("Enter id: ");
            String id = console.next();

            System.out.println("Enter email: ");
            String email = console.next();

            System.out.println("Enter department: ");
            String department = console.next();

            sendMessage(Identifiers.REGISTER_VALIDATOR + " " + name + " " + id + " " + email + " " + department);
            response = receiveMessage();
        } while (response.trim().equalsIgnoreCase(Identifiers.NO));
    }

    private void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
            System.out.println("client says>" + message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String receiveMessage() {
        String message;
        System.out.println("Waiting...");
        try {
            message = (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            message = "Message not received";
        }
        System.out.println("Server says> " + message);

        return message;
    }

    public static void main(String[] args) {
        new Client().clientapp();
    }
}//class

