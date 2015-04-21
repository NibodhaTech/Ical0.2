# Ical0.2

Ical0.2 is the module for making the Ical sync as a reusable module.

The source code consisits of two files namely 

   IcalCredentialsReader.java.
   StartService.java
   
   
interface IcalCredentialsReader.
    This interface consist of a map which is used to store the necessary credentials of user's and calendars which is need to synced.
    
    
 StartService.
 
    The startService consists a main method and a service method. There are four variables  created which are used to store the credentials.
	
	APPLICATION_NAME
    SERVICE_ACCOUNT_EMAIL
    loc 
    CALENDAR_ID
	
	APPLICATION_NAME 
	
		The name of the application.
		
    SERVICE_ACCOUNT_EMAIL
	
		The store the service accounts.
		
	loc 
	
		To store the key file path which is in .p12 format.
		
	CALENDAR_ID
	
		To store the calendar Id.
	
    In the main method there are two different Iterator are used.
    Scanner scanner = new Scanner(new FileReader("C:/Users/MohanRaj/Desktop/filename.txt"))
	
		This scanner is used to read the credentials from the local path and put them in a map.
		
    Iterator<Map.Entry<String, String>> iterator = credentialsMap.entrySet().iterator() 

		This Iterator will iterate through the map and assign to the variables mentioned.
    
	FileInputStream fin = new FileInputStream("C:/Users/MohanRaj/Desktop/Pepsi_Indian_Premier_League.ics")
 
		In this method it will read the calendar which is in ics format from the local path and create a virtual calender along with events.
		
	Configure method.
	
	  Configure method will return the calendar. The credentials  are assigned to the variables and the business logics are done here.
	  
	  
    
