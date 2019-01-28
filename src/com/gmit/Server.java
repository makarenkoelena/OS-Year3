package com.gmit;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final Set<User> userList = new HashSet<>();
    private static final Set<Bug> bugList = new HashSet<>();
    private static int bugCounter = 0;

    public static void main(String[] args) {

        ServerSocket listener;

        try {
            listener = new ServerSocket(2006, 10);

            while (true) {
                System.out.println("Main thread listening for incoming new connections");
                Socket newconnection = listener.accept();

                System.out.println("New connection received and spanning a thread");

                ConnectionHandler ch = new ConnectionHandler(newconnection);
                Thread thread = new Thread(ch);
                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Socket not opened");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // all methods here have to be synchronised!!!

    // compares IDs (hashcodes) -> use contains()
    // do i need to compare ids if set is a collection of unique element???
    public synchronized static boolean contains(User user) {
        return userList.contains(user);
    }

    //  compares emails
    public synchronized static boolean hasEmail(User user) {
        for (User u : userList) {
            if (u.getEmail().equalsIgnoreCase(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public synchronized static void add(User user) {
        userList.add(user);
    }

    public synchronized static boolean contains(Bug bug) {
        return bugList.contains(bug);
    }

    public synchronized static void add(Bug bug) throws IOException {
        //bug.setId(bugCounter++);
        bugList.add(bug);
        System.out.println("BugList " + bugList);

        String addToFile = "Added to file: ";
        FileWriter fileWriter = new FileWriter("./Files/BugReport.txt", true);
        fileWriter.append(bugList.toString());
        fileWriter.close();
    }

    public synchronized static void assignBug(Bug bug) {
        String bugToReassign = "";
        Iterator<Bug> iterator = bugList.iterator();
        while (iterator.hasNext()) {
            if (bugToReassign == iterator.next().getName()) {//bug found
                //bugList.getClass().setStatus() = Identifiers.STATUS_ASSIGNED;
            }

        }
    }

    public synchronized static String viewAllBugs() {
        String response = "";
        Iterator<Bug> iterator = bugList.iterator();
        StringBuilder sb = new StringBuilder("List of bugs:");
        //for List
//        for (int i = 0; i < bugs.size(); i++) {
//            bugList.get(i);
//        }

        for(Bug b : bugList) {
            sb.append("id: " + b.getId());
            sb.append("Name: " + b.getName());
            sb.append("\n");
        }

//        bugList.stream()
//                .map(Bug::)

       // Iterator<Bug> iterator = bugList.iterator();
//        while (iterator.hasNext()) {
//            response = sb.append("Id №: ")
//                    .append(iterator.next().getId())
//                    .append("Name: ")
//                    .append(iterator.next().getName())
//                    .append("\n").toString();
//
//            System.out.println();
//        }
        return sb.toString();
    }

    public synchronized static String viewUnassignedBugs() {
        String response = "";
        StringBuilder sb = new StringBuilder("List of unassigned bugs:");

        Iterator<Bug> iterator = bugList.iterator();

        while (iterator.hasNext()) {
            //if (bugList.getClass().getStatus() == 1) {
                response = sb.append(iterator.next()).toString();
            }
//            else
//                response = "There are no unassigned bugs";
//        }
        return response;
    }
}







