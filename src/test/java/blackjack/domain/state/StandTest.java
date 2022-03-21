package blackjack.domain.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Pattern;
import blackjack.util.BlackjackTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StandTest {

    @Test
    @DisplayName("Stand 상태에서는 hit할 수 없다.")
    void cannotHit() {
        // given
        Cards cards = new Cards(BlackjackTestUtil.createCards(20));
        State stand = new Stand(cards);

        // then
        assertThatThrownBy(() -> stand.hit(Card.of(Pattern.CLOVER, Denomination.ACE)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Stand 상태에서는 stand할 수 없다.")
    void cannotStand() {
        // given
        Cards cards = new Cards(BlackjackTestUtil.createCards(20));
        State stand = new Stand(cards);

        // then
        assertThatThrownBy(stand::stand)
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("끝났는지 물어보면 true를 반환한다.")
    void finishedTrue() {
        // given
        Cards cards = new Cards(BlackjackTestUtil.createCards(20));
        State stand = new Stand(cards);

        // when
        boolean actual = stand.isFinished();

        // then
        assertThat(actual).isTrue();
    }
}
