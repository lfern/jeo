package org.jeo.geom;

import static org.jeo.geom.CoordinatePath.PathStep.CLOSE;
import static org.jeo.geom.CoordinatePath.PathStep.LINE_TO;
import static org.jeo.geom.CoordinatePath.PathStep.MOVE_TO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jeo.geom.CoordinatePath.PathStep;
import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class CoordinatePathTest {

    GeometryBuilder gb;

    @Before
    public void setUp() {
        gb = new GeometryBuilder();
    }

    @Test
    public void testPoint() {
        Point p = gb.point(1,2);
        PointPath pi = new PointPath(p);

        assertSequence(pi, 1, 2, MOVE_TO);
    }

    @Test
    public void testLineString() {
        LineString l = gb.lineString(1,2,3,4,5,6);
        CoordinatePath li = CoordinatePath.create(l);

        assertSequence(li, 1,2,MOVE_TO, 3,4,LINE_TO, 5,6,LINE_TO);
    }

    @Test
    public void testLineStringGeneralize() throws Exception {
        LineString l = gb.lineString(1,1,2,2,3,3,4,4,5,5,6,6);
        CoordinatePath li = CoordinatePath.create(l, true, 2, 2);

        assertSequence(li, 1,1,MOVE_TO, 3,3,LINE_TO, 5,5,LINE_TO);
    }

    @Test
    public void testPolygon() {
        Polygon p = gb.polygon(1,2,3,4,5,6,1,2);
        CoordinatePath pi = CoordinatePath.create(p);

        assertSequence(pi, 1,2,MOVE_TO, 3,4,LINE_TO, 5,6,LINE_TO, 1,2,CLOSE);
        
    }

    @Test
    public void testPolygonGeneralize() {
        Polygon p = gb.polygon(1,1,2,2,3,3,4,4,5,5,6,6,1,1);
        CoordinatePath pi = CoordinatePath.create(p, true, 2, 2);

        assertSequence(pi, 1,1,MOVE_TO, 3,3,LINE_TO, 5,5,LINE_TO, 1,1,CLOSE);
    }

    @Test
    public void testPolygonWithHole() {
        Polygon p = 
            gb.polygon(gb.polygon(0,0,10,0,10,10,0,10,0,0), gb.polygon(2,2,8,2,8,8,2,8,2,2));
        CoordinatePath pi = CoordinatePath.create(p);

        assertSequence(pi, 0,0,MOVE_TO, 10,0,LINE_TO, 10,10,LINE_TO, 0,10,LINE_TO, 0,0,CLOSE, 
            2,2,MOVE_TO, 8,2,LINE_TO, 8,8,LINE_TO, 2,8,LINE_TO, 2,2,CLOSE);
    }

    @Test
    public void testPolygonWithHoleGeneralize() {
        Polygon p = gb.polygon(gb.polygon(0,0, 3,0, 5,0, 8,0, 10,0,  10,3, 10,5, 10,8, 10,10,
                                          8,10, 5,10, 3,10, 0,10, 0,8, 0,5, 0,3, 0,0), 
                              gb.polygon(3,3, 5,3, 8,3,  8,5, 8,8,  5,8, 3,8,  3,5, 3,3));

        CoordinatePath pi = CoordinatePath.create(p, true, 4, 4);
        assertSequence(pi, 0,0,MOVE_TO, 5,0,LINE_TO, 10,0,LINE_TO, 10,5,LINE_TO, 10,10,LINE_TO, 
            5,10,LINE_TO, 0,10,LINE_TO, 0,5,LINE_TO, 0,0,CLOSE, 3,3,MOVE_TO, 8,3,LINE_TO, 
            8,8,LINE_TO, 3,8,LINE_TO, 3,3,CLOSE);
    }

    @Test
    public void testMultiPoint() throws Exception {
        MultiPoint mp = gb.multiPoint(1,1, 2,2, 3,3);
        CoordinatePath mpi = CoordinatePath.create(mp);

        assertSequence(mpi, 1,1,MOVE_TO, 2,2,MOVE_TO, 3,3,MOVE_TO);
    }

    @Test
    public void testGeometryCollection() {
        GeometryCollection gcol = gb.geometryCollection(
            gb.polygon(1,2,3,4,5,6,1,2), gb.lineString(1,2,3,4,5,6), gb.point(1,2));

        CoordinatePath gci = CoordinatePath.create(gcol);

        assertSequence(gci, 1,2,MOVE_TO, 3,4,LINE_TO, 5,6,LINE_TO, 1,2,CLOSE, 
            1,2,MOVE_TO, 3,4,LINE_TO, 5,6,LINE_TO,   1, 2, MOVE_TO);
        
    }

    void assertSequence(CoordinatePath it, Object...seq) {
        if (seq.length % 3 != 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seq.length; i+=3) {
            assertTrue(it.hasNext());
            Coordinate c = 
                new Coordinate(((Number)seq[i]).doubleValue(),((Number)seq[i+1]).doubleValue());
            assertEquals(c, it.next());
            assertEquals((PathStep)seq[i+2], it.getStep());
        }

        assertFalse(it.hasNext());
        assertNull(it.next());
        assertEquals(PathStep.STOP, it.getStep());
    }
}
