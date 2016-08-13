import java.util.Date;

import org.hibernate.Session;
import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.model.CardDetails;
import org.mudit.rath.model.DummyBankAccount;


public class Test {

	public static void main(String[] args) 
	{	
		//generateAccounts();
	}
	
	public static void generateAccounts(){
		CardDetails mCardDetails1 = new CardDetails();
		mCardDetails1.setCardNo("1000100010001000");
		mCardDetails1.setcVV(100);
		mCardDetails1.setHolderName("Card Holder 1");
		mCardDetails1.setExpiryDate(new Date(2019,12,31));
		DummyBankAccount mAccount1 = new DummyBankAccount();
		mAccount1.setCardDetails(mCardDetails1);
		mAccount1.setBalance(100);
		CardDetails mCardDetails2 = new CardDetails();
		mCardDetails2.setCardNo("2000200020002000");
		mCardDetails2.setcVV(200);
		mCardDetails2.setHolderName("Card Holder 2");
		mCardDetails2.setExpiryDate(new Date(2019,12,31));
		DummyBankAccount mAccount2 = new DummyBankAccount();
		mAccount2.setCardDetails(mCardDetails2);
		mAccount2.setBalance(100);
		CardDetails mCardDetails3 = new CardDetails();
		mCardDetails3.setCardNo("3000300030003000");
		mCardDetails3.setcVV(100);
		mCardDetails3.setHolderName("Card Holder 3");
		mCardDetails3.setExpiryDate(new Date(2019,12,31));
		DummyBankAccount mAccount3 = new DummyBankAccount();
		mAccount3.setCardDetails(mCardDetails3);
		mAccount3.setBalance(100);
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		mSession.save(mCardDetails1);
		mSession.save(mCardDetails2);
		mSession.save(mCardDetails3);	
		mSession.save(mAccount1);
		mSession.save(mAccount2);
		mSession.save(mAccount3);
		mSession.getTransaction().commit();
		mSession.close();
	}
}
