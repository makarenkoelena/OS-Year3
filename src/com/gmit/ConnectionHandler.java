package com.gmit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

public class ConnectionHandler implements Runnable {
    Socket individualconnection;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ConnectionHandler(Socket s) {
        individualconnection = s;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(individualconnection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(individualconnection.getInputStream());
            System.out.println("Connection from IP address " + individualconnection.getInetAddress());

            //Commence the conversation with the client......
            //if response = -1 return/exit; otherwise split the response, put it into string array,
            // pass it into clientHandler() which "deals with  responses"
            // read the first bit of response(array at index 0) and go to appropriate method
            String response = "";
            while (true) {
                response = receiveMessage();
                if (response.trim().equalsIgnoreCase(Identifiers.EXIT)) {
                    sendMessage("Connection closed");
                    return;
                }
                String[] resArr = response.split(" ");
                clientHandler(resArr);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                individualconnection.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }//end of class

    private void clientHandler(String[] response) throws IOException {
        switch (response[0]) {
            case Identifiers.REGISTER_VALIDATOR:
                register(response);
                break;
            case Identifiers.LOGINPAGE:
                login(response);
                break;
            case Identifiers.ADD_BUG:
                addBug(response);
                break;
            case Identifiers.ASSIGN_BUG:
                assignBug(response);
                break;
            case Identifiers.VIEW_ALL_BUGS:
                viewBug();
                break;
            case Identifiers.VIEW_UNASSIGNED_BUGS:
                viewUnassignedBugs(response);
                break;
            case Identifiers.UPDATE_BUG_RECORD:
                updateBugRecord(response);
                break;
        }
    }

    private void updateBugRecord(String[] response) {
    }
    private void viewUnassignedBugs(String[] response) {
        String answer = Server.viewUnassignedBugs();
        sendMessage(answer);
        sendMessage(Identifiers.YES);
    }
    private void viewBug() {
        String answer = Server.viewAllBugs();
        sendMessage(answer);
        sendMessage(Identifiers.YES);
    }
    private void assignBug(String[] response) {
        int id = Integer.parseInt(response[1]);
        int status = Identifiers.STATUS_OPEN;

        Bug bug = new Bug(id, status);
        if (Server.contains(bug)) { // id matches
            sendMessage(Identifiers.YES);
            Server.assignBug(bug);
        }
        else sendMessage(Identifiers.NO);
    }
    private void register(String[] response) {
        String name = response[1];
        int id = Integer.parseInt(response[2]);
        String email = response[3];
        String department = response[4];

        User user = new User(name, id, email, department);

        if (Server.contains(user)) { // id is NOT unique
            sendMessage(Identifiers.NO);
            return;
        }

        if (Server.hasEmail(user)) {//email is NOT unique
            sendMessage(Identifiers.NO);
            return;
        }
        // else part: id and email is unique - register the user, pass an OK message
        Server.add(user);
        sendMessage(Identifiers.YES);
    }//register()
    // we are in addBug() as response array at index 0 is ADD_BUG
    // read the rest of the response, create an object with the parameters passed in response
    // call add(), send an OK response
    private void addBug(String[] response) throws IOException {
        int id = Integer.parseInt(response[1]);
        String name = response[2];

        Bug bug = new Bug(id, name);

        //no need to check id as it is assigned not by the user -> will be always unique??
        if (Server.contains(bug)) { // id matches
            sendMessage(Identifiers.NO);
            return;
        }
        Server.add(bug);

        sendMessage(Identifiers.YES);

//        this.b.add(new Bug(n,y,m,d,p,des,s));
//        server.addBug(n, y, m, d, p, des, s);
       // Timestamp ts = new Timestamp(date.getTime());
       // System.out.println("Current date and time is " + ts);

      //  System.out.println("each bug " + bug.getId() + bug.getName());
    }
    private void login(String[] response) {
        int id = Integer.parseInt(response[1]);
        String email = response[2];
        User user = new User(id, email);

        // there is such a user, send an ok response
        if (Server.contains(user) && Server.hasEmail(user)) {
            sendMessage(Identifiers.YES);
            return;
        }

        //else part: there is no such user, send error message
        sendMessage(Identifiers.NO);
    }
    private void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private String receiveMessage() {
        String message = "";
        try {
            message = (String) in.readObject();
            System.out.println("Client received: " + message);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            message = "Message not received";
        }
        return message;
    }
}//end of class

