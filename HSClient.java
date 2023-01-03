import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class HSClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        
        try (
        // Read the number of nodes from the command line
        // int numNodes = Integer.parseInt(args[0]);
        // Read the number of nodes from the input

         // Read the number of nodes from the input
        Scanner inputScanner = new Scanner(System.in)) {
        System.out.println("Enter Number of nodes (same as server):");
        int numNodes = inputScanner.nextInt();
    
        // Get the RMI registry
        Registry registry = LocateRegistry.getRegistry(1099);

      
        // Check if each node is the leader
        for (int i = 1; i <= numNodes; i++) 
        {
            HS node = (HS) registry.lookup("Node" + i);
            if (node.isLeader()) {
                node.showMessage();
                System.out.println("Node with id: " + node.getId() + " is the leader");
            }
        }
    }
}}
