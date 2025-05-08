
public class Authority extends User {

    public Authority(int userId, String userName, String userSurname, String email, String password) {
        super(userId, userName, userSurname, email, password);
    }

    public void resolveReport(ProblemReport pr) {
        pr.setResolved(true);
    }

    public void removeReport(ProblemReport pr) {
        pr.setArchived(true);
    }

    @Override
    public String getUserType() {
        return "Authority";
    }
}
