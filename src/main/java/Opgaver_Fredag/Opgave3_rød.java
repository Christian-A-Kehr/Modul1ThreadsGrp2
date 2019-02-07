/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opgaver_Fredag;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Bruger
 */
public class Opgave3_rød {
    

    public static void main(String[] args) throws Exception {
        picoServer02(8080);
//        picoServer04();
//        picoServer05();
//        picoServer06();
    }

    /*
    Plain server that just answers what date it is.
    It ignores all path and parameters and really just tell you what date it is
     */
    private static void picoServer01() throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        while (true) { // spin forever } }
            try (Socket socket = server.accept()) {
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            }

        }
    }

    /*
    Same server, but this one writes to system.out to show what info we get
    from the browser/client when we it sends a request to the server.
    It still just tell the browser what time it is.
     */
    private static void picoServer02(int portNr) throws Exception {
        try {
            final ServerSocket server = new ServerSocket(portNr);
            System.out.println("Listening for connection on port " + portNr + "....");
            while (true) { // keep listening (as is normal for a server)
                try (Socket socket = server.accept()) {
                    System.out.println("-----------------");
                    System.out.println("Client: " + socket.getInetAddress().getHostName());
                    System.out.println("-----------------");
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while (!((line = br.readLine()).isEmpty())) {
                        System.out.println(line);
                    }
                    System.out.println(">>>>>>>>>>>>>>>");
                    Date today = new Date();
                    String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today + " Mads! \n";

                    socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }

            }
        } catch (Exception e) {
            picoServer02(portNr + 1);
        }
    }

    /*
    This server uses a HttpRequest object to *parse* the text-request into a 
    java object we can then use to examine the different aspect of the request
    using the getters of the HttpRequest object.
    It still just returns the date to the client.
     */
    private static void picoServer03() throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        int count = 0;
        while (true) { // keep listening (as is normal for a server)
            try (Socket socket = server.accept()) {
                System.out.println("---- Request: " + count++ + " --------");
                HttpRequest req = new HttpRequest(socket.getInputStream());

                System.out.println("Method: " + req.getMethod());
                System.out.println("Protocol: " + req.getProtocol());
                System.out.println("Path: " + req.getPath());
                System.out.println("Parameters:");
                for (Map.Entry e : req.getParameters().entrySet()) {
                    System.out.println("    " + e.getKey() + ": " + e.getValue());
                }
                System.out.println("Headers:");
                for (Map.Entry e : req.getHeaders().entrySet()) {
                    System.out.println("    " + e.getKey() + ": " + e.getValue());
                }

                System.out.println("---- BODY ----");
                System.out.println(req.getBody());
                System.out.println("==============");
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            }
        }
    }

    /*
    This server uses the path of the HttpRequest object to return a html file to
    the browser. See the notes on Java ressources.
     */
    private static void picoServer04() throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        String root = "pages";
        while (true) { // keep listening (as is normal for a server)
            try (Socket socket = server.accept()) {
                System.out.println("-----------------");
                HttpRequest req = new HttpRequest(socket.getInputStream());
                String path = root + req.getPath();
                String html = getResourceFileContents(path);
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + html;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                System.out.println("<<<<<<<<<<<<<<<<<");
            }
        }
//        System.out.println( getFile("adding.html") );
    }

    /*
    This server has exception handling - so if something goes wrong we do not
    have to start it again. (this is a yellow/red thing for now)
     */
    private static void picoServer05() throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        String root = "pages";
        while (true) { // keep listening (as is normal for a server)
            Socket socket = server.accept();;
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
//        System.out.println( getFile("adding.html") );
    }

    /*
    This server requires static files to be named ".html" or ".txt". Other path
    names is assumed to be a name of a service.
     */
    private static void picoServer06() throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        String root = "pages";
        int count = 0;
        while (true) { // keep listening (as is normal for a server)
            Socket socket = server.accept();;
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

}