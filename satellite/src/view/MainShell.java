/*******************************************************************************
 * Copyright (c) 2018, Antoine DELACROIX.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Antoine DELACROIX - initial API and implementation
 *******************************************************************************/

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
	
	public static final String APPLIDATION_TITLE = "Satellite Application";
	
	public static final String JPG_EXTENSION = "jpg";
	
	public static final String LEFT = "Left";
	
	public static final String RIGHT = "Right";
	
	/**
	 * Complete Image List
	 */
	public List<Image> imageList;
	
	/**
	 * Current Image Index
	 */
	public int index = 0;
	
	/**
	 * Current image
	 */
	public Image image;

	public MainShell() {
		// setup the SWT window
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());
		shell.setText(APPLIDATION_TITLE);
		String imagePath = "";
		Composite parent = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		parent.setLayout(gridLayout);

		// Get the Display default icons
		imageList = new ArrayList<Image>();

		String path = new File(".").getAbsolutePath();
		File runnableFile = new File(path);

		String parentPath = runnableFile.getParentFile().getPath();
		imagePath = parentPath + "//photos";
		File folder = new File(imagePath);
		File[] fList = folder.listFiles();

		for (File file : fList) {
			// check file extension
			String extension = file.getPath().toString().substring(file.getPath().toString().length() - 3);
			if (extension.equals(JPG_EXTENSION)) {
				imageList.add(new Image(display, file.getPath()));
			}
		}

		image = imageList.get(0);

		Label label = new Label(parent, SWT.NONE);
		image = resize(image, 200, 300);
		label.setImage(image);

		Composite buttonsParent = new Composite(parent, SWT.NONE);
		GridLayout buttonsParentGridLayout = new GridLayout(2, true);
		buttonsParent.setLayout(buttonsParentGridLayout);
		Button leftButton = new Button(buttonsParent, SWT.PUSH);
		leftButton.setText(LEFT);
		leftButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				downIndex();
				label.setImage(resize(imageList.get(index), 200, 300));
			}
		});

		Button rightButton = new Button(buttonsParent, SWT.PUSH);
		rightButton.setText(RIGHT);
		rightButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				upIndex();
				label.setImage(resize(imageList.get(index), 200, 300));
			}
		});
		endProgram(shell, display);
	}
	
	public void downIndex() {
		index--;
		if (index < 0) {
			index = imageList.size() - 1;
		}
		System.out.println("index = " + index + " imageList.size() = " + imageList.size());
	}

	public void endProgram(Shell shell, Display display) {
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
	
	public void upIndex() {
		index++;
		if (index > imageList.size() - 1) {
			index = 0;
		}
	}
}
