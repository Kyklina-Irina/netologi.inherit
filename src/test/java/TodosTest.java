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

    // Тест 1: поиск находит несколько задач
    @Test
    void shouldFindMultipleTasksWhenSearching() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Купить хлеб"));
        todos.add(new Epic(2, new String[]{"Позвонить маме", "Купить молоко"}));
        todos.add(new Meeting(3, "Обсуждение хлеба", "Проект Хлеб", "Завтра"));

        Task[] result = todos.search("Купить");

        Task[] expected = {
                new SimpleTask(1, "Купить хлеб"),
                new Epic(2, new String[]{"Позвонить маме", "Купить молоко"})
        };

        assertArrayEquals(expected, result);
    }

    // Тест 2: поиск находит ровно одну задачу
    @Test
    void shouldFindExactlyOneTaskWhenSearching() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(10, "Прочитать книгу"));
        todos.add(new Epic(20, new String[]{"Помыть посуду", "Погулять с собакой"}));
        todos.add(new Meeting(30, "Планирование", "Проект Alpha", "Среда"));

        Task[] result = todos.search("книгу");

        Task[] expected = {
                new SimpleTask(10, "Прочитать книгу")
        };

        assertArrayEquals(expected, result);
    }

    // Тест 3: поиск ничего не находит
    @Test
    void shouldFindNoTasksWhenSearching() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Купить хлеб"));
        todos.add(new Epic(2, new String[]{"Позвонить другу"}));
        todos.add(new Meeting(3, "Ретроспектива", "Проект Beta", "Пятница"));

        Task[] result = todos.search("несуществующий запрос");

        Task[] expected = new Task[0];

        assertArrayEquals(expected, result);
    }
}