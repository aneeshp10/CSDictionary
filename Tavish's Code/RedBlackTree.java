// --== CS400 File Header Information ==--
// Name: Tavish Vats
// Email: tvats@wisc.edu
// Team: GA
// Role: Test Engineer
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: there is a database.txt for this CS400Dictionary application
// which contains terms and definitions for testing purposes

import java.util.LinkedList;

/**
 * Red Black Tree implementation with a Node inner class for representing the nodes within a binary
 * search tree. You can use this class' insert method to build a red black tree, and its toString
 * method to display the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

  /**
   * This class represents a node holding a single value within a red black tree the parent, left,
   * and right child references are always be maintained. Moreover, the isBlack is true when the
   * value is a black node, and false when it's a red node.
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
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
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
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the tree already contains data
   */
  public void insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null) {
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    }
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
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references (as
   *                                  defined by Comparable.compareTo())
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
        enforceRBTreePropertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.leftChild);
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.rightChild);
    }
  }


  private void enforceRBTreePropertiesAfterInsert(Node<T> node) {
    // create a temp node to store the uncle node
    Node<T> tempNode = null;
    // if the node's parent is red
    if (!node.parent.isBlack) {
      // check if the node's parent has a parent and is the left child of its parent
      if (node.parent.isLeftChild()) {
        // set the uncle node which is the node's grandparent's right child
        tempNode = node.parent.parent.rightChild;
        // check if uncle node is not null and red in color
        if ((tempNode != null) && (!tempNode.isBlack)) {
          // set node's parent and uncle to black color
          // set the node's grandparent to red color
          node.parent.isBlack = true;
          tempNode.isBlack = true;
          node.parent.parent.isBlack = false;
          // do a recursive call on the node's grandparent if it is not the root
          if (node.parent.parent != root) {
            enforceRBTreePropertiesAfterInsert(node.parent.parent);
          }
        } // if the uncle node is black in color
        else {
          // if the node has a parent and is the right child of it's parent
          if (!node.isLeftChild()) {
            // set node to it's parent and perform rotate left
            // after rotate left, node becomes the left child and its parent is the node we called
            // this method with as the input parameter
            node = node.parent;
            rotate(node.rightChild, node);
          }
          // set the node's parent to black and its grandparent to red
          // then perform rotate right on the node's parent and grandparent
          node.parent.isBlack = true;
          node.parent.parent.isBlack = false;
          rotate(node.parent, node.parent.parent);
        }
      } // if the node's parent has a parent and is the right child of its parent
      else {
        if (node.parent.parent != null) {
          // set the uncle node which is the node's grandparent's left child
          tempNode = node.parent.parent.leftChild;
          // check if uncle node is not null and red in color
          if ((tempNode != null) && (!tempNode.isBlack)) {
            // set node's parent and uncle to black color
            // set the node's grandparent to red color
            node.parent.isBlack = true;
            tempNode.isBlack = true;
            node.parent.parent.isBlack = false;
            // do a recursive call on the node's grandparent if it is not the root
            if (node.parent.parent != root) {
              enforceRBTreePropertiesAfterInsert(node.parent.parent);
            }
          } // if the uncle node is black in color
          else {
            // if the node has a parent and is the left child of it's parent
            if (node.isLeftChild()) {
              // set node to it's parent and perform rotate right
              // after rotate right, node becomes the right child and its parent is the node we
              // called this method with as input parameter
              node = node.parent;
              rotate(node.leftChild, node);
            }
            // set the node's parent to black and its grandparent to red
            // then perform rotate left on the node's parent and grandparent
            node.parent.isBlack = true;
            node.parent.parent.isBlack = false;
            rotate(node.parent, node.parent.parent);
          }
        }
      }
    }
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection, like java.util.ArrayList, LinkedList,
   * etc).
   * 
   * @return string containing the values of this tree in level order
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * Performs the rotation operation on the provided nodes within this BST. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation (sometimes
   * called a left-right rotation). When the provided child is a rightChild of the provided parent,
   * this method will perform a left rotation (sometimes called a right-left rotation). When the
   * provided nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // if the parent's left child is child, perform rotate right algorithm
    if (parent.leftChild == child) {
      // set the parent's left child pointer to the child's right child
      parent.leftChild = child.rightChild;
      // if the child's right child is not null, set its parent to the parent node
      if (child.rightChild != null) {
        child.rightChild.parent = parent;
      }
      // the child's parent node is set to the parent's parent node
      child.parent = parent.parent;
      // if parent is the root, then assign the child as the root
      // if parent has a parent, then assign the grandparents left/right child pointer to child
      if (parent.parent == null) {
        root = child;
      } else if (parent.isLeftChild()) {
        parent.parent.leftChild = child;
      } else {
        parent.parent.rightChild = child;
      }
      // finally assign the child's right child node to parent and the parent's parent node to child
      // to complete the rotate right
      child.rightChild = parent;
      parent.parent = child;
    }
    // if the parent's right child is child, perform rotate left algorithm
    else if (parent.rightChild == child) {
      // set the parent's right child pointer to the child's left child
      parent.rightChild = child.leftChild;
      // if the child's left child is not null, set its parent to the parent node
      if (child.leftChild != null) {
        child.leftChild.parent = parent;
      }
      // the child's parent node is set to the parent's parent node
      child.parent = parent.parent;
      // if parent is the root, then assign the child as the root
      // if parent has a parent, then assign the grandparents left/right child pointer to child
      if (parent.parent == null) {
        root = child;
      } else if (parent.isLeftChild()) {
        parent.parent.leftChild = child;
      } else {
        parent.parent.rightChild = child;
      }
      // finally assign the child's left child node to parent and the parent's parent node to child
      // to complete the rotate left
      child.leftChild = parent;
      parent.parent = child;
    }
    // if parent and child are not initially related, throw IllegalArgumentException
    else {
      throw new IllegalArgumentException();
    }
  }
}
