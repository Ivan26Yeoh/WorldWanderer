package flight;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FlightSearchTest {

    @Test
    void testValidSearch() {
        FlightSearch flightSearch = new FlightSearch();
        
        //Test a valid flight search
        boolean result = flightSearch.runFlightSearch(
            "25/12/2025", "syd", false,
            "30/12/2025", "mel", "economy",
            2, 1, 0
        );
        
        assertTrue(result, "Valid search should return true");
    }

    @Test
    void testZeroPassengers() {
        FlightSearch flightSearch = new FlightSearch();
        
        //Test with zero passengers (should be invalid)
        boolean result = flightSearch.runFlightSearch(
            "25/12/2025", "syd", false,
            "30/12/2025", "mel", "economy",
            0, 0, 0
        );
        
        assertFalse(result, "Zero passengers should return false");
    }

    @Test
    void testChildrenInEmergencyRow() {
        FlightSearch flightSearch = new FlightSearch();
        
        //Test children in emergency row (should be invalid)
        boolean result = flightSearch.runFlightSearch(
            "25/12/2025", "syd", true,
            "30/12/2025", "mel", "economy",
            1, 1, 0
        );
        
        assertFalse(result, "Children in emergency row should return false");
    }

    @Test
    void testInvalidDate() {
        FlightSearch flightSearch = new FlightSearch();
        
        //Test with invalid date format
        boolean result = flightSearch.runFlightSearch(
            "32/13/2025", "syd", false,
            "30/12/2025", "mel", "economy",
            1, 0, 0
        );
        
        assertFalse(result, "Invalid date should return false");
    }

    @Test
    void testAttributesInitializedOnSuccess() {
        FlightSearch flightSearch = new FlightSearch();
        
        //Test that attributes are set when validation passes
        boolean result = flightSearch.runFlightSearch(
            "25/12/2025", "syd", false,
            "30/12/2025", "mel", "economy",
            2, 1, 0
        );
        
        assertTrue(result, "Valid search should return true");
        assertEquals("25/12/2025", flightSearch.getDepartureDate());
        assertEquals("syd", flightSearch.getDepartureAirportCode());
        assertEquals("mel", flightSearch.getDestinationAirportCode());
        assertEquals("economy", flightSearch.getSeatingClass());
        assertEquals(2, flightSearch.getAdultPassengerCount());
        assertEquals(1, flightSearch.getChildPassengerCount());
        assertEquals(0, flightSearch.getInfantPassengerCount());
    }

    @Test
    void testAttributesNotInitializedOnFailure() {
        FlightSearch flightSearch = new FlightSearch();
        
        //Test that attributes are NOT set when validation fails
        boolean result = flightSearch.runFlightSearch(
            "25/12/2025", "syd", false,
            "30/12/2025", "mel", "economy",
            0, 0, 0  // Invalid: zero passengers
        );
        
        assertFalse(result, "Invalid search should return false");
        assertNull(flightSearch.getDepartureDate());
        assertNull(flightSearch.getDepartureAirportCode());
        assertNull(flightSearch.getReturnDate());
        assertNull(flightSearch.getDestinationAirportCode());
        assertNull(flightSearch.getSeatingClass());
        assertEquals(0, flightSearch.getAdultPassengerCount());
        assertEquals(0, flightSearch.getChildPassengerCount());
        assertEquals(0, flightSearch.getInfantPassengerCount());
    }
}