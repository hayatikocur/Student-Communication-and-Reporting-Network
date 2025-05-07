import java.time.LocalDateTime;
/**
 * This class represent main specifications of user
 * other types of users like authorites will extends from this class
 */
public abstract class User {
    private int userId;
    private String userName;
    private String userSurname;
    private String email;
    private String password;

    public User(int userId, String userName, String userSurname, String email, String password) 
    {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.email = email;
        this.password = password;
    }

    // password validating
    public boolean validatePassword(String password) 
    {
        return this.password.equals(password);
    }

    // password changing
    public void changePassword(String newPassword){
        this.password = newPassword;
    }

    // change name
    public void changeName(String newName){
        this.userName = newName;
    }

    // change surname
    public void changeSurname(String newSurname){
        this.userSurname = newSurname;
    }

    /**
     * This method adds a comment to a report
     * @param report  the report which comment will be added
     * @param content the content of the comment
     */
    public void addComment(ProblemReport report, String content) 
    {
        // the new id come from the existing report number plus one
        int nextCommentId = report.getCommentNumber() + 1;

        Comment comment = new Comment( this, nextCommentId, content, LocalDateTime.now(), report);

        report.getComments().add(comment);
        report.setCommentNumber(nextCommentId);

    }

    // getter setters

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserSurname(){
        return userSurname;
    }


    // mail
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    
}