/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opgaver_Fredag;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruger
 */
public class Opgave1_gul {

    public static void main(String[] args) throws Exception {
        picoServer06();
    }

    private static void picoServer06() throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        String root = "pages";
        int count = 0;
        ExecutorService worker = Executors.newFixedThreadPool(4);

        while (true) { // keep listening (as is normal for a server)
            Socket socket = server.accept();
            worker.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        makeResponse(socket, root, worker);
                    } catch (IOException ex) {
                        Logger.getLogger(Opgave1_gul.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }); 
                    
            try {
                System.out.println("---- reqno: " + (++count) + " ----");
                HttpRequest req = new HttpRequest(socket.getInputStream());
                String path = req.getPath();
                if (path.endsWith(".html") || path.endsWith(".txt")) {
                    String html = getResourceFileContents(root + path);
                    String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + html;
                    socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                } else {
                    String res = "";
                    switch (path) {
                        case "/addournumbers":
                            res = addOurNumbers(req);
                            worker.submit(task)
                            break;
                        case "/multiplyOurNumbers":
                            res = multiplyOurNumbers(req);
                            break;
                        default:
                            res = "Unknown path: " + path;
                    }
                    String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + res;
                    socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
            } catch (Exception ex) {
                String httpResponse = "HTTP/1.1 500 Internal error\r\n\r\n"
                        + "UUUUPS: " + ex.getLocalizedMessage();
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }
        }
//        System.out.println( getFile("adding.html") );
    }

    public static void makeResponse(Socket socket, String root, ExecutorService worker) throws IOException {
        try {
            System.out.println("-----------------");
            HttpRequest req = new HttpRequest(socket.getInputStream());
            String path = root + req.getPath();
            String html = getResourceFileContents(path);
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + html;
            socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            System.out.println("<<<<<<<<<<<<<<<<<");
        } catch (Exception ex) {
            String httpResponse = "HTTP/1.1 500 Internal error\r\n\r\n"
                    + "UUUUPS: " + ex.getLocalizedMessage();
            socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
        } finally {
            if (socket != null) {
                socket.close();
            }
            
        }
        
    }

    /*
    It is not part of the curriculum (pensum) to understand this method.
    You are more than welcome to bang your head on it though.
     */
    private static String getResourceFileContents(String fileName) throws Exception {
        //Get file from resources folder
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL url = classLoader.getResource(fileName);
        String content = "";
        if (url != null) {
            File file = Paths.get(url.toURI()).toFile();
            content = new String(Files.readAllBytes(file.toPath()), UTF_8);
        }
        return content;

    }

    private static String addOurNumbers(HttpRequest req) throws Exception {
        String first = req.getParameter("firstnumber");
        String second = req.getParameter("secondnumber");
        int fi = Integer.parseInt(first);
        int si = Integer.parseInt(second);
//        String res = RES;
        String res = getResourceFileContents("resultAdd.tmpl");
        res = res.replace("$0", first);
        res = res.replace("$1", second);
        res = res.replace("$2", String.valueOf(fi + si));
        return res;
    }

    private static String multiplyOurNumbers(HttpRequest req) throws Exception {
        String first = req.getParameter("firstnumber");
        String second = req.getParameter("secondnumber");
        int fi = Integer.parseInt(first);
        int si = Integer.parseInt(second);
//        String res = RES;
        String res = getResourceFileContents("resultMultiply.tmpl");
        res = res.replace("$0", first);
        res = res.replace("$1", second);
        res = res.replace("$2", String.valueOf(fi * si));
        return res;
    }

    private static String RES = "<!DOCTYPE html>\n"
            + "<html lang=\"da\">\n"
            + "    <head>\n"
            + "        <title>Adding form</title>\n"
            + "        <meta charset=\"UTF-8\">\n"
            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "    </head>\n"
            + "    <body>\n"
            + "        <h1>Super: Resultatet af $0 + $1 blev: $2</h1>\n"
            + "        <a href=\"adding.html\">Læg to andre tal sammen</a>\n"
            + "    </body>\n"
            + "</html>\n";

    private static class myTask implements Runnable {

        public myTask() {
        }

        private myTask(Socket socket, String root) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void run() {
            makeResponse(socket, RES);
        }
    }

}
