package pt.unl.fct.di.apdc.firstwebapp.util;

public class RemoveData {

    public String username2del;
    public String username;
    public String tokenID;

    public RemoveData(){}

    public RemoveData(String username,String username2del,String tokenID) {
        this.username2del = username2del;
        this.username = username;
        this.tokenID = tokenID;
    }
    public boolean outRoles(String role1, String role2) {
        if(role1.equals("SU")) return true;
        if(role1.equals("GS") && (role2.equals("USER") || role2.equals("GBO"))) return true;
        if(role1.equals("GBO") && role2.equals("USER")) return true;
        return false;
    }
}
