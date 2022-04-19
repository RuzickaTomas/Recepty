/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.handle;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {
    
    private ExceptionHandlerFactory factory;
    
    public CustomExceptionHandlerFactory () {}
    
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory factory) {
        this.factory = factory;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(this.factory.getExceptionHandler());
    }
    
    
}
