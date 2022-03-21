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
        //a.connect("localhost:33069"); // Local test connection
        a.connect("db:3306");

        //Reports
        //All countries organised by population Largest to Smallest
        a.getCountriesOrganisedByPopulationAndDisplay();

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
                // Display error - why cannot close the connection
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Get a report by passing a Class type and Sql
     * @param t, sql Generic t of the Class type required and Sql
     * @return ArrayList of Passed Class
     */
    public <T> ArrayList<T> getReport(Class<T> t, String sql)
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

                    //Initialize Country
                    Country country = new Country();

                    //Map SQL Column values to Country Variables
                    country.Code = rset.getString("Code");
                    country.Name = rset.getString("Name");
                    country.Continent = rset.getString("Continent");
                    country.Region = rset.getString("Region");
                    country.Population = new Population(rset.getInt("Population"));
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
                    city.Country.Name = rset.getString("Country");
                    city.Name = rset.getString("Name");
                    city.District = rset.getString("District");
                    city.Population = new Population(rset.getInt("Population"));
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
     *Get report for All countries organised by population Largest to Smallest and Display these in terminal
     */
    public void getCountriesOrganisedByPopulationAndDisplay()
    {
        //Add string for the SQL statement
        String sql = "SELECT Code, c.Name AS Name, Continent, Region, c.Population AS Population, cp.Name AS Capital "
                   + "From country c "
                   + "JOIN city cp on c.Capital = cp.ID "
                   + "Order By Population DESC";

        //Get All countries organised by population Largest to Smallest
        ArrayList<Country> countries = getReport(Country.class, sql);

        //Check if retrieved any Countries
        if (countries != null)
        {
            //Display the retrieved Countries
            displayCountries(countries);
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
            System.out.println(String.format("%-4s %-60s %-15s %-26s %-30s %-10s", c.Code, c.Name, c.Continent, c.Region, c.Population, c.Capital != null ? c.Capital.Name : ""));
        }
    }
}