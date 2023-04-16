package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.util.logging.Logger;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.*;
import pt.unl.fct.di.apdc.firstwebapp.util.RegisterData;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RegisterResource {

	private static final Logger LOG = Logger.getLogger(RegisterResource.class.getName());

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	public RegisterResource() {}

	@POST
	@Path("/op1")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegistration(RegisterData data) {
		LOG.fine("Attempt to register user: " + data.username);

		data.optionalAttributes();

		Transaction txn = datastore.newTransaction();
		try {
			Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity user = txn.get(userKey);
			if(!data.allFilled()){
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity("Fill all mandatory fields.").build();
			}
			if(!data.validEmail()) {
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity("Invalid email.").build();
			}
			if(!data.passwordMatches()){
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity("Passwords do not match.").build();
			}
			if (user != null) {
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity("User already exists.").build();
			} else {
				user = Entity.newBuilder(userKey)
						.set("role","USER")
						.set("state","INACTIVE")
						.set("username", data.username)
						.set("password", DigestUtils.sha512Hex(data.password1))
						.set("email", data.email)
						.set("name",data.name)
						.set("profile",data.profile)
						.set("landPhone",data.landPhone)
						.set("cellPhone",data.cellPhone)
						.set("occupation",data.occupation)
						.set("workplace",data.workplace)
						.set("address",data.address)
						.set("compAddress",data.compAddress)
						.set("NIF",data.NIF)
						.set("imageUrl",data.imageUrl)
						.build();
				txn.add(user);
				LOG.info("User registered" + data.username);
				txn.commit();
				return Response.ok("{registered}").build();
			}
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
}