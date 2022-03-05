package paket.proba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.TreeSet;

public class SimPa {

	static LinkedHashMap<String, String> prijelazi;
	static ArrayList<String> ulazi;
	static TreeSet<String> stanja;
	static TreeSet<String> abeceda;
	static TreeSet<String> stogZnakovi;
	static TreeSet<String> OKStanja;
	static String pStanje;
	static String curStanje;
	static char pStogZnak;
	static char curStogZnak;
	static Stack<Character> stog = new Stack<>();
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		String[] split;
		prijelazi = new LinkedHashMap<>();
		
		// 1. red
		line = br.readLine();
		split = line.split("\\|");
		ulazi = new ArrayList<>();
		for (int i = 0; i < split.length; ++i) ulazi.add(split[i]);
		
		// 2. red
		line = br.readLine();
		split = line.split(",");
		stanja = new TreeSet<>();
		for (int i = 0; i < split.length; ++i) stanja.add(split[i]);
		
		// 3. red
		line = br.readLine();
		split = line.split(",");
		abeceda = new TreeSet<>();
		for (int i = 0; i < split.length; ++i) abeceda.add(split[i]);
		
		// 4. red
		line = br.readLine();
		split = line.split(",");
		stogZnakovi = new TreeSet<>();
		for (int i = 0; i < split.length; ++i) stogZnakovi.add(split[i]);
		
		// 5. red
		line = br.readLine();
		split = line.split(",");
		OKStanja = new TreeSet<>();
		for (int i = 0; i < split.length; ++i) OKStanja.add(split[i]);
		
		// 6. red
		line = br.readLine();
		pStanje = line;
		
		// 7. red
		line = br.readLine();
		pStogZnak = line.toCharArray()[0];
		
		// ostali
		while ((line = br.readLine()) != null ) {
			split = line.split("->");
			prijelazi.put(split[0], split[1]);
		}
		
		for (String ulaz : ulazi) {
			
			String[] uStanja = ulaz.split(",");
			stog.add(pStogZnak);
			curStogZnak = pStogZnak;
			curStanje = pStanje;
			System.out.printf("%s#%c|", curStanje, curStogZnak);
			
			boolean fail = false;
			for (int i = 0; i < uStanja.length; ++i) {
				if (prijelazi.containsKey(curStanje + "," + uStanja[i] + "," + curStogZnak)) {
					next(curStanje + "," + uStanja[i] + "," + curStogZnak);
				} else if (prijelazi.containsKey(curStanje + ",$," + curStogZnak)) {
					next(curStanje + ",$," + curStogZnak);
					--i;
				} else {
					System.out.println("fail|0");
					fail = true;
					break;
				}
			}
			
			while (!OKStanja.contains(curStanje)) {
				boolean promjena = false;
				if (prijelazi.containsKey(curStanje + ",$," + curStogZnak)) {
					next(curStanje + ",$," + curStogZnak);
					promjena = true;
				}
				if (!promjena) break;
			}
			
			if (!fail) {
				if (OKStanja.contains(curStanje)) System.out.println("1");
				else System.out.println("0");
			}
			
			stog.clear();
		}
	}
	
	static void next(String key) {
		String prijelaz = prijelazi.get(key);
		curStanje = prijelaz.split(",")[0];
		
		char[] revZnakovi = new StringBuilder(prijelaz.split(",")[1]).reverse().toString().toCharArray();
		if (!stog.isEmpty()) stog.pop();
		for (char znak : revZnakovi) stog.push(znak);
		if (revZnakovi[0] == '$') stog.pop();
		if (stog.isEmpty()) stog.push('$');
		curStogZnak = stog.peek();
		
		System.out.printf("%s#", curStanje);
		String ispis = "";
		for (char znak : stog) ispis = ispis + znak;
		char[] revIspis =  new StringBuilder(ispis).reverse().toString().toCharArray();
		for (int i = 0; i < revIspis.length; ++i) System.out.printf("%c", revIspis[i]);
		System.out.printf("|");
	}
}
