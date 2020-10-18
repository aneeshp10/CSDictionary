import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class LoadFile {
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
		Scanner scnr = new Scanner(new File("CS400Dictionary.txt"));
		
		while (scnr.hasNext()) // returns a boolean value
		{
			String inputStr = scnr.nextLine();
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
			}

		}

		scnr.close(); // closes the scanner

	}

}
