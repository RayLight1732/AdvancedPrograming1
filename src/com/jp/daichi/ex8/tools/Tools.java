package com.jp.daichi.ex8.tools;

public class Tools {
    public static final SimplePen PEN = new SimplePen();
    public static final Eraser ERASER = new Eraser();
    public static final Picker PICKER = new Picker();
    public static final TemplateShapeTool RECTANGLE = new TemplateShapeTool(ShapeFactories.RECTANGLE.getFactory(),false,"長方形");
    public static final TemplateShapeTool ELLIPSE = new TemplateShapeTool(ShapeFactories.ELLIPSE.getFactory(),false,"円");

    public static final TemplateShapeTool FILLED_RECTANGLE = new TemplateShapeTool(ShapeFactories.RECTANGLE.getFactory(),true,"塗りつぶし長方形");
    public static final TemplateShapeTool FILLED_ELLIPSE = new TemplateShapeTool(ShapeFactories.ELLIPSE.getFactory(),true,"塗りつぶし円");

}
