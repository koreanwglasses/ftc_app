package robohawks.async.error;

/**
 * Created by fchoi on 9/25/2016.
 */
public class ErrorArgs {
    Object sender;
    String error;
    String message;

    public ErrorArgs(Object sender) {
        this.sender = sender;
    }
}
