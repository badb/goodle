package edu.mimuw.goodle.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import edu.mimuw.goodle.client.GreetingService;
import edu.mimuw.goodle.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

  public String greetServer(String input) throws IllegalArgumentException {
    // Verify that the input is valid. 
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException(
          "Name must be at least 4 characters long");
    }

    String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
    userAgent = escapeHtml(userAgent);
    String toRet = "";

    try {
        URL url = new URL("http://apps.usos.edu.pl/services/courses/classtypes_index?format=json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            toRet += line;
        }
        reader.close();

    } catch (MalformedURLException e) {
        // ...
    } catch (IOException e) {
        // ...
    }
    
    return toRet;
    		/*"Hello, " + input + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent;*/
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }
}
