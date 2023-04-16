package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.util.logging.Logger;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;

import com.google.cloud.datastore.*;
import pt.unl.fct.di.apdc.firstwebapp.util.AuthToken;
import pt.unl.fct.di.apdc.firstwebapp.util.LoginData;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
    private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    private Gson g = new Gson();

    public LoginResource(){}

    @POST
    @Path("/op6")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response doLogin(LoginData data){
        LOG.fine("Login attempt by:" + data.username);

        if(!data.isValid()) return Response.status(Status.BAD_REQUEST).entity("Invalid username or password").build();

        Key key = datastore.newKeyFactory().setKind("User").newKey(data.username);
        Transaction txn = datastore.newTransaction();

        try{
            Entity user = txn.get(key);

            if(user==null){
                txn.rollback();
                return Response.status(Status.BAD_REQUEST).entity("Unregistered user.").build();
            }
            if(!(user.getString("password").equals(DigestUtils.sha512Hex(data.password)))){
                txn.rollback();
                return Response.status(Status.FORBIDDEN).entity("Wrong password.").build();
            }
            AuthToken token = new AuthToken(data.username, user.getString("role"));
            Key tKey = datastore.newKeyFactory().setKind("Token").newKey(data.username);

            Entity newToken = txn.get(tKey);
            newToken = Entity.newBuilder(tKey)
                    .set("role",token.role)
                    .set("username",token.username)
                    .set("tokenID",token.tokenID)
                    .set("validFrom",token.validFrom)
                    .set("validTo",token.validTo)
                    .build();
            txn.add(newToken);
            LOG.info("Token created");
            txn.commit();
            return Response.ok(token.tokenID).build();

        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
