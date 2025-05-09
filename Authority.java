public class Authority extends User {

    public Authority(int userId, String userName, String userSurname, String email, String password){
        super(userName, userSurname, email, password);
    }

    public void resolveReport(ProblemReport pr) {
        pr.setResolved(true);
    }

    public void removeReport(ProblemReport pr) {
        // Remove from DB or list
    }
}