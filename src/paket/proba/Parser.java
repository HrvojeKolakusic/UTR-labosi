package paket.proba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Parser {

	static Stack<Character> stog = new Stack<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		
		for (int i = line.length() - 1; i >= 0; --i) stog.push(line.charAt(i));
		
		boolean OK = rekParsiranje('S');
		System.out.println();
		
		if (OK && (stog.isEmpty())) System.out.println("DA");
		else System.out.println("NE");
		
	}
	
	static boolean rekParsiranje(char znak) {
		
		if (znak == 'S') {
			System.out.print(znak);
			
			if (stog.isEmpty()) return false;
			else if (stog.peek().equals('b')) {
				stog.pop();
				if (rekParsiranje('B')) return rekParsiranje('A');
				return false;
			}
			
			if (stog.isEmpty()) return false;
			else if (stog.peek().equals('a')) {
				stog.pop();
				if (rekParsiranje('A')) return rekParsiranje('B');
				return false;
			}
			
		} else if (znak == 'A') {
			System.out.print(znak);
			
			if (stog.isEmpty()) return false;
			else if (stog.peek().equals('b')) {
				stog.pop();
				return rekParsiranje('C');
			}
			
			if (stog.isEmpty()) return false;
			else if (stog.peek().equals('a')) {
				stog.pop();
				return true;
			}
			
		} else if (znak == 'B') {
			System.out.print(znak);
			
			if (stog.isEmpty()) return true;
			else if (stog.peek().equals('c')) {
				stog.pop();
				if (stog.peek().equals('c')) {
					stog.pop();
					if (rekParsiranje('S') && stog.peek().equals('b')) {
						stog.pop();
						if (stog.peek().equals('c')) {
							stog.pop();
							return true;
						}
					}
				}
			} else return true;
			
		} else if (znak == 'C') {
			System.out.print(znak);
			if (rekParsiranje('A')) return rekParsiranje('A');
			return false;
		}
		
		return false;
	}
}
