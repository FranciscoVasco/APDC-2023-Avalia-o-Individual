package pt.unl.fct.di.apdc.firstwebapp.listener;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.datastore.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import java.util.logging.Logger;

    @WebListener
    public class startSU extends HttpServlet implements ServletContextListener {

        private static final Logger LOG = Logger.getLogger(startSU.class.getName());

        private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            LOG.info("Creating SUPER USER...");

            Key userKey = datastore.newKeyFactory().setKind("User").newKey("SU");

            Transaction txn = datastore.newTransaction();

            Entity su = Entity.newBuilder(userKey)
                    .set("username", "SU")
                    .set("password", DigestUtils.sha512Hex("SU"))
                    .set("email", "SU@fct.unl.pt")
                    .set("name", "SU")
                    .set("profile", "private")
                    .set("landPhone", "")
                    .set("cellPhone", "")
                    .set("occupation", "")
                    .set("workplace", "")
                    .set("address", "")
                    .set("compAddress", "")
                    .set("NIF", "")
                    .set("imageUrl","")
                    .set("role", "SU")
                    .set("state", "ACTIVE")   //false-inactive, true-active
                    .build();

            txn.put(su);
            txn.commit();

            LOG.info("Super user created successfully.");
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
        }
    }
