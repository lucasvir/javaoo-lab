package one.digitalinovation.laboojava.entidade.constantes;

public enum Materias {
    M2 (2),
    M5 (5),
    M10 (10);

    private int fator;

    Materias(int fator) {
        this.fator = fator;
    }

    public int getFator() {
        return fator;
    }
}
