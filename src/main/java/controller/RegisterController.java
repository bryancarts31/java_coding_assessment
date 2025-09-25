package controller;

import model.authentication.RegisterModel;
import model.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import util.MessageKeys;
import util.MessageLoader;
import util.SendResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final String FILE_PATH ="C:\\Java\\Assessment\\Coding_Assesment\\src\\main\\data\\user.json";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/pages/authentication/register.html");
        requestDispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        JSONArray users = new JSONArray(content);

        if(isEmpty(username,password,confirmPassword,email)){
            SendResponse.sendResponse(response, new Response(MessageKeys.REQUIRED, MessageLoader.get(MessageKeys.REQUIRED)));
            return;
        }if(password.length() < 6){
            SendResponse.sendResponse(response, new Response(MessageKeys.PASSWORD_LENGTH, MessageLoader.get(MessageKeys.PASSWORD_LENGTH)));
            return;
        } if (!password.equals(confirmPassword)) {
            SendResponse.sendResponse(response, new Response(MessageKeys.PASSWORD_MISMATCH, MessageLoader.get(MessageKeys.PASSWORD_MISMATCH)));
            return;
        }if(!email.matches(EMAIL_REGEX)){
            SendResponse.sendResponse(response, new Response(MessageKeys.INVALID_EMAIL, MessageLoader.get(MessageKeys.INVALID_EMAIL)));
            return;
        }

        for(int i = 0; i<users.length(); i++){
            JSONObject user = users.getJSONObject(i);
            if(user.getString("username").equals(username)){
                SendResponse.sendResponse(response, new Response(MessageKeys.EXISTING_USERNAME, MessageLoader.get(MessageKeys.EXISTING_USERNAME)));
                return;
            } else if (user.getString("email").equals(email)) {
                SendResponse.sendResponse(response, new Response(MessageKeys.EXISTING_EMAIL, MessageLoader.get(MessageKeys.EXISTING_EMAIL)));
                return;
            }
        }

        int generateId = users.length() + 1;
        RegisterModel createUser = new RegisterModel(generateId,username,password,confirmPassword,email);
        users.put(createUser.saveToJson());

        Files.write(Paths.get(FILE_PATH), users.toString().getBytes());

        Response success = new Response(MessageKeys.SUCCESS_REGISTER,MessageLoader.get(MessageKeys.SUCCESS_REGISTER));
        success.setRedirectUrl("/login");
        SendResponse.sendResponse(response,success);
    }

    private boolean isEmpty(String... fields){
        for(String field: fields){
            if(field == null || field.trim().isEmpty())
                return true;
        }
        return false;
    }
}
