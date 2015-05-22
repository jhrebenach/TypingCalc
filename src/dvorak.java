import java.util.ArrayList;

public class dvorak {
	
	public ArrayList<Character> left = new ArrayList<Character>();
	public ArrayList<Character> right = new ArrayList<Character>();
	
	public dvorak() {
		
		left.add(',');
		left.add('.');
		left.add('p');
		left.add('y');
		left.add('a');
		left.add('o');
		left.add('e');
		left.add('u');
		left.add('i');
		left.add(';');
		left.add('q');
		left.add('j');
		left.add('k');
		left.add('1');
		left.add('2');
		left.add('3');
		left.add('4');
		left.add('5');
		left.add('`');
		left.add(' ');
		
		right.add('6');
		right.add('7');
		right.add('8');
		right.add('9');
		right.add('0');
		right.add('[');
		right.add(']');
		right.add('f');
		right.add('g');
		right.add('c');
		right.add('r');
		right.add('l');
		right.add('d');
		right.add('h');
		right.add('t');
		right.add('n');
		right.add('s');
		right.add('x');
		right.add('b');
		right.add('m');
		right.add('w');
		right.add('v');
		right.add('z');
		right.add('-');
		right.add('/');
		right.add('=');
		right.add('\n');
		right.add('\r');
		right.add(' ');
	}

}
