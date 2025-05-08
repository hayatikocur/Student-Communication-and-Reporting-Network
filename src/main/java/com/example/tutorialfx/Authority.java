package com.example.tutorialfx;

public class Authority extends User {
    public Authority(int userId, String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    public void resolveReport(ProblemReport pr) {
        pr.setResolved(true);
    }

    public void removeReport(ProblemReport pr) {
        // Remove from DB or list
    }
}