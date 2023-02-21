import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MyCalendar {
    private static final Map<Integer, Repeatable> actualTasks = new HashMap<>();

    public static void addTask(Scanner scanner) {
        try {
            scanner.nextLine();
            System.out.println("Введите название задачи: ");
            String title = ValidateUtils.checkString(scanner.nextLine());
            System.out.println("Введите описание задачи: ");
            String description = ValidateUtils.checkString(scanner.nextLine());
            System.out.println(" Введите тип задачи: 0 - Рабочая 1 - Личная");
            TaskType taskType = TaskType.values()[scanner.nextInt()];
            System.out.println("Введите повторяемость задачи: 0 - Однократная, 1 - Ежедневная, 2- Еженедельная, 3 - Ежемесячная, 4 - Ежегодная");
            int occurrence = scanner.nextInt();
            System.out.println("Введите дату dd.MM.yyyy HH:mm ");
            scanner.nextLine();
            createEvent(scanner, title, description, taskType, occurrence);
            System.out.println("Для выхода нажмите Enter\n");
            scanner.nextLine();
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createEvent(Scanner scanner, String title, String description, TaskType taskType, int occurrence) {
        try {
            LocalDateTime eventDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            Repeatable task = null;
            try {
                task = createTask(occurrence, title, description, taskType, eventDate);
                System.out.println("Создана задача " + task);
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Проверьте формат dd.MM.yyyy HH:mm и попробуйте ещё раз");
            createEvent(scanner, title, description, taskType, occurrence);
        }
    }

    public static void editTask(Scanner scanner) {
        try {
            System.out.println("Редактирование задачи: введите id");
            printActualTasks();
            int id = scanner.nextInt();
            if (!actualTasks.containsKey(id)) {
                throw new WrongInputException("Задача не найдена");
            }
            System.out.println("Редактирование 0 - заголовок 1 - описание 2 - тип 3 - дата");
            int menuCase = scanner.nextInt();
            switch (menuCase) {
                case 0 -> {
                    scanner.nextLine();
                    System.out.println("Введите название задачи: ");
                    String title = scanner.nextLine();
                    Repeatable task = actualTasks.get(id);
                    task.setTitle(title);
                }
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("Введите описание задачи: ");
                    String description = scanner.nextLine();
                    Repeatable task = actualTasks.get(id);
                    task.setDescription(description);
                }
                case 2 -> {
                    scanner.nextLine();
                    System.out.println("Введите тип задачи: ");
                    String taskType = scanner.nextLine();
                    Repeatable task = actualTasks.get(id);
                    task.setTaskType(taskType);
                }
                case 3 -> {
                    scanner.nextLine();
                    System.out.println("Введите дату задачи: ");
                    String eventDate = scanner.nextLine();
                    Repeatable task = actualTasks.get(id);
                    task.setEventDate(eventDate);
                }

            }
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTask(Scanner scanner) {
        System.out.println("Текущие задачи\n");
        printActualTasks();
        System.out.println("Для удаления введите id задачи\n");
        int id = scanner.nextInt();
        if (actualTasks.containsKey(id)) {
            actualTasks.remove(id);
            System.out.println("Задача " + id + " удалена\n");
        } else {
            System.out.println("Такой задачи не существует\n");
        }
    }

    public static void getTaskByDay(Scanner scanner) {
        System.out.println("Введите дату как dd.MM.yyyy:");
        try {
            String date = scanner.next();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate requestedDate = LocalDate.parse(date, dateFormatter);
            List<Repeatable> foundEvents = findTasksByDate(requestedDate);
            System.out.println("События на " + requestedDate + ":");
            for (Repeatable task : foundEvents) {
                System.out.println(task);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Проверьте формат даты dd.MM.yyyy и попробуйте ещё раз.");
        }
        scanner.nextLine();
        System.out.println("Для выхода нажмите Enter\n");
    }


    private static Repeatable createTask(int occurrence, String title, String description, TaskType taskType, LocalDateTime localDateTime) throws WrongInputException {
        return switch (occurrence) {
            case 0 -> {
                SingleTask singleTask = new SingleTask(title, description, taskType, localDateTime);
                actualTasks.put(singleTask.getId(), singleTask);
                yield singleTask;
            }
            case 1 -> {
                DailyTask dailyTask = new DailyTask(title, description, taskType, localDateTime);
                actualTasks.put(dailyTask.getId(), dailyTask);
                yield dailyTask;
            }
            case 2 -> {
                WeeklyTask weeklyTask = new WeeklyTask(title, description, taskType, localDateTime);
                actualTasks.put(weeklyTask.getId(), weeklyTask);
                yield weeklyTask;
            }
            case 3 -> {
                MonthlyTask monthlyTask = new MonthlyTask(title, description, taskType, localDateTime);
                actualTasks.put(monthlyTask.getId(), monthlyTask);
                yield monthlyTask;
            }
            case 4 -> {
                AnnualTask annualTask = new AnnualTask(title, description, taskType, localDateTime);
                actualTasks.put(annualTask.getId(), annualTask);
                yield annualTask;
            }
            default -> null;
        };
    }
    private static void printActualTasks(){
        for (Repeatable task: actualTasks.values()){
            System.out.println(task);
        }
    }
    private static List<Repeatable> findTasksByDate(LocalDate date) {
        List<Repeatable> tasks = new ArrayList<>();
        for (Repeatable task : actualTasks.values()) {
            if (task.checkOccurrence(date.atStartOfDay())) {
                tasks.add(task);
            }
        }
        return tasks;
    }
}
