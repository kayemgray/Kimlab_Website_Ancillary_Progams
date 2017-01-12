// Name Shifter, By Kevin Gray
// This program moves entries from the "undergraduates" or "research scientists" 
// sections of the website to the "former members" section. It requires two text 
// files: 1) The body of the undergraduate section titled "UndergradCode" and 2)
// the body of the former members section, titled "FormerCode". Output files for the 
// Undergraduates or Former Members sections are respectively titled UndergradPage.txt and
// FormerPage.txt.

import java.io.*; 
import java.util.*;

public class Name_Shifter_KG {

   public static void main(String []args) throws FileNotFoundException {
	
      Scanner console = new Scanner(System.in);
      ArrayList<String> ActiveMembers = new ArrayList<String>();
      ArrayList<String> FormerMembers = new ArrayList<String>(); 
      Map<String,String> Pics = new TreeMap<String,String>(); 
      Map<String,String> Emails = new TreeMap<String,String>();
      Map<String,String> Position = new TreeMap<String,String>();

      Scanner ActiveInput = new Scanner(new File("UndergradCode.txt"));
      Scanner FormerInput = new Scanner(new File("FormerCode.txt"));
      PrintStream UndergradPage = new PrintStream(new File("UndergradPage.txt"));
      PrintStream FormerPage = new PrintStream(new File("FormerPage.txt")); 
     
      assignNames(ActiveInput, ActiveMembers, Pics, Emails, Position);      
      String more = "y";
      while (more.equals("y")){
         System.out.println(ActiveMembers);
         System.out.println("enter a person to deactivate");
         String Name = console.nextLine(); 
	      FormerMembers.add("\'\'\'" + Name + "\'\'\'");
	      ActiveMembers.remove("\'\'\'" + Name + "\'\'\'");
         System.out.println("any more names? y/n");
         more = console.nextLine();
      }

	PrintPage(ActiveMembers, Emails, Pics, UndergradPage, Position);
   for (String i : FormerMembers) {
      Position.put(i, "Undergraduate Student"); // Change to "Research Scientist" when updating
                                                // Reaserch Scientist page
   }

	assignNames(FormerInput, FormerMembers, Pics, Emails, Position);
	PrintPage(FormerMembers, Emails, Pics, FormerPage, Position);
	
   }
   
   // Extracts information from the webpage necessary to transfer member profiles from one 
   // section to another
	public static void assignNames(Scanner input, ArrayList<String> Members, 
                             Map<String,String> Pics, Map<String,String> Emails,
                             Map<String, String> Position) {
	   
      String Line = input.nextLine();
	   int i = Members.size();
      Queue<String> store = new LinkedList<String>();
	   while (input.hasNextLine()) { 
         if (Line.startsWith("|[")) {
            store.add(Line.substring(1)); 
		   } else if (Line.startsWith("|width=\"175\"|\'\'\'")) {
			   Members.add("\'\'\'" + Line.split("\'\'\'")[1] + "\'\'\'");
			   Emails.put(Members.get(i), Line.split("<br>")[1]);
            Pics.put(Members.get(i), store.remove());
            Line = Line + " ";
            Position.put(Members.get(i), Line.split("<br>")[2]);
			   i++;
         }
		   Line = input.nextLine();
      }
      Members.add(" ");
	   Members.add(" ");
      Emails.put(" "," ");
      Pics.put(" "," ");
      Position.put(" ", " "); 
   }
   
   // Prints a list of names, pictures, and associated info into the correct format with 
   // which the website can be updated
	public static void PrintPage(ArrayList List, Map<String,String> Emails, Map<String,
                        String> Pics, PrintStream Page, Map<String,String> Position) {
		for (int i = 0; i < List.size() / 3; i++) {
         Page.println("|-align=\"center\""); 
			Page.println("|");
			Page.println("|" + Pics.get(List.get(3 * i))); 
			Page.println("|");
			Page.println("|" + Pics.get(List.get(3 * i + 1))); 
			Page.println("|");
			Page.println("|" + Pics.get(List.get(3 * i + 2)));
         Page.println(); 
			Page.println( "|-align=\"center\" valign=\"top\"");
			Page.println("|width=\"25\"|");
			Page.println("|width=\"175\"|" + List.get(3 * i) + "<br> " + Emails.get(List.get(3 * i)) + "<br>" + Position.get(List.get(3 * i)));
			Page.println("|width=\"70\"|");
			Page.println("|width=\"175\"|" + List.get(3 * i + 1) + "<br> " + Emails.get(List.get(3 * i + 1)) + "<br>" + Position.get(List.get(3 * i + 1)));
			Page.println("|width=\"70\"|");
		 	Page.println("|width=\"175\"|" + List.get(3 * i + 2) + "<br> " + Emails.get(List.get(3 * i + 2)) + "<br>" + Position.get(List.get(3 * i + 2)));  
			Page.println("|width=\"25\"|");
			Page.println();

		}
	}
}