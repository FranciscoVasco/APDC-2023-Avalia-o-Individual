package pt.unl.fct.di.apdc.firstwebapp.util;



public class ListData {

    public String username;
    public String tokenID;
    public ListData(){

    }

    public ListData(String username,String tokenID){
        this.tokenID = tokenID;
        this.username = username;
    }
}
