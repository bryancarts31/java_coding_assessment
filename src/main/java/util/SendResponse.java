package util;


import com.fasterxml.jackson.databind.ObjectMapper;
import model.response.Response;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendResponse extends HttpServlet {
    public static void sendResponse(HttpServletResponse response, Response jresponse) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(jresponse));
    }
}
