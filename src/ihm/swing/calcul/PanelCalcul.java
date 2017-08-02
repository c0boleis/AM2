package ihm.swing.calcul;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class PanelCalcul extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2370753641533962119L;
	
	private JTextField textFieldCalculZone;
	
	public PanelCalcul() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		textFieldCalculZone = new JTextField();
		add(textFieldCalculZone, "2, 2, 9, 1, fill, default");
		textFieldCalculZone.setColumns(10);
		
		JButton btn7 = new JButton("7");
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"7");
			}
		});
		add(btn7, "2, 4");
		
		JButton btn8 = new JButton("8");
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"8");
			}
		});
		add(btn8, "4, 4");
		
		JButton btn9 = new JButton("9");
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"9");
			}
		});
		add(btn9, "6, 4");
		
		JButton btnDivide = new JButton("/");
		btnDivide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"/");
			}
		});
		add(btnDivide, "8, 4");
		
		JButton btn4 = new JButton("4");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"4");
			}
		});
		add(btn4, "2, 6");
		
		JButton btn5 = new JButton("5");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"5");
			}
		});
		add(btn5, "4, 6");
		
		JButton btn6 = new JButton("6");
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"6");
			}
		});
		add(btn6, "6, 6");
		
		JButton btnStart = new JButton("*");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"*");
			}
		});
		add(btnStart, "8, 6");
		
		JButton btn1 = new JButton("1");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"1");
			}
		});
		add(btn1, "2, 8");
		
		JButton btn2 = new JButton("2");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"2");
			}
		});
		add(btn2, "4, 8");
		
		JButton btn3 = new JButton("3");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"3");
			}
		});
		add(btn3, "6, 8");
		
		JButton btnMoins = new JButton("-");
		btnMoins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"-");
			}
		});
		add(btnMoins, "8, 8");
		
		JButton btn0 = new JButton("0");
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"0");
			}
		});
		add(btn0, "2, 10, 3, 1");
		
		JButton btnPts = new JButton(",");
		btnPts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+",");
			}
		});
		add(btnPts, "6, 10");
		
		JButton btnPlus = new JButton("+");
		btnPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textFieldCalculZone.getText();
				textFieldCalculZone.setText(text+"+");
			}
		});
		add(btnPlus, "8, 10");
		
		JButton btnEqual = new JButton("=");
		btnEqual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				double result = processParenthese(line)
			}
		});
		add(btnEqual, "10, 4, 1, 7");
	}
	
	private double processMulti(String line) {
		String txt = line.trim();
		if(txt.length()==0) {
			return 0.0;
		}
		String[] parse1 = PanelCalcul.split(txt, '*','/');
		System.out.println(Arrays.toString(parse1));
		int[] tab = new int[parse1.length-1];
		int indexStart = 0;
		for(int index = 0;index<tab.length;index++) {
			int k = txt.indexOf('*', indexStart);
			if(k<0) {
				k = txt.length();
			}
			int k1 = txt.indexOf('/', indexStart);
			if(k1<0) {
				k1 = txt.length();
			}
			k = Math.min(k, k1);
			indexStart = k+1;
			char c = txt.charAt(k);
			if(c == '*') {
				tab[index] = 1;
			}else if(c == '/') {
				tab[index] = -1;
			}
		}
		double out = Double.parseDouble(parse1[0]);
		for(int index = 1;index<parse1.length;index++) {
			if(tab[index-1]>0) {
				out *= Double.parseDouble(parse1[index]);
			}else if(tab[index-1]<0) {
				out /= Double.parseDouble(parse1[index]);
			}
		}
		return out;
	}
	
	private double processAdd(String line) {
		String txt = line.trim();
		/*
		 * check for first sign
		 */
		double fact = 0;
		if(!Character.isDigit(txt.charAt(0))) {
			
			if(txt.charAt(0) == '+') {
				fact = 1;
			}else if(txt.charAt(0) == '-') {
				fact = -1;
			}
			txt = txt.substring(1, txt.length());
		}else {
			fact = 1;
		}
		String[] parse1 = PanelCalcul.split(txt, '+','-');
		if(parse1.length==1) {
//			return fact*Double.parseDouble(txt);
			try {
				return fact*Double.parseDouble(txt);
			}catch(NumberFormatException e) {
				return fact*processMulti(txt);
			}
		}
		double[] tab = new double[parse1.length];
		/*
		 * process next
		 */
		int indexStart = 0;
		tab[0] = fact;
		String txtTest = txt.replace("*-", "*").replaceAll("/-", "/");
		for(int index = 1;index<tab.length;index++) {
			int k = txtTest.indexOf('+', indexStart);
			if(k<0) {
				k = txtTest.length();
			}
			int k1 = txtTest.indexOf('-', indexStart);
			if(k1<0) {
				k1 = txtTest.length();
			}
			k = Math.min(k, k1);
			indexStart = k+1;
			char c = txtTest.charAt(k);
			if(c == '+') {
				tab[index] = 1;
			}else if(c == '-') {
				tab[index] = -1;
			}
		}
		double out = 0.0;
		int index =0;
		for(String st : parse1) {
			double d = 0.0;
			try {
				d = Double.parseDouble(st);
			}catch(NumberFormatException e) {
				d = processMulti(st);
			}
			out+= tab[index]*d;
//			System.out.println("double : "+d);
			index++;
		}
		return out;
	}
	
	public double processParenthese(String line) {
		String txt = line.trim();
		if(!txt.contains("(") && !txt.contains(")")) {
			return processAdd(txt);
		}
		int kStart = txt.indexOf('(');
		int kEnd = txt.lastIndexOf(')');
		if(kEnd<kStart) {
			throw new NumberFormatException();
		}
		if(kEnd<0 || kStart<0) {
			throw new NumberFormatException();
		}
		String txtS = txt.substring(kStart+1, kEnd);
		String newTxtStart = txt.substring(0, kStart)+String.valueOf(processParenthese(txtS));
		String newTxtEnd = txt.substring(kEnd+1, txt.length());
		return processParenthese(newTxtStart+newTxtEnd);
	}
	
	public double processCalcul(String line) {
		boolean stop = false;
		double result = 0.0;
		while(!stop) {
			String info = findParenthese(line);
			result= this.processParenthese(info);
			System.out.println("info : "+info+"\t result : "+result);
			line  = line.replace("("+info+")", String.valueOf(result));
			if(!line.contains("(") && !line.contains("(")) {
				result= this.processParenthese(line);
				stop = true;
			}
			System.out.println(line);
		}
		return result;
	}
	
	public static String removeSign(String line) {
		if(line.length()==0) {
			return "";
		}
		char c = line.charAt(0);
		if(c == '*' || c == '/'){
			line = line.substring(1, line.length());
		}
		if(line.trim().length()==0) {
			return "";
		}
		c = line.charAt(line.length()-1);
		if(c == '*' || c == '/'){
			line = line.substring(0, line.length()-1);
		}
		return line;
	}

	public static String findParenthese(String line) {
		HashMap<Integer, String> map = new HashMap<Integer,String>();
		char[] charTab = line.toCharArray();
		String st = "";
		int oldFact = 0;
		int fact = 0;
		int maxFact = -1;
		for(int index = 0;index<charTab.length;index++) {
			if(charTab[index] == '(') {
				fact++;
			}else if(charTab[index] == ')') {
				fact--;
			}
			if(fact == oldFact) {
				st+=charTab[index];
				if(fact>maxFact) {
					maxFact = fact;
				}
			}else {
				if(st.length()!=0) {
					map.put(new Integer(oldFact), st);
					st = "";
				}
			}
			oldFact = fact;
		}
		return map.get(new Integer(maxFact));
	}

	public static String[] split(String txt,char... tabSep) {
		List<String> info = new ArrayList<String>();
		char[] charTab = txt.toCharArray();
		String st = "";
		boolean add = false;
		for(int index = 0;index<charTab.length;index++) {
			if(isMatch(charTab[index], tabSep)) {
				if(index>0){
					if(Character.isDigit(charTab[index-1])) {
						add = true;
					}
				}
			}
			if(add) {
				info.add(st);
				st = "";
				add = false;
			}else {
				st+= charTab[index];
			}
		}
		info.add(st);
		return info.toArray(new String[0]);
	}

	public static boolean isMatch(char c,char[] tabSep) {
		for(int index = 0;index<tabSep.length;index++) {
			if(c == tabSep[index]) {
				return true;
			}
		}
		return false;
	}
	

}
