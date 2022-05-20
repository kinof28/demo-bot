package mail;

import com.mailslurp.apis.*;
import com.mailslurp.clients.*;
import com.mailslurp.clients.auth.ApiKeyAuth;
import com.mailslurp.models.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MailServices {
    private final String MY_API_KEY = "7f496bc2db3ca5e7df17a1a4c25d7d65fb777e4ca9f6ea409f74b057d7bbe528";
    private ApiClient defaultClient;
    private final Long TIMEOUT_MILLIS = 300000L;
    private ApiKeyAuth API_KEY;

    public MailServices(){
        this.defaultClient = Configuration.getDefaultApiClient();
        this.defaultClient.setBasePath("https://api.mailslurp.com");
        // Configure API key authorization: API_KEY
        this.API_KEY = (ApiKeyAuth) defaultClient.getAuthentication("API_KEY");
        this.API_KEY.setApiKey(MY_API_KEY);
    }

    public String createEmail() {
        CommonActionsControllerApi apiInstance = new CommonActionsControllerApi(defaultClient);
        Boolean allowTeamAccess = false; // Boolean |
        Boolean useDomainPool = false; // Boolean |
        Long expiresIn = null; // Long |
        String inboxType = "HTTP_INBOX"; // String |
        String description = "description_example"; // String |
        String name = "example"+Math.random()*10000; // String |
        List<String> tags = Arrays.asList(); // List<String> |
        Boolean favourite = false; // Boolean |
        Boolean virtualInbox = false; // Boolean |
        try {
            InboxDto result = apiInstance.createRandomInbox(allowTeamAccess, useDomainPool, null, expiresIn, null, inboxType, description, name, tags, favourite, virtualInbox);
            System.out.println(result);
            return result.getEmailAddress();
        } catch (ApiException e) {
            System.err.println("Exception when calling CommonActionsControllerApi#createRandomInbox");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return "";
        }
    }
    public void readMail(String emailAddress) throws ApiException {
        WaitForControllerApi apiInstance = new WaitForControllerApi(defaultClient);
        UUID inboxId = UUID.fromString(emailAddress.substring(0,36)); // UUID | Id of the inbox we are fetching emails from
        Long timeout = 56L; // Long | Max milliseconds to wait
        Boolean unreadOnly = false; // Boolean | Optional filter for unread only.
        String sort = "sort_example"; // String | Sort direction
        Long delay = 1000L; // Long | Max milliseconds delay between calls
        try {
        Thread.sleep(2000);
            Email result = apiInstance.waitForLatestEmail(inboxId, timeout, unreadOnly,null, null, null, delay);
            System.out.println(result.getBody());
        } catch (ApiException e) {
            System.err.println("Exception when calling WaitForControllerApi#waitForLatestEmail");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }catch(InterruptedException e){
            System.err.println("Exception when calling Thread.sleep");
            System.err.println("error message: " + e.getMessage());
            System.err.println("Reason: " );
            e.printStackTrace();
        }
    }

//    public void useTempMailApi() throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://privatix-temp-mail-v1.p.rapidapi.com/request/mail/id/null/"))
//                .header("X-RapidAPI-Host", "privatix-temp-mail-v1.p.rapidapi.com")
//                .header("X-RapidAPI-Key", "1d17c23cffmshe16b51cbe823204p1a12adjsn17c554225e6f")
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
//    }
}
