
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * FlashCard Set Class
 */
public class FlashCardSet implements Serializable{
    private String topic; /** Topic of the FlashCards */
    private ArrayList<FlashCard> cards; /** List of Flashcards */
    private int numOfCards;

    /**
     * Constructor
     */
    public FlashCardSet(String topic, ArrayList<FlashCard> cards){
        this.topic = topic;
        this.cards = new ArrayList<>();

        this.numOfCards = cards.size();
        // Create a deep copy
        for(int i = 0; i < cards.size(); i++)
        {
            this.cards.add(new FlashCard(cards.get(i).getTerm(), cards.get(i).getDef()));
            
        }
    }

    /**
     * Getter to get the topic
     * 
     * @return the topic of the set
     */
    public String getTopic(){
        return this.topic;
    }

    /**
     * Setter to set the topic
     * 
     * @param topic topic name
     */
    public String setTopic(String topic)
    {
        return this.topic = topic;
    }

    /**
     * Getter to get a card from the set
     * 
     * @param index index of the card that will be returned
     * @return a FlashCard Object at that given index
     */
    public FlashCard getCard(int index)
    {
        if(index >= numOfCards) // Compliant with CWE-1284, ensuring input is validate to ensure it's a valid index
            return null; 

        return new FlashCard(cards.get(index).getTerm(), cards.get(index).getDef()); // Compliant with CWE-375, returning a copy of the object to a untrusted caller
    }

    /**
     * Add a flashcard to the set
     * 
     * @param card card to be added
     */
    public void addCard(FlashCard card)
    {
        cards.add(new FlashCard(card.getTerm(), card.getDef()));
        numOfCards++;
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

    /**
     * Method to return the number of cards
     * 
     * @return the number of cards in the set
     */
    public int getNumOfCards() {
        return numOfCards;
    }

}