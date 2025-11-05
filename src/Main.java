import core.ProjectScheduler;

import java.util.List;

// main class untuk demonstrasi project scheduler
public class Main {
    private static final String SEPARATOR = "=".repeat(60);
    private static final String LINE = "-".repeat(60);

    public static void main(String[] args) {
        demonstrateValidSchedule();
        System.out.println("\n");
        demonstrateCycleDetection();
    }


    // demonstrasi penjadwalan valid
    private static void demonstrateValidSchedule() {
        printHeader("PENJADWALAN TUGAS PROYEK - TOPOLOGICAL SORT");

        // buat instance projectscheduler
        ProjectScheduler scheduler = new ProjectScheduler();

        // tambahkan tugas-tugas
        System.out.println("Menambahkan tugas ke dalam proyek...");
        scheduler.addTask("Analisis");
        scheduler.addTask("Desain UI");
        scheduler.addTask("Coding Backend");
        scheduler.addTask("Testing");
        scheduler.addTask("Deploy");
        System.out.println("Semua tugas berhasil ditambahkan");
        System.out.println();

        // tambahkan dependensi
        System.out.println("Mengatur dependensi antar tugas...");
        scheduler.addDependency("Analisis", "Desain UI");
        scheduler.addDependency("Analisis", "Coding Backend");
        scheduler.addDependency("Desain UI", "Testing");
        scheduler.addDependency("Coding Backend", "Testing");
        scheduler.addDependency("Testing", "Deploy");

        printDependencies();

        // dapatkan urutan tugas
        System.out.println("Menghitung urutan tugas optimal...");
        List<String> taskOrder = scheduler.getTaskOrder();

        // cetak hasil
        printTaskOrder(taskOrder);
    }

    // demonstrasi deteksi siklus
    private static void demonstrateCycleDetection() {
        printHeader("PENGUJIAN DETEKSI SIKLUS");

        ProjectScheduler schedulerWithCycle = new ProjectScheduler();

        System.out.println("Membuat proyek dengan dependensi sirkular...");
        schedulerWithCycle.addTask("Task A");
        schedulerWithCycle.addTask("Task B");
        schedulerWithCycle.addTask("Task C");

        schedulerWithCycle.addDependency("Task A", "Task B");
        schedulerWithCycle.addDependency("Task B", "Task C");
        schedulerWithCycle.addDependency("Task C", "Task A");

        System.out.println("Dependensi: A → B → C → A (sirkular)");
        System.out.println();

        List<String> cyclicOrder = schedulerWithCycle.getTaskOrder();

        System.out.println(LINE);
        if (cyclicOrder.isEmpty()) {
            System.out.println("Siklus terdeteksi dengan benar - mengembalikan list kosong");
            System.out.println("Penjadwalan tidak mungkin dilakukan dengan dependensi sirkular.");
        } else {
            System.out.println("✗ Error: Seharusnya mendeteksi siklus");
        }
        System.out.println(LINE);
    }

    // cetak header
    private static void printHeader(String title) {
        System.out.println(SEPARATOR);
        System.out.println(title);
        System.out.println(SEPARATOR);
        System.out.println();
    }

    // cetak informasi dependensi
    private static void printDependencies() {
        System.out.println("Dependensi berhasil dikonfigurasi:");
        System.out.println("  - Analisis → Desain UI");
        System.out.println("  - Analisis → Coding Backend");
        System.out.println("  - Desain UI → Testing");
        System.out.println("  - Coding Backend → Testing");
        System.out.println("  - Testing → Deploy");
        System.out.println();
    }

    // cetak urutan eksekusi tugas
    private static void printTaskOrder(List<String> taskOrder) {
        System.out.println(LINE);
        if (taskOrder.isEmpty()) {
            System.out.println("ERROR: Tidak dapat menjadwalkan tugas - terdeteksi dependensi sirkular!");
        } else {
            System.out.println("URUTAN EKSEKUSI TUGAS YANG VALID:");
            System.out.println();
            for (int i = 0; i < taskOrder.size(); i++) {
                System.out.printf("  %d. %s%n", i + 1, taskOrder.get(i));
            }
            System.out.println();
            System.out.println("Urutan tugas: " + taskOrder);
        }
        System.out.println(LINE);
    }
}