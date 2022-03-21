package blackjack.view;

import blackjack.domain.GameResult;
import blackjack.domain.card.Card;
import blackjack.dto.ParticipantInitialResponse;
import blackjack.dto.ParticipantResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    private OutputView() {
        
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public static void printStartMessage(ParticipantInitialResponse dealer, List<ParticipantResponse> players) {
        List<String> playerNames = players.stream()
                .map(ParticipantResponse::getName)
                .collect(Collectors.toUnmodifiableList());
        System.out.printf("%n%s와 %s에게 2장의 카드를 나누었습니다.%n%n", dealer.getName(), String.join(", ", playerNames));
        printInitialDealerCards(dealer);
        players.forEach(OutputView::printPlayerCards);
    }

    public static void printInitialDealerCards(ParticipantInitialResponse dealer) {
        String cardsInfo = createCardsString(dealer.getCards());
        System.out.printf("%s 카드: %s%n", dealer.getName(), cardsInfo);
    }

    public static void printPlayersCards(List<ParticipantResponse> players) {
        players.forEach(OutputView::printPlayerCards);
    }

    public static void printPlayerCards(ParticipantResponse participant) {
        String cardsInfo = createCardsString(participant.getCards());
        System.out.printf("%s 카드: %s - 합계: %d%n", participant.getName(), cardsInfo, participant.getScore());
    }

    private static String createCardsString(List<Card> cards) {
        return cards.stream()
                .map(OutputView::createCardInfoString)
                .collect(Collectors.joining(", "));
    }

    private static String createCardInfoString(Card card) {
        return String.format("%s%s", card.getDenomination().getName(), card.getPattern().getName());
    }

    public static void printBlackJackMessage(String name) {
        System.out.printf("%s는 블랙잭입니다!!%n", name);
    }

    public static void printBustMessage(String name) {
        System.out.printf("%s의 카드의 합이 21을 넘었습니다.%n", name);
    }

    public static void printDealerHitMessage(ParticipantResponse dealer) {
        System.out.printf("%n%s는 16이하라 한 장의 카드를 더 받았습니다.%n", dealer.getName());
    }

    public static void printCardResultMessage() {
        System.out.printf("%n## 최종 카드%n");
    }

    public static void printGameResult(GameResult gameResult) {
        System.out.printf("%n## 최종 수익%n");
        printDealerProfit(gameResult);
        printPlayersProfit(gameResult.getProfits());
    }

    private static void printDealerProfit(GameResult gameResult) {
        System.out.printf("딜러: %d%n", gameResult.getDealerProfit());
    }

    private static void printPlayersProfit(Map<String, Integer> playersProfits) {
        playersProfits.forEach((key, value) -> System.out.printf("%s: %d%n", key, value));
    }
}
