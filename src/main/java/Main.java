import bls.actions.InitialActions;
import mail.MailServices;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) throws AWTException, InterruptedException {

        InitialActions initialActions = new InitialActions();
            initialActions.subscribe("cc32b94c-97f5-4367-9da2-21faae399cdd@mailslurp.com","699789012");
//        initialActions.login("ab28fb@gmail.com");

    }

    public static String getContent(String s) {
        try {
            URL u = new URL(s);
            URLConnection c = u.openConnection();
            //we lie, java user-agents are sometimes blacklisted
            c.setRequestProperty("User-Agent", "MSIE 7.0");
            InputStream r = c.getInputStream();
            String ret = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(r));
            for (String line; (line = reader.readLine()) != null; ) ret += line;
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "some bullshit went wrong here";
    }
}
