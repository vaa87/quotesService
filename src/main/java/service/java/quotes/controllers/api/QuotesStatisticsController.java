package service.java.quotes.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.java.quotes.models.QuoteStatistic;
import service.java.quotes.repository.QuotesRepository;
import service.java.quotes.repository.Statistic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api(description="Get statistics on quotes")
@RestController
public class QuotesStatisticsController {
    private final QuotesRepository repository;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    QuotesStatisticsController(QuotesRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Get statistics by days of downloading quotes")
    @GetMapping(path = "/quotes/statistics", produces = "application/json")
    List<QuoteStatistic> statistics() {
        List<Statistic> statistics = repository.getStatistics();
        return statistics.stream().filter(s -> s.getDay() != null)
                .map(s -> {
                    Date day = s.getDay();
                    int count = s.getCountQuotes();
                    return new QuoteStatistic(formatter.format(day), count);
                })
                .collect(Collectors.toList());
    }
}
