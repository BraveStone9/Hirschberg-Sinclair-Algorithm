import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HS extends Remote {
    
    int getId() throws RemoteException;
    void startElection() throws RemoteException;
    void receiveElection(HSNodeImpl requestingNode) throws RemoteException;
    void becomeCoordinator(HSNodeImpl coordinator) throws RemoteException;
    boolean isLeader() throws RemoteException;
    void showMessage() throws RemoteException;
    void receiveCoordinator(HSNodeImpl coordinator) throws RemoteException;
}
