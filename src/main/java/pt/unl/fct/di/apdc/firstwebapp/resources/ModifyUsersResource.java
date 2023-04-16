package pt.unl.fct.di.apdc.firstwebapp.resources;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.datastore.*;
import pt.unl.fct.di.apdc.firstwebapp.util.ModificationData;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

public class ModifyUsersResource {

    private static final Logger LOG = Logger.getLogger(ModifyUsersResource.class.getName());

    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    public ModifyUsersResource() {}

    @PUT
    @Path("/op4")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doModification(ModificationData data) {
        LOG.fine("Attempt to register user: " + data.username);


        Transaction txn = datastore.newTransaction();

        return Response.status(Response.Status.BAD_REQUEST).entity("placeholder").build();
    }
}
