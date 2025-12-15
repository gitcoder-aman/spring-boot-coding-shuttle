package com.tech.module2;

import java.util.List;
import java.util.stream.Stream;

//Extra Class : Java 8 Lambda And Stream API
public class MainClass {
    public static void main(String[] args) {
//        Walkable obj = new WalkFast();
//        obj.walk(4);

//        Walkable obj = new Walkable() {
//            @Override
//            public int walk(int steps) {
//                return 0;
//            }
//        };
        //lambda function
        Walkable obj = (int steps)->{
            System.out.println("Walking fast "+steps+" steps.");
            return 2*steps;
        };
        obj.walk(10);

        //Stream

        List<String> fruits = List.of("Apple", "Kiwi", "Banana");
        Stream<String> stream = fruits.stream();

        stream
                .sorted()
                .map(fruit->fruit.length())
                .forEach((fruit) -> System.out.println(fruit));

    }
}

interface Walkable {
    int walk(int steps);
}

class WalkFast implements Walkable {

    @Override
    public int walk(int steps) {
        System.out.println("Walking fast " + steps + " steps.");
        return 2 * steps;
    }
}
