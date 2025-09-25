package model.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginModel {

    private String Username;
    private String Password;

    public LoginModel(){};
    public LoginModel(String username, String password){
        this.Username = username;
        this.Password = password;
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

}
