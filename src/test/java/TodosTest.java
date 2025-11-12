import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodosTest {

    @Test
    void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.findAll();
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldSearchTasks() {
        Todos todos = new Todos();

        todos.add(new SimpleTask(1, "Купить хлеб"));
        todos.add(new Epic(2, new String[]{"Сходить в магазин", "Приготовить ужин"}));
        todos.add(new Meeting(3, "Обсуждение хлеба", "Проект Хлеб", "Завтра"));

        Task[] result = todos.search("хлеб");

        Task[] expected = {
                new SimpleTask(1, "Купить хлеб"),
                new Meeting(3, "Обсуждение хлеба", "Проект Хлеб", "Завтра")
        };

        assertArrayEquals(expected, result);
    }
}