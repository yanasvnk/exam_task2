import java.time.LocalDateTime;

public interface Repeatable {
    boolean checkOccurrence(LocalDateTime localDateTime);
    void setTitle(String title);
    LocalDateTime getFirstDate();
}
