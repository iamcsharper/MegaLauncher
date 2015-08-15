package net.xupoh.megalauncher.utils;

import java.net.URL;
import java.util.Map;
import net.xupoh.megalauncher.main.Starter;

public abstract class AbstractStyleLoader<I, T> {

    protected Map<I, T> elements;

    public abstract void loadLocal(I name, String filename);

    public abstract void load(I name, URL path);

    public T getRef(I name) {
        return elements.get(name);
    }

    public URL getStylePath(String to) {
        return Starter.class.getResource("/net/xupoh/styles/" + to);
    }
}
