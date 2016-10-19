package robohawks.utils;

/**
 * Created by fchoi on 10/19/2016.
 */
public class Color {
    public int a;
    public int r;
    public int g;
    public int b;

    public Color(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(int r, int g, int b) {
        this.a = 255;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static Color fromArgb(int argb) {
        int a, r, g, b;
        a = (argb >> 24) & 0xFF;
        r = (argb >> 16) & 0xFF;
        g = (argb >> 8) & 0xFF;
        b = (argb) & 0xFF;

        return new Color(a, r, g, b);
    }

    @Override
    public String toString() {
        return "argb(" + a + ", " + r + ", " + g + ", " + b + ")";
    }
}
