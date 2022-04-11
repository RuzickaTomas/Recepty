/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.ds;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 *
 * Jedinacek ktery spravuje parametry spojeni do databaze
 */
public final class Connector {
    
    //Objekt ktery zajisti spojeni s databazi - v nasem pripade MySql
    private final MysqlDataSource dataSource;
    
    private static Connector  INSTANCE;
    
    private Connector() {
        this.dataSource = new MysqlDataSource();
        this.dataSource.setURL("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        this.dataSource.setUser("root");
        this.dataSource.setPassword("asd");
       // this.dataSource.setPassword("admin123");
    }

    public MysqlDataSource getDataSource() {
        return dataSource;
    }
    
    public static Connector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Connector();
        }
        return INSTANCE;
    }
}
