import java.util.ArrayList;

public interface Matcher {
    ArrayList<HelperStudent> findMatches(NeedyStudent needy, ArrayList<HelperStudent> helpers);
}