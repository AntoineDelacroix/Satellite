package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MainShell {
	public int index = 0;
	public Image image;

	public MainShell() {
		// setup the SWT window
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());
		shell.setText("Photo Application");
		String imagePath = "";
		// initialize a parent composite with a grid layout manager
		// with 5x columns
		Composite parent = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		parent.setLayout(gridLayout);

		// Get the Display default icons
		List<Image> imageList = new ArrayList<Image>();

		// String path =
		// MainShell.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println(new File(".").getAbsolutePath());
		String path = new File(".").getAbsolutePath();
		File runnableFile = new File(path);

		String parentPath = runnableFile.getParentFile().getPath();
		System.out.println("path = " + path);
		imagePath = parentPath + "//photos";
		// String decodedPath = URLDecoder.decode(path, "UTF-8");
		File folder = new File(imagePath);
		File[] fList = folder.listFiles();

		for (File file : fList) {
			// check file extension
			String extension = file.getPath().toString().substring(file.getPath().toString().length() - 3);
			if (extension.equals("jpg")) {
				imageList.add(new Image(display, file.getPath()));
				System.out.println(file.getPath() + " added");
			}
		}

		image = imageList.get(0);

		// for (Image image : imageList) {
		Label label = new Label(parent, SWT.NONE);
		image = resize(image, 200, 300);
		label.setImage(image);
		// }
		// show the SWT window

		Composite buttonsParent = new Composite(parent, SWT.NONE);
		GridLayout buttonsParentGridLayout = new GridLayout(2, true);
		buttonsParent.setLayout(buttonsParentGridLayout);
		Button buttonLeft = new Button(buttonsParent, SWT.PUSH);
		buttonLeft.setText("Left");
		buttonLeft.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println("Left");
				// image.dispose();
				index--;
				if (index < 0) {
					index = imageList.size() - 1;
				}
				System.out.println("index = " + index + " imageList.size() = " + imageList.size());
				label.setImage(resize(imageList.get(index), 200, 300));
			}
		});

		Button buttonRight = new Button(buttonsParent, SWT.PUSH);
		buttonRight.setText("Right");
		buttonRight.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println("Right");
				// image.dispose();
				index++;
				if (index > imageList.size() - 1) {
					index = 0;
				}
				System.out.println("index = " + index + " imageList.size() = " + imageList.size());
				label.setImage(resize(imageList.get(index), 200, 300));
			}
		});
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		// tear down the SWT window
		display.dispose();
	}

	private Image resize(Image image, int width, int height) {
		Image scaled = null;
		if (image != null) {
			scaled = new Image(Display.getDefault(), width, height);
			GC gc = new GC(scaled);
			gc.setAntialias(SWT.ON);
			gc.setInterpolation(SWT.HIGH);
			gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
			gc.dispose();
		}
		return scaled;
	}
}
