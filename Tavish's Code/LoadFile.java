// --== CS400 File Header Information ==--
// Name: Tavish Vats
// Email: tvats@wisc.edu
// Team: GA
// Role: Test Engineer
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: there is a database.txt for this CS400Dictionary application
// which contains terms and definitions for testing purposes

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class is used to load and save data from file to a Red Black Tree and its contents
 * 
 * @author Jeff
 *
 */
public class LoadFile {
  protected int size; // size of Dictionary
  protected File file; // database of Dictionary
  private Scanner scan = null; // scan the file
  private boolean isEmpty = true; // true if file is empty, otherwise false
  WordNode node; // term, definition, and module
  String word = null; // term
  String define = null; // definition
  String module = null; // module

  /**
   * File constructor that takes the file as a parameter.
   * 
   * @param file - stores the terms and definitions in the dictionary
   */
  public LoadFile(File file) {
    Scanner temp;
    try {
      file.createNewFile();
      this.file = file;
      temp = new Scanner(file);
      isEmpty = !temp.hasNext();
      scan = new Scanner(file);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  /**
   * This method loads data from a file, and then inserts it into a new RBT.
   * 
   * @param tree - stores the terms, definitions and module.
   * @throws FileNotFoundException - if file not found
   */
  public void loadData(RedBlackTree<WordNode> tree) throws FileNotFoundException {
    // check for next line
    while (scan.hasNext()) {
      String inputStr = scan.nextLine();
      String info;
      if (inputStr.equals("") || inputStr.equals("\n")) {
        continue;
      }

      info = inputStr;
      // assign inputString to the everything before the ":"
      inputStr = inputStr.substring(0, inputStr.indexOf(":"));
      // if inputString is Word, store the term in word
      if (inputStr.equals("Word")) {
        word = info.substring(info.indexOf(":") + 1).trim();
      }
      // if inputString is Define, store the definition in define
      if (inputStr.equals("Define")) {
        define = info.substring(info.indexOf(":") + 1).trim();
      }
      // if inputString is Module, store the module no. in module
      if (inputStr.equals("Module")) {
        module = info.substring(info.indexOf(":") + 1).trim();
      }
      // if inputString is stop, then insert the WordNode to the tree
      if (inputStr.contains("stop")) {
        tree.insert(new WordNode(word, define, module));
        size++;
      }
    }
    scan.close(); // closes the scanner
  }

  /**
   * This method clears a file and sets size of the dictionary to 0
   * 
   */
  protected void clearFile() {
    try {
      new FileWriter(file.getPath(), false).close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    size = 0;
  }

  /**
   * This method saves the terms, definitions, and modules to the database
   * 
   * @param rbt - the terms, definitons, and modules needed to be stored in the database
   *
   */
  public void saveData(RedBlackTree<WordNode> rbt) {
    // clear the file and set size to 0
    clearFile();
    // level order traversal of the rbt
    LinkedList<RedBlackTree.Node<WordNode>> q = new LinkedList<>();
    q.add(rbt.root);
    while (!q.isEmpty()) {
      RedBlackTree.Node<WordNode> next = q.removeFirst();
      if (next.leftChild != null)
        q.add(next.leftChild);
      if (next.rightChild != null)
        q.add(next.rightChild);
      // store the term, definiton, and module no. in the info array
      String[] info = {"", "", ""};
      info[0] += " " + next.data.getWord();
      info[0] = info[0].trim();
      info[1] += " " + next.data.getDefinition();
      info[1] = info[1].trim();
      info[2] += " " + next.data.getModule();
      info[2] = info[2].trim();
      info[2] += " ";

      // write the terms, definition, and module no. in the file
      writeFile("Word: " + info[0]);
      writeFile("Define: " + info[1]);
      writeFile("Module: " + info[2]);
      writeFile("stop: " + "\n");

      // increment the size of the dictionary
      size++;
    }
    // write end: to the file to indicate the end of the file
    writeFile("end:");
  }

  /**
   * This is a helper method that allows the file to be written in
   * 
   * @param input The input to write into the file
   */
  private void writeFile(String input) {
    FileWriter fw = null;
    try {
      // if the file is empty, initializes without append, if not then initialize append to true
      if (isEmpty == true) {
        fw = new FileWriter(file.getPath());
        isEmpty = false;
      } else {
        fw = new FileWriter(file.getPath(), true);
      }
      // write the given input to the file
      fw.write(input + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fw != null) {
        try {
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
