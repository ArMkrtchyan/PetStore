package am.petstore.PetStore.pets.model;

import am.petstore.PetStore.pets.entity.PetEntity;

public class Pet {
    private Long id;
    private String title;
    private String photo;

    public Pet() {
    }

    public Pet(Long id, String name, String photo) {
        this.id = id;
        this.title = name;
        this.photo = photo;
    }

    public Pet(PetEntity petEntity) {
        this.id = petEntity.getId();
        this.title = petEntity.getTitle();
        this.photo = petEntity.getPhoto();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
