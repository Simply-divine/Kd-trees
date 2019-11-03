import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> point2DS;
    private int n=0;
    public PointSET()
    {
        point2DS = new SET<>();
    }
    public boolean isEmpty()
    {
        return n==0;

    }
    public int size()
    {
        return n;

    }
    public void insert(Point2D p)
    {
        if(p == null)throw new IllegalArgumentException("calls insert() with null argument");
        if(!contains(p)) {
            point2DS.add(p);
            n++;
        }
    }

    public boolean contains(Point2D p)
    {
        if(p==null)throw new IllegalArgumentException("argument to contains() is null");
        return point2DS.contains(p);
    }
    public void draw()
    {
        for (Point2D p:point2DS) {
            StdDraw.point(p.x(),p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rectHV)
    {
        if(rectHV==null)throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p:point2DS)
        {
            if(rectHV.contains(p))
            {
                queue.enqueue(p);
            }
        }
        return queue;
    }
    public Point2D nearest(Point2D p)
    {
        if(p==null)throw new IllegalArgumentException();
        if(point2DS.isEmpty()){
            return null;
        }
        Point2D min = point2DS.min();
        for (Point2D point2D:point2DS)
        {
            if (p.distanceTo(point2D)<p.distanceTo(min))
            {
                min = point2D;
            }
        }

        return min;
    }

    public static void main(String[] args)
    {
        // PointSET pointSET = new PointSET();
        PointSET kdTree = new PointSET();
        System.out.println(kdTree.nearest(new Point2D(0.75, 0.0)));
        Point2D p1 = new Point2D(.7,.2);
        Point2D p2 = new Point2D(.5,.4);
        Point2D p3 = new Point2D(.2,.3);
        Point2D p4 = new Point2D(.4,.7);
        Point2D p5 = new Point2D(.9,.6);
        // Point2D p6 = new Point2D(.1,.2);
        // Point2D p7 = new Point2D(.3,.7);
        kdTree.insert(new Point2D(0.75,0.0));
        kdTree.insert(new Point2D(0.0, 0.5));
        kdTree.insert(new Point2D(1.0, 1.0));
        kdTree.insert(new Point2D(1.0, 0.7));
        kdTree.insert(new Point2D(0.75, 1.0));
        kdTree.insert(new Point2D(0.75, 0.2));
        kdTree.insert(new Point2D(0.0, 0.2));
        kdTree.insert(new Point2D(0.0, 0.0));
        kdTree.insert(new Point2D(0.25, 0.7));
        kdTree.insert(new Point2D(1.0, 0.0));
        System.out.println("hi "+kdTree.contains(new Point2D(1.0, 0.0)));
        kdTree.insert(p1);
        System.out.println(kdTree.contains(p1));
        kdTree.insert(p2);
        System.out.println(kdTree.contains(p2));
        kdTree.insert(p3);
        System.out.println(kdTree.contains(p4));
        System.out.println(kdTree.contains(p3));
        kdTree.insert(p4);
        kdTree.insert(p5);
        RectHV queryRect = new RectHV(0,0,1,1);
        // Point2D query = new Point2D(0.6,0.5);
        Iterable<Point2D> li = kdTree.range(queryRect);
        for(Point2D n : li){
            // System.out.println("here");
            System.out.println(n);
        }

    }

}
