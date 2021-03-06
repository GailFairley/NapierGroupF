/**
 * Project Name: coursework
 * Package: com.napier.NapierGroupF
 * User Created: Hamza Shabir
 * Date Created: 21/03/2022
 * Date Last Updated: 24/04/2022
 * File Description: The App test java file which will have all the Integration tests for the App.java class.
 **/

package com.napier.NapierGroupF;

//Imports
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
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
     * Disconnect from the db after all tests
     */
    @AfterAll
    static void close()
    {
        app.disconnect();
    }

    /**
     * Test for executeQuery when the SQL is Normal
     */
    @Test
    void testExecuteQuery()
    {
        //executeQuery Normal
        var rs = app.executeQuery( "SELECT * FROM country");

        //Check if the returned ResultSet isn't null
        assertNotNull(rs);
    }

    /**
     * Test for executeQuery when the sql is null
     */
    @Test
    void testExecuteSqlNullSql()
    {
        //executeQuery but Sql is null
        app.executeQuery(null);
    }

    /**
     * Test for executeQuery when the sql is Empty
     */
    @Test
    void testExecuteSqlEmptySql()
    {
        //executeQuery but Sql is null
        app.executeQuery("");
    }

    /**
     * Test for executeQuery when the sql is invalid
     */
    @Test
    void testExecuteSqlInvalidSql()
    {
        //executeQuery but Sql is Invalid
        app.executeQuery("SELECT * frm country");
    }

    /**
     * Test for getAndMapCountries when the sql is invalid
     */
    @Test
    void testGetAndMapCountries()
    {
        //getAndMapCountry Test
        ArrayList<Country> countries = app.getCountries("SELECT * FROM country");
        assertTrue(countries != null && countries.size() > 0);
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

    /**
     * Test get Top N Capital Cities in Continent organised by population
     */
    @Test
    void testGetTopCapitalCitiesInContinent()
    {
        //User Given Inputs
        String continent = "Europe";
        int n = 100;

        //Get Top 100 Cities
        ArrayList<City> cities = app.getTopCapitalCitiesInContinentOrganisedByPopulation(n, continent);
        //Check if the returned list isn't null the and either total size equals 100 or the list size is greater than 0
        assertTrue( cities != null && (cities.size() == n || cities.size() > 0));

        //Check if all Capital cities' Continent is Europe
        for (City c : cities)
        {
            assertEquals(continent, c.Country.Continent);
        }
    }

    /**
     * Test get Top N Capital Cities in a Region organised by population
     */
    @Test
    void testGetTopCapitalCitiesInRegion()
    {
        //User Given Inputs
        String region = "Western Europe";
        int n = 10;

        //Get Top 100 Cities
        ArrayList<City> cities = app.getTopCapitalCitiesInRegionOrganisedByPopulation(n, region);
        //Check if the returned list isn't null the and either total size equals 100 or the list size is greater than 0
        assertTrue( cities != null && (cities.size() == n || cities.size() > 0));

        //Check if all Capital cities' Region is Western Europe
        for (City c : cities)
        {
            assertEquals(region, c.Country.Region);
        }
    }

    /**
     * Test for Population in cities and not in cities for each continent
     */
    @Test
    void testGetPopulationInCitiesAndNotInCitiesForEachContinent()
    {
        //Get all Population in Cities and not in Cities for each Continent
        ArrayList<Population> populations = app.getPopulationInCitiesAndNotInCitiesInContinents();
        //Check if the returned list isn't null or empty
        assertTrue( populations != null && populations.size() > 0);
    }

    /**
     * Test for Population in cities and not in cities for each region
     */
    @Test
    void testGetPopulationInCitiesAndNotInCitiesForEachRegion()
    {
        //Get all Population in Cities and not in Cities for each Region
        ArrayList<Population> populations = app.getPopulationInCitiesAndNotInCitiesInRegions();
        //Check if the returned list isn't null or empty
        assertTrue( populations != null && populations.size() > 0);
    }

    /**
     * Test for Population in cities and not in cities for each country
     */
    @Test
    void testGetPopulationInCitiesAndNotInCitiesForEachCountry()
    {
        //Get all Population in Cities and not in Cities for each Country
        ArrayList<Population> populations = app.getPopulationInCitiesAndNotInCitiesInCountries();
        //Check if the returned list isn't null or empty
        assertTrue( populations != null && populations.size() > 0);
    }

    /**
     * Test for Languages population Report
     */
    @Test
    void testGetLanguagesPopulationReport()
    {
        //Get all Population in Cities and not in Cities for each Country
        ArrayList<Population> populations = app.getPopulationOfLanguages();
        //Check if the returned list isn't null or empty
        assertTrue( populations != null && populations.size() > 0);
    }

    /**
     * Test for Total World Population
     */
    @Test
    void testGetTotalWorldPopulation()
    {
        //Get Total World Population
        Population p = app.getTotalPopulationOfWorld().get(0);
        //Check if the returned value is greater than 0
        assertTrue( p.TotalPopulation > 0);
    }

    /**
     * Test for Total World Population
     */
    @Test
    void testGetTotalContinentPopulation()
    {
        //Get Total Continent Population
        Population p = app.getTotalPopulationOfContinent("Europe").get(0);
        //Check if the returned value is greater than 0
        assertTrue( p.TotalPopulation > 0);
    }

    /**
     * Test for Total Region Population
     */
    @Test
    void testGetTotalRegionPopulation()
    {
        //Get Total Region Population
        Population p = app.getTotalPopulationOfRegion("Western Europe").get(0);
        //Check if the returned value is greater than 0
        assertTrue( p.TotalPopulation > 0 );
    }

    /**
     * Test for Total Country Population
     */
    @Test
    void testGetTotalCountryPopulation()
    {
        //Get Total Country Population
        Population p = app.getTotalPopulationOfCountry("China").get(0);
        //Check if the returned value is greater than 0
        assertTrue( p.TotalPopulation > 0 );
    }

    /**
     * Test for Total City Population
     */
    @Test
    void testGetTotalCityPopulation()
    {
        //Get Total City Population
        Population p = app.getTotalPopulationOfCity("London").get(0);
        //Check if the returned value is greater than 0
        assertTrue( p.TotalPopulation > 0 );
    }

    /**
     * Test for Total District Population
     */
    @Test
    void testGetTotalDistrictPopulation()
    {
        //Get Total District Population
        Population p = app.getTotalPopulationOfDistrict("Ontario").get(0);
        //Check if the returned value is greater than 0
        assertTrue( p.TotalPopulation > 0 );
    }


    /**
     * Test of all Get Reports Methods
     */
    @Test
    void testGetReports()
    {
        //Get and Display all Reports
        app.getAndDisplayAllReports();
    }
}
