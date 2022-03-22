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

    /**
     * Test get all Cities organised by population
     */
    @Test
    void testGetCities()
    {
        //Get all Cities
        ArrayList<City> cities = app.getCitiesOrganisedByPopulation();
        //Check if the returned list isn't null or empty
        assertTrue( cities != null && cities.size() > 0);
    }

    /**
     * Test get all Cities organised by population given a Continent
     */
    @Test
    void testGetCitiesInContinent()
    {
        //Get all Cities in a Continent
        String continent = "Europe";
        ArrayList<City> cities = app.getCitiesInContinentOrganisedByPopulation(continent);
        //Check if the returned list isn't null or empty
        assertTrue( cities != null && cities.size() > 0);
        //Check if all cities' continent is Europe
        for (City c : cities)
        {
            assertEquals(continent, c.Country.Continent);
        }
    }

    /**
     * Test get all Cities organised by population given a Region
     */
    @Test
    void testGetCitiesInRegion()
    {
        //Get all Cities in a Region
        String region = "Eastern Europe";
        ArrayList<City> cities = app.getCitiesInARegionOrganisedByPopulation(region);
        //Check if the returned list isn't null or empty
        assertTrue( cities != null && cities.size() > 0);
        //Check if all cities' Region is Eastern Europe
        for (City c : cities)
        {
            assertEquals(region, c.Country.Region);
        }
    }

    /**
     * Test get all Cities organised by population given a Country
     */
    @Test
    void testGetCitiesInCountry()
    {
        //Get all Cities in a Country
        String country = "China";
        ArrayList<City> cities = app.getCitiesInCountryOrganisedByPopulation(country);
        //Check if the returned list isn't null or empty
        assertTrue( cities != null && cities.size() > 0);
        //Check if all cities' Country is China
        for (City c : cities)
        {
            assertEquals(country, c.Country.Name);
        }
    }

    /**
     * Test get all Cities organised by population given a District
     */
    @Test
    void testGetCitiesInDistrict()
    {
        //Get all Cities in a District
        String district = "Noord-Holland";
        ArrayList<City> cities = app.getCitiesInDistrictOrganisedByPopulation(district);
        //Check if the returned list isn't null or empty
        assertTrue( cities != null && cities.size() > 0);
        //Check if all cities' District is Noord-Holland
        for (City c : cities)
        {
            assertEquals(district, c.District);
        }
    }

    /**
     * Test get Top N Cities organised by population
     */
    @Test
    void testGetTopCities()
    {
        //Get Top 100 Cities
        ArrayList<City> cities = app.getTopCitiesOrganisedByPopulation(100);
        //Check if the returned list isn't null the total size equals 100
        assertTrue( cities != null && cities.size() == 100);
    }

    /**
     * Test get Top N Cities organised by population given a Continent
     */
    @Test
    void testGetTopCitiesInContinent()
    {
        //Get Top 100 Cities in a Continent
        String continent = "Europe";
        ArrayList<City> cities = app.getTopCitiesInContinentOrganisedByPopulation(continent, 100);
        //Check if the returned list isn't null the total size equals 100
        assertTrue( cities != null && cities.size() == 100);
        //Check if all cities' continent is Europe
        for (City c : cities)
        {
            assertEquals(continent, c.Country.Continent);
        }
    }

    /**
     * Test get Top N Cities organised by population given a Region
     */
    @Test
    void testGetTopCitiesInRegion()
    {
        //Get Top 50 cities in a Region
        String region = "Eastern Europe";
        ArrayList<City> cities = app.getTopCitiesInARegionOrganisedByPopulation(region, 50);
        //Check if the returned list isn't null the total size equals 50
        assertTrue( cities != null && cities.size() == 50);
        //Check if all cities' Region is Eastern Europe
        for (City c : cities)
        {
            assertEquals(region, c.Country.Region);
        }
    }

    /**
     * Test get Top N Cities organised by population given a Country
     */
    @Test
    void testGetTopCitiesInCountry()
    {
        //Get Top 100 Cities in a Country
        String country = "China";
        ArrayList<City> cities = app.getTopCitiesInCountryOrganisedByPopulation(country, 100);
        //Check if the returned list isn't null and the total size equals 100
        assertTrue( cities != null && cities.size() == 100);
        //Check if all cities' Country is China
        for (City c : cities)
        {
            assertEquals(country, c.Country.Name);
        }
    }

    /**
     * Test get Top N Cities organised by population given a District
     */
    @Test
    void testGetTopCitiesInDistrict()
    {
        //Get Top 5 Cities in a District
        String district = "Noord-Holland";
        ArrayList<City> cities = app.getTopCitiesInDistrictOrganisedByPopulation(district, 5);
        //Check if the returned list isn't null and either the total size equals 5 or the list is not empty
        assertTrue( cities != null && (cities.size() == 5 || cities.size() > 0));
        //Check if all cities' District is Noord-Holland
        for (City c : cities)
        {
            assertEquals(district, c.District);
        }
    }

    /**
     * Test get all Capital Cities organised by population
     */
    @Test
    void testGetCapitalCities()
    {
        //Get all Capital Cities
        ArrayList<City> cities = app.getCapitalCitiesOrganisedByPopulation();
        //Check if the returned list isn't null or empty
        assertTrue( cities != null && cities.size() > 0);
    }

    /**
     * Test get all Capital Cities organised by population given a Continent
     */
    @Test
    void testGetCapitalCitiesInContinent()
    {
        //Get all Capital Cities in a Continent
        String continent = "Europe";
        ArrayList<City> cities = app.getCapitalCitiesInContinentOrganisedByPopulation(continent);
        //Check if the returned list isn't null or empty
        assertTrue( cities != null && cities.size() > 0);
        //Check if all Capital cities' continent is Europe
        for (City c : cities)
        {
            assertEquals(continent, c.Country.Continent);
        }
    }

    /**
     * Test get all Cities organised by population given a Region
     */
    @Test
    void testGetCapitalCitiesInRegion()
    {
        //Get all Capital Cities in a Region
        String region = "Eastern Europe";
        ArrayList<City> cities = app.getCapitalCitiesInARegionOrganisedByPopulation(region);
        //Check if the returned list isn't null or empty
        assertTrue( cities != null && cities.size() > 0);
        //Check if all Capital cities' Region is Eastern Europe
        for (City c : cities)
        {
            assertEquals(region, c.Country.Region);
        }
    }

    /**
     * Test get Top N Capital Cities organised by population
     */
    @Test
    void testGetTopCapitalCities()
    {
        //Get Top 100 Cities
        ArrayList<City> cities = app.getTopCapitalCitiesOrganisedByPopulation(100);
        //Check if the returned list isn't null the total size equals 100
        assertTrue( cities != null && cities.size() == 100);
    }
}
