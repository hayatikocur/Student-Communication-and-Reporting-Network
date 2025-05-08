public class Authority extends User {
    public void resolveReport(ProblemReport pr) {
        pr.setResolved(true);
    }

    public void removeReport(ProblemReport pr) {
        // Remove from DB or list
    }
}