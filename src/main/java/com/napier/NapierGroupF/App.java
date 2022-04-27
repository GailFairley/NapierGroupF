/**
 * Project Name: coursework
 * Package: com.napier.NapierGroupF
 * User Created: Josh McQueen
 * Date Created: 06/02/2022
 * Date Last Updated: 27/04/2022
 * File Description: The main java file which will connect to db and display all the relevant reports.
 **/

package com.napier.NapierGroupF;

//Imports
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


/**
 * Main class to connect to db and display reports
 */
public class App
{
    /**
     * The Main method of the program
     * @param args Arguments passed to the program
     */
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to world database
        //if no args given run with local host
        if (args.length < 1)
        {
            a.connect("localhost:33069", 3000);
        }
        else
        {
            //else use args given connection and timer
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        //generate reports
        a.getAndDisplayAllReports();

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connecting to MySql world db
     * @param loc - Specifies the Location to connect to
     */
    public void connect(String loc, int delay)
    {
        //Try loading db the driver
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        //Catch any errors
        catch (ClassNotFoundException e)
        {
            //Catch Drive error
            System.out.println("Error: Could not load SQL driver");
            System.exit(-1);
        }

        //Try Connecting for ten times
        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Debug: Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(delay);

                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + loc + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "world");
                System.out.println("Debug: Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                //Catch sql Connection or Query error
                System.out.println("Error: Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                //Catch interruption error
                System.out.println("Error: Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        //Check if connected
        if (con != null)
        {
            //Try disconnecting
            try
            {
                // Close connection
                con.close();
            }
            //Catch any errors
            catch (Exception e)
            {
                // Display error - why cannot close the connection
                System.out.println("Error: Error closing connection to database");
            }
        }
    }

    public void getAndDisplayAllReports()
    {
        String title;

        //Reports

        //Use Case 1
        //1
        title = ("1. All Countries organised by population Largest to Smallest.");
        //All countries organised by population Largest to Smallest
        ArrayList<Country> countriesOrganisedByPopulation = getCountriesOrganisedByPopulation();
        //Output the report as a markdown file
        outputCountries(countriesOrganisedByPopulation, "CountriesOrganisedByPopulation", title);

        //2
        title = ("2. All Countries Given Continent 'Europe' organised by population Largest to Smallest.");
        //All countries organised by population Largest to Smallest
        ArrayList<Country> countriesInAContinentOrganisedByPopulation = getCountriesInAContinentOrganisedByPopulation("Europe");
        //Output the report as a markdown file
        outputCountries(countriesInAContinentOrganisedByPopulation, "CountriesInAContinentOrganisedByPopulation", title);

        //3
        title = ("3. All Countries Given Region 'Eastern Europe' organised by population Largest to Smallest.");
        //All the countries in a region organised by largest population to smallest.
        ArrayList<Country> countriesInARegionOrganisedByPopulation = getCountriesInARegionOrganisedByPopulation("Eastern Europe");
        //Output the report as a markdown file
        outputCountries(countriesInARegionOrganisedByPopulation, "CountriesInARegionOrganisedByPopulation", title);

        //Use Case 2
        //4
        title = ("4. The top '100' populated countries in the world.");
        //The top N populated countries in the world where N is provided by the user.
        ArrayList<Country> getTopNCountries = getTopNCountries(100);
        //Output the report as a markdown file
        outputCountries(getTopNCountries, "TopNCountries", title);

        //5
        title = ("5. The top '20' populated countries in the world in the continent 'Europe'.");
        //The top N populated countries in a continent where N is provided by the user.
        ArrayList<Country> getTopNCountriesInAContinent = getTopNCountriesInAContinent(20, "Europe");
        //Output the report as a markdown file
        outputCountries(getTopNCountriesInAContinent, "TopNCountriesInAContinent", title);

        //6
        title = ("6. The top '10' populated countries in the world in the Region 'Eastern Europe'.");
        //The top N populated countries in a region where N is provided by the user.
        ArrayList<Country> getTopNCountriesInARegion = getTopNCountriesInARegion(10, "Eastern Europe");
        //Output the report as a markdown file
        outputCountries(getTopNCountriesInARegion, "TopNCountriesInARegion", title);

        //Use Case 3
        //7
        title = ("7. All the Cities in the World Organised by largest population to smallest.");
        //All the cities in the world organised by largest population to smallest.
        ArrayList<City> getCitiesOrganisedByPopulation = getCitiesOrganisedByPopulation();
        //Output the report as a markdown file
        outputCities(getCitiesOrganisedByPopulation, "AllCitiesInWorldOrganisedByPopulation", title, false);

        //8
        title = ("8. All the Cities in a Continent 'Asia' organised by largest population to smallest.");
        //All the cities in a continent organised by largest population to smallest.
        ArrayList<City> getCitiesInContinentOrganisedByPopulation = getCitiesInContinentOrganisedByPopulation("Asia");
        //Output the report as a markdown file
        outputCities(getCitiesInContinentOrganisedByPopulation, "AllCitiesInContinentOrganisedByPopulation", title, false);

        //9
        title = ("9. All the Cities in a Region 'Eastern Europe' organised by largest population to smallest.");
        //All the cities in a region organised by largest population to smallest.
        ArrayList<City> getCitiesInARegionOrganisedByPopulation = getCitiesInARegionOrganisedByPopulation("Eastern Europe");
        //Output the report as a markdown file
        outputCities(getCitiesInARegionOrganisedByPopulation, "AllCitiesInARegionOrganisedByPopulation", title, false);

        //10
        title = ("10. All the Cities in a Country 'China' organised by largest population to smallest.");
        //All the cities in a Country organised by largest population to smallest.
        ArrayList<City> getCitiesInCountryOrganisedByPopulation = getCitiesInCountryOrganisedByPopulation("China");
        //Output the report as a markdown file
        outputCities(getCitiesInCountryOrganisedByPopulation, "AllCitiesInCountryOrganisedByPopulation", title, false);

        //11
        title = ("11. All the Cities in a District 'Noord-Holland' organised by largest population to smallest.");
        //All the cities in a District organised by largest population to smallest.
        ArrayList<City> getCitiesInDistrictOrganisedByPopulation = getCitiesInDistrictOrganisedByPopulation("Noord-Holland");
        //Output the report as a markdown file
        outputCities(getCitiesInDistrictOrganisedByPopulation, "AllCitiesInDistrictOrganisedByPopulation", title, false);

        //Use Case 4
        //12
        title = ("12. The Top N '100' Cities in the World Organised by largest population to smallest.");
        //The top N populated cities in the world where N is provided by the user.
        ArrayList<City> getTopCitiesOrganisedByPopulation = getTopCitiesOrganisedByPopulation(100);
        //Output the report as a markdown file
        outputCities(getTopCitiesOrganisedByPopulation, "TopNCitiesInTheWorldOrganisedByPopulation", title, false);

        //13
        title = ("13. The Top N '100' Cities in a Continent 'Asia' organised by largest population to smallest.");
        //The top N populated cities in a continent where N is provided by the user.
        ArrayList<City> getTopCitiesInContinentOrganisedByPopulation = getTopCitiesInContinentOrganisedByPopulation("Asia", 100);
        //Output the report as a markdown file
        outputCities(getTopCitiesInContinentOrganisedByPopulation, "TopNCitiesInContinentOrganisedByPopulation", title, false);

        //14
        title = ("14. The Top N '50' Cities in a Region 'Eastern Europe' organised by largest population to smallest.");
        //The top N populated cities in a region where N is provided by the user.
        ArrayList<City> getTopCitiesInARegionOrganisedByPopulation = getTopCitiesInARegionOrganisedByPopulation("Eastern Europe", 50);
        //Output the report as a markdown file
        outputCities(getTopCitiesInARegionOrganisedByPopulation, "TopNCitiesInARegionOrganisedByPopulation", title, false);

        //15
        title = ("15. The Top N '50' Cities in a Country 'China' organised by largest population to smallest.");
        //The top N populated cities in a country where N is provided by the user.
        ArrayList<City> getTopCitiesInCountryOrganisedByPopulation = getTopCitiesInCountryOrganisedByPopulation("China", 50);
        //Output the report as a markdown file
        outputCities(getTopCitiesInCountryOrganisedByPopulation, "TopNCitiesInCountryOrganisedByPopulation", title, false);

        //16
        title = ("16. The Top N '5' Cities in a District 'Noord-Holland' organised by largest population to smallest.");
        //The top N populated cities in a district where N is provided by the user.
        ArrayList<City> getTopCitiesInDistrictOrganisedByPopulation = getTopCitiesInDistrictOrganisedByPopulation("Noord-Holland", 5);
        //Output the report as a markdown file
        outputCities(getTopCitiesInDistrictOrganisedByPopulation, "TopNCitiesInDistrictOrganisedByPopulation", title, false);

        //Use Case 5
        //17
        title = ("17. All the Capital Cities in the World Organised by largest population to smallest.");
        //All the Capital cities in the world organised by largest population to smallest.
        ArrayList<City> getCapitalCitiesOrganisedByPopulation = getCapitalCitiesOrganisedByPopulation();
        //Output the report as a markdown file
        outputCities(getCapitalCitiesOrganisedByPopulation, "AllCapitalCitiesInTheWorldOrganisedByPopulation", title, true);

        //18
        title = ("18. All the Capital Cities in a Continent 'Europe' organised by largest population to smallest.");
        //All the Capital cities in a continent organised by largest population to smallest.
        ArrayList<City> getCapitalCitiesInContinentOrganisedByPopulation = getCapitalCitiesInContinentOrganisedByPopulation("Europe");
        //Output the report as a markdown file
        outputCities(getCapitalCitiesInContinentOrganisedByPopulation, "AllCapitalCitiesInContinentOrganisedByPopulation", title, true);

        //19
        title = ("19. All the Capital Cities in a Region 'Western Europe' organised by largest population to smallest.");
        //All the Capital cities in a region organised by largest population to smallest.
        ArrayList<City> getCapitalCitiesInARegionOrganisedByPopulation = getCapitalCitiesInARegionOrganisedByPopulation("Western Europe");
        //Output the report as a markdown file
        outputCities(getCapitalCitiesInARegionOrganisedByPopulation, "AllCapitalCitiesInARegionOrganisedByPopulation", title, true);

        //Use Case 6
        //20
        title = ("20. The Top N '100' Capital Cities in the world organised by largest population to smallest.");
        //The top N populated Capital cities in the world where N is provided by the user.
        ArrayList<City> getTopCapitalCitiesOrganisedByPopulation = getTopCapitalCitiesOrganisedByPopulation( 100);
        //Output the report as a markdown file
        outputCities(getTopCapitalCitiesOrganisedByPopulation, "TopNCapitalCitiesInTheWorldOrganisedByPopulation", title, true);

        //21
        title = ("21. The Top N '20' Capital Cities in the Continent 'Europe' organised by largest population to smallest.");
        //The top N populated Capital cities in the continent where N is provided by the user.
        ArrayList<City> getTopCapitalCitiesInContinentOrganisedByPopulation = getTopCapitalCitiesInContinentOrganisedByPopulation(20, "Europe");
        //Output the report as a markdown file
        outputCities(getTopCapitalCitiesInContinentOrganisedByPopulation, "TopNCapitalCitiesInContinentOrganisedByPopulation", title, true);

        //22
        title = ("22. The top N '20' populated capital cities in a region 'Western Europe' where N is provided by the user.");
        //The top N populated Capital cities in the region where N is provided by the user.
        ArrayList<City> getTopCapitalCitiesInRegionOrganisedByPopulation = getTopCapitalCitiesInRegionOrganisedByPopulation(20, "Western Europe");
        //Output the report as a markdown file
        outputCities(getTopCapitalCitiesInRegionOrganisedByPopulation, "TopNCapitalCitiesInRegionOrganisedByPopulation", title, true);

        //Use Case 7
        //23
        title = ("23. The population of people living in cities, and people not living in cities in each continent.");
        //The population of people living in cities, and people not living in cities in each continent
        ArrayList<Population> getPopulationInCitiesAndNotInCitiesInContinents = getPopulationInCitiesAndNotInCitiesInContinents();
        //Output the report as a markdown file
        outputPopulation(getPopulationInCitiesAndNotInCitiesInContinents, "PopulationOfPeopleInCitiesAndNotInCitiesInContinents", title, "Continent", false);

        //24
        title = ("24. The population of people living in cities, and people not living in cities in each region.");
        //The population of people living in cities, and people not living in cities in each region
        ArrayList<Population> getPopulationInCitiesAndNotInCitiesInRegions = getPopulationInCitiesAndNotInCitiesInRegions();
        //Output the report as a markdown file
        outputPopulation(getPopulationInCitiesAndNotInCitiesInRegions, "PopulationOfPeopleInCitiesAndNotInCitiesInRegions", title, "Region", false);

        //25
        title = ("25. The population of people living in cities, and people not living in cities in each country.");
        //The population of people living in cities, and people not living in cities in each country
        ArrayList<Population> getPopulationInCitiesAndNotInCitiesInCountries = getPopulationInCitiesAndNotInCitiesInCountries();
        //Output the report as a markdown file
        outputPopulation(getPopulationInCitiesAndNotInCitiesInCountries, "PopulationOfPeopleInCitiesAndNotInCitiesInCountries", title, "Country", false);

        //Use Case 8
        //26
        title = ("26. The total Population of the world.");
        //The total population of the world
        ArrayList<Population> getTotalPopulationOfWorld = getTotalPopulationOfWorld();
        //Output the report as a markdown file
        outputPopulation(getTotalPopulationOfWorld, "TotalPopulationOfWorld", title, "World", true);

        //27
        title = ("27. The total Population of a given continent.");
        //The total population of a given continent
        ArrayList<Population> getTotalPopulationOfContinent = getTotalPopulationOfContinent("Europe");
        //Output the report as a markdown file
        outputPopulation(getTotalPopulationOfContinent, "TotalPopulationOfContinent", title, "Continent", true);

        //28
        title = ("28. The total Population of a given region.");
        //The total population of a given region
        ArrayList<Population> getTotalPopulationOfRegion = getTotalPopulationOfRegion("Western Europe");
        //Output the report as a markdown file
        outputPopulation(getTotalPopulationOfRegion, "TotalPopulationOfRegion", title, "Region", true);

        //29
        title = ("29. The total Population of a given country.");
        //The total population of a given country
        ArrayList<Population> getTotalPopulationOfCountry = getTotalPopulationOfCountry("China");
        //Output the report as a markdown file
        outputPopulation(getTotalPopulationOfCountry, "TotalPopulationOfCountry", title, "Country", true);

        //30
        title = ("30. The total Population of a given city.");
        //The total population of a given city
        ArrayList<Population> getTotalPopulationOfCity = getTotalPopulationOfCity("Glasgow");
        //Output the report as a markdown file
        outputPopulation(getTotalPopulationOfCity, "TotalPopulationOfCity", title, "City", true);

        //31
        title = ("31. The total Population of a given district.");
        //The total population of a given district
        ArrayList<Population> getTotalPopulationOfDistrict = getTotalPopulationOfDistrict("Ontario");
        //Output the report as a markdown file
        outputPopulation(getTotalPopulationOfDistrict, "TotalPopulationOfDistrict", title, "District", true);

        //Use Case 9
        //32
        title = ("32. A Report for the number of people who speak the following languages (Chinese, English, Hindi, Spanish, Arabic) from the greatest number to smallest, including the percentage of the world population.");
        //The number of people who speak the following languages (Chinese, English, Hindi, Spanish, Arabic) from the greatest number to smallest, including the percentage of the world population
        ArrayList<Population> getPopulationOfLanguages = getPopulationOfLanguages();
        //Output the report as a markdown file
        outputPopulation(getPopulationOfLanguages, "LanguagesPopulationReport", title, "Language", false);

        //End
    }

    /**
     * Execute the given SQL query
     * @param sql The SQL query to execute
     * @return ResultSet The Result set of the query
     */
    public ResultSet executeQuery(String sql)
    {
        //Try executing the SQL query
        try
        {
            //Create an SQL Statement
            Statement stmnt = con.createStatement();

            //Validate Sql not null or empty
            validateString(sql);

            //Execute the SQL statement and return the result
            return stmnt.executeQuery(sql);
        }
        //Catch the SQLException
        catch (SQLException e)
        {
            //Print Exception error message
            System.out.println(e.getMessage());
            System.out.println("Error: Failed to Execute Query!");
            return null;
        }
    }

    /**
     * Retrieve from the ResultSet and Map to Country Variables
     * @param sql The given SQL statement to be executed
     * @return countries ArrayList of Countries
     */
    public ArrayList<Country> getCountries(String sql)
    {
        //Get the ResultSet of the SQL query
        ResultSet rset = executeQuery(sql);

        //initiate new Countries list
        ArrayList<Country> countries = new ArrayList<>();

        //Map Countries
        mapCountries(countries, rset);

        //Return Countries ArrayList
        return countries;
    }

    /**
     * Map Result set Sql Columns to Country Variables
     * @param countries ArrayList of Countries to Map
     * @param rset The ResultSet of the SQL query
     */
    public void mapCountries (ArrayList<Country> countries, ResultSet rset)
    {
        //Try Map the ResultSet to Country Variables
        try
        {
            //While there is a Row
            while (rset.next())
            {
                //Get Each Country information and add it to countries arraylist
                //Initialize Country
                Country country = new Country();

                //Try Finding the column and if not found set to -
                country.Code = findColumn(rset, "Code") ? rset.getString("Code") : "-";
                country.Name = findColumn(rset, "Name") ? rset.getString("Name") : "-";
                country.Continent = findColumn(rset, "Continent") ? rset.getString("Continent") : "-";
                country.Region = findColumn(rset, "Region") ? rset.getString("Region") : "-";
                country.Population = findColumn(rset, "Population") ? new Population(rset.getLong("Population")) : new Population(0);
                country.Capital = findColumn(rset, "Capital") ? new City(rset.getString("Capital")) : new City("-");

                //Add Country to Countries ArrayList
                countries.add(country);
            }
        }
        //Catch the SQLException + NullPointerException
        catch (SQLException | NullPointerException e)
        {
            //Print Exception error message
            System.out.println(e.getMessage());
            System.out.println("Error: Failed to Map Countries!");
        }
    }

    /**
     * Retrieve from the ResultSet and Map to City Variables
     * @param sql The given SQL statement to be executed
     * @return cities All Retrieved Cities
     */
    public ArrayList<City> getCities(String sql)
    {
        //Get the ResultSet of the SQL query
        ResultSet rset = executeQuery(sql);

        //Extract each City information
        ArrayList<City> cities = new ArrayList<>();

        //Map Cities
        mapCities(rset, cities);

        //Return cities arraylist
        return cities;
    }

    /**
     * Map Result set Sql Columns to City Variables
     * @param rset Result set of the SQL query
     * @param cities Cities to Map to
     */
    public void mapCities(ResultSet rset, ArrayList<City> cities)
    {
        //Try Map the ResultSet to City Variables
        try
        {
            //While there is a Row
            while (rset.next())
            {
                //Get Each City information and add it to cities arraylist
                City city = new City();

                //Try Finding the column and if not found set to -
                city.Country = findColumn(rset,"Country") ? new Country(rset.getString("Country")) : new Country("-");//Country Name
                city.Country.Continent = findColumn(rset,"Continent")  ? rset.getString("Continent") : "";//Continent
                city.Country.Region = findColumn(rset,"Region") ? rset.getString("Region") : "";//Region
                city.Name = findColumn(rset,"Name") ? rset.getString("Name") : "-";//City Name
                city.District = findColumn(rset,"District") ? rset.getString("District") : "-";//District
                city.Population = findColumn(rset,"Population") ? new Population(rset.getLong("Population")) : new Population();//Population

                //Add City to cities arraylist
                cities.add(city);
            }
        }
        //Catch any SQL exceptions
        catch (SQLException | NullPointerException e)
        {
            //Print Exception error message
            System.out.println(e.getMessage());
            System.out.println("Error: Failed to Map Cities!");
        }
    }

    /**
     * Retrieve from the ResultSet and Map to Population Variables
     * @param sql The given SQL statement to be executed
     * @return population the retrieved population arraylist
     */
    public ArrayList<Population> getPopulation(String sql)
    {
        //Validate Sql not null or empty
        validateString(sql);

        //Get the ResultSet of the SQL query
        ResultSet rset = executeQuery(sql);

        //Extract each Population information
        ArrayList<Population> populations = new ArrayList<>();

        //Map Cities
        mapPopulation(rset, populations);

        //Return population arraylist
        return populations;
    }

    /**
     * Map Result set Sql Columns to Populations Variables
     * @param rset Result set of the SQL query
     * @param populations Populations arraylist to Map to
     */
    public void mapPopulation(ResultSet rset, ArrayList<Population> populations)
    {
        //Try Map the ResultSet to Population Variables
        try
        {
            //While there is a Row
            while (rset.next())
            {
                //Get Each Population information and add it to populations arraylist
                Population population = new Population();

                //Try Finding the column and if not found set to default
                population.Name = findColumn(rset,"Name") ? rset.getString("Name") : "-";//Country/Region/Continent Name
                population.TotalPopulation = findColumn(rset, "TotalPopulation") ? rset.getLong("TotalPopulation") : 0;//Total Population
                population.UrbanPopulation = findColumn(rset, "UrbanPopulation") ? rset.getLong("UrbanPopulation") : 0;//Urban Population
                population.UrbanPopulationPercentage = findColumn(rset, "UrbanPopulationPercentage") ? rset.getDouble("UrbanPopulationPercentage") : 0;//Urban Population Percentage
                population.RuralPopulation = findColumn(rset, "RuralPopulation") ? rset.getLong("RuralPopulation") : 0;//Rural Population
                population.RuralPopulationPercentage = findColumn(rset, "RuralPopulationPercentage") ? rset.getDouble("RuralPopulationPercentage") : 0;//Rural Population Percentage
                population.PopulationPercentage = findColumn(rset, "PopulationPercentage") ? rset.getDouble("PopulationPercentage") : 0;//Population Percentage

                //Add population to populations arraylist
                populations.add(population);
            }
        }
        //Catch any SQL exceptions
        catch (SQLException | NullPointerException e)
        {
            //Print Exception error message
            System.out.println(e.getMessage());
            System.out.println("Error: Failed to Map Population!");
        }
    }

    /**
     * Validate the Given String Statement check if it's not null or empty
     * @param string The String to validate
     */
    public void validateString(String string)
    {
        //Try to Validate String not null or empty
        try
        {
            //Check if string is not null or Empty
            if (string == null || string.isEmpty())
            {
                throw new InvalidStringException("Error: Empty or Null String provided!", new Exception());
            }
        }
        //Catch InvalidStringException
        catch (InvalidStringException e)
        {
            //Print Exception error message
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to Check if a given Column exists in the Result set
     * @param rs The Result set
     * @param column The column name to find
     * @return boolean True if found else False
     */
    public boolean findColumn(ResultSet rs, String column)
    {
        //Check if column exists in the Result set
        try
        {
            //Check if Column string Valid
            if (column == null || column.isEmpty())
            {
                throw new InvalidStringException("Empty or Null Column Name provided!", new Exception());
            }

            //Find the column in the Result set
            rs.findColumn(column);
            //Return True if found
            return true;
        }
        //Catch and Ignore SQL + InvalidString Exception
        catch (SQLException | InvalidStringException ignored) {}
        //If column not found return false
        return false;
    }

    /**
     * Get report for All countries organised by population Largest to Smallest.
     * @return countries List of all Countries retrieved from the db
     */
    public ArrayList<Country> getCountriesOrganisedByPopulation()
    {
        //Add string for the SQL statement
        String sql = "SELECT Code, c.Name AS Name, Continent, Region, c.Population AS Population, cp.Name AS Capital "
                   + "From country c "
                   + "JOIN city cp on c.Capital = cp.ID "
                   + "Order By Population DESC";

        //Get All countries organised by population Largest to Smallest
        return getCountries(sql);
    }

    /**
     * Get report for All the countries in a continent organised by largest population to smallest.
     * @return countries List of all Countries retrieved from the db
     * @param continent The Continent to get Countries for
     */
    public ArrayList<Country> getCountriesInAContinentOrganisedByPopulation(String continent)
    {
        //Add string for the SQL statement
        String sql = "SELECT Code, country.Name AS Name, Continent, Region, country.Population AS Population, city.Name AS Capital "
                   + "From country "
                   + "JOIN city on country.Capital = city.ID "
                   + "where Continent = '" + continent + "' "//Given Continent
                   + "Order By country.Population DESC ";

        //Get All the countries in a continent organised by largest population to smallest
        return getCountries(sql);
    }

    /**
     * Get report for All the countries in a Region organised by largest population to smallest.
     * @return countries List of all Countries retrieved from the db
     * @param region The Region to Filter Countries for
     */
    public ArrayList<Country> getCountriesInARegionOrganisedByPopulation(String region)
    {
        //Add string for the SQL statement
        String sql = "SELECT Code, country.Name AS Name, Continent, Region, country.Population AS Population, city.Name AS Capital "
                   + "From country "
                   + "JOIN city on country.Capital = city.ID "
                   + "WHERE Region = '" + region + "' "//Given Region
                   + "Order By country.Population DESC ";

        //All the countries in a region organised by largest population to smallest.
        return getCountries(sql);
    }

    /**
     * Get report for The top N populated countries in the world where N is provided by the user.
     * @return countries List of all Countries retrieved from the db
     * @param n The limit given by user
     */
    public ArrayList<Country> getTopNCountries(int n)
    {
        //Add string for the SQL statement
        String sql = "SELECT Code, country.Name AS Name, Continent, Region, country.Population AS Population, city.Name AS Capital "
                   + "From country "
                   + "JOIN city on country.Capital = city.ID "
                   + "Order By country.Population DESC "
                   + "LIMIT " + n;//Given number to limit by

        //The top N populated countries in the world where N is provided by the user.
        return getCountries(sql);
    }

    /**
     * Get report for The top N populated countries in the world in a given continent where N and continent is provided by the user.
     * @return countries List of all Countries retrieved from the db
     * @param n the limit given by user
     * @param continent the continent to filter the Countries by
     */
    public ArrayList<Country> getTopNCountriesInAContinent(int n, String continent)
    {
        //Add string for the SQL statement
        String sql = "SELECT Code, country.Name AS Name, Continent, Region, country.Population AS Population, city.Name AS Capital "
                   + "From country "
                   + "JOIN city on country.Capital = city.ID "
                   + "where Continent = '" + continent + "' "//Given Continent
                   + "Order By country.Population DESC "
                   + "LIMIT " + n;//Given number to limit by

        //The top N populated countries in a continent where N is provided by the user.
        return getCountries(sql);
    }

    /**
     * Get report for The top N populated countries in the world in a given Region where N and region is provided by the user.
     * @return countries List of all Countries retrieved from the db
     * @param n the limit given by user
     * @param region the Region to filter the Countries by
     */
    public ArrayList<Country> getTopNCountriesInARegion(int n, String region)
    {
        //Add string for the SQL statement
        String sql = "SELECT Code, country.Name AS Name, Continent, Region, country.Population AS Population, city.Name AS Capital "
                   + "From country "
                   + "JOIN city on country.Capital = city.ID "
                   + "where Region = '" + region + "' "//Given region
                   + "Order By country.Population DESC "
                   + "LIMIT " + n;//Given number to limit by

        //The top N populated countries in a region where N is provided by the user.
        return getCountries(sql);
    }

    /**
     * Get report for All the cities in the world organised by largest population to smallest.
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getCitiesOrganisedByPopulation()
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "Order By city.Population DESC ";

        //All the cities in the world organised by largest population to smallest.
        return getCities(sql);
    }

    /**
     * Get report for All the cities in a continent organised by largest population to smallest.
     * @param continent The continent to filter the Cities by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getCitiesInContinentOrganisedByPopulation(String continent)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population, country.Continent AS Continent, country.Region AS Region "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "WHERE country.Continent = '" + continent + "' " // Given continent
                   + "Order By city.Population DESC ";

        //All the cities in a continent organised by largest population to smallest.
        return getCities(sql);
    }

    /**
     * Get report for All the cities in a region organised by largest population to smallest.
     * @param region The Country Region to filter the Cities by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getCitiesInARegionOrganisedByPopulation(String region)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population, country.Region AS Region, country.Continent AS Continent "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "WHERE country.Region = '" + region + "' " // Given region
                   + "Order By city.Population DESC ";

        //All the cities in a region organised by largest population to smallest.
        return getCities(sql);
    }

    /**
     * Get Report of All the cities in a Country organised by largest population to smallest.
     * @param country The Country to filter the Cities by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getCitiesInCountryOrganisedByPopulation(String country)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "WHERE country.Name = '" + country + "' " // Given Country
                   + "Order By city.Population DESC ";

        //All the cities in a Country organised by largest population to smallest.
        return getCities(sql);
    }

    /**
     * Get Report of All the cities in a district organised by largest population to smallest
     * @param district The District to filter the Cities by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getCitiesInDistrictOrganisedByPopulation(String district)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "WHERE District = '" + district + "' " // Given District
                   + "Order By city.Population DESC ";

        //All the cities in a District organised by largest population to smallest.
        return getCities(sql);
    }

    /**
     * Get report for The top N populated cities in the world where N is provided by the user.
     * @param n Number to limit the result by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getTopCitiesOrganisedByPopulation(int n)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "Order By city.Population DESC "
                   + "LIMIT " + n ; // Limit the Results by N

        //The top N populated cities in the world where N is provided by the user.
        return getCities(sql);
    }

    /**
     * Get report for The top N populated cities in a continent where N is provided by the user.
     * @param continent The continent to filter the Cities by
     * @param n Number to limit the result by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getTopCitiesInContinentOrganisedByPopulation(String continent, int n)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population, country.Continent AS Continent "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "WHERE country.Continent = '" + continent + "' " // Given continent
                   + "Order By city.Population DESC "
                   + "LIMIT " + n ; // Limit the Results by N

        //The top N populated cities in a continent where N is provided by the user.
        return getCities(sql);
    }

    /**
     * Get report for The top N populated cities in a region where N is provided by the user.
     * @param region The Country Region to filter the Cities by
     * @param n Number to limit the result by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getTopCitiesInARegionOrganisedByPopulation(String region, int n)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population, country.Region AS Region "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "WHERE country.Region = '" + region + "' " // Given region
                   + "Order By city.Population DESC "
                   + "LIMIT " + n ; // Limit the Results by N

        //The top N populated cities in a region where N is provided by the user.
        return getCities(sql);
    }

    /**
     * Get Report of The top N populated cities in a country where N is provided by the user.
     * @param country The Country to filter the Cities by
     * @param n Number to limit the result by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getTopCitiesInCountryOrganisedByPopulation(String country, int n)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "WHERE country.Name = '" + country + "' " // Given Country
                   + "Order By city.Population DESC "
                   + "LIMIT " + n ; // Limit the Results by N

        //The top N populated cities in a country where N is provided by the user.
        return getCities(sql);
    }

    /**
     * Get Report of The top N populated cities in a district where N is provided by the user.
     * @param district The District to filter the Cities by
     * @param n Number to limit the result by
     * @return cities List of all Cities retrieved from the db
     */
    public ArrayList<City> getTopCitiesInDistrictOrganisedByPopulation(String district, int n)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population "
                   + "From city "
                   + "JOIN country on city.CountryCode = country.code "
                   + "WHERE District = '" + district + "' " // Given District
                   + "Order By city.Population DESC "
                   + "LIMIT " + n ;  // Limit the Results by N

        //The top N populated cities in a district where N is provided by the user.
        return getCities(sql);
    }

    /**
     * Get report for All the capital cities in the world organised by largest population to smallest.
     * @return cities List of all Capital Cities retrieved from the db
     */
    public ArrayList<City> getCapitalCitiesOrganisedByPopulation()
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population "
                   + "From city "
                   + "JOIN country on city.id = country.capital "
                   + "Order By city.Population DESC ";

        //All the capital cities in the world organised by largest population to smallest.
        return getCities(sql);
    }

    /**
     * Get report for All the capital cities in a continent organised by largest population to smallest.
     * @param continent The continent to filter the Capital Cities by
     * @return cities List of all Capital Cities retrieved from the db
     */
    public ArrayList<City> getCapitalCitiesInContinentOrganisedByPopulation(String continent)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population, country.Continent AS Continent "
                   + "From city "
                   + "JOIN country on city.id = country.capital "
                   + "WHERE country.Continent = '" + continent + "' " // Given continent
                   + "Order By city.Population DESC ";

        //All the capital cities in a continent organised by largest population to smallest.
        return getCities(sql);
    }

    /**
     * Get report for All the capital cities in a region organised by largest to smallest.
     * @param region The Country Region to filter the Capital Cities by
     * @return cities List of all Capital Cities retrieved from the db
     */
    public ArrayList<City> getCapitalCitiesInARegionOrganisedByPopulation(String region)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.Name AS Name, country.name as Country, District, city.Population AS Population, country.Region AS Region "
                + "From city "
                + "JOIN country on city.id = country.capital "
                + "WHERE country.Region = '" + region + "' " // Given region
                + "Order By city.Population DESC ";

        //All the capital cities in a region organised by largest to smallest.
        return getCities(sql);
    }

    /**
     * Get Report of The top N capital cities in the world where N is provided by the user.
     * @param n determines the number to limit number of rows in results
     * @return list of retrieved cities from the database
     */
    public ArrayList<City> getTopCapitalCitiesOrganisedByPopulation(int n)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.name as Name, country.name as Country, city.population AS Population, District "
                   + "From city "
                   + "JOIN country on city.id = country.capital "
                   + "Order By city.Population DESC "
                   + "Limit " + n ;  // Limit the Results by N

        //Get Report of The top N capital cities in the world where N is provided by the user.
        return getCities(sql);
    }

    /**
     * The top N populated capital cities in a continent where N is provided by the user.
     * @param n determines the number to limit number of rows in results
     * @param continent the continent to filter the capital cities by
     * @return list of retrieved cities from the database
     */
    public ArrayList<City> getTopCapitalCitiesInContinentOrganisedByPopulation(int n, String continent)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.name as Name, country.name as Country, city.population, District, country.Continent AS Continent "
                   + "From city "
                   + "JOIN country on city.id = country.capital "
                   + "WHERE continent = '" + continent + "' " // the continent to filter by
                   + "Order By city.Population DESC "
                   + "Limit " + n ;  // Limit the Results by N

        //Get Report of The top N populated capital cities in a continent where N is provided by the user.
        return getCities(sql);
    }

    /**
     * The top N populated capital cities in a region where N is provided by the user.
     * @param n determines the number to limit number of rows in results
     * @param region the Region to filter the capital cities by
     * @return list of retrieved cities from the database
     */
    public ArrayList<City> getTopCapitalCitiesInRegionOrganisedByPopulation(int n, String region)
    {
        //Add string for the SQL statement
        String sql = "SELECT city.name as Name, country.name as Country, city.population, District, country.Region AS Region "
                   + "From city "
                   + "JOIN country on city.id = country.capital "
                   + "WHERE country.Region = '" + region + "' " // the region to filter by
                   + "Order By city.Population DESC "
                   + "Limit " + n ;  // Limit the Results by N

        //Get Report of The top N populated capital cities in a region where N is provided by the user.
        return getCities(sql);
    }

    /**
     * The population of people living in cities, and people not living in cities in each continent.
     * @return list of retrieved Population report from the database
     */
    public ArrayList<Population> getPopulationInCitiesAndNotInCitiesInContinents()
    {
        //Add string for the SQL statement
        String sql = "SELECT Name, TotalPopulation, UrbanPopulation, UrbanPopulationPercentage, RuralPopulation, 100 - UrbanPopulationPercentage AS RuralPopulationPercentage "
                   + "FROM "
                   + "(SELECT tp.continent AS Name, TotalPopulation, UrbanPopulation,Round(UrbanPopulation/TotalPopulation * 100,2) AS UrbanPopulationPercentage, TotalPopulation - UrbanPopulation AS RuralPopulation "
                   + "FROM "
                   + "(SELECT continent, SUM(population) AS TotalPopulation "
                   + "FROM country "
                   + "GROUP BY continent) AS tp "
                   + "JOIN "
                   + "(SELECT continent, SUM(city.population) AS UrbanPopulation "
                   + "FROM country "
                   + "JOIN city on country.code = city.countrycode "
                   + "GROUP BY Continent) AS up "
                   + "ON tp.Continent = up.Continent ) AS continentpopdivide";

        //Get Report of The population of people living in cities, and people not living in cities in each continent.
        return getPopulation(sql);
    }

    /**
     * The population of people living in cities, and people not living in cities in each region.
     * @return list of retrieved Population report from the database
     */
    public ArrayList<Population> getPopulationInCitiesAndNotInCitiesInRegions()
    {
        //Add string for the SQL statement
        String sql = "SELECT Name, TotalPopulation, UrbanPopulation, UrbanPopulationPercentage, RuralPopulation, 100 - UrbanPopulationPercentage AS RuralPopulationPercentage "
                   + "FROM "
                   + "(SELECT tp.region AS Name, TotalPopulation, UrbanPopulation,Round(UrbanPopulation/TotalPopulation * 100,2) AS UrbanPopulationPercentage, TotalPopulation - UrbanPopulation AS RuralPopulation "
                   + "FROM "
                   + "(SELECT region, SUM(population) AS TotalPopulation "
                   + "FROM country "
                   + "GROUP BY region) AS tp "
                   + "JOIN "
                   + "(SELECT region, SUM(city.population) AS UrbanPopulation "
                   + "FROM country "
                   + "JOIN city on country.code = city.countrycode "
                   + "GROUP BY region) AS up "
                   + "ON tp.region = up.region ) AS continentpopdivide";

        //Get Report of The population of people living in cities, and people not living in cities in each region.
        return getPopulation(sql);
    }

    /**
     * The population of people living in cities, and people not living in cities in each country.
     * @return list of retrieved Population report from the database
     */
    public ArrayList<Population> getPopulationInCitiesAndNotInCitiesInCountries()
    {
        //Add string for the SQL statement
        String sql = "SELECT Name, TotalPopulation, UrbanPopulation, UrbanPopulationPercentage, RuralPopulation, 100 - UrbanPopulationPercentage AS RuralPopulationPercentage "
                   + "FROM "
                   + "(SELECT tp.Name AS Name, TotalPopulation, UrbanPopulation,Round(UrbanPopulation/TotalPopulation * 100,2) AS UrbanPopulationPercentage, TotalPopulation - UrbanPopulation AS RuralPopulation "
                   + "FROM "
                   + "(SELECT country.Name, SUM(population) AS TotalPopulation "
                   + "FROM country "
                   + "GROUP BY country.Name) AS tp "
                   + "JOIN "
                   + "(SELECT country.Name, SUM(city.population) AS UrbanPopulation "
                   + "FROM country "
                   + "JOIN city on country.code = city.countrycode "
                   + "GROUP BY country.Name) AS up "
                   + "ON tp.Name = up.Name ) AS continentpopdivide";

        //Get Report of The population of people living in cities, and people not living in cities in each country.
        return getPopulation(sql);
    }

    /**
     * A Report for the number of people who speak the following languages (Chinese, English, Hindi, Spanish, Arabic) from the greatest number to smallest, including the percentage of the world population.
     * @return list of retrieved Population report from the database
     */
    public ArrayList<Population> getPopulationOfLanguages()
    {
        //Add string for the SQL statement
        String sql = "SELECT Name, TotalPopulation, Round(TotalPopulation/total_population * 100,2) AS PopulationPercentage "
                   + "FROM "
                   + "    (SELECT Language AS Name, Round(SUM(people_speaking),0) AS TotalPopulation "
                   + "    FROM "
                   + "        (SELECT language, population / Percentage AS people_speaking "
                   + "            FROM countrylanguage "
                   + "            JOIN country ON countrylanguage.CountryCode = country.Code "
                   + "            WHERE Language IN ('Hindi','English','Spanish','Chinese','Arabic') "
                   + "        ) AS peeps_speak "
                   + "    GROUP BY language "
                   + "    ) AS ts "
                   + "JOIN ( select sum(population) AS total_population FROM country) as tp "
                   + "ORDER BY TotalPopulation desc";

        //Get population language Report
        return getPopulation(sql);
    }

    /**
     * Get Total Population of the world
     * @return a population object with the total population of the world
     */
    public ArrayList<Population> getTotalPopulationOfWorld()
    {
        //Add string for the SQL statement
        String sql = "SELECT SUM(population) AS TotalPopulation "
                   + "FROM country";

        //Get population language Report
        return getPopulation(sql);
    }

    /**
     * Get total population of a given continent
     * @param continent the continent to get the population of
     */
    public ArrayList<Population> getTotalPopulationOfContinent(String continent)
    {
        //Add string for the SQL statement
        String sql = "SELECT Continent AS Name, SUM(population) AS TotalPopulation "
                   + "FROM country "
                   + "WHERE Continent = '" + continent + "' "
                   + "GROUP BY Continent";

        //Get population language Report
        return getPopulation(sql);
    }

    /**
     * Get total population of a given country
     * @param country name of the country to get population of
     */
    public ArrayList<Population> getTotalPopulationOfCountry(String country)
    {
        //Add string for the SQL statement
        String sql = "SELECT Name, population AS TotalPopulation "
                   + "FROM country "
                   + "WHERE Name = '" + country + "'";

        //Get population language Report
        return getPopulation(sql);
    }

    /**
     * Get total population of a given city
     * @param city The city to get the population of
     */
    public ArrayList<Population> getTotalPopulationOfCity(String city)
    {
        //Add string for the SQL statement
        String sql = "SELECT Name, population AS TotalPopulation "
                   + "FROM city "
                   + "WHERE Name = '" + city + "'";

        //Get population language Report
        return getPopulation(sql);
    }

    /**
     * Get total population of a given District
     * @param district The district to get the population of
     */
    public ArrayList<Population> getTotalPopulationOfDistrict(String district)
    {
        //Add string for the SQL statement
        String sql = "SELECT District AS Name, SUM(population) AS TotalPopulation "
                   + "FROM city "
                   + "WHERE District = '" + district + "' "
                   + "GROUP BY District";

        //Get population language Report
        return getPopulation(sql);
    }

    /**
     * Get total population of a given region
     * @param region The region to get the population of
     */
    public ArrayList<Population> getTotalPopulationOfRegion(String region)
    {
        //Add string for the SQL statement
        String sql = "SELECT Region AS Name, SUM(population) AS TotalPopulation "
                   + "FROM country "
                   + "WHERE Region = '" + region + "' "
                   + "GROUP BY Region";

        //Get population language Report
        return getPopulation(sql);
    }

    /**
     * Outputs Countries report to Markdown file
     * @param filename The name of the file to output to
     * @param countries The list of countries to Display
     * @param title The title of the report
     */
    public void outputCountries(ArrayList<Country> countries, String filename, String title)
    {
        // Check countries list is not null or empty
        if (countries == null || countries.isEmpty())
        {
            System.out.println("Error: Null or Empty Countries List provided!");
            return;
        }

        //initiate String builder
        StringBuilder sb = new StringBuilder();

        // Print header
        sb.append("# " + ((title != null && !title.isEmpty()) ? title : "Country Report") + "\n");
        sb.append("\n| Code | Name | Continent | Region | Population | Capital |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- |\r\n");

        // Loop over all countries in the list
        for (Country c : countries)
        {
            // Continue if this country is null
            if (c == null) continue;

            // Add the Country variables to the StringBuilder
            sb.append("| " + c.Code + " | " + c.Name + " | " + c.Continent + " | " + c.Region + " | " + (c.Population != null ? c.Population.TotalPopulation : "-") + " | " + (c.Capital != null ? c.Capital.Name : "-") + " |\r\n");
        }
        //Try creating reports directory and write string reader to file
        writeToFile(filename, sb);
    }

    /**
     * Outputs Cities report to Markdown
     * @param filename The name of the file to output to
     * @param cities The list of cities to Display
     * @param title The title of the report
     */
    public void outputCities(ArrayList<City> cities, String filename, String title, boolean isCapital)
    {
        // Check cities is not null
        if (cities == null || cities.isEmpty())
        {
            System.out.println("Error: Null or Empty Cities List provided!");
            return;
        }

        //initiate String builder
        StringBuilder sb = new StringBuilder();

        String name = (isCapital ? "Capital City" : "City");

        // Print header
        sb.append("# " + ((title != null && !title.isEmpty()) ? title : "City Report") + "\n");
        sb.append("\n| " + name + " | Country |" + (isCapital ? "" : " District ") + "| Population |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        // Loop over all cities in the list
        for (City c : cities)
        {
            // Continue if this city is null
            if (c == null) continue;

            // Add the City variable to the StringBuilder
            sb.append("| " + c.Name + " | " + (c.Country != null ? c.Country.Name : "-") + " | " + (isCapital ? "" : (c.District + " | ")) + (c.Population != null ? c.Population.TotalPopulation : "-") + " |\r\n");
        }
        //Try creating reports directory and write string reader to file
        writeToFile(filename, sb);
    }

    /**
     * Outputs Populations report to Markdown
     * @param filename The name of the file to output to
     * @param populations The list of populations to Display
     * @param title The title of the report
     */
    public void outputPopulation(ArrayList<Population> populations, String filename, String title, String populationType, boolean showOnlyTotalPopulation)
    {
        // Check populations ArrayList is not null
        if (populations == null || populations.isEmpty())
        {
            System.out.println("Error: Null or Empty Populations List provided!");
            return;
        }

        //initiate String builder
        StringBuilder sb = new StringBuilder();

        //Initialize row string
        String row;

        // Print header
        sb.append("# " + ((title != null && !title.isEmpty()) ? title : "Population Report") + "\n");

        //If population type is Language we want different headers
        if (populationType.equals("Language"))
        {
            sb.append("\n| " + populationType + " | Total Population | Population Percentage |\r\n");
            sb.append("| --- | --- | --- |\r\n");
        }
        else if (showOnlyTotalPopulation)
        {
            sb.append("\n| " + populationType + " | Total Population |\r\n");
            sb.append("| --- | --- |\r\n");
        }
        else
        {
            sb.append("\n| " + populationType + " | Total Population | Urban Population | Urban Population Percentage | Rural Population | Rural Population Percentage |\r\n");
            sb.append("| --- | --- | --- | --- | --- | --- |\r\n");
        }

        // Loop over all populations in the list
        for (Population p : populations)
        {
            // Continue if this population entry is null
            if (p == null) continue;

            //If population type is language we want different column name
            row = (populationType.equals("Language")) ? ("| " + p.Name + " | " + (p.TotalPopulation != 0 ? p.TotalPopulation : "-") + " | " + p.PopulationPercentage + "%" + " |\r\n")
                    : (showOnlyTotalPopulation) ? ("| " + p.Name + " | " + (p.TotalPopulation != 0 ? p.TotalPopulation : "-") + " |\r\n")
                    : ("| " + p.Name + " | " + (p.TotalPopulation != 0 ? p.TotalPopulation : "-") + " | " + (p.UrbanPopulation != 0 ? p.UrbanPopulation : "-") + " | " + (p.UrbanPopulationPercentage != 0 ? p.UrbanPopulationPercentage + "%" : "-") + " | " +  (p.RuralPopulation != 0 ? p.RuralPopulation : "-") + " | " +  (p.RuralPopulationPercentage != 0 ? p.RuralPopulationPercentage + "%" : "-") + "|\r\n");

            // Add the Population variable to the StringBuilder
            sb.append(row);
        }
        //Try creating reports directory and write string reader to file
        writeToFile(filename, sb);
    }

    /**
     * Create reports directory and write string reader to file
     * @param filename The name of the file to output to
     * @param sb The string reader to write to file
     */
    public void writeToFile(String filename, StringBuilder sb)
    {
        //Try creating reports directory and write string reader to file
        try
        {
            new File("./reports/").mkdir();
            //validate file name
            validateString(filename);
            //Create new Writer file with the filename provided
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/" + filename + ".md"));
            //Terminate if the string builder is null
            if (sb == null)
            {
                writer.close();
                System.out.println("Error: Could not write to file!");
                return;
            }
            //Write the string builder to the file
            writer.write(sb.toString());
            //Close the writer
            writer.close();
        }
        //Catch any IO errors
        catch (IOException | NullPointerException e)
        {
            e.printStackTrace();
            System.out.println("Error: Could not write to file!");
        }
    }
}