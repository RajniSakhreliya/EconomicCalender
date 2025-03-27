package com.pomanager.economiccalender.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseModel implements Serializable {
    String id = "",
            time = "",
            country_code = "",
            pair = "",
            country_name = "",
            event = "",
            priority = "",
            priority_ = "",
            period = "",
            previous = "-",
            forecast = "-",
            actual = "-";
    List<Events> events = new ArrayList<>();

    String lang = "", event_description = "", event_source = "", created_at = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getEvent() {
        return event;
    }


    public String getPriority() {
        return priority;
    }


    public String getPeriod() {
        return period;
    }


    public String getPrevious() {
        return previous;
    }


    public String getForecast() {
        return forecast;
    }


    public String getActual() {
        return actual;
    }


    public List<Events> getEvents() {
        return events;
    }

    public String getEvent_description() {
        return event_description;
    }

    public static class Events implements Serializable {
        String time_value = "", actual_value = "", forecast_value = "", previous_value = "";

        public String getTime_value() {
            return time_value;
        }

        public String getActual_value() {
            return actual_value;
        }

        public String getForecast_value() {
            return forecast_value;
        }

    }
}
