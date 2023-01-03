import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class HSServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        try (
        // Read the number of nodes from the command line
        // int numNodes = Integer.parseInt(args[0]);
        // Read the number of nodes from the input
        Scanner inputScanner = new Scanner(System.in)) {
            System.out.println("Enter Number of nodes:");
            int numNodes = inputScanner.nextInt();
            // Create the first node in the ring
            HSNodeImpl firstNode = new HSNodeImpl(1, null, null);
            HSNodeImpl currentNode = firstNode;
            // Create the remaining nodes in the ring and add them to the ring structure
            for (int i = 2; i <= numNodes; i++) {
                HSNodeImpl nextNode = new HSNodeImpl(i, currentNode, null);
                currentNode.right = nextNode;
                currentNode = nextNode;
            }
            // Set the right field of the last node to point back to the first node, completing the ring
            currentNode.right = firstNode;
            // Start the RMI registry
            Registry registry = LocateRegistry.createRegistry(1099);
            // Bind the nodes to the registry
            for (int i = 1; i <= numNodes; i++) {
                registry.bind("Node"+i, currentNode);
                currentNode = (HSNodeImpl) currentNode.right;
                System.out.println("Node " + i + " with id: " + i );
            }
            // Start the election
            firstNode.startElection();
        }
        System.out.println("Server is ready");
    }
    
}
