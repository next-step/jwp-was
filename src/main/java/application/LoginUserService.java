package application;

import db.DataBase;
import model.User;

import java.util.Optional;

public class LoginUserService {
    public static final String LOGINED_FLAG = "logined";

    private static Optional<User> findUserById(String userId) {
        User user = DataBase.findUserById(userId);

        if (user == null || user.getUserId() == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public static boolean isPossibleLogin(LoginUserCommand loginUserCommand) {
        Optional<User> optionalUser = findUserById(loginUserCommand.getUserId());

        return optionalUser.isPresent() && optionalUser.get().getPassword().equals(loginUserCommand.getPassword());
    }
}
