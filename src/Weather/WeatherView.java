package Weather;

import javafx.event.ActionEvent;

import javafx.scene.control.*;
public class WeatherView {
    public Button btnSubmit;
    public Toggle btnToggleTemp;
    public TextField txtZipCode;
    public TextField txtAddress;
    public TextField txtTemperature;
    public TextField txtWindSpeed;
    public TextField txtWindDirection;
    public TextField txtIsDay;
    WeatherData weatherData;

    public void handleButtonClick(ActionEvent actionEvent) { // handles submit button
        String zipcode = txtZipCode.getCharacters().toString();
        if (ZipValidator.isValidZipCode(zipcode)){
            weatherData = (WeatherController.getWeather(zipcode));
            setTxtFields();
        }else {
            txtZipCode.setText("Invalid Zip!");
        }
    }
    int hasClicked = 1;

    public void toggleTemp(ActionEvent actionEvent){ // handles temp toggle
        if(hasClicked == 1){
            txtTemperature.setPromptText("Temp C");
            hasClicked -= 1;
        } else {
            txtTemperature.setPromptText("Temp F");
            hasClicked += 1;
        }
    }
    int hasClickedWind = 1;
    public void toggleSpeed(ActionEvent actionEvent){ //handles wind speed toggle
        if(hasClickedWind == 1){
            txtWindSpeed.setPromptText("Speed kph");
            hasClickedWind -= 1;
        } else {
            txtWindSpeed.setPromptText("Speed mph");
            hasClickedWind += 1;
        }
    }

    public void clearTxtFields (ActionEvent actionEvent){// clears text fields
        txtTemperature.setText("");
        txtIsDay.setText("");
        txtZipCode.setText("");
        txtAddress.setText("");
        txtWindDirection.setText("");
        txtWindSpeed.setText("");
    }
    public void setTxtFields() { //fills in text fields with data
        txtAddress.setText(weatherData.getAddress());
        txtDisplayTemp();
        txtDisplayWindSpeed();
        txtWindDirection.setText(weatherData.getWindDirection());
        txtIsDay.setText(weatherData.getIsDay());
    }

    private void txtDisplayTemp() { //checs to see the state of the toggle button the displays appropriate temperature
        double temp = weatherData.getTemperature();
        if (hasClicked == 0){
            temp = (temp - 32) * (5.0/9.0);
            txtTemperature.setText(Math.round(temp*10.0)/10.0 + " C");
        }else if (hasClicked == 1) {
            txtTemperature.setText(Math.round(temp*10.0)/10.0 + " F");
        }else {
            txtTemperature.setText(hasClicked + "");
        }
    }

    private void txtDisplayWindSpeed() {//checks to see the state of the toggle button then displays appropriate wind speed
        double windSpeed = weatherData.getWindSpeed();
        if (hasClickedWind == 0){
            windSpeed = (windSpeed / 1.609);
            txtWindSpeed.setText(Math.round(windSpeed*10.0)/10.0 + " kph");
        }else {
            txtWindSpeed.setText(windSpeed + " mph");
        }
    }
}
