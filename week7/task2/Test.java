package main;

public class Test {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {

        testMethodChaining();
        testBatteryDecreaseOnPhoto();
        testBatteryDecreaseOnMusic();
        testRuntimePolymorphism();
        testLowBatteryScenario();

        System.out.println("\n================ RESULT ================");
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);

        if (testsFailed == 0) {
            System.out.println("ALL TESTS PASSED");
        } else {
            System.out.println("Some tests failed");
        }
    }


    // ---------- Individual Tests ----------

    private static void testMethodChaining() {
        SmartPhone phone = (SmartPhone) new SmartPhone()
                .setOwner("Alice")
                .charge(100);

        boolean condition =
                phone.getOwner().equals("Alice") &&
                phone.getBatteryLevel() == 100;

        report("Method chaining sets owner and battery", condition);
    }


    private static void testBatteryDecreaseOnPhoto() {
        SmartPhone phone = (SmartPhone) new SmartPhone()
                .setOwner("Bob")
                .charge(50);

        phone.takePhoto();

        boolean condition = phone.getBatteryLevel() == 45;
        report("Taking photo decreases battery by 5", condition);
    }


    private static void testBatteryDecreaseOnMusic() {
        SmartPhone phone = (SmartPhone) new SmartPhone()
                .setOwner("Carol")
                .charge(40);

        phone.playSong();

        boolean condition = phone.getBatteryLevel() == 37;
        report("Playing music decreases battery by 3", condition);
    }


    private static void testRuntimePolymorphism() {
        Tool device = new SmartPhone().setOwner("Dana").charge(30);

        // Should call SmartPhone implementation at runtime
        ((SmartPhone) device).takePhoto();

        boolean condition = device.getBatteryLevel() == 25;
        report("Runtime polymorphism works through Tool reference", condition);
    }


    private static void testLowBatteryScenario() {
        SmartPhone phone = (SmartPhone) new SmartPhone()
                .setOwner("Eve")
                .charge(3);

        phone.takePhoto(); // Should not allow (costs 5%, only 3% available)

        boolean photoCondition = phone.getBatteryLevel() >= 0; // Battery should not go negative

        phone.charge(2); // Now at 2%
        phone.playSong(); // Should not allow (costs 3%, only 2% available)

        boolean songCondition = phone.getBatteryLevel() >= 0; // Battery should not go negative

        report("Low battery prevents negative values", photoCondition && songCondition);
    }


    // ---------- Utility ----------

    private static void report(String testName, boolean passed) {
        if (passed) {
            testsPassed++;
            System.out.println("[PASS] " + testName);
        } else {
            testsFailed++;
            System.out.println("[FAIL] " + testName);
        }
    }
}

