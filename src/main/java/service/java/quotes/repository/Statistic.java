package service.java.quotes.repository;

import java.util.Date;

public interface Statistic {
    Date getDay();

    int getCountQuotes();
}
