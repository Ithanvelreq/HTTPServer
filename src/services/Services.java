package services;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Services {
    public void  getHTMLpage (String ressource, HttpServletResponse response){
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/doc/static");
        filePath = filePath.concat(ressource);
        System.out.println("filepath" + filePath);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        {
            out.println(line);// print current line

            line = reader.readLine();// read next line
        }
    }
}
