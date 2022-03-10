package blackjack;

import blackjack.domain.HitRequest;
import blackjack.domain.Name;
import blackjack.domain.Rule;
import blackjack.domain.card.BlackJackCardsGenerator;
import blackjack.domain.card.CardDeck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import blackjack.domain.winResult;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.List;
import java.util.stream.Collectors;

public class BlackJackApplication {

    public static void main(String[] args) {
        try {
            CardDeck deck = new CardDeck(new BlackJackCardsGenerator());
            Dealer dealer = new Dealer(deck.drawDouble());
            List<Player> players = createPlayers(inputPlayerNames(), deck);
            play(deck, dealer, players);
        } catch (NullPointerException | IllegalArgumentException e) {
            OutputView.printFatalErrorMessage(e.getMessage());
        }
    }

    private static List<Name> inputPlayerNames() {
        try {
            return InputView.inputPlayerNames().stream()
                    .map(Name::new)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return inputPlayerNames();
        }
    }

    private static List<Player> createPlayers(List<Name> playerNames, CardDeck deck) {
        return playerNames.stream()
                .map(name -> new Player(name, deck.drawDouble()))
                .collect(Collectors.toUnmodifiableList());
    }

    private static void play(CardDeck deck, Dealer dealer, List<Player> players) {
        alertStart(dealer, players);
        proceedPlayersTurn(players, deck);
        proceedDealer(dealer, deck);
        showResult(dealer, players);
    }

    private static void alertStart(Dealer dealer, List<Player> players) {
        OutputView.printStartMessage(dealer, players);
        OutputView.printDealerFirstCard(dealer);
        players.forEach(player -> {
            int score = Rule.INSTANCE.calculateSum(player.getCards());
            OutputView.printParticipantCards(player, score);
        });
    }

    private static void proceedPlayersTurn(List<Player> players, CardDeck deck) {
        players.forEach(player -> proceedPlayer(player, deck));
    }

    private static void proceedPlayer(Player player, CardDeck deck) {
        while (player.isHittable() && inputHitRequest(player) == HitRequest.YES) {
            player.hit(deck);
            OutputView.printParticipantCards(player, Rule.INSTANCE.calculateSum(player.getCards()));
        }
        showStopReason(player);
    }

    private static HitRequest inputHitRequest(Player player) {
        try {
            return HitRequest.find(InputView.inputHitRequest(player.getName()));
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return inputHitRequest(player);
        }
    }

    private static void showStopReason(Player player) {
        if (Rule.INSTANCE.isBlackJack(player.getCards())) {
            OutputView.printBlackJackMessage();
            return;
        }
        if (Rule.INSTANCE.isBust(player.getCards())) {
            OutputView.printBustMessage();
        }
    }

    private static void proceedDealer(Dealer dealer, CardDeck deck) {
        while (dealer.isHittable()) {
            dealer.hit(deck);
            OutputView.printDealerHitMessage();
        }
    }

    private static void showResult(Dealer dealer, List<Player> players) {
        OutputView.printCardResultMessage();
        OutputView.printParticipantCards(dealer, Rule.INSTANCE.calculateSum(dealer.getCards()));
        players.forEach(
                player -> OutputView.printParticipantCards(player, Rule.INSTANCE.calculateSum(player.getCards())));
        OutputView.printWinResult(new winResult(dealer, players));
    }
}
