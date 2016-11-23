package controllers;

/**
 * Created by Rangel on 22/11/2016.
 */
public abstract class AbstractController {

    protected ApplicationController applicationController;

    public void init(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }
}
