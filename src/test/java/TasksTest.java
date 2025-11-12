import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TasksTest {

    @Test
    void testSimpleTaskMatches() {
        SimpleTask task = new SimpleTask(1, "Купить молоко");
        assertTrue(task.matches("молоко"));
        assertFalse(task.matches("хлеб"));
    }

    @Test
    void testEpicMatches() {
        String[] subtasks = {"Сделать дз", "Позвонить маме", "Прочитать книгу"};
        Epic epic = new Epic(2, subtasks);
        assertTrue(epic.matches("дз"));
        assertTrue(epic.matches("маме"));
        assertFalse(epic.matches("спорт"));
    }

    @Test
    void testMeetingMatches() {
        Meeting meeting = new Meeting(3, "Релиз", "Проект Альфа", "Понедельник");
        assertTrue(meeting.matches("Релиз"));
        assertTrue(meeting.matches("Альфа"));
        assertFalse(meeting.matches("Бета"));
        assertFalse(meeting.matches("Понедельник")); // start не участвует в поиске
    }

    @Test
    void shouldFindMultipleTasks() {
        Todos todos = new Todos();

        SimpleTask task1 = new SimpleTask(1, "Купить молоко");
        Epic task2 = new Epic(2, new String[]{"Купить хлеб", "Сварить кофе"});
        Meeting task3 = new Meeting(3, "Обсуждение молока", "Проект Dairy", "Понедельник");

        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("Купить");

        Task[] expected = {
                new SimpleTask(1, "Купить молоко"),
                new Epic(2, new String[]{"Купить хлеб", "Сварить кофе"})
        };

        assertArrayEquals(expected, result);
    }

    @Test
    void shouldFindExactlyOneTask() {
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

    @Test
    void shouldFindNoTasks() {
        Todos todos = new Todos();

        todos.add(new SimpleTask(1, "Купить хлеб"));
        todos.add(new Epic(2, new String[]{"Позвонить другу"}));
        todos.add(new Meeting(3, "Ретроспектива", "Проект Beta", "Пятница"));

        Task[] result = todos.search("несуществующий запрос");

        Task[] expected = new Task[0];

        assertArrayEquals(expected, result);
    }
}