/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinpatiti.automation.util;

/**
 *
 * @author ianub
 */
import java.io.FileWriter;// Import the File class
import java.io.IOException;

public class DataFile {
    
    public void writeData(String roundID, String date, String time, String winner, String[] cardsA, String deckOfA, String[] cardsB, String deckOfB){
      try {
            //File myObj = new File("C:\\filename.txt");
            FileWriter myWriter = new FileWriter("Results.txt");
            myWriter.write(roundID+"\n");
            myWriter.write(date+"\n");
            myWriter.write(time+"\n");
            myWriter.write(winner+"\n");
            myWriter.write(cardsA[0]+"\n");
            myWriter.write(cardsA[1]+"\n");
            myWriter.write(cardsA[2]+"\n");
            myWriter.write(cardsB[0]+"\n");
            myWriter.write(cardsB[1]+"\n");
            myWriter.write(cardsB[2]+"\n");
            myWriter.write(deckOfA+"\n");
            myWriter.write(deckOfB+"\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    }
    public void writeException(String exception) throws IOException {
        FileWriter myWriter = new FileWriter("Error.txt");
        myWriter.write(exception+"\n");

    }
    }
    
    
    
    
  

