/**
 * Project Name: coursework
 * Package: com.napier.NapierGroupF
 * User Created: Hamza Shabir
 * Date Created: 21/03/2022
 * Date Last Updated: 24/04/2022
 * File Description: The App test java file which will have all the Unit tests for the App.java class.
 **/

package com.napier.NapierGroupF;

//Imports
import com.mockrunner.mock.jdbc.MockResultSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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

    //Output Countries Test
    /**
     * Test for outputCountries method when null countries list given
     */
    @Test
    void outputCountriesNullTest()
    {
        app.outputCountries(null, "country", "Title");
    }

    /**
     * Test for outputCountries method when an Empty countries list given
     */
    @Test
    void outputCountriesEmptyTest()
    {
        //Empty Country Arraylist
        app.outputCountries(new ArrayList<>(), "country", "Title");
    }

    /**
     * Test for outputCountries method when the countries list contains a Null Country
     */
    @Test
    void outputCountriesContainsNullTest()
    {
        //new Countries arraylist
        ArrayList<Country> countries = new ArrayList<Country>();
        //add null country
        countries.add(null);

        app.outputCountries(countries, "country", "Title");
    }

    /**
     * Test outputCountries with Empty City and Population for the Country
     */
    @Test
    void outputCountriesEmptyCityPopulationTest()
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

        app.outputCountries(countries, "country", "Title");
    }

    /**
     * Test outputCountries with Null City (capital) and Population for the Country
     */
    @Test
    void outputCountriesNullCityPopulationTest()
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

        app.outputCountries(countries, "country", "Title");
    }

    /**
     * Test outputCountries with normal input
     */
    @Test
    void outputCountriesTest()
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

        app.outputCountries(countries, "country", "Title");
    }

    /**
     * Test outputCountries with null Filename and Title
     */
    @Test
    void outputCountriesNullFileTitleTest()
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

        //Test outputCountries with null Filename and Title
        app.outputCountries(countries, null, null);
    }

    /**
     * Test outputCountries with Empty Filename and Title
     */
    @Test
    void outputCountriesEmptyFileTitleTest()
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

        //Test outputCountries with empty Filename and Title
        app.outputCountries(countries, "", "");
    }

    //displayCities Tests
    /**
     * Test for outputCities method when null cities list given
     */
    @Test
    void outputCitiesNullTest()
    {
        app.outputCities(null, "city", "Title");
    }

    /**
     * Test for outputCities method when an Empty cities list given
     */
    @Test
    void outputCitiesEmptyTest()
    {
        //Empty Country Arraylist
        app.outputCities(new ArrayList<>(), "city", "Title");
    }

    /**
     * Test for outputCities method when the cities list contains a Null City
     */
    @Test
    void outputCitiesContainsNullCityTest()
    {
        //new Cities arraylist
        ArrayList<City> cities = new ArrayList<>();
        //add null city
        cities.add(null);

        app.outputCities(cities, "city", "Title");
    }

    /**
     * Test for outputCities method when the cities list contains an Empty City
     */
    @Test
    void outputCitiesContainsEmptyCityTest()
    {
        //new Cities arraylist
        ArrayList<City> cities = new ArrayList<>();
        //add null city
        cities.add(new City());

        app.outputCities(cities, "city", "Title");
    }

    /**
     * Test outputCities with Empty country and Population
     */
    @Test
    void outputCitiesEmptyCountryPopulationTest()
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

        app.outputCities(cities, "city", "Title");
    }

    /**
     * Test outputCities with Null Country and Population for the City
     */
    @Test
    void outputCitiesNullCountryPopulationTest()
    {
        //Initialize Cities List
        ArrayList<City> cities = new ArrayList<>();

        //Initialize City obj
        City c = new City();
        c.Name = "Washington, D.C.";
        c.District = "Columbia";
        //Null Country + Population
        cities.add(c);

        app.outputCities(cities, "city", "Title");
    }

    /**
     * Test outputCities with normal input
     */
    @Test
    void outputCitiesNormalTest()
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

        app.outputCities(cities, "city", "Title");
    }

    /**
     * Test outputCities with null Filename and Title
     */
    @Test
    void outputCitiesNullFileTitleTest()
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

        app.outputCities(cities, null, null);
    }

    /**
     * Test outputCities with empty Filename and Title
     */
    @Test
    void outputCitiesEmptyFileTitleTest()
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

        app.outputCities(cities, "", "");
    }

    //Output Population tests
    /**
     * Test outputPopulation with null populations list
     */
    @Test
    void outputPopulationNullListTest()
    {
        app.outputPopulation(null, "population", "Title", "Region", false);
    }

    /**
     * Test outputPopulation with Empty populations list
     */
    @Test
    void outputPopulationEmptyListTest()
    {
        var populations = new ArrayList<Population>();
        app.outputPopulation(populations, "population", "Title", "Region", false);
    }

    /**
     * Test outputPopulation with null population entry in the list
     */
    @Test
    void outputPopulationNullPopulationTest()
    {
        //Initialize Population List
        ArrayList<Population> populations = new ArrayList<>();
        //add null population
        populations.add(null);

        app.outputPopulation(populations, "population", "Title", "Continent", false);
    }

    /**
     * Test outputPopulation with empty population entry in the list
     */
    @Test
    void outputPopulationEmptyPopulationTest()
    {
        //Initialize Population List
        ArrayList<Population> populations = new ArrayList<>();
        //add empty population
        populations.add(new Population());

        app.outputPopulation(populations, "population", "Title", "Continent", false);
    }

    /**
     * Test outputPopulation with normal input
     */
    @Test
    void outputPopulationNormalTest()
    {
        //Initialize Population List and Map the Test Result set
        ArrayList<Population> populations = new ArrayList<>();
        app.mapPopulation(populationContinentResultSet(), populations);

        app.outputPopulation(populations, "population", "Title", "Continent", false);
    }

    /**
     * Test outputPopulation with null Filename and Title
     */
    @Test
    void outputPopulationNullFileTitleTest()
    {
        //Initialize Population List and Map the Test Result set
        ArrayList<Population> populations = new ArrayList<>();
        app.mapPopulation(populationContinentResultSet(), populations);

        app.outputPopulation(populations, null, null, "Continent", false);
    }

    /**
     * Test outputPopulation with empty Filename and Title
     */
    @Test
    void outputPopulationEmptyFileTitleTest()
    {
        //Initialize Population List and Map the Test Result set
        ArrayList<Population> populations = new ArrayList<>();
        app.mapPopulation(populationContinentResultSet(), populations);

        app.outputPopulation(populations, "", "", "Continent", false);
    }

    /**
     * Output Population Language population type test
     */
    @Test
    void outputPopulationLanguageTest()
    {
        //Initialize Population List and Map the Test Result set
        ArrayList<Population> populations = new ArrayList<>();
        app.mapPopulation(populationLanguageResultSet(), populations);

        app.outputPopulation(populations, "population", "Title", "Language", false);
    }

    /**
     * Output Population Language population type test
     */
    @Test
    void outputPopulationOnlyTotalPopulationTest()
    {
        //Initialize Population List and Map the Test Result set
        ArrayList<Population> populations = new ArrayList<>();
        app.mapPopulation(populationLanguageResultSet(), populations);

        app.outputPopulation(populations, "population", "Title", "Language", true);
    }

    @Test
    void writeToFileTestNull()
    {
        app.writeToFile(null, null);
    }

    /**
     * Test writeToFile with empty Filename and String Builder
     */
    @Test
    void writeToFileTestEmpty()
    {
        app.writeToFile("", new StringBuilder());
    }

    /**
     * Test writeToFile Normal
     */
    @Test
    void writeToFileTestNormal()
    {
        //Initialize String Builder
        StringBuilder sb = new StringBuilder();
        sb.append("Test");

        app.writeToFile("file", sb);
    }

    /**
     * Test for validateSql when the sql is not null or empty
     */
    @Test
    void validateSqlTest()
    {
        //Validate sql normal
        app.validateString("SELECT * FROM country");
    }

    /**
     * Test for validateSql when the sql is null
     */
    @Test
    void validateSqlNullTest()
    {
        //validate sql null SQL string
        app.validateString(null);
    }

    /**
     * Test for validateSql when the sql is Empty
     */
    @Test
    void validateSqlEmptyTest()
    {
        //validate sql empty SQL string
        app.validateString("");
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
     * Test for mapCountries when the Result set is Null
     */
    @Test
    void mapCountriesNullResultSetTest()
    {
        //mapCities Test
        app.mapCountries(new ArrayList<>(), null);
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
     * Test for mapCities when the Result set is Null
     */
    @Test
    void mapCitiesNullResultSetTest()
    {
        //mapCities Test
        app.mapCities(null, new ArrayList<>());
    }

    /**
     * Test for mapPopulation normal - Continent
     */
    @Test
    void mapPopulationContinentTest()
    {
        //mapPopulation Test
        app.mapPopulation(populationContinentResultSet(), new ArrayList<>());
    }

    /**
     * Test for mapPopulation normal - Region
     */
    @Test
    void mapPopulationRegionTest()
    {
        //mapPopulation Test
        app.mapPopulation(populationRegionResultSet(), new ArrayList<>());
    }

    /**
     * Test for mapPopulation normal - Country
     */
    @Test
    void mapPopulationCountryTest()
    {
        //mapPopulation Test
        app.mapPopulation(populationCountryResultSet(), new ArrayList<>());
    }

    /**
     * Test for mapPopulation when Population Arraylist is null
     */
    @Test
    void mapPopulationNullListTest()
    {
        //mapPopulation Test
        app.mapPopulation(populationCountryResultSet(), null);
    }

    /**
     * Test for mapPopulation when the Result set is empty
     */
    @Test
    void mapPopulationEmptyResultSetTest()
    {
        //mapPopulation Test
        app.mapPopulation(new MockResultSet("population"), new ArrayList<>());
    }

    /**
     * Test for mapPopulation when the Result set is Null
     */
    @Test
    void mapPopulationNullResultSetTest()
    {
        //mapPopulation Test
        app.mapPopulation(null, new ArrayList<>());
    }

    /**
     * Method to populate mock Result set for Country
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
     * Method to populate mock Result set for City
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

    /**
     * Method to populate mock Result set for Country population
     * @return Mock Result set
     */
    ResultSet populationCountryResultSet()
    {
        //Initiate Mock Result set
        var rs = new MockResultSet("countryPopulation");

        //Add Mocked Population Columns
        rs.addColumn("Name",  new String[]{"UK", "USA", "Canada", "Australia", "India", "Russia", "China", "South Africa"});
        rs.addColumn("TotalPopulation",  new Long[]{2500000L, 125000000L, 38900000L, 4000000L, 160000000L, 89000000L, 180000000L, 20500000L});
        rs.addColumn("UrbanPopulation",  new Long[]{2000000L, 120000000L, 30000000L, 2900000L, 100000000L, 80000000L, 100000000L, 20000000L});
        rs.addColumn("RuralPopulation",  new Long[]{500000L, 5000000L, 8900000L, 1100000L, 60000000L, 9000000L, 80000000L, 500000L});

        //Return Mock Result set
        return rs;
    }

    /**
     * Method to populate mock Result set for Region population
     * @return Mock Result set
     */
    ResultSet populationRegionResultSet()
    {
        //Initiate Mock Result set
        var rs = new MockResultSet("regionPopulation");

        //Add Mocked Population Columns
        rs.addColumn("Name",  new String[]{"Western Europe", "Caribbean", "Southern Europe", "Middle East", "British Islands", "Eastern Africa", "Southern and Central Asia", "South America"});
        rs.addColumn("TotalPopulation",  new Long[]{25000000L, 121000000L, 39000000L, 4000000L, 180000000L, 90000000L, 190000000L, 20800000L});
        rs.addColumn("UrbanPopulation",  new Long[]{20000000L, 120000000L, 30000000L, 3000000L, 100000000L, 80000000L, 100000000L, 20000000L});
        rs.addColumn("RuralPopulation",  new Long[]{500000L, 1000000L, 9000000L, 1000000L, 80000000L, 10000000L, 90000000L, 800000L});

        //Return Mock Result set
        return rs;
    }

    /**
     * Method to populate mock Result set for Continent population
     * @return Mock Result set
     */
    ResultSet populationContinentResultSet()
    {
        //Initiate Mock Result set
        var rs = new MockResultSet("continentPopulation");

        //Add Mocked Population Columns
        rs.addColumn("Name",  new String[]{"Asia", "Europe", "North America", "Africa", "Oceania", "South America"});
        rs.addColumn("TotalPopulation",  new Long[]{35000000L, 125000000L, 40000000L, 4000000L, 180000000L, 90000000L});
        rs.addColumn("UrbanPopulation",  new Long[]{30000000L, 120000000L, 29000000L, 3000000L, 100000000L, 80000000L});
        rs.addColumn("RuralPopulation",  new Long[]{500000L, 5000000L, 11000000L, 1000000L, 80000000L, 10000000L});

        //Return Mock Result set
        return rs;
    }

    /**
     * Method to populate mock Result set for Language population report
     * @return Mock Result set
     */
    ResultSet populationLanguageResultSet()
    {
        //Initiate Mock Result set
        var rs = new MockResultSet("languagePopulation");

        //Add Mocked Population Columns
        rs.addColumn("Name",  new String[]{"English", "Arabic", "Chinese", "Hindi", "Spanish"});
        rs.addColumn("TotalPopulation",  new Long[]{35000000L, 125000000L, 40000000L, 4000000L, 180000000L});
        rs.addColumn("Percentage",  new Integer[]{21, 9, 30, 24, 14});

        //Return Mock Result set
        return rs;
    }
}
