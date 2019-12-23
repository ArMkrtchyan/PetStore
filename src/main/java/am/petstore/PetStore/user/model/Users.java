package am.petstore.PetStore.user.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Users {
    private List<UserResponse> users;

    public List<UserResponse> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        users.sort(Comparator.comparing(UserResponse::getId));
        this.users = users;
    }

}
