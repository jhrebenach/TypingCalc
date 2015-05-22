import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class DiadList {
	private char a, b;
	private int x, y, charCount;
	ArrayList<Edge> list = new ArrayList<Edge>();
	boolean[][] exists = new boolean[127][127];
	ValidChars isChar = new ValidChars();
	qwerty qList = new qwerty();
	dvorak dList = new dvorak();
	private long startTime, endTime;
	ArrayList<Edge> topDiads = new ArrayList<Edge>();
	private float qwertyTime, dvorakTime, partitionTime;
	Partition keyb;
	int rcnt, lcnt;
	static Scanner scan;
	private String file;

	
	public DiadList(){
		readText();
	}
	
	
	public void readText() {

		charCount = 0;
		BufferedReader reader;
		System.out.println("Enter the name of the plain text file you'd like to scan");
		
		while (true) {

			scan = new Scanner(System.in);
			String fileName = scan.nextLine();
			startTime = System.currentTimeMillis();
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),Charset.forName("UTF-8")));
				file = fileName;
				break;
			}
			catch (FileNotFoundException e) {
				System.out.println("File not found");
				System.out.println("Please enter a valid file name");
			}
		}
		try {
			while((x = reader.read()) != -1) {
				boolean charOne = false;
				if (x <= 90 && x >= 65)
					x = x + 32;
				a = (char) x;
				for (char p : isChar.list) {
					if (a == p)
						charOne = true;
				}
				if (charOne) {
					charCount++;
					while ((y = reader.read()) != -1) {
						if (y <= 90 && y >= 65)
							y = y + 32;
						boolean charTwo = false;
						b = (char) y;
						for (char p : isChar.list) {
							if (b == p)
								charTwo = true;
						}
						if (charTwo) {
							charCount++;
						    if (!exists[x][y]){
						    	if (((a != '\n' && a != '\r') || (b != '\n' && b != '\r')) || a == b) {
						    		if (b == '\r') {
						    			b = '\n';
						    			y = (int) b;
						    		}
						    		if (a == '\r') {
						    			a = '\n';
						    			x = (int) a;
						    		}
							    	list.add(new Edge(a, b));
							    	exists[x][y] = true;
						    	}
						    }
						    
						    else {
						    	for (Edge e : list) {
						    		if (e.getChar1() == a && e.getChar2() == b)
						    			e.countUp();
						    	}
						    }
							a = b;
							x = y;
						}
						else {
							break;
						}

					    
					}
				}
					
			}
		} 
			catch (IOException e) {
				System.out.println("File not found");
		}
		endTime = System.currentTimeMillis();
	} 
			

	public void printall() {
		System.out.println();
		for (Edge e : list)
			e.print();
	}
	
	public String getFileName() {
		return file;
	}
	
	public int totalDiadCount() {
		int sum = 0;
		for (Edge e : list)
			sum += e.getCount();
		return sum;
	}
	
	public int uniqueDiadCount() {
		return list.size();
	}
	
	public int charCount() {
		return charCount;
	}
	
	public float totalTime() {
		float totalTime = ((float)(endTime - startTime))/1000;
		return totalTime;
	}
	
	public void topDiads() {
		topDiads = list;
		Comparator<Edge> C = new Comparator<Edge>() {
		    public int compare(Edge e1, Edge e2) {
		        if (e1.getCount() < e2.getCount())
		            return 1;
		        if (e1.getCount() > e2.getCount())
		            return -1;
		        return 0;
		    }
		};
		
		Collections.sort(topDiads, C);
		System.out.println("Top 10 Diad Frequencies:");
		for (int i = 0; i < 10; i++)
			topDiads.get(i).print();
		
		
	}
	
	public void printAllTypingTimes() {
		qwertyTimeBasic();
		qwertyTimeImproved();
		System.out.println();
		dvorakTimeBasic();
		dvorakTimeImproved();
		System.out.println();
		partitionTimeBasic();
		partitionTimeImproved();
	}
	
	// QWERTY METHODS
	
	public void qwertyTimeBasic() {
		qwertyTime = 0;
		long startTime, endTime, finalTime;

		startTime = System.currentTimeMillis();
		for (Edge e : list) {
			char hand1 = 'r';
			char hand2 = 'r';
			for (char c : qList.left){
				if (e.getChar1() == c)
					hand1 = 'l';
				if (e.getChar2() == c)
					hand2 = 'l';
			}
			if (hand1 == hand2) {
				qwertyTime += (float)e.getCount()/120;
			}
			else {
				qwertyTime += (float)e.getCount()/240;
			}
			
		}
		endTime = System.currentTimeMillis();
		finalTime = (endTime - startTime);
		System.out.println("Can type in " + qwertyTime + " minutes on a QWERTY "
				+ "keyboard without alternating hand spaces, or " + qwertyTime/60 + " hours");
		System.out.println("Took " + finalTime + " ms to calculate this");

	}
	
	
	public void qwertyTimeImproved() {
		qwertyTime = 0;
		long startTime, endTime, finalTime;
		boolean spaceChange = false;
		startTime = System.currentTimeMillis();
		
		for (Edge e : list) {
			char hand1 = 'r';
			char hand2 = 'r';
			for (char c : qList.left){
				if (e.getChar1() == c)
					hand1 = 'l';
				if (e.getChar2() == c)
					hand2 = 'l';
				if (e.getChar1() == ' ' && e.getChar2() == ' ') {
					if (!spaceChange) {
						hand2 = 'r';
						spaceChange = true;
					}
					else if (spaceChange) {
						hand1 = 'r';
						spaceChange = false;
					}
				}
				else if (e.getChar2() == ' ' && hand2 == hand1) {
					if (hand1 == 'l') {
						hand2 = 'r';
						spaceChange = true;
					}

				}
				else if (e.getChar1() == ' ') {
					if (spaceChange) {
						hand1 = 'r';
						spaceChange = false;
					}
					
				}
					
			}
			if (hand1 == hand2) {
				qwertyTime += (float)e.getCount()/120;
			}
			else {
				qwertyTime += (float)e.getCount()/240;
			}
			
		}
		endTime = System.currentTimeMillis();
		finalTime = (endTime - startTime);
		System.out.println("Can type in " + qwertyTime + " minutes on a QWERTY "
				+ "keyboard with alternating hand spaces, or " + qwertyTime/60 + " hours");
		System.out.println("Took " + finalTime + " ms to calculate this");

			
		
	}
	


	// DVORAK METHODS
	
	public void dvorakTimeBasic() {
		dvorakTime = 0;
		long startTime, endTime, finalTime;
		startTime = System.currentTimeMillis();
		
		for (Edge e : list) {
			char hand1 = 'r';
			char hand2 = 'r';
			for (char c : dList.left){
				if (e.getChar1() == c)
					hand1 = 'l';
				if (e.getChar2() == c)
					hand2 = 'l';
			}
			if (hand1 == hand2) {
				dvorakTime += (float)e.getCount()/120;
			}
			else {
				dvorakTime += (float)e.getCount()/240;
			}
			
		}
		endTime = System.currentTimeMillis();
		finalTime = (endTime - startTime);
		System.out.println("Can type in " + dvorakTime + " minutes on a DVORAK keyboard "
				+ "without alternating hand spaces, or " + dvorakTime/60 + " hours");
		System.out.println("Took " + finalTime + " ms to calculate this");
	}
	
	public void dvorakTimeImproved() {
		dvorakTime = 0;
		long startTime, endTime, finalTime;
		startTime = System.currentTimeMillis();
		boolean spaceChange = false;
		for (Edge e : list) {
			char hand1 = 'r';
			char hand2 = 'r';
			for (char c : dList.left){
				if (e.getChar1() == c)
					hand1 = 'l';
				if (e.getChar2() == c)
					hand2 = 'l';
			}
			if (e.getChar1() == ' ' && e.getChar2() == ' ') {
				if (!spaceChange) {
					hand2 = 'r';
					spaceChange = true;
				}
				else if (spaceChange) {
					hand1 = 'r';
					spaceChange = false;
				}
			}
			else if (e.getChar2() == ' ' && hand2 == hand1) {
				if (hand1 == 'l') {
					hand2 = 'r';
					spaceChange = true;
				}

			}
			else if (e.getChar1() == ' ') {
				if (spaceChange) {
					hand1 = 'r';
					spaceChange = false;
				}
				
			}
				
			if (hand1 == hand2) {
				dvorakTime += (float)e.getCount()/120;
			}
			else {
				dvorakTime += (float)e.getCount()/240;
			}
			
		}
		endTime = System.currentTimeMillis();
		finalTime = (endTime - startTime);
		System.out.println("Can type in " + dvorakTime + " minutes on a DVORAK keyboard "
				+ "with alternating hand spaces, or " + dvorakTime/60 + " hours");
		System.out.println("Took " + finalTime + " ms to calculate this");
	}
	
	
	// PARTITION METHODS
	
	public void partition() {
		long startTime, endTime, finalTime;
		rcnt = 0; lcnt = 0;
		startTime = System.currentTimeMillis();
		keyb = new Partition();
		for (Edge e : topDiads) {
			char hand1 = 'n';
			char hand2 = 'n';
			if ((e.getChar1() != ' ' && e.getChar2() != ' ') && e.getChar1() != e.getChar2()) {
				for (char c : keyb.left) {
					if (e.getChar1() == c)
						hand1 = 'l';
					if (e.getChar2() == c)
						hand2 = 'l';
					}
				for (char c : keyb.right){
					if (e.getChar1() == c)
						hand1 = 'r';
					if (e.getChar2() == c)
						hand2 = 'r';
				}
				if (hand1 == 'n' && hand2 == 'n') {
					keyb.left.add(e.getChar1());
					lcnt++;
					keyb.right.add(e.getChar2());
					rcnt++;
				}
				else if (hand1 == 'n' && hand2 != 'n') {
					if (hand2 == 'l' && rcnt < lcnt+4) {
						keyb.right.add(e.getChar1());
						rcnt++;
					}
					else if (hand2 == 'r' && lcnt < rcnt+4) {
						keyb.left.add(e.getChar1());
						lcnt++;
					}
				}
				else if (hand1 != 'n' && hand2 == 'n') {
					if (hand1 == 'l' && rcnt < lcnt+4) {
						keyb.right.add(e.getChar2());
						rcnt++;
					}
					else if (hand1 == 'r' && lcnt < rcnt+4) {
						keyb.left.add(e.getChar2());
						lcnt++;
					}
	
				}
			}
			
		}
		keyb.left.add(' ');
		lcnt++;
		keyb.right.add(' ');
		rcnt++;
		
		for (char c : isChar.list) {
			boolean isFound = false;
			if (c != '\r') {
				for (char a : keyb.left) {
					if (a == c)
						isFound = true;
				}
				for (char a : keyb.right) {
					if (a == c)
						isFound = true;
				}
				if (!isFound) {
					if (lcnt < rcnt) {
						keyb.left.add(c);
						lcnt++;
					}
					else {
						keyb.right.add(c);
						rcnt++;
					}
				}
			}
		}
		
		
		endTime = System.currentTimeMillis();
		finalTime = (endTime - startTime);
		System.out.println("\nTook " + finalTime + " ms to partition the keyboard");
	}
	
	public void partitionTimeBasic() {
		long startTime, endTime, finalTime;
		startTime = System.currentTimeMillis();
		
		partitionTime = 0;

		for (Edge e : list) {
			char hand1 = 'r';
			char hand2 = 'r';
			for (char c : keyb.left){
				if (e.getChar1() == c)
					hand1 = 'l';
				if (e.getChar2() == c)
					hand2 = 'l';
			}
			if (hand1 == hand2) {
				partitionTime += (float)e.getCount()/120;
			}
			else {
				partitionTime += (float)e.getCount()/240;
			}
			
		}
		endTime = System.currentTimeMillis();
		finalTime = (endTime - startTime);

		System.out.println("Can type in " + partitionTime + " minutes on our optimized keyboard "
				+ "without alternating hand spaces, or " + partitionTime/60 + " hours");

		System.out.println("Took " + finalTime + " ms to calculate this");
	}
	
	public void partitionTimeImproved() {
		long startTime, endTime, finalTime;
		startTime = System.currentTimeMillis();
		
		partitionTime = 0;
		boolean spaceChange = false;
		
		for (Edge e : list) {
			char hand1 = 'r';
			char hand2 = 'r';

			for (char c : keyb.left){
				if (e.getChar1() == c)
					hand1 = 'l';
				if (e.getChar2() == c)
					hand2 = 'l';
			}
			if (e.getChar1() == ' ' && e.getChar2() == ' ') {
				if (!spaceChange) {
					hand2 = 'r';
					spaceChange = true;
				}
				else if (spaceChange) {
					hand1 = 'r';
					spaceChange = false;
				}
			}
			else if (e.getChar2() == ' ' && hand2 == hand1) {
				if (hand1 == 'l') {
					hand2 = 'r';
					spaceChange = true;
				}

			}
			else if (e.getChar1() == ' ') {
				if (spaceChange) {
					hand1 = 'r';
					spaceChange = false;
				}
				
			}
				
			if (hand1 == hand2) {
				partitionTime += (float)e.getCount()/120;
			}
			else {
				partitionTime += (float)e.getCount()/240;
			}
			
		}
		endTime = System.currentTimeMillis();
		finalTime = (endTime - startTime);
		
		System.out.println("Can type in " + partitionTime + " minutes on our optimized keyboard with "
				+ "alternating hand spaces, or " + partitionTime/60 + " hours");
		System.out.println("Took " + finalTime + " ms to calculate this");
	}
	
	
	public void printPartition() {
		System.out.println("\nLeft Hand Keys of Efficient Keyboard :");
		for (char c : keyb.left) {
			if (c == ' ')
				System.out.println("space");
			else if (c == '\n')
				System.out.println("enter");
			else 
				System.out.println(c);
		}
		System.out.println("Total left handed keys : " + lcnt);
		System.out.println();
		
		System.out.println("Right Hand Keys of Efficient Keyboard :");
		for (char c : keyb.right) {
			if (c == ' ')
				System.out.println("space");
			else if (c == '\n')
				System.out.println("enter");
			else 
				System.out.println(c);
		}
		System.out.println("Total right handed keys : " + rcnt);
		System.out.println();
	}
	
}
