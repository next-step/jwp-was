package model;

public class User {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(Builder builder) {
        this.userId = builder.userId;
        this.password = builder.password;
        this.name = builder.name;
        this.email = builder.email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    public static class Builder {

        private String userId;
        private String password;
        private String name;
        private String email;

        public Builder(){}

        public Builder userId(String val){
            userId = val;
            return this;
        }

        public Builder password(String val){
            password = val;
            return this;
        }

        public Builder name(String val){
            name = val;
            return this;
        }

        public Builder email(String val){
            email = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
