package Graph_proj1;
import java.util.*;
import java.lang.*;
import java.io.*;

/* This program has been implemented using string. For example -> S1 would be "abcdefgh "
 * the empty block of the 3x3 grid will be the empty space in the string. For example -> "abc defgh" */

public class F012800Source {
	
	// Initialised arraylists and arrays
	
	static HashMap<String, Boolean> visited_nodes = new HashMap<String, Boolean>();
	static int[] Indexes = {0,1,2,3,4,5,6,7,8}; // Possible indexes of the 3x3 grid
	static ArrayList<String> possible_basemoves = new ArrayList();
	static ArrayList<String> covered_nodes = new ArrayList();
	static ArrayList<String> base_nodes = new ArrayList();
	static ArrayList<String> states_intersection = new ArrayList();

	
	// Method to detect what moves a state can make
	
	static void check_Possible_States(int input) {
		
		for (int value: Indexes) {
			if(value == input - 3 ) {
				
				possible_basemoves.add("Up");
				
			}
			else if(value == input + 3 ) {
				
				possible_basemoves.add("Down");
				
			}
			else if(value == input - 1 ) {
				
				possible_basemoves.add("Left");
				
			}
			else if(value == input + 1 ) {
				
				possible_basemoves.add("Right");
			
			}
			
			else {
				continue;
			}
		}
	}
	
	
	// Method to implement possible moves on the input node
	
	static void all_nodes(String Input) {
		
		char[] Actual_Input = Input.toCharArray();
		char temp;
		int Index = 0;
		String covered_word = "";
		
		// operation to check if input has already been covered, it wont be covered again
		if(base_nodes.contains(Input)) {
			;
		}else {
			base_nodes.add(Input);
		}
		
		// Operation to check what the index of the empty block is
		
		for(int i = 0; i< Actual_Input.length; i++) {
			if(Actual_Input[i] == ' ') {
				Index = i;	
			}
		}
		
		// Method run to check what moves are possible for empty block at that index
		
		check_Possible_States(Index);
		
		// For-loop executed through possible moves for current state to generate state once move completed
		
		for(int i = 0; i< possible_basemoves.size(); i++) {
			
			// State implementation when possible move is Up
			
			if(possible_basemoves.get(i)== "Up") {
				for(int i1 = 0; i1< Actual_Input.length; i1++) {
					covered_word = covered_word + Actual_Input[i1];
				}
				
				// interchanging char with index. For example -> "abcdefgh " when moved UP -> "abcde ghf"
				
				char[] c = covered_word.toCharArray();
				temp = c[Index];
				c[Index] = c[Index - 3];
				c[Index - 3] = temp;
				
				covered_word = "";
				String new_word = new String(c);
				
				// If move has already been covered, wont be covered again
				if(covered_nodes.contains(new_word)) {
					
					continue;
				}
				else {
					
					/* After each operation, all the possible basemoves are cleared and the method is run again
					 If only one possible move, nothing will be done. If multiple states, code will just skip over covered state till 
					 all are covered */
						
						
						possible_basemoves.clear();
						covered_nodes.add(new_word);
						all_nodes(Input);
				
				}	
				
			}
			
			// State implementation when possible move is Down
			
			else if(possible_basemoves.get(i)== "Down") {
				for(int i1 = 0; i1< Actual_Input.length; i1++) {
					covered_word = covered_word + Actual_Input[i1];
				}
				char[] c = covered_word.toCharArray();
				
				temp = c[Index];
				c[Index] = c[Index + 3];
				c[Index + 3] = temp;
				
				covered_word = "";
				String new_word = new String(c);
				if(covered_nodes.contains(new_word)) {
					
					continue;
				}
				else {
					possible_basemoves.clear();
					covered_nodes.add(new_word);
					all_nodes(Input);
				}
			}
			
			// State implementation when possible move is Left
			
			else if(possible_basemoves.get(i)== "Left") {
				for(int i1 = 0; i1< Actual_Input.length; i1++) {
					covered_word = covered_word + Actual_Input[i1];
				}
				
				// since 3 or 6 is on the left edge and cannot move left to index 2 or 5, it isnt covered
				if(Index == 3 || Index == 6) {
					
					covered_word = "";
				}
				else {
				char[] c = covered_word.toCharArray();
				
				temp = c[Index];
				c[Index] = Actual_Input[Index - 1];
				c[Index - 1] = temp;
				
				covered_word = "";
				String new_word = new String(c);
				if(covered_nodes.contains(new_word)) {
					
					continue;
				}
			
				else {
					possible_basemoves.clear();
					
					covered_nodes.add(new_word);
					all_nodes(Input);
				}		
				}		
			}
			
			// State implementation when possible move is Right
			
			else if(possible_basemoves.get(i)== "Right") {
				for(int i1 = 0; i1< Actual_Input.length; i1++) {
					covered_word = covered_word + Actual_Input[i1];
				}
				
				// since 2 or 5 is on the right edge and cannot move right to index 3 or 6, it isnt covered
				if(Index == 2 || Index == 5) {
					
					covered_word = "";     
				}
				else {
					
				char[] c = covered_word.toCharArray();
				
				temp = c[Index];
				c[Index] = c[Index + 1];
				c[Index + 1] = temp;
				covered_word = "";
				String new_word = new String(c);
				 
				if(covered_nodes.contains(new_word)) {
					
					continue;
				}
				else {
					
					possible_basemoves.clear();
					covered_nodes.add(new_word);
					all_nodes(Input);
				}
				
				}				
			}
			
			else {
				continue;
			}
			
		}
		
		possible_basemoves.clear();
		
	}
	
	
	// Method to implement iterative Depth First Search. Implementation done using a stack
	// Input is the base state

	static private void DFS_it(String s) throws IOException {
		
		// base input is pushed into stack. All outputs are written into a text file
		                                                 
		Stack<String> S = new Stack<String>();
		S.push(s);   

		File output_file = new File("output.txt");
		FileWriter fstream = new FileWriter("output.txt");
		BufferedWriter info = new BufferedWriter(fstream);
		
		// each node that is pushed into the stack will be put into a hashmap as visited
		visited_nodes.put(s, true);
		
		
		// while loop to traverse through depth of each node. Runs till stack is empty
		
		while(!S.empty()) {
			
			// Takes the first element from the stack and pops it
			
			String u = S.peek();
			S.pop();
			all_nodes(u);	//method to get the possible states	
			info.write(u);
			info.newLine();
			
			for(String v: covered_nodes ){
				
				// checking to see  whether state has already been covered
				
				if(visited_nodes.containsKey(v)) {
					continue;
				}
				
				else {
					S.push(v);
					
					visited_nodes.put(v, true);
					
				}
				
			}
			covered_nodes.clear();
		
		}
		info.close();
		
	}
	
	// Method to check common states between R(S1) and R(S2)
	
	static void intersection_of_states(String First_Input, String Second_Input) throws IOException {
		
		// First input DFS
		
		DFS_it(First_Input);
		ArrayList<String> First_reachable_states = new ArrayList(); 
		First_reachable_states.addAll(visited_nodes.keySet());
		visited_nodes.clear();
		covered_nodes.clear();
	
		// Second Input DFS
		
		DFS_it(Second_Input);
		ArrayList<String> Second_reachable_states = new ArrayList(); 
		Second_reachable_states.addAll(visited_nodes.keySet());
		
		// Common states are found and written in text file
		
		for (int i = 0; i< Second_reachable_states.size(); i++) {  
	           
            if (First_reachable_states.contains(Second_reachable_states.get(i))) {
            	states_intersection.add(Second_reachable_states.get(i));	
            }
            else {
            	continue;
            }
		}
		
		File conjunction_output_file = new File("common_states_output.txt");
		FileWriter fstream = new FileWriter("common_states_output.txt");
		BufferedWriter info = new BufferedWriter(fstream);
		
		for(String i: states_intersection) {
		
			info.write(i);
			info.newLine();
		}
		info.close();
		
	}
	
	// Console interface for user to interact with system

	static void Program(String Input1, String Input2) throws IOException {
		System.out.println("Please select a character to proceed");
		
		System.out.println("");
		
		// Possible inputs for user with description of each input
		// Only character input accepted
		
		System.out.println("a -> All reachable states of S1");
		System.out.println("b -> The length of reachable states of S1");
		System.out.println("c -> All reachable states of S2");
		System.out.println("d -> The length of reachable states of S2");
		System.out.println("e -> All reachable states common between S1 and S2");
		System.out.println("f -> The length of common reachable states between S1 and S2");
		
		System.out.println("");
		
		Scanner sc=new Scanner(System.in);  
		char response = sc.next().charAt(0); 
		
		// Operations implemented for each valid input
		// Outputs for a, c and e are written in a text file
		
		if(response == 'a') {
			
			
			DFS_it(Input1);
			System.out.println("a) The reachable states of " + "\"" + Input1 + "\"" + " are printed into the text file.");
			System.out.println("The number of reachable states for " + "\""+ Input2 + "\"" +  " is: " + visited_nodes.size());
			visited_nodes.clear();
			covered_nodes.clear();
			
			System.out.println("------");
			
			// To check whether the user wants to continue with other operations
			
			System.out.println("Do you want to continue? Y -> Yes or N -> No");
			
			char continuation_response = sc.next().charAt(0); 
			if(continuation_response == 'Y') {
				
				Program(Input1, Input2);
				
			}
			else if(continuation_response == 'N') {
				System.out.println("Have a good day!");
				System.exit(0);
				
			}
			
			// Error checking
			else {
				System.out.println("Invalid!");
				System.exit(0);
				
			}
			
		}
		else if(response == 'b') {
			
			DFS_it(Input1);
			System.out.println("b) The number of reachable states for " + "\""+ Input2 + "\""+  " is: " + visited_nodes.size());
			visited_nodes.clear();
			covered_nodes.clear();
			System.out.println("------");
			System.out.println("Do you want to continue? Y -> Yes or N -> No");
			
			char continuation_response = sc.next().charAt(0); 
			if(continuation_response == 'Y') {
				
				Program(Input1, Input2);
				
			}
			else if(continuation_response == 'N') {
				System.out.println("Have a good day!");
				System.exit(0);
				
			}
			else {
				System.out.println("Invalid!");
				System.exit(0);
			}
			
		}
		else if(response == 'c') {
			
			DFS_it(Input2);
			System.out.println("c) The reachable states of " + "\"" + Input2 + "\"" + " are printed into the text file.");
			System.out.println("The number of reachable states for " + "\""+ Input2 + "\"" +  " is: " + visited_nodes.size());
			
			visited_nodes.clear();
			covered_nodes.clear();
			
			System.out.println("------");
			System.out.println("Do you want to continue? Y -> Yes or N -> No");
			
			char continuation_response = sc.next().charAt(0); 
			if(continuation_response == 'Y') {
				
				Program(Input1, Input2);
				
			}
			else if(continuation_response == 'N') {
				System.out.println("Have a good day!");
				System.exit(0);
				
			}
			else {
				System.out.println("Invalid!");
				System.exit(0);
			}
			
					
				}
		else if(response == 'd') {
			
			DFS_it(Input2);
			System.out.println("d) The number of reachable states for " + "\"" + Input2 + "\""+  " is: "  + visited_nodes.size());
			visited_nodes.clear();
			covered_nodes.clear();
			System.out.println("------");
			System.out.println("Do you want to continue? Y -> Yes or N -> No");
			
			char continuation_response = sc.next().charAt(0); 
			if(continuation_response == 'Y') {
				
				Program(Input1, Input2);
				
			}
			else if(continuation_response == 'N') {
				
			}
			else {
				System.out.println("Invalid!");
				System.exit(0);
			}
			
		}
		else if(response == 'e') {
			
			
			intersection_of_states(Input1, Input2);
			System.out.println("e) The reachable states of " + "\"" + Input1 + "\"" + " and " + "\"" + Input2 + "\"" + " are printed into the text file.");
			System.out.println("Size of common states: " + states_intersection.size());
			System.out.println("------");
			System.out.println("Do you want to continue? Y -> Yes or N -> No");
			
			char continuation_response = sc.next().charAt(0); 
			if(continuation_response == 'Y') {
				
				Program(Input1, Input2);
				
			}
			else if(continuation_response == 'N') {
				System.out.println("Have a good day!");
				System.exit(0);
			}
			else {
				System.out.println("Invalid!");
				System.exit(0);
			}
			
		}
		else if(response == 'f') {
			
			intersection_of_states(Input1, Input2);
			System.out.println("f) The number of reachable states for " + "\"" + Input1 + "\""+ " and " + "\"" + Input2 + "\""  + "  is "  + states_intersection.size());
			
			System.out.println("------");
			System.out.println("Do you want to continue? Y -> Yes or N -> No");
			
			char continuation_response = sc.next().charAt(0); 
			if(continuation_response == 'Y') {
				
				Program(Input1, Input2);
				
			}
			else if(continuation_response == 'N') {
				
				System.out.println("Have a good day!");
				System.exit(0);
				
			}
			
			else {
				
				System.out.println("Invalid!");
				System.exit(0);
			}
			
		}
		
		// Error checking
		
		else {
			System.out.println("Invalid!");
			System.exit(0);
		}
	
	}
	
	
	
	/* Main method where base inputs are entered. If user wants to run their own input,
	 * please replace the inputs below. Please enter a space at the index you would like the empty block to be located
	 */
	
	public static void main(String[] args) throws IOException {
		
		String input1 = "abcdefgh "; // Standard input1 from Figure 2
		String input2 = " ebahcdgf"; // Standard input2 from Figure 3
		
		Scanner sc = new Scanner(System.in);  
		
		// User input taken. For 4 ii please select C and for 4 iii Select S
		
		System.out.println("Do you want to enter custom inputs or use the ones from the Figure 2 and Figure 3? C -> Custom Inputs, S - Standard Inputs ");
		
		char response = sc.next().charAt(0);
		
		if(response == 'C') {

			System.out.print("Please enter the number of strings you want to enter (Only 2): ");   
		
			// Program proceeds only if input is 2
			
			if (sc.nextInt() == 2) {
			
				System.out.println("Please enter string inputs: (enter one and the other after hitting enter)");
			
				// String inputs with space as indication of empty block need to be entered
				// type one input, hit enter and then the other one
				
				String[] string = new String [2];      
				//consuming the <enter> from input above  
				sc.nextLine();  
			
				for (int i = 0; i < string.length; i++)   { 
				
					string[i] = sc.nextLine();  
					
				}  
			
				input1 = string[0];
				input2 = string[1];
				Program(input1, input2);
			
			}
			
			else {
				
				System.out.println("Please enter 2!");
				System.exit(0);
				
			}
			
			
		}
		
		else if(response == 'S'){
			
			Program(input1, input2);	
			
		}
		else {
			System.out.println("Invalid!");
			System.exit(0);
		}
		
		
		
	}
		}