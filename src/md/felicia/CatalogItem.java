package md.felicia;

public class CatalogItem {
    private String codulMedicamentului;
    private int codulVamal;
    private String denumireaComerciala;
    private String formaFarmaceutica;
    private String doza;
    private String volum;
    private String divizarea;
    private String country;
    private String detinatorul;
    private int numarDeInregistrare;
    private String registerDate;
    private String codATC;
    private String denumireaInternationala;
    private int termenDeValabilitate;
    private String barcode;
    private double priceMDL;
    private double priceVal;
    private String currency;
    private String dataAprobarii;
    private String modificari;
    private String modificari2;

    public CatalogItem(String codulMedicamentului, int codulVamal, String denumireaComerciala, String formaFarmaceutica, String doza, String volum, String divizarea, String country, String detinatorul, int numarDeInregistrare, String registerDate, String codATC, String denumireaInternationala, int termenDeValabilitate, String barcode, double priceMDL, double priceVal, String currency, String dataAprobarii, String modificari, String modificari2) {
        this.codulMedicamentului = codulMedicamentului;
        this.codulVamal = codulVamal;
        this.denumireaComerciala = denumireaComerciala;
        this.formaFarmaceutica = formaFarmaceutica;
        this.doza = doza;
        this.volum = volum;
        this.divizarea = divizarea;
        this.country = country;
        this.detinatorul = detinatorul;
        this.numarDeInregistrare = numarDeInregistrare;
        this.registerDate = registerDate;
        this.codATC = codATC;
        this.denumireaInternationala = denumireaInternationala;
        this.termenDeValabilitate = termenDeValabilitate;
        this.barcode = barcode;
        this.priceMDL = priceMDL;
        this.priceVal = priceVal;
        this.currency = currency;
        this.dataAprobarii = dataAprobarii;
        this.modificari = modificari;
        this.modificari2 = modificari2;
    }

    @Override
    public String toString() {
        return "CatalogItem{" +
                "codulMedicamentului='" + codulMedicamentului + '\'' +
                ", codulVamal=" + codulVamal +
                ", denumireaComerciala='" + denumireaComerciala + '\'' +
                ", formaFarmaceutica='" + formaFarmaceutica + '\'' +
                ", doza='" + doza + '\'' +
                ", volum='" + volum + '\'' +
                ", divizarea='" + divizarea + '\'' +
                ", country='" + country + '\'' +
                ", detinatorul='" + detinatorul + '\'' +
                ", numarDeInregistrare=" + numarDeInregistrare +
                ", registerDate=" + registerDate +
                ", codATC='" + codATC + '\'' +
                ", denumireaInternationala='" + denumireaInternationala + '\'' +
                ", termenDeValabilitate=" + termenDeValabilitate +
                ", barcode=" + barcode +
                ", priceMDL=" + priceMDL +
                ", priceVal=" + priceVal +
                ", currency='" + currency + '\'' +
                ", dataAprobarii='" + dataAprobarii + '\'' +
                ", modificari='" + modificari + '\'' +
                ", modificari2='" + modificari2 + '\'' +
                '}';
    }
}

