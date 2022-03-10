package blackjack.view;

import blackjack.domain.Dealer;
import blackjack.domain.Participant;
import blackjack.domain.Player;
import blackjack.domain.card.Card;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public static void printFatalErrorMessage(String message) {
        System.err.println(message);
        System.err.println("심각한 오류가 발생하여 프로그램을 종료합니다.");
    }

    public static void printStartMessage(Dealer dealer, List<Player> players) {
        List<String> playerNames = players.stream()
                .map(Player::getName)
                .collect(Collectors.toUnmodifiableList());
        System.out.printf("%s와 %s에게 2장의 카드를 나누었습니다.", dealer.getName(), String.join(", ", playerNames));
        System.out.println();
    }

    public static void printDealerFirstCard(Dealer dealer) {
        Card firstCard = dealer.getCards().get(0);
        System.out.printf("%s: %s", dealer.getName(), createCardInfoString(firstCard));
        System.out.println();
    }

    public static void printParticipantCards(Participant participant, int score) {
        String cardsInfo = participant.getCards()
                .stream()
                .map(OutputView::createCardInfoString)
                .collect(Collectors.joining(", "));

        System.out.printf("%s카드: %s - 합계: %d", participant.getName(), cardsInfo, score);
        System.out.println();
    }

    private static String createCardInfoString(Card card) {
        return card.getDenomination().getName() + card.getPattern().getName();
    }

    public static void printBustMessage() {
        System.out.println("카드의 합이 21을 넘겼습니다.");
    }

    public static void printBlackJackMessage() {
        System.out.println("블랙잭입니다!!");
    }

    public static void printDealerHitMessage() {
        System.out.println("딜러는 16이하라 한 장의 카드를 더 받았습니다.");
    }
}
