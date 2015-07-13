package examples;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

public class ImageConvolutionTask {

    public static class Convolution {

        public static BufferedImage processImage(BufferedImage img) throws IOException {
            float ninth = 1.0f / 9.0f;
            float[] blurKernel = {
                    ninth, ninth, ninth,
                    ninth, ninth, ninth,
                    ninth, ninth, ninth
            };

            float[] edgeKernel = {
                    0.0f, -1.0f, 0.0f,
                    -1.0f, 4.0f, -1.0f,
                    0.0f, -1.0f, 0.0f
            };

            BufferedImageOp blur = new ConvolveOp(new Kernel(3, 3, edgeKernel));
            BufferedImage dest = blur.filter(img, null);
            return dest;
        }

    }

    public static void main(String[] args) throws IOException {
        System.out.println("Image convolution task has been started.\n");

        File[] imgs = new File("images").listFiles();
        for(File imgfile : imgs){
            if(imgfile.isDirectory()) continue; // if current file is directory then skip to the next iteration

            System.out.println(imgfile.getName() + " is under processing now.");

            BufferedImage img = ImageIO.read(imgfile);
            BufferedImage result = Convolution.processImage(img);
            ImageIO.write(result, "jpg", new File("images/out/result_" + imgfile.getName()));

            System.out.println("Image " + imgfile.getName() + " has been successfully processed.\n");
        }
    }
}
