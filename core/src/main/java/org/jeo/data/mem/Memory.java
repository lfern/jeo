package org.jeo.data.mem;

import java.io.IOException;
import java.util.Map;

import org.jeo.data.Driver;

public class Memory implements Driver<MemWorkspace> {

    public static MemWorkspace open() {
        return new MemWorkspace();
    }

    @Override
    public String getName() {
        return "Memory";
    }

    @Override
    public Class<MemWorkspace> getType() {
        return MemWorkspace.class;
    }

    @Override
    public boolean canOpen(Map<?, Object> opts) {
        return opts.isEmpty();
    }

    @Override
    public MemWorkspace open(Map<?, Object> opts) throws IOException {
        return new MemWorkspace();
    }
}
