import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class MyTelegramBot extends TelegramLongPollingBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final String URL = "https://api.nasa.gov/planetary/apod?api_key=gpoxySiPqVz12UxbaZbN1rI05JMEdNZHSX7g1MOx";

    public MyTelegramBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        // TODO
        if (update.hasMessage() && update.getMessage().hasText()){
            String[] separatedAction = update.getMessage().getText().split(" ");
            String action = separatedAction[0];
            long chatID = update.getMessage().getChatId();
            switch (action){
                case "/help":
                    sendMessage("'этот бот присылает картинку дня по запросу /image", chatID);
                    break;
                case "/start":
                case "/image":
                    String image = Utils.getUrl(URL);
                    sendMessage(image, chatID);
                    break;
                case "/date":
                    image = Utils.getUrl(URL + "&date=" + separatedAction[1]);
                    sendMessage(image,chatID);
                    break;
                case "/dateHD":
                    image = Utils.getHDUrl(URL + "&date=" + separatedAction[1]);
                    sendMessage(image,chatID);
                    break;
                default:
                    sendMessage("я такой команды не знаю", chatID);

            }
        }
    }

    void sendMessage(String msg, long chatId) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText(msg);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        // TODO
        return BOT_TOKEN;
    }
}
