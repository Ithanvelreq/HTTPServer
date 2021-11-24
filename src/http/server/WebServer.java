///A Simple Web Server (WebServer.java)

package http.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;


/**
 * Example program from Chapter 1 Programming Spiders, Bots and Aggregators in
 * Java Copyright 2001 by Jeff Heaton
 * 
 * WebServer is a very simple web-server. Any request is responded with a very
 * simple web-page.
 * 
 * @author Jeff Heaton
 * @version 1.0
 */
public class WebServer {

  /**
   * Methode HTTP GET permettant de demander une ressource
   * @param ressource ressource demandee par le client
   * @param out Flux d'ecriture partant du serveur vers le client pour envoyer le header de la reponse
   * @param os Flux d'ecriture binaire vers le client pour lui envoyer la ressource en bytes
   */
  private void doGet(String ressource, PrintWriter out, OutputStream os){
    try {
      System.out.println("file ---->>> :" + ressource);
      String filePath = new File("").getAbsolutePath();
      filePath = filePath.concat("/doc/static");
      filePath = filePath.concat(ressource);
      System.out.println("filepath :" + filePath);
      File file = new File(filePath);


      if(file.exists() && file.isFile()) {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: " + ressource.substring(ressource.indexOf(".")+1));
        out.println("Content-length : "+ file.length());
        out.println("Server: Bot");
        out.println("");
        out.flush();

        byte[] content = Files.readAllBytes(Paths.get(filePath));
        os.write(content,0, content.length);
        os.flush();
      }else{
        out.println("HTTP/1.0 404 Not Found");
        out.println("Server: Bot");
        out.println("");
        out.println("ERROR 404 NOT FOUND");
      }
    }catch(Exception e){
      System.out.println("Error: " + e);
      out.println("500 Internal Server Error");
    }
  }

  /**
   * Methode HTTP POST permettant de publier des donnees dans une ressource
   * @param ressource ressource que le client veut modifier
   * @param out Flux d'ecriture partant du serveur vers le client pour envoyer le header de la reponse
   * @param in Buffer permettant de lire le texte du flux d'entree du client
   */
  private void doPost(String ressource, PrintWriter out, BufferedReader in){
    try {
      System.out.println("file ---->>> :" + ressource);
      String filePath = new File("").getAbsolutePath();
      filePath = filePath.concat("/doc/static");
      filePath = filePath.concat(ressource);
      System.out.println("filepath :" + filePath);
      File file = new File(filePath);
      BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(filePath,true));

      if(file.exists() && file.isFile()) {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: " + ressource.substring(ressource.indexOf(".")+1));
        out.println("Server: Bot");
        out.println("");
        out.flush();
        String ENDL = System.getProperty("line.separator");
        writer.write(ENDL.getBytes());
      }else {
        out.println("HTTP/1.0 201 Created");
        out.println("Server: Bot");
        out.println("");
        out.flush();
      }

      while(in.ready()  ) {
        byte[] content = in.readLine().getBytes();
        writer.write(content, 0, content.length);
      }
      writer.flush();
      writer.close();

    }catch(Exception e){
      System.out.println("Error: " + e);
      out.println("500 Internal Server Error");
    }
  }

  /**
   * Methode HTTP HEAD permettant d'envoyer les informations de la ressource demandee
   * @param ressource ressource demandee par le client
   * @param out Flux d'ecriture partant du serveur vers le client pour envoyer le header de la reponse
   */
  private void doHead(String ressource, PrintWriter out){
    try {
      System.out.println("file ---->>> :" + ressource);
      String filePath = new File("").getAbsolutePath();
      filePath = filePath.concat("/doc/static");
      filePath = filePath.concat(ressource);
      System.out.println("filepath :" + filePath);
      File file = new File(filePath);

      if(file.exists() && file.isFile()) {
        Path path = Paths.get(filePath);
        BasicFileAttributes attr =
                Files.readAttributes(path, BasicFileAttributes.class);
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: "+ressource.substring(ressource.indexOf(".")+1));
        out.println("Content-length : "+ file.length());
        out.println("creationTime: " + attr.creationTime());
        out.println("Last-modified : "+ attr.lastModifiedTime());
        out.println("Server: Bot");
        out.println("");
      }else{
        out.println("HTTP/1.0 404 Not Found");
        out.println("");
        out.println("ERROR 404 NOT FOUND");
      }
    }catch(Exception e){
      System.out.println("Error: " + e);
      out.println("500 Internal Server Error");
    }
  }

  /**
   * Methode HTTP DELETE permettant de supprimer une ressource
   * @param ressource ressource demandee par le client
   * @param out Flux d'ecriture partant du serveur vers le client pour envoyer le header de la reponse
   */
  private void doDelete(String ressource, PrintWriter out){
    try {
      System.out.println("file ---->>> :" + ressource);
      String filePath = new File("").getAbsolutePath();
      filePath = filePath.concat("/doc/static");
      filePath = filePath.concat(ressource);
      System.out.println("filepath :" + filePath);
      File file = new File(filePath);

      if(file.exists() && file.isFile()) {
        if(file.delete()) {
          out.println("HTTP/1.0 204 No Content");
          out.println("Server: Bot");
          out.println("");
          out.println("204 NO CONTENT");
        }else {
          out.println("HTTP/1.0 403 Forbidden");
          out.println("");
          out.println("ERROR 403 FORBIDDEN");
        }
      }else{
        out.println("HTTP/1.0 404 Not Found");
        out.println("");
        out.println("ERROR 404 NOT FOUND");
      }

    }catch(Exception e){
      System.out.println("Error: " + e);
      out.println("500 Internal Server Error");
    }
  }

  /**
   * Methode HTTP PUT permettant de publier des donnees dans une ressource en remplacant ce qui existait deja
   * @param ressource ressource que le client veut modifier
   * @param out Flux d'ecriture partant du serveur vers le client pour envoyer le header de la reponse
   * @param in Buffer permettant de lire le texte du flux d'entree du client
   */
  private void doPut(String ressource, PrintWriter out, BufferedReader in){
    try {
      System.out.println("file ---->>> :" + ressource);
      String filePath = new File("").getAbsolutePath();
      filePath = filePath.concat("/doc/static");
      filePath = filePath.concat(ressource);
      System.out.println("filepath :" + filePath);
      File file = new File(filePath);
      BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(filePath));

      if(file.exists() && file.isFile()) {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: " + ressource.substring(ressource.indexOf(".")+1));
        out.println("Server: Bot");
        out.println("");
        out.flush();
      }else {
        out.println("HTTP/1.0 201 Created");
        out.println("Server: Bot");
        out.println("");
        out.flush();
      }

      while(in.ready()  ) {
        byte[] content = in.readLine().getBytes();
        writer.write(content, 0, content.length);
      }
      writer.flush();
      writer.close();

    }catch(Exception e){
      System.out.println("Error: " + e);
      out.println("500 Internal Server Error");
    }
  }

  /**
   * WebServer constructor.
   */
  protected void start() {
    ServerSocket s;

    System.out.println("Webserver starting up on port 3000");
    System.out.println("(press ctrl-c to exit)");
    try {
      // create the main server socket
      s = new ServerSocket(3000);
    } catch (Exception e) {
      System.out.println("Error: " + e);
      return;
    }

    System.out.println("Waiting for connection");
    for (;;) {
      try {
        // wait for a connection
        Socket remote = s.accept();
        // remote is now the connected socket
        System.out.println("Connection, sending data.");
        BufferedReader in = new BufferedReader(new InputStreamReader(
            remote.getInputStream()));
        PrintWriter out = new PrintWriter(remote.getOutputStream());
        OutputStream os = remote.getOutputStream();

        // read the data sent. We basically ignore it,
        // stop reading once a blank line is hit. This
        // blank line signals the end of the client HTTP
        // headers.
        String str = ".";

        str = in.readLine();
        System.out.println(str);
        String[] method = str.split(" ");
        String requestMethod = method[0];
        System.out.println("Methode de request :::: " + method[0]);
        System.out.println("Ressource :::: " + method[1]);

        while (str != null && !str.equals("")) {
          str = in.readLine();
          System.out.println(str);
        }

        // Send the HTML page
        switch (requestMethod){
          case "GET":
            doGet(method[1],out, os);
            break;

          case "POST":
            doPost(method[1],out,in);
            break;

          case "HEAD":
            doHead(method[1],out);
            break;

          case "DELETE":
            doDelete(method[1],out);
            break;

          case "PUT":
            doPut(method[1],out,in);
            break;

          default:
            out.println("500 Internal Server Error");
            //error request method
            break;
        }

        out.flush();
        remote.close();
      } catch (Exception e) {
        System.out.println("Error: " + e);
      }
    }
  }

  /**
   * Start the application.
   * 
   * @param args
   *            Command line parameters are not used.
   */
  public static void main(String args[]) {
    WebServer ws = new WebServer();
    ws.start();
  }
}
