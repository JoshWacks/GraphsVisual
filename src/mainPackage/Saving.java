package mainPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Saving {
    private static Robot r;

    static {
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getGraphImage() {//Used to get a screenshot of the actual graph
        BufferedImage image=null;
        try {
            Thread.sleep(120);


            // Used to get ScreenSize and capture image
            Rectangle rectangle=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            rectangle.width= (int) (rectangle.width*0.6);
            rectangle.height= (int) (rectangle.height*0.7);
            rectangle.x=0;
            rectangle.y=230;

            image = r.createScreenCapture(rectangle);


        }
        catch ( InterruptedException ex) {
            System.out.println(ex);
        }

        return image;
    }

    private BufferedImage getResultsImage(){//used to get a screenshot of the results canvas
        BufferedImage image=null;
        try {
            Thread.sleep(120);


            // Used to get ScreenSize and capture image
            Rectangle rectangle=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            rectangle.width= 567;
            rectangle.height= 400;
            rectangle.x=823;
            rectangle.y=100;

            image = r.createScreenCapture(rectangle);


        }
        catch ( InterruptedException ex) {
            System.out.println(ex);
        }

        return image;
    }

    public void performSave() throws IOException {
        File savesdirectory=new File("Graphs_Visual_Saves");
        savesdirectory.mkdir();

        File newDirectory=new File(System.getProperty("user.dir")+"/"+savesdirectory.getPath()+"/Graph_Session");
        int session=1;
        while(!newDirectory.mkdir()){
            newDirectory=new File(System.getProperty("user.dir")+"/"+savesdirectory.getPath()+"/Graph_Session"+session);
            session++;
        }

        String currentDirectory=newDirectory.getPath();
        System.out.println(currentDirectory);

        File file=new File(currentDirectory+"/saves.png");
        

        int saveVersion=1;
        while(!file.createNewFile()){
            file=new File(currentDirectory+"/saves"+saveVersion+".png");
            saveVersion++;
        }

        File resultsFile=new File(currentDirectory+"/results.png");
        ImageIO.write(getGraphImage(), "png", file);
        ImageIO.write(getResultsImage(),"png",resultsFile);
    }
}
