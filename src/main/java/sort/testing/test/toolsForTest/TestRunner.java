package src.main.java.sort.testing.test.toolsForTest;

public class TestRunner {
    private int access;
    private int failed;
    public void run(String nameTest,Runnable test){
        try {
            test.run();
            access ++;
            System.out.println("Тест успешно завершен " + nameTest);
        }catch (Throwable error){
            failed++;
            System.out.println("Тест не завершился "+ nameTest + ": " + error.getMessage());
        }
    }
    public void end(){
        System.out.println("Завершенных= "+access);
        System.out.println("Проваленных= "+failed);
    }
}
