import java.util.*;

// Base class for all vehicles
abstract class Vehicle {
    String idKode;      // maybe should rename later?
    String namaKendaraan;
    int hargaSewa;
    boolean isAvailable;

    public Vehicle(String kode, String nama, int harga) {
        this.idKode = kode;
        this.namaKendaraan = nama;
        this.hargaSewa = harga;
        this.isAvailable = true; // default: ready to rent
    }

    // each vehicle must define its own type
    abstract String getType();

    // calculate total rental price
    public int hitungBiaya(int hari, boolean pakaiPromo) {
        int totalHarga = hargaSewa * hari;

        // coba kasih diskon kalau promo aktif
        if (pakaiPromo) {
            String tipe = getType(); // simpan dulu biar gak manggil berulang
            if (tipe.equals("CAR")) {
                totalHarga = totalHarga - 20000;
            } else if (tipe.equals("BIKE")) {
                totalHarga = totalHarga - 10000;
            }
        }

        // just in case total jadi minus (harusnya sih enggak)
        if (totalHarga < 0) {
            totalHarga = 0;
        }

        return totalHarga;
    }

    // status kendaraan sekarang
    public String getStatusKendaraan() {
        if (isAvailable) {
            return "TERSEDIA";
        } else {
            return "DISEWA";
        }
    }
}

// class mobil
class Car extends Vehicle {

    public Car(String kode, String nama, int harga) {
        super(kode, nama, harga);
    }

    @Override
    String getType() {
        return "CAR"; // hardcoded dulu aja
    }
}

// class motor
class Bike extends Vehicle {

    public Bike(String kode, String nama, int harga) {
        super(kode, nama, harga);
    }

    @Override
    String getType() {
        return "BIKE";
    }
}

public class VehicleRental {

    // pakai hashmap buat simpan data kendaraan
    static Map<String, Vehicle> dataKendaraan = new HashMap<>();

    public static void main(String[] args) {

        // identity (kayaknya buat tugas?)
        System.out.println("Fardhan Haikal TTaufik");
        System.out.println("255150707111008");

        Scanner input = new Scanner(System.in);

        int jumlahPerintah = Integer.parseInt(input.nextLine());

        // loop semua command
        for (int i = 0; i < jumlahPerintah; i++) {

            String raw = input.nextLine();
            String[] cmd = raw.split(" ");

            String aksi = cmd[0]; // ambil command utama

            // sempet kepikiran pakai if-else, tapi ya switch aja lah
            switch (aksi) {

                case "ADD":
                    handleAdd(cmd);
                    break;

                case "RENT":
                    handleSewa(cmd);
                    break;

                case "RETURN":
                    handleReturn(cmd[1]);
                    break;

                case "DETAIL":
                    showDetail(cmd[1]);
                    break;

                case "COUNT":
                    System.out.println("Total kendaraan: " + dataKendaraan.size());
                    break;

                default:
                    // ignore aja kalau command aneh
                    // System.out.println("Unknown command");
                    break;
            }
        }

        // lupa close scanner? yaudahlah
    }

    static void handleAdd(String[] data) {
        String tipe = data[1];
        String kode = data[2];
        String nama = data[3];
        int harga = Integer.parseInt(data[4]);

        if (dataKendaraan.containsKey(kode)) {
            System.out.println("Kode kendaraan sudah ada");
            return;
        }

        Vehicle kendaraanBaru;

        // bikin object sesuai tipe
        if (tipe.equals("CAR")) {
            kendaraanBaru = new Car(kode, nama, harga);
        } else {
            // default ke BIKE (asumsi input valid sih)
            kendaraanBaru = new Bike(kode, nama, harga);
        }

        dataKendaraan.put(kode, kendaraanBaru);

        System.out.println(tipe + " " + kode + " berhasil ditambahkan");
    }

    static void handleSewa(String[] data) {
        String kode = data[1];
        int lamaHari = Integer.parseInt(data[2]);

        boolean isPromo = false;
        if (data.length == 4) {
            if (data[3].equals("PROMO")) {
                isPromo = true;
            }
        }

        if (!dataKendaraan.containsKey(kode)) {
            System.out.println("Kendaraan tidak ditemukan");
            return;
        }

        Vehicle v = dataKendaraan.get(kode);

        if (!v.isAvailable) {
            System.out.println("Kendaraan lagi disewa"); // typo diperbaiki dikit
            return;
        }

        int totalBayar = v.hitungBiaya(lamaHari, isPromo);

        // set jadi tidak tersedia
        v.isAvailable = false;

        System.out.println("Total sewa " + kode + ": " + totalBayar);
    }

    static void handleReturn(String kode) {

        Vehicle v = dataKendaraan.get(kode);

        // cek dulu ada atau enggak
        if (v == null) {
            System.out.println("Kendaraan tidak ditemukan");
            return;
        }

        if (v.isAvailable) {
            System.out.println("Kendaraan belum disewa");
            return;
        }

        v.isAvailable = true;

        System.out.println(kode + " sudah dikembalikan");
    }

    static void showDetail(String kode) {

        if (!dataKendaraan.containsKey(kode)) {
            System.out.println("Kendaraan tidak ditemukan");
            return;
        }

        Vehicle v = dataKendaraan.get(kode);

        // format output (masih manual banget sih)
        String output = kode + " | " + v.getType() + " | " + v.namaKendaraan
                + " | harga: " + v.hargaSewa
                + " | status: " + v.getStatusKendaraan();

        System.out.println(output);
    }
}