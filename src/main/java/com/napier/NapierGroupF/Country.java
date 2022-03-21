/**
 * Project: NapierGroupF
 * Package: com.napier.NapierGroupF
 * User: Hamza Shabir - 40425459
 * Date Created: 27/02/22 4:54 PM
 * Date last updated: 21/03/22
 * Class Description: A Country class which represents the required columns for a country report.
 */

package com.napier.NapierGroupF;

/**
 * Represents a Country
 */
public class Country
{
    /**
     * Represents the Country code as a String
     */
    public String Code;

    /**
     * The Name of the Country
     */
    public String Name;

    /**
     * The continent of the Country
     */
    public String Continent;

    /**
     * The region the Country is in
     */
    public String Region;

    /**
     * The Total Population of the Country
     */
    public Population Population;

    /**
     * The Capital City of the Country
     */
    public City Capital;

    /**
     * Constructor for Country with Name variable
     */
    public Country (String n)
    {
        Name = n;
    }

    /**
     * Constructor with all Variables
     * @param n Name
     * @param c Country Code
     * @param con Continent
     * @param r Region
     * @param p Population
     * @param city Capital City
     */
    public Country (String n, String c, String con, String r, Population p, City city)
    {
        Name = n;
        Code = c;
        Continent = con;
        Region = r;
        Population = p;
        Capital = city;
    }

    /**
     * Constructor with 4 Variables
     * @param n Name
     * @param c Country Code
     * @param con Continent
     * @param r Region
     */
    public Country (String n, String c, String con, String r)
    {
        Name = n;
        Code = c;
        Continent = con;
        Region = r;
    }

    /**
     * Empty Constructor
     */
    public Country ()
    {
    }
}
