import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @author  Marco Gonzalez (mag0089@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version TODAY
 *
 */
public class Extractor {
   
   /** raw data: all (x,y) points from source data. */
   private Point[] points;
   
   /** lines identified from raw data. */
   private SortedSet<Line> lines;
  
   /**
    * Builds an extractor based on the points in the file named by filename. 
    */
   public Extractor(String filename) {
    
      try {
      
         Scanner scan = new Scanner(new File(filename));
         int count = 0;
         points = new Point[scan.nextInt()];
         
         while (scan.hasNext()) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            Point obj = new Point(x, y);
            points[count] = obj;
            count++;
         }
      
      }
      catch (Exception e) {
         System.out.println("");
      }
   }
  
   /**
    * Builds an extractor based on the points in the Collection named by pcoll. 
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }
  
   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesBrute() {
      lines = new TreeSet<Line>();
      
      Point[] newArray = Arrays.copyOf(points, points.length);
      
      for (int i = 3; i < newArray.length; i++) {
         
         for (int j = 2; j < i; j++) {
          
            for (int k = 1; k < j; k++) {
             
               for (int z = 0; z < k; z++) {
               
                  boolean check1 = (newArray[i].slopeOrder.compare(newArray[j], newArray[k]) == 0);
                  boolean check2 = (newArray[i].slopeOrder.compare(newArray[k], newArray[z]) == 0);
                  
                  if ((check1) && (check2)) {
                     
                     Line line = new Line();
                     
                     line.add(newArray[i]);
                     line.add(newArray[j]);
                     line.add(newArray[k]);
                     line.add(newArray[z]);
                     
                     lines.add(line);
                     
                  }
               
               } 
              
            } 
              
         }   
      }
      
      return lines;
   }
  
   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesFast() {
      lines = new TreeSet<Line>();
      
      Point[] newArray = Arrays.copyOf(points, points.length);
      int count1 = 0;
      int count2 = 0;
      
      for (int i = 0; i < points.length; i++) {
         
         Arrays.sort(newArray, points[i].slopeOrder);
         
         for (int j = 0; j < points.length - 1; j = j + count1) {
         
            count1 = 0;
            count2 = j;
         
            while ((count2 < points.length) && (points[i].slopeOrder.compare(newArray[j], newArray[count2]) == 0)) {
            
               count1++;
               count2++;
            
            }
            
            if (count1 >= 3) {
            
               Line line = new Line();
               
               line.add(points[i]);
               
               int count3 = 0;
               while (!(count3 == count1)) {
               
                  line.add(newArray[j + count3]);
                  count3++;
               
               }     
               lines.add(line);
            
            }
         
         }  
      }
      
      return lines;
   }
   
}
