package controller;

import database.TableFactory;
import controller.Services.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Worker implements Runnable {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            sois = new ObjectInputStream(clientSocket.getInputStream());
            soos = new ObjectOutputStream(clientSocket.getOutputStream());
            generateDB();
            while (true) {
                String choice = sois.readObject().toString();
                switch (choice) {

                    case "exit": {
                        soos.writeObject("OK");
                        soos.close();
                        sois.close();
                        System.out.println("Client " + clientSocket.getInetAddress().toString() + "disconnected.");
                    }
                    break;
                    case"Reports":{
                        String message = (String) sois.readObject();
                        ReportsService reportsService=new ReportsService(message,sois,soos,clientSocket);
                    }
                    break;
                    case"Diagrams":{
                        String message = (String) sois.readObject();
                        DiagramsService diagramsService=new DiagramsService(message,sois,soos,clientSocket);
                    }
                    break;
                    case"Method":{
                        String message = (String) sois.readObject();
                        MethodService methodService=new MethodService(message,sois,soos,clientSocket);
                    }
                    break;
                    case"Users":{
                        String message = (String) sois.readObject();
                        UsersService usersService=new UsersService(message,sois,soos,clientSocket);
                    }
                    break;
                    case"AnalogCompanies":{
                        String message = (String) sois.readObject();
                        AnalogCompaniesService analogCompaniesService=new AnalogCompaniesService (message,sois,soos,clientSocket);
                    }
                    break;
                    case"Companies":{
                        String message = (String) sois.readObject();
                        CompaniesService companiesService=new CompaniesService(message,sois,soos,clientSocket);
                    }
                    break;

                }

            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void generateDB(){
          TableFactory factory=new TableFactory();
          factory.getCountries().create();
          factory.getUsers().create();
          factory.getCompanies().create();
          factory.getInfo().create();
          factory.getParameters().create();
          factory.getMultipliers().create();
          factory.getDeposits().create();
    }
}

