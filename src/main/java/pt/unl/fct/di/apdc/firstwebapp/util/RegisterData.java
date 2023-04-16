package pt.unl.fct.di.apdc.firstwebapp.util;

import java.util.regex.Pattern;

public class RegisterData {
    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "Inactive";

    public String username;
    public String password1;
    public String password2;
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


    public RegisterData() {

    }

    public RegisterData(String username,String email,String name, String password1, String password2, String profile,String landPhone,String cellPhone,String occupation,String workplace, String address, String compAddress, String NIF, String imageUrl) {
        this.username = username;
        this.name = name;
        this.password1 = password1;
        this.password2= password2;
        this.email = email;
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
    public boolean allFilled(){
        return password1 != null && password2 != null && email!=null && username!=null && name!=null;
    }
    public boolean passwordMatches(){
        return password1.equals(password2);
    }

    public boolean validEmail() {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
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
