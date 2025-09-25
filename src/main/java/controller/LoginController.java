package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.authentication.LoginModel;
import model.response.Response;
import util.MessageLoader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final String File_Path ="C:\\Java\\Assessment\\Coding_Assesment\\src\\main\\data\\user.json";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/pages/authentication/login.html");
        requestDispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ObjectMapper mapper = new ObjectMapper();
        List<LoginModel> findUser = mapper.readValue(new File(File_Path), new TypeReference<List<LoginModel>>() {});

        boolean found = findUser.stream()
                .anyMatch(user -> user.getUsername().equals(username) &&
                        user.getPassword().equals(password));

        Response jresponse = null;
        boolean isSuccess = false;

        if(found){
            String message = MessageLoader.get("success-login");
            jresponse = new Response("success",message);

        } else {
            String message = MessageLoader.get("failed");
            jresponse = new Response("failed",message);
        }
        response.setContentType("application/json");
        String jsonMessage = mapper.writeValueAsString(jresponse);
        response.getWriter().write(jsonMessage);
    }

}
