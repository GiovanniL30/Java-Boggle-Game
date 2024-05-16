package Client_Java.view.panels;

import App.GamePlayer;
import Client_Java.controller.ClientController;
import Client_Java.utilities.ClientViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FilledButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameSummary extends JPanel {

    private JLabel winnerLabel = new JLabel();

    public GameSummary(ClientController clientController, GamePlayer[] gamePlayers, int currentRound, boolean roundEnd) {
        winnerLabel.setFont(FontFactory.newPoppinsBold(20));
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ColorFactory.beige());

        FilledButton button = new FilledButton("Return to Home Page", new Dimension(100, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.white);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        Arrays.sort(gamePlayers, (o1, o2) -> Integer.compare(o2.score, o1.score));

        Map<Integer, LinkedList<GamePlayer>> groupedByScore = new HashMap<>();

        for (GamePlayer gamePlayer : gamePlayers) {
            groupedByScore.computeIfAbsent(gamePlayer.score, k -> new LinkedList<>()).add(gamePlayer);
        }

        int rank = 1;
        LinkedList<LinkedList<GamePlayer>> sortedPlayers = sortPlayers(groupedByScore);

        List<String[]> tableData = new ArrayList<>();

        for (LinkedList<GamePlayer> players : sortedPlayers) {

            String playersWithScores = players.stream()
                    .map(player -> player.user.userName + " (" + player.score + ")")
                    .collect(Collectors.joining(", "));

            String prefix = (roundEnd ? (rank == 1 ? (players.size() >1 ? "Tie: " : "Game winner: ")  : rank+"") : (rank == 1 ?  (players.size() > 1 ? "Tie: " : "Round " + currentRound + " winner: ") : rank+""));

            String result = prefix + (rank == 1 ? "" : ". ") + playersWithScores;

            if (rank == 1) {
                winnerLabel.setText(result);
            } else {
                tableData.add(new String[]{result});
            }
            rank++;
        }

        String[] columnNames = {"Players"};
        DefaultTableModel model = new DefaultTableModel(tableData.toArray(new String[0][]), columnNames);
        JTable otherPlayersTable = new JTable(model);
        otherPlayersTable.setFont(FontFactory.newPoppinsDefault(15));
        otherPlayersTable.setPreferredScrollableViewportSize(new Dimension(400, 100));
        otherPlayersTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(otherPlayersTable);
        tableScrollPane.setSize(new Dimension(400, 100));

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(winnerLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        if (!tableData.isEmpty()) {
            tableScrollPane.setBorder(null);
            contentPanel.add(tableScrollPane);
        }

        if (roundEnd) {
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(button);
            button.addActionListener(e -> clientController.changeFrame(ClientViews.HOME_PAGE));
        }

        contentPanel.add(Box.createVerticalGlue());
        add(contentPanel, BorderLayout.CENTER);
    }

    public static LinkedList<LinkedList<GamePlayer>> sortPlayers(Map<Integer, LinkedList<GamePlayer>> playerScores) {
        LinkedList<Integer> sortedScores = new LinkedList<>();
        LinkedList<LinkedList<GamePlayer>> gamePlayersSorted = new LinkedList<>();
        playerScores.forEach((score, gamePlayers) -> sortedScores.add(score));
        sortedScores.sort(Comparator.reverseOrder());
        sortedScores.forEach(score -> gamePlayersSorted.add(playerScores.get(score)));
        return gamePlayersSorted;
    }
}
