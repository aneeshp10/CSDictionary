import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CS400Dictionary {
	private LoadFile loader;
	private static RedBlackTree<WordNode> rbTree;
	private Scanner scan;
	private HashMap<String, String> wordsDefine;
	private boolean running = true;
	protected int size;

	public CS400Dictionary() throws FileNotFoundException {
		loader = new LoadFile(new File("define.txt"));
		wordsDefine = new HashMap<String, String>();
		rbTree = new RedBlackTree<WordNode>();
		loader.loadData(rbTree);
		scan = new Scanner(System.in);

	}

	public void userInterface() {
		System.out.println("Welcome to the CS400 Dictionary. Press the enter key to begin.");
		while (running) {
			String input = scan.nextLine();
			System.out.println("Menu");
			System.out.println("Press 'a' to lookup a word");
			System.out.println("Press 'b' to list all words and definitions");
			System.out.println("Press 'c' to start practice tool");
			if (input.equals("a")) {
				boolean retry = true;
				System.out.println("What word do you want to search? ");
				String input2 = scan.nextLine();
				lookup(input2).display();
				while (retry) {

				}
			} else if (input.equals("b")) {
				// System.out.println(rbt.toString());
			}
		}
	}

	/*
	 * Adds a new term to the dictionary (RBTree)
	 * 
	 * @param newWord: user-defined object consisting of term, definition, module
	 * number
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
		System.out.println("Press any key to start, press 'q' to exit");
		String userIn = scan.next();
		boolean run = true;

		if (userIn.equalsIgnoreCase("q")) {
			run = false;
		}

		while (run) {
			String str = ""; // initialize an empty string
			if (current == null) {
				return ""; // return empty string if tree is empty
			}

			else {
				// perform in-order traversal starting at root
				str += getAllTerms(current.leftChild); // recursive call descends into left subtree
				str += (current.data.getWord() + ": " + current.data.getDefinition() + " " + "("
						+ current.data.getModule() + ")" + "\n"); // current is printed in
				// the given form once
				// left is done
				str += getAllTerms(current.rightChild); // recursively descend into right tree
			}
			// return final string
		}
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

	public static void main(String[] args) throws FileNotFoundException {
		CS400Dictionary dict = new CS400Dictionary();
		System.out.println(dict.size);
		// dict.userInterface();
		dict.quizInterface(rbTree.root);
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
