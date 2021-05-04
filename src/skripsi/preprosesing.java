/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.io.File;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author us
 */
public class preprosesing {

    String inputFile;
    
    static int id[];
    static int idKelas[]; //berisi data id kelas, mulai dari 0, id ini digunakan sebagai index dalam array
    static int penugasan[][];
    static String namaMapel[];
    static String namaKelas[];
    static int lokasi[]; //index dari lokasi sama dengan index dengan ruangan
    static Integer idGuru[];
    //static int idGuruTugas[] = null;
    static int requestGuru[][];

    //static double fitness[] = null;
    //static int pelanggaran[][] = null;
    //static int tmpPelanggaran[] = null;
    //static int pelanggaranMax[] = null;
    
    void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public static void setId(int[] id) {
        preprosesing.id = id;
    }

    public static void setIdKelas(int[] idKelas) {
        preprosesing.idKelas = idKelas;
    }

    public static void setPenugasan(int[][] penugasan) {
        preprosesing.penugasan = penugasan;
    }

    public static void setNamaMapel(String[] namaMapel) {
        preprosesing.namaMapel = namaMapel;
    }

    public static void setNamaKelas(String[] namaKelas) {
        preprosesing.namaKelas = namaKelas;
    }

    public static void setLokasi(int[] lokasi) {
        preprosesing.lokasi = lokasi;
    }

    public static void setIdGuru(Integer[] idGuru) {
        preprosesing.idGuru = idGuru;
    }

    public static void setRequestGuru(int[][] requestGuru) {
        preprosesing.requestGuru = requestGuru;
    }

    public String getInputFile() {
        return inputFile;
    }

    public static int[] getId() {
        return id;
    }

    public static int[] getIdKelas() {
        return idKelas;
    }

    public static int[][] getPenugasan() {
        return penugasan;
    }

    public static String[] getNamaMapel() {
        return namaMapel;
    }

    public static String[] getNamaKelas() {
        return namaKelas;
    }

    public static int[] getLokasi() {
        return lokasi;
    }

    public static Integer[] getIdGuru() {
        return idGuru;
    }

    public static int[][] getRequestGuru() {
        return requestGuru;
    }

    public preprosesing() throws IOException, BiffException {
        //File excelFile = new File(inputFile);
        File excelFile = new File("kacuk.xls");
        
        Workbook workbook = Workbook.getWorkbook(excelFile);
        Sheet sheet1 = workbook.getSheets()[0];
        Sheet sheet2 = workbook.getSheets()[1];
        Sheet sheet3 = workbook.getSheets()[2];
        Sheet sheet4 = workbook.getSheets()[3];
        Sheet sheet5 = workbook.getSheets()[4];

        id = new int[sheet1.getRows()];

        //for (int column = 0; column < 1; column++) {
        for (int row = 0; row < sheet1.getRows(); row++) {
            String sid = sheet1.getCell(0, row).getContents(); //0 adalah kolom pertama dari sheet yg ditunjuk
            id[row] = Integer.parseInt(sid);
        }
        //}

        idKelas = new int[sheet2.getRows()];
        //for (int column = 0; column < 1; column++) {
        for (int row = 0; row < sheet2.getRows(); row++) {
            String sidKelas = sheet2.getCell(0, row).getContents();
            idKelas[row] = Integer.parseInt(sidKelas);
        }
        //}

        penugasan = new int[sheet5.getRows()][sheet5.getColumns()];
        for (int column = 0; column < 5; column++) {
            for (int row = 0; row < sheet5.getRows(); row++) {
                String spenugasan = sheet5.getCell(column, row).getContents();
                penugasan[row][column] = Integer.parseInt(spenugasan);
                //System.out.println(sheet5.getColumns());
            }
        }

        namaMapel = new String[sheet3.getRows()];
        for (int row = 0; row < sheet3.getRows(); row++) {
            namaMapel[row] = sheet3.getCell(1, row).getContents();
            // = ;
            //System.out.println(sheet5.getColumns());
        }

        namaKelas = new String[sheet2.getRows()];
        for (int row = 0; row < sheet2.getRows(); row++) {
            namaKelas[row] = sheet2.getCell(1, row).getContents();
        }

        lokasi = new int[sheet5.getRows()];
        for (int row = 0; row < sheet5.getRows(); row++) {
            String slokasi = sheet5.getCell(1, row).getContents();
            lokasi[row] = Integer.parseInt(slokasi);
        }


        idGuru = new Integer[sheet5.getRows()];
        for (int row = 0; row < sheet5.getRows(); row++) {
            String sidGuru = sheet5.getCell(4, row).getContents();
            idGuru[row] = Integer.parseInt(sidGuru);
        }

        requestGuru = new int[sheet4.getRows()][sheet5.getColumns()];
        for (int column = 0; column < 2; column++) {
            for (int row = 0; row < sheet4.getRows(); row++) {
                String srequestGuru = sheet4.getCell(column, row).getContents();
                requestGuru[row][column] = Integer.parseInt(srequestGuru);
                //System.out.println(sheet5.getColumns());
            }
        }

    }

    /*public void baca() {
     for (int column = 0; column < 1; column++) {
     for (int row = 0; row < 3; row++) {
     System.out.println(preprosesing.id);
     }
     }
     }*/

    /*public void id() throws IOException, BiffException {
     File fileExcel = new File(fileInput);
     Workbook w;
     try {
     w = Workbook.getWorkbook(fileExcel);

     // Ambil sheet pertama, nomer 0 menandakan sheet ke 1
     Sheet sheet = w.getSheet(0);
     String sid;
     // Looping sebanyak kolom dan baris yang ada
     for (int j = 0; j < 1; j++) {
     for (int i = 0; i < sheet.getRows(); i++) {
     jxl.Cell cell = sheet.getCell(j, i);
     //sid = cell.getContents();
     //id[i] = Integer.parseInt(sid);
     //Jika baris adalah TABEL HEADER
     /*if ((i == 0 && j == 0) || (i == 0 && j == 1)) {
     System.out.println("Isi tabel "
     + cell.getContents());
     } else {
     if (cell.getType() == CellType.LABEL) {
     System.out.println("Ini adalah Label "
     + cell.getContents());
     }

     if (cell.getType() == CellType.NUMBER) {
     System.out.println("Ini adalah Nomor "
     + cell.getContents());
     }
     }
     System.out.println(cell.getType());
     //System.out.println(cell.getContents());
     System.out.println(cell.getContents());
     }
     }
     } catch (BiffException e) {
     e.printStackTrace();
     }
     }*/
    public static void main(String[] args) throws IOException, BiffException {
        preprosesing test = new preprosesing();

        //test.setInputFile("kacuk.xls");
        //test.baca();

        //System.out.println(id[0]);
        /*for (int i = 0; i < 1; i++) {
         for (int j = 0; j < idGuru.length; j++) {
         System.out.println(idGuru[j]);
         }
         System.out.println(idGuru.length);
         }*/

        System.out.println(penugasan.length);

        //tes menampilkan isi penugasan[][]
        /*for (int column = 0; column < 2; column++) {
            System.out.println("Kolom ke-" + (column + 1));
            for (int row = 0; row < requestGuru.length; row++) {
                //System.out.println(sheet5.getColumns());
                System.out.println(requestGuru[row][column]);
            }
        }*/
    }

    
}
