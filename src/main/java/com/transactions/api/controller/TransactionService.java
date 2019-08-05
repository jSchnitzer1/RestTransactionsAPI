package com.transactions.api.controller;

import com.transactions.api.database.DatabaseManager;
import com.transactions.api.database.TransactionStatus;
import com.transactions.api.model.ErrorMessage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.*;

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
    @Path("/createTransaction/{accountId}/{transactionAmount}/{transactionUUID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(@PathParam("accountId") int accountId, @PathParam("transactionAmount") int transactionAmount, @PathParam("transactionUUID") String transactionUUID) {
        LOGGER.info("createTransaction is triggered");
        String errorMessageStr = "Internal database error in creating a new transaction for accountId" + accountId;
        return createOrDeleteTransaction(accountId, DBMANAGER.createTransaction(accountId, transactionAmount, transactionUUID), errorMessageStr);
    }

    @DELETE
    @Path("/deleteTransaction/{accountId}/{transactionUUID}")
    public Response deleteTransaction(@PathParam("accountId") int accountId, @PathParam("transactionUUID") String transactionUUID) {
        LOGGER.info("deleteTransaction is triggered");
        String errorMessageStr = "Internal database error in deleting the transaction of transactionUUID" + transactionUUID;
        return createOrDeleteTransaction(accountId, DBMANAGER.deleteTransaction(accountId, transactionUUID), errorMessageStr);
    }

    private Response createOrDeleteTransaction(int accountId, TransactionStatus transaction, String errorMessageStr) {
        JSONObject json = null;
        TransactionStatus ts = transaction;
        if (ts.getResult() == TransactionStatus.TransactionResult.SUCCESS) {
            json = new JSONObject();
            json.put("result", "success");
            json.put("message", ts.getMessage());
            return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
        }
        ErrorMessage errorMessage = new ErrorMessage(errorMessageStr, 500, ts.getMessage());
        return Response.serverError().entity(errorMessage).build();
    }
}
