package blackjack.domain.card;

import blackjack.domain.Score;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cards {

    private static final int BUST_STANDARD = 21;
    private static final Score BLACKJACK_SCORE = new Score(21);
    private static final int BLACKJACK_SIZE = 2;

    private final List<Card> cards;

    public Cards(List<Card> cards) {
        Objects.requireNonNull(cards, "[ERROR] 카드는 null일 수 없습니다.");
        cards = new ArrayList<>(cards);
        validateDistinct(cards);
        this.cards = cards;
    }

    private void validateDistinct(List<Card> cards) {
        if (cards.stream().distinct().count() != cards.size()) {
            throw new IllegalArgumentException("[ERROR] 카드는 중복될 수 없습니다.");
        }
    }

    public Cards add(Card card) {
        cards.add(card);
        return new Cards(cards);
    }

    public Score calculateScore() {
        int score = calculateMinimumScore(cards);
        if (containsAce(cards) && score + 10 <= BUST_STANDARD) {
            return new Score(score + 10);
        }
        return new Score(score);
    }

    private int calculateMinimumScore(List<Card> cards) {
        return cards.stream()
                .mapToInt(Card::getValue)
                .sum();
    }

    private boolean containsAce(List<Card> cards) {
        return cards.stream()
                .anyMatch(card -> card.isSameValueWith(Denomination.ACE));
    }

    public boolean isBust() {
        return calculateScore().isGreaterThan(BUST_STANDARD);
    }

    public boolean isBlackjack() {
        return cards.size() == BLACKJACK_SIZE && calculateScore().equals(BLACKJACK_SCORE);
    }

    public List<Card> getCards() {
        return cards;
    }
}
