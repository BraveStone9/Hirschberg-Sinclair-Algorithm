import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class HSNodeImpl extends UnicastRemoteObject implements HS {
    private static final long serialVersionUID = 1L;

    private int id;
    private HS left;
    HS right;
    private boolean isLeader;

    public HSNodeImpl(int id, HSNodeImpl left, HSNodeImpl right) throws RemoteException {
        this.id = id;
        this.left = left;
        this.right = right;
        this.isLeader = false;
    }  
public int getId() throws RemoteException {
    return id;
}


// Implementation of HS algorithm for leader election
public void startElection() throws RemoteException {
    // Check if the right neighbor has a higher ID than the current node
    if (right.getId() > id) {
        // Pass on the request to start the election to the next node in the ring
        right.startElection();
    } else {
        // The current node has the highest ID, so start the election by sending a request to the left neighbor
        left.receiveElection(this);
    }
}

public void receiveElection(HSNodeImpl requestingNode) throws RemoteException {
    int requestingNodeId = requestingNode.getId();
    if (requestingNodeId > id) {
        if (right != null) {
            right.receiveElection(requestingNode);
        } else {
            becomeCoordinator(requestingNode);
        }
    } else if (requestingNodeId < id) {
        if (left != null) {
            left.receiveElection(requestingNode);
        } else {
            becomeCoordinator(requestingNode);
        }
    } else {
        becomeCoordinator(requestingNode);
    }
}


public void receiveCoordinator(HSNodeImpl requestingNode) throws RemoteException {
    if (requestingNode.getId() != id) {
        right.receiveCoordinator(requestingNode);
    }
}
public void becomeCoordinator(HSNodeImpl requestingNode) throws RemoteException {
    isLeader = true;
    if(left!=null){
    left.receiveCoordinator(requestingNode);
}
if(right!=null){
    right.receiveCoordinator(requestingNode);
}
}
public boolean isLeader() throws RemoteException {
    return isLeader;
}

// function to show message on server
public void showMessage() throws RemoteException
{
    if (this.isLeader()) {
        System.out.println("Node with Id: "+this.id+" is leader");
    } 
}

}
