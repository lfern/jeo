/* Copyright 2013 The jeo project. All rights reserved.
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
package io.jeo.geojson;

import java.nio.file.Path;

import io.jeo.Tests;
import io.jeo.vector.VectorApiTestBase;
import io.jeo.vector.VectorDataset;

public class GeoJSONTest extends VectorApiTestBase {

    @Override
    protected VectorDataset createVectorData() throws Exception {
        Path dir = Tests.unzip(getClass().getResourceAsStream("states.zip"), Tests.newTmpDir());
        return new GeoJSONDataset(dir.resolve("states.json").toFile());
    }

}
