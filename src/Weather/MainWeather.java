package Weather;
//console application of the weather app

import java.util.Scanner;

public class MainWeather {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true){
            while (true){

            System.out.print("Enter a Zip code: ");
            String zipCode = scan.nextLine();
                if(ZipValidator.isValidZipCode(zipCode)){
                    System.out.println(WeatherController.getWeather(zipCode).toString());
                    break;
                } else {
                    System.out.println("Please Enter A valid Zip!");
                }
            }

            System.out.print("Do you want to continue: (y/n): ");
            String yesNo = scan.nextLine();
            if (yesNo.equalsIgnoreCase("N")){
                break;
            }
        }
    }
}
