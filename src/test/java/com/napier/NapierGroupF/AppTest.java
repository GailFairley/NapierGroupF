/**
 * Project Name: coursework
 * Package: com.napier.NapierGroupF
 * User Created: Hamza Shabir
 * Date Created: 21/03/2022
 * Date Last Updated: 21/03/2022
 * File Description: The App test java file which will have all the Unit tests for the App.java class.
 **/

package com.napier.NapierGroupF;

//Imports
import com.mockrunner.mock.jdbc.MockResultSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * AppTest class for App.java unit tests
 */
public class AppTest<T>
{
    //Global App static declaration
    static App app;

    /**
     * before any tests initialize the App class
     */
    @BeforeAll
    static void init()
    {
        app = new App();
    }

    //Display Countries Test
    /**
     * Test for displayCountries method when null countries list given
     */
    @Test
    void displayCountriesTestNull()
    {
        app.displayCountries(null);
    }

    /**
     * Test for displayCountries method when an Empty countries list given
     */
    @Test
    void displayCountriesTestEmpty()
    {
        //Empty Country Arraylist
        app.displayCountries(new ArrayList<Country>());
    }

    /**
     * Test for displayCountries method when the countries list contains an Empty Country
     */
    @Test
    void displayCountriesTestContainsNull()
    {
        //new Countries arraylist
        ArrayList<Country> countries = new ArrayList<Country>();
        //add null country
        countries.add(null);

        app.displayCountries(countries);
    }

    /**
     * Test with Empty City and Population for the Country
     */
    @Test
    void displayCountriesTestEmptyCityPopulation()
    {
        //Initialize Countries List
        ArrayList<Country> countries = new ArrayList<Country>();

        //Initialize Country obj
        Country c = new Country();
        c.Code = "123";
        c.Name = "America";
        c.Region = "USA";
        c.Continent = "Americas";
        //City Name
        c.Capital = new City();
        //Total Population
        c.Population = new Population();
        countries.add(c);

        app.displayCountries(countries);
    }

    /**
     * Test with Null City (capital) and Population for the Country
     */
    @Test
    void displayCountriesTestNullCityPopulation()
    {
        //Initialize Countries List
        ArrayList<Country> countries = new ArrayList<Country>();

        //Initialize Country obj
        Country c = new Country();
        c.Code = "123";
        c.Name = "America";
        c.Region = "USA";
        c.Continent = "Americas";
        //Null City and Population
        countries.add(c);

        app.displayCountries(countries);
    }

    /**
     * Test with normal input
     */
    @Test
    void displayCountriesTest()
    {
        //Initialize Countries List
        ArrayList<Country> countries = new ArrayList<Country>();

        //Initialize Country obj
        Country c = new Country();
        c.Code = "123";
        c.Name = "America";
        c.Region = "USA";
        c.Continent = "Americas";
        //City Name
        c.Capital = new City("Washington, D.C.");
        //Total Population
        c.Population = new Population(325000000);
        countries.add(c);

        app.displayCountries(countries);
    }

    //displayCities Tests
    /**
     * Test for displayCities method when null cities list given
     */
    @Test
    void displayCitiesTestNull()
    {
        app.displayCities(null);
    }

    /**
     * Test for displayCities method when an Empty cities list given
     */
    @Test
    void displayCitiesTestEmpty()
    {
        //Empty Country Arraylist
        app.displayCities(new ArrayList<>());
    }

    /**
     * Test for displayCities method when the cities list contains an Empty City
     */
    @Test
    void displayCitiesTestContainsNull()
    {
        //new Cities arraylist
        ArrayList<City> cities = new ArrayList<>();
        //add null city
        cities.add(null);

        app.displayCities(cities);
    }

    /**
     * Test with Empty country and Population for the Country
     */
    @Test
    void displayCitiesTestEmptyCountryPopulation()
    {
        //Initialize Cities List
        ArrayList<City> cities = new ArrayList<>();

        //Initialize city obj
        City c = new City();
        c.Name = "Washington, D.C.";
        c.District = "Columbia";
        //Country Name
        c.Country = new Country();
        //Total Population
        c.Population = new Population();
        cities.add(c);

        app.displayCities(cities);
    }

    /**
     * Test with Null Country and Population for the City
     */
    @Test
    void displayCitiesTestNullCountryPopulation()
    {
        //Initialize Cities List
        ArrayList<City> cities = new ArrayList<>();

        //Initialize City obj
        City c = new City();
        c.Name = "Washington, D.C.";
        c.District = "Columbia";
        //Null Country + Population
        cities.add(c);

        app.displayCities(cities);
    }

    /**
     * Test with normal input
     */
    @Test
    void displayCitiesTestNormal()
    {
        //Initialize Cities List
        ArrayList<City> cities = new ArrayList<>();

        //Initialize Cities obj
        City c = new City();
        c.Name = "Washington, D.C.";
        c.District = "Columbia";
        //Country Name
        c.Country = new Country("America");
        //Total Population
        c.Population = new Population(692683);
        cities.add(c);

        app.displayCities(cities);
    }

    /**
     * Test for validateSql when the sql is not null or empty
     */
    @Test
    void validateSqlTest()
    {
        //Validate sql normal
        app.validateSql("SELECT * FROM country");
    }

    /**
     * Test for validateSql when the sql is null
     */
    @Test
    void validateSqlNullTest()
    {
        //validate sql null SQL string
        app.validateSql(null);
    }

    /**
     * Test for validateSql when the sql is Empty
     */
    @Test
    void validateSqlEmptyTest()
    {
        //validate sql empty SQL string
        app.validateSql("");
    }

    /**
     * Test for findColumn Normal
     */
    @Test
    void findColumnTest()
    {
        //find column normal
        assertTrue(app.findColumn(countryResultSet(), "Code"));
    }

    /**
     * Test for findColumn Normal for City Result set
     */
    @Test
    void findColumnCitiesTest()
    {
        //find column normal
        assertTrue(app.findColumn(cityResultSet(), "Country"));
    }

    /**
     * Test for findColumn column doesnt exist
     */
    @Test
    void findColumnNotExistsTest()
    {
        //find column where column doesnt exist
        assertFalse(app.findColumn(countryResultSet(), "Employee"));
    }

    /**
     * Test for findColumn Normal for City Result set column doesn't exist
     */
    @Test
    void findColumnCitiesNotExistsTest()
    {
        //find column where column doesnt exist
        assertFalse(app.findColumn(cityResultSet(), "Code"));
    }

    /**
     * Test for findColumn when Result test is empty
     */
    @Test
    void findColumnEmptyResultSetTest()
    {
        //Empty ResultSet
        var rs = new MockResultSet("country");
        //find column empty ResultSet
        assertFalse(app.findColumn(rs, "Code"));
    }

    /**
     * Test for findColumn when Column name is Empty
     */
    @Test
    void findColumnEmptyColumnNameTest()
    {
        //find column empty Column name
        assertFalse(app.findColumn(countryResultSet(), ""));
    }

    /**
     * Test for findColumn when Column name is Null
     */
    @Test
    void findColumnNullColumnNameTest()
    {
        //find column null Column name
        assertFalse(app.findColumn(countryResultSet(), null));
    }

    /**
     * Test for mapCountries normal
     */
    @Test
    void mapCountriesTest()
    {
        //mapCountries Test
        app.mapCountries(new ArrayList<>(), countryResultSet());
    }

    /**
     * Test for mapCountries when Country Arraylist is null
     */
    @Test
    void mapCountriesNullCountryListTest()
    {
        //mapCountries Test
        app.mapCountries(null, countryResultSet());
    }

    /**
     * Test for mapCountries when Result set is Empty
     */
    @Test
    void mapCountriesEmptyResultSetTest()
    {
        //mapCountries Test
        app.mapCountries(new ArrayList<>(), new MockResultSet("country"));
    }

    /**
     * Test for mapCities normal
     */
    @Test
    void mapCitiesTest()
    {
        //mapCities Test
        app.mapCities(cityResultSet(), new ArrayList<>());
    }

    /**
     * Test for mapCities when City Arraylist is null
     */
    @Test
    void mapCitiesNullCountryListTest()
    {
        //mapCities Test
        app.mapCities(cityResultSet(), null);
    }

    /**
     * Test for mapCities when the Result set is empty
     */
    @Test
    void mapCitiesEmptyResultSetTest()
    {
        //mapCities Test
        app.mapCities(new MockResultSet("country"), new ArrayList<>());
    }

    /**
     * Method to populate mock Result set
     * @return Mock Result set
     */
    ResultSet countryResultSet()
    {
        //Initiate Mock Result set
        var rs = new MockResultSet("country");

        //Add Mocked Country Columns
        rs.addColumn("Code",  new String[]{"01", "02", "03", "04", "05", "06", "07", "08"});
        rs.addColumn("Name",  new String[]{"UK", "USA", "Canada", "Australia", "India", "Russia", "China", "South Africa"});
        rs.addColumn("Continent",  new String[]{"Europe", "Americas", "Americas", "Australia", "Asia", "Europe", "Asia", "Africa"});
        rs.addColumn("Region",  new String[]{"E.U", "NORTH AMERICA", "NORTH AMERICA", "OCEANIA", "SOUTH ASIA", "EASTERN EUROPE", "EASTERN ASIA", "NORTH AFRICA"});
        rs.addColumn("Population",  new Long[]{2500000L, 125000000L, 38900000L, 4000000L, 160000000L, 89000000L, 180000000L, 20500000L});
        rs.addColumn("Capital",  new String[]{"London", "Washington D.C", "Ottawa", "Canberra", "New Delhi", "Moscow", "Beijing", "Cape Town"});

        //Return Mock Result set
        return rs;
    }

    /**
     * Method to populate mock Result set
     * @return Mock Result set
     */
    ResultSet cityResultSet()
    {
        //Initiate Mock Result set
        var rs = new MockResultSet("city");

        //Add Mocked Country Columns
        rs.addColumn("Country",  new String[]{"UK", "USA", "Canada", "Australia", "India", "Russia", "China", "South Africa"});
        rs.addColumn("Continent",  new String[]{"Europe", "Americas", "Americas", "Australia", "Asia", "Europe", "Asia", "Africa"});
        rs.addColumn("Region",  new String[]{"E.U", "NORTH AMERICA", "NORTH AMERICA", "OCEANIA", "SOUTH ASIA", "EASTERN EUROPE", "EASTERN ASIA", "NORTH AFRICA"});
        rs.addColumn("Population",  new Long[]{2500000L, 125000000L, 38900000L, 4000000L, 160000000L, 89000000L, 180000000L, 20500000L});
        rs.addColumn("Name",  new String[]{"London", "Washington D.C", "Ottawa", "Canberra", "New Delhi", "Moscow", "Beijing", "Cape Town"});
        rs.addColumn("District",  new String[]{"London District", "Washington District", "Ottawa District", "Canberra District", "New Delhi District", "Moscow District", "Beijing District", "Cape Town District"});

        //Return Mock Result set
        return rs;
    }
}
