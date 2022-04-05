/**
 * Project Name: coursework
 * Package: com.napier.NapierGroupF
 * User Created: Josh McQueen
 * Date Created: 06/02/2022
 * Date Last Updated: 21/03/2022
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
        outputCities(getCitiesOrganisedByPopulation, "AllCitiesInWorldOrganisedByPopulation", title);

        //8
        title = ("8. All the Cities in a Continent 'Asia' organised by largest population to smallest.");
        //All the cities in a continent organised by largest population to smallest.
        ArrayList<City> getCitiesInContinentOrganisedByPopulation = getCitiesInContinentOrganisedByPopulation("Asia");
        //Output the report as a markdown file
        outputCities(getCitiesInContinentOrganisedByPopulation, "AllCitiesInContinentOrganisedByPopulation", title);

        //9
        title = ("9. All the Cities in a Region 'Eastern Europe' organised by largest population to smallest.");
        //All the cities in a region organised by largest population to smallest.
        ArrayList<City> getCitiesInARegionOrganisedByPopulation = getCitiesInARegionOrganisedByPopulation("Eastern Europe");
        //Output the report as a markdown file
        outputCities(getCitiesInARegionOrganisedByPopulation, "AllCitiesInARegionOrganisedByPopulation", title);

        //10
        title = ("10. All the Cities in a Country 'China' organised by largest population to smallest.");
        //All the cities in a Country organised by largest population to smallest.
        ArrayList<City> getCitiesInCountryOrganisedByPopulation = getCitiesInCountryOrganisedByPopulation("China");
        //Output the report as a markdown file
        outputCities(getCitiesInCountryOrganisedByPopulation, "AllCitiesInCountryOrganisedByPopulation", title);

        //11
        title = ("11. All the Cities in a District 'Noord-Holland' organised by largest population to smallest.");
        //All the cities in a District organised by largest population to smallest.
        ArrayList<City> getCitiesInDistrictOrganisedByPopulation = getCitiesInDistrictOrganisedByPopulation("Noord-Holland");
        //Output the report as a markdown file
        outputCities(getCitiesInDistrictOrganisedByPopulation, "AllCitiesInDistrictOrganisedByPopulation", title);

        //Use Case 4
        //12
        title = ("12. The Top N '100' Cities in the World Organised by largest population to smallest.");
        //The top N populated cities in the world where N is provided by the user.
        ArrayList<City> getTopCitiesOrganisedByPopulation = getTopCitiesOrganisedByPopulation(100);
        //Output the report as a markdown file
        outputCities(getTopCitiesOrganisedByPopulation, "TopNCitiesInTheWorldOrganisedByPopulation", title);

        //13
        title = ("13. The Top N '100' Cities in a Continent 'Asia' organised by largest population to smallest.");
        //The top N populated cities in a continent where N is provided by the user.
        ArrayList<City> getTopCitiesInContinentOrganisedByPopulation = getTopCitiesInContinentOrganisedByPopulation("Asia", 100);
        //Output the report as a markdown file
        outputCities(getTopCitiesInContinentOrganisedByPopulation, "TopNCitiesInContinentOrganisedByPopulation", title);

        //14
        title = ("14. The Top N '50' Cities in a Region 'Eastern Europe' organised by largest population to smallest.");
        //The top N populated cities in a region where N is provided by the user.
        ArrayList<City> getTopCitiesInARegionOrganisedByPopulation = getTopCitiesInARegionOrganisedByPopulation("Eastern Europe", 50);
        //Output the report as a markdown file
        outputCities(getTopCitiesInARegionOrganisedByPopulation, "TopNCitiesInARegionOrganisedByPopulation", title);

        //15
        title = ("15. The Top N '50' Cities in a Country 'China' organised by largest population to smallest.");
        //The top N populated cities in a country where N is provided by the user.
        ArrayList<City> getTopCitiesInCountryOrganisedByPopulation = getTopCitiesInCountryOrganisedByPopulation("China", 50);
        //Output the report as a markdown file
        outputCities(getTopCitiesInCountryOrganisedByPopulation, "TopNCitiesInCountryOrganisedByPopulation", title);

        //16
        title = ("16. The Top N '5' Cities in a District 'Noord-Holland' organised by largest population to smallest.");
        //The top N populated cities in a district where N is provided by the user.
        ArrayList<City> getTopCitiesInDistrictOrganisedByPopulation = getTopCitiesInDistrictOrganisedByPopulation("Noord-Holland", 5);
        //Output the report as a markdown file
        outputCities(getTopCitiesInDistrictOrganisedByPopulation, "TopNCitiesInDistrictOrganisedByPopulation", title);

        //Use Case 5
        //17
        title = ("17. All the Capital Cities in the World Organised by largest population to smallest.");
        //All the Capital cities in the world organised by largest population to smallest.
        ArrayList<City> getCapitalCitiesOrganisedByPopulation = getCapitalCitiesOrganisedByPopulation();
        //Output the report as a markdown file
        outputCities(getCapitalCitiesOrganisedByPopulation, "AllCapitalCitiesInTheWorldOrganisedByPopulation", title);

        //18
        title = ("18. All the Capital Cities in a Continent 'Europe' organised by largest population to smallest.");
        //All the Capital cities in a continent organised by largest population to smallest.
        ArrayList<City> getCapitalCitiesInContinentOrganisedByPopulation = getCapitalCitiesInContinentOrganisedByPopulation("Europe");
        //Output the report as a markdown file
        outputCities(getCapitalCitiesInContinentOrganisedByPopulation, "AllCapitalCitiesInContinentOrganisedByPopulation", title);

        //19
        title = ("19. All the Capital Cities in a Region 'Western Europe' organised by largest population to smallest.");
        //All the Capital cities in a region organised by largest population to smallest.
        ArrayList<City> getCapitalCitiesInARegionOrganisedByPopulation = getCapitalCitiesInARegionOrganisedByPopulation("Western Europe");
        //Output the report as a markdown file
        outputCities(getCapitalCitiesInARegionOrganisedByPopulation, "AllCapitalCitiesInARegionOrganisedByPopulation", title);

        //Use Case 6
        //20
        title = ("20. The Top N '100' Capital Cities in the world organised by largest population to smallest.");
        //The top N populated Capital cities in the world where N is provided by the user.
        ArrayList<City> getTopCapitalCitiesOrganisedByPopulation = getTopCapitalCitiesOrganisedByPopulation( 100);
        //Output the report as a markdown file
        outputCities(getTopCapitalCitiesOrganisedByPopulation, "TopNCapitalCitiesInTheWorldOrganisedByPopulation", title);

        //21
        title = ("21. The Top N '20' Capital Cities in the Continent 'Europe' organised by largest population to smallest.");
        //The top N populated Capital cities in the continent where N is provided by the user.
        ArrayList<City> getTopCapitalCitiesInContinentOrganisedByPopulation = getTopCapitalCitiesInContinentOrganisedByPopulation(20, "Europe");
        //Output the report as a markdown file
        outputCities(getTopCapitalCitiesInContinentOrganisedByPopulation, "TopNCapitalCitiesInContinentOrganisedByPopulation", title);

        //22
        title = ("22. The top N '20' populated capital cities in a region 'Western Europe' where N is provided by the user.");
        //The top N populated Capital cities in the region where N is provided by the user.
        ArrayList<City> getTopCapitalCitiesInRegionOrganisedByPopulation = getTopCapitalCitiesInRegionOrganisedByPopulation(20, "Western Europe");
        //Output the report as a markdown file
        outputCities(getTopCapitalCitiesInRegionOrganisedByPopulation, "TopNCapitalCitiesInRegionOrganisedByPopulation", title);

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
        //Validate Sql not null or empty
        validateString(sql);

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
        //Validate Sql not null or empty
        validateString(sql);

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
     * Validate the Given String Statement check if it's not null or empty
     * @param string The String to validate
     */
    public void validateString(String string)
    {
        //Try to Validate String not null or empty
        try
        {
            //Check if string is not null or Empty
            if (string == null || string.isEmpty()) {
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
     * Outputs Countries report to Markdown file
     * @param filename The name of the file to output to
     * @param countries The list of countries to Display
     * @param title The title of the report
     */
    public void outputCountries(ArrayList<Country> countries, String filename, String title)
    {
        // Check countries list is not null or empty
        if (countries == null)
        {
            System.out.println("Error: Null Countries List provided!");
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
    public void outputCities(ArrayList<City> cities, String filename, String title)
    {
        // Check cities is not null
        if (cities == null)
        {
            System.out.println("Error: Null Cities List provided!");
            return;
        }

        //initiate String builder
        StringBuilder sb = new StringBuilder();

        // Print header
        sb.append("# " + ((title != null && !title.isEmpty()) ? title : "City Report") + "\n");
        sb.append("\n| Name | Country | District | Population |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        // Loop over all cities in the list
        for (City c : cities)
        {
            // Continue if this city is null
            if (c == null) continue;

            // Add the City variable to the StringBuilder
            sb.append("| " + c.Name + " | " + (c.Country != null ? c.Country.Name : "-") + " | " + c.District + " | " + (c.Population != null ? c.Population.TotalPopulation : "-") + " |\r\n");
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