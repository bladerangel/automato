package controllers;

import views.LayoutGraph;

/**
 * Created by Rangel on 22/11/2016.
 */
public abstract class AbstractController {

    protected ApplicationController applicationController;
    protected LayoutGraph layoutGraph;

    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        this.applicationController = applicationController;
        this.layoutGraph = layoutGraph;
    }
}
