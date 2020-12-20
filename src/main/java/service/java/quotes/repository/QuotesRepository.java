package service.java.quotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service.java.quotes.models.Quote;

import java.util.List;

public interface QuotesRepository extends JpaRepository<Quote, String> {

    @Query(
            value = "SELECT q.day as day, COUNT(q.id) as countQuotes "
                    + "FROM (select id, truncate(created_at) AS day FROM Quote) AS q "
                    + "GROUP BY day;",
            nativeQuery = true
    )
    List<Statistic> getStatistics();

    @Query(
            value = "SELECT * FROM Quote ORDER BY RANDOM() LIMIT 1;",
            nativeQuery = true
    )
    Quote findRandomQuote();
}
