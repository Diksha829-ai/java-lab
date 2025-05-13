import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class JavaBasicQuizGUI extends JFrame {
    private String[] questions = {
        "1. What is the default value of an int variable in Java?",
        "2. Which keyword is used to create a constant in Java?",
        "3.What is the parent class of all classes in Java?",
        "4. Which method is used to start a thread in Java?",
        "5. Which of the following declares an array of integers?"
    };

    private String[][] options = {
        {"A. 0.0", "B. null", "C. 0", "D. undefined"},
        {"A. static", "B. const", "C. final", "D. immutable"},
        {"A. Object", "B. Class", "C. Main", "D. Base"},
        {"A. run()", "B. start()", "C. execute()", "D. init()"},
        {"A. int arr[] = new int[5];", "B. int arr = new int[5];", "C. int arr(5);", "D. array int arr;"}
    };

    Color DarkP = new Color(84,9,218);
    Color Light = new Color(78,113,255);

    private String[] correctAnswers = {"C", "C", "A", "B", "A"};
    private int currentQuestion = 0;
    private int score = 0;
    private int timeLeft = 10;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private JLabel timerLabel;
    private JButton nextButton;

    private Timer timer;

    public JavaBasicQuizGUI() {
        setTitle("Java Basics Quiz");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        timerLabel = new JLabel("Time left: 10 seconds");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(timerLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 1));
        centerPanel.setBackground(Light); 
        centerPanel.setOpaque(true);

        questionLabel = new JLabel("Question comes here");
        questionLabel.setBackground(DarkP);
        questionLabel.setOpaque(true); 
        questionLabel.setForeground(Color.white);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        centerPanel.add(questionLabel);

        optionButtons = new JRadioButton[4];
        
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setOpaque(false);
            optionButtons[i].setFont(new Font("Arial", Font.BOLD, 22));
            optionButtons[i].setForeground(Color.white);
            optionButtons[i].setFocusable(false);
            optionButtons[i].setBorderPainted(false);
            optionButtons[i].setContentAreaFilled(false);
            optionGroup.add(optionButtons[i]);
            centerPanel.add(optionButtons[i]);
        }

        add(centerPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.setBackground(DarkP);
        nextButton.setForeground(Color.white);
        nextButton.setFont(new Font("Arial", Font.PLAIN, 20));
        nextButton.setFocusable(false);
        add(nextButton, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> submitAnswer());

        displayQuestion();
        startTimer();
    }

    private void displayQuestion() {
        questionLabel.setText("<html>" + questions[currentQuestion].replace("\n", "<br>") + "</html>");
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[currentQuestion][i]);
            optionButtons[i].setSelected(false);
        }
    }

    private void submitAnswer() {
        timer.cancel();
        String selected = null;
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                selected = optionButtons[i].getText().substring(0, 1);
                break;
            }
        }

        if (selected != null && selected.equalsIgnoreCase(correctAnswers[currentQuestion])) {
            score++;
        }

        currentQuestion++;
        if (currentQuestion < questions.length) {
            displayQuestion();
            startTimer();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        timeLeft = 10;
        timerLabel.setText("Time left: 10 seconds");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + " seconds");
                if (timeLeft <= 0) {
                    timer.cancel();
                    JOptionPane.showMessageDialog(null, "Time's up!");
                    submitAnswer();
                }
            }
        }, 1000, 1000);
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "You scored " + score + " out of " + questions.length);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JavaBasicQuizGUI quiz = new JavaBasicQuizGUI();
            quiz.setVisible(true);
        });
    }
}