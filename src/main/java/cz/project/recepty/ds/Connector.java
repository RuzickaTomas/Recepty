/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.ds;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;

/**
 *
 *  Spravuje parametry spojeni do databaze
 */
@DataSourceDefinition(className = "com.mysql.cj.jdbc.Driver",
        name = "java:global/recepty/MyDS",
        url= "jdbc:mysql://mysql:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull",
        user = "root", 
        password = "admin123",
        minPoolSize = 10,
        maxPoolSize = 50)
@Stateless
public class Connector {
  

}
