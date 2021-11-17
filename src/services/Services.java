package services;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

public class Services {
    public int getHTMLpage (String resource, HttpURLConnection response){
        String filePath = new File("").getAbsolutePath();
        int status = 200;
        filePath = filePath.concat("/doc/static");
        filePath = filePath.concat(resource);
        System.out.println("filepath" + filePath);

        FileReader fileReader;
        BufferedReader reader = null;
        PrintWriter out;
        try {
            out = new PrintWriter(response.getOutputStream());
        }catch (Exception e){
            System.out.println("No writer found");
            return  500;
        }
        try {
            fileReader = new FileReader(filePath);
            try {
                reader = new BufferedReader(fileReader);
            }catch (Exception e){
                System.out.println("Can't open the reader");
                return 500;
            }
        }catch (Exception e){
            System.out.println("file not found");
            status = 400;
        }
        try {
            if(reader != null) {
                String line = reader.readLine();
            }
            out.println("HTTP/1.0 "+status+" OK");
            out.println("Content-Type: text/html");
            out.println("Server: Bot");
            while (line != null)// repeat till the file is read
            {
                out.println(line);// print current line
                line = reader.readLine();// read next line
            }
        }catch (Exception e){
            System.out.println("Problem sending the file");
            return 500;
        }
        return status;
    }
}
