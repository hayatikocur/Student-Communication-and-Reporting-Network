package com.example.tutorialfx;

public abstract class User {
    protected int userId;
    protected String userName;
    protected String userSurname;
    protected String email;
    protected String password;

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeName(String newName) {
        this.userName = newName;
    }

    public void changeSurname(String newSurname) {
        this.userSurname = newSurname;
    }

    public void addComment(ProblemReport pr, String comment) {
        pr.addComment(new Comment(this, comment));
    }
}