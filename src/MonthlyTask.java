import java.time.LocalDateTime;

public class MonthlyTask extends Task implements Repeatable{
    public MonthlyTask(String title, String description, TaskType taskType, LocalDateTime date) throws WrongInputException {
        super(title, description, taskType, date);
    }

    @Override
    public boolean checkOccurrence(LocalDateTime requestedDate) {
        return getFirstDate().getMonth().equals(requestedDate.getMonth());
    }

    @Override
    public void setTaskType(String taskType) {

    }

    @Override
    public void setEventDate(String eventDate) {

    }
}
