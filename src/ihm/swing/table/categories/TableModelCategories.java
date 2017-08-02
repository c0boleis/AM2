package ihm.swing.table.categories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import controller.Category;
import controller.User;

/**
 * 
 * @author C.B.
 * @version 1.1
 * @since 1.1
 *
 */
public class TableModelCategories extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4240532127348779960L;
	
	private List<Category> categories = new ArrayList<Category>();
	
	private static final String[] HEADERS = {"Category","Amount"};
	
	private static final Class<?>[] COLUMN_CLASS = {String.class,Double.class};
	
	public TableModelCategories() {
		categories = User.get().getCategoryWithTransaction();
	}
	
	public TableModelCategories(Date dateStart,Date dateEnd) {
		categories = User.get().getCategoryWithTransaction(dateStart,dateEnd);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return HEADERS.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return HEADERS[columnIndex];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if(categories==null) {
			return 0;
		}
		return categories.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(categories==null) {
			return null;
		}
		Category category = categories.get(rowIndex);
		if(columnIndex==0) {
			return category.getName();
		}
		return category.getAmount();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int arg0, int columnIndex) {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_CLASS[columnIndex];
	}
	
	

}
