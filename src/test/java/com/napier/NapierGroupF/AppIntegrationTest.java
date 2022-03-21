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
    // Global App static declaration
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

    /**
     * Test for getReport when the given Type is null
     */
    @Test
    void testGetReportNullType()
    {
        //get Report but type is null
        app.getReport(null, "SELECT * FROM country");
    }

    /**
     * Test for getReport when the sql is null
     */
    @Test
    void testGetReportNullSql()
    {
        //get Report but Sql is null
        app.getReport(Country.class, null);
    }

    /**
     * Test for getReport when the sql is Empty
     */
    @Test
    void testGetReportEmptySql()
    {
        //get Report but Sql is null
        app.getReport(Country.class, "");
    }

    /**
     * Test for getReport when the sql is invalid
     */
    @Test
    void testGetReportInvalidSql()
    {
        //get Report but Sql is null
        app.getReport(Country.class, "SELECT * frm country");
    }

    /**
     * Test get all Countries organised by population
     */
    @Test
    void testGetCountries()
    {
        //Get all Countries
        ArrayList<Country> countries = app.getCountriesOrganisedByPopulation();
        //Check if the returned list isn't null or empty
        assertTrue( countries != null && countries.size() > 0);
    }

    /**
     * Test get all Countries organised by population given a Continent
     */
    @Test
    void testGetCountriesInContinent()
    {
        //Get all Countries in a Continent
        ArrayList<Country> countries = app.getCountriesInAContinentOrganisedByPopulation("Europe");
        //Check if the returned list isn't null or empty
        assertTrue( countries != null && countries.size() > 0);
        //Check if all countries' continent is Europe
        for (Country c : countries)
        {
            assertEquals("Europe", c.Continent);
        }
    }

    /**
     * Test get all Countries organised by population given a Region
     */
    @Test
    void testGetCountriesInRegion()
    {
        //Get all Countries in a Region
        String region = "Eastern Europe";
        ArrayList<Country> countries = app.getCountriesInARegionOrganisedByPopulation(region);
        //Check if the returned list isn't null or empty
        assertTrue( countries != null && countries.size() > 0);
        //Check if all countries' Region is Asia
        for (Country c : countries)
        {
            assertEquals(region, c.Region);
        }
    }

    /**
     * Test Top N Countries organised by population
     */
    @Test
    void testGetTopNCountries()
    {
        //Get Top N Countries
        int n = 10;
        ArrayList<Country> countries = app.getTopNCountries(n);
        //Check if the returned list isn't null and is equal to N
        assertTrue( countries != null && countries.size() == n);
    }

    /**
     * Test get all Countries organised by population given a Continent
     */
    @Test
    void testGetTopNCountriesInContinent()
    {
        //Get all Countries in a Continent
        String continent = "Europe";
        int n = 10;

        ArrayList<Country> countries = app.getTopNCountriesInAContinent(n,continent);
        //Check if the returned list isn't null and contains N items
        assertTrue( countries != null && (countries.size() == n || countries.size() > 0));
        //Check if all countries' continent is Europe
        for (Country c : countries)
        {
            assertEquals(continent, c.Continent);
        }
    }

    /**
     * Test get all Countries organised by population given a Continent
     */
    @Test
    void testGetTopNCountriesInRegion()
    {
        //Get all Countries in a Region
        String region = "Eastern Europe";
        int n = 10;
        ArrayList<Country> countries = app.getTopNCountriesInARegion(n,region);
        //Check if the returned list isn't null And is either same size as N or not empty
        assertTrue( countries != null && (countries.size() == n || countries.size() > 0));
        //Check if all countries' Region is Asia
        for (Country c : countries)
        {
            assertEquals(region, c.Region);
        }
    }
}
