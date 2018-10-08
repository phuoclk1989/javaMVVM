package com.redhelmet.alert2me.data.model;

import com.redhelmet.alert2me.data.model.base.Model;

public class EventGroupDisplayFilter implements Model {
    private String[] layers;
    private String[] overlays;

    public String[] getLayers() {
        return layers;
    }

    public void setLayers(String[] layers) {
        this.layers = layers;
    }

    public String[] getOverlays() {
        return overlays;
    }

    public void setOverlays(String[] overlays) {
        this.overlays = overlays;
    }
}
