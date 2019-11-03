/* *****************************************************************************
 *  Name: Hardik Upadhyay
 *  Date: 25/10/2019
 *  Description: KdTree for range and nearest query point search
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {

    private final boolean RED = true;
    /**
     * root of the tree
     */
    private Node root;

    private int size;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p, double minx, double miny, double maxx, double maxy) {
            this.p = p;
            this.rect = new RectHV(minx, miny, maxx, maxy);
        }

    }
    public KdTree() {

    }

    // @edu.um/d.cs.findbugs.annotations.SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        // System.out.println(kdTree.nearest(new Point2D(0.75, 0.0)));
        // Point2D p1 = new Point2D(.7,.2);
        // Point2D p2 = new Point2D(.5,.4);
        // Point2D p3 = new Point2D(.2,.3);
        // Point2D p4 = new Point2D(.4,.7);
        // Point2D p5 = new Point2D(.9,.6);
        // // Point2D p6 = new Point2D(.1,.2);
        // // Point2D p7 = new Point2D(.3,.7);
        //
        // kdTree.insert(p1);
        // System.out.println(kdTree.contains(p1));
        // kdTree.insert(p2);
        // System.out.println(kdTree.contains(p2));
        // kdTree.insert(p3);
        // System.out.println(kdTree.contains(p4));
        // System.out.println(kdTree.contains(p3));
        // kdTree.insert(p4);
        // kdTree.insert(p5);
        kdTree.insert(new Point2D(0.7, 0.2));
         kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        Point2D test =new Point2D(0.699, 0.758);
        Point2D n = kdTree.nearest(test);
        System.out.println(n);
        n.drawTo(test);
        System.out.println("hi "+kdTree.contains(new Point2D(1.0, 0.0)));
        // RectHV queryRect = new RectHV(0,0,1,1);
        Point2D query = new Point2D(0.1,0.5);
        // Iterable<Point2D> li = kdTree.range(queryRect);
        // StdDraw.filledCircle(.1,.5,0.005);

        // for(Point2D n : li){
            // System.out.println("here");
            // System.out.println(n);
        // }
        Point2D near = kdTree.nearest(query);
        System.out.println(near);
        kdTree.draw();
        near.drawTo(query);
        // kdTree.insert(p7);
        // kdTree.insert(p6);/
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to insert() is null");
        if(!contains(p)) {
            root = insert(root, p, RED, 0, 0, 1, 1);
            size++;
        }
    }

    private Node insert(Node k, Point2D p, boolean orientation, double minx, double miny, double maxx, double maxy) {
        if (k == null){
            return new Node(p, minx, miny, maxx, maxy);
        }
        int cmp;
        if (orientation) {
            double cachex = k.p.x();
            cmp = Double.compare(p.x(),cachex);
            if(cmp<0)k.lb = insert(k.lb, p, !orientation, k.rect.xmin(), k.rect.ymin(), cachex,
                                   k.rect.ymax());
            else k.rt = insert(k.rt, p, !orientation, cachex, k.rect.ymin(), k.rect.xmax(),
                               k.rect.ymax());
        }
        else {
            double cachey = k.p.y();
            cmp = Double.compare(p.y(), cachey);
            if(cmp<0)k.lb = insert(k.lb, p, !orientation, k.rect.xmin(), k.rect.ymin(), k.rect.xmax(),
                                   cachey);
            else k.rt = insert(k.rt, p, !orientation, k.rect.xmin(), cachey, k.rect.xmax(),
                               k.rect.ymax());
        }
        return k;
    }

    public boolean contains(Point2D p) {
        return contains(root, p, RED);
    }

    private boolean contains(Node k, Point2D p, boolean red) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");
        if (k == null) return false;
        if (p.x() == k.p.x() && p.y() == k.p.y()) return true;
        int cmp;
        if (red) cmp = Double.compare(p.x(), k.p.x());
        else cmp = Double.compare(p.y(), k.p.y());
        if (cmp < 0) return contains(k.lb, p, !red);
        else return contains(k.rt, p, !red);
    }

    public void draw() {
        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setPenColor(StdDraw.BLACK);
        drawPoints(root);
        drawRectangles(root,RED);
        StdDraw.setPenColor(StdDraw.BLACK);
        //remove after testing

        // StdDraw.rectangle(0.5,0.5,0.5,0.5);
    }

    private void drawRectangles(Node root, boolean red) {
        if(root==null){
            return;
        }
        drawRectangles(root.lb ,!red);
        drawRectangles(root.rt,!red);
        if(red!=RED){
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D p = new Point2D(root.rect.xmin(),root.p.y());
            p.drawTo(new Point2D(root.rect.xmax(),root.p.y()));
        }
        else{
            StdDraw.setPenColor(StdDraw.RED);
            Point2D p1 = new Point2D(root.p.x(),root.rect.ymin());
            p1.drawTo(new Point2D(root.p.x(),root.rect.ymax()));
        }
    }

    private void drawPoints(Node root) {
        if(root==null)return;
        StdDraw.setPenColor(StdDraw.BLACK);
        drawPoints(root.lb);
        StdDraw.filledCircle(root.p.x(),root.p.y(),0.005);
        drawPoints(root.rt);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect==null)throw new IllegalArgumentException("second Argument to range() is null");
        ArrayList<Point2D> l = range(new ArrayList<Point2D>(),root, rect);;
        return l;
    }

    private ArrayList<Point2D> range(ArrayList<Point2D> l,Node root, RectHV rect) {
        if(root==null)return l;

        if(rect.intersects(root.rect)){
            range(l, root.lb, rect);
            if(rect.contains(root.p)){
                l.add(root.p);
            }

            range(l, root.rt, rect);

        }
        return l;
    }
    public Point2D nearest(Point2D query){
        if(query==null)throw new IllegalArgumentException();
        //check for null pointer exception
        if(root == null)return null;
        Point2D p1 = nearest(root.p,root,query,RED);
        // StdDraw.line(p.x(),p.y(),p1.x(),p1.y());
        // if(p1.x()==100)return null;
        return p1;
    }

    private Point2D nearest(Point2D nearest, Node root, Point2D query,boolean red) {
        if(root==null){
            return nearest;
        }
        int cmp;
        if(root.p.distanceSquaredTo(query)<nearest.distanceSquaredTo(query)) {
            nearest = new Point2D(root.p.x(), root.p.y());
        }
        if (red == RED) {
            cmp = Double.compare(query.x(), root.p.x());
        }
        else cmp = Double.compare(query.y(),root.p.y());

        if (cmp < 0) {
                nearest = nearest(nearest, root.lb, query, !red);
                if(root.rt!=null) {
                    double distanceToRightRectangle = root.rt.rect.distanceSquaredTo(query);
                    if (nearest.distanceSquaredTo(query) > distanceToRightRectangle) {
                        nearest = nearest(nearest, root.rt, query, !red);
                    }
                }
            }
        else {
                nearest = nearest(nearest, root.rt, query, !red);
                if(root.lb!=null) {
                    double distanceToLeftRectangle = root.lb.rect.distanceSquaredTo(query);
                    if (nearest.distanceSquaredTo(query) > distanceToLeftRectangle) {
                        nearest = nearest(nearest, root.lb, query, !red);
                    }
                }
        }


        return nearest;
    }

}
