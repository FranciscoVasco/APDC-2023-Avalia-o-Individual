package pt.unl.fct.di.apdc.firstwebapp.util;

public class LoginData {
    public String password;
    public String username;

    public LoginData(){}

    public LoginData(String username,String password){
        this.username = username;
        this.password = password;
    }

    public boolean isValid() {
        return username != null && password != null;
    }
}
