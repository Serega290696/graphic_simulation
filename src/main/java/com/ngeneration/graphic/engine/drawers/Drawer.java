package com.ngeneration.graphic.engine.drawers;

import com.ngeneration.graphic.engine.drawablecomponents.RenderedComponent;
import com.ngeneration.graphic.engine.view.DrawArea;
import com.ngeneration.graphic.engine.view.Window;

public abstract class Drawer<ID> {

    public final void render(RenderedComponent component, DrawArea holder) {
        if (component.isVisible() && isDrawable(component)) {
            RenderedComponent transformed = component;
            if(holder != null) {
                transformed = transform(component, holder);
            }
            doRender(transformed);
        }
    }

    protected abstract boolean isDrawable(RenderedComponent component);

    protected abstract RenderedComponent transform(RenderedComponent component, DrawArea holder);

    protected abstract void doRender(RenderedComponent component);

    public abstract ID createAndBindToWindow(Window<ID> window);

    public abstract void startContinuouslyDrawing();
}
