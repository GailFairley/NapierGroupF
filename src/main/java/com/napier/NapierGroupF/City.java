/**
 * Project: NapierGroupF
 * Package: com.napier.NapierGroupF
 * User: Hamza Shabir - 40425459
 * Date Created: 2/27/22 5:03 PM
 * Date last updated: 02/27/2022
 * Class Description: A City class which represents the required columns for a City report
 */

package com.napier.NapierGroupF;

/**
 * Represents a City
 */
public class City
{
    /**
     * The Name of the City
     */
    public String Name;

    /**
     * The Country this City belongs to
     */
    public Country Country;

    /**
     * The District of the City
     */
    public String District;

    /**
     * The total population of the current City
     */
    public Population Population;

    /**
     * Constructor with all variables
     * @param n Name of City
     * @param c Country the City belongs to
     * @param d District of city
     * @param p Population of the City
     */
    public City (String n, Country c, String d, Population p)
    {
        Name = n;
        Country = c;
        District = d;
        Population = p;
    }

    /**
     * Constructors with only the name
     * @param n Name of the City
     */
    public City (String n)
    {
        Name = n;
    }

    /**
     * Empty Constructor
     */
    public City ()
    {
    }
}
