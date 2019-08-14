import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FolderDemo {
	
	static BufferedImage getThumbnail(Image image, int width, int height) {
		BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = thumbnail.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return thumbnail;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//File folder = new File();
		JFrame frame = new JFrame("Gallery");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout() );
		JPanel panelsouth = new JPanel( new FlowLayout() );
		JScrollPane scroll = new JScrollPane(panelsouth);
		//List<Image> img = new ArrayList<>();
		ImageView output = new ImageView();
		
		output.setPreferredSize( new Dimension(600, 400) );
		scroll.setPreferredSize( new Dimension(96, 96) );
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		int response = fc.showOpenDialog(fc);
		
		if(response != JFileChooser.APPROVE_OPTION) {
			System.exit(0);
		}
		
		File f = fc.getSelectedFile();
		
		for (File file : f.listFiles()) {
			
			// skips non-image files
			String type = Files.probeContentType(file.toPath());
			if (type == null || !type.startsWith("image/"))
				continue;
			
			BufferedImage Img = ImageIO.read(file);
			BufferedImage tn = getThumbnail(Img, 64, 64);
			
			JLabel label = new JLabel( new ImageIcon(tn) );
			
			label.setPreferredSize( new Dimension(64, 64) );
			
			label.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20) );
			
			panelsouth.add(label);
			
			label.addMouseListener( new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					output.setImage(Img);
					
				}
			});
			/*String name = file.getName();
			String type = Files.probeContentType(file.toPath());  // MIME type
			if (type != null && type.startsWith("image/"))
				System.out.printf("%s (%s)\n", name, type);

			if (name.endsWith(".png"))
				System.out.println(name);*/
		}

		frame.add(output, BorderLayout.CENTER);
		frame.add(scroll,BorderLayout.SOUTH);
		
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);  // put frame in center of the screen
		frame.setVisible(true);
	}
}
//"/Users/cyberninjas/Desktop/Gallery"