package slipp.service;

import slipp.db.DataBase;
import slipp.exception.AuthenticationFailException;
import slipp.model.User;

public class AuthenticateService {

    public User authenticate(String userId, String password) {
        return DataBase.findUserById(userId)
                .filter(user -> user.equalsPassword(password))
                .orElseThrow(() -> new AuthenticationFailException("입력하신 정보와 일치하는 회원정보가 존재하지 않습니다."));
    }
}
