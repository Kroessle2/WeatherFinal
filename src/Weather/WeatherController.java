package Weather;
//this class takes in a string zip checks it with the validator method from the vipValidator class
//and outputs a weather data object
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WeatherController {

    public static ForecastData getForecast(String zipCode) {
        int parsedZipCode = 0;
        if (ZipValidator.parseZipCode(zipCode) != -1) {
            parsedZipCode = ZipValidator.parseZipCode(zipCode);
        } else {
            System.out.println("Please Enter a Valid ZipCode");
        }

        JSONObject forecast = null;
        ForecastData forecastData= null;
        try {

            JSONObject jsonResponse = getJsonObject(parsedZipCode);

            Double latitude = jsonResponse.getDouble("lat");// these are the output of the geocodio api
            Double longitude = jsonResponse.getDouble("lng");
            String City = jsonResponse.getString("address");

            JSONObject jsonResponse2 = getJsonObject(String.format("https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&temperature_unit=fahrenheit&hourly=temperature_2m,relative_humidity_2m,precipitation_probability&forecast_days=7 ", latitude, longitude));
            forecast = jsonResponse2.getJSONObject("hourly");

            //calls to the api
            JSONArray timeArray = forecast.getJSONArray("time");
            JSONArray temperatureArray = forecast.getJSONArray("temperature_2m");
            JSONArray humidityArray =forecast.getJSONArray("relative_humidity_2m");
            JSONArray precipitationArray = forecast.getJSONArray("precipitation_probability");

            System.out.println(timeArray);
            System.out.println(temperatureArray);
            forecastData = new ForecastData(temperatureArray, timeArray, humidityArray, precipitationArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return forecastData;
    }

    public static WeatherData getWeather(String zipCode) {
        int parsedZipCode = 0;
        if (ZipValidator.parseZipCode(zipCode) != -1) {
            parsedZipCode = ZipValidator.parseZipCode(zipCode);
        } else {
            System.out.println("Please Enter a Valid ZipCode");
        }

        JSONObject currentWeather = null;

        WeatherData weatherData = null;

        JSONObject forecast = null;
        try {

            JSONObject jsonResponse = getJsonObject(parsedZipCode);

            Double latitude = jsonResponse.getDouble("lat");// these are the output of the geocodio api
            Double longitude = jsonResponse.getDouble("lng");
            String City = jsonResponse.getString("address");

            JSONObject jsonResponse2 = getJsonObject(String.format("https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true&temperature_unit=fahrenheit&hourly=temperature_2m", latitude, longitude));
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

            weatherData = new WeatherData(temperatureFahrenheit, windSpeed, isDay, windDirection, City); // creates a weatherData object then returns it

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(forecast);
        return weatherData;
    }

    private static JSONObject getJsonObject(String latitude) throws IOException {
        String urlStringWeather = latitude;

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
        return jsonResponse2;
    }

    private static JSONObject getJsonObject(int parsedZipCode) throws IOException {
        System.out.println(parsedZipCode);
        JSONObject jsonResponse = getJsonObject(String.format("https://api.geocod.io/v1.7/geocode?postal_code="+parsedZipCode+"&api_key=f167b9ffff7cca2721c97f96f1676cf2799f696&format=simple"));
        return jsonResponse;
    }

    private static String getCompassDirection(double angle){ //get the angle the wind is coming from and converts it to compass directions
        String[] directions = {"North","Northeast","East","Southeast","South","Southwest","West","Northwest","North"};
        return directions[(int)Math.round(((angle % 360) / 45))];
    }
}
