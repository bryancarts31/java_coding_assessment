package model.authentication;

import org.json.JSONObject;

public class RegisterModel {
    private int Id;
    private String Username;
    private String Password;
    private String ConfirmPassword;
    private String Email;

    public RegisterModel(int id,String username, String password,String confirmPassword,String email){
        this.Id = id;
        this.Username = username;
        this.Password = password;
        this.ConfirmPassword = confirmPassword;
        this.Email = email;
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public JSONObject saveToJson(){
        JSONObject obj = new JSONObject();
        obj.put("id",Id);
        obj.put("username",Username);
        obj.put("password",Password);
        obj.put("email",Email);
        return obj;
    }
}
