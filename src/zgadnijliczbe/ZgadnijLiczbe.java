package zgadnijliczbe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZgadnijLiczbe extends JFrame {
    private int randomNumber;
    private JTextField inputNumber;
    private JLabel messageLabel;
    private JLabel titleLabel;
    private int maxNumber = 100;
    private JLabel hintLabel;
    private int attempts;
    private int wins = 0;
    private List<Integer> guessHistory = new ArrayList<>();
    private JTextArea historyArea;
    private JScrollPane scrollPane;

    public ZgadnijLiczbe() {
        setTitle("Zgadnij liczbę");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 1));
        getContentPane().setBackground(new Color(144, 238, 144));

        titleLabel = new JLabel("Zgadnij liczbę (1-" + maxNumber + ")", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        inputNumber = new JTextField();
        inputNumber.setHorizontalAlignment(JTextField.CENTER);
        inputNumber.addActionListener(e -> checkGuess());
        add(inputNumber);

        messageLabel = new JLabel("Wpisz liczbę i naciśnij Enter", SwingConstants.CENTER);
        add(messageLabel);

        JButton changeMaxNumber = new JButton("Inny zakres liczb");
        changeMaxNumber.addActionListener(e -> showRangeOptions());
        add(changeMaxNumber);

        hintLabel = new JLabel("", SwingConstants.CENTER);
        hintLabel.setVisible(false);
        add(hintLabel);

        JCheckBox hintCheckBox = new JCheckBox("Pokaż podpowiedź");
        hintCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        hintCheckBox.addActionListener(e -> hintLabel.setVisible(hintCheckBox.isSelected()));
        add(hintCheckBox);

        JCheckBox historyCheckBox = new JCheckBox("Wyświetl historię zgadywania");
        historyCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        historyCheckBox.addActionListener(e -> historyArea.setVisible(historyCheckBox.isSelected()));
        add(historyCheckBox);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setVisible(false);
        scrollPane = new JScrollPane(historyArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVisible(false);
        add(scrollPane);

        randomNumber = new Random().nextInt(maxNumber) + 1;
        System.out.println("Wylosowana liczba: " + randomNumber);

        setVisible(true);
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(inputNumber.getText());
            attempts++;
            guessHistory.add(guess);
            updateHistory();

            if (guess < 1 || guess > maxNumber) {
                messageLabel.setText("Liczba musi być w zakresie 1-" + maxNumber + "!");
            } else if (guess < randomNumber) {
                messageLabel.setText("Za mało! Spróbuj ponownie. Próba: " + attempts);
            } else if (guess > randomNumber) {
                messageLabel.setText("Za dużo! Spróbuj ponownie. Próba: " + attempts);
            } else {
                wins++;
                messageLabel.setText("Gratulacje! Trafiłeś w " + attempts + " próbach!");
                JOptionPane.showMessageDialog(this, "Brawo! Zgadłeś liczbę " + randomNumber + " w " + attempts + " próbach!");
                resetGame();
            }

            if (hintLabel.isVisible()) {
                updateHint(guess);
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Wpisz poprawną liczbę!");
        }
        inputNumber.setText("");
    }

    private void updateHistory() {
        StringBuilder historyText = new StringBuilder("Twoje zgadywania:\n");
        for (int guess : guessHistory) {
            historyText.append(guess).append("\n");
        }
        historyArea.setText(historyText.toString());
        scrollPane.setVisible(true);
    }

    private void resetGame() {
        randomNumber = new Random().nextInt(maxNumber) + 1;
        System.out.println("Wylosowana liczba: " + randomNumber);
        messageLabel.setText("Wpisz liczbę i naciśnij Enter");
        titleLabel.setText("Zgadnij liczbę (1-" + maxNumber + ")");
        guessHistory.clear();
        historyArea.setText("");
    }

    private void updateHint(int guess) {
        int difference = Math.abs(randomNumber - guess);
        if (difference == 0) {
            hintLabel.setText("");
        } else if (difference <= 5) {
            hintLabel.setText("Bardzo ciepło!");
        } else if (difference <= 15) {
            hintLabel.setText("Ciepło!");
        } else {
            hintLabel.setText("Zimno!");
        }
    }

    private void showRangeOptions() {
        JDialog newNumberDialog = new JDialog(this, "Wybierz zakres", true);
        newNumberDialog.setSize(200, 250);
        newNumberDialog.setLayout(new GridLayout(4, 1));

        JButton maxNumber50 = new JButton("1-50");
        JButton maxNumber100 = new JButton("1-100");
        JButton maxNumber150 = new JButton("1-150");
        JButton maxNumber200 = new JButton("1-200");

        maxNumber50.addActionListener(e -> setNewMaxNumber(50, newNumberDialog));
        maxNumber100.addActionListener(e -> setNewMaxNumber(100, newNumberDialog));
        maxNumber150.addActionListener(e -> setNewMaxNumber(150, newNumberDialog));
        maxNumber200.addActionListener(e -> setNewMaxNumber(200, newNumberDialog));

        newNumberDialog.add(maxNumber50);
        newNumberDialog.add(maxNumber100);
        newNumberDialog.add(maxNumber150);
        newNumberDialog.add(maxNumber200);

        newNumberDialog.setLocationRelativeTo(this);
        newNumberDialog.setVisible(true);
    }

    private void setNewMaxNumber(int newMax, JDialog dialog) {
        this.maxNumber = newMax;
        setTitle("Zgadnij liczbę (1-" + maxNumber + ")");
        dialog.dispose();
        resetGame();
    }

    public static void main(String[] args) {
        new ZgadnijLiczbe();
    }
}
