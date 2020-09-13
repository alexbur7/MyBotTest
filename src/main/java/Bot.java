import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message!=null && message.hasText()){
            String text = message.getText();
            if ("/help".equals(text)) {
                sendMsg(message, "Чем могу помочь?");
            } else if ("/settings".equals(text)) {
                sendMsg(message, "Что будете настраивать?");
            }
            else if("соси".equals(text)){
                sendMsg(message,"Сам соси, пидор!");
            }
            else if("/start".equals(text)){
                sendMsg(message,"Я бот Витя, я буду тебе помогать узнавать курсы валют");
            }
            else if ("/course".equals(text)){
                sendMsg(message,"Окей, тогда вводи валюты в формате USDRUB");
            }
            else {
                try {
                    sendMsg(message, Currency.getCurrency(message.getText(),model));
                } catch (IOException e) {
                    sendMsg(message, "Прости ,брат, такой валюты нет");
                }
            }
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try{
            execute(sendMessage);
            setButtons(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        ArrayList <KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("/start"));
        keyboardRow.add(new KeyboardButton("/help"));
        keyboardRow.add(new KeyboardButton("/course"));
       // keyboardRow.add(new KeyboardButton("/settings"));

        keyboardRows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public String getBotUsername() {
        return "VityaTestBot";
    }

    public String getBotToken() {
        return "1334302101:AAF48OLHXFQWOKpmrqeX_nboNhOQZABFWzg";
    }
}
