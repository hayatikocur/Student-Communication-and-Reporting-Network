package com.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class Authority extends User {

    public Authority(String userName, String userSurname, String email, String password){
        super(userName, userSurname, email, password);
    }

    public void resolveReport(ProblemReport pr) {
        pr.setResolved(true);
    }

    public void removeReport(ProblemReport pr) {
        // Remove from DB or list
    }

    
    
}