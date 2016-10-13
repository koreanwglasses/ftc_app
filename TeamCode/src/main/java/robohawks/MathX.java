package robohawks;

/**
 * Created by fchoi on 10/13/2016.
 */
public class MathX {
    public static double expScale(double value, double e) {
        return Math.pow(Math.abs(value), e) * Math.signum(value);
    }
}
