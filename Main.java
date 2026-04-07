import java.util.*;

class Student {
    String nama;
    int saldo;

    public Student(String nama) {
        this.nama = nama;
        this.saldo = 0;
    }

    public String getType() {
        return "STUDENT";
    }

    public void save(int jumlah) {
        saldo += jumlah;
        System.out.println("Saldo " + nama + ": " + saldo);
    }

    public void take(int jumlah) {
        if (saldo < jumlah) {
            System.out.println("Saldo " + nama + " tidak cukup");
        } else {
            saldo -= jumlah;
            System.out.println("Saldo " + nama + ": " + saldo);
        }
    }
}

class Regular extends Student {
    public Regular(String nama) {
        super(nama);
    }

    @Override
    public String getType() {
        return "REGULER";
    }
}

class Beasiswa extends Student {
    public Beasiswa(String nama) {
        super(nama);
    }

    @Override
    public String getType() {
        return "BEASISWA";
    }

    @Override
    public void take(int jumlah) {
        if (saldo < jumlah) {
            System.out.println("Saldo " + nama + " tidak cukup");
        } else {
            saldo -= jumlah;
            saldo += 1000; // potongan khusus
            System.out.println("Saldo " + nama + ": " + saldo);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Fardhan Haikal Taufik");
        System.out.println("255150707111008");
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());

        Map<String, Student> data = new HashMap<>();

        for (int i = 0; i < N; i++) {
            String input = sc.nextLine();
            String[] parts = input.split(" ");

            String cmd = parts[0];

            if (cmd.equals("CREATE")) {
                String type = parts[1];
                String nama = parts[2];

                if (data.containsKey(nama)) {
                    System.out.println("Akun sudah terdaftar");
                } else {
                    if (type.equals("REGULER")) {
                        data.put(nama, new Regular(nama));
                    } else {
                        data.put(nama, new Beasiswa(nama));
                    }
                    System.out.println(type + " " + nama + " berhasil dibuat");
                }

            } else if (cmd.equals("SAVE")) {
                String nama = parts[1];
                int jumlah = Integer.parseInt(parts[2]);

                if (!data.containsKey(nama)) {
                    System.out.println("Akun tidak ditemukan");
                } else {
                    data.get(nama).save(jumlah);
                }

            } else if (cmd.equals("TAKE")) {
                String nama = parts[1];
                int jumlah = Integer.parseInt(parts[2]);

                if (!data.containsKey(nama)) {
                    System.out.println("Akun tidak ditemukan");
                } else {
                    data.get(nama).take(jumlah);
                }

            } else if (cmd.equals("CHECK")) {
                String nama = parts[1];

                if (data.containsKey(nama)) {
                    Student s = data.get(nama);
                    System.out.println(nama + " | " + s.getType() + " | saldo: " + s.saldo);
                }
            }
        }

        sc.close();
    }
}