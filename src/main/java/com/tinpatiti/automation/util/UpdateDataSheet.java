/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinpatiti.automation.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author ianub
 */
public class UpdateDataSheet {
    public void callSheets(){
       
    String command = "cmd /c python Sheets.py";
    try{
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
          String line;
        while ((line = bri.readLine()) != null) {
            System.out.println(line);
          }
          bri.close();
          while ((line = bre.readLine()) != null) {
            System.out.println(line);
          }
          bre.close();
          p.waitFor();
          System.out.println("Done.");

          p.destroy();
    }catch (Exception e){
        System.out.println("hi");
    }
    

    }
}
