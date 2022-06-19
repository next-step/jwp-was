package service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.User;

public class Fixture {
    private Fixture() {}

    public static List<User> createUsers() {
        return IntStream.range(1, 10)
                .mapToObj(number -> new User(
                        "userId" + number,
                        "password" + number,
                        "name" + number,
                        "email" + number
                )).collect(Collectors.toList());
    }
}
