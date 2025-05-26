import java.util.ArrayList;

public class HelperStudent extends Student {
    public HelperStudent(String name, ArrayList<String> canHelp) {
        super(name, canHelp, new ArrayList<>());
    }
}