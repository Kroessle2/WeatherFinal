package Weather;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
public class WeatherController {

    public static WeatherData getWeather(String zipCode) {
        float parsedZipCode = 0;
        if (ZipValidator.parseZipCode(zipCode) != -1) {
            parsedZipCode = ZipValidator.parseZipCode(zipCode);
        } else {
            System.out.println("Please Enter a Valid ZipCode");
        }

        JSONObject currentWeather = null;

        WeatherData weatherData = null;
        try {

            String urlStringLocation = String.format("https://api.geocod.io/v1.7/geocode?postal_code=%f&api_key=f167b9ffff7cca2721c97f96f1676cf2799f696&format=simple", parsedZipCode);

            URL url = new URL(urlStringLocation);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;

            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            Double latitude = jsonResponse.getDouble("lat");// these are the output of the geocodio api
            Double longitude = jsonResponse.getDouble("lng");
            String City = jsonResponse.getString("address");


            String urlStringWeather = String.format("https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true&temperature_unit=fahrenheit", latitude, longitude);

            URL url2 = new URL(urlStringWeather);
            HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
            con2.setRequestMethod("GET");


            BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream(), StandardCharsets.UTF_8));
            String inputLine2;

            StringBuilder response2 = new StringBuilder();

            while ((inputLine2 = in2.readLine()) != null) {
                response2.append(inputLine2);
            }
            in2.close();

            // parse JSON response
            JSONObject jsonResponse2 = new JSONObject(response2.toString());
            currentWeather = jsonResponse2.getJSONObject("current_weather");

            //calls to the api
            double temperatureFahrenheit = currentWeather.getDouble("temperature");
            int isDayOrNight = currentWeather.getInt("is_day");
            double windSpeed = currentWeather.getDouble("windspeed");
            int windDirectionInt = currentWeather.getInt("winddirection");

            String windDirection = getCompassDirection(windDirectionInt);

            String isDay;
            if (isDayOrNight == 0) {
                isDay = "It's Night time";
            } else {
                isDay = "It's Day time";
            }


            weatherData = new WeatherData(temperatureFahrenheit, windSpeed, isDay, windDirection, City);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    private static String getCompassDirection(double angle){
        String[] directions = {"North","Northeast","East","Southeast","South","Southwest","West","Northwest","North"};
        return directions[(int)Math.round(((angle % 360) / 45))];
    }
}
