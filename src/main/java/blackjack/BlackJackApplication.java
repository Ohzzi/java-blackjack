package blackjack;

import blackjack.domain.Dealer;
import blackjack.domain.Name;
import blackjack.domain.Player;
import blackjack.domain.Rule;
import blackjack.domain.card.BlackJackCardsGenerator;
import blackjack.domain.card.CardDeck;
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
            List<Name> playerNames = inputPlayerNames();
            List<Player> players = createPlayers(playerNames, deck);
            alertStart(dealer, players);
            proceedPlayersTurn(players, deck);
            proceedDealer(dealer, deck);
            showResult(dealer, players);
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
        while (player.isHittable() && InputView.inputHitRequest(player.getName()).equals("y")) {
            player.hit(deck);
            int score = Rule.INSTANCE.calculateSum(player.getCards());
            OutputView.printParticipantCards(player, score);
        }
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
