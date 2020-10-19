// --== CS400 File Header Information ==--
// Name: Arjun Iyer
// Email: iyer9@wisc.edu
// Team: GA
// TA: Daniel Kiel
// Lecturer: Florian Heimerl
// Notes to Grader: -

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * The main class that runs the User Interface for the CS400 Dictionary application
 *
 * @author Arjun Iyer
 *
 */
public class CS400DictionaryA extends RedBlackTree<WordNode> {

	private LoadFile loader;
	private Scanner scan;
	private RedBlackTree<WordNode> rbt;

	static String actionPrompt = "Select one of the following actions:\n"
			+ "  1.Add a new term                               [Enter 'a']\n"
			+ "  2.Get an existing term's definition            [Enter 'g']\n"
			+ "  3.Display all terms in the dictionary          [Enter 'd']\n"
			+ "  4.Quiz yourself on the terms                   [Enter 'q']\n"
			+ "  5.Exit the dictionary?                         [Enter 'e']";

	/**
	 * @author Arjun Iyer
	 * @throws FileNotFoundException
	 */
	public CS400DictionaryA() throws FileNotFoundException {

		loader = new LoadFile(new File("define.txt"));
		rbt = new RedBlackTree<WordNode>();
		loader.loadData(rbt);
		scan = new Scanner(System.in);

	}

	/**
	 * @author Arjun Iyer
	 */
	public void userMenu() {

		String userInput;
		boolean running = true;

		while (running) {

			userInput = "";
			scan = new Scanner(System.in);

			System.out.println();
			System.out.println(actionPrompt);
			System.out.println();
			System.out.print("--> ");
			userInput = scan.nextLine().trim().toLowerCase();

			switch (userInput) {

			case "a":
				addTermHelper();
				break;

			case "g":
				getTermDefHelper();
				break;

			case "d":
				displayAllTermsHelper();
				break;

			case "q":
				quizHelper();
				break;

			case "e":
				running = false;
				break;

			default:
				System.out.println("Input not recognized, please try again\n");
				break;

			}
		}
	}

	/**
	 * Starts a quiz
	 * 
	 * @author Arjun Iyer
	 */
	private void quizHelper() {

		scan = new Scanner(System.in);

		boolean run = true;
		Node<WordNode> current = rbt.root;

		while (run) {
			Random rand = new Random();
			int randNum = rand.nextInt(3);
			if (randNum == 0) {
				preOrder(current);
			} else if (randNum == 1) {
				inOrder(current);
			} else {
				postOrder(current);
			}

			run = false;

			System.out.println("No more terms left. The quiz has ended");
			System.out.println(
					"Enter 'e' if you would like to exit to the main menu or press any other key to go over the terms again: ");
			if (scan.nextLine().trim().toLowerCase().equals("e"))
				return;
			else
				quizHelper();
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
		if (node == null) {
			return;
		}

		System.out.println("Define: " + node.data.getWord());
		try {
			int i = 10;
			System.out.print("Time Left:  ");
			while (i >= 0) {
				Thread.sleep(1000);
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(node.data.getDef() + "\n");
		try {
			int i = 5;
			System.out.print("Moving to next term in:  ");
			while (i >= 0) {
				Thread.sleep(1000);
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		preOrder(node.leftChild);
		preOrder(node.rightChild);

	}

	/**
	 * Recursive helper method to print variables in in-order fashion to be used by
	 * quiz interface
	 * 
	 * @param node node of the tree from where we'll start traversing *
	 * @author aneesh, Arjun Iyer
	 */
	private void inOrder(RedBlackTree.Node<WordNode> node) {
		if (node == null) {
			return;
		}

		inOrder(node.leftChild);
		System.out.println("Define: " + node.data.getWord());
		try {
			int i = 10;
			System.out.print("Time Left:  ");
			while (i >= 0) {
				Thread.sleep(1000);
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Answer: " + node.data.getDef() + "\n");
		try {
			int i = 5;
			System.out.print("Moving to next term in:  ");
			while (i >= 0) {
				Thread.sleep(1000);
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		inOrder(node.rightChild);
	}

	/**
	 * Recursive helper method to print variables in post-order fashion to be used
	 * by quiz interface
	 * 
	 * @param node node of the tree from where we'll start traversing
	 * @author aneesh, Arjun Iyer
	 */
	private void postOrder(RedBlackTree.Node<WordNode> node) {
		if (node != null) {
			postOrder(node.leftChild);
			postOrder(node.rightChild);
			System.out.println("Define: " + node.data.getWord());
			try {
				int i = 10;
				System.out.print("Time Left:  ");
				while (i >= 0) {
					Thread.sleep(1000);
					System.out.print(i + ".. ");
					i--;
				}
				System.out.println();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Answer: " + node.data.getDef() + "\n");
			try {
				int i = 5;
				System.out.print("Moving to next term in:  ");
				while (i >= 0) {
					Thread.sleep(1000);
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
	 * Displays all the terms in the Dictionary
	 * 
	 * @author Arjun Iyer
	 */
	private void displayAllTermsHelper() {

		System.out.println(getAllTerms(rbt.root));
		if(getAllTerms(rbt.root).isBlank()) {
			System.out.println("The dictionary is empty, please add terms using the 'Add a new term' feature.");
			return;
		}
		try {
			int i = 15;
			System.out.print("Returning to Main menu in :  ");
			while (i >= 0) {
				Thread.sleep(1000);
				System.out.print(i + ".. ");
				i--;
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
			treeString += (current.data.getWord() + ": " + current.data.getDef() + " " + "(Module: "
					+ current.data.getModule() + ")" + "\n"); // print values
			treeString += getAllTerms(current.rightChild); // recurse on right tree
		}
		return treeString;
	}

	/**
	 * Method to lookup a term's definition in the dictionary
	 * 
	 * @author Arjun Iyer
	 */
	private void getTermDefHelper() {

		try {
			String term;
			scan = new Scanner(System.in);

			System.out.println("Enter the term you would like to lookup:");
			term = scan.nextLine();

			System.out.println(term + " : " + getDef(term));

			System.out.println(
					"Enter 'e' if you would like to exit to the main menu or press any other key to lookup another term: ");
			String inp = scan.next();
			if (inp.equals("e"))
				return;
			else
				getTermDefHelper();

		} catch (NoSuchElementException e) {
			System.out.println("Term does not exist in Dictionary, please try again:");
			System.out.println();
			getTermDefHelper();

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
				returnDef = curr.data.getDef();
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
		scan = new Scanner(System.in);

		WordNode data;

		System.out.println("Enter the term you would like to add:");
		term = scan.nextLine();
		if (checkTerm(term)) {
			System.out.println("!!WARNING!!*Term already exists in the dictionary. Going back to the main menu...");
			System.out.println();
			return;
		}

		System.out.println("Enter the definition of the term:");
		definition = scan.nextLine();
		System.out.println("Enter the module it is from:");
		module = scan.nextLine();

		data = new WordNode(term, definition, module);
		rbt.insert(data);

		System.out.println("Saving new term to dictionary...");
		// TODO: save the new term to define.txt
		System.out.println("New term successfully added.");
		System.out.println();

		System.out.println(
				"Enter 'e' if you would like to exit to the main menu or enter any other value to add another term: ");

		userInput = scan.next();
		if (userInput.equals("e"))
			return;
		else
			addTermHelper();

	}

	/**
	 * Method to check if a term exists in the dictionary
	 * 
	 * @author Yuan Ling
	 * @return true if term exists, false otherwise
	 */
	public boolean checkTerm(String term) {

		// String listOfTerms = rbt.toString().replace('[', ' ').replace(']', '
		// ').replaceAll("\\s", "");
		Node<WordNode> curr = rbt.root;
		boolean returnBoo = false;

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
		CS400DictionaryA dict = new CS400DictionaryA();
		System.out.println("***********************************************************************************\n"
				+ "\n" + "***************************    WELCOME TO CS400 DICTIONARY    ***************************\n" + "\n"
				+ "***********************************************************************************" + "\n");
		System.out.println("Welcome to this interactive CS400 dictionary, study tool made to help you learn terms\n"
				+ "and their definitions. Let's begin!" + "\n");
		dict.userMenu();
		System.out.println("Thank you for using this interactive dictionary!");
	}
}
