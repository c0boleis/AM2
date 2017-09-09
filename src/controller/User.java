package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import controller.listener.AccountListener;
import controller.listener.TransactionListener;
import controller.listener.UserListener;
import model.Account;
import model.AmountException;
import model.Transaction;
import model.TransactionDetail;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.0
 */
public class User {

	public static final String VERSION = "1.2";
	
	public static Logger LOGGER = Logger.getLogger(User.class);

	public static User INSTANCE = new User();

	private List<Account> accounts = new ArrayList<Account>();

	private List<String> categories = new ArrayList<String>();

	private List<UserListener> listeners = new ArrayList<UserListener>();

	private AccountListener accountListener;

	private TransactionListener transactionListener;

	private Properties properties;

	private File accountsFolder;

	private boolean needSave = false;

	private static final String PROPETIES_FILE_NAME = "info.properties";
	
	private volatile static boolean logInit;

	private User() {
		logInit = false;
		initLog();
		LOGGER.info("Init user");
		properties = new Properties();
		InputStream in = User.class.getResourceAsStream(PROPETIES_FILE_NAME);
		try {
			properties.load(in);
		} catch (IOException e) {
			LOGGER.error("User properties wasn't load", e);
		}
		String folder = properties.getProperty("folder", "");
		accountsFolder = new File(folder);
		load();
	}

	/**
	 * 
	 * @return {@link User} instance
	 */
	public static User get() {
		return INSTANCE;
	}

	private void load() {
		File[] files = accountsFolder.listFiles();
		if(files==null) {
			return;
		}
		for(File file : files) {
			if(!file.isFile()) {
				continue;
			}
			try {
				createAccount(file);
			} catch (IOException e) {
				LOGGER.error("account not add",e);
			}
		}
		boolean oldNeedSave = needSave;
		needSave = false;
		if(Boolean.compare(oldNeedSave, needSave)!=0) {
			fireNeedSaveChange();
		}
	}

	private Account createAccount(File file) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			LOGGER.error("account file can't be read",e);
			return null;
		}
		String line = null;
		Account account = null;
		try {
			line = reader.readLine();
			account = new Account(line);
			line = reader.readLine();
		} catch (IOException e) {
			LOGGER.error("account file can't be read",e);
			reader.close();
			return null;
		}
		//the first line is the account number
		addAccount(account);
		while(line!=null) {
			try {
				Transaction transaction = Transaction.parse(line);
				if(transaction==null) {
					LOGGER.warn("transaction can't parse:"+line);
				}else {
					account.addTransaction(transaction);
					analyseCategory(transaction);
				}
			} catch (AmountException e) {
				LOGGER.warn("transaction can't parse:"+line,e);
			}
			line = reader.readLine();
		}
		reader.close();
		return account;
	}
	
	private void analyseCategory(Transaction transaction) {
		addCategory(transaction.getCategory());
		TransactionDetail[] transactionDetails = transaction.getTransactionDetails();
		for(TransactionDetail transactionDetail : transactionDetails) {
			addCategory(transactionDetail.getCategory());
		}
	}

	/**
	 * @return the accounts
	 */
	public Account[] getAccounts() {
		return accounts.toArray(new Account[0]);
	}

	/**
	 * 
	 * @param account to add
	 * @return true if the account was add.
	 */
	public boolean addAccount(Account account) {
		if(account==null) {
			LOGGER.debug("User.addAccount : account not because is null");
			return false;
		}
		if(accounts.contains(account)) {
			LOGGER.debug("account wasn't add because it already in the user");
			return false;
		}
		this.accounts.add(account);
		account.addListener(getAccountListener());
		boolean oldNeedSave = needSave;
		needSave = true;
		if(Boolean.compare(oldNeedSave, needSave)!=0) {
			fireNeedSaveChange();
		}
		fireAccountAdd(account);
		LOGGER.info("account add");
		return true;
	}

	/**
	 * 
	 * @param account to remove
	 * @return true if the account was remove
	 */
	public boolean removeAccount(Account account) {
		if(accounts.remove(account)) {
			fireAccountRemove(account);
			return true;
		}
		return false;
	}

	public void save() {
		//we clean the folder
		File[] files = accountsFolder.listFiles();
		if(files!=null) {
			for(File file : files) {
				file.delete();
			}
		}
		//we save the accounts
		Account[] accountsTmp = getAccounts();
		int index = 1;
		for(Account account : accountsTmp) {
			File file = new File(accountsFolder.getPath()+File.separator+"account_"+index+".acnt");
			try {
				account.save(file);
			} catch (IOException e) {
				LOGGER.warn("account : "+account.getAccountNumber(),e);
			}
			index++;
		}
		boolean oldNeedSave = needSave;
		needSave = false;
		if(Boolean.compare(oldNeedSave, needSave)!=0) {
			fireNeedSaveChange();
		}
	}

	/**
	 * @return the categories
	 */
	public String[] getCategories() {
		return categories.toArray(new String[0]);
	}

	/**
	 * 
	 * @param cat of transaction to add
	 */
	public void addCategory(String cat) {
		if(cat==null) {
			return;
		}
		cat = cat.trim();
		if(categories.contains(cat)) {
			return;
		}
		this.categories.add(cat);
		fireCategoryAdd(cat);
	}

	/**
	 * @return the listeners
	 */
	public UserListener[] getListeners() {
		return listeners.toArray(new UserListener[0]);
	}

	public void addListener(UserListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(UserListener listener) {
		this.listeners.remove(listener);
	}

	private void fireAccountAdd(Account account) {
		UserListener[] listenersTmp = getListeners();
		for(UserListener listener : listenersTmp) {
			listener.accountAdd(account);
		}
	}

	private void fireAccountRemove(Account account) {
		UserListener[] listenersTmp = getListeners();
		for(UserListener listener : listenersTmp) {
			listener.accountRemove(account);
		}
	}

	private void fireCategoryAdd(String category) {
		UserListener[] listenersTmp = getListeners();
		for(UserListener listener : listenersTmp) {
			listener.categoryAdd(category);
		}
	}

	private void fireNeedSaveChange() {
		UserListener[] listenersTmp = getListeners();
		for(UserListener listener : listenersTmp) {
			listener.needSaveChange();
		}
	}

	public List<Transaction> getAllTransactions(){
		List<Transaction> transactionsOut = new ArrayList<Transaction>();
		Account[] accountsTmp = getAccounts();
		for(Account account : accountsTmp) {
			transactionsOut.addAll(Arrays.asList(account.getTransactions()));
		}
		return transactionsOut;
	}

	public List<Transaction> getAllTransactionsByCategorie(String category){
		List<Transaction> transactionsOut = new ArrayList<Transaction>();
		Account[] accountsTmp = getAccounts();
		for(Account account : accountsTmp) {
			Transaction[] transactionsTmp = account.getTransactions();
			for(Transaction transaction : transactionsTmp) {
				if(transaction.getCategory().equals(category)) {
					transactionsOut.add(transaction);
				}
			}
		}
		return transactionsOut;
	}

	private void refreshCategory() {
		List<Transaction> tmp = getAllTransactions();
		for(Transaction transaction : tmp) {
			this.addCategory(transaction.getCategory());
		}
	}

	public List<Category> getCategoryWithTransaction(){
		refreshCategory();
		List<Category> categoriesOut = new ArrayList<Category>();
		String[] categoriesTmp = getCategories();
		for(String st : categoriesTmp) {
			categoriesOut.add(new Category(st));
		}
		return categoriesOut;
	}

	public List<Category> getCategoryWithTransaction(Date start,Date end){
		refreshCategory();
		List<Category> categoriesOut = new ArrayList<Category>();
		String[] categoriesTmp = getCategories();
		for(String st : categoriesTmp) {
			Category cat = new Category(st, start, end);
			if(!cat.isEmpty()) {
				categoriesOut.add(cat );
			}
		}
		return categoriesOut;
	}

	/**
	 * @return the accountListener
	 */
	public AccountListener getAccountListener() {
		if(accountListener == null) {
			accountListener = new AccountListener() {

				@Override
				public void transactionRemove(Transaction transaction) {
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("transactionRemove set need save");
						fireNeedSaveChange();
					}
				}

				@Override
				public void transactionAdd(Transaction transaction) {
					transaction.addListener(getTransactionListener());
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("transactionAdd set need save");
						fireNeedSaveChange();
					}
				}
			};
		}
		return accountListener;
	}

	/**
	 * @return the transactionListener
	 */
	public TransactionListener getTransactionListener() {
		if(transactionListener == null) {
			transactionListener = new TransactionListener() {

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#dateChange(java.util.Date, java.util.Date)
				 */
				@Override
				public void dateChange(Date oldDate, Date newDate) {
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("dateChange set need save");
						fireNeedSaveChange();
					}
				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#commentChange(java.lang.String, java.lang.String)
				 */
				@Override
				public void commentChange(String oldComment, String newComment) {
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("commentChange set need save");
						fireNeedSaveChange();
					}
				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#categoryChange(java.lang.String, java.lang.String)
				 */
				@Override
				public void categoryChange(String oldCategory, String newCategory) {
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("categoryChange set need save");
						fireNeedSaveChange();
					}
				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#amountChange(double, double)
				 */
				@Override
				public void amountChange(double oldAmount, double newAmount) {
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("amountChange set need save");
						fireNeedSaveChange();
					}
				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#transactionDetailAdd(model.TransactionDetail)
				 */
				@Override
				public void transactionDetailAdd(TransactionDetail transactionDetail) {
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("transactionDetailAdd set need save");
						fireNeedSaveChange();
					}

				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#transactionDetailRemove(model.TransactionDetail)
				 */
				@Override
				public void transactionDetailRemove(TransactionDetail transactionDetail) {
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("transactionDetailRemove set need save");
						fireNeedSaveChange();
					}

				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#doneChange(boolean, boolean)
				 */
				@Override
				public void doneChange(boolean oldDone, boolean newDone) {
					boolean oldNeedSave = needSave;
					needSave = true;
					if(Boolean.compare(oldNeedSave, needSave)!=0) {
						LOGGER.debug("doneChange set need save");
						fireNeedSaveChange();
					}
				}
			};
		}
		return transactionListener;
	}

	/**
	 * @return the needSave
	 */
	public boolean needSave() {
		return needSave;
	}
	
	public static void initLog(){
		if(logInit) {
			return;
		}
		logInit = true;
		try {
			Properties logProperties = new Properties();
			InputStream read = User.class.getClassLoader().getResourceAsStream("controller/log4j.properties");
			logProperties.load(read);
			PropertyConfigurator.configure(logProperties);
			((RollingFileAppender )Logger.getRootLogger().getAppender("R")).rollOver();
		} catch (Exception e) {
			System.err.println("Could not initialize logger");
		}
		LOGGER.info("Application start");
	}

}
