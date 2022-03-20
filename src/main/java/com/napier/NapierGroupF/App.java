/**
 * Project Name: coursework
 * Package: com.napier.NapierGroupF
 * User Created: Josh McQueen
 * Date Created: 06/02/2022
 * Date Last Updated: 27/02/2022
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
        //a.connect("localhost:33069"); // Local test connection
        a.connect("db:3306");

        //Reports
        //All countries organised by population Largest to Smallest
        ArrayList<Country> countries = a.getCountriesOrganisedByPopulation();
        if (countries != null)
        {
            a.displayCountries(countries);
        }

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
    public void connect(String loc)
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

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);

                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + loc + "/world?useSSL=false", "root", "world");
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
                System.out.println("Error closing connection to database");
            }
        }
    }
    /**
     * Get a single City given valid string name
     *
     * @param name
     * @return
     */
    public City getCity(String name) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT  Name, c.Name AS Country, District, Population " +
                    "FROM city " +
                    "JOIN Country c ON c.Code = CountryCode" +
                    "WHERE Name = '" + name + "'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            //Return city if valid
            //Check one is returned
            if (rset.next()) {
                City c = new City();
                c.Name = rset.getString("Name");
                Country cou = new Country();
                cou.Name = rset.getString("Country");
                c.Country = cou;
                c.District = rset.getString("District");
                c.Population = rset.getInt("Population");
                return c;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    /**
     * Get a single capital details given a valid string country
     * @param country
     * @return
     */
    public City getCapital(String country) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT city.Name, country.Name, city.Population " +
                    "FROM city JOIN country ON city.ID = country.Capital " +
                    "WHERE country.Name = '" + country + "'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract capital information
            if (rset.next()) {
                City c = new City();
                c.Name = rset.getString("city.Name");
                Country co = new Country();
                co.Name = rset.getString("country.Name");
                c.Country = co;
                c.Population = rset.getInt("city.Population");
                return c;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capital details");
            return null;
        }
    }

/**
 * Queries that return Country details
 */

    /**
     * Get all countries in a continent
     * @param continent
     * @return
     */
    public ArrayList<Country> CountriesContinent(String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT country.Name, country.Code, country.Region, country.Population " +
                    "FROM country " +
                    "WHERE country.Continent = '" + continent + "'" +
                    "ORDER BY country.Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country co = new Country();
                co.Name = rset.getString("country.name");
                co.Code = rset.getString("country.Code");
                co.Region = rset.getString("country.Region");
                co.Population = rset.getInt("country.Population");
                countries.add(co);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     *Get all countries in a region ordered by population
     * @param region
     * @return
     */
    public ArrayList<Country> CountriesRegion(String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT country.Name, country.Code, country.Region, country.Population " +
                    "FROM country " +
                    "WHERE country.Region = '" + region + "'" +
                    "ORDER BY country.Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country co = new Country();
                co.Name = rset.getString("country.name");
                co.Code = rset.getString("country.Code");
                co.Region = rset.getString("country.Region");
                co.Population = rset.getInt("country.Population");
                countries.add(co);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Get all countries in the world ordered by population
     *
     * @return
     */
    public ArrayList<Country> CountriesWorld() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT country.Name, country.Code, country.Region, country.Population " +
                    "FROM country " +
                    "ORDER BY country.Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country co = new Country();
                co.Name = rset.getString("country.name");
                co.Code = rset.getString("country.Code");
                co.Region = rset.getString("country.Region");
                co.Population = rset.getInt("country.Population");
                countries.add(co);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Get the top X countries in a continent ordered by population. Given a valid int X and string continent
     * @param topX
     * @param continent
     * @return
     */
    public ArrayList<Country> topCountriesContinent(Integer topX, String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT country.Name, country.Code, country.Region, country.Population " +
                    "FROM country " +
                    "WHERE country.Continent = '" + continent + "'" +
                    "ORDER BY country.Population DESC " +
                    "LIMIT " + topX;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country co = new Country();
                co.Name = rset.getString("country.name");
                co.Code = rset.getString("country.Code");
                co.Region = rset.getString("country.Region");
                co.Population = rset.getInt("country.Population");
                countries.add(co);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Get the top X countries in a region ordered by population. Given a valid int X and string region
     *
     * @param topX
     * @param region
     * @return
     */
    public ArrayList<Country> TopCountriesRegion(Integer topX, String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT country.Name, country.Code, country.Region, country.Population " +
                    "FROM country " +
                    "WHERE country.Region = '" + region + "'" +
                    "ORDER BY country.Population DESC " +
                    "LIMIT " + topX;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country co = new Country();
                co.Name = rset.getString("country.name");
                co.Code = rset.getString("country.Code");
                co.Region = rset.getString("country.Region");
                co.Population = rset.getInt("country.Population");
                countries.add(co);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Get the top X countries in teh world ordered by population. Given a valid int topX
     *
     * @param //topX
     * @return
     */
    public ArrayList<Country> topCountriesWorld() {//(Integer topX) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT country.Name, country.Code, country.Region, country.Population " +
                    "FROM country " +
                    "ORDER BY country.Population DESC ";// "LIMIT " + topX;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country co = new Country();
                co.Name = rset.getString("country.name");
                co.Code = rset.getString("country.Code");
                co.Region = rset.getString("country.Region");
                co.Population = rset.getInt("country.Population");
                countries.add(co);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }


/**
 * All queries that return a City report
 */
    /**
     * Returns all the cities in the world organised by largest population to smallest
     * @return cities
     */
    public ArrayList<City> listCitiesWorld() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT city.Name, city.CountryCode, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next()) {
                City c = new City();
                c.Name = rset.getString("city.Name");
                Country cou = new Country();
                cou.Name = rset.getString("city.CountryCode");
                c.Country = cou;
                c.District = rset.getString("city.District");
                c.Population = rset.getInt("city.Population");
                cities.add(c);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /** ------------------- JM:insert new code above --------------------- */

    /**
     * All countries organised by population Largest to Smallest
     * @return ArrayList of Countries
     */
    public ArrayList<Country> getCountriesOrganisedByPopulation()
    {
        //Add string for the SQL statement
        String sql = "SELECT Code, c.Name AS Name, Continent, Region, c.Population AS Population, cp.Name AS Capital "
                   + "From country c "
                   + "JOIN city cp on c.Capital = cp.ID "
                   + "Order By Population DESC";

        return getreport ( Country.class, sql );
    }

    /**
     * Get report of the Given Class
     * @return ArrayList of the Class provided
     */
    public <T> ArrayList<T> getreport(Class<T> t, String sql)
    {
        try
        {
            //Create an SQL Statement
            Statement stmnt = con.createStatement();

            //Execute the SQL statement
            ResultSet rset = stmnt.executeQuery(sql);

            if (t.isInstance(new Country()))
            {
                //Extract each Country information
                ArrayList<T> countries = new ArrayList<>();
                //While there is a Row
                while (rset.next())
                {
                    //Get Each Country information and add it to countries arraylist
                    Country country = new Country();
                    country.Code = rset.getString("Code");
                    country.Name = rset.getString("Name");
                    country.Continent = rset.getString("Continent");
                    country.Region = rset.getString("Region");
                    country.Population = rset.getInt("Population");
                    country.Capital = rset.getString("Capital");
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
                    city.Country.Name = rset.getString("Country");
                    city.Name = rset.getString("Name");
                    city.District = rset.getString("District");
                    city.Population = rset.getInt("Population");
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
     * Displays the list of Countries
     * @param countries The list of countries to Display
     */
    public void displayCountries(ArrayList<Country> countries)
    {
        //Display the Header
        System.out.println(String.format("%-4s %-60s %-15s %-26s %-30s %-10s", "Code", "Name", "Continent", "Region", "Population", "Capital"));

        //For each Country Display each country information
        for (Country c : countries)
        {
            System.out.println(String.format("%-4s %-60s %-15s %-26s %-30s %-10s", c.Code, c.Name, c.Continent, c.Region, c.Population, c.Capital));
        }
    }
}