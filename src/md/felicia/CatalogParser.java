package md.felicia;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class CatalogParser {
    public static String parse(String file) {
        String result = "";
        InputStream in = null;
        HSSFWorkbook wb = null;

        try {
            in = new FileInputStream(file);
            wb = new HSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert wb != null;
        Sheet sheet = wb.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getLastCellNum() == 21 && row.getRowNum() > 0) {
                CatalogItem catalogItem = createItem(row);
                System.out.println(catalogItem);
            } else {
                System.out.println("Wrong file format!");
            }
        }

        System.out.println(result);
        return null;
    }

    public static CatalogItem createItem(Row row) {
        String codulMedicamentului = row.getCell(0).getStringCellValue();
        int codulVamal;
        switch (row.getCell(1).getCellType()) {

            case NUMERIC:
                codulVamal = (int) row.getCell(1).getNumericCellValue();
                break;
            case STRING:
                codulVamal = row.getCell(1).getStringCellValue().equals("") ? 0 : Integer.parseInt(row.getCell(1).getStringCellValue());
                break;
            default:
                codulVamal = 0;
        }
        System.out.println(row.getRowNum());
        String denumireaComerciala = row.getCell(2).getStringCellValue();
        String formaFarmaceutica = row.getCell(3).getStringCellValue();
        String doza = row.getCell(4).getStringCellValue();
        String volum = row.getCell(5).getStringCellValue();
        String divizarea = row.getCell(6).getStringCellValue();
        String country = row.getCell(7).getStringCellValue();
        String detinatorul = row.getCell(8).getStringCellValue();

        int numarDeInregistrare;
        switch (row.getCell(9).getCellType()) {
            case NUMERIC:
                numarDeInregistrare = (int) row.getCell(9).getNumericCellValue();
                break;
            case STRING:
                numarDeInregistrare = Integer.parseInt(row.getCell(9).getStringCellValue());
                break;
            default:
                numarDeInregistrare = Integer.parseInt(row.getCell(9).getStringCellValue());
                break;
        }


        String registerDate = row.getCell(10).getStringCellValue();
        String codATC = row.getCell(11).getStringCellValue();
        String denumireaInternationala = row.getCell(12).getStringCellValue();
        int termenDeValabilitate = (int) row.getCell(13).getNumericCellValue();
        ;
        String barcode = row.getCell(14).getStringCellValue();
        double priceMDL = row.getCell(15).getNumericCellValue();
        ;
        double priceVal = row.getCell(16).getNumericCellValue();
        ;
        String currency = row.getCell(17).getStringCellValue();
        String dataAprobarii = row.getCell(18).getStringCellValue();
        String modificari = row.getCell(19).getStringCellValue();
        String modificari2 = row.getCell(20).getStringCellValue();


        return new CatalogItem(codulMedicamentului, codulVamal, denumireaComerciala, formaFarmaceutica, doza, volum, divizarea, country, detinatorul, numarDeInregistrare,
                registerDate, codATC, denumireaInternationala, termenDeValabilitate, barcode, priceMDL, priceVal, currency, dataAprobarii, modificari, modificari2);
    }
}
