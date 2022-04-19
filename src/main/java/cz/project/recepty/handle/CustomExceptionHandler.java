/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.handle;

import jakarta.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;

/**
 *
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler handler;

    public CustomExceptionHandler(ExceptionHandler handler) {
        this.handler = handler;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.handler;
    }

    @Override
    public void handle() throws FacesException {
        Iterable<ExceptionQueuedEvent> unhandledEvents = this.getUnhandledExceptionQueuedEvents();
        unhandledEvents.forEach(e -> {

            ExceptionQueuedEventContext exceptionQueuedEventContext = (ExceptionQueuedEventContext) e.getSource();

            Throwable throwable = exceptionQueuedEventContext.getException();
            System.err.println("Exception: " + throwable.getMessage());

            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
            NavigationHandler nav = context.getApplication().getNavigationHandler();

            requestMap.put("error-message", throwable.getMessage());
            requestMap.put("error-stack", throwable.getStackTrace());
            nav.handleNavigation(context, null, "/error");
            context.renderResponse();

        });

    }

}
