import java.util.*;

abstract class Vehicle {
    String kode;
    String nama;
    int harga;
    boolean tersedia;

    public Vehicle(String kode, String nama, int harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.tersedia = true;
    }

    abstract String getType();

    public int calculateRental(int days, boolean promo) {
        int total = harga * days;
        if (promo) {
            if (getType().equals("CAR")) {
                total -= 20000;
            } else if (getType().equals("BIKE")) {
                total -= 10000;
            }
        }
        return Math.max(total, 0);
    }

    public String getStatus() {
        return tersedia ? "TERSEDIA" : "DISEWA";
    }
}

class Car extends Vehicle {
    public Car(String code, String name, int price) {
        super(code, name, price);
    }

    @Override
    String getType() {
        return "CAR";
    }
}

class Bike extends Vehicle {
    public Bike(String code, String name, int price) {
        super(code, name, price);
    }

    @Override
    String getType() {
        return "BIKE";
    }
}

public class VehicleRental {
    static Map<String, Vehicle> vehicles = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Fardhan Haikal TTaufik");
        System.out.println("255150707111008");
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < N; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            switch (parts[0]) {
                case "ADD":
                    addVehicle(parts);
                    break;

                case "RENT":
                    rentalVehicle(parts);
                    break;

                case "RETURN":
                    returnVehicle(parts[1]);
                    break;

                case "DETAIL":
                    detailVehicle(parts[1]);
                    break;

                case "COUNT":
                    System.out.println("Total kendaraan: " + vehicles.size());
                    break;
            }
        }
    }

    static void addVehicle(String[] parts) {
        String type = parts[1];
        String code = parts[2];
        String name = parts[3];
        int price = Integer.parseInt(parts[4]);

        if (vehicles.containsKey(code)) {
            System.out.println("Kode kendaraan sudah ada");
            return;
        }

        Vehicle v;
        if (type.equals("CAR")) {
            v = new Car(code, name, price);
        } else {
            v = new Bike(code, name, price);
        }

        vehicles.put(code, v);
        System.out.println(type + " " + code + " telah ditambahkan");
    }

    static void rentalVehicle(String[] parts) {
        String code = parts[1];
        int days = Integer.parseInt(parts[2]);
        boolean promo = parts.length == 4 && parts[3].equals("PROMO");

        if (!vehicles.containsKey(code)) {
            System.out.println("Kendaraan tidak ditemukan");
            return;
        }

        Vehicle v = vehicles.get(code);

        if (!v.tersedia) {
            System.out.println("Kendaraan agi disewa");
            return;
        }

        int total = v.calculateRental(days, promo);
        v.tersedia = false;

        System.out.println("Total sewa " + code + ": " + total);
    }

    static void returnVehicle(String code) {
        if (!vehicles.containsKey(code)) {
            System.out.println("Kendaraan nda ditemukan");
            return;
        }

        Vehicle v = vehicles.get(code);

        if (v.tersedia) {
            System.out.println("Kendaraan beyum disewa");
            return;
        }

        v.tersedia = true;
        System.out.println(code + " berhasil dikembalikan");
    }

    static void detailVehicle(String code) {
        if (!vehicles.containsKey(code)) {
            System.out.println("Kendaraan nda ditemukan");
            return;
        }

        Vehicle v = vehicles.get(code);
        System.out.println(code + " | " + v.getType() + " | " + v.nama +
                " | harga: " + v.harga + " | status: " + v.getStatus());
    }
}