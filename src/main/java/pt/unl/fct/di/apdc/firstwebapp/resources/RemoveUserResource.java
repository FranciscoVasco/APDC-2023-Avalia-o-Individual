package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.util.logging.Logger;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.*;
import pt.unl.fct.di.apdc.firstwebapp.util.RemoveData;

@Path("/remove")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RemoveUserResource {

    private static final Logger LOG = Logger.getLogger(RemoveUserResource.class.getName());

    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    public RemoveUserResource() {}

    @DELETE
    @Path("/op2")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doRemove(RemoveData data) {
        LOG.fine("Attempt to remove user: " + data.username2del);
        Transaction txn = datastore.newTransaction();

        Key delKey = datastore.newKeyFactory().setKind("User").newKey(data.username2del);
        Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);

        Key delTokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.username2del);
        Key userTokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.username);

        try {
            Entity user2del = txn.get(delKey);
            Entity user = txn.get(userKey);

            if(user == null || user2del == null){
                txn.rollback();
                return Response.status(Status.BAD_REQUEST).entity("One of the users does not exist").build();
            }
            String delRole = user2del.getString("role");
            String userRole = user.getString("role");

            Entity delToken = txn.get(delTokenKey);
            Entity userToken = txn.get(userTokenKey);
            if(!(userToken.getLong("validTo") - System.currentTimeMillis() > 0 ) || !(data.tokenID.equals(userToken.getString("tokenID")))) {
                txn.delete(userTokenKey);
                return Response.status(Status.FORBIDDEN).entity("Login to remove user.").build();
            }

            if(user.getString("state").equals("INACTIVE")){
                txn.rollback();
                return Response.status(Status.FORBIDDEN).entity("Inactive account").build();
            }

            if(data.outRoles(userRole,delRole) || (userRole.equals("USER") && data.username2del.equals(data.username))){
                txn.delete(delKey);
                txn.delete(delTokenKey);
                LOG.info("Deleted " + data.username2del);
                txn.commit();
                return Response.ok("{removed}").build();
            } else {
                LOG.info("Not allowed");
                return Response.status(Status.FORBIDDEN).entity("No permission").build();
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }
}