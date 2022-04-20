package ahmed.haikal.myplanner.Model;

public class ProjectCard {

    String projectName, projectDescription;
    int numOfTeamMembers, projectBackgroundColor;

    public ProjectCard(String projectName, String projectDescription, int numOfTeamMembers, int projectBackgroundColor) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.numOfTeamMembers = numOfTeamMembers;
        this.projectBackgroundColor = projectBackgroundColor;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getNumOfTeamMembers() {
        return numOfTeamMembers;
    }

    public void setNumOfTeamMembers(int numOfTeamMembers) {
        this.numOfTeamMembers = numOfTeamMembers;
    }

    public int getProjectBackgroundColor() {
        return projectBackgroundColor;
    }

    public void setProjectBackgroundColor(int projectBackgroundColor) {
        this.projectBackgroundColor = projectBackgroundColor;
    }

    public String getProjectDescription() {
        return projectDescription;
    }
}
