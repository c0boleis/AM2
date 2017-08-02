package ihm.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.javafx.collections.MapAdapterChange;

import ihm.swing.calcul.PanelCalcul;

public class MainTestCalcule {

	public static void main(String[] args) {
		PanelCalcul pane = new PanelCalcul();
		//		String test = "56-67+67*-7/89+56-67";
		//		double d = pane.processParenthese(test);
		//		double r = 56-67+67*-7/89+56-67;
		//		System.out.println("######## "+r+" ######result = "+d);
		//		
		//		test = "(56-67+67)*-7/89+56-67";
		//		d = pane.processParenthese(test);
		//		r = (56-67+67)*-7/89+56-67;
		//		System.out.println("######## "+r+" ######result = "+d);
		//		
		//		test = "(56-67+67)*-7/(89+56-67)";
		//		d = pane.processParenthese(test);
		//		r = (56-67+67)*-7/(89+56-67);
		//		System.out.println("######## "+r+" ######result = "+d);

		String line = "(45+56*78)*(45/78-45*-5*(45*(45-67)))";
//		String line = "45+56*78";
		boolean stop = false;
		double result = 0.0;
		while(!stop) {
			String info = findParenthese(line);
			result= pane.processParenthese(info);
			System.out.println("info : "+info+"\t result : "+result);
			line  = line.replace("("+info+")", String.valueOf(result));
			if(!line.contains("(") && !line.contains("(")) {
				result= pane.processParenthese(line);
				stop = true;
			}
			System.out.println(line);
		}
		
		System.out.println("result : "+result);
		//		for(String st : info) {
		//			if(st.trim().length()==0) {
		//				continue;
		//			}
		//			String tmp = removeSign(st.trim());
		//			if(tmp.trim().length()==0) {
		//				continue;
		//			}
		//			double d = pane.processParenthese(tmp);
		//			line = line.replace(tmp, String.valueOf(d));
		//		}
		//		System.out.println(line);
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
