package org.mudit.rath.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.mudit.rath.dao.TransactionDAO;
import org.mudit.rath.model.Transaction;

@Path("paymentgateway")
public class TransactionResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public int processpayment(Transaction myTransaction){
		TransactionDAO mTransactionDAO = new TransactionDAO();
		return mTransactionDAO.executeTransaction(myTransaction);
	}
}
