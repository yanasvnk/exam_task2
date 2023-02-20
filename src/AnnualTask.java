import java.time.LocalDateTime;

public class AnnualTask extends Task implements Repeatable{
    public AnnualTask(String title, String description, TaskType taskType, LocalDateTime date) throws WrongInputException {
        super(title, description, taskType, date);
    }

    @Override
    public boolean checkOccurrence(LocalDateTime requestedDate) {
        return getFirstDate().getYear()==requestedDate.getYear();
    }
}
