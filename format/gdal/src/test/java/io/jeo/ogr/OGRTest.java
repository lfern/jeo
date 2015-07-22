/* Copyright 2014 The jeo project. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jeo.ogr;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;

import io.jeo.Tests;
import io.jeo.data.Drivers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OGRTest {

    File data;

    @BeforeClass
    public static void initOGR() {
        try {
            OGR.init();
        }
        catch(Throwable e) {
            Assume.assumeTrue(false);
        }
    }

    @Before
    public void setUp() throws Exception {
        Path dir = Tests.newTmpDir("ogr", "data");
        Tests.unzip(getClass().getResourceAsStream("states.zip"), dir);

        data = dir.resolve("states.shp").toFile();
    }

    @Test
    public void testDriverFind() throws Exception {
        assertTrue(OGR.class.isInstance(Drivers.find("ogr")));
        assertTrue(Shapefile.class.isInstance(Drivers.find("shp")));
    }

    @Test
    public void testDriverOpen() throws Exception {
        assertTrue(OGRDataset.class.isInstance(Drivers.open(data)));
    }
}
