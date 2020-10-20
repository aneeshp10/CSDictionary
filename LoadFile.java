import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class LoadFile {
  protected int size;
  private File file;
  private Scanner scan = null;
  private boolean isEmpty = true;
  WordNode node;
  String word = null;
  String define = null;
  String module = null;

  public LoadFile(File file) {
    Scanner temp;
    try {
      file.createNewFile();
      this.file = file;
      temp = new Scanner(file);
      isEmpty = !temp.hasNext();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  public void loadData(RedBlackTree<WordNode> tree) throws FileNotFoundException {
    scan = new Scanner(new File("CS400Dictionary.txt"));

    while (scan.hasNext()) // returns a boolean value
    {
      String inputStr = scan.nextLine();
      String info;
      if (inputStr.equals("") || inputStr.equals("\n")) {
        continue;
      }

      info = inputStr;

      inputStr = inputStr.substring(0, inputStr.indexOf(":"));

      if (inputStr.equals("Word")) {
        word = info.substring(info.indexOf(":") + 1).trim();
      }

      if (inputStr.equals("Define")) {
        define = info.substring(info.indexOf(":") + 1).trim();
      }

      if (inputStr.equals("Module")) {
        module = info.substring(info.indexOf(":") + 1).trim();
      }

      if (inputStr.contains("stop")) {
        tree.insert(new WordNode(word, define, module));
        size++;
      }
    }
    scan.close(); // closes the scanner
  }

  /**
   * This method clears a file
   * 
   */
  private void clearFile() {
    try {
      new FileWriter(file.getPath(), false).close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    size = 0;
  }

  /**
   * This method saves the data to the file
   * 
   * @param newData-the data being saved to file.
   *
   */
  public void saveData(RedBlackTree<WordNode> rbt) {
    clearFile();

    LinkedList<RedBlackTree.Node<WordNode>> q = new LinkedList<>();
    q.add(rbt.root);
    while (!q.isEmpty()) {
      RedBlackTree.Node<WordNode> next = q.removeFirst();
      if (next.leftChild != null)
        q.add(next.leftChild);
      if (next.rightChild != null)
        q.add(next.rightChild);
      String[] info = {"", "", ""};
      info[0] += " " + next.data.getWord();
      info[0] = info[0].trim();
      info[1] += " " + next.data.getDefinition();
      info[1] = info[1].trim();
      info[2] += " " + next.data.getModule();
      info[2] = info[2].trim();
      info[2] += " ";

      writeFile("Word: " + info[0]);
      writeFile("Define: " + info[1]);
      writeFile("Module: " + info[2]);
      writeFile("stop: " + "\n");

      size++;
    }

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
      if (isEmpty == true) {
        fw = new FileWriter(file.getPath());
        isEmpty = false;
      } else {
        fw = new FileWriter(file.getPath(), true);
      }

      fw.write(input + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fw != null) {
        try {
          fw.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }
}
