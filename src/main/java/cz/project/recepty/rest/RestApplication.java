/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author tomas
 */
@ApplicationPath("/rest/v1")
public class RestApplication extends Application {

    private Set<Class<?>> empty = new HashSet<>();

    public RestApplication() {
        empty.add(ReceptyResource.class);
    }

    public Set<Class<?>> getClasses() {
        return empty;
    }

}
