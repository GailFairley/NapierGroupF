/**
 * Project Name: coursework
 * Package: com.napier.NapierGroupF
 * User Created: Hamza Shabir
 * Date Created: 29/03/2022
 * Date Last Updated: 29/03/2022
 * File Description: This class contains the custom exceptions for the program
 **/

package com.napier.NapierGroupF;

/**
 * This class contains the custom exceptions for the program
 */
public class InvalidStringException extends Exception
{
    /**
     * Constructor with message and cause
     * @param message message to be displayed
     * @param cause cause of the exception
     */
    public InvalidStringException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
