package paket.proba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeSet;

public class SimEnka {

	static ArrayList<String> ulazi;
	static TreeSet<String> stanja;
	static TreeSet<String> abeceda;
	static TreeSet<String> OKStanja;
	static String pocetnoStanje;
	static LinkedHashMap<String, String> prijelazi;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		String[] split;
		prijelazi = new LinkedHashMap<String, String>();
		
		// 1. red
		line = br.readLine();
		split = line.split("\\|");
		ulazi = new ArrayList<>();
		for (int i = 0; i < split.length; i++) ulazi.add(split[i]);
		
		// 2. red
		line = br.readLine();
		split = line.split(",");
		stanja = new TreeSet<>();
		for (int i = 0; i < split.length; i++) stanja.add(split[i]);
		
		// 3. red
		line = br.readLine();
		split = line.split(",");
		abeceda = new TreeSet<>();
		for (int i = 0; i < split.length; i++) abeceda.add(split[i]);
		
		// 4. red
		line = br.readLine();
		split = line.split(",");
		OKStanja = new TreeSet<>();
		for (int i = 0; i < split.length; i++) OKStanja.add(split[i]);
		
		// 5. red
		line = br.readLine();
		pocetnoStanje = line;
		
		// ostali redovi
		while ((line = br.readLine()) != null ) {
			split = line.split("->");
			prijelazi.put(split[0], split[1]);
		}
		
		for (String ulaz : ulazi) {
			automat(ulaz);
		}	
	}
	
	private static void automat(String ulaz) {
		String[] ulazniZnakovi = ulaz.split(",");
		TreeSet<String> EStanja = new TreeSet<>();
		TreeSet<String> curStanja = new TreeSet<>();
		TreeSet<String> retE = new TreeSet<>();
		TreeSet<String> retP = new TreeSet<>();
		
		curStanja.add(pocetnoStanje);
		
		// epsilon okruzenje pocetnog stanja
		EStanja.addAll(curStanja);
		int brRetStanja = 0;
		while (true) {
			retE = provjeriE(EStanja);
			if (retE.size() == 0 || retE.size() == brRetStanja) break;
			brRetStanja = retE.size();
			EStanja.addAll(retE);
		}
		
		curStanja.addAll(EStanja);
		ispis(curStanja);
		System.out.printf("|");
		
		int i = 0;
		for (String znak : ulazniZnakovi) {
			EStanja.removeAll(EStanja);
			retP = napraviPrijelaz(curStanja, znak);
			EStanja.addAll(retP);
			
			//epsilon okruzenja
			brRetStanja = 0;
			if (retP.size() != 0) {
				while (true) {
					retE = provjeriE(EStanja);
					if (retE.size() == 0 || retE.size() == brRetStanja) break;
					brRetStanja = retE.size();
					EStanja.addAll(retE);
				}

				if (EStanja.contains("#") && EStanja.size() != 1) EStanja.remove("#");
				ispis(EStanja);

				curStanja.removeAll(curStanja);
				curStanja.addAll(EStanja);

			} else {
				curStanja.removeAll(curStanja);
				curStanja.add("#");
				System.out.print("#");
			}
			
			if (i != ulazniZnakovi.length - 1) System.out.print("|");
			++i;
		}
		System.out.println();
		
	}
	
	private static TreeSet<String> provjeriE(TreeSet<String> curStanja) {
		
		TreeSet<String> retStanja = new TreeSet<>();
		
		for (String stanje : curStanja) {
			String Eprijelaz = stanje.concat(",$");
			
			if (prijelazi.containsKey(Eprijelaz)) {
				String nextStanja = prijelazi.get(Eprijelaz);
				String[] splitStanja = nextStanja.split(",");
				for (String st : splitStanja) {
					retStanja.add(st);
				}
			}
		}
		
		return retStanja;
	}
	
	private static TreeSet<String> napraviPrijelaz(TreeSet<String> curStanja, String znak) {
		TreeSet<String> retStanja = new TreeSet<>();
		
		for (String stanje : curStanja) {
			String prijelaz = stanje.concat("," + znak);
			if (prijelazi.containsKey(prijelaz)) {
				String nextStanja = prijelazi.get(prijelaz);
				String[] splitStanja = nextStanja.split(",");
				for (String st : splitStanja) {
					retStanja.add(st);
				}
				
			} else {
				retStanja.add("#");
			}
		}
		
		return retStanja;
	}
	
	private static void ispis(TreeSet<String> stanjaZaIspis) {
		int i = 0;
		for (String x : stanjaZaIspis) {
			System.out.printf("%s", x);
			if (i < stanjaZaIspis.size()-1) {
				System.out.printf(",");
				++i;
			}
		}
	}

}
