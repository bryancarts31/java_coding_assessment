package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.authentication.LoginModel;
import model.response.Response;
import util.MessageKeys;
import util.MessageLoader;
import util.SendResponse;

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


        if(found){
            SendResponse.sendResponse(response, new Response(MessageKeys.SUCCESS_LOGIN, MessageLoader.get(MessageKeys.SUCCESS_LOGIN)));
        } else {
            SendResponse.sendResponse(response, new Response(MessageKeys.FAILED, MessageLoader.get(MessageKeys.FAILED)));
        }
    }

}
