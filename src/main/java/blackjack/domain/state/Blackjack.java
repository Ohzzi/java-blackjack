package blackjack.domain.state;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;

public final class Blackjack implements State {
    public Blackjack(Cards cards) {
    }

    @Override
    public State hit(Card card) {
        return null;
    }
}
