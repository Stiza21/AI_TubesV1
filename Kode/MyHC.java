import java.util.*;

public class MyHC {

    /**
     * Menghasilkan solusi awal acak yang valid untuk penempatan stasiun pemadam
     * kebakaran.
     * Solusi ini memastikan setiap stasiun pemadam kebakaran ditempatkan di lokasi
     * yang valid
     * (yaitu, tidak di atas rumah atau pohon). Solusi awal ini berfungsi sebagai
     * titik awal untuk proses pencarian Hill Climbing.
     *
     * @param map Grid 2D yang mewakili lingkungan (rumah, pohon, tanah kosong)
     * @param p   Jumlah stasiun pemadam kebakaran yang akan ditempatkan
     * @return Array yang mewakili koordinat stasiun pemadam kebakaran sebagai
     *         solusi awal
     */
    static Random rng; //global var untuk mengambil seed random
    static int seedUsed; //agar rng bisa diubah menjadi integer untuk output

    static House[] generateInitialSolution(int[][] layout, int p, int seed) {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[0].length; j++) {
                if (layout[i][j] == 0)
                    emptyCells.add(new int[] { i, j });
            }
        }
        seedUsed = seed;
        rng = new Random(seed);
        Collections.shuffle(emptyCells, rng);
        House[] stations = new House[p];
        for (int k = 0; k < p; k++) {
            int[] pos = emptyCells.get(k);
            stations[k] = new House(pos[0], pos[1]);
        }
        return stations;
    }

    /**
     * Menghasilkan semua solusi tetangga yang valid dari solusi saat ini.
     * Solusi tetangga diperoleh dengan memindahkan satu stasiun pemadam kebakaran
     * satu langkah ke salah satu
     * dari empat arah (atas, bawah, kiri, kanan) tanpa tumpang tindih
     * dengan petak yang tidak valid (seperti pohon atau rumah).
     *
     * @param currentSolution Penempatan stasiun pemadam kebakaran saat ini
     * @param map             Grid 2D yang mewakili lingkungan
     * @return Daftar solusi tetangga yang dihasilkan dari solusi saat ini
     */

    static List<House[]> generateNeighbors(House[] currentStations, int[][] layout) {
        List<House[]> neighbors = new ArrayList<>();
        int[][] moves = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }; // 4-arah perpindahan

        for (int i = 0; i < currentStations.length; i++) {
            for (int[] move : moves) {
                int newX = currentStations[i].xCoordinate + move[0];
                int newY = currentStations[i].yCoordinate + move[1];
                if (newX >= 0 && newX < layout.length && newY >= 0 && newY < layout[0].length) {
                    if (layout[newX][newY] == 0) { // empty cell only
                        House[] neighbor = new House[currentStations.length];
                        for (int k = 0; k < currentStations.length; k++) {
                            neighbor[k] = new House(currentStations[k].xCoordinate, currentStations[k].yCoordinate);
                        }
                        neighbor[i] = new House(newX, newY);
                        neighbors.add(neighbor);
                    }
                }
            }
        }
        return neighbors;
    }

    // Method Hill Climbing baru

    /**
     * Hill Climbing
     * 
     * @param map     Grid 2D yang mewakili lingkungan
     * @param p       Jumlah stasiun pemadam kebakaran
     * @param maxIter Jumlah iterasi maksimum yang diizinkan
     * @param seed    Seed 
     * @return Solusi terbaik yang ditemukan (posisi stasiun pemadam kebakaran)
     *         dengan nilai fitness tertinggi
     */

    static House[] hillClimbing(Fitness fitness, int[][] layout, int p, int maxIter, int seed) {
        House[] current = generateInitialSolution(layout, p, seed);
        double bestScore = fitness.f(current);

        for (int iter = 0; iter < maxIter; iter++) {
            List<House[]> neighbors = generateNeighbors(current, layout);
            boolean improved = false;

            for (House[] neighbor : neighbors) {
                double score = fitness.f(neighbor);
                if (score < bestScore) {
                    bestScore = score;
                    current = neighbor;
                    improved = true;
                    break;
                }
            }
            if (!improved)
                break; // local minimum
        }
        return current;
    }

    // Method random restart baru

    /**
     * @param map       Peta grid 2D yang mewakili lingkungan
     * @param p         Jumlah stasiun pemadam kebakaran
     * @param nRestarts Jumlah restart acak yang akan dilakukan
     * @param maxIter   Jumlah iterasi maksimum per eksekusi Hill Climbing
     * @return Solusi terbaik secara keseluruhan yang ditemukan dari semua restart
     */

    static House[] randomRestartHC(int nRestarts, Fitness fitness, int[][] layout, int p, int maxIter, Random seedRandomizer) {
        House[] bestSolution = null;
        double bestScore = Double.MAX_VALUE;
        
        for (int r = 0; r < nRestarts; r++) {
            int seed = seedRandomizer.nextInt(100000); //jika ingin manual seeds ubah disini 
            House[] solution = hillClimbing(fitness, layout, p, maxIter, seed);
            double score = fitness.f(solution);
            if (score < bestScore) {
                bestScore = score;
                bestSolution = solution;
            }
        }
        return bestSolution;
    }

    public static void main(String[] args) {
        // Random-restart Hill climbing
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt(); // panjang map
        int n = sc.nextInt(); // lebar map
        int p = sc.nextInt(); // banyak fire station
        int h = sc.nextInt(); // banyak rumah
        int t = sc.nextInt(); // banyak pohon
        
        House[] house = new House[h]; // inisialisasi array rumah
        int[][] map = new int[m][n];
        for (int i = 0; i < h; i++) { // masukan informasi koordinat rumah
            int posX = sc.nextInt();
            int posY = sc.nextInt();
            house[i] = new House(posX - 1, posY - 1);
            map[posX - 1][posY - 1] = 1;
        }
        for (int i = 0; i < t; i++) { // masukan informasi koordinat pohon
            int posX = sc.nextInt();
            int posY = sc.nextInt();
            map[posX - 1][posY - 1] = 2;
        }
        Fitness fitnessVal = new Fitness(map, house);
        Random seedRandomizer = new Random(); //pilih seed secara random dengan rentang seperti pada parameter (random)
        House[] bestStations = randomRestartHC(20, fitnessVal, map, p, Integer.parseInt(args[0]), seedRandomizer);//random
        double totalDist = 0.0;
        for (House home : house) {
            int minDist = Integer.MAX_VALUE;
            for (House fireStation : bestStations) {
                int d = fitnessVal.distances[Arrays.asList(house).indexOf(home)][fireStation.xCoordinate][fireStation.yCoordinate];
                if (d < minDist)
                    minDist = d;
            }
            totalDist += minDist;
        }
        
        double avgDist = totalDist / h; //cari rata2 terdekat untuk jarak rumah dengan fire station
        System.out.println("Seed : " + seedUsed);
        System.out.printf("%d %.5f%n", p, avgDist); //output jumlah firestation dan rata2 jarak dengan rumah 
        for (House fireStation : bestStations) {
            System.out.printf("%d %d%n", fireStation.xCoordinate + 1, fireStation.yCoordinate + 1);//output koordinat dengan firestation
        }

    }
}