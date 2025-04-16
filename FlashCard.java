
/**
 * FlashCard Class
 */
public class FlashCard{
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
}
