package robohawks.async;

import java.util.ArrayList;
import java.util.List;

/**
 * Sequencer objects drive sequences. To create a new sequence associated with an instance of Sequencer, use the begin method.
 *
 * Sequences in a sequencer will be iterated simultaneously when loop is called, which, in effect, allows for the easy execution of asynchronous tasks.
 */
public class Sequencer{
    private List<Sequence> sequences;

    public Sequencer() {
        sequences = new ArrayList<>();
    }

    /**
     * Create a new sequence
     * @param action first operation
     * @return the new sequence
     */
    public Sequence begin(Operation action) {
        Sequence sequence = new Sequence();
        sequence.then(action);

        sequences.add(sequence);
        return sequence;
    }

    public void loop() {
        if(sequences.size() > 0) {
            for (int i = sequences.size() - 1; i >= 0; i--) {
                Sequence sequence = sequences.get(i);
                if (sequence.isFinished()) {
                    sequences.remove(i);
                } else {
                    sequence.loop();
                }
            }
        }
    }
}
