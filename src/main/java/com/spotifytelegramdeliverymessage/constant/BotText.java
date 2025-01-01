package com.spotifytelegramdeliverymessage.constant;

public class BotText {

    public static final String START_TEXT = "Welcome on Spotify Telegram Bot. " +
            "Here you will receive information about new releases of artists you are subscribed to." +
            "In order to receive this information, firstly you need to enter 'register' command and your email which is linked to the Spotify App. \n" +
            "Example: /register example@example.com";

    public static final String CONFIRMATION_TEXT = """
            To confirm your email, enter the 6-digit code that was sent to your email.\s
            Please note that until you confirm your email, you will not be able to receive notifications about new releases.\s
            To confirmation enter the command /confirm and your code.\s
            Example: /confirm 000000""";

    public static final String SUCCESSFULLY_CONFIRMATION_TEXT = "Congratulations! " +
            "Your code is correct and your email has been successfully verified.";

    public static final String FAILED_CONFIRMATION_TEXT =
            "You entered an incorrect code. Please try again.";

    public static final String SUBSCRIBED_TEXT = "You have subscribed to the newsletter for information about new releases. \n" +
            "You will receive information about new releases from artists you are subscribed to at 8 AM";

    public static final String UNSUBSCRIBED_TEXT =
            "You have successfully unsubscribed and will no longer receive notifications about new releases.";
}
