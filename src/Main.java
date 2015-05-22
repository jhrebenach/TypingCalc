//JEFFREY HREBENACH

public class Main {
	
	static String fileName = "DemocracyInAmerica";
	static DiadList dl;

	
	public static void main(String args[]) {

		
		dl = new DiadList();
		System.out.println("Text : " + dl.getFileName());
		System.out.println("Total character count : " + dl.charCount());
		System.out.println("Unique diad count : " + dl.uniqueDiadCount());
		//dl.printall();
		System.out.println("\nTook " + dl.totalTime() + " seconds to process the diads \n");
		dl.topDiads();
		dl.partition();
		dl.printPartition();
		dl.printAllTypingTimes();

	}
}