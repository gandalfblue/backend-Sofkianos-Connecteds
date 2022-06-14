package co.com.sofka.questions.model;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * QuestionDTO class.
 * DTO para la colección Question
 */
public class QuestionDTO {
    private String id;
    @NotBlank
    private String userId;
    @NotBlank(message = "Debe existir el userName para este objeto")
    private String userName;
    @NotBlank
    private String question;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    private List<AnswerDTO> answers;

    public QuestionDTO() {

    }

    public QuestionDTO(String userId, String userName, String question, String description, String category) {
        this.userId = userId;
        this.userName = userName;
        this.question = question;
        this.description = description;
        this.category = category;
    }

    public QuestionDTO(String id, String userId, String userName, String question, String description, String category) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.question = question;
        this.description = description;
        this.category = category;
    }

    public List<AnswerDTO> getAnswers() {
        this.answers = Optional.ofNullable(answers).orElse(new ArrayList<>());
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", question='" + question + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QuestionDTO that = (QuestionDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
