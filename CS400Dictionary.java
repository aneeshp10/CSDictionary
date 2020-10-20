// --== CS400 File Header Information ==--
// Name: Aneesh Patil
// Email: apatil6@wisc.edu
// Team: GA
// Role: Back End
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * The main class that runs the User Interface for the CS400 Dictionary
 * application
 *
 * @author Aneesh, Arjun Iyer
 *
 */
public class CS400Dictionary extends RedBlackTree<WordNode> {

	private LoadFile loader;
	private Scanner scan;
	public RedBlackTree<WordNode> rbt;

	static String actionPrompt = "Select one of the following actions:\n"
			+ "  1.Add a new term                               [Enter 'a']\n"
			+ "  2.Get an existing term's definition            [Enter 'g']\n"
			+ "  3.Display all terms in the dictionary          [Enter 'd']\n"
			+ "  4.Quiz yourself on the terms                   [Enter 'q']\n"
			+ "  5.Exit the dictionary?                         [Enter 'e']";

	/**
	 * Default constructor - initializes instance variables, database (txt file)
	 * 
	 * @author Arjun Iyer
	 * @throws FileNotFoundException
	 */
	public CS400Dictionary() throws FileNotFoundException {

		loader = new LoadFile(new File("CS400Dictionary.txt"));
		rbt = new RedBlackTree<WordNode>();
		loader.loadData(rbt);
		scan = new Scanner(System.in);

	}

	/**
	 * Main interface for the Dictionary application
	 * 
	 * @author Arjun Iyer
	 */
	public void userMenu() {

		String userInput;
		boolean running = true; // controls the running of the main app

		// while boolean variable is true
		while (running) {

			userInput = ""; // initialize user input

			System.out.println();
			System.out.println(actionPrompt);
			System.out.println();
			System.out.print("--> ");
			userInput = scan.nextLine().trim().toLowerCase();

			// match user input with expected output, print message if input is invalid
			switch (userInput) {

			case "a":
				System.out.println("");
				addTermHelper();
				break;

			case "g":
				System.out.println("");
				getTermDefHelper();
				break;

			case "d":
				System.out.println("");
				displayAllTermsHelper();
				break;

			case "q":
				System.out.println("");
				quizHelper();
				break;

			case "e":
				System.out.println("");
				running = false;
				break;

			default:
				System.out.println("Input not recognized, please try again\n");
				break;

			}
		}
	}

	/**
	 * Models the interface of the CS400 Dictionary Quiz
	 * 
	 * @author aneesh, Arjun Iyer
	 */

	public void quizHelper() {

		System.out.println("NOTE: this quiz has " + loader.size + " terms. You will have 10 seconds "
				+ "to come up with each definition before the answer is displayed."); // print welcome message and
																						// number of words in the
																						// dictionary
		System.out.println("Press any key to start the quiz. If you want to return to main menu, press 'e'");

		// checks user input, if 'e' exit to main menu
		if (scan.nextLine().trim().toLowerCase().equals("e")) {
			System.out.print("\nGoing back to the main menu");
			// UI for exiting
			try {
				int i = 3;
				while (i > 0) {
					Thread.sleep(1000); // variable --> check
					System.out.print(".");
					i--;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("");
			return;
		}
		// enters else block if input character is not 'e'
		else {
			boolean run = true;
			Node<WordNode> current = rbt.root; // start traversing by setting root node of tree
			System.out.println("");
			System.out.print("\nStarting the quiz");
			// UI for running quiz
			try {
				int i = 3;
				while (i > 0) {
					Thread.sleep(1000);
					System.out.print(".");
					i--;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("");

			// while run is true, continue the quiz
			while (run) {
				Random rand = new Random(); // use random number generator to shuffle the quiz order to help revision be
											// more comprehensive and less monotonous
				int randNum = rand.nextInt(3);
				// if 0, preOrder. if 1, inOrder. if 2, postOrder
				if (randNum == 0) {
					preOrder(current);
				} else if (randNum == 1) {
					inOrder(current);
				} else {
					postOrder(current);
				}
				run = false; // set flag off
				System.out.println("\nNo more terms left. The quiz has ended");
				System.out.println(
						"Enter 'e' if you would like to exit to the main menu or press any other key to go over the terms again: ");
				// check if user would like to continue
				if (scan.nextLine().trim().toLowerCase().equals("e")) {
					System.out.print("\nGoing back to the main menu");
					// UI for exiting
					try {
						int i = 3;
						while (i > 0) {
							Thread.sleep(1000);
							System.out.print(".");
							i--;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(""); // UI linespace
					return;
				}
				// rerun quiz if user wants to
				else {
					System.out.println(""); // UI linespace
					quizHelper(); // self method call
				}
			}
		}
	}

	/**
	 * Recursive helper method to print variables in pre-order fashion to be used by
	 * quiz interface
	 * 
	 * @param node node of the tree from where we'll start traversing
	 * @author aneesh, Arjun Iyer
	 */
	private void preOrder(RedBlackTree.Node<WordNode> node) {
		// check if node is null
		if (node == null) {
			return;
		}

		System.out.println("Define: " + node.data.getWord()); // print word

		// give time for user to come up with their solution before presenting answer
		try {
			int i = 5;
			System.out.print("Time Left:  ");
			System.out.print("10.. ");
			Thread.sleep(500); // millis variable (500 for testing)
			while (i >= 0) {
				Thread.sleep(100);
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println(); // UI linespacing
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(node.data.getDefinition() + "\n"); // print definition
		// give x seconds of time before presenting new word
		try {
			int i = 5;
			System.out.print("Moving to next term in:  ");
			while (i >= 0) {
				Thread.sleep(100); // variable time(millis)
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		preOrder(node.leftChild); // recurse on left tree
		preOrder(node.rightChild); // recruse on right tree

	}

	/**
	 * Recursive helper method to print variables in in-order fashion to be used by
	 * quiz interface
	 * 
	 * @param node node of the tree from where we'll start traversing *
	 * @author aneesh, Arjun Iyer
	 */
	private void inOrder(RedBlackTree.Node<WordNode> node) {
		// check if node is null
		if (node == null) {
			return;
		}

		inOrder(node.leftChild); // recurse on left node
		System.out.println("Define: " + node.data.getWord()); // print word
		// give time for user to come up with their solution before presenting answer
		try {
			int i = 5;
			System.out.print("Time Left:  ");
			System.out.print("10.. ");
			Thread.sleep(500);
			while (i >= 0) {
				Thread.sleep(100);
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Answer: " + node.data.getDefinition() + "\n");

		// time before presenting the next term
		try {
			int i = 5;
			System.out.print("Moving to next term in:  ");
			while (i >= 0) {
				Thread.sleep(100);
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		inOrder(node.rightChild); // recurse on right tree
	}

	/**
	 * Recursive helper method to print variables in post-order fashion to be used
	 * by quiz interface
	 * 
	 * @param node node of the tree from where we'll start traversing
	 * @author aneesh, Arjun Iyer
	 */
	private void postOrder(RedBlackTree.Node<WordNode> node) {
		// enter if node is not null
		if (node != null) {
			postOrder(node.leftChild); // recurse on left child
			postOrder(node.rightChild); // recurse on right child
			System.out.println("Define: " + node.data.getWord()); // print word

			try {
				int i = 5;
				System.out.print("Time Left:  ");
				System.out.print("10.. ");
				Thread.sleep(500);
				while (i >= 0) {
					Thread.sleep(100);
					System.out.print(i + ".. ");
					i--;
				}
				System.out.println();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Answer: " + node.data.getDefinition() + "\n");
			try {
				int i = 5;
				System.out.print("Moving to next term in:  ");
				while (i >= 0) {
					Thread.sleep(100);
					System.out.print(i + ".. ");
					i--;
				}
				System.out.println();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Method invoked when all terms need to be displayed
	 * 
	 * @author Arjun Iyer
	 */
	public void displayAllTermsHelper() {
		System.out.println("Total number of terms in this dictionary: " + loader.size + "\n");
		System.out.println(getAllTerms(rbt.root));
		if (getAllTerms(rbt.root).isBlank()) {
			System.out.println("The dictionary is empty, please add terms using the 'Add a new term' feature.");
			return;
		}
		boolean input = true;
		while (input) {
			System.out.println("Enter 'e' to return to the main menu");
			if (scan.nextLine().trim().toLowerCase().equals("e")) {
				System.out.print("\nGoing back to the main menu");
				try {
					int i = 3;
					while (i > 0) {
						Thread.sleep(1000);
						System.out.print(".");
						i--;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("");
				input = false;
			}
		}
	}

	/**
	 * Helper method that retusns the whole tree (converted to a String type)
	 * traversed in an inOrder traversal. Returned value includes word, definition,
	 * and module number
	 * 
	 * @param current "root" of the tree from where we'll start traversing
	 * @return str String that contains the entire tree's values
	 * 
	 * @author aneesh
	 */
	private String getAllTerms(RedBlackTree.Node<WordNode> current) {

		String treeString = ""; // initialize string to be returned

		// check if root node is null
		if (current == null) {
			return ""; // return empty string if tree is empty
		}

		// traverse through the tree in an inOrder fashion
		else {
			treeString += getAllTerms(current.leftChild); // recurse on left tree
			treeString += ("Word: " + current.data.getWord() + " (" + current.data.getModule() + ")\n" + "Definition: "
					+ current.data.getDefinition() + "\n\n"); // print values
			treeString += getAllTerms(current.rightChild); // recurse on right tree
		}
		return treeString;
	}

	/**
	 * Method to lookup a term's definition in the dictionary
	 * 
	 * @throws NoSuchElementException when word entered by user does not exist
	 * @author Arjun Iyer
	 */
	private void getTermDefHelper() {
		try {
			String term;
			System.out.println("Enter the term you would like to lookup:");
			term = scan.nextLine().trim().toLowerCase();
			System.out.println("Definition: " + getDef(term)); // print definition, if word does not exist throw
																// NoSuchElementException and enter catch block

			System.out.println(
					"Enter 'e' if you would like to exit to the main menu or press any other key to lookup another term: ");
			String inp = scan.nextLine();
			// go back to main menu if user enters 'e'
			if (inp.trim().toLowerCase().equals("e")) {
				System.out.print("\nGoing back to the main menu");
				try {
					int i = 3;
					while (i > 0) {
						Thread.sleep(1000);
						System.out.print(".");
						i--;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("");
				return;
			}
			// self method call if user wants to continue
			else {
				getTermDefHelper();
			}
		} catch (NoSuchElementException e) {
			System.out.println("Term does not exist in Dictionary, please try again:");
			System.out.println();
			getTermDefHelper(); // self method call to prompt a valid word
		}
	}

	/**
	 * Method to retrieve a definition of a term
	 * 
	 * @author Yuan Ling
	 * @throws NoSuchElementException when trying to get a definition of a term that
	 *                                doesn't exist
	 * @return definition of the string
	 */
	public String getDef(String term) throws NoSuchElementException {

		// if term does not exist
		if (!this.checkTerm(term)) {
			throw new NoSuchElementException("Term does not exist in dictionary.");
		}

		Node<WordNode> curr = rbt.root;
		String returnDef = "";

		while (curr != null) {

			if (curr.data.getWord().equalsIgnoreCase(term)) {
				returnDef = curr.data.getDefinition();
				break;
			}

			// if current node is larger than term being searched
			else if (curr.data.getWord().compareToIgnoreCase(term) > 0) {
				curr = curr.leftChild;
			}

			// if current node is smaller than term being searched
			else {
				curr = curr.rightChild;
			}

		}
		returnDef += "\n";
		return returnDef;
	}

	/**
	 * Method to add a new term to the dictionary
	 * 
	 * @author Yuan Ling, Arjun Iyer
	 * @throws NullPointerException when attempting to add null references
	 */
	private void addTermHelper() {
		String term;
		String definition;
		String module;
		String userInput = "";

		WordNode data;
		System.out.println("Enter the term you would like to add: ");
		term = scan.nextLine(); // check user input, print error message if entry already exists
		if (checkTerm(term)) {
			System.out.println("\nWARNING: term already exists in the dictionary.");
			boolean input = true; // boolean flag to control function flow (while loop)
			while (input) {
				System.out.println("Enter 'e' to return to the main menu");
				if (scan.nextLine().trim().toLowerCase().equals("e")) {
					System.out.print("\nGoing back to the main menu");
					try {
						int i = 3;
						while (i > 0) {
							Thread.sleep(1000);
							System.out.print(".");
							i--;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("");
					input = false;
				}
			}
			return;
		}
		System.out.println("Enter the definition of the term:");
		definition = scan.nextLine(); // store user input for definition
		System.out.println("Enter the module it is from:");
		module = scan.nextLine(); // store user input for module

		data = new WordNode(term, definition, module); // store the new WordNode in variable 'data'
		rbt.insert(data); // insert into the tree
		loader.size++; // increment the size of the dictionary
		System.out.println("Saving new term to dictionary..."); // UI
		loader.saveData(rbt); // call saveData to append to database (txt file)
		System.out.println("New term successfully added."); // print success message
		System.out.println();

		System.out.println(
				"Enter 'e' if you would like to exit to the main menu or enter any other value to add another term: ");

		// check userinput - if 'e', exit the function else contine the same
		userInput = scan.nextLine();
		if (userInput.trim().toLowerCase().equals("e"))
			return;
		else
			addTermHelper(); // self method call
	}

	/**
	 * Method to check if a term exists in the dictionary
	 * 
	 * @author Yuan Ling
	 * @return true if term exists, false otherwise
	 */
	public boolean checkTerm(String term) {

		Node<WordNode> curr = rbt.root;
		boolean returnBoo = false;

		// check if the WordNode node is not null
		while (curr != null) {

			if (curr.data.getWord().equalsIgnoreCase(term)) {
				returnBoo = true;
				break;
			}

			// if current node is larger than term being searched
			else if (curr.data.getWord().compareToIgnoreCase(term) > 0) {
				curr = curr.leftChild;
			}

			// if current node is smaller than term being searched
			else {
				curr = curr.rightChild;
			}

		}

		return returnBoo;
	}

	public static void main(String[] args) throws FileNotFoundException {
		CS400Dictionary dict = new CS400Dictionary();
		System.out.println("***********************************************************************************\n"
				+ "\n" + "************************    WELCOME TO CS400 DICTIONARY    ************************\n" + "\n"
				+ "***********************************************************************************" + "\n");
		System.out.println("Welcome to this interactive CS400 dictionary, study tool made to help you learn terms\n"
				+ "and their definitions. Let's begin!" + "\n");
		dict.userMenu();
		System.out.println("***********************************************************************************\n"
				+ "\n" + "*******************    THANK YOU FOR USING CS400 DICTIONARY!    *******************\n" + "\n"
				+ "***********************************************************************************" + "\n");
	}
}
