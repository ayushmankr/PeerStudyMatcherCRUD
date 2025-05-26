import java.util.ArrayList;

public class MatcherImpl implements Matcher {

    public ArrayList<HelperStudent> findMatches(NeedyStudent needy, ArrayList<HelperStudent> helpers) {
        ArrayList<HelperStudent> matches = new ArrayList<>();

        for (HelperStudent h : helpers) {
            int matchCount = getMatchCount(h, needy);
            if (matchCount > 0) {
                matches.add(h);
            }
        }

        matches.sort((h1, h2) -> Integer.compare(getMatchCount(h2, needy), getMatchCount(h1, needy)));
        return matches;
    }

    private int getMatchCount(HelperStudent helper, NeedyStudent needy) {
        int count = 0;
        for (String s : needy.getSubjectsNeedHelp()) {
            if (helper.getSubjectsCanHelp().contains(s)) {
                count++;
            }
        }
        return count;
    }
}