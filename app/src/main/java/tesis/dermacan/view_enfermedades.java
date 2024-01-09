package tesis.dermacan;

public class view_enfermedades {
    int imageID;
    String enfermedad, descripción, contagio, contagio_a, contagio_b, sintomas, sintoma_1, sintoma_2, sintoma_3, sintoma_4, sintoma_5, sintoma_6, sintoma_7;

    public view_enfermedades(int imageID, String enfermedad, String descripción, String contagio, String contagio_a, String contagio_b, String sintomas, String sintoma_1, String sintoma_2, String sintoma_3, String sintoma_4, String sintoma_5, String sintoma_6, String sintoma_7) {
        this.imageID = imageID;
        this.enfermedad = enfermedad;
        this.descripción = descripción;
        this.contagio = contagio;
        this.contagio_a = contagio_a;
        this.contagio_b = contagio_b;
        this.sintomas = sintomas;
        this.sintoma_1 = sintoma_1;
        this.sintoma_2 = sintoma_2;
        this.sintoma_3 = sintoma_3;
        this.sintoma_4 = sintoma_4;
        this.sintoma_5 = sintoma_5;
        this.sintoma_6 = sintoma_6;
        this.sintoma_7 = sintoma_7;
    }
}
