package com.tinpatiti.automation.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

public class Sheets {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/Credentials.json";
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = Sheets.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    public static int getLastSerialNumber(com.google.api.services.sheets.v4.Sheets service, String spreadsheetId, String range) throws IOException {
        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();
        int numRows = result.getValues() != null ? result.getValues().size() : 0;
        System.out.printf("%d rows retrieved.", numRows);
        return  numRows;
    }

    public static int getLastRowNumber(com.google.api.services.sheets.v4.Sheets service, String spreadsheetId, String range) throws IOException {
        int lastFilledRow = getLastSerialNumber(service, spreadsheetId, range);
        return lastFilledRow+3;
    }


    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public void updateSheet() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        Hashtable<String, Integer> sheet_columns = new Hashtable<String, Integer>();

        File f = new File("Results.txt");
        Scanner reader = new Scanner(f);
        String roundID = reader.nextLine();
        String date = reader.nextLine();
        String time = reader.nextLine();
        String winner = reader.nextLine();
        String cardsA1 = reader.nextLine();
        String cardsA2 = reader.nextLine();
        String cardsA3 = reader.nextLine();
        String cardsB1 = reader.nextLine();
        String cardsB2 = reader.nextLine();
        String cardsB3 = reader.nextLine();
        String deckOfA = reader.nextLine();
        String deckOfB = reader.nextLine();

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1fR1cZKtUMQbpxDqnthQTkcbAdVS_tlAKrfT4KTwexjs";
        final String range = "Sheet1!A3:A30";
        com.google.api.services.sheets.v4.Sheets service = new com.google.api.services.sheets.v4.Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        //int lastRowNumber = getLastRowNumber(service, spreadsheetId, range); // returns the last unfilled row...
        int lastserialNumber = getLastSerialNumber(service, spreadsheetId, range); //returns the last serial number on the sheet...
        //System.out.println("last row:"+ lastRowNumber);
        System.out.println("Serial Number:"+lastserialNumber);

        List<List<Object>> values = Arrays.asList(
                Arrays.asList(lastserialNumber+1,date, time, roundID, cardsA1, cardsA2, cardsA3, cardsB1, cardsB2, cardsB3, winner, deckOfA, deckOfB)
        );
        ValueRange body = new ValueRange()
                .setValues(values);
        AppendValuesResponse result =
                service.spreadsheets().values().append(spreadsheetId, range, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
        System.out.printf("%d cells appended.", result.getUpdates().getUpdatedCells());
    }
}