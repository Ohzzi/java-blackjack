package blackjack;

import blackjack.domain.Dealer;
import blackjack.domain.Name;
import blackjack.domain.Player;
import blackjack.domain.card.BlackJackCardsGenerator;
import blackjack.domain.card.CardDeck;
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
}
