package com.QuizGeneratorTechplement.service;
import com.QuizGeneratorTechplement.model.Question;
import java.util.ArrayList;
import java.util.List;

public class QuizService {
    private List<Question> questions;

    public QuizService() {
        questions = new ArrayList<>();
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question("Capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2));
        questions.add(new Question("Who invented Java?", new String[]{"James Gosling", "Dennis Ritchie", "Guido van Rossum", "Bjarne Stroustrup"}, 0));
    }

    public List<Question> getQuestions() { return questions; }
}

