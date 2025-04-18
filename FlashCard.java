
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;


/**
 * FlashCard Class
 */
public class FlashCard implements Serializable{
    private String term; /** Term*/
    private String definition; /** Definition*/

    /**
     * Constuctor
     * 
     * @param term term for the flashcard
     * @param definition definition of the term
     */
    public FlashCard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    /**
     * Getter to get the term
     */
    public String getTerm(){
        return this.term;
    }
    
    /**
     * Getter to get the definition
     */
    public String getDef(){
        return this.definition;
    }

    /**
     * Setter to set the term of the flashcard
     * 
     * @param term term of the flashcard
     */
    public void updateTerm(String term){
        this.term = term;
    }

    /**
     * Setter to set the definition
     * 
     * @param definition definition of the term
     */
    public void updateDef(String definition){
        this.definition = definition;
    }

    /**
     * Reads the state of the object from an input stream. Uses Java's built in serialization
     * 
     * @param stream the stream the object will be read from
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException { // Compliant with CWE-397, Not throwing a Generic Expression
        stream.defaultReadObject();
    }
}
