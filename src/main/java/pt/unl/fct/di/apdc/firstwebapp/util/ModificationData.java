package pt.unl.fct.di.apdc.firstwebapp.util;

import java.util.regex.Pattern;

public class ModificationData {

    public String username;
    public String password;
    public String name;
    public String email;
    public String profile;
    public String landPhone;
    public String cellPhone;
    public String occupation;
    public String workplace;
    public String address;
    public String compAddress;
    public String NIF;
    public String imageUrl;

    public ModificationData(){}

    public ModificationData(String profile,String landPhone,String cellPhone,String occupation,String workplace, String address, String compAddress, String NIF, String imageUrl){
        this.profile = profile;
        this.landPhone = landPhone;
        this.cellPhone = cellPhone;
        this.occupation = occupation;
        this.workplace = workplace;
        this.address = address;
        this.compAddress = compAddress;
        this.NIF = NIF;
        this.imageUrl = imageUrl;
    }

    public void optionalAttributes(){
        if(profile == null || profile.equals("")){
            this.profile = "undef";
        }
        if(landPhone == null || landPhone.equals("")){
            this.landPhone = "undef";
        }
        if(cellPhone == null || cellPhone.equals("")){
            this.cellPhone = "undef";
        }
        if(occupation == null || occupation.equals("")){
            this.occupation = "undef";
        }
        if(workplace == null || workplace.equals("")){
            this.workplace = "undef";
        }
        if(address == null || address.equals("")){
            this.address = "undef";
        }
        if(compAddress == null || compAddress.equals("")){
            this.compAddress = "undef";
        }
        if(NIF == null || NIF.equals("")){
            this.NIF = "undef";
        }
        if(imageUrl == null || imageUrl.equals("")){
            this.address = "undef";
        }
    }
}
