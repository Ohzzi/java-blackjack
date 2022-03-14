package blackjack.domain.participant;

import blackjack.domain.Name;
import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import java.util.List;

public class Dealer extends Participant {

    private static final int HIT_STANDARD = 17;

    public Dealer(List<Card> cards) {
        super(new Name("딜러"), new Cards(cards));
    }

    @Override
    public List<Card> showInitialCards() {
        return List.of(getCards().get(0));
    }

    public boolean shouldHit() {
        return getScore() < HIT_STANDARD;
    }
}
