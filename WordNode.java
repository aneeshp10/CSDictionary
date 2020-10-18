/*
 * This class records a Word Node for a Red-Black Tree
 */

public class WordNode implements Comparable<WordNode> {

	private String word;
	private String definition;
	private String module;

	public WordNode(String word, String definition, String module) {
		this.word = word;
		this.definition = definition;
		this.module = module;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Override
	public int compareTo(WordNode o) {
		// TODO Auto-generated method stub
		return (this.word.compareTo(o.word));
	}

	public void display() {
		System.out.println(getWord() + ": " + getDefinition());
	}

	public static void main(String[] args) {
		//WordNode x = new WordNode("hii", "its me");
		//WordNode x2 = new WordNode("hi", "its me again");
		//System.out.println(x.compareTo(x2));
		RedBlackTree<WordNode> obj = new RedBlackTree<WordNode>();
		//Node<WordNode> obj2 = obj.root;
		System.out.println(obj.root.data);
	}

}
