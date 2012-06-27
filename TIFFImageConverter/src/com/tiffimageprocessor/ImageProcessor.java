package com.tiffimageprocessor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.jai.JAI;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.TIFFEncodeParam;

public class ImageProcessor extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2685655382409831974L;

	JFileChooser chooser;
	String choosertitle;
	JButton go;
	JFrame frame;
	
	
	public static void main(String[] foo) {		
		new ImageProcessor();
	}

	

	public int[] getPixelRGB(int pixel) {
		int[] pixelA = new int[3];
		;
		pixelA[0] = (pixel >> 16) & 0xff;
		pixelA[1] = (pixel >> 8) & 0xff;
		pixelA[2] = (pixel) & 0xff;
		return pixelA;
	}

	private void marchThroughImages(List<String> fileName, String directoryName) throws IOException {

		Raster raster[] = new Raster[fileName.size()];
		String message = "";

		for (int i = 0; i < fileName.size(); i++) {
			File file = new File(fileName.get(i));
			SeekableStream s = new FileSeekableStream(file);

			TIFFDecodeParam param = null;

			ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);

			raster[i] = dec.decodeAsRaster();

			message += "Images Processed " + fileName.get(i) + " width: "
					+ raster[i].getWidth() + " height: " + raster[0].getHeight()  + "\n";
			s.close();

		}

		int w = raster[0].getWidth(), h = raster[0].getHeight();
		int[] resultImageArray = new int[w * h];
		

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int[] averagePixel = new int[3];
				for (int k = 0; k < raster.length; k++) {
					// System.out.println("Image " + fileName[k] +
					// " Processing Started");
					int[] pixelA = null;
					pixelA = raster[k].getPixel(i, j, pixelA);
					if (pixelA.length < 3)
						pixelA = getPixelRGB(pixelA[0]);

					averagePixel[0] += pixelA[0];
					averagePixel[1] += pixelA[1];
					averagePixel[2] += pixelA[2];
					// System.out.println("Image " + fileName[k] +
					// " Processing Done ");
				}
				averagePixel[0] = averagePixel[0] / raster.length;
				averagePixel[1] = averagePixel[1] / raster.length;
				averagePixel[2] = averagePixel[2] / raster.length;				
				resultImageArray[j * w + i] = ((averagePixel[0] & 0x0ff) << 16)
						| ((averagePixel[1] & 0x0ff) << 8)
						| (averagePixel[2] & 0x0ff);
			}
		}
		BufferedImage pixelImage = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		pixelImage.setRGB(0, 0, w, h, resultImageArray, 0, w);

		String fileSaved = directoryName +"\\" + System.currentTimeMillis() + ".tif";
		FileOutputStream fileoutput = new FileOutputStream(fileSaved);

		String tiffFlag = System.getProperty("compress_tiff");
		boolean compressTiffs = tiffFlag != null;

		TIFFEncodeParam params = null;
		if (compressTiffs) {
			params = new TIFFEncodeParam();
			params.setCompression(TIFFEncodeParam.COMPRESSION_DEFLATE);
		}

		JAI.create("encode", pixelImage, fileoutput, "TIFF", params);
		fileoutput.close();
		
		JOptionPane.showMessageDialog(frame,message + "Image File Processed and Saved.Saved file name is " + fileSaved);

		
	}


	public ImageProcessor() {
		
		frame = new JFrame("TIFF Image Calibrator");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		frame.getContentPane().add(this, "Center");
		frame.setSize(this.getPreferredSize());
		frame.setVisible(true);
		go = new JButton("Select Folder");
		go.addActionListener(this);
		add(go);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		go.setEnabled(false);

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		String directoryName= "";
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {		
		
			
			List<String> results = new ArrayList<String>();
			File[] files = null;
			try {
				directoryName = chooser.getSelectedFile().getCanonicalPath();
				files = new File(directoryName).listFiles(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						if(name.toLowerCase().endsWith("tif") || name.toLowerCase().endsWith("tiff")) return true;
						else return false;
					}
				});
				
				for (File file : files) {
				    if (file.isFile()) {
				    	System.out.println(file.getCanonicalPath());
				        results.add(file.getCanonicalPath());
				    }
				}
				
				marchThroughImages(results,directoryName);
				
			} catch (IOException e1) {			
				e1.printStackTrace();
			}
			
			
		} else {
			System.out.println("No Selection ");
		}
		
		go.setEnabled(true);
	}

	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

}