package com.reusable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.io.Files;

public class StartService implements IcalCredentialsReader{
    
    private static  String APPLICATION_NAME;
    private static  String SERVICE_ACCOUNT_EMAIL;
    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static  URL loc ;
    private static  String CALENDAR_ID;
    public static void main(String[] args) throws IOException, ParserException {
        
        Scanner scanner = new Scanner(new FileReader("C:/Users/MohanRaj/Desktop/filename.txt"));

        //  HashMap<String, String> map = new HashMap<String, String>();

          while (scanner.hasNextLine()) {
              String[] columns = scanner.nextLine().split(" ");
              credentialsMap.put(columns[0], columns[1]);
          }

          System.out.println(credentialsMap);
          
          Iterator<Map.Entry<String, String>> iterator = credentialsMap.entrySet().iterator() ;
          while(iterator.hasNext()){
              Map.Entry<String, String> val = iterator.next();
              if(val.getKey() .equals("APPLICATION_NAME")){
                  APPLICATION_NAME = val.getValue();
                  System.out.println(val.getKey() +" :: "+ APPLICATION_NAME);
              } else if(val.getKey() .equals("SERVICE_ACCOUNT_EMAIL")){
                  SERVICE_ACCOUNT_EMAIL = val.getValue();
                  System.out.println(val.getKey() +" :: "+ SERVICE_ACCOUNT_EMAIL);
              } else if(val.getKey() .equals("KEY")){
                   loc = new URL( "file:///"+ val.getValue()); 
                  System.out.println(val.getKey() +" :: "+ loc);
              } else if(val.getKey() .equals("CALENDAR_ID")){
                  CALENDAR_ID = val.getValue();
                  System.out.println(val.getKey() +" :: "+ CALENDAR_ID);
              }
            //  System.out.println(val.getKey() +" :: "+ val.getValue());
              //You can remove elements while iterating.
              //iterator.remove();
          }
          
          FileInputStream fin = new FileInputStream("C:/Users/MohanRaj/Desktop/Pepsi_Indian_Premier_League.ics");
          
          CalendarBuilder builder = new CalendarBuilder();
          
          net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);
          Event event = new Event();
          Calendar service =null;
        //  System.out.println(calendar);
          for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
              Component component = (Component) i.next();
              System.out.println("Component [" + component.getName() + "]");
              if(component.getName().equals("VEVENT") ){
                 
                  System.out.println(" Inside event");
              } else{
                  System.out.println(" Outside event");
              }

              for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                  Property property = (Property) j.next();
                  
                  
                  if(property.getName().equals("DTSTART")){
                     
                      DateTime start =  getDateTime(property.getValue());
                      event.setStart(new EventDateTime().setDateTime(start));
                      System.out.println("Inside DTSTRAT");
                  } else if(property.getName().equals("DTEND")) {
                      DateTime end =  getDateTime(property.getValue());
                      event.setEnd(new EventDateTime().setDateTime(end));
                     // event.setStart(new EventDateTime().setDateTime(property.getValue()));
                      System.out.println("Inside DTEND"); 
                  } else if(property.getName().equals("DESCRIPTION")  )
                  {
                      event.setDescription(property.getValue());
                      System.out.println("Inside DESCRIPTION"); 
                  } else if(property.getName().equals("SUMMARY"))
                  {
                      event.setSummary(property.getValue());
                      System.out.println("Inside SUMMARY"); 
                  }  else if(property.getName().equals("STATUS") )
                  {
                      event.setStatus(property.getValue());
                      System.out.println("Inside STATUS"); 
                  }  else if(property.getName().equals("LOCATION"))
                  {
                      event.setLocation(property.getValue());
                      System.out.println("Inside LOCATION"); 
                  }
                     
                      
                  System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
                  System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
              }
          }
          // TODO Auto-generated method stub
          service = new StartService().configure();
          Event createdEvent = service.events().insert("foli9kj7virdsnhk17i1e7m9ic@group.calendar.google.com", event).execute();

 
          
      
    }
    public  Calendar configure() {
        try {
          try {
            httpTransport = new NetHttpTransport();
            // check for valid setup
            if (SERVICE_ACCOUNT_EMAIL.startsWith("Enter ")) {
              System.err.println(SERVICE_ACCOUNT_EMAIL);
              System.exit(1);
            }
         //  URL loc = new URL( "file:///C:/Users/MohanRaj/Desktop/subasen85-7c9289551ad2.p12"); 
          URL loc = this.getClass().getResource("/subasen85-7c9289551ad2.p12"); 
            String path = loc.getPath(); 
            File file = new File(path);
            String p12Content = Files.readFirstLine(file, Charset.defaultCharset());
            if (p12Content.startsWith("Please")) {
              System.err.println(p12Content);
              System.exit(1);
            }
            // service account credential (uncomment setServiceAccountUser for domain-wide delegation)
            GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
                .setServiceAccountPrivateKeyFromP12File(file)
                           .build();
         Calendar   client = new com.google.api.services.calendar.Calendar.Builder(
                 httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME).build();
         System.out.println("Client : "+client.getApplicationName());
         System.out.println("Account Id :" +credential.getServiceAccountId());
         
         return client;

          } catch (IOException e) {
            System.err.println(e.getMessage());
          }
        } catch (Throwable t) {
          t.printStackTrace();
        }
        System.exit(1);
        return null;
      }
    private static DateTime getDateTime(String value){
        com.google.api.client.util.DateTime datetime;
        StringBuilder builder=new StringBuilder(value);
        builder.insert(4, '-');
        builder.insert(7, '-');
        //builder.replace( 10, 11,"T");
        builder.insert(13, ':');
        builder.insert(16, ':');
//        builder.replace(19,20, "");
        return com.google.api.client.util.DateTime.parseRfc3339(builder.toString());
    }
}
