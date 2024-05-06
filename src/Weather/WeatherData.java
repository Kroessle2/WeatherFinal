/*
 * Author: Kason Roessler
 * Description : This program creates public objects that contain the weather information from WeatherService.JAVA
 * Date Last Edited: 4/26/2024
 */
package Weather;
//this class handles the weather Data object that the console application and the fxml access to display data
public class WeatherData {

    private Double temperature;
    private Double windSpeed;
    private String isDay;
    private String windDirection;
    private String address;

    public WeatherData(Double temperature, double windSpeed, String isDay, String windDirection, String address){
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.isDay = isDay;
        this.windDirection = windDirection;
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIsDay() {
        return isDay;
    }

    public void setIsDay(String isDay) {
        this.isDay = isDay;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    @Override
    public String toString() {
        return "Weather\n" +
                "Address: "+ address + "\n" +
                "Temperature: " + temperature +" Fahrenheit\n" +
                "Wind Speed: " + windSpeed + " mph\n" +
                "Wind is coming from: the " + windDirection + "\n" +
                "Time: "+ isDay;
    }
}
