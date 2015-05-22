
public class Edge {
	
	private char v, w;
	private int count = 1;
	
    public Edge(char vInput, char wInput) {
    	v = vInput;
    	w = wInput;
    }
    
    public char getChar1() {
    	return v;
    }
    
    public char getChar2() {
    	return w;
    }
    
    public void countUp() {
    	count++;
    }
    
    public int getCount() {
    	return count;
    }
    
    public void print() {
    	String a, b;
    	if (v == '\n')
    		a = "enter";
    	else if (v == ' ')
    		a = "space";
    	else
    		a = "" + v;
    	if (w == '\n')
    		b = "enter";
    	else if (w == ' ')
    		b = "space";
    	else
    		b = "" + w;

    	System.out.println(a + ", " + b + " : " + "count = " + count);
    }


}
