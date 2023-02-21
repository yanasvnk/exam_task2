import java.time.LocalDateTime;

public class SingleTask extends Task implements Repeatable{
    public SingleTask(String title, String description, TaskType taskType, LocalDateTime date) throws WrongInputException {
        super(title, description, taskType, date);
    }

    @Override
    public boolean checkOccurrence(LocalDateTime requestedDate) {
        return getFirstDate().toLocalDate().equals(requestedDate.toLocalDate());
    }

    @Override
    public void setTaskType(String taskType) {

    }

    @Override
    public void setEventDate(String eventDate) {

    }
}
