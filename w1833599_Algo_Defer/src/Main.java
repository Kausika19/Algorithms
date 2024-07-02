import java.util.*;

//Graph's edge class
class Edge
{
    int source, finalDest;
    double weight;

    public Edge(int source, int finalDest, double weight)
    {
        this.source = source;
        this.finalDest = finalDest;
        this.weight = weight;
    }
}

//Heap node class
class Node
{
    int vertex;
    double weight;

    public Node(int vertex, double weight)
    {
        this.vertex = vertex;
        this.weight = weight;
    }
}

//Represent a graph object in a class
class GraphN
{
    //Adjacency list
    List<List<Edge>> adjList = null;

    //Constructor
    GraphN(List<Edge> edges, int n)
    {
        adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        //Add edges to the directed graph
        for (Edge edge: edges) {
            adjList.get((int) edge.source).add(edge);
        }
    }
}

//A class for hashmap
class StationNameMap<K, V> extends HashMap<K, V>
{
    Map<V, K> reverseMap = new HashMap<>();

    @Override
    public V put(K key, V value)
    {
        reverseMap.put(value, key);
        return super.put(key, value);
    }

    //to get the key by value (for input purpose)
    public K getKey(V value) {
        return reverseMap.get(value);
    }

}

class Main {
    private static void getRoute(int[] prev, int i, List<Integer> route) {
        if (i >= 0) {
            getRoute(prev, prev[i], route);
            route.add(i);
        }
    }

    //Dijkstra’s Shortest Path algorithm
    public static void findShortestPaths(GraphN graph, int source, int destination, int n, Map<Integer, String> stations) {
        long start = System.currentTimeMillis();

        //Creating a heap and separate source node having weight 0
        PriorityQueue<Node> minHeap;
        minHeap = new PriorityQueue<>(Comparator.comparingDouble(node -> node.weight));
        minHeap.add(new Node(source, 0));

        //Set initial weight to source to destination as Maxvalue(infinity)
        List<Double> time;
        time = new ArrayList<>(Collections.nCopies(n, (double) Integer.MAX_VALUE));

        //Time for source to source is 0
        time.set(source, (double) 0);

        //Boolean array to track vertices for which is already visited
        boolean[] visited = new boolean[n];
        visited[source] = true;

        //Stores predecessor
        int[] prev = new int[n];
        prev[source] = -1;

        //Till min-heap is empty the code will execute
        while (!minHeap.isEmpty()) {
            //Remove and return the head of the minHeap queue
            Node node = minHeap.poll();

            //Get the vertex number
            int u = node.vertex;

            for (Edge edge : graph.adjList.get(u)) {
                int v = edge.finalDest;
                double weight = edge.weight;

                //Setting the time with the proper condition
                if (!visited[v] && (time.get(u) + weight) < time.get(v)) {
                    time.set(v, (time.get(u) + weight));
                    prev[v] = u;
                    minHeap.add(new Node(v, time.get(v)));
                }
            }

            //Mark vertex u as visited, so it won't get picked up again
            visited[u] = true;
        }

        List<Integer> route = new ArrayList<>();

        boolean b = false;
        for (int i = 0; i <= destination; i++) {
            if (i != source && time.get(i) != Integer.MAX_VALUE && i == destination) {
                getRoute(prev, i, route);

                System.out.println();
                System.out.print("Route: " + stations.get(source) + " -> " + stations.get(i) + " : ");
                System.out.println("Minimum time = " + String.format("%.2f", time.get(i)) + " minutes");
                System.out.println();
                System.out.println("Route => ");
                //to print the route

                for (int r = 0; r < (route.size() - 1); r++) {
                    int a = route.get(r);
                    System.out.println("(" + (r + 1) + ") " + stations.get(a) + " -> " + stations.get(route.get(r + 1)) + " : " + String.format("%.2f", (time.get(route.get(r + 1)) - time.get(a))) + " minutes");
                }
                System.out.println();
                System.out.println("Total Journey Time: " + String.format("%.2f", time.get(i)) + " minutes");
                route.clear();
                b = true;
            }
        }if(b==false) {
            System.out.println("--------Entered stations doesn't have a route!--------");
        }
        long now = System.currentTimeMillis();
        double elapsedSecs = (now-start);
        System.out.println();
        System.out.println("Algorithm execution time: " + elapsedSecs + " ms");
    }

    public static void main(String[] args) {
        StationNameMap<Integer, String> stations = new StationNameMap();
        stations.put(0, "BAKER STREET");
        stations.put(1, "REGENTS PARK");
        stations.put(2, "OXFORD CIRCUS");
        stations.put(3, "PICCADILLY CIRCUS");
        stations.put(4, "CHARING CROSS");
        stations.put(5, "EMBANKMENT");
        stations.put(6, "NOTTING HILL GATE");
        stations.put(7, "QUEENSWAY");
        stations.put(8, "LANCASTER GATE");
        stations.put(9, "MARBLE ARCH");
        stations.put(10, "BOND STREET");
        stations.put(11, "TOTTENHAM COURT ROAD");
        stations.put(12, "HOLBORN");
        stations.put(13, "CHANCERY LANE");
        stations.put(14, "ST PAULS");
        stations.put(15, "BANK");
        stations.put(16, "LIVERPOOL STREET");
        stations.put(17, "PADDINGTON");
        stations.put(18, "EDGWARE ROAD");
        stations.put(19, "GREAT PORTLAND STREET");
        stations.put(20, "EUSTON SQUARE");
        stations.put(21, "KINGS CROSS ST PANCRAS");
        stations.put(22, "FARRINGDON");
        stations.put(23, "BARBICAN");
        stations.put(24, "MOORGATE");
        stations.put(25, "ALDGATE");
        stations.put(26, "TOWER HILL");
        stations.put(27, "MONUMENT");
        stations.put(28, "CANNON STREET");
        stations.put(29, "MANSION HOUSE");
        stations.put(30, "BLACKFRIARS");
        stations.put(31, "TEMPLE");
        stations.put(32, "WESTMINSTER");
        stations.put(33, "ST JAMES PARK");
        stations.put(34, "VICTORIA");
        stations.put(35, "SLOANE SQUARE");
        stations.put(36, "SOUTH KENSINGTON");
        stations.put(37, "GLOUCESTER ROAD");
        stations.put(38, "HIGH STREET KENSINGTON");
        stations.put(39, "BAYSWATER");

        //Initialize edges
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1.68),
                new Edge(1, 2, 1.85),
                new Edge(2, 3, 1.95),
                new Edge(3, 4, 1.35),
                new Edge(4, 5, 0.95),

                new Edge(2, 1, 1.85),
                new Edge(1, 0, 1.68),

                new Edge(6, 7, 1.17),
                new Edge(7, 8, 1.35),
                new Edge(8, 9, 1.92),
                new Edge(9, 10, 1.00),
                new Edge(10, 2, 1.10),
                new Edge(2, 11, 0.98),
                new Edge(11, 12, 1.63),
                new Edge(12, 13, 0.87),
                new Edge(13, 14, 1.52),
                new Edge(14, 15, 1.62),
                new Edge(15, 16, 1.62),

                new Edge(17, 18, 2.33),
                new Edge(18, 0, 1.47),
                new Edge(0, 19, 1.90),
                new Edge(19, 20, 1.25),
                new Edge(20, 21, 1.75),
                new Edge(21, 22, 2.98),
                new Edge(22, 23, 1.22),
                new Edge(23, 24, 1.32),
                new Edge(24, 16, 1.18),
                new Edge(16, 25, 2.18),
                new Edge(25, 26, 1.37),
                new Edge(26, 27, 1.48),
                new Edge(27, 28, 0.88),
                new Edge(28, 29, 0.93),
                new Edge(29, 30, 1.22),
                new Edge(30, 31, 1.37),
                new Edge(31, 5, 1.43),
                new Edge(5, 32, 1.40),
                new Edge(32, 33, 1.52),
                new Edge(33, 34, 1.33),
                new Edge(34, 35, 1.75),
                new Edge(35, 36, 2.00),
                new Edge(36, 37, 1.48),
                new Edge(37, 38, 2.23),
                new Edge(38, 6, 1.68),
                new Edge(6, 39, 1.77),
                new Edge(39, 17, 1.63)
        );

        //Total number of nodes in the graph is 40 (labelled from 0 to 39)
        //Construct graph
        GraphN graph = new GraphN(edges, 40);

        //Outputs
        System.out.println();
        System.out.println("Station Names: ");

        System.out.println("01\tBAKER STREET\n" +
                "02\tREGENTS PARK\n" +
                "03\tOXFORD CIRCUS\n" +
                "04\tPICCADILLY CIRCUS\n" +
                "05\tCHARING CROSS\n" +
                "06\tEMBANKMENT\n" +
                "07\tNOTTING HILL GATE\n" +
                "08\tQUEENSWAY\n" +
                "09\tLANCASTER GATE\n" +
                "10\tMARBLE ARCH\n" +
                "11\tBOND STREET\n" +
                "12\tTOTTENHAM COURT ROAD\n" +
                "13\tHOLBORN\n" +
                "14\tCHANCERY LANE\n" +
                "15\tST PAULS\n" +
                "16\tBANK\n" +
                "17\tLIVERPOOL STREET\n" +
                "18\tPADDINGTON \n" +
                "19\tEDGWARE ROAD\n" +
                "20\tGREAT PORTLAND STREET\n" +
                "21\tEUSTON SQUARE\n" +
                "22\tKINGS CROSS ST PANCRAS\n" +
                "23\tFARRINGDON\n" +
                "24\tBARBICAN\n" +
                "25\tMOORGATE\n" +
                "26\tALDGATE\n" +
                "27\tTOWER HILL\n" +
                "28\tMONUMENT\n" +
                "29\tCANNON STREET\n" +
                "30\tMANSION HOUSE\n" +
                "31\tBLACKFRIARS\n" +
                "32\tTEMPLE\n" +
                "33\tWESTMINSTER\n" +
                "34\tST JAMES PARK\n" +
                "35\tVICTORIA\n" +
                "36\tSLOANE SQUARE\n" +
                "37\tSOUTH KENSINGTON\n" +
                "38\tGLOUCESTER ROAD\n" +
                "39\tHIGH STREET KENSINGTON\n" +
                "40\tBAYSWATER");

        boolean repeat = false;
        do {
            System.out.println();
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the starting station: ");
            String sA = input.nextLine().toUpperCase();
            if(stations.containsValue(sA)){
                int s = stations.getKey(sA);
                System.out.println("Enter the final destination station: ");
                String sB = input.nextLine().toUpperCase();
                if (stations.containsValue(sB)) {
                    int d = stations.getKey(sB);
                    findShortestPaths(graph, s, d, 40, stations);
                    repeat = true;
                } else {
                    System.out.println("--------Entered station is wrong!--------");
                }
            } else
                System.out.println("--------Entered station is wrong!--------");
        } while (repeat != true);


    }


}

