/**
 * Project: NapierGroupF
 * Package: com.napier.NapierGroupF
 * User: Hamza Shabir - 40425459
 * Date Created: 2/27/22 5:09 PM
 * Date last updated: 29/03/2022
 * Class Description: A Population class which represents the required columns for a Population report.
 */

package com.napier.NapierGroupF;

/**
 * Population Report Class
 */
public class Population
{
    /**
     * The name of Continent/Region/Country
     */
    public String Name;

    /**
     * The Total population of the Continent/Region/Country
     */
    public long TotalPopulation;

    /**
     * The population of a Continent/Region/Country living in Cities
     */
    public long PopulationInCities;

    /**
     * The population of a Continent/Region/Country living in Cities as a Percentage
     */
    public double PercentageOfPopulationInCities;

    /**
     * The population of a Continent/Region/Country not living in Cities
     */
    public long PopulationNotInCities;

    /**
     * The population of a Continent/Region/Country not living in Cities as a Percentage
     */
    public double PercentageOfPopulationNotInCities;

    /**
     * Constructor with only Total Population
     * @param tp Total Population
     */
    public Population (long tp)
    {
        TotalPopulation = tp;
    }

    /**
     * Empty Constructor
     */
    public Population ()
    {
    }
}
