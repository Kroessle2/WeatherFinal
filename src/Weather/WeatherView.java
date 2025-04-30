package Weather;

import javafx.event.ActionEvent;

import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class WeatherView {
    @javafx.fxml.FXML
    public Button btnSubmit;
    @javafx.fxml.FXML
    public TextField txtZipCode;
    @javafx.fxml.FXML
    public TextField txtAddress;
    @javafx.fxml.FXML
    public TextField txtTemperature;
    @javafx.fxml.FXML
    public TextField txtWindSpeed;
    @javafx.fxml.FXML
    public TextField txtWindDirection;
    @javafx.fxml.FXML
    public TextField txtIsDay;

    WeatherData weatherData;
    ForecastData forecastData;

    @javafx.fxml.FXML
    private ToggleButton btnToggleSpeed;
    @javafx.fxml.FXML
    private Button btnClearText;
    @javafx.fxml.FXML
    private Label lblZipCode;
    @javafx.fxml.FXML
    private CategoryAxis lblChartXaxis;
    @javafx.fxml.FXML
    private ToggleButton btnToggleTemp;
    @javafx.fxml.FXML
    private ImageView imgClouds;
    @javafx.fxml.FXML
    private NumberAxis lblCrtYAxis;
    @javafx.fxml.FXML
    private LineChart crtForecast;
    @javafx.fxml.FXML
    private Pane pnGraph;
    @javafx.fxml.FXML
    private Label lblhover;


    @javafx.fxml.FXML
    public void handleButtonSubmit(ActionEvent actionEvent) { // handles submit button
        String zipcode = txtZipCode.getCharacters().toString();
        if (ZipValidator.isValidZipCode(zipcode)){
            weatherData = WeatherController.getWeather(zipcode);
            forecastData= WeatherController.getForecast(zipcode);
            setTxtFields();
            populateChart();
        }else {
            txtZipCode.setText("Invalid Zip!");
        }
    }

    int hasClicked = 1;

    @javafx.fxml.FXML
    public void toggleTemp(ActionEvent actionEvent){ // handles temp toggle
        if(hasClicked == 1){
            txtTemperature.setPromptText("Temp C");
            hasClicked -= 1;
        } else {
            txtTemperature.setPromptText("Temp F");
            hasClicked += 1;
        }
        txtDisplayTemp();
    }
    int hasClickedWind = 1;
    @javafx.fxml.FXML
    public void toggleSpeed(ActionEvent actionEvent){ //handles wind speed toggle
        if(hasClickedWind == 1){
            txtWindSpeed.setPromptText("Speed kph");
            hasClickedWind -= 1;

        } else {
            txtWindSpeed.setPromptText("Speed mph");
            hasClickedWind += 1;
        }
        txtDisplayWindSpeed();
    }

    @javafx.fxml.FXML
    public void clearTxtFields (ActionEvent actionEvent){// clears text fields and the weather data object
        txtTemperature.setText("");
        txtIsDay.setText("");
        txtZipCode.setText("");
        txtAddress.setText("");
        txtWindDirection.setText("");
        txtWindSpeed.setText("");
        crtForecast.getData().clear();

        weatherData.setTemperature(null);
        weatherData.setAddress(null);
        weatherData.setIsDay(null);
        weatherData.setWindDirection(null);
        weatherData.setWindSpeed(null);
    }
    public void setTxtFields() { //fills in text fields with data
        txtAddress.setText(weatherData.getAddress());
        txtDisplayTemp();
        txtDisplayWindSpeed();
        txtWindDirection.setText(weatherData.getWindDirection());
        txtIsDay.setText(weatherData.getIsDay());
    }

    private void txtDisplayTemp() { //checks to see the state of the toggle button the displays appropriate temperature
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

    private void populateChart() {
        String[] time = forecastData.getTimes();
        double[] temperature = forecastData.getTemperatures();
        double[] humidity = forecastData.getHumidity();
        double[] precipitation = forecastData.getPrecipitation();

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("E MMM d' at 'h a");

        List<String> formattedTimes = new ArrayList<>();

        for (String rawTime : time) {
            LocalDateTime dateTime = LocalDateTime.parse(rawTime, inputFormatter);
            String formatted = dateTime.format(outputFormatter);
            formattedTimes.add(formatted);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesHumidity = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesPrecipitation = new XYChart.Series<>();
        series.setName("Temperature");
        seriesHumidity.setName("Humidity %");
        seriesPrecipitation.setName("Precipitation %");

        for (int i = 0; i < time.length; i++) {
            series.getData().add(new XYChart.Data<>(formattedTimes.get(i), temperature[i]));
            seriesHumidity.getData().add(new XYChart.Data<>(formattedTimes.get(i), humidity[i]));
            seriesPrecipitation.getData().add(new XYChart.Data<>(formattedTimes.get(i), precipitation[i]));

        }

        crtForecast.getData().clear();
        crtForecast.getData().add(series);
        crtForecast.getData().add(seriesHumidity);
        crtForecast.getData().add(seriesPrecipitation);
        for (XYChart.Data<String, Number> data : series.getData()) {
            Node node = data.getNode();
            if (node != null) {
                node.setVisible(false); // Make the symbol node invisible
                node.setMouseTransparent(false); // Keep it interactive for hover
            }
            series.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 2px;");
        }
        for (XYChart.Data<String, Number> data : seriesHumidity.getData()) {
            Node node = data.getNode();
            if (node != null) {
                node.setVisible(false); // Make the symbol node invisible
                node.setMouseTransparent(false); // Keep it interactive for hover
            }
            seriesHumidity.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 2px;");
        }
        for (XYChart.Data<String, Number> data : seriesPrecipitation.getData()) {
            Node node = data.getNode();
            if (node != null) {
                node.setVisible(false); // Make the symbol node invisible
                node.setMouseTransparent(false); // Keep it interactive for hover
            }
            seriesPrecipitation.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 2px;");
        }
    }

    ArrayList<Node> lastNode = new ArrayList<>();

    @javafx.fxml.FXML
    private void trackMouse(MouseEvent event) {
        double sceneX = event.getSceneX();
        lblhover.setStyle("-fx-background-color: white; -fx-border-color: black;");
        lblhover.setVisible(false);
        lblhover.toFront();

        String nearestX = getNearestXValue(sceneX);
        if (nearestX == null) return;

        if (lastNode != null){
            for (Node node : lastNode)
                node.setVisible(false);
        }

        StringBuilder hoverText = new StringBuilder(nearestX + ":\n");

        for (XYChart.Series<String, Number> series : (List<XYChart.Series<String, Number>>) crtForecast.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getXValue().equals(nearestX)) {
                    String label = series.getName();
                    data.getNode().setVisible(true);
                    lastNode.add(data.getNode());
                    double value = data.getYValue().doubleValue();
                    if (label.equals("Temperature") && hasClicked == 0) {
                        value = (value - 32) * (5.0 / 9.0);
                        label += " (C)";
                    } else if (label.equals("Temperature")) {
                        label += " (F)";
                    }
                    hoverText.append(String.format("%s: %.1f\n", label, value));
                    break;
                }
            }
        }
        lblhover.setText(hoverText.toString().trim());
        lblhover.setLayoutX(sceneX + 10);
        lblhover.setLayoutY(event.getSceneY() - 10);
        lblhover.setVisible(true);
    }


    @javafx.fxml.FXML
    private  void exit(MouseEvent event){
        lblhover.setVisible(false);
        if (lastNode != null){
            for (Node node : lastNode)
                node.setVisible(false);
        }
    }

    private String getNearestXValue(double sceneX) {
        if (crtForecast.getData().isEmpty()) return null;

        XYChart.Series<String, Number> series = (XYChart.Series<String, Number>) crtForecast.getData().get(0);
        double minDistance = Double.MAX_VALUE;
        String closestX = null;

        for (XYChart.Data<String, Number> data : series.getData()) {
            Node node = data.getNode();
            if (node == null) continue;

            double nodeX = node.localToScene(node.getBoundsInLocal()).getCenterX();
            double distance = Math.abs(sceneX - nodeX);

            if (distance < minDistance) {
                minDistance = distance;
                closestX = data.getXValue();
            }
        }

        return closestX;
    }


}

