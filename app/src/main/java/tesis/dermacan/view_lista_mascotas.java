package tesis.dermacan;

public class view_lista_mascotas {
    private String name_pet;
    private String age_pet;
    private String raza_pet;
    private String image_pet;

    public view_lista_mascotas(String name_pet, String age_pet, String raza_pet, String image_pet) {
        this.name_pet = name_pet;
        this.age_pet = age_pet;
        this.raza_pet = raza_pet;
        this.image_pet = image_pet;
    }

    public String getName_pet() {
        return name_pet;
    }

    public String getAge_pet() {
        return age_pet;
    }

    public String getRaza_pet() {
        return raza_pet;
    }

    public String getImage_pet() {
        return image_pet;
    }
}
