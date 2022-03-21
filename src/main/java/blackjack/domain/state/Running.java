package blackjack.domain.state;

import blackjack.domain.card.Cards;

public abstract class Running extends AbstractState {

    protected Running(Cards cards) {
        super(cards);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
