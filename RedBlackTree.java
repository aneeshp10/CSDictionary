
import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes within a binary search tree. You can use this class' insert method
 * to build a binary search tree, and its toString method to display the level
 * order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

	/**
	 * This class represents a node holding a single value within a binary tree the
	 * parent, left, and right child references are always be maintained.
	 */
	protected static class Node<T> {
		public T data;
		public Node<T> parent; // null for root node
		public Node<T> leftChild;
		public Node<T> rightChild;
		public boolean isBlack;

		public Node(T data) {
			this.data = data;
			this.isBlack = false;
		}

		/**
		 * @return true when this node has a parent and is the left child of that
		 *         parent, otherwise return false
		 */
		public boolean isLeftChild() {
			return parent != null && parent.leftChild == this;
		}

		/**
		 * This method performs a level order traversal of the tree rooted at the
		 * current node. The string representations of each data value within this tree
		 * are assembled into a comma separated string within brackets (similar to many
		 * implementations of java.util.Collection).
		 * 
		 * @return string containing the values of this tree in level order
		 */
		@Override
		public String toString() { // display subtree in order traversal
			String output = "[";
			LinkedList<Node<T>> q = new LinkedList<>();
			q.add(this);
			while (!q.isEmpty()) {
				Node<T> next = q.removeFirst();
				if (next.leftChild != null)
					q.add(next.leftChild);
				if (next.rightChild != null)
					q.add(next.rightChild);
				output += next.data.toString();
				if (!q.isEmpty())
					output += ", ";
			}
			return output + "]";
		}
	}

	protected Node<T> root; // reference to root node of tree, null when empty

	/**
	 * Performs a naive insertion into a binary search tree: adding the input data
	 * value to a new node in a leaf position within the tree. After this insertion,
	 * no attempt is made to restructure or balance the tree. This tree will not
	 * hold null references, nor duplicate data values.
	 * 
	 * @param data to be added into this binary search tree
	 * @throws NullPointerException     when the provided data argument is null
	 * @throws IllegalArgumentException when the tree already contains data
	 */
	public void insert(T data) throws NullPointerException, IllegalArgumentException {
		// null references cannot be stored within this tree
		if (data == null)
			throw new NullPointerException("This RedBlackTree cannot store null references.");

		Node<T> newNode = new Node<>(data);
		if (root == null) {
			root = newNode;
		} // add first node to an empty tree
		else {
			insertHelper(newNode, root); // recursively insert into subtree
		}

		root.isBlack = true;
	}

	/**
	 * Recursive helper method to find the subtree with a null reference in the
	 * position that the newNode should be inserted, and then extend this tree by
	 * the newNode in that position.
	 * 
	 * @param newNode is the new node that is being added to this tree
	 * @param subtree is the reference to a node within this tree which the newNode
	 *                should be inserted as a descenedent beneath
	 * @throws IllegalArgumentException when the newNode and subtree contain equal
	 *                                  data references (as defined by
	 *                                  Comparable.compareTo())
	 */
	private void insertHelper(Node<T> newNode, Node<T> subtree) {
		int compare = newNode.data.compareTo(subtree.data);
		// do not allow duplicate values to be stored within this tree
		if (compare == 0)
			throw new IllegalArgumentException("This RedBlackTree already contains that value.");

		// store newNode within left subtree of subtree
		else if (compare < 0) {
			if (subtree.leftChild == null) { // left subtree empty, add here
				subtree.leftChild = newNode;
				newNode.parent = subtree;
				// otherwise continue recursive search for location to insert
			} else
				insertHelper(newNode, subtree.leftChild);
		}

		// store newNode within the right subtree of subtree
		else {
			if (subtree.rightChild == null) { // right subtree empty, add here
				subtree.rightChild = newNode;
				newNode.parent = subtree;
				// otherwise continue recursive search for location to insert
			} else
				insertHelper(newNode, subtree.rightChild);
		}

		enforceRBTPropertiesAfterInsert(newNode);
	}

	/**
	 * This method performs a level order traversal of the tree. The string
	 * representations of each data value within this tree are assembled into a
	 * comma separated string within brackets (similar to many implementations of
	 * java.util.Collection, like java.util.ArrayList, LinkedList, etc).
	 * 
	 * @return string containing the values of this tree in level order
	 */
	@Override
	public String toString() {
		return root.toString();
	}

	/**
	 * Performs the rotation operation on the provided nodes within this BST. When
	 * the provided child is a leftChild of the provided parent, this method will
	 * perform a right rotation (sometimes called a left-right rotation). When the
	 * provided child is a rightChild of the provided parent, this method will
	 * perform a left rotation (sometimes called a right-left rotation). When the
	 * provided nodes are not related in one of these ways, this method will throw
	 * an IllegalArgumentException.
	 * 
	 * @param child  is the node being rotated from child to parent position
	 *               (between these two node arguments)
	 * @param parent is the node being rotated from parent to child position
	 *               (between these two node arguments)
	 * @throws IllegalArgumentException when the provided child and parent node
	 *                                  references are not initially (pre-rotation)
	 *                                  related that way
	 */
	private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
		// TODO: Implement this method.\
		/*
		 * if (parent.leftChild == null || parent.rightChild == null) { throw new
		 * IllegalArgumentException(); }
		 */

		// rotate right when child is left child of parent
		if (parent.leftChild == child) {

			Node<T> superParent = parent.parent;
			parent.leftChild = child.rightChild;

			child.parent = parent.parent; // new added

			if (child.rightChild != null) {
				child.rightChild.parent = parent;
			}
			child.rightChild = parent;
			parent.parent = child;
			if (root == parent) {
				root = child;
			}
			if (superParent != null) {
				if (superParent.leftChild == parent) {
					superParent.leftChild = child;
				} else {
					superParent.rightChild = child;
				}
			}

		}

		// rotate left when child is right child of parent
		else if (parent.rightChild == child) {
			Node<T> superParent = parent.parent;
			parent.rightChild = child.leftChild;

			child.parent = parent.parent; // new added

			if (child.leftChild != null) {
				child.leftChild.parent = parent;
			}
			child.leftChild = parent;
			parent.parent = child;
			// System.out.println(child.parent.data);
			if (root == parent) {
				root = child;
			}
			if (superParent != null) {
				if (superParent.leftChild == parent) {
					superParent.leftChild = child;
				} else {
					superParent.rightChild = child;
				}
			}
		}

		// throw IllegalArgumentException if not related
		else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Fixes the tree after a new node is added to the tree if the RBT properties
	 * are violated
	 * 
	 * @param newNode is the latest node that has been added to the tree and is
	 *                being checked if it violates any RBT properties
	 * 
	 */
	private void enforceRBTPropertiesAfterInsert(Node<T> newNode) {

		while (newNode.parent != null && !newNode.parent.isBlack && !newNode.isBlack) {

			Node<T> sibling = null; // holds the sibling node

			// if newNode's parent node is a right child of grandparent node, sibling node
			// will be left child of grandparent node
			if (newNode.parent == newNode.parent.parent.rightChild) {
				sibling = newNode.parent.parent.leftChild;

				// check if sibling is also red, if sibling is red then recolor parent and
				// sibling and check if paernt's parent is is root node. If not, recolor
				// parent's parent too
				if (sibling != null && !sibling.isBlack) {
					sibling.isBlack = true;
					newNode.parent.isBlack = true;

					// recolor the parent's parent
					if (sibling.parent != root) {
						sibling.parent.isBlack = false;
					} else if (newNode.parent.parent == root) {
						newNode.parent.parent.isBlack = true;
					}

					// System.out.println(newNode.data);
					enforceRBTPropertiesAfterInsert(newNode.parent.parent);
				}

				else if (newNode == newNode.parent.rightChild) {
					// System.out.println(newNode.data);
					rotate(newNode.parent, newNode.parent.parent);
					// System.out.println("Hi " + newNode.parent.leftChild.data);
					// newNode.leftChild.isBlack = false;
					newNode.parent.leftChild.isBlack = false;
					newNode.parent.isBlack = true;

					// System.out.println(newNode.rightChild.rightChild.isBlack);

					enforceRBTPropertiesAfterInsert(newNode.parent);

				}

				else {

					rotate(newNode, newNode.parent);
					rotate(newNode, newNode.parent);
					newNode.isBlack = true;
					newNode.leftChild.isBlack = false;
					// System.out.println("helloo " + newNode.parent.parent);
					// System.out.println(newNode.parent.data);
					// enforceRBTPropertiesAfterInsert(newNode.parent.parent);
					enforceRBTPropertiesAfterInsert(newNode.parent);

				}

			}

			else if (newNode.parent == newNode.parent.parent.leftChild) {
				sibling = newNode.parent.parent.rightChild;
				if (sibling != null && !sibling.isBlack) {
					sibling.isBlack = true;
					newNode.parent.isBlack = true;
					if (sibling.parent != root) {
						sibling.parent.isBlack = false;
					}
				}

				else if (newNode == newNode.parent.leftChild) {

					rotate(newNode.parent, newNode.parent.parent);
					newNode.parent.rightChild.isBlack = false;
					newNode.parent.isBlack = true;
					// newNode.parent.parent.isBlack = false;
					// newNode.parent.isBlack = true;
				}

				else {
					// System.out.println("Enter");
					rotate(newNode, newNode.parent);
					rotate(newNode, newNode.parent);

					newNode.isBlack = true;
					newNode.rightChild.isBlack = false;
				}

			}

			else {
				throw new IllegalArgumentException();
			}
		}
	}

	// For the next two test methods, review your notes from the Module 4: Red
	// Black Tree Insertion Activity. Questions one and two in that activity
	// presented you with an initial BST and then asked you to trace the
	// changes that would be applied as a result of performing different
	// rotations on that tree. For each of the following tests, you'll first
	// create the initial BST that you performed each of these rotations on.
	// Then apply the rotations described in that activity: the right-rotation
	// in the Part1 test below, and the left-rotation in the Part2 test below.
	// Then ensure that these tests fail if and only if the level ordering of
	// tree values dose not match the order that you came up with in that
	// activity.

	public static void main(String[] args) {
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		tree.insert(10);
		tree.insert(18);
		tree.insert(7);
		tree.insert(15);
		tree.insert(16);
		tree.insert(30);
		tree.insert(25);
		tree.insert(40);
		tree.insert(60);
		tree.insert(2);
		tree.insert(1);
		tree.insert(70);
		// System.out.println(tree.toString());
		// System.out.println(tree.root.rightChild.rightChild.rightChild.rightChild.isBlack);

	}
}
