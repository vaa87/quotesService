package service.java.quotes.models;

import io.swagger.annotations.ApiModelProperty;

public class QuoteStatistic {
    @ApiModelProperty(notes = "Day in the format 'yyyy-MM-dd'")
    private String day;

    @ApiModelProperty(notes = "Count of quotes added on this day")
    private int count;

    public QuoteStatistic(String day, int count) {
        this.day = day;
        this.count = count;
    }

    public String getDay() {
        return day;
    }

    public int getCount() {
        return count;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
