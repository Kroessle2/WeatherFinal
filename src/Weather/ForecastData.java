package Weather;

import org.json.JSONArray;

import java.util.Arrays;

public class ForecastData {

    public String[] times;
    public double[] temperatures;
    public double[] humidity;
    public double[] precipitation;

    public ForecastData(JSONArray temperatureData, JSONArray timeData, JSONArray humidityData, JSONArray precipitationData) {
        times = new String[timeData.length()];
        temperatures = new double[temperatureData.length()];
        humidity = new double[humidityData.length()];
        precipitation = new double[precipitationData.length()];

        for (int i = 0; i < humidityData.length(); i++) {
            humidity[i] = humidityData.getDouble(i);
        }
        for (int i = 0; i < precipitationData.length(); i++) {
            precipitation[i] = precipitationData.getDouble(i);
        }
        for (int i = 0; i < timeData.length(); i++) {
            times[i] = timeData.getString(i);
        }
        for (int i = 0; i < temperatureData.length(); i++) {
            temperatures[i] = temperatureData.getDouble(i);
        }

    }

    public double[] getHumidity() {
        return humidity;
    }

    public double[] getPrecipitation() {
        return precipitation;
    }

    public String[] getTimes() {
        return times;
    }

    public void setTimes(String[] times) {
        this.times = times;
    }

    public double[] getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(double[] temperatures) {
        this.temperatures = temperatures;
    }

    @Override
    public String toString() {
        return "ForecastData{" +
                "times=" + Arrays.toString(times) +
                ", temperatures=" + Arrays.toString(temperatures) +
                '}';
    }
}