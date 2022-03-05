package paket.proba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeSet;

public class SimTS {

	static LinkedHashMap<String, String> prijelazi;
	static TreeSet<String> stanja;
	static ArrayList<String> ulazi;
	static TreeSet<String> znakoviT;
	static String empty;
	static char[] traka;
	static TreeSet<String> OKStanja;
	static String stanje;
	static Integer glava;
 	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		String[] split;
		prijelazi = new LinkedHashMap<>();
		
		// 1. red
		line = br.readLine();
		split = line.split(",");
		stanja = new TreeSet<>();
		for (int i = 0; i < split.length; ++i) stanja.add(split[i]);
		
		// 2. red
		line = br.readLine();
		split = line.split(",");
		ulazi = new ArrayList<>();
		for (int i = 0; i < split.length; ++i) ulazi.add(split[i]);
		
		// 3. red
		line = br.readLine();
		split = line.split(",");
		znakoviT = new TreeSet<>();
		for (int i = 0; i < split.length; ++i) znakoviT.add(split[i]);
		
		// 4. red
		empty = br.readLine();
		
		// 5. red
		line = br.readLine();
		traka = line.toCharArray();
		
		// 6. red
		line = br.readLine();
		split = line.split(",");
		OKStanja = new TreeSet<>();
		for (int i = 0; i < split.length; ++i) OKStanja.add(split[i]);
		
		// 7. red
		stanje = br.readLine();
		
		// 8. red
		line = br.readLine();
		glava = Integer.parseInt(line);
		
		// ostali
		while ((line = br.readLine()) != null ) {
			split = line.split("->");
			prijelazi.put(split[0], split[1]);
		}
		
		// TS
		while (true) {
			if (!prijelazi.containsKey(stanje + "," + traka[glava])) break;
			else {
				String prijelaz = prijelazi.get(stanje + "," + traka[glava]);
				stanje = prijelaz.split(",")[0];
				traka[glava] = prijelaz.split(",")[1].toCharArray()[0];
				String pomak = prijelaz.split(",")[2];
				
				if ((glava == 0 && pomak.equals("L"))
						|| (glava == 69 && pomak.equals("R"))) break;
				else if (pomak.equals("L")) glava--;
				else glava++;
			}
		}
		
		// ispis
		System.out.print(stanje + "|" + glava + "|");
		for (char x : traka) System.out.print(x);
		if (OKStanja.contains(stanje)) System.out.println("|1");
		else System.out.println("|0");
	}
}
