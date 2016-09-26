package robohawks.async;

/**
 * Created by fchoi on 9/25/2016.
 */
public interface Module {
    void start(Sequence.Callback callback);

    void loop(Sequence.Callback callback);
}
