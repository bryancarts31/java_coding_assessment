package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.authentication.RegisterModel;
import model.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import util.MessageLoader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        if(!password.equals(confirmPassword)){
            response.getWriter().write("Passwords do not match");
            return;
        }

        File file = new File(FILE_PATH);
        if (!file.exists()){
            file.createNewFile();
            Files.write(Paths.get(FILE_PATH),"[]".getBytes());
        }

        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        Response jresponse = null;
        boolean isExisting = false;
        JSONArray users = new JSONArray(content);

        for(int i = 0; i<users.length(); i++){
            JSONObject user = users.getJSONObject(i);
            if(user.getString("username").equals(username)){
                String message = MessageLoader.get("existing-username");
                jresponse = new Response("existing-username",message);
                isExisting = true;
                break;
            } else if (user.getString("email").equals(email)) {
                String message = MessageLoader.get("existing-email");
                jresponse = new Response("existing-email",message);
                isExisting = true;
                break;
            }
        }

        if(!isExisting){
            int generateId = users.length() + 1;
            RegisterModel createUser = new RegisterModel(generateId,username,password,confirmPassword,email);

            users.put(createUser.saveToJson());

            Files.write(Paths.get(FILE_PATH), users.toString().getBytes());

            String message = MessageLoader.get("success-register");
            jresponse = new Response("success", message);
            jresponse.setRedirectUrl("/login");
        }

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mapper.writeValueAsString(jresponse);
        response.getWriter().write(jsonMessage);
    }

}
