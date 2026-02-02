package main;

public class App 
{
    public static void main(String[] args) {

        Address address = new Address("Finland", "Helsinki");

        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Tesla", "Model 3");
        Car[] cars = {car1, car2};

        Dog dog1 = new Dog();
        Dog dog2 = new Dog();
        Dog[] dogs = {dog1, dog2};

        Human human = new Human(dogs, address, cars);

        dog1.owner = human;
        dog2.owner = human;

        human.breathe();
        human.reproduce();

        dog1.lovesHumans();

        Tiger tiger = new Tiger();
        tiger.attacksHumans();

        System.out.println("\nHuman lives in: " 
            + human.address.city + ", " + human.address.country);

        System.out.println("Human owns " + human.cars.length + " cars:");
        for (Car car : human.cars) {
            System.out.println("- " + car.make + " " + car.model);
        }

        System.out.println("Human owns " + human.dogs.length + " dogs.");
        System.out.println("Dog's owner is a Human: " + (dog1.owner == human));
    }
}
