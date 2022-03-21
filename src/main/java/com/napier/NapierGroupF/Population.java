/**
 * Project: NapierGroupF
 * Package: com.napier.NapierGroupF
 * User: Hamza Shabir - 40425459
 * Date Created: 2/27/22 5:09 PM
 * Date last updated: 21/03/2022
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
    public int TotalPopulation;

    /**
     * The population of a Continent/Region/Country living in Cities
     */
    public int PopulationInCities;

    /**
     * The population of a Continent/Region/Country living in Cities as a Percentage
     */
    public double PercenatgeOfPopulationInCities;

    /**
     * The population of a Continent/Region/Country not living in Cities
     */
    public int PopulationNotInCities;

    /**
     * The population of a Continent/Region/Country not living in Cities as a Percentage
     */
    public double PercentageOfPopulationNotInCities;

    /**
     * Constructor with all variables
     * @param n City/Country/Continent/Region
     * @param tp Total Population
     * @param pic The population of a Continent/Region/Country living in Cities
     * @param popic The population of a Continent/Region/Country living in Cities as a Percentage
     * @param pnic The population of a Continent/Region/Country not living in Cities
     * @param popnic The population of a Continent/Region/Country not living in Cities as a Percentage
     */
    public Population (String n, int tp, int pic, int popic, int pnic, int popnic)
    {
        Name = n;
        TotalPopulation = tp;
        PopulationInCities = pic;
        PercenatgeOfPopulationInCities = popic;
        PopulationNotInCities = pnic;
        PercentageOfPopulationNotInCities = popnic;
    }

    /**
     * Constructor with only Total Population
     * @param tp Total Population
     */
    public Population (int tp)
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
