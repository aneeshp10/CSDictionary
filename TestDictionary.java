import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;

/**
 * This class implements the test methods for the CS400Dictionary application
 * 
 * @author Tavish
 *
 */
public class TestDictionary {

  /**
   * This method performs a level order traversal of the tree rooted at the current node. The string
   * representations of each data value and its color (red or black) within this tree are assembled
   * into a comma separated string within brackets (similar to many implementations of
   * java.util.Collection). An example of the output would be [a: black, b: red, c: red]
   * 
   * @return string containing the values and its colors of this tree in level order
   */
  private static String displayNodeAndColor(RedBlackTree<WordNode> rbt) {
    String output = "[";
    LinkedList<RedBlackTree.Node<WordNode>> q = new LinkedList<>();
    q.add(rbt.root);
    while (!q.isEmpty()) {
      RedBlackTree.Node<WordNode> next = q.removeFirst();
      if (next.leftChild != null)
        q.add(next.leftChild);
      if (next.rightChild != null)
        q.add(next.rightChild);
      output += next.data.getWord() + ": " + (next.isBlack ? "black" : "red");
      if (!q.isEmpty())
        output += ", ";
    }
    return output + "]";
  }

  @Test
  /**
   * This test method checks if the CS400Dictionary constructor is initialized correctly, and all
   * the words stored in the Red Black Tree in the right order
   * 
   */
  public void dictionaryInitTest() {
    try {
      // Initialize the dictionary and store the Red Black Tree in rbtInit
      CS400Dictionary dictionary = new CS400Dictionary();
      RedBlackTree<WordNode> rbtInit = dictionary.rbt;
      // expected output of level order of rbtInit
      String expectedOutput = "[cs: black, bash: black, load factor: red, binary search tree: red, "
          + "hash function: black, ssh: black, emacs: red, junit: red, make: red, vim: red]";
      // if expected output matches actual output, test pass otherwise fail.
      assertEquals(expectedOutput, displayNodeAndColor(rbtInit));
    } catch (FileNotFoundException e) {
      // dictionary not found, test failed
      e.printStackTrace();
      fail("Dictionary not found, test failed");
    }
  }

  @Test
  /**
   * This method checks if the add function of the dictionary works as expected, and it checks if
   * the program flow is correct when giving a valid or invalid input
   * 
   */
  public void dictionaryAddTest() {
    try {
      // Initialize the dictionary and store the Red Black Tree in rbtInit
      // Initialize the file database.txt
      LoadFile file = new LoadFile(new File("database.txt"));
      RedBlackTree<WordNode> rbtInit = new RedBlackTree<WordNode>();
      // load the words to rbtInit
      file.loadData(rbtInit);
      // Commands that add a new term to the dictionary and continues with standard flow of program
      String userInput1 = "a" + System.getProperty("line.separator") + "regular expression"
          + System.getProperty("line.separator")
          + "A regular expression is a sequence of characters that define a search pattern."
          + System.getProperty("line.separator") + "Module 7" + System.getProperty("line.separator")
          + "e" + System.getProperty("line.separator") + "e" + System.getProperty("line.separator");

      System.setIn(new ByteArrayInputStream(userInput1.getBytes()));

      try {
        // run the application and store the updated Red Black Tree in rbt
        CS400Dictionary dictionary = new CS400Dictionary();
        dictionary.userMenu();
        RedBlackTree<WordNode> rbt = dictionary.rbt;
        // expected output of level order of rbtInit
        String expectedOutput =
            "[load factor: black, cs: red, ssh: red, bash: black, hash function: black, "
                + "make: black, vim: black, binary search tree: red, emacs: red, "
                + "junit: red, regular expression: red]";
        // if expected output matches actual output, test pass otherwise fail.
        assertEquals(expectedOutput, displayNodeAndColor(rbt));
      } catch (Exception e) {
        e.printStackTrace();
        fail("The add term function doesn't work correctly");
      }

      // Commands that try adding an existng term and then adding new term to the dictionary and
      // continues with standard flow of program
      String userInput2 = "a" + System.getProperty("line.separator") + "vim"
          + System.getProperty("line.separator") + "e" + System.getProperty("line.separator") + "a"
          + System.getProperty("line.separator") + "anonymous class"
          + System.getProperty("line.separator")
          + "anonymous classes are inner classes with no name."
          + System.getProperty("line.separator") + "Module 5" + System.getProperty("line.separator")
          + "e" + System.getProperty("line.separator") + "e" + System.getProperty("line.separator");

      System.setIn(new ByteArrayInputStream(userInput2.getBytes()));

      try {
        // run the application and store the update Red Black Tree in rbt
        CS400Dictionary dictionary = new CS400Dictionary();
        dictionary.userMenu();
        RedBlackTree<WordNode> rbt = dictionary.rbt;
        String expectedOutput =
            "[load factor: black, cs: red, ssh: red, bash: black, hash function: black, "
                + "make: black, vim: black, anonymous class: red, binary search tree: red, "
                + "emacs: red, junit: red, regular expression: red]";
        // if expected output matches actual output, test pass otherwise fail.
        assertEquals(expectedOutput, displayNodeAndColor(rbt));
      } catch (Exception e) {
        e.printStackTrace();
        fail("The add term function doesn't work correctly");
      }

      file.saveData(rbtInit);
    } catch (FileNotFoundException e) {
      // dictionary not found, test failed
      e.printStackTrace();
      fail("Dictionary not found, test failed");
    }
  }

  @Test
  /**
   * This method checks if the get method of the dictionary works as expected, and it checks if the
   * program flow is correct when giving a valid or invalid input
   * 
   */
  public void dictionaryGetTest() {
    // Commands that get an existng term and definiton,
    // continues with standard flow of program
    String userInput1 = "g" + System.getProperty("line.separator") + "ssh"
        + System.getProperty("line.separator") + "e" + System.getProperty("line.separator") + "e"
        + System.getProperty("line.separator");

    System.setIn(new ByteArrayInputStream(userInput1.getBytes()));

    try {
      // run the application and check program flow
      CS400Dictionary dictionary = new CS400Dictionary();
      dictionary.userMenu();
    } catch (Exception e) {
      e.printStackTrace();
      fail("The add term function doesn't work correctly");
    }

    // Commands that tries getting an non existng term and then an existing term,
    // continues with standard flow of program
    String userInput2 = "g" + System.getProperty("line.separator") + "chroma"
        + System.getProperty("line.separator") + "emacs" + System.getProperty("line.separator")
        + "f" + System.getProperty("line.separator") + "bash" + System.getProperty("line.separator")
        + "e" + System.getProperty("line.separator") + "e" + System.getProperty("line.separator");

    System.setIn(new ByteArrayInputStream(userInput2.getBytes()));

    try {
      // run the application and check program flow
      CS400Dictionary dictionary = new CS400Dictionary();
      dictionary.userMenu();
    } catch (Exception e) {
      e.printStackTrace();
      fail("The add term function doesn't work correctly");
    }
  }

  @Test
  /**
   * This method checks if all the terms and definitons in the dictionary are displayed correctly,
   * and it checks if the program flow is correct.
   * 
   */
  public void dictionaryDisplayInterfaceTest() {
    // Commands that displays all the terms and definitions,
    // continues with standard flow of program
    String userInput = "d" + System.getProperty("line.separator") + "e"
        + System.getProperty("line.separator") + "e" + System.getProperty("line.separator");

    System.setIn(new ByteArrayInputStream(userInput.getBytes()));

    try {
      // run the application and check program flow
      CS400Dictionary dictionary = new CS400Dictionary();
      dictionary.userMenu();
    } catch (Exception e) {
      e.printStackTrace();
      fail("The display terms interface interface is incorrect");
    }
  }

  @Test
  /**
   * This method checks if quiz interface of the dictionary is displayed correctly, and it checks if
   * the program flow is correct.
   * 
   */
  public void dictionaryQuizInterfaceTest() {
    // Commands that displays the quiz interface,
    // continues with standard flow of program
    String userInput = "q" + System.getProperty("line.separator") + "s"
        + System.getProperty("line.separator") + "e" + System.getProperty("line.separator") + "e"
        + System.getProperty("line.separator");

    System.setIn(new ByteArrayInputStream(userInput.getBytes()));
    try {
      // run the application and check program flow
      CS400Dictionary dictionary = new CS400Dictionary();
      dictionary.userMenu();
    } catch (Exception e) {
      e.printStackTrace();
      fail("The display interface interface is incorrect");
    }
  }

  @Test
  /**
   * This method checks if terms and definition are loaded and saved in the dictionary database
   * correctly, and checks if the words stored in the Red Black Tree are in the right order
   * 
   */
  public void FileLoaderAndSaverTester() {
    // load a new test file and create a new Red Black Tree initTree
    LoadFile loader = new LoadFile(new File("LoadFileTester.txt"));
    RedBlackTree<WordNode> initTree = new RedBlackTree<>();
    // store words in initTree
    for (int i = 0; i < 8; i++) {
      initTree.insert(new WordNode(Integer.toString(i), "", ""));
    }
    // save the data from initTree to the test file
    loader.saveData(initTree);

    try {
      // create a new loadTree and load the data from the test file to loadTree
      RedBlackTree<WordNode> loadTree = new RedBlackTree<>();
      loader.loadData(loadTree);
      String expected = "[3: black, 1: black, 5: red, 0: red, 2: red, 4: black, 6: black, 7: red]";
      // if expected output matches actual output, test pass otherwise fail.
      if (!expected.equals(displayNodeAndColor(loadTree))) {
        fail("LoadFile class not working");
      }
      // clear the test file
      loader.clearFile();
    } catch (FileNotFoundException e) { // loadData method of LoadFile doesn't work
      e.printStackTrace();
      fail("The file didn't load the data properly");
    }
  }
}
