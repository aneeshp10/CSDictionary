// --== CS400 File Header Information ==--
// Name: Tavish Vats
// Email: tvats@wisc.edu
// Team: GA
// Role: Test Engineer
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: there is a database.txt for this CS400Dictionary application
// which contains terms and definitions for testing purposes

/**
 * This class models a WordNode consisting of a term, definition, and module in a Red Black Tree
 *
 * @author aneesh
 */
public class WordNode implements Comparable<WordNode> {

  private String word; // data field which represents a word within WordNode.
  private String definition; // data field which represents a definition within WordNode.
  private String module; // data field which represents a module within WordNode.

  /**
   * Creates a new WordNode with given data
   * 
   * @param word       reference to the term in the WordNode
   * @param definition reference to the definition in the WordNode
   * @param module     reference to the module in the WordNode
   * 
   */
  public WordNode(String word, String definition, String module) {
    this.word = word;
    this.definition = definition;
    this.module = module;
  }

  /**
   * Gets the term related to the WordNode
   * 
   * @return term from CS dictionary
   * 
   */
  public String getWord() {
    return word;
  }

  /**
   * Sets word in the WordNode
   * 
   * @param word the term to set
   * 
   */
  public void setWord(String word) {
    this.word = word;
  }

  /**
   * Gets the definition of a specific WordNode
   * 
   * @return definition of type string
   * 
   */
  public String getDefinition() {
    return definition;
  }

  /**
   * Sets definition in the WordNode
   * 
   * @param definition the defintition to set
   *
   */
  public void setDefinition(String definition) {
    this.definition = definition;
  }

  /**
   * Gets the module from the WordNode
   * 
   * @return the module in String type
   * 
   */
  public String getModule() {
    return module;
  }

  /**
   * Sets module in the WordNode
   * 
   * @param module the module to set
   * 
   */
  public void setModule(String module) {
    this.module = module;
  }


  /**
   * Compares two WordNodes based on lexicographical form of the word
   * 
   * @returns the value 0 if the argument o is the same as this WordNode; a value less than 0 if
   *          this patient is lexicographically lower in value; value greater than 0 otherwise.
   * @throws NullPointerException if o is null
   * 
   */
  @Override
  public int compareTo(WordNode o) {
    return (this.word.compareTo(o.word));
  }
}
