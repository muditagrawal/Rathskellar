package org.mudit.rath.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.model.CardDetails;
import org.mudit.rath.model.DummyBankAccount;
import org.mudit.rath.model.Transaction;

public class TransactionDAO {
	public int executeTransaction(Transaction myTransaction){
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		CardDetails mCardDetails = (CardDetails)mSession.createCriteria(CardDetails.class).add(Restrictions.eqOrIsNull("cardNo", myTransaction.getCardDetails().getCardNo())).uniqueResult();
		if(mCardDetails != null){
			System.out.println("Verifying Card");
			if(mCardDetails.getHolderName().compareToIgnoreCase(myTransaction.getCardDetails().getHolderName())==0){
				if((mCardDetails.getcVV()==myTransaction.getCardDetails().getcVV())&&(mCardDetails.getExpiryDate().getDate()==myTransaction.getCardDetails().getExpiryDate().getDate())&&(mCardDetails.getExpiryDate().getMonth()==myTransaction.getCardDetails().getExpiryDate().getMonth())&&(mCardDetails.getExpiryDate().getYear()==myTransaction.getCardDetails().getExpiryDate().getYear())){
					System.out.println("Card Verified");
					try{
					mSession.update(mCardDetails);
					mSession.getTransaction().commit();
					mSession.beginTransaction();
					DummyBankAccount mAccount = (DummyBankAccount)mSession.createCriteria(DummyBankAccount.class).add(Restrictions.eq("cardDetails",mCardDetails)).list().get(0);
					if(mAccount.getBalance()>=myTransaction.getAmount()){
						mAccount.setBalance(mAccount.getBalance()-myTransaction.getAmount());
						mSession.update(mAccount);
						myTransaction.setCardDetails(mCardDetails);
						mSession.save(myTransaction);
						mSession.getTransaction().commit();
						mSession.close();
						return 200;
					}else{
						
					}
					}catch(Exception e){
						
					}
					
				}else{
					
				}
			}
			
		}else{
			return 0;
		}
		return 0;
	}
}
