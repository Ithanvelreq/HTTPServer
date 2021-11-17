import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.net.URL;
import services.Services;

public class ServicesTest {

    @Test
    void testSendFile(){
        try {
            URL url = new URL("http://localhost:3000");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            Services services = new Services();
            int x = services.getHTMLpage("example.html", request);
        }catch (Exception e){
            assert(false);
        }
    }
}
