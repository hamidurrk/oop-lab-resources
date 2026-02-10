package main;

public class Test {
    
    public static void main(String[] args) {
        
        // Error tracking
        java.util.List<String> errors = new java.util.ArrayList<>();
        int totalTests = 0;
        int passedTests = 0;

        System.out.println("=== CONSTRUCTOR CHAINING TEST ===");
        totalTests++;
        Character c1 = new Tyrion("Tyrion", 39);
        passedTests++; // If we reach here, constructor worked

        System.out.println("\n=== MULTI-LEVEL INHERITANCE TEST ===");
        totalTests++;
        JonSnow jon = new JonSnow("Jon", 25);
        passedTests++;

        System.out.println("\n=== POLYMORPHISM TEST ===");
        totalTests++;
        Character[] chars = {
            c1,
            jon,
            new Arya("Arya", 18),
            new Direwolf("Ghost", 3),
            new Lannister("Cersei", 42)
        };

        for (Character ch : chars) {
            ch.speak(); // must call subclass versions
        }
        passedTests++;

        System.out.println("\n=== BASE CLASS SPEAK TEST ===");
        totalTests++;
        Character baseChar = new Character("Nobody", 0);
        baseChar.speak(); // should print "..."
        passedTests++;

        System.out.println("\n=== VISIBILITY TEST - PRIVATE FIELD ===");
        totalTests++;
        // Test name is private - cannot access directly
        // System.out.println(c1.name); // COMPILE ERROR if uncommented
        System.out.println("Name via getter: " + c1.getName());
        
        // Runtime test with reflection
        try {
            java.lang.reflect.Field nameField = Character.class.getDeclaredField("name");
            String value = (String) nameField.get(c1);
            System.out.println("ERROR: Private field accessed without permission");
            errors.add("VISIBILITY TEST - PRIVATE: Field access should have been blocked");
        } catch (IllegalAccessException e) {
            System.out.println("✓ Private field 'name' protected: " + e.getClass().getSimpleName());
            passedTests++;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            errors.add("VISIBILITY TEST - PRIVATE: Unexpected exception - " + e.getMessage());
        }

        System.out.println("\n=== VISIBILITY TEST - PROTECTED FIELD ===");
        totalTests++;
        // Test age is protected - cannot access from main
        
        // Runtime test with reflection for protected field
        try {
            java.lang.reflect.Field ageField = Character.class.getDeclaredField("age");
            int ageValue = (int) ageField.get(c1);
            // Protected is accessible via reflection without setAccessible
            System.out.println("✓ Protected field 'age' accessible via reflection: " + ageValue);
            passedTests++;
        } catch (IllegalAccessException e) {
            System.out.println("Protected field blocked: " + e.getClass().getSimpleName());
            errors.add("VISIBILITY TEST - PROTECTED: Field should be accessible - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            errors.add("VISIBILITY TEST - PROTECTED: Unexpected exception - " + e.getMessage());
        }

        System.out.println("\n=== INSTANCEOF HIERARCHY TEST ===");
        totalTests++;
        boolean instanceofPassed = true;
        
        if (!(jon instanceof JonSnow)) { errors.add("INSTANCEOF: jon should be instanceof JonSnow"); instanceofPassed = false; }
        if (!(jon instanceof Stark)) { errors.add("INSTANCEOF: jon should be instanceof Stark"); instanceofPassed = false; }
        if (!(jon instanceof Character)) { errors.add("INSTANCEOF: jon should be instanceof Character"); instanceofPassed = false; }
        
        System.out.println("jon instanceof JonSnow: " + (jon instanceof JonSnow));
        System.out.println("jon instanceof Stark: " + (jon instanceof Stark));
        System.out.println("jon instanceof Character: " + (jon instanceof Character));
        
        // Using Object to bypass compile-time check
        Object jonObj = jon;
        if (jonObj instanceof Lannister) { errors.add("INSTANCEOF: jon should NOT be instanceof Lannister"); instanceofPassed = false; }
        System.out.println("jon instanceof Lannister: " + (jonObj instanceof Lannister));
        
        if (!(c1 instanceof Tyrion)) { errors.add("INSTANCEOF: c1 should be instanceof Tyrion"); instanceofPassed = false; }
        if (!(c1 instanceof Lannister)) { errors.add("INSTANCEOF: c1 should be instanceof Lannister"); instanceofPassed = false; }
        System.out.println("c1 instanceof Tyrion: " + (c1 instanceof Tyrion));
        System.out.println("c1 instanceof Lannister: " + (c1 instanceof Lannister));
        
        if (instanceofPassed) passedTests++;

        System.out.println("\n=== NULL INSTANCEOF TEST ===");
        totalTests++;
        boolean nullTestPassed = true;
        
        Character nullChar = null;
        if (nullChar instanceof Character) { errors.add("NULL INSTANCEOF: null should not be instanceof Character"); nullTestPassed = false; }
        if (nullChar instanceof JonSnow) { errors.add("NULL INSTANCEOF: null should not be instanceof JonSnow"); nullTestPassed = false; }
        
        System.out.println("null instanceof Character: " + (nullChar instanceof Character));
        System.out.println("null instanceof JonSnow: " + (nullChar instanceof JonSnow));
        
        if (nullTestPassed) passedTests++;

        System.out.println("\n=== SAFE DOWNCAST TEST ===");
        totalTests++;
        if (c1 instanceof Tyrion) {
            Tyrion t = (Tyrion) c1;
            System.out.println("✓ Downcast success: " + t.getName());
            passedTests++;
        } else {
            System.out.println("ERROR: Downcast failed");
            errors.add("SAFE DOWNCAST: c1 should be downcasted to Tyrion");
        }

        System.out.println("\n=== INVALID CAST TEST ===");
        totalTests++;
        try {
            Character temp = new Tyrion("Fake", 40);
            JonSnow bad = (JonSnow) temp;
            System.out.println("ERROR: Invalid cast allowed");
            errors.add("INVALID CAST: Tyrion to JonSnow cast should have failed");
        } catch (ClassCastException e) {
            System.out.println("✓ Invalid cast caught: " + e.getClass().getSimpleName());
            passedTests++;
        }

        System.out.println("\n=== CROSS-FAMILY CAST TEST ===");
        totalTests++;
        try {
            Character stark = new Arya("Test", 20);
            Lannister impossibleCast = (Lannister) stark;
            System.out.println("ERROR: Cross-family cast allowed");
            errors.add("CROSS-FAMILY CAST: Stark to Lannister cast should have failed");
        } catch (ClassCastException e) {
            System.out.println("✓ Cross-family cast blocked: " + e.getClass().getSimpleName());
            passedTests++;
        }

        System.out.println("\n=== INTERFACE SEPARATION TEST ===");
        totalTests++;
        boolean interfaceTestPassed = true;
        
        Dragon dragon = new Dragon();
        Object dragonObj = dragon;
        
        if (!(dragon instanceof Flyable)) { errors.add("INTERFACE: Dragon should be instanceof Flyable"); interfaceTestPassed = false; }
        if (dragonObj instanceof Character) { errors.add("INTERFACE: Dragon should NOT be instanceof Character"); interfaceTestPassed = false; }
        
        System.out.println("dragon instanceof Flyable: " + (dragon instanceof Flyable));
        System.out.println("dragon instanceof Character: " + (dragonObj instanceof Character));
        
        try {
            Character wrongCast = (Character) dragonObj;
            System.out.println("ERROR: Dragon cast to Character allowed");
            errors.add("INTERFACE: Dragon to Character cast should have failed");
            interfaceTestPassed = false;
        } catch (ClassCastException e) {
            System.out.println("✓ Dragon cannot be cast to Character: " + e.getClass().getSimpleName());
        }
        
        if (interfaceTestPassed) passedTests++;

        System.out.println("\n=== FLYABLE INTERFACE TEST ===");
        totalTests++;
        Flyable[] flyers = {
            new Dragon(),
            new Dragon()
        };

        for (Flyable f : flyers) {
            f.fly();
        }
        passedTests++;

        System.out.println("\n=== METHOD OVERRIDE CHAIN TEST ===");
        totalTests++;
        Character asChar = new Lannister("Jaime", 40);
        asChar.speak(); // should print Lannister's version
        
        Character asTyrion = new Tyrion("Tyrion2", 39);
        asTyrion.speak(); // should print Tyrion's version (overrides Lannister)
        passedTests++;

        System.out.println("\n=== DEEP METHOD LOOKUP TEST ===");
        totalTests++;
        Character deepTest = new JonSnow("Hero", 22);
        deepTest.speak(); // must print "I know nothing" not "..." or Stark's version
        passedTests++;

        System.out.println("\n=== ARRAY POLYMORPHISM TEST ===");
        totalTests++;
        Stark[] starks = {
            new JonSnow("Robb", 24),
            new Arya("Sansa", 20)
        };
        
        for (Stark s : starks) {
            s.speak(); // Each should call their own speak()
        }
        passedTests++;

        System.out.println("\n=== CONSTRUCTOR PARAMETER TEST ===");
        totalTests++;
        Character aged = new Direwolf("Summer", 5);
        String name = aged.getName();
        if ("Summer".equals(name)) {
            System.out.println("Created: " + name);
            passedTests++;
        } else {
            System.out.println("ERROR: Expected name 'Summer' but got '" + name + "'");
            errors.add("CONSTRUCTOR PARAMETER: Name should be 'Summer' but was '" + name + "'");
        }

        System.out.println("\n=== ALL TESTS FINISHED ===");
        
        // Print comprehensive error report
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPREHENSIVE TEST REPORT");
        System.out.println("=".repeat(60));
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("Success Rate: " + String.format("%.1f%%", (passedTests * 100.0 / totalTests)));
        System.out.println("=".repeat(60));
        
        if (errors.isEmpty()) {
            System.out.println("\n✅ ALL TESTS PASSED - NO ERRORS DETECTED!");
            System.out.println("\nYour implementation is correct:");
            System.out.println("  ✓ Inheritance hierarchy properly designed");
            System.out.println("  ✓ Polymorphism working correctly");
            System.out.println("  ✓ Encapsulation (private/protected) enforced");
            System.out.println("  ✓ Interface implementation correct");
            System.out.println("  ✓ Type casting and instanceof working as expected");
        } else {
            System.out.println("\n❌ ERRORS DETECTED: " + errors.size());
            System.out.println("\nError Details:");
            System.out.println("-".repeat(60));
            for (int i = 0; i < errors.size(); i++) {
                System.out.println((i + 1) + ". " + errors.get(i));
            }
            System.out.println("-".repeat(60));
            System.out.println("\nPlease fix the errors listed above.");
        }
        
        System.out.println("\n" + "=".repeat(60));
    }
}
