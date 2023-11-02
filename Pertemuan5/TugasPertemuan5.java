import java.util.*;

// Definisi class TugasPertemuan5 yang merepresentasikan simpul dalam algoritma pencarian.
class TugasPertemuan5 {
    int location;          // Lokasi simpul saat ini
    TugasPertemuan5 parent; // Simpul induk (simpul sebelumnya dalam jalur)
    int cost;              // Biaya (jarak) dari titik awal ke simpul saat ini
    int heuristic;         // Nilai heuristik untuk memperkirakan jarak ke tujuan
    int totalCost;         // Biaya total, yaitu cost + heuristic

    public TugasPertemuan5(int location, TugasPertemuan5 parent, int cost, int heuristic) {
        this.location = location;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
        this.totalCost = cost + heuristic;
    }
}

public class Main {
    public static List<Integer> aStarSearch(int initialLocation, int goalLocation, int[][] cityMap, int[] heuristicValues) {
        PriorityQueue<TugasPertemuan5> openList = new PriorityQueue<>(Comparator.comparingInt(node -> node.totalCost));
        Set<Integer> closedSet = new HashSet<>();

        // Membuat simpul awal dengan lokasi awal.
        TugasPertemuan5 startNode = new TugasPertemuan5(initialLocation, null, 0, heuristicValues[initialLocation]);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            TugasPertemuan5 currentNode = openList.poll();

            if (currentNode.location == goalLocation) {
                return reconstructPath(currentNode); // Jika sampai ke tujuan, kembalikan jalur.
            }

            closedSet.add(currentNode.location);

            for (int neighbor : getNeighbors(currentNode.location, cityMap)) {
                if (closedSet.contains(neighbor)) {
                    continue; // Skip jika tetangga sudah pernah dikunjungi.
                }

                int neighborCost = currentNode.cost + cityMap[currentNode.location][neighbor];
                int neighborHeuristic = heuristicValues[neighbor];
                // Buat simpul tetangga dan tambahkan ke openList.
                TugasPertemuan5 neighborNode = new TugasPertemuan5(neighbor, currentNode, neighborCost, neighborHeuristic);
                openList.add(neighborNode);
            }
        }

        return null; // Jika jalur tidak ditemukan, kembalikan null.
    }

    // Fungsi untuk merekonstruksi jalur dari simpul terakhir ke simpul awal.
    public static List<Integer> reconstructPath(TugasPertemuan5 node) {
        List<Integer> path = new ArrayList<>();
        while (node != null) {
            path.add(node.location);
            node = node.parent;
        }
        Collections.reverse(path); // Balik jalur agar dimulai dari awal ke tujuan.
        return path;
    }

    // Fungsi untuk mendapatkan tetangga dari lokasi tertentu dalam peta kota.
    public static List<Integer> getNeighbors(int location, int[][] cityMap) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < cityMap[location].length; i++) {
            if (cityMap[location][i] > 0) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    public static void main(String[] args) {
        int initialLocation = 0; // Lokasi awal
        int goalLocation = 4;   // Lokasi tujuan
        int[][] cityMap = {
            {0, 2, 0, 0, 3},
            {2, 0, 4, 0, 0},
            {0, 4, 0, 1, 0},
            {0, 0, 1, 0, 5},
            {3, 0, 0, 5, 0}
        };
        int[] heuristicValues = {8, 6, 5, 2, 0}; // Nilai heuristik untuk setiap lokasi

        // Melakukan pencarian menggunakan algoritma A*.
        List<Integer> aStarPath = aStarSearch(initialLocation, goalLocation, cityMap, heuristicValues);
        if (aStarPath != null) {
            System.out.println("A* Path: " + aStarPath);
        } else {
            System.out.println("No path found using A*.");
        }
    }
}
