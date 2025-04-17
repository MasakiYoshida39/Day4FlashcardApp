import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class FlashcardApp {
    private static int currentIndex = 0;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlashcardApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("単語帳アプリ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        ArrayList<Flashcard> cards = new ArrayList<>();

        JTextField wordField = new JTextField();
        JTextField meaningField = new JTextField();
        JButton addButton = new JButton("カードを追加");

        JLabel displayLabel = new JLabel("ここに単語が表示されます", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton showAnswerButton = new JButton("答えを見る");
        JButton nextButton = new JButton("次へ");

        addButton.addActionListener(e -> {
            String word = wordField.getText();
            String meaning = meaningField.getText();
            if (!word.isEmpty() && !meaning.isEmpty()) {
                cards.add(new Flashcard(word, meaning));
                wordField.setText("");
                meaningField.setText("");
                JOptionPane.showMessageDialog(frame, "カードを追加しました！");

                // 新しいカード追加後に最初のカードを表示
                currentIndex = 0;  // 最初のカードに戻す
                displayLabel.setText(cards.get(currentIndex).word);
            } else {
                JOptionPane.showMessageDialog(frame, "単語と意味の両方を入力してください。");
            }
        });

        showAnswerButton.addActionListener(e -> {
            if (!cards.isEmpty()) {
                displayLabel.setText(cards.get(currentIndex).meaning);
            }
        });

        nextButton.addActionListener(e -> {
            if (!cards.isEmpty()) {
                currentIndex = (currentIndex + 1) % cards.size();
                displayLabel.setText(cards.get(currentIndex).word);
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("単語："));
        inputPanel.add(wordField);
        inputPanel.add(new JLabel("意味："));
        inputPanel.add(meaningField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(showAnswerButton);
        buttonPanel.add(nextButton);

        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(displayLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    static class Flashcard {
        String word;
        String meaning;

        Flashcard(String word, String meaning) {
            this.word = word;
            this.meaning = meaning;
        }
    }
}
