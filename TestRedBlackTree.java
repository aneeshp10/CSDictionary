
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

/*
 * This class tests the working and functionality of a Red Black Tree. 
 * Throughout this class there is a total of 4 distinct cases tested. 
 * After inserting element, the tree's level-order traversal is 
 * confirmed with the expected structure as well as each element 
 * in the tree's color. If incorrect results are obtained, JUnit tests
 * throws an error.
 */

public class TestRedBlackTree {

	/*
	 * Inserts the following integers in this order: 8,18.5,15,17,25,40,80 Checks if
	 * after each insertion the level-order traversal and each node's color matches
	 * the expected
	 */
	@Test
	public void firstTest() {
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		tree.insert(8);
		tree.insert(18);
		tree.insert(5);
		assertEquals("[8, 5, 18]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(false, tree.root.leftChild.isBlack);

		tree.insert(15);
		assertEquals("[8, 5, 18, 15]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(true, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.leftChild.isBlack);

		tree.insert(17);
		assertEquals("[8, 5, 17, 15, 18]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(true, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.isBlack);

		tree.insert(25);
		assertEquals("[8, 5, 17, 15, 18, 25]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.rightChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.rightChild.isBlack);

		tree.insert(40);
		assertEquals("[8, 5, 17, 15, 25, 18, 40]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.rightChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.rightChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.leftChild.isBlack);

		tree.insert(80);
		assertEquals("[17, 8, 25, 5, 15, 18, 40, 80]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(false, tree.root.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.rightChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.leftChild.isBlack);
		assertEquals(true, tree.root.leftChild.rightChild.isBlack);

// Final output:		
//						  17 
//					   /     \
//					  8       25
//					 / \     /  \
//					5   15  18   40
//								   \
//								    80

	}

	/*
	 * Inserts the following integers in this order:2, 1, 4, 5, 9, 3, 6, 7 Checks if
	 * after each insertion the level-order traversal and each node's color matches
	 * the expected
	 */
	@Test
	public void secondTest() {
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		tree.insert(2);
		tree.insert(1);
		tree.insert(4);
		assertEquals("[2, 1, 4]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(false, tree.root.leftChild.isBlack);

		tree.insert(5);
		assertEquals("[2, 1, 4, 5]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(true, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.isBlack);

		tree.insert(9);
		assertEquals("[2, 1, 5, 4, 9]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(true, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.isBlack);
		assertEquals(false, tree.root.rightChild.leftChild.isBlack);

		tree.insert(3);
		assertEquals("[2, 1, 5, 4, 9, 3]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.rightChild.isBlack);
		assertEquals(true, tree.root.rightChild.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.leftChild.leftChild.isBlack);

		tree.insert(6);
		assertEquals("[2, 1, 5, 4, 9, 3, 6]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.rightChild.isBlack);
		assertEquals(true, tree.root.rightChild.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.leftChild.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.leftChild.isBlack);

		tree.insert(7);
		assertEquals("[2, 1, 5, 4, 7, 3, 6, 9]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.rightChild.isBlack);
		assertEquals(true, tree.root.rightChild.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.leftChild.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.rightChild.isBlack);
		Integer leafNode = 9;
		assertEquals(leafNode, tree.root.rightChild.rightChild.rightChild.data);

		// Final output:
//		  2 
//	   /     \
//	  1       5
//	         /  \
//	        4    7
//		   /    / \
//		  3    6    9	 

	}

	/*
	 * Inserts the following integers in this order:45,25,72,18,31,68,91 Checks if
	 * after each insertion the level-order traversal and each node's color matches
	 * the expected (Example from lecture video)
	 */
	@Test
	public void thirdTest() {
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		tree.insert(45);
		tree.insert(26);
		tree.insert(72);
		assertEquals(false, tree.root.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);

		// following is the pre-determined values entered in the tree from the lecture
		// video
		tree.insert(18);
		tree.insert(31);
		tree.insert(68);
		tree.insert(91);
		tree.insert(13);
		tree.insert(28);
		tree.insert(88);
		assertEquals("[45, 26, 72, 18, 31, 68, 91, 13, 28, 88]", tree.toString());

		tree.insert(19);
		assertEquals("[45, 26, 72, 18, 31, 68, 91, 13, 19, 28, 88]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(false, tree.root.leftChild.isBlack);
		assertEquals(true, tree.root.leftChild.leftChild.isBlack);
		assertEquals(true, tree.root.leftChild.rightChild.isBlack);
		assertEquals(false, tree.root.leftChild.leftChild.leftChild.isBlack);
		assertEquals(false, tree.root.leftChild.leftChild.rightChild.isBlack);
		assertEquals(false, tree.root.leftChild.rightChild.leftChild.isBlack);
		assertEquals(false, tree.root.rightChild.isBlack);
		assertEquals(true, tree.root.rightChild.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.rightChild.isBlack);
		assertEquals(false, tree.root.rightChild.rightChild.leftChild.isBlack);

		tree.insert(20);
		assertEquals("[45, 26, 72, 18, 31, 68, 91, 13, 19, 28, 88, 20]", tree.toString());
		assertEquals(false, tree.root.leftChild.leftChild.rightChild.rightChild.isBlack); // check if 20 (new entered
																							// node) is red
		// check if the colors correctly changed from the previous tree
		assertEquals(true, tree.root.leftChild.isBlack); // root's left and right child isBlack
		assertEquals(true, tree.root.rightChild.isBlack);
		assertEquals(false, tree.root.leftChild.leftChild.isBlack);
		assertEquals(true, tree.root.leftChild.leftChild.leftChild.isBlack);
		assertEquals(true, tree.root.leftChild.leftChild.rightChild.isBlack);
		assertNull(tree.root.leftChild.leftChild.leftChild.leftChild);

		tree.insert(21);
		assertEquals("[45, 26, 72, 18, 31, 68, 91, 13, 20, 28, 88, 19, 21]", tree.toString());
		Integer twenty = 20;
		Integer nineteen = 19;
		Integer twentyOne = 21;
		assertEquals(twenty, tree.root.leftChild.leftChild.rightChild.data);
		assertEquals(nineteen, tree.root.leftChild.leftChild.rightChild.leftChild.data);
		assertEquals(twentyOne, tree.root.leftChild.leftChild.rightChild.rightChild.data);

		tree.insert(22);
		assertEquals("[45, 20, 72, 18, 26, 68, 91, 13, 19, 21, 31, 88, 22, 28]", tree.toString());

// Final output:
//					       45 
//	 			   /            \
//				   20             72
//				 /   \            /  \
//				18     26        68   91
//			   / \	   / \		      / 
//			 13   19  21  31          88   
//                     \    /
//					   22   28

	}

	/*
	 * Inserts the following characters in this order:J,F,D,G,G,F,B,E,H,Z Checks if
	 * after each insertion the level-order traversal and each node's color matches
	 * the expected
	 */
	@Test
	public void fourthTest() {
		RedBlackTree<Character> tree = new RedBlackTree<Character>();
		tree.insert('J');
		tree.insert('F');
		tree.insert('D');
		assertEquals("[F, D, J]", tree.toString());

		tree.insert('G');
		assertEquals("[F, D, J, G]", tree.toString());
		assertEquals(true, tree.root.isBlack);
		assertEquals(true, tree.root.leftChild.isBlack);
		assertEquals(true, tree.root.rightChild.isBlack);
		assertEquals(false, tree.root.rightChild.leftChild.isBlack);

		// adding a duplicate element 'G' and expect an IllegalArgumentException
		Assert.assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {

			@Override
			public void run() throws Throwable {
				// TODO Auto-generated method stub
				tree.insert('G');
			}
		});

		// adding another duplicate element expecting an exception to be thrown
		Assert.assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {

			@Override
			public void run() throws Throwable {
				// TODO Auto-generated method stub
				tree.insert('F');

			}
		});

		// normal insertion
		tree.insert('B');
		tree.insert('E');
		assertEquals("[F, D, J, B, E, G]", tree.toString());

		// case 3 where sibling is null/black -> two rotations needed
		tree.insert('H');
		assertEquals("[F, D, H, B, E, G, J]", tree.toString());

		// case 2 where sibling is red -> one rotation
		tree.insert('Z');
		assertEquals("[F, D, H, B, E, G, J, Z]", tree.toString());

// Final output:		
//		  F 
//	   /     \
//	  D       H
//	 / \     /  \
//	B   E  G      J
//				   \
//			        Z

	}
}
