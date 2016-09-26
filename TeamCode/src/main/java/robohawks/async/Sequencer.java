package robohawks.async;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fchoi on 9/25/2016.
 */
public class Sequencer{
    private List<Sequence> sequences;

    public Sequencer() {
        sequences = new ArrayList<>();
    }

    public Sequence begin(Module action) {
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
