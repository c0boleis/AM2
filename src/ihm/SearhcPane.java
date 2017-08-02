package ihm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SearhcPane {

	private static JTextField textField;
	
	private static JLabel labelErreur;
	
	private static JTextField textFieldRegularExpression;
	
	private static JCheckBox checkRegularExpression;
	
	private static JCheckBox checkCaseSensitive;
	
	private static JCheckBox checkWholeWord;
	
	private static boolean error = false;
	
	/**
	 * @return the checkCaseSensitive
	 */
	private static JCheckBox getCheckCaseSensitive() {
		if(checkCaseSensitive == null){
			checkCaseSensitive = new JCheckBox("Maj. et Min. ?");
			checkCaseSensitive.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					SearhcPane.getTextFieldRegularExpression().setText(getRegex());
					checkError();
				}
			});
		}
		return checkCaseSensitive;
	}

	/**
	 * @return the checkRegularExpression
	 */
	private static JCheckBox getCheckRegularExpression() {
		if(checkRegularExpression == null){
			checkRegularExpression = new JCheckBox("Expresion regulier");
			checkRegularExpression.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					if(checkRegularExpression.isSelected()){
						checkCaseSensitive.setSelected(false);
						checkCaseSensitive.setEnabled(false);
						
						checkWholeWord.setSelected(false);
						checkWholeWord.setEnabled(false);
					}else{
						checkCaseSensitive.setEnabled(true);
						checkWholeWord.setEnabled(true);
					}
					SearhcPane.getTextFieldRegularExpression().setText(getRegex());
					checkError();
				}
			});
		}
		return checkRegularExpression;
	}

	/**
	 * @return the checkWholeWord
	 */
	private static JCheckBox getCheckWholeWord() {
		if(checkWholeWord == null){
			checkWholeWord = new JCheckBox("Mot entier");
			checkWholeWord.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					SearhcPane.getTextFieldRegularExpression().setText(getRegex());
					checkError();
				}
			});
		}
		return checkWholeWord;
	}

	private static JPanel getPanelSearch(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panelCentre = new JPanel();
		GridLayout layout = new GridLayout(3, 2);
		panelCentre.setLayout(layout);
		
		
		panelCentre.add(getTextField());panelCentre.add(getCheckRegularExpression());
		panelCentre.add(new JLabel(""));panelCentre.add(getCheckCaseSensitive());
		panelCentre.add(new JLabel(""));panelCentre.add(getCheckWholeWord());
		
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new BorderLayout());
		panelSouth.add(getLabelErreur(), BorderLayout.WEST);
		panelSouth.add(getTextFieldRegularExpression(),BorderLayout.CENTER);
		
		panel.add(panelCentre, BorderLayout.CENTER);
		panel.add(panelSouth,BorderLayout.SOUTH);
		return panel;
	}

	public static String getSearchRegex(){
		int rep = JOptionPane.showConfirmDialog(null,
				  getPanelSearch(),
                  "Recherche",
                  JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.PLAIN_MESSAGE);
		if(rep!=JOptionPane.OK_OPTION){
			return null;
		}
		if(error){
			JOptionPane.showMessageDialog(null, 
					"le regex in incorecte",
					"ERREUR",
					JOptionPane.ERROR_MESSAGE);
			return getSearchRegex();
		}
		String text = getRegex();
		if(text.trim().length()==0){
			JOptionPane.showMessageDialog(null, 
					"Vous ne pouvez pas rechercher\n"
					+ "un mot vide.",
					"ATTENTION",
					JOptionPane.INFORMATION_MESSAGE);
			return getSearchRegex();
		}
		return getRegex();
	}
	
	private static String getRegex(){
		String regex = getTextField().getText();
		if(regex.trim().length()==0){
			return "";
		}
		if(getCheckRegularExpression().isSelected()){
			return regex;
		}
		/*
		 * we replace all specific char
		 */
		regex = regex.replace(".", "\\.");
		regex = regex.replace("[", "\\[");
		regex = regex.replace("]", "\\]");
		regex = regex.replace("*", "\\*");
		regex = regex.replace("|", "\\|");
//		if(getCheckCaseSensitive().isSelected() || getCheckWholeWord().isSelected()){
//			regex = StringUtil.sansAccent(regex);
//		}
		if(getCheckCaseSensitive().isSelected()){
			regex = toStringCaseNotSensitive(regex);
		}
		if(getCheckWholeWord().isSelected()){
			regex = "[\\W]"+regex+"[\\W]";
		}
		return regex;
	}
	
	private static String toStringCaseNotSensitive(String regex){
		regex = regex.toLowerCase();
		char[] tab = regex.toCharArray();
		String out ="";
		for(int index = 0;index<tab.length;index++){
			char c = tab[index];
			String charT = String.valueOf(c);
			if(Character.isAlphabetic(c)){
				 out+="["+charT.toLowerCase()+"|"+charT.toUpperCase()+"]";
			}else{
				out+=charT;
			}
		}
		return out;
	}
	
	/**
	 * @return the textField
	 */
	private static JTextField getTextField() {
		if(textField == null){
			textField = new JTextField();
			textField.addCaretListener(new CaretListener() {
				
				@Override
				public void caretUpdate(CaretEvent arg0) {
					SearhcPane.getTextFieldRegularExpression().setText(getRegex());
					checkError();
				}
			});
			
		}
		return textField;
	}

	/**
	 * @return the textFieldRegularExpression
	 */
	private static JTextField getTextFieldRegularExpression() {
		if(textFieldRegularExpression == null){
			textFieldRegularExpression = new JTextField();
			textFieldRegularExpression.setEditable(false);
		}
		return textFieldRegularExpression;
	}

	/**
	 * @return the labelErreur
	 */
	public static JLabel getLabelErreur() {
		if(labelErreur == null){
			labelErreur = new JLabel("");
		}
		return labelErreur;
	}
	
	private static void checkError(){
		String regex = getRegex();
		if(regex.trim().length()==0){
			getLabelErreur().setText("");
			return;
		}
		try{
			Pattern.compile(regex);
			error = false;
		}catch(Exception e){
			error = true;
		}
		if(error){
			getLabelErreur().setText("ERREUR");
			getLabelErreur().setForeground(Color.RED);
		}else{
			getLabelErreur().setText("OK");
			getLabelErreur().setForeground(Color.GREEN);
		}
		
	}

}