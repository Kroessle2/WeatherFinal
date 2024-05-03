package Weather;


import java.util.Scanner;

public class MainWeather {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a Zip code: ");
        String zipCode = scan.nextLine();
        System.out.println(WeatherController.getWeather(zipCode));

    }
}
