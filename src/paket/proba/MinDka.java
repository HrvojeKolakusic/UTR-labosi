package paket.proba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;

public class MinDka {

	static TreeSet<String> stanja;
	static TreeSet<String> abeceda;
	static TreeSet<String> OKStanja;
	static String pocetnoStanje;
	static LinkedHashMap<String, String> prijelazi;
	static LinkedHashMap<String, String> ispisPrijelazi = new LinkedHashMap<>();
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		String[] split;
		prijelazi = new LinkedHashMap<String, String>();
		
		// 1. red
		line = br.readLine();
		split = line.split(",");
		stanja = new TreeSet<>();
		for (int i = 0; i < split.length; i++) stanja.add(split[i]);
		
		// 2. red
		line = br.readLine();
		split = line.split(",");
		abeceda = new TreeSet<>();
		for (int i = 0; i < split.length; i++) abeceda.add(split[i]);
		
		// 3. red
		line = br.readLine();
		split = line.split(",");
		OKStanja = new TreeSet<>();
		for (int i = 0; i < split.length; i++) OKStanja.add(split[i]);
		
		// 4. red
		line = br.readLine();
		pocetnoStanje = line;
		
		// ostali redovi
		while ((line = br.readLine()) != null ) {
			split = line.split("->");
			prijelazi.put(split[0], split[1]);
		}
		
		// dohvatljiva stanja
		List<String> dohStanja = new ArrayList<>();
		dohStanja.add(pocetnoStanje);
		for (int i = 0; i < dohStanja.size(); ++i) {
			String curStanje = dohStanja.get(i);
			for (String prijelaz : prijelazi.keySet()) {
				String novoStanje = prijelazi.get(prijelaz);
				if (prijelaz.split(",")[0].equals(curStanje) 
						&& !(dohStanja.contains(novoStanje))) dohStanja.add(novoStanje);
			}
		}
		
		// brisi prijelaze za nedohvatljiva stanja pa nedohvatljiva stanja
		LinkedHashMap<String, String> pomPrijelazi = new LinkedHashMap<>();
		for (String prijelaz : prijelazi.keySet()) {
			if (dohStanja.contains(prijelaz.split(",")[0])) pomPrijelazi.put(prijelaz, prijelazi.get(prijelaz));
		}
		
		prijelazi = pomPrijelazi;
		TreeSet<String> pom = new TreeSet<>();
		pom.addAll(dohStanja);
		stanja = pom;
		
		// minimizacija DKA
		LinkedHashMap<String, String> tablicaX = new LinkedHashMap<>();
		ArrayList<String> stanjaList = new ArrayList<>();
		stanjaList.addAll(stanja);
		for (int i = 0; i < stanja.size() - 1; ++i) {
			for (int j = i + 1; j < stanja.size(); ++j) {
				boolean testI = OKStanja.contains(stanjaList.get(i));
				boolean testJ = OKStanja.contains(stanjaList.get(j));
				if (testI != testJ) {
					tablicaX.put(stanjaList.get(i) + "," + stanjaList.get(j), "X");
				}
			}
		}
		
		LinkedHashMap<String, String> listaUvjeta = new LinkedHashMap<>();
		for (int i = 0; i < stanja.size() - 1; ++i) {
			for (int j = i + 1; j < stanja.size(); ++j) {
				for (String znak : abeceda) {
					String stanje1 = prijelazi.get(stanjaList.get(i) + "," + znak);
					String stanje2 = prijelazi.get(stanjaList.get(j) + "," + znak);
					if (stanje1.compareTo(stanje2) > 0) {
						String zamjena;
						zamjena = stanje1;
						stanje1 = stanje2;
						stanje2 = zamjena;
					}
					if (tablicaX.containsKey(stanje1 + "," + stanje2)) {
						tablicaX.put(stanjaList.get(i) + "," + stanjaList.get(j), "X");
					} else {
						listaUvjeta.put(stanje1 + "," + stanje2, 
								stanjaList.get(i) + "," + stanjaList.get(j));
					}
				}
			}
		}	
		
		int brojac = 0;
		while (true) {
			for (String stanja : listaUvjeta.keySet()) {
				if (tablicaX.containsKey(stanja)) {
					tablicaX.put(listaUvjeta.get(stanja), "X");
					brojac++;
				}
			}
			if ((brojac > listaUvjeta.size() * 4) || brojac == 0) break;
		}
		
		TreeSet<String> jednakaStanja = new TreeSet<>();
		for (int i = 0; i < stanja.size() - 1; i++) {
			for (int j = i + 1; j < stanja.size(); j++) {
				if (!tablicaX.containsKey(stanjaList.get(i) + "," + stanjaList.get(j)))
					jednakaStanja.add(stanjaList.get(i) + "," + stanjaList.get(j));
			}
		}
		
		// brisanje istih stanja
		ispisPrijelazi = prijelazi;
		for (String stanje : jednakaStanja) {
			stanja.remove(stanje.split(",")[1]);
			OKStanja.remove(stanje.split(",")[1]);
			for (String znak : abeceda) {
				ispisPrijelazi.remove(stanje.split(",")[1] + "," + znak);
			}
			if (stanje.split(",")[1].equals(pocetnoStanje)) pocetnoStanje = stanje.split(",")[0];
		}
		
		for (String stanje : ispisPrijelazi.keySet()) {
			for (String istaStanja : jednakaStanja) {
				if (ispisPrijelazi.get(stanje).equals(istaStanja.split(",")[1])) {
					ispisPrijelazi.put(stanje, istaStanja.split(",")[0]);
				}
			}
		}
		
		// pomoc za zadnji
		TreeSet<String> pomOK = new TreeSet<>();
		pomOK.addAll(OKStanja);
		for (String stanje : pomOK) {
			if (!(stanja.contains(stanje))) OKStanja.remove(stanje);
		}
		
		// ispisi stanja, abecedu, okstanja, pocetno i ispisPrijelazi
		printSet(stanja);
		printSet(abeceda);
		printSet(OKStanja);
		System.out.println(pocetnoStanje);
		for (String x : ispisPrijelazi.keySet()) {
			System.out.println(x + "->" + ispisPrijelazi.get(x));
		}
		
	}
	
	private static void printSet(TreeSet<String> elementi) {
		if (elementi.size() == 0) {
			System.out.println();
			return;
		}
		for (String x : elementi) {
			if (x.equals(elementi.last())) {
				System.out.println(x);
			} else {
				System.out.print(x + ",");
			}
		}
	}
}
