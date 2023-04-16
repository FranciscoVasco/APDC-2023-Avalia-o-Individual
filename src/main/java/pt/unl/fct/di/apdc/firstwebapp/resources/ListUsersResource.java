package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.*;
import com.google.gson.Gson;
import pt.unl.fct.di.apdc.firstwebapp.util.ListData;
import pt.unl.fct.di.apdc.firstwebapp.util.RegisterData;

@Path("/list")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ListUsersResource {
    private static final Logger LOG = Logger.getLogger(ListUsersResource.class.getName());

    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    public Gson gson = new Gson();

    public ListUsersResource(){}

    private List<String> listSU() {
        List<String> list = new ArrayList<String>();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("User")
                .build();
        QueryResults<Entity> users = datastore.run(query);

        users.forEachRemaining(user -> {
            list.add("[username: " + user.getString("username") + "   email: " + user.getString("email") + "  name: "
                    + user.getString("name") + "   role: " + user.getString("role") + "   state: "
                    + user.getString("state") + "   profile: " + user.getString("profile") + "   landPhone: "
                    + user.getString("landPhone") + "   cellPhone " + user.getString("cellPhone") + "   occupation: "
                    + user.getString("occupation") + "   workplace: " + user.getString("workplace") + "   nif: "
                    + user.getString("NIF") + "   address: " + user.getString("address") + "   compAddress: "
                    + user.getString("compAddress") + "  imageUrl: " + user.getString("imageUrl") + "]");
        });
        return list;
    }

    private List<String> listGS() {
        List<String> list = new ArrayList<String>();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("User")
                .setFilter(StructuredQuery.PropertyFilter.eq("role", "USER"))
                .build();
        QueryResults<Entity> users = datastore.run(query);

        users.forEachRemaining(user -> {
            list.add("[username: " + user.getString("username") + "   email: " + user.getString("email") + "  name: "
                    + user.getString("name") + "   role: " + user.getString("role") + "   state: "
                    + user.getString("state") + "   profile: " + user.getString("profile") + "   landPhone: "
                    + user.getString("landPhone") + "   cellPhone " + user.getString("cellPhone") + "   occupation: "
                    + user.getString("occupation") + "   workplace: " + user.getString("workplace") + "   nif: "
                    + user.getString("NIF") + "   address: " + user.getString("address") + "   compAddress: "
                    + user.getString("compAddress") + "  imageUrl: " + user.getString("imageUrl") + "]");

        });
        Query<Entity> query2 = Query.newEntityQueryBuilder().setKind("User")
                .setFilter(StructuredQuery.PropertyFilter.eq("role", "GBO"))
                .build();
        users = datastore.run(query2);
        users.forEachRemaining(user -> {
            list.add("[username: " + user.getString("username") + "   email: " + user.getString("email") + "  name: "
                    + user.getString("name") + "   role: " + user.getString("role") + "   state: "
                    + user.getString("state") + "   profile: " + user.getString("profile") + "   landPhone: "
                    + user.getString("landPhone") + "   cellPhone " + user.getString("cellPhone") + "   occupation: "
                    + user.getString("occupation") + "   workplace: " + user.getString("workplace") + "   nif: "
                    + user.getString("NIF") + "   address: " + user.getString("address") + "   compAddress: "
                    + user.getString("compAddress") + "  imageUrl: " + user.getString("imageUrl") + "]");

        });


        return list;
    }

    private List<String> listGBO() {
        List<String> list = new ArrayList<String>();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("User")
                .setFilter(StructuredQuery.PropertyFilter.eq("role", "USER"))
                .build();
        QueryResults<Entity> users = datastore.run(query);

        users.forEachRemaining(user -> {
            list.add("[username: " + user.getString("username") + "   email: " + user.getString("email") + "  name: "
                    + user.getString("name") + "   role: " + user.getString("role") + "   state: "
                    + user.getString("state") + "   profile: " + user.getString("profile") + "   landPhone: "
                    + user.getString("landPhone") + "   cellPhone " + user.getString("cellPhone") + "   occupation: "
                    + user.getString("occupation") + "   workplace: " + user.getString("workplace") + "   nif: "
                    + user.getString("NIF") + "   address: " + user.getString("address") + "   compAddress: "
                    + user.getString("compAddress") + "  imageUrl: " + user.getString("imageUrl") + "]");
        });
        return list;
    }

    private List<String> listUSER() {
        List<String> list = new ArrayList<String>();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("User")
                .setFilter(StructuredQuery.CompositeFilter.and(
                        StructuredQuery.PropertyFilter.eq("role", "USER"),
                        StructuredQuery.PropertyFilter.eq("profile", "Public"),
                        StructuredQuery.PropertyFilter.eq("state", "INACTIVE")))
                .build();
        QueryResults<Entity> users = datastore.run(query);

        users.forEachRemaining(user -> {
            list.add("[username: " + user.getString("username") + "   email: " + user.getString("email") + "  name: "
                    + user.getString("name") + "]");
        });
        return list;
    }

    @POST
    @Path("/op3")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response doListUsers(ListData data) {
        Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
        
        Entity user = datastore.get(userKey);

        if(user == null) return Response.status(Status.BAD_REQUEST).entity("User doesn't exist.").build();

        Transaction txn = datastore.newTransaction();
        try{
            Key userTokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.username);
            Entity userToken = txn.get(userTokenKey);
            if(!(userToken.getLong("validTo") - System.currentTimeMillis() > 0 ) || !(data.tokenID.equals(userToken.getString("tokenID")))) {
                txn.delete(userTokenKey);
                return Response.status(Status.FORBIDDEN).entity("Login to list users.").build();
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }

        String role = user.getString("role");
        if(role.equals("SU")){
            return Response.ok(gson.toJson(listSU())).build();
        }

        if(role.equals("GS")){
            return Response.ok(gson.toJson(listGS())).build();
        }

        if(role.equals("GBO")){
            return Response.ok(gson.toJson(listGBO())).build();
        }

        if(role.equals("USER")){
            return Response.ok(gson.toJson(listUSER())).build();
        }

        return Response.status(Status.BAD_REQUEST).entity("Unrecognized role").build();
    }
}

