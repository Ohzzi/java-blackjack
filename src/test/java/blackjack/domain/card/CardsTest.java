package blackjack.domain.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.domain.Score;
import blackjack.util.BlackjackTestUtil;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardsTest {

    @Test
    @DisplayName("카드는 null일 수 없다.")
    void notNull() {
        // then
        assertThatThrownBy(() -> new Cards(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("[ERROR] 카드는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("시작 시 카드를 두 장 가지고 있어야 한다.")
    void validCardsSize() {
        // given
        Card card1 = Card.of(Pattern.DIAMOND, Denomination.THREE);
        Card card2 = Card.of(Pattern.CLOVER, Denomination.THREE);
        List<Card> cards = List.of(card1, card2);

        // when
        Cards actual = new Cards(cards);

        // then
        assertThat(actual.getCards()).containsOnly(card1, card2);
    }

    @Test
    @DisplayName("카드가 중복되면 예외가 발생한다.")
    void duplicatedCards() {
        // given
        Card card = Card.of(Pattern.DIAMOND, Denomination.THREE);
        List<Card> cards = List.of(card, card);

        // then
        assertThatThrownBy(() -> new Cards(cards))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 카드는 중복될 수 없습니다.");
    }

    @Test
    @DisplayName("카드의 점수 총합을 계산한다. (에이스가 없는 경우)")
    void calculateCardsSum() {
        // given
        Card card1 = Card.of(Pattern.DIAMOND, Denomination.THREE);
        Card card2 = Card.of(Pattern.CLOVER, Denomination.THREE);
        Cards cards = new Cards(List.of(card1, card2));

        // when
        Score actual = cards.calculateScore();
        Score expected = new Score(6);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("카드의 점수 총합을 계산한다. (에이스가 있는 경우)")
    void calculateCardsSumWithACE() {
        // given
        Card card1 = Card.of(Pattern.DIAMOND, Denomination.TEN);
        Card card2 = Card.of(Pattern.CLOVER, Denomination.ACE);
        Cards cards = new Cards(List.of(card1, card2));

        // when
        Score actual = cards.calculateScore();
        Score expected = new Score(21);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("카드의 점수 총합을 계산한다. (총 합이 21이 넘는 경우)")
    void calculateCardsSumOver21() {
        // given
        Card additionalCard = Card.of(Pattern.HEART, Denomination.TWO);
        Cards cards = new Cards(BlackjackTestUtil.createCards(20));
        cards.add(additionalCard);

        // when
        Score actual = cards.calculateScore();
        Score expected = new Score(22);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
