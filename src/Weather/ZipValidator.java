/*
 * Author: Kason Roessler
 * Description : This program accesses the Geocod.io API
 * Date Last Edited: 4/26/2024
 */
package Weather;
// handles zip validation and parsing  for geocodio to access
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipValidator {

    //parse ZipCode validates and converts zip code to float for the geocodio API
    public static float parseZipCode(String zipCode){
        if (isValidZipCode(zipCode)) {
            float convertedZip = Float.parseFloat(zipCode);

            return convertedZip;
        }
        else
            return -1; //returns negative on if validation fails
    }
    public static boolean isValidZipCode(String zipString){
        String regexString = "^\\d{5}(?:[-\\s]\\d{4})?$"; //Regex to validate zipcodes

        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(zipString);

        return matcher.matches();
    }

}

