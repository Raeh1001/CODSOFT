import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class NumberGuessingGameUI extends JFrame {
    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JButton guessButton;
    private JButton playAgainButton;
    private int randomNumber;
    private int attempts;
    private final int maxAttempts = 10;
    private int score;
    private int rounds;

    public NumberGuessingGameUI() {
        setTitle("Number Guessing Game");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        newRound();

        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        messageLabel = new JLabel("Guess a number between 1 and 100", JLabel.CENTER);
        panel.add(messageLabel);

        guessField = new JTextField();
        panel.add(guessField);

        guessField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    guessButton.doClick();
                }
            }
        });

        attemptsLabel = new JLabel("Attempts left: " + maxAttempts, JLabel.CENTER);
        panel.add(attemptsLabel);

        scoreLabel = new JLabel("Rounds Won: 0 | Total Rounds: 0", JLabel.CENTER);
        panel.add(scoreLabel);

        JPanel buttonPanel = new JPanel();
        guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());
        buttonPanel.add(guessButton);

        playAgainButton = new JButton("Play Again");
        playAgainButton.setEnabled(false);
        playAgainButton.addActionListener(new PlayAgainButtonListener());
        buttonPanel.add(playAgainButton);

        panel.add(buttonPanel);

        add(panel);
    }

    private void newRound() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
        attempts = 0;
        rounds++;
        guessField.setText("");
        messageLabel.setText("Guess a number between 1 and 100");
        attemptsLabel.setText("Attempts left: " + maxAttempts);
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Rounds Won: " + score + " | Total Rounds: " + rounds);
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;
                int attemptsLeft = maxAttempts - attempts;

                if (guess == randomNumber) {
                    score++;
                    messageLabel.setText("Congratulations! You guessed the correct number.");
                    guessButton.setEnabled(false);
                    playAgainButton.setEnabled(true);
                } else if (guess < randomNumber) {
                    messageLabel.setText("Too low! Try again.");
                } else {
                    messageLabel.setText("Too high! Try again.");
                }

                if (attemptsLeft > 0) {
                    attemptsLabel.setText("Attempts left: " + attemptsLeft);
                } else {
                    guessButton.setEnabled(false);
                    playAgainButton.setEnabled(true);
                    messageLabel.setText("Sorry, you've run out of attempts! The correct number was " + randomNumber);
                }

                updateScoreLabel();

            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid number.");
            }
        }
    }

    private class PlayAgainButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            newRound();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberGuessingGameUI());
    }
}
