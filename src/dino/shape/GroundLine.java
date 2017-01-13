package dino.shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * The ground line with holes.
 */
public class GroundLine extends BoundingBox {
    private final List<BoundingBox> segments;
    private final float canvasWidth;
    
    private static final int NUM_HOLES = 15;
    private static final float MIN_GAP_BETWEEN_HOLES = 100.0f;
    
    public GroundLine(float x, float y, float width, float height, float canvasWidth) {
        super(x, y, width, height);
        segments = initSegments(NUM_HOLES+1, getWidth(), MIN_GAP_BETWEEN_HOLES);
        this.canvasWidth = canvasWidth;
    }

    public void render(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(Color.white);
        
        float endOfLine = shiftRightIfOffCanvas();

        float beginOfSegment = 0.0f;
        float endOfSegment = 0.0f;
        for(BoundingBox segment : segments) {
            beginOfSegment = segment.getX();
            if (beginOfSegment > canvasWidth)
                break; // don't draw segments which fall past the end of the canvas
            endOfSegment = segment.getX() + segment.getWidth();
            if (endOfSegment < 0)
                continue; // don't draw segments which fall to the left of the canvas
            g.fillRect(segment.getX(), getY(), segment.getWidth(), getHeight());
        }
        
        if (endOfLine < canvasWidth) {
            // The ground line ends before the canvas ends.
            // Draw the ground line again after the end of the line, 
            // but offset with one full line length.
            for(BoundingBox segment : segments) {
                beginOfSegment = segment.getX() + getWidth();
                if (beginOfSegment > canvasWidth)
                    break; // don't draw segments falling past the end of the canvas
                endOfSegment = segment.getX() + segment.getWidth() + getWidth();
                if (endOfSegment < 0)
                    continue; // don't draw segments which fall to the left of the canvas
                g.fillRect(beginOfSegment, getY(), segment.getWidth(), getHeight());
            }
        }
        g.setColor(oldColor);
    }

    public boolean left(float delta) {
        setX(getX() - delta);
        for(BoundingBox segment : segments) {
            segment.setX(segment.getX() - delta);
        }
        return true;
    }

    public boolean right(float delta) {
        setX(getX() + delta);
        for(BoundingBox segment : segments) {
            segment.setX(segment.getX() + delta);
        }
        return true;
    }

    /**
     * We test for intersection against the list of segments as they are rendered.
     * We create a new bounding box to account for the segments that are rendered
     * at the end of the segments list.
     */
    @Override
    public boolean intersects(BoundingBox other) {

        float endOfLine = shiftRightIfOffCanvas();

        for (BoundingBox segment : segments) {
            if (segment.intersects(other))
                return true;
        }

        if (endOfLine < canvasWidth) {
            // Test for intersection again, segments are offset right
            for (BoundingBox segment : segments) {
                BoundingBox s = new BoundingBox(segment);
                s.setX(s.getX() + getWidth());
                if (s.intersects(other))
                    return true;
            }
        }
        return false;
    }
    
    static class Hole {
        private float x;
        private static final float WIDTH = 70.0f;
        
        Hole(float position) {
            this.x = position;
        }
        
        float left() {
            return x;
        }
        
        float right() {
            return x + WIDTH;
        }
    }
    
    /**
     * Initialize line segments separated by holes in the ground line.
     * We divide the ground line into a number of equal parts. Each part has 
     * a region where a hole is randomly placed and a region where no 
     * holes are placed (the "gap").  By having gaps in the line parts we
     * ensure that adjacent holes do not fall very close to each other.
     * 
     * @param numSegments the number of segments we're going to create
     * @param totalLen total length of the line
     * @param gapLen the length of the gap in each segment where no holes
     *      are created 
     * @return a list of line segments
     */
    private List<BoundingBox> initSegments(int numSegments, float totalLen, float gapLen) {
        if (numSegments < 2)
            throw new IllegalArgumentException("numSegments=" + numSegments);
        
        List<BoundingBox> segments = new ArrayList<>(numSegments);
        Random r = new Random();
        
        /*
         * Generate 'numSegments' segments of random length separated by holes:
         * 1. Divide total line length 'totalLen' into 'numSegments' parts ==> 
         *    parts of length 'fixedPartLen'
         * 2. Each segment starts with an area of length 'gapLen' where no 
         *    holes are placed, followed by the 'randomRange' part where a
         *    hole is randomly placed. 
         * 3. Generate a Hole at a random position within the 'randomRange' part
         * 4. Add the previous segment
         */
        final float fixedPartLen = (totalLen + Hole.WIDTH) / numSegments;
        final float randomRange = fixedPartLen - gapLen;
        float begin = 0; // beginning of segment (x coordinate)
        float end = 0;   // end of segment
        float lenFromPreviousHole = 0.0f;
        
        // first N-1 segments end with a hole
        for (int i = 0; i < numSegments-1; i++) {
            float lenToHole = gapLen + r.nextFloat() * randomRange;
            end = begin + lenToHole + lenFromPreviousHole;
            segments.add(new BoundingBox(begin, getY(), end - begin, getHeight()));

            // next segment starts after the hole
            begin = end + Hole.WIDTH;
            lenFromPreviousHole = fixedPartLen - Hole.WIDTH - lenToHole;
        }
        
        // last segment
        segments.add(new BoundingBox(begin, getY(), totalLen - begin, getHeight()));
        return segments;
    }

    /**
     * If the entire ground line is off the canvas, 
     * shift it right with one line length.
     */
    private float shiftRightIfOffCanvas() {
        float endOfLine = getX() + getWidth();
        if (endOfLine < 0) {
            right(getWidth());
            endOfLine = getX() + getWidth();
        }
        return endOfLine;
    }
}
