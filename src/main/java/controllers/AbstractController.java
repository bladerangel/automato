package controllers;

import views.LayoutGraph;

/**
 * Created by Rangel on 22/11/2016.
 */
public abstract class AbstractController {

    protected ApplicationController applicationController;
    protected LayoutGraph layoutGraph;

    protected LayoutGraph layoutGraph1;
    protected LayoutGraph layoutGraph2;

    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        this.applicationController = applicationController;
        this.layoutGraph = layoutGraph;
    }

    public void init(ApplicationController applicationController, LayoutGraph layoutGraph1, LayoutGraph layoutGraph2) {
        this.applicationController = applicationController;
        this.layoutGraph1 = layoutGraph1;
        this.layoutGraph2 = layoutGraph2;
    }
}
