package com.ngeneration;

import com.ngeneration.graphic.engine.ComponentsScheduler;
import com.ngeneration.graphic.engine.Shape;
import com.ngeneration.graphic.engine.Vector;
import com.ngeneration.graphic.engine.drawablecomponents.RenderedComponent;
import com.ngeneration.graphic.engine.enums.ColorEnum;
import com.ngeneration.graphic.engine.view.DrawContext;
import com.ngeneration.graphic.engine.view.Window;
import com.ngeneration.ui.ControlPanel;
import com.ngeneration.ui.ControlPanelController;

import java.util.*;

public class SimpleFacadeSimulation implements Simulation {

    private final Set<Window<Long>> windows = new HashSet<>();

    public void init() {
        System.out.println("Init TavrovSimulation. . .");
        System.out.println("Loading control panel");
        ControlPanelController controlPanelController = ControlPanel.create(this);
    }

    //    Set<RenderedComponent> cos = new HashSet<>();
    double[] ampl = new double[1];
    Map<RenderedComponent, Double> ts = new HashMap<>();

    {
        ampl[0] = 10;
    }

    public void start() {
        DrawContext canvas = LwjglSimuationFacade.createWindowAndGetContext("TavrovSimulation", 1000, 1000);
        RenderedComponent component = new RenderedComponent(
                new Vector(10, 20),
                new Vector(40, 9),
                3.14 / 6, ColorEnum.DARK_RED, 0, Shape.RECT);
        canvas.put(1, component);
        ComponentsScheduler<RenderedComponent> scheduler = new ComponentsScheduler<RenderedComponent>(
                (c, delta) -> {
//                    c.setRotation(c.getRotation() + 1d * delta);
                    double tt = ts.put(c, 0d);
                    ampl[0] -= 0.0005;
                    c.setPosition(new Vector(ampl[0] * Math.cos(tt) ,
                            ampl[0] * Math.sin(tt)));
                    ts.put(c, tt + 0.1);
                    if(Math.abs(ampl[0]) > 100) {
                        ampl[0] = 100;
                    }
//                    t[0] += 0.0001;
                }
        );
        ComponentsScheduler<RenderedComponent> scheduler2 = new ComponentsScheduler<RenderedComponent>(
                (c, delta) -> {
//                    if(Math.random() < 0.1) {
//                        return;
//                    }
                    RenderedComponent comp = new RenderedComponent(
                            Vector.zero().plus(Vector.one().multiple(1000)),
//                            new Vector(100 * Math.random() - 50, -50 + 100 * Math.random()),
                            new Vector(40, 9),
                            3.14 / 0.5, ColorEnum.values()[(int) (Math.random() * 5)], Math.random(), Shape.RECT);
                    canvas.putDown(1, comp);
//                    cos.add(comp);
                    scheduler.add(comp);
                    ts.put(comp, 0d);
//                    for (RenderedComponent co : cos) {
//                        double x = co.getPosition().getX() + 0.5d * delta;
////                        co.setPosition(new Vector(x, Math.cos(x) * 35));
//                    }
                }
        );
        scheduler2.add(component);
    }

    public void finish() {
        for (Window<? extends Long> window : windows) {
            window.close();
        }
        windows.clear();
    }

    public void restart() {
        finish();
        start();
    }

}
