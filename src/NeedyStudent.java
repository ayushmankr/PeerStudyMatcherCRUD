import java.util.ArrayList;

public class NeedyStudent extends Student {
    public NeedyStudent(String name, ArrayList<String> needHelp) {
        super(name, new ArrayList<>(), needHelp);
    }
}