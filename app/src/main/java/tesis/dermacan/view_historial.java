package tesis.dermacan;

public class view_historial {
    private String Hname_pet;
    private String Hfecha_fcder;
    private String Hdiagnostico;
    private String Hruta_imagen;
    private String Hid_pet;
    private String Hid_fcder;
    private String Hid_diag;

    public view_historial(String name_pet, String fecha_fcder, String diagnostico, String ruta_imagen, String id_pet, String id_fcder, String id_diag) {
        this.Hname_pet = name_pet;
        this.Hfecha_fcder = fecha_fcder;
        this.Hdiagnostico = diagnostico;
        this.Hruta_imagen = ruta_imagen;

        this.Hid_pet = id_pet;
        this.Hid_fcder = id_fcder;
        this.Hid_diag = id_diag;
    }

    public String getHName_pet() {
        return Hname_pet;
    }

    public String getHfecha_fcder() {
        return Hfecha_fcder;
    }

    public String getHdiagnostico() {
        return Hdiagnostico;
    }

    public String getHruta_imagen() {return Hruta_imagen;}

    public String getHid_pet() {
        return Hid_pet;
    }
    public String getHid_fcder() {
        return Hid_fcder;
    }

    public String getHid_diag() {
        return Hid_diag;
    }

}
