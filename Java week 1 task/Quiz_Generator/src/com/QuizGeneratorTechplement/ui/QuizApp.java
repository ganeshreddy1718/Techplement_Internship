package com.QuizGeneratorTechplement.ui;
import com.QuizGeneratorTechplement.model.Question;
import com.QuizGeneratorTechplement.service.QuizService;
import java.util.List;
import java.util.Scanner;

public class QuizApp {
    public static void main(String[] args) {
        QuizService quizService = new QuizService();
        List<Question> questions = quizService.getQuestions();
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        for (Question q : questions) {
            System.out.println(q.getQuestion());
            String[] options = q.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            System.out.print("Your answer: ");
            int userAnswer = scanner.nextInt() - 1;

            if (userAnswer == q.getCorrectAnswerIndex()) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong! Correct answer: " + options[q.getCorrectAnswerIndex()] + "\n");
            }
        }

        System.out.println("Quiz Finished! Score: " + score + "/" + questions.size());
        scanner.close();
    }
}

