package service.java.quotes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class BotService extends TelegramLongPollingBot {
    static final Logger log = LoggerFactory.getLogger(BotService.class);
    final int RECONNECT_PAUSE = 10000;
    static final String cmdGetJoke = "joke";
    private String botUserName;
    private String botToken;
    private RssService rssService;

    public BotService(
            @Value("${telegram.bot.name}") String botUserName,
            @Value("${telegram.bot.token}") String botToken,
            RssService rssService) {
        this.botUserName = botUserName;
        this.botToken = botToken;
        this.rssService = rssService;
        botConnect();
    }

    public String getBotUsername() {
        return botUserName;
    }

    public String getBotToken() {
        return botToken;
    }

    private void botConnect() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            log.info("TelegramAPI started. Look for messages");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        String chatId = "";
        String inputText = "";
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                SendMessage sendMessageRequest = new SendMessage();
                chatId = message.getChatId().toString();
                inputText = message.getText();
                if (inputText != null && inputText.indexOf(cmdGetJoke) >= 0) {
                    String text = rssService.getRandomQuotes().getDescription();
                    sendMessageRequest.setChatId(chatId);
                    sendMessageRequest.setText(text);
                    try {
                        execute(sendMessageRequest); //at the end, so some magic and send the message ;)
                    } catch (TelegramApiException e) {
                        log.error("Error send message with chatId=" + chatId);
                    }
                }
            }
        }
        log.info(inputText + ", chatId=" + chatId);
    }

    public void sendString(String chatId, String message) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(chatId);
        sendMessageRequest.setText(message);
        try {
            execute(sendMessageRequest);
        } catch (TelegramApiException e) {
            //do some error handling
        }
        log.info("Send message: " + message + ", with chatId = " + chatId);
    }
}

