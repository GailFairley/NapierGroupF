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
import java.sql.*;
import java.util.ArrayList;

//Main class to connect to db and display reports
public class App
{
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

        // Display All the Reports in terminal
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
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            //Catch Drive error
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        //Try Connecting for ten times
        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(delay);

                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + loc + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "world");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                //Catch sql Connection or Query error
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                //Catch interruption error
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                // Display error - why cannot close the connection
                System.out.println("Error closing connection to database");
            }
        }
    }

    public void getAndDisplayAllReports()
    {
        //Reports
        //1
        System.out.println("\nAll Countries organised by population Largest to Smallest\n");
        //All countries organised by population Largest to Smallest
        ArrayList<Country> countriesOrganisedByPopulation = getCountriesOrganisedByPopulation();
        //Display the retrieved Countries
        displayCountries(countriesOrganisedByPopulation);

        //2
        System.out.println("\nAll Countries Given Continent 'Europe' organised by population Largest to Smallest\n");
        //All countries organised by population Largest to Smallest
        ArrayList<Country> countriesInAContinentOrganisedByPopulation = getCountriesInAContinentOrganisedByPopulation("Europe");
        //Display the retrieved Countries
        displayCountries(countriesInAContinentOrganisedByPopulation);

        //3
        System.out.println("\nAll Countries Given Region 'Eastern Europe' organised by population Largest to Smallest\n");
        //All the countries in a region organised by largest population to smallest.
        ArrayList<Country> countriesInARegionOrganisedByPopulation = getCountriesInARegionOrganisedByPopulation("Eastern Europe");
        //Display the retrieved Countries
        displayCountries(countriesInARegionOrganisedByPopulation);

        //4
        System.out.println("\nThe top '100' populated countries in the world.\n");
        //The top N populated countries in the world where N is provided by the user.
        ArrayList<Country> getTopNCountries = getTopNCountries(100);
        //Display the retrieved Countries
        displayCountries(getTopNCountries);

        //5
        System.out.println("\nThe top '20' populated countries in the world in the continent 'Europe'.\n");
        //The top N populated countries in a continent where N is provided by the user.
        ArrayList<Country> getTopNCountriesInAContinent = getTopNCountriesInAContinent(20, "Europe");
        //Display the retrieved Countries
        displayCountries(getTopNCountriesInAContinent);

        //6
        System.out.println("\nThe top '10' populated countries in the world in the Region 'Eastern Europe'.\n");
        //The top N populated countries in a region where N is provided by the user.
        ArrayList<Country> getTopNCountriesInARegion = getTopNCountriesInARegion(10, "Eastern Europe");
        //Display the retrieved Countries
        displayCountries(getTopNCountriesInARegion);

        //End
    }

    /**
     * Get a report by passing a Class type and Sql
     * @param t Generic t of the Class type required
     * @param sql The SQL statement provided
     * @return ArrayList of Passed Class
     */
    public <T> ArrayList<T> getReport(Class<T> t, String sql)
    {
        try
        {
            //Create an SQL Statement
            Statement stmnt = con.createStatement();

            //Check if sql is not null
            if(sql == null || sql.isEmpty())
            {
                System.out.println("Empty or Null Sql Statement provided!");
                return null;
            }

            //Execute the SQL statement
            ResultSet rset = stmnt.executeQuery(sql);

            //Check if Class type provided
            if(t == null)
            {
                System.out.println("Null Class Given!");
                return null;
            }

            if (t.isInstance(new Country()))
            {
                //Extract each Country information
                ArrayList<T> countries = new ArrayList<>();
                //While there is a Row
                while (rset.next())
                {
                    //Get Each Country information and add it to countries arraylist

                    //Initialize Country
                    Country country = new Country();

                    //Map SQL Column values to Country Variables
                    country.Code = rset.getString("Code");
                    country.Name = rset.getString("Name");
                    country.Continent = rset.getString("Continent");
                    country.Region = rset.getString("Region");
                    country.Population = new Population(rset.getLong("Population"));
                    country.Capital = new City(rset.getString("Capital"));
                    countries.add(t.cast(country));
                }

                return countries;
            }
            else if (t.isInstance(new City()))
            {
                //Extract each City information
                ArrayList<T> cities = new ArrayList<>();
                //While there is a Row
                while (rset.next())
                {
                    //Get Each City information and add it to cities arraylist
                    City city = new City();
                    city.Country = new Country(rset.getString("Country"));//Country Name
                    city.Name = rset.getString("Name");//city Name
                    city.District = rset.getString("District");
                    city.Population = new Population(rset.getLong("Population"));
                    cities.add(t.cast(city));
                }

                return cities;
            }

            return null;
        }

        //Catch Exception
        catch (SQLException e)
        {
            //Print Exception error message
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
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
        return getReport(Country.class, sql);
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
        return getReport(Country.class, sql);
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
        return getReport(Country.class, sql);
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
        return getReport(Country.class, sql);
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
        return getReport(Country.class, sql);
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
        return getReport(Country.class, sql);
    }

    /**
     * Displays the list of Countries
     * @param countries The list of countries to Display
     */
    public void displayCountries(ArrayList<Country> countries)
    {
        //check if countries in null
        if (countries == null)
        {
            //If null display error and exit this method
            System.out.println("The Countries list provided is null!");
            return;
        }
        //Display the Header
        System.out.printf("%-4s %-60s %-15s %-26s %-30s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

        //For each Country Display each country information
        for (Country c : countries)
        {
            //Continue if this country is null
            if (c == null)
            {
                continue;
            }

            //Display country information
            System.out.printf("%-4s %-60s %-15s %-26s %-30s %-10s%n", c.Code, c.Name, c.Continent, c.Region, c.Population != null ? c.Population.TotalPopulation : "", c.Capital != null ? c.Capital.Name : "");
        }
    }

    /**
     * Displays the list of cities
     * @param cities The list of cities to Display
     */
    public void displayCities(ArrayList<City> cities)
    {
        //check if cities in null
        if (cities == null)
        {
            //If null display error and exit this method
            System.out.println("The Cities list provided is null!");
            return;
        }
        //Display the Header
        System.out.printf("%-25s %-60s %-15s %-20s", "Name", "Country", "District", "Population");

        //For each Country Display each country information
        for (City c : cities)
        {
            //Continue if this City is null
            if (c == null)
            {
                continue;
            }

            //Display city information
            System.out.printf("%-25s %-60s %-15s %-20s", c.Name, c.Country != null ? c.Country.Name : "", c.District, c.Population != null ? c.Population.TotalPopulation : "");
        }
    }
}