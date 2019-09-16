package service.java.quotes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.java.quotes.models.Quote;
import service.java.quotes.repository.QuotesRepository;
import service.java.quotes.rssReaders.BashImRssReader;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RssService {
    private static final long INIT_DELAY = 0;
    private static final long DELAY = 6;

    private ScheduledExecutorService executor = null;

    @Autowired
    private QuotesRepository repository;

    @PostConstruct
    public void init() {
        log.info("RssService initialization...");
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(this::readRssTask,
                INIT_DELAY, DELAY, TimeUnit.HOURS);
    }

    private void readRssTask() {
        log.info("Read quotes...");
        List<Quote> quotes = BashImRssReader.readQuotes();
        log.info("Read " + quotes.size() + " quotes");
        saveQuotes(quotes);
    }

    private void saveQuotes(List<Quote> quotes) {
        List<Quote> newQuotes = quotes.stream().filter(q -> !repository.existsById(q.getId())).collect(Collectors.toList());
        log.info("Saving new quotes...");
        repository.saveAll(newQuotes);
        log.info("Saved " + newQuotes.size() + " quotes");
    }
}
