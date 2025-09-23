package controller;

import model.authentication.RegisterModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;


public class AuthenticationController extends HttpServlet {
    private static final String FILE_PATH ="C:\\Java\\Assessment\\Coding_Assesment\\src\\main\\data\\user.json";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        String action = request.getParameter("action");

        if("register".equalsIgnoreCase(action)){
            registerUser(request,response);
        }else if("login".equalsIgnoreCase(action)){
            loginUser(request,response);
        }else{
            response.getWriter().write("Unknown Action.");
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
       String username = request.getParameter("username");
       String password = request.getParameter("username");
       String confirmPassword = request.getParameter("username");
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
         JSONArray users = new JSONArray(content);

         for(int i = 0; i<users.length(); i++){
             JSONObject user = users.getJSONObject(i);
             if(user.getString("username").equals(username)){

             }
         }

        int generateId = users.length() + 1;
         RegisterModel user = new RegisterModel(generateId,username,password,confirmPassword,email);
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{

    }
}
