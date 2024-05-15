package Client_Java.view.panels;

import App.GamePlayer;
import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.ClientViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FilledButton;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.util.*;
import java.util.stream.Collectors;


public class GameSummary extends JPanel {

    private JLabel winnerLabel = new JLabel();

    public GameSummary(ClientController clientController, GamePlayer[] gamePlayers, int currentRound,  boolean roundEnd) {

        winnerLabel.setFont(FontFactory.newPoppinsBold(20));
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ColorFactory.beige());

        FilledButton button = new FilledButton("Return to Home Page", new Dimension(100, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.white);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel otherPlayers = new JPanel();
        otherPlayers.setLayout(new BoxLayout(otherPlayers, BoxLayout.Y_AXIS));

        Arrays.sort(gamePlayers, (o1, o2) -> Integer.compare(o2.score, o1.score));
        Map<Integer, LinkedList<GamePlayer>> groupedByScore = new HashMap<>();

        for (GamePlayer gamePlayer : gamePlayers) {
            groupedByScore.computeIfAbsent(gamePlayer.score, k -> new LinkedList<>()).add(gamePlayer);
        }

        int rank = 1;


        for (Map.Entry<Integer, LinkedList<GamePlayer>> entry : groupedByScore.entrySet()) {
            StringBuilder result = new StringBuilder();
            LinkedList<GamePlayer> players = entry.getValue();
            String playersWithScores = players.stream()
                    .map(player -> player.user.userName + " (" + player.score + ")")
                    .collect(Collectors.joining(", "));

            result.append(roundEnd ? "Game Winner " : rank == 1 ? "Round " + currentRound + " Winner" : "").append(rank == 1 ? "" : rank).append(". ").append(playersWithScores);
            if(rank == 1) {
                winnerLabel.setText(result.toString());
            }else {
                JLabel otherPlayer = new JLabel(result.toString());
               otherPlayer.setFont(FontFactory.newPoppinsDefault(14));
               otherPlayers.add(otherPlayer);
            }

            rank++;
        }

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(winnerLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        if(otherPlayers.getComponentCount() > 0) {
            JScrollPane scrollPane = new JScrollPane(otherPlayers);
            scrollPane.setBorder(null);
            contentPanel.add(scrollPane);
        }

        if(roundEnd) {
            contentPanel.add(button);

            button.addActionListener(e -> clientController.changeFrame(ClientViews.HOME_PAGE));
        }

        contentPanel.add(Box.createVerticalGlue());
        add(contentPanel, BorderLayout.CENTER);
    }


}
