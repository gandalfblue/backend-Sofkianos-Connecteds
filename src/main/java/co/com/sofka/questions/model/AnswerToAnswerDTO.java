package co.com.sofka.questions.model;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * AnswerDTO class.
 * DTO para la colección AnswerToAnswer
 */
public class AnswerToAnswerDTO {

    private String id;
    @NotBlank(message = "Debe existir el userId para este objeto")
    private String userId;
    @NotBlank(message = "Debe existir el userName para este objeto")
    private String userName;
    @NotBlank
    private String answerId;
    @NotBlank
    private String answer;
    private Integer position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnswerToAnswerDTO(String id, @NotBlank String userId, @NotBlank String userName, @NotBlank String answerId, @NotBlank String answer, Integer position, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.answerId = answerId;
        this.answer = answer;
        this.position = position;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public AnswerToAnswerDTO() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AnswerToAnswerDTO answerToAnswerDTO = (AnswerToAnswerDTO) o;
        return Objects.equals(userId, answerToAnswerDTO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
