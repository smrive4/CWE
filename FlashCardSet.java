
import java.util.ArrayList;

/**
 * FlashCard Set Class
 */
public class FlashCardSet{
    private String topic; /** Topic of the FlashCards */
    private ArrayList<FlashCard> cards; /** List of Flashcards */

    /**
     * Constructor
     */
    public FlashCardSet(String topic, ArrayList<FlashCard> cards){
        this.topic = topic;

        // Create a deep copy
        for(int i = 0; i < cards.size(); i++)
        {
            this.cards.add(new FlashCard(cards.get(i).getTerm(), cards.get(i).getDef()));
        }

    }

    /**
     * Getter to get the topic
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
     */
    public FlashCard getCard(int index)
    {
        return new FlashCard(cards.get(index).getTerm(), cards.get(index).getDef());
    }

    /**
     * Add a flashcard to the set
     * 
     * @param card card to be added
     */
    public void addCard(FlashCard card)
    {
        cards.add(new FlashCard(card.getTerm(), card.getDef()));
    }

}