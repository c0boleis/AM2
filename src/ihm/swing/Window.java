package ihm.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import controller.User;
import controller.listener.UserListener;
import ihm.swing.calcul.PanelCalcul;
import ihm.swing.info.TabInfo;
import ihm.swing.table.account.AccountTableSwing;
import model.Account;

/**
 * 
 * @author C.B.
 * @version 1.0
 *
 */
public class Window extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 891786380764741088L;
	
	private JTabbedPane mainTabbedPane;
	
	private List<Account> accounts;
	
	private UserListener userListener;
	
	private static Window INSTANCE;
	
	private JSplitPane mainSplitPane;
	
	private JSplitPane splitCalcule;
	
	private TabInfo tabInfo;
	
	private WindowAdapter windowListener;
	
	private PanelCalcul panelCalcule;
	
	public static final String WINDOW_TITLE = "Account Manager";
	
	public Window() {
		super("Account Manager");
		this.accounts = new ArrayList<Account>();
		this.setSize(600, 500);
		this.setMinimumSize(new Dimension(400, 300));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setJMenuBar(new MenuBarAccount());
		this.setLayout(new BorderLayout());
		this.add(getMainSplitPane(), BorderLayout.CENTER);
		User.get().addListener(getUserListener());
		this.addWindowListener(getWindowListener());
	}

	public static void main(String[] args) {
		User.get();
		User.initLog();
		/*
		 * IHM
		 */
		INSTANCE = new Window();
		Account[] tab = User.get().getAccounts();
		for(Account account : tab) {
			INSTANCE.addAccount(account);
		}
		INSTANCE.setVisible(true);
	}

	/**
	 * @return the mainTabbedPane
	 */
	public JTabbedPane getAccountsTabbedPane() {
		if(mainTabbedPane == null) {
			mainTabbedPane = new JTabbedPane();
		}
		return mainTabbedPane;
	}
	
	public void addAccount(Account account) {
		if(account == null) {
			return;
		}
		if(accounts.contains(account)) {
			return;
		}
		
		this.accounts.add(account);
		AccountTableSwing table = new AccountTableSwing(account);
		JScrollPane pane = new JScrollPane(table);
		getAccountsTabbedPane().add(account.getAccountNumber(), pane);
	}
	
	public void removeAccount(Account account) {
		if(account == null) {
			return;
		}
		if(!accounts.contains(account)) {
			return;
		}
		int indexRemove = -1;
		int nbrCmp = getAccountsTabbedPane().getTabCount();
		for(int index = 0;index<nbrCmp;index++) {
			Component cmpTest = getAccountsTabbedPane().getComponentAt(index);
			if(cmpTest instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane)cmpTest;
				cmpTest = scrollPane.getViewport().getView();
				if(cmpTest instanceof AccountTableSwing) {
					if(((AccountTableSwing) cmpTest).getAccount().equals(account)) {
						indexRemove = index;
						break;
					}
				}
			}
		}
		if(indexRemove>=0) {
			getAccountsTabbedPane().removeTabAt(indexRemove);
			getAccountsTabbedPane().repaint();
		}
	}
	
	public static Window get() {
		return INSTANCE;
	}

	/**
	 * @return the userListener
	 */
	private UserListener getUserListener() {
		if(userListener == null) {
			userListener = new UserListener() {
				
				@Override
				public void categoryAdd(String category) {
				}
				
				@Override
				public void accountRemove(Account account) {
					removeAccount(account);
				}
				
				@Override
				public void accountAdd(Account account) {
					addAccount(account);
				}

				@Override
				public void needSaveChange() {
					if(User.get().needSave()) {
						setTitle(WINDOW_TITLE+" *");
					}else {
						setTitle(WINDOW_TITLE);
					}
				}
			};
		}
		return userListener;
	}

	/**
	 * @return the mainSplitPane
	 */
	public JSplitPane getMainSplitPane() {
		if(mainSplitPane == null) {
			mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			mainSplitPane.setLeftComponent(getAccountsTabbedPane());
			mainSplitPane.setRightComponent(getTabInfo());
			mainSplitPane.setDividerLocation(300);
		}
		return mainSplitPane;
	}

	/**
	 * @return the tabInfo
	 */
	public TabInfo getTabInfo() {
		if(tabInfo == null) {
			tabInfo = new TabInfo();
		}
		return tabInfo;
	}
	
	public void close() {
		if(!User.get().needSave()) {
				System.exit(0);
		}
		int rep = JOptionPane.showConfirmDialog(INSTANCE, "Do you want save before close?","Close",JOptionPane.YES_NO_OPTION);;
		if(rep== JOptionPane.YES_OPTION) {
			User.get().save();
			if(!User.get().needSave()) {
				System.exit(0);
			}
			JOptionPane.showMessageDialog(INSTANCE, "The accounts wasn't save!");
			return;
		}
		int rep2  = JOptionPane.showConfirmDialog(INSTANCE, "Are you shure to close without saving the accounts?","Close without save",JOptionPane.YES_NO_OPTION);
		if(rep2==JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		return;
	}

	/**
	 * @return the windowListener
	 */
	public WindowAdapter getWindowListener() {
		if(windowListener == null) {
			windowListener = new WindowAdapter() {

				/* (non-Javadoc)
				 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
				 */
				@Override
				public void windowClosing(WindowEvent arg0) {
					close();
				}
				
			};
		}
		return windowListener;
	}
	
	public void setDisplayCalcul(boolean b) {
		if(b) {
			splitCalcule = null;
			this.getMainSplitPane().setLeftComponent(getSplitCalcule());
		}else {
			this.getMainSplitPane().setLeftComponent(getAccountsTabbedPane());
		}
	}

	/**
	 * @return the splitCalcule
	 */
	public JSplitPane getSplitCalcule() {
		if(splitCalcule == null) {
			splitCalcule = new JSplitPane();
			splitCalcule.setLeftComponent(getAccountsTabbedPane());
			splitCalcule.setRightComponent(getPanelCalcule());
		}
		return splitCalcule;
	}

	/**
	 * @return the panelCalcule
	 */
	public PanelCalcul getPanelCalcule() {
		if(panelCalcule == null) {
			panelCalcule = new PanelCalcul();
		}
		return panelCalcule;
	}
}
