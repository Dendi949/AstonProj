package src.main.java.sort.testing.test.toolsForTest;

import java.util.Objects;

public final class Assertion {

    public Assertion() {
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + ". Ожидалось: " + expected + ", получено: " + actual
            );
        }
    }

    public static void assertThrows(Class<? extends Throwable> expectedException, Runnable action, String message) {
        try {
            action.run();
        } catch (Throwable actualException) {
            if (expectedException.isInstance(actualException)) {
                return;
            }

            throw new AssertionError(
                    message
                            + ". Получено исключение: "
                            + actualException.getClass().getName(),
                    actualException
            );
        }

        throw new AssertionError(
                message + ". Исключений не было"
        );
    }
}
