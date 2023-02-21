import java.time.LocalDateTime;

public interface Repeatable {
    boolean checkOccurrence(LocalDateTime localDateTime);
    void setTitle(String title);
    LocalDateTime getFirstDate();

    void setDescription(String description);

    void setTaskType(String taskType);

    void setEventDate(String eventDate);
}
