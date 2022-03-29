/**
 * Project: NapierGroupF
 * Package: com.napier.NapierGroupF
 * User: Hamza Shabir - 40425459
 * Date Created: 27/02/22 5:03 PM
 * Date last updated: 29/03/2022
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
