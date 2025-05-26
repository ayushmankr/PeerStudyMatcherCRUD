import java.util.ArrayList;

public class Student {
    private String name;
    private ArrayList<String> subjectsCanHelp;
    private ArrayList<String> subjectsNeedHelp;

    public Student(String name, ArrayList<String> canHelp, ArrayList<String> needHelp) {
        this.name = name;
        this.subjectsCanHelp = canHelp;
        this.subjectsNeedHelp = needHelp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSubjectsCanHelp() {
        return subjectsCanHelp;
    }

    public void setSubjectsCanHelp(ArrayList<String> subjectsCanHelp) {
        this.subjectsCanHelp = subjectsCanHelp;
    }

    public ArrayList<String> getSubjectsNeedHelp() {
        return subjectsNeedHelp;
    }

    public void setSubjectsNeedHelp(ArrayList<String> subjectsNeedHelp) {
        this.subjectsNeedHelp = subjectsNeedHelp;
    }

    public String toString() {
        return name;
    }
}