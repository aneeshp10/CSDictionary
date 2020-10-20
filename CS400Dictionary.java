// --== CS400 File Header Information ==--
// Name: <the name of the team member who wrote the code in this file>
// Email: <the team member's @wisc.edu email address>
// Team: <the team name: two letters>
// Role: <the team member's role in your team>
// TA: <name of the team's ta>
// Lecturer: <name of the team mate's lecturer>
// Notes to Grader: <optional extra notes>


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Random;

/*
 * Models a CS400 terms and definitions dictionary
 * 
 * @author aneesh
 */

public class CS400Dictionary {
	private LoadFile loader;
	private static RedBlackTree<WordNode> rbTree;
	private Scanner scan;
	protected int size;

	static int allowedWidth = "***********************************************************************************"
			.length();
	static String welcomeHeader = "***********************************************************************************\n"
			+ "\n" + "***************************    WELCOME TO DICTIONARY    ***************************\n" + "\n"
			+ "***********************************************************************************";
	static String welcomeMessage = "Welcome to this interactive dictionary, study tool made to help you learn new words\n"
			+ "and their meanings. Let's begin!";
	static String actionPrompt = "Would you like to:\n" + "  Add a new term               (Press 'a')\n"
			+ "  Get an existing term         (Press 'g')\n" + "  Display all terms            (Press 'd')\n"
			+ "  Quiz yourself on the terms   (Press 'q'}\n" + "  Or exit the dictionary?      (Press 'e')\n";
	static String userInput;

	public CS400Dictionary() throws FileNotFoundException {
		loader = new LoadFile(new File("define.txt"));
		rbTree = new RedBlackTree<WordNode>();
		loader.loadData(rbTree);
		scan = new Scanner(System.in);

	}

	/**
	 * Models user interface for main dictionary application
	 * 
	 * @throws NoSuchElementException, IllegalArgumentException
	 * 
	 * @author _____, aneesh
	 */
	public void userInterface() {
		System.out.println(welcomeHeader + "\n");
		System.out.println(welcomeMessage + "\n");

		userInput = "";
		while (!userInput.equals("e")) {
			System.out.println(actionPrompt);
			userInput = scan.nextLine().trim().toLowerCase();

			switch (userInput) {
			case "a":
				System.out.println("Enter the new word/term you want to add in your dictionary");
				String in = scan.nextLine();
				System.out.println("Enter the definition for the word: ");
				String def = scan.nextLine();
				System.out.println("Lastly, the module/week the concept was introduced in lecture: ");
				String mod = scan.nextLine();
				addWord(new WordNode(in, def, mod));
				System.out.println("\n" + "Successfully added " + "\"" + in + "\"" + " to the dictionary!" + "\n");
				break;

			case "g":
				System.out.println("Enter a word you want to look up: ");
				String userIn = scan.nextLine();
				System.out.println("Fetching definition of \"" + lookup(userIn).getWord() + "\"");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println(
						"Definition of \"" + lookup(userIn).getWord() + "\": " + lookup(userIn).getDefinition() + "\n");
				break;

			case "d":
				System.out.println("Fetching all words and definitions ..." + "\n");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println(getAllTerms(rbTree.root));
				break;

			case "q":
				System.out.println("Entering CS400 Quiz Interface ...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				quizInterface(rbTree.root);
				break;

			case "e":
				exitInterface();
				break;

			default:
				System.out.println("Input not recognized, please try again\n");
				break;
			}

		}

	}

	/**
	 * Adds a new term to the dictionary (RBTree)
	 * 
	 * 
	 * @param newWord user-defined object consisting of term, definition, module
	 *                number
	 * @throws IllegalArgumentException if the entered object is null
	 * 
	 * @author aneesh
	 */
	public void addWord(WordNode newWord) {

		// check if newWord is null, throw exception if true
		if (newWord == null) {
			throw new IllegalArgumentException();
		}

		// add the user-defined object 'newWord' into the RedBlackTree
		rbTree.insert(new WordNode(newWord.getWord(), newWord.getDefinition(), newWord.getModule()));
		size++; // ?

		// block that enters the newWord's contents into the txt file holding all words,
		// definitions, and module numbers
		try (FileWriter f = new FileWriter("define.txt", true);
				BufferedWriter b = new BufferedWriter(f);
				PrintWriter p = new PrintWriter(b);) {
			p.println("");
			p.println("Word: " + newWord.getWord());
			p.println("Define: " + newWord.getDefinition());
			p.println("Module: " + newWord.getModule());
			p.println("stop: ");
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	/**
	 * Invoked when user wants to search the definition of a certain word
	 * 
	 * 
	 * @param findWord String user input - word that needs to be searched
	 * @throws NoSuchElementException if the entered word does not exist in the
	 *                                dictionary
	 * 
	 * @author aneesh
	 */
	public WordNode lookup(String findWord) {
		WordNode word = new WordNode(findWord, " ", " ");
		return lookupHelper(word, rbTree.root);

	}

	/**
	 * Recursive helper method to lookup a definition given a reference WordNode
	 * with the same word
	 * 
	 * @param word    a reference to a WorNode target we are lookup for a match in
	 *                the RBT rooted at root.
	 * @param current "root" of the subtree we are looking for a match to word
	 *                within it.
	 * @return reference to the WordNode stored stored in this RBT which matches the
	 *         desired word.
	 * @throws NoSuchElementException with a descriptive error message if there is
	 *                                no term that matches the entered
	 * 
	 * @author aneesh
	 * 
	 */
	private WordNode lookupHelper(WordNode word, RedBlackTree.Node<WordNode> root) {

		// ensure root is not null to continue block
		while (root != null) {

			// check if the current node's data (WordNode) is the same as the one we're
			// searching for
			if (root.data.compareTo(word) == 0) {
				return root.data;
			}
			// if the returned compareTo value is positive, recurse on left child
			else if (root.data.compareTo(word) > 0) {
				return lookupHelper(word, root.leftChild);
			}
			// if the returned compareTo value is negative, recurse on right child
			else if (root.data.compareTo(word) < 0) {
				return lookupHelper(word, root.rightChild);
			}
		}

		// throw NoSuchElementException if found word does not exist
		throw new NoSuchElementException("This term does not exist");
	}

	/**
	 * Models the interface of the CS400 Dictionary Quiz
	 * 
	 * @param current "root" of the tree the caller method will be passing
	 * @return
	 * 
	 * @author aneesh
	 */
	public String quizInterface(RedBlackTree.Node<WordNode> current) {
		System.out.println("****************************Welcome to the CS400 Quiz!*****************************");
		System.out
				.println("****Assess yourself on important CS400 definitions to help you revise on exams!****" + "\n");
		System.out.println("Press any key to start, press 'q' to exit");
		String userIn = scan.next(); // storing user's input
		boolean run = true; // boolean variable to control running of quiz interface

		// if q quit the application
		if (userIn.equalsIgnoreCase("q")) {
			run = false;
		}

		// quiz application while loop
		while (run) {
			// run the quiz in a shuffled format using a random number generator - shuffle
			// adds on to better revising and avoids monotony
			Random rand = new Random();
			int randNum = rand.nextInt(3); // generate a random number between 0,1,2
			// if 0, run the quiz in preOrder traversal, if 1, run quiz in inOrder
			// traversal, if 2, run quiz in postOrder traversal
			if (randNum == 0) {
				preOrder(current);
			} else if (randNum == 1) {
				inOrder(current);
			} else {
				postOrder(current);
			}

			run = false; // set boolean var to false

			System.out.println("Press any key to go over the terms again, press 'q' to quit");
			String str = scan.next();
			// System.out.println("THe word: " + str);
			// check if user entered q, if true end application, else continue quiz
			if (!str.equalsIgnoreCase("q")) {
				run = true;
			}

		}

		// exit message after end of quiz
		System.out.println("");
		System.out.println("Thank you for using the CS 400 Quiz! How many did you get right?");

		return "";
	}

	/**
	 * Return the whole tree (converted to a String type) traversed in an inOrder
	 * traversal. Returned value includes word, definition, and module number
	 * 
	 * @param current "root" of the tree from where we'll start traversing
	 * @return str String that contains the entire tree's values
	 * 
	 * @author aneesh
	 */
	public String getAllTerms(RedBlackTree.Node<WordNode> current) {
		String treeString = ""; // initialize string to be returned

		// check if root node is null
		if (current == null) {
			return ""; // return empty string if tree is empty
		}

		// traverse through the tree in an inOrder fashion
		else {
			treeString += getAllTerms(current.leftChild); // recurse on left tree
			treeString += (current.data.getWord() + ": " + current.data.getDefinition() + " " + "("
					+ current.data.getModule() + ")" + "\n"); // print values
			treeString += getAllTerms(current.rightChild); // recurse on right tree
		}
		return treeString;
	}

	/**
	 * UI to display when user exits the main dictionary application
	 * 
	 * @author aneesh
	 */
	public void exitInterface() {
		System.out
				.println("***********************************************************************************" + "\n");
		System.out.print("			      Exiting Dictionary");

		// UI details
		for (int i = 0; i < 3; i++) {

			try {
				Thread.sleep(750);
				System.out.print(". ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("");
		System.out
				.println("\n" + "*************************Thanks for using CS400 Dictionary!*************************");
	}

	/**
	 * Recursive helper method to print variables in in-order fashion to be used by
	 * quiz interface
	 * 
	 * @param node node of the tree from where we'll start traversing *
	 * @author aneesh
	 */
	private void inOrder(RedBlackTree.Node<WordNode> node) {
		// check if node is null
		if (node == null) {
			return;
		}

		// recurse on left tree
		inOrder(node.leftChild);
		System.out.println("Define: " + node.data.getWord()); // print out the word

		// give time for user to come up with their solution before presenting answer
		try {
			Thread.sleep(100); // millis value to be waited, variable
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Answer: " + node.data.getDefinition() + "\n"); // print out the definition
		try {
			Thread.sleep(100);
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
	 * @author aneesh
	 */
	private void postOrder(RedBlackTree.Node<WordNode> node) {
		// check if node is null
		if (node != null) {
			postOrder(node.leftChild); // recurse on left tree
			postOrder(node.rightChild); // recurse on right tree
			System.out.println(node.data.getWord()); // print out word

			// give time for user to come up with their solution before presenting answer
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(node.data.getDefinition() + "\n"); // print out definition
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Recursive helper method to print variables in pre-order fashion to be used by
	 * quiz interface
	 * 
	 * @param node node of the tree from where we'll start traversing
	 * @author aneesh
	 */
	private void preOrder(RedBlackTree.Node<WordNode> node) {
		// check if node is null
		if (node == null) {
			return;
		}

		System.out.println(node.data.getWord()); // print word
		// give time for user to come up with their solution before presenting answer
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(node.data.getDefinition() + "\n"); // print definition
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		preOrder(node.leftChild); // recurse on left tree
		preOrder(node.rightChild); // recurse on right tree

	}

	/**
	 * This method resizes long definitions/phrases so that they are the same width
	 * as the screen
	 * 
	 * @param inputPhrase the definition that needs to be fitted to the screen width
	 * @author Jacob
	 */
	@SuppressWarnings("unused")
	private static String resize(String inputPhrase) {
		String fittedSubstring;
		String remainingSubstring;
		// boolean leadingSpace = false;

		if (inputPhrase.length() > 83) {
			if (inputPhrase.charAt(83) == ' ') {
				fittedSubstring = inputPhrase.substring(0, 83) + "\n";
				remainingSubstring = inputPhrase.substring(83, inputPhrase.length());
			} else {
				fittedSubstring = inputPhrase.substring(0, 83 - 1) + "-\n";
				remainingSubstring = inputPhrase.substring(83 - 1, inputPhrase.length());
			}
			return fittedSubstring + resize(remainingSubstring);
		}

		return inputPhrase;
	}

	public static void main(String[] args) throws FileNotFoundException {
		CS400Dictionary dict = new CS400Dictionary();
		// System.out.println(dict.size);
		dict.userInterface();
		// dict.quizInterface(rbTree.root);
//		System.out.println("PRE******************************");
//		dict.preOrder(rbTree.root);
//		System.out.println("IN******************************");
//		dict.inOrder(rbTree.root);
//		System.out.println("POST******************************");
//		dict.postOrder(rbTree.root);

		// dict.getAllTerms(rbTree.root);
		// System.out.println(dict.getAllTerms(rbTree.root));
		/*
		 * WordNode word = new WordNode("CS", "Computer Science", "1.4"); WordNode word2
		 * = new WordNode("DS", "Computer Science", "1.4"); WordNode word3 = new
		 * WordNode("BS", "Computer Science", "1.4");
		 * 
		 * dict.addWord(word); dict.addWord(word2); dict.addWord(word3);
		 * 
		 * // System.out.println(rbTree.root.leftChild.data.getWord());
		 * 
		 * WordNode obj = dict.lookup("bash"); System.out.println(obj.getDefinition());
		 */

		// System.out.println(dict.getAllTerms(rbTree.root));
	}

}
