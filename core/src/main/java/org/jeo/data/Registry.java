package org.jeo.data;

import java.io.IOException;
import java.util.Iterator;

/**
 * A container of {@link Workspace} objects.
 * 
 * @author Justin Deoliveira, OpenGeo
 */
public interface Registry {

    /**
     * The names of all workspaces of the registry.
     */
    Iterator<String> keys();

    /**
     * Returns a workspace object by name.
     * 
     * @param key The name/key of the workspaces.
     * 
     * @return The workspace or <code>null</code> if so such workspaces matching the key exists.
     */
    Workspace get(String key) throws IOException;

    /**
     * Disposes the registry.
     * <p>
     * Application code must ensure this method is called when the registry is no longer needed.
     * </p>
     */
    void dispose();
}
