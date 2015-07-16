package experiments;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Epanchee on 26.02.15.
 */
public class Img2Gray_OpenCV_single {

    public static class Img2GrayClass {

        public void run(){
            Mat mat = Highgui.imread("/root/mipr/best.jpg");

            Mat mat1 = new Mat(mat.height(),mat.width(), CvType.CV_8UC1);
            Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);

            Highgui.imwrite("/root/grayImg/_gray.jpg", mat1);
        }

    }

    public static void main(String[] args) {
        System.out.println("OpenCV started");

        System.load("/usr/local/share/OpenCV/java/libopencv_java2411.so");
        new Img2GrayClass().run();

        System.out.println("OpenCV finished");
    }
}