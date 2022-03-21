/**
 * Project Name: coursework
 * Package: com.napier.NapierGroupF
 * User Created: Hamza Shabir
 * Date Created: 21/03/2022
 * Date Last Updated: 21/03/2022
 * File Description: The App test java file which will have all the Integration tests for the App.java class.
 **/

package com.napier.NapierGroupF;

//Imports
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Class to Test the integration with the world db
 */
public class AppIntegrationTest
{
    // App static declaration
    static App app;

    /**
     * Before anything initialize App and Connect to Local db
     */
    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33069", 3000);

    }

    @Test
    void testGetCountries()
    {
        ArrayList<Country> countries = app.getCountriesOrganisedByPopulation();
        assertTrue( countries != null && countries.size() > 0, "The Countries List is either Null or Empty!");
    }
}
