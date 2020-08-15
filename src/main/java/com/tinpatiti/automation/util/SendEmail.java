package com.tinpatiti.automation.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SendEmail {
    public void sendEmail(){

        String command = "cmd /c python Notification.py";
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
            System.out.println("Exception occur");
        }


    }
}


