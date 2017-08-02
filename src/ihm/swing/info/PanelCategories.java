package ihm.swing.info;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import ihm.swing.table.categories.TableCategories;
import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.1
 * @since 1.1
 *
 */
public class PanelCategories extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9123256285268433976L;
	
	private JPanel panelOptions;
	
	private JTextField textFieldDateStartSearch;
	
	private JTextField textFieldDateEndSearch;
	
	private TableCategories tableCategories;
	
	public PanelCategories() {
		super();
		this.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(getTableCategories());
		this.add(getPanelOptions(),BorderLayout.WEST);
		this.add(scrollPane,BorderLayout.CENTER);
	}

	/**
	 * @return the panelOptions
	 */
	public JPanel getPanelOptions() {
		if(panelOptions==null) {
			panelOptions = new JPanel();
			panelOptions.setLayout(new FormLayout(new ColumnSpec[] {
					FormSpecs.RELATED_GAP_COLSPEC,
					FormSpecs.DEFAULT_COLSPEC,
					FormSpecs.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,}));
			
			JLabel lblDateStart = new JLabel("date Start :");
			panelOptions.add(lblDateStart, "2, 2, right, default");
			
			panelOptions.add(getTextFieldDateStartSearch(), "4, 2, fill, default");
			getTextFieldDateStartSearch().setColumns(10);
			
			JLabel lblDateEnd = new JLabel("date End :");
			panelOptions.add(lblDateEnd, "2, 4, right, default");
			
			panelOptions.add(getTextFieldDateEndSearch(), "4, 4, fill, default");
			getTextFieldDateEndSearch().setColumns(10);
			
			JButton btnRefresh = new JButton("Refresh");
			panelOptions.add(btnRefresh, "2, 6, 3, 1");
			btnRefresh.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					refresh();
					
				}
			});
		}
		return panelOptions;
	}
	
	private void refresh() {
		Date dateStart = null;
		Date dateEnd = null;
		try {
			dateStart = Transaction.dateFormat.parse(
					getTextFieldDateStartSearch().getText().trim());
			dateEnd = Transaction.dateFormat.parse(
					getTextFieldDateEndSearch().getText().trim());
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(dateStart!=null && dateEnd!=null) {
			this.tableCategories.refresh(dateStart, dateEnd);
		}else {
			this.tableCategories.refresh();
		}
	}

	/**
	 * @return the textFieldDateEndSearch
	 */
	public JTextField getTextFieldDateEndSearch() {
		if(textFieldDateEndSearch == null) {
			textFieldDateEndSearch = new JTextField();
		}
		return textFieldDateEndSearch;
	}

	/**
	 * @return the textFieldDateStartSearch
	 */
	public JTextField getTextFieldDateStartSearch() {
		if(textFieldDateStartSearch == null) {
			textFieldDateStartSearch = new JTextField();
		}
		return textFieldDateStartSearch;
	}
	

	/**
	 * @return the tableCategorie
	 */
	public TableCategories getTableCategories() {
		if(tableCategories == null) {
			tableCategories =new TableCategories();
		}
		return tableCategories;
	}
}
