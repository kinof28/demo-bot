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
//        HttpRequest request = HttpRequest.newBuilder(new URI("https://algeria.blsspainvisa.com/login.php")).header("User-Agent", "MSIE 7.0").GET().build();
//        HttpResponse response= HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(getContent("https://algeria.blsspainvisa.com/login.php"));
//        System.out.println(response.body());
//        Robot robot = new Robot();
//        StringSelection stringSelection = new StringSelection("Salam....");
//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        clipboard.setContents(stringSelection, null);
//        Thread.sleep(3000);
//        for (int i = 0; i < 5; i++) {
//            Thread.sleep(1000);
//            robot.keyPress(KeyEvent.VK_CONTROL);
//            robot.keyPress(KeyEvent.VK_V);
//            robot.keyRelease(KeyEvent.VK_CONTROL);
//            robot.keyRelease(KeyEvent.VK_V);
//            robot.keyPress(KeyEvent.VK_ENTER);
//            robot.keyRelease(KeyEvent.VK_ENTER);
//        }
//        InitialActions.start();
        try{

//        MailServices.establishConnection();
//            MailServices services=new MailServices();
            InitialActions initialActions=new InitialActions();
//            String email=services.createEmail();
            initialActions.subscribe("cc32b94c-97f5-4367-9da2-21faae399cdd@mailslurp.com","699789012");

//            services.readMail("cc32b94c-97f5-4367-9da2-21faae399cdd@mailslurp.com");
//            MailServices.useTempMailApi();
//            System.out.println("8ad0380f-1e26-4b89-8d1f-e81c4a9f0cb3".length());
        }catch (Exception e){
//            System.out.println("exception : "+e);
            e.printStackTrace();
        }

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
