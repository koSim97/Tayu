package com.example.tayu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherTest {

    @SerializedName("weather")    @Expose    private List<Weather> weather;    @SerializedName("base")    @Expose    private String base;
    @SerializedName("main")    @Expose    private Main main;    @SerializedName("visibility")    @Expose    private Integer visibility;
    @SerializedName("wind")    @Expose    private Wind wind;   @SerializedName("dt")    @Expose    private Integer dt;
    @SerializedName("id")    @Expose    private Integer id;    @SerializedName("name")    @Expose    private String name;
    @SerializedName("cod")    @Expose    private Integer cod;
    public List<Weather> getWeather() {        return weather;    }
    public Main getMain() {        return main;    }
    public Wind getWind() {        return wind;    }
    public Integer getId() {        return id;    }
    public void setId(Integer id) {        this.id = id;    }
    public String getName() {        return name;    }
    public void setName(String name) {        this.name = name;    }
    public Integer getCod() {        return cod;    }
    public void setCod(Integer cod) {        this.cod = cod;    }}
class Main {
    @SerializedName("temp")    @Expose    private Double temp;    @SerializedName("pressure")
    @Expose    private Integer pressure;    @SerializedName("humidity")    @Expose    private Integer humidity;
    @SerializedName("temp_min")    @Expose    private Double tempMin;    @SerializedName("temp_max")
    @Expose    private Double tempMax;
    public Double getTemp() {        return temp;    }
    public void setTemp(Double temp) {        this.temp = temp;    }
    public Integer getPressure() {        return pressure;    }
    public void setPressure(Integer pressure) {        this.pressure = pressure;    }
    public Integer getHumidity() {        return humidity;    }
    public void setHumidity(Integer humidity) {        this.humidity = humidity;    }
    public Double getTempMin() {        return tempMin;    }
    public void setTempMin(Double tempMin) {        this.tempMin = tempMin;    }
    public Double getTempMax() {        return tempMax;    }
    public void setTempMax(Double tempMax) {        this.tempMax = tempMax;    }
}
class Weather {
    @SerializedName("id")    @Expose    private Integer id;    @SerializedName("main")
    @Expose    private String main;    @SerializedName("description")
    @Expose    private String description;    @SerializedName("icon")    @Expose    private String icon;
    public Integer getId() {        return id;    }
    public void setId(Integer id) {        this.id = id;    }
    public String getMain() {        return main;    }
    public void setMain(String main) {        this.main = main;    }
    public String getDescription() {        return description;    }
    public void setDescription(String description) {        this.description = description;    }
    public String getIcon() {        return icon;    }
    public void setIcon(String icon) {        this.icon = icon;    }
}
class Wind {
    @SerializedName("speed")    @Expose    private Double speed;    @SerializedName("deg")    @Expose
    private Integer deg;
    public Double getSpeed() {        return speed;    }
    public void setSpeed(Double speed) {        this.speed = speed;    }
    public Integer getDeg() {        return deg;    }
    public void setDeg(Integer deg) {        this.deg = deg;    }
}