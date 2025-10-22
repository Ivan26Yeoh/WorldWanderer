package flight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class FlightSearch {
   private String  departureDate;
   private String  departureAirportCode;
   private boolean emergencyRowSeating;
   private String  returnDate;
   private String  destinationAirportCode; 
   private String  seatingClass;
   private int     adultPassengerCount;
   private int     childPassengerCount;
   private int     infantPassengerCount;

   public boolean runFlightSearch(String departureDate,    String departureAirportCode,   boolean emergencyRowSeating, 
                                  String returnDate,       String destinationAirportCode, String seatingClass, 
                                  int adultPassengerCount, int childPassengerCount,       int infantPassengerCount) {
      boolean valid = true;

      //Condition 1: Total passengers between 1 and 9
      int totalPassengers = adultPassengerCount + childPassengerCount + infantPassengerCount;
      if (totalPassengers < 1 || totalPassengers > 9) {
         valid = false;
      }

      //Condition 2: Children cannot be in emergency row or first class
      if (childPassengerCount > 0 && (emergencyRowSeating || "first".equals(seatingClass))) {
         valid = false;
      }

      //Condition 3: Infants cannot be in emergency row or business class
      if (infantPassengerCount > 0 && (emergencyRowSeating || "business".equals(seatingClass))) {
         valid = false;
      }

      //Condition 4: Child to adult ratio (max 2 children per adult)
      if (childPassengerCount > 0 && adultPassengerCount == 0) {
         valid = false; // Children without adults
      }
      if (childPassengerCount > adultPassengerCount * 2) {
         valid = false; // Too many children for the number of adults
      }

      //Condition 5: Infant to adult ratio (max 1 infant per adult)
      if (infantPassengerCount > 0 && adultPassengerCount == 0) {
         valid = false; // Infants without adults
      }
      if (infantPassengerCount > adultPassengerCount) {
         valid = false; // Too many infants for the number of adults
      }

      //Condition 6, 7 and 8: Date validation
      if (!isValidDate(departureDate) || !isValidDate(returnDate)) {
         valid = false;
      } else {
         try {
            LocalDate depDate = parseDate(departureDate);
            LocalDate retDate = parseDate(returnDate);
            LocalDate today = LocalDate.now();

            //Condition 6: Departure date cannot be in past
            if (depDate.isBefore(today)) {
               valid = false;
            }

            //Condition 8: Return date must be after departure date
            if (!retDate.isAfter(depDate)) {
               valid = false;
            }
         }  
         catch (DateTimeParseException e) {
            valid = false;
         }
      }

      //Condition 9: Valid seating class
      if (!isValidSeatingClass(seatingClass)) {
         valid = false;
      }

      //Condition 10: Emergency row only in economy class
      if (emergencyRowSeating && !"economy".equals(seatingClass)) {
         valid = false;
      }

      //Condition 11: Valid airports and different codes
      if (!isValidAirport(departureAirportCode) || !isValidAirport(destinationAirportCode) || departureAirportCode.equals(destinationAirportCode)) {
         valid = false;
      }

      //If all validations pass, initialize class attributes
      if (valid) {
         this.departureDate = departureDate;
         this.departureAirportCode = departureAirportCode;
         this.emergencyRowSeating = emergencyRowSeating;
         this.returnDate = returnDate;
         this.destinationAirportCode = destinationAirportCode;
         this.seatingClass = seatingClass;
         this.adultPassengerCount = adultPassengerCount;
         this.childPassengerCount = childPassengerCount;
         this.infantPassengerCount = infantPassengerCount;
      }
      
      return valid;
   }

   //Helper method to validate date format and existence
   private boolean isValidDate(String dateStr) {
      try {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
         LocalDate.parse(dateStr, formatter);
         return true;
      }
      catch (DateTimeParseException e) {
         return false;
      }
   }

   // Helper method to parse date
   private LocalDate parseDate(String dateStr) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
      return LocalDate.parse(dateStr, formatter);
   }

   // Helper method to validate seating class
   private boolean isValidSeatingClass(String seatingClass) {
      return "economy".equals(seatingClass) || "premium economy".equals(seatingClass) || "business".equals(seatingClass) || "first".equals(seatingClass);
   }

   // Helper method to validate airport codes
   private boolean isValidAirport(String airportCode) {
      return "syd".equals(airportCode) || "mel".equals(airportCode) || "lax".equals(airportCode) || "cdg".equals(airportCode) ||
             "del".equals(airportCode) || "pvg".equals(airportCode) || "doh".equals(airportCode);
   }

   // Getters for testing attribute initialization
   public String getDepartureDate() {
      return departureDate;
   }

   public String getDepartureAirportCode() {
      return departureAirportCode;
   }

   public boolean isEmergencyRowSeating() {
      return emergencyRowSeating;
   }

   public String getReturnDate() {
      return returnDate;
   }

   public String getDestinationAirportCode() {
      return destinationAirportCode;
   }

   public String getSeatingClass() {
      return seatingClass;
   }

   public int getAdultPassengerCount() {
      return adultPassengerCount;
   }

   public int getChildPassengerCount() {
      return childPassengerCount;
   }

   public int getInfantPassengerCount() {
      return infantPassengerCount;
   }

}