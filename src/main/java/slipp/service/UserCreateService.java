package slipp.service;

import slipp.db.DataBase;
import slipp.exception.UserDuplicationException;
import slipp.model.User;

public class UserCreateService {

    public void addUser(User user) {
        String userId = user.getUserId();
        if (DataBase.exists(userId)) {
            throw new UserDuplicationException("이미 존재하는 회원 ID입니다. userId: " + userId);
        }

        DataBase.addUser(user);
    }
}
