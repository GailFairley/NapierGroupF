/**
 * Project: NapierGroupF
 * Package: com.napier.NapierGroupF
 * User: Hamza Shabir - 40425459
 * Date Created: 27/02/22 4:54 PM
 * Date last updated: 29/03/22
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
     * Empty Constructor
     */
    public Country ()
    {
    }
}
