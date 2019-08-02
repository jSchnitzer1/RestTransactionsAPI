package com.transactions.api.controller;

import com.transactions.api.database.DatabaseManager;
import com.transactions.api.model.dto.AccountsTransactionsDTO;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transaction")
public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());
    private static final DatabaseManager DBMANAGER = DatabaseManager.getInstance();
    @Context private ServletContext context;

    @PostConstruct
    public void postConst() {
        String log4jConfigPath = context.getRealPath("WEB-INF/log4j.properties");
        PropertyConfigurator.configure(log4jConfigPath);
        DBMANAGER.initDatabase();
    }

    @PreDestroy
    public void preDestroy() {
        DBMANAGER.destroyDatabase();
    }

    @POST
    @Path("/createTestDatabase")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createTestDatabase() {
        LOGGER.info("createDatabase is triggered");
        if(DBMANAGER.createTestDatabase())
            return Response.ok("Test database is created successfully").build();
        else
            return Response.serverError().entity("Internal Server Error - unable to create the test database").build();
    }

    @GET
    @Path("/testDatabase")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkTransactionEndpoint() {
        LOGGER.info("checkTransactionEndpoint is triggered");
        AccountsTransactionsDTO acDTO = DBMANAGER.testDatabase();
        if(acDTO != null)
            return Response.ok(acDTO, MediaType.APPLICATION_JSON).build();
        else
            return Response.serverError().entity("Internal Server Error - unable to fetch database records").build();
    }
}
