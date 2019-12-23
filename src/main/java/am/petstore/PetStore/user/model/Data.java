package am.petstore.PetStore.user.model;

import java.io.Serializable;

public class Data implements Serializable {
    private RegisterModel data;

    public RegisterModel getData() {
        return data;
    }

    public void setData(RegisterModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=" + data +
                '}';
    }
}
