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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.ArrayList;

/**
 * AppTest class for App.java unit tests
 */
public class AppTest
{
    // App static declaration
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
}
