import java.io.File;
import java.io.FileNotFoundException;
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
	private boolean running = true;
	protected int size;

	public CS400Dictionary() throws FileNotFoundException {
		loader = new LoadFile(new File("define.txt"));
		rbTree = new RedBlackTree<WordNode>();
		loader.loadData(rbTree);
		scan = new Scanner(System.in);

	}

	public void userInterface() {
		System.out.println("Welcome to the CS400 Dictionary. Press the enter key to begin.");
		while (running) {
			String input = scan.nextLine();
			System.out.println("*********************Menu*******************************");
			System.out.println("Press 'a' to lookup a word");
			System.out.println("Press 'b' to list all words and definitions");
			System.out.println("Press 'c' to start practice tool");
			System.out.println("Press 'd' to add a new term and definition to the dictionary");
			System.out.println("Press 'q' to exit");
			if (input.equals("a")) {
				boolean retry = true;
				System.out.println("What word do you want to search? ");
				String input2 = scan.nextLine();
				lookup(input2).display();
				while (retry) {

				}
			} else if (input.equals("b")) {
				getAllTerms(rbTree.root);
			} else if (input.equals("c")) {
				quizInterface(rbTree.root);
			} else if (input.equals("d")) {
				System.out.println("Enter the new word/term you want to add in your dictionary");
				String in = scan.nextLine();
				System.out.println("Enter the definition for the word: ");
				String def = scan.nextLine();
				System.out.println("Lastly, the module/week the concept was introduced in lecture: ");
				String mod = scan.nextLine();
				addWord(new WordNode(in, def, mod));
			}
			else if (input.equals("q")) {
				exitInterface();
				break;
			}
		}
	}

	/*
	 * Adds a new term to the dictionary (RBTree)
	 * 
	 * 
	 * @param newWord: user-defined object consisting of term, definition, module
	 * number
	 * 
	 * @author aneesh
	 */
	public void addWord(WordNode newWord) {

		if (newWord == null) {
			throw new IllegalArgumentException();
		}

		rbTree.insert(new WordNode(newWord.getWord(), newWord.getDefinition(), newWord.getModule()));
		size++;
	}

	public WordNode lookup(String findWord) {
		WordNode word = new WordNode(findWord, " ", " ");
		return lookupHelper(word, rbTree.root);

	}

	private WordNode lookupHelper(WordNode word, RedBlackTree.Node<WordNode> root) {

		while (root != null) {
			if (root.data.compareTo(word) == 0) {
				return root.data;
			} else if (root.data.compareTo(word) > 0) {
				return lookupHelper(word, root.leftChild);
			} else if (root.data.compareTo(word) < 0) {
				return lookupHelper(word, root.rightChild);
			}
		}

		throw new NoSuchElementException("This term does not exist");
	}

	public String quizInterface(RedBlackTree.Node<WordNode> current) {
		System.out.println(
				"****************************Welcome to the CS400 Quiz!*************************************************************");
		System.out.println(
				"***************Assess yourself on important CS400 definitions to help you revise on exams and concepts!*************");
		System.out.println("Press any key to start, press 'q' to exit");
		String userIn = scan.next();
		boolean run = true;

		if (userIn.equalsIgnoreCase("q")) {
			run = false;
		}

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

			System.out.println("Press any key to go over the terms again, press 'q' to quit");
			String str = scan.next();
			// System.out.println("THe word: " + str);
			if (!str.equalsIgnoreCase("q")) {
				run = true;
			}

		}

		System.out.println("");
		System.out.println("Thank you for using the CS 400 Quiz! How many did you get right?");

		return "";
	}

	public String getAllTerms(RedBlackTree.Node<WordNode> current) {
		String str = ""; // initialize an empty string
		if (current == null) {
			return ""; // return empty string if tree is empty
		}

		else {
			// perform in-order traversal starting at root
			str += getAllTerms(current.leftChild); // recursive call descends into left subtree
			str += (current.data.getWord() + ": " + current.data.getDefinition() + " " + "(" + current.data.getModule()
					+ ")" + "\n"); // current is printed in
			// the given form once
			// left is done
			str += getAllTerms(current.rightChild); // recursively descend into right tree
		}
		return str; // return final string
	}

	public void exitInterface() {
		System.out.println("Exiting Dictionary");

		for (int i = 0; i < 3; i++) {

			try {
				Thread.sleep(750);
				System.out.println(". ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void inOrder(RedBlackTree.Node<WordNode> node) {
		if (node == null) {
			return;
		}

		inOrder(node.leftChild);
		System.out.println("Define: " + node.data.getWord());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Answer: " + node.data.getDefinition() + "\n");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		inOrder(node.rightChild);
	}

	private void postOrder(RedBlackTree.Node<WordNode> node) {
		if (node != null) {
			postOrder(node.leftChild);
			postOrder(node.rightChild);
			System.out.println(node.data.getWord());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(node.data.getDefinition() + "\n");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void preOrder(RedBlackTree.Node<WordNode> node) {
		if (node == null) {
			return;
		}

		System.out.println(node.data.getWord());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(node.data.getDefinition() + "\n");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		preOrder(node.leftChild);
		preOrder(node.rightChild);

	}

	public static void main(String[] args) throws FileNotFoundException {
		CS400Dictionary dict = new CS400Dictionary();
		//System.out.println(dict.size);
		dict.userInterface();
		//dict.quizInterface(rbTree.root);
//		System.out.println("PRE******************************");
//		dict.preOrder(rbTree.root);
//		System.out.println("IN******************************");
//		dict.inOrder(rbTree.root);
//		System.out.println("POST******************************");
//		dict.postOrder(rbTree.root);

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
