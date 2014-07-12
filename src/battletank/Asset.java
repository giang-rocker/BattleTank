/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Administrator
 */
public  class Asset {
      public static  String path_asset = Paths.get("").toAbsolutePath().toString()+"/src/battletank/" + "Asset/";
    
    // directory
     public static String path_board = path_asset+"board.jpg" ;
         public static String path_board_lg = path_asset+"boardmd.png" ;
     public static String path_tankA = path_asset+"tankAmd.png" ;
       public static String path_tankB = path_asset+"tankBmd.png" ;
        public static String path_fire = path_asset+"firemd.png" ;
          public static String path_dead = path_asset+"dead.png" ;
       
    // image
   
     public static MyImage board = new MyImage(path_board);
       public static MyImage boardlg = new MyImage(path_board_lg);
     public static MyImage tankA = new MyImage(path_tankA);
      public static MyImage tankB = new MyImage(path_tankB);
            public static MyImage fire = new MyImage(path_fire);
              public static MyImage dead = new MyImage(path_dead);

}
