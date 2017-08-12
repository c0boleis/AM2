package ihm.swing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import controller.User;
import model.AmountException;
import model.Transaction;

public class MainWriteSeed {
	
	private static final Logger LOGGER = Logger.getLogger(MainWriteSeed.class);

	public static void main(String[] args) {
		User.initLog();
		File fichier1 = new File("account"+File.separator+"account_1.txt");
		File fichier2 = new File("account"+File.separator+"account_2.txt");
		File fichier3 = new File("account"+File.separator+"account_3.txt");
		File fichierSeed  = new File("seed.rb");
		try {
			BufferedReader buf1 = new BufferedReader(new FileReader(fichier1));
			BufferedReader buf2 = new BufferedReader(new FileReader(fichier2));
			BufferedReader buf3 = new BufferedReader(new FileReader(fichier3));
			
			BufferedWriter bufSeed = new BufferedWriter(new FileWriter(fichierSeed));
			/*
			 * line accounts numbers
			 */
			String line1 = buf1.readLine();
			String line2 = buf2.readLine();
			String line3 = buf3.readLine();
			bufSeed.write("Account.create!([");
			bufSeed.write("{user_id: 1, account_number: \'"+line1.trim()+"\'},\n");
			bufSeed.write("{user_id: 1, account_number: \'"+line2.trim()+"\'},\n");
			bufSeed.write("{user_id: 1, account_number: \'"+line3.trim()+"\'}])\n\n");
			/*
			 * write init transaction
			 */
			bufSeed.write("Transaction.create!([");
			/*
			 * fetch transaction account 1
			 */
			line1 = buf1.readLine();
			List<String> lineOut = new ArrayList<String>();
			while(line1!=null) {
				try {
					Transaction t = Transaction.parse(line1);
					lineOut.add("{account_id: 1, "
							+ 	"date: \'"+Transaction.dateFormat.format(t.getDate())+"\', "
							+ 	"category: \'"+t.getCategory()+"\', "
							+	"comment: \'"+t.getComment()+"\', "
							+	"amount: "+String.valueOf(t.getAmount()).replace(",",".")+", "
							+	"done: "+String.valueOf(t.isDone())+"}");
				} catch (AmountException e) {
					LOGGER.warn("transaction not parse for account 1", e);
				}
				
				line1 = buf1.readLine();
			}
			
			line1 = buf1.readLine();
			while(line1!=null) {
				try {
					Transaction t = Transaction.parse(line1);
					lineOut.add("{account_id: 2, "
							+ 	"date: \'"+Transaction.dateFormat.format(t.getDate())+"\', "
							+ 	"category: \'"+t.getCategory()+"\', "
							+	"comment: \'"+t.getComment()+"\', "
							+	"amount: "+String.valueOf(t.getAmount()).replace(",",".")+", "
							+	"done: "+String.valueOf(t.isDone())+"}");
				} catch (AmountException e) {
					LOGGER.warn("transaction not parse for account 1", e);
				}
				
				line1 = buf1.readLine();
			}
			line2 = buf2.readLine();
			while(line2!=null) {
				try {
					Transaction t = Transaction.parse(line2);
					lineOut.add("{account_id: 2, "
									+ 	"date: \'"+Transaction.dateFormat.format(t.getDate())+"\', "
									+ 	"category: \'"+t.getCategory()+"\', "
									+	"comment: \'"+t.getComment()+"\', "
									+	"amount: "+String.valueOf(t.getAmount()).replace(",",".")+", "
									+	"done: "+String.valueOf(t.isDone())+"}");
				} catch (AmountException e) {
					LOGGER.warn("transaction not parse for account 2", e);
				}
				
				line2 = buf2.readLine();
			}
			line3 = buf3.readLine();
			while(line3!=null) {
				try {
					Transaction t = Transaction.parse(line3);
					lineOut.add("{account_id: 3, "
							+ 	"date: \'"+Transaction.dateFormat.format(t.getDate())+"\', "
							+ 	"category: \'"+t.getCategory()+"\', "
							+	"comment: \'"+t.getComment()+"\', "
							+	"amount: "+String.valueOf(t.getAmount()).replace(",",".")+", "
							+	"done: "+String.valueOf(t.isDone())+"}");
				} catch (AmountException e) {
					LOGGER.warn("transaction not parse for account 3", e);
				}
				line3 = buf3.readLine();
			}
			for(int index = lineOut.size()-1;index>=0;index--) {
				String st = lineOut.get(index);
				if(index!=0) {
					st+=",\n";
				}
				bufSeed.write(st);
			}
			bufSeed.write("])");
			
			bufSeed.close();
			buf1.close();
			buf2.close();
			buf3.close();
		} catch (IOException e) {
			LOGGER.error("error write seed", e);
		}
		
		
//		PanelCalcul pane = new PanelCalcul();
//		//		String test = "56-67+67*-7/89+56-67";
//		//		double d = pane.processParenthese(test);
//		//		double r = 56-67+67*-7/89+56-67;
//		//		System.out.println("######## "+r+" ######result = "+d);
//		//		
//		//		test = "(56-67+67)*-7/89+56-67";
//		//		d = pane.processParenthese(test);
//		//		r = (56-67+67)*-7/89+56-67;
//		//		System.out.println("######## "+r+" ######result = "+d);
//		//		
//		//		test = "(56-67+67)*-7/(89+56-67)";
//		//		d = pane.processParenthese(test);
//		//		r = (56-67+67)*-7/(89+56-67);
//		//		System.out.println("######## "+r+" ######result = "+d);
//
//		String line = "(45+56*78)*(45/78-45*-5*(45*(45-67)))";
////		String line = "45+56*78";
//		boolean stop = false;
//		double result = 0.0;
//		while(!stop) {
//			String info = findParenthese(line);
//			result= pane.processParenthese(info);
//			System.out.println("info : "+info+"\t result : "+result);
//			line  = line.replace("("+info+")", String.valueOf(result));
//			if(!line.contains("(") && !line.contains("(")) {
//				result= pane.processParenthese(line);
//				stop = true;
//			}
//			System.out.println(line);
//		}
//		
//		System.out.println("result : "+result);
//		//		for(String st : info) {
//		//			if(st.trim().length()==0) {
//		//				continue;
//		//			}
//		//			String tmp = removeSign(st.trim());
//		//			if(tmp.trim().length()==0) {
//		//				continue;
//		//			}
//		//			double d = pane.processParenthese(tmp);
//		//			line = line.replace(tmp, String.valueOf(d));
//		//		}
//		//		System.out.println(line);
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
		List<String> info = new ArrayList<String>();
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
//		System.out.println("max : "+maxFact);
//		System.out.println(map.toString());
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
