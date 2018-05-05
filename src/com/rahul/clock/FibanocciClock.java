package com.rahul.clock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

public class FibanocciClock {
	private JPanel panel_1_1, panel_1_2, panel_2, panel_3, panel_5;
	private JPanel renderer;
	private int SIZE = 15;
	private Point initialClick;
	private JWindow jWindow;
	private JPopupMenu popup;
	private Random rand = new Random();
	private int[] mColors = { 3780049, 3176875, 12736885, 14766680, 16352347, 8621255, 8218270, 5487540, 5354605,
			14723864, 6519441, 15766192, 12042439 };

	private MouseAdapter adapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			checkPopup(e);
		}

		private void checkPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popup.show(jWindow, e.getX(), e.getY());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			initialClick = e.getPoint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int thisX = jWindow.getLocation().x;
			int thisY = jWindow.getLocation().y;

			int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
			int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

			int X = thisX + xMoved;
			int Y = thisY + yMoved;
			jWindow.setLocation(X, Y);
		}
	};

	Calendar calendar = Calendar.getInstance();

	private boolean isTime = true;
	private boolean h5 = false, h3 = false, h2 = false, h1_1 = false, h1_2 = false;
	private boolean m5 = false, m3 = false, m2 = false, m1_1 = false, m1_2 = false;
	int hour, minute;
	private ActionListener updateClock = (e) -> {
		updateTime();
		updateTimeColor();
	};

	private void updateTime() {
		calendar.setTimeInMillis(System.currentTimeMillis());

		hour = calendar.get(Calendar.HOUR);
		minute = Math.round(calendar.get(Calendar.MINUTE) / 5.0f);
		if (minute == 12) {
			minute = 0;
			hour++;
			if (hour == 12)
				hour = 0;
		}

		h5 = hour >= 5 ? ((hour -= 5) >= 0) : false;
		h3 = hour >= 3 ? ((hour -= 3) >= 0) : false;
		h2 = hour >= 2 ? ((hour -= 2) >= 0) : false;
		h1_1 = hour >= 1 ? ((hour -= 1) >= 0) : false;
		h1_2 = hour >= 1 ? ((hour -= 1) >= 0) : false;

		m5 = minute >= 5 ? ((minute -= 5) >= 0) : false;
		m3 = minute >= 3 ? ((minute -= 3) >= 0) : false;
		m2 = minute >= 2 ? ((minute -= 2) >= 0) : false;
		m1_1 = minute >= 1 ? ((minute -= 1) >= 0) : false;
		m1_2 = minute >= 1 ? ((minute -= 1) >= 0) : false;
	}

	boolean change_p_1_1, change_p_1_2, change_p_2, change_p_3, change_p_5;

	private void updateTimeColor() {
		if (!isTime)
			return;
		if (!setColor(h1_1, m1_1).equals(panel_1_1.getBackground()) && !change_p_1_1)
			new Thread(() -> {
				change_p_1_1 = true;
				changeTimeColor(setColor(h1_1, m1_1), panel_1_1);
				change_p_1_1 = false;
			}).start();

		if (!setColor(h1_2, m1_2).equals(panel_1_2.getBackground()) && !change_p_1_2)
			new Thread(() -> {
				change_p_1_2 = true;
				changeTimeColor(setColor(h1_2, m1_2), panel_1_2);
				change_p_1_2 = false;
			}).start();

		if (!setColor(h2, m2).equals(panel_2.getBackground()) && !change_p_2)
			new Thread(() -> {
				change_p_2 = true;
				changeTimeColor(setColor(h2, m2), panel_2);
				change_p_2 = false;
			}).start();

		if (!setColor(h3, m3).equals(panel_3.getBackground()) && !change_p_3)
			new Thread(() -> {
				change_p_3 = true;
				changeTimeColor(setColor(h3, m3), panel_3);
				change_p_3 = false;

			}).start();

		if (!setColor(h5, m5).equals(panel_5.getBackground()) && !change_p_5)
			new Thread(() -> {
				change_p_5 = true;
				changeTimeColor(setColor(h5, m5), panel_5);
				change_p_5 = false;
			}).start();
	}

	private void changeTimeColor(Color to, JPanel panel) {
		Color from = panel.getBackground();
		int steps = 150;

		int dRed = to.getRed() - from.getRed();
		int dGreen = to.getGreen() - from.getGreen();
		int dBlue = to.getBlue() - from.getBlue();

		try {
			if (dRed != 0 || dGreen != 0 || dBlue != 0) {
				for (int i = 0; i <= steps; i++) {
					if (!isTime)
						break;
					panel.setBackground(new Color(// Color change
							from.getRed() + ((dRed * i) / steps), // Red
							from.getGreen() + ((dGreen * i) / steps), // Green
							from.getBlue() + ((dBlue * i) / steps))); // Blue

					Thread.sleep(10);
				}
			}
		} catch (InterruptedException e) {
			panel.setBackground(to);
		}
	}

	private Color setColor(boolean h, boolean m) {
		return h && m ? Color.blue : h ? Color.red : m ? Color.green : Color.gray;
	}

	Timer t;

	public FibanocciClock() {
		jWindow = new JWindow();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jWindow.setLocation((dim.width / 2) - (SIZE * 4), (dim.height / 2) - ((SIZE * 5) / 2));
		renderer = new JPanel();
		renderer.setLayout(null);
		jWindow.add(renderer);

		init();
		updateSize(SIZE);
		t = new Timer(1000, updateClock);
		t.start();

		jWindow.addMouseListener(adapter);
		jWindow.addMouseMotionListener(adapter);
		jWindow.setVisible(true);
	}

	private void updateSize(int size) {
		SIZE = size;
		jWindow.setSize(SIZE * 8, SIZE * 5);
		renderer.setSize(SIZE * 8, SIZE * 5);
		updateFrame();
	}

	private void init() {
		panel_1_1 = new JPanel();
		panel_1_1.setBounds(SIZE * 2, 0, SIZE, SIZE);
		panel_1_1.setBackground(Color.gray);
		panel_1_1.setBorder(new EtchedBorder());
		renderer.add(panel_1_1);

		panel_1_2 = new JPanel();
		panel_1_2.setBounds(SIZE * 2, SIZE, SIZE, SIZE);
		panel_1_2.setBackground(Color.gray);
		panel_1_2.setBorder(new EtchedBorder());
		renderer.add(panel_1_2);

		panel_2 = new JPanel();
		panel_2.setBounds(0, 0, SIZE * 2, SIZE * 2);
		panel_2.setBackground(Color.gray);
		panel_2.setBorder(new EtchedBorder());
		renderer.add(panel_2);

		panel_3 = new JPanel();
		panel_3.setBounds(0, SIZE * 2, SIZE * 3, SIZE * 3);
		panel_3.setBackground(Color.gray);
		panel_3.setBorder(new EtchedBorder());
		renderer.add(panel_3);

		panel_5 = new JPanel();
		panel_5.setBounds(SIZE * 3, 0, SIZE * 5, SIZE * 5);
		panel_5.setBackground(Color.gray);
		panel_5.setBorder(new EtchedBorder());
		renderer.add(panel_5);
		popup = new JPopupMenu();
		popup.setBackground(new Color(0, 0, 0, 0));
		popup.setOpaque(true);
		JMenuItem item = new JMenuItem("Resize");

		item.addActionListener((e) -> {
			showResizeWindow();
		});

		popup.add(item);
		popup.addSeparator();
		JMenuItem animation = new JMenuItem("Start Animation");

		animation.addActionListener((e) -> {
			if (e.getActionCommand().equals("Start Animation")) {
				animation.setText("Stop Animation");
				animation.setActionCommand("Stop Animation");
				startAnimation();
			} else {
				animation.setText("Start Animation");
				animation.setActionCommand("Start Animation");
				stopAnimation();
			}
		});

		popup.add(animation);
		popup.addSeparator();
		JCheckBoxMenuItem onTop = new JCheckBoxMenuItem("Always on top");

		onTop.addItemListener((e) -> {
			jWindow.setAlwaysOnTop(e.getStateChange() == ItemEvent.SELECTED);
		});

		popup.add(onTop);

		popup.addSeparator();
		item = new JMenuItem("Opacity");
		item.addActionListener((e) -> {
			changeOpacity();
		});
		popup.add(item);

		popup.addSeparator();
		item = new JMenuItem("Close");
		item.addActionListener((e) -> {
			System.exit(0);
		});
		popup.add(item);

	}

	private int currentOpacity = 100;

	private void changeOpacity() {
		JOptionPane optionPane = new JOptionPane();
		JSlider slider = getSlider(optionPane);
		optionPane.setMessage(new Object[] { "Change Opacity", slider });
		optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = optionPane.createDialog(jWindow.getRootPane(), "Change Opacity");
		dialog.setVisible(true);
	}

	JSlider getSlider(final JOptionPane optionPane) {
		JSlider slider = new JSlider();
		slider.setMajorTickSpacing(10);
		slider.setMinimum(20);
		slider.setValue(currentOpacity);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		ChangeListener changeListener = (changeEvent) -> {
			JSlider theSlider = (JSlider) changeEvent.getSource();
			currentOpacity = theSlider.getValue();
			jWindow.setOpacity((currentOpacity / 100f));
		};
		slider.addChangeListener(changeListener);
		return slider;
	}

	private void showResizeWindow() {
		boolean flag = jWindow.isAlwaysOnTop();
		if (flag)
			jWindow.setAlwaysOnTop(false);

		JPanel p = new JPanel(new BorderLayout(5, 5));
		JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
		labels.add(new JLabel("Size", SwingConstants.RIGHT));
		p.add(labels, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		JFormattedTextField fWidth = new JFormattedTextField(formatter);
		fWidth.setValue(SIZE);
		controls.add(fWidth);
		p.add(controls, BorderLayout.CENTER);
		JOptionPane.showMessageDialog(jWindow.getRootPane(), p, "Resize", JOptionPane.QUESTION_MESSAGE);

		int size = Integer.parseInt(fWidth.getText());

		if (size > 5)
			updateSize(size);
		else
			JOptionPane.showMessageDialog(jWindow.getRootPane(), "Please set a size greater than 5", "Size too small",
					JOptionPane.ERROR_MESSAGE);
		jWindow.setAlwaysOnTop(flag);
	}

	private void updateFrame() {
		panel_1_1.setBounds(SIZE * 2, 0, SIZE, SIZE);
		panel_1_2.setBounds(SIZE * 2, SIZE, SIZE, SIZE);
		panel_2.setBounds(0, 0, SIZE * 2, SIZE * 2);
		panel_3.setBounds(0, SIZE * 2, SIZE * 3, SIZE * 3);
		panel_5.setBounds(SIZE * 3, 0, SIZE * 5, SIZE * 5);
		renderer.updateUI();
	}

	private Color randomColor() {
		int randomNumber = rand.nextInt(mColors.length);
		return new Color(mColors[randomNumber]);
	}

	private boolean endAnimation = false;

	private void changeColor(Color to, JPanel panel) {
		Color from = panel.getBackground();
		int steps = 150;

		int dRed = to.getRed() - from.getRed();
		int dGreen = to.getGreen() - from.getGreen();
		int dBlue = to.getBlue() - from.getBlue();

		try {
			if (dRed != 0 || dGreen != 0 || dBlue != 0) {
				for (int i = 0; i <= steps; i++) {
					if (endAnimation)
						break;
					panel.setBackground(new Color(// Color change
							from.getRed() + ((dRed * i) / steps), // Red
							from.getGreen() + ((dGreen * i) / steps), // Green
							from.getBlue() + ((dBlue * i) / steps))); // Blue

					Thread.sleep(10);
				}
			}
		} catch (InterruptedException e) {
			panel.setBackground(to);
		}
	}

	private void startAnimation() {
		isTime = false;
		endAnimation = false;
		new Thread(() -> {
			Color close = randomColor();
			while (!endAnimation) {
				changeColor(close, panel_1_1);
				close = randomColor();
			}
		}).start();

		new Thread(() -> {
			Color close = randomColor();
			while (!endAnimation) {
				changeColor(close, panel_1_2);
				close = randomColor();
			}
		}).start();

		new Thread(() -> {
			Color close = randomColor();
			while (!endAnimation) {
				changeColor(close, panel_2);
				close = randomColor();
			}
		}).start();

		new Thread(() -> {
			Color close = randomColor();
			while (!endAnimation) {
				changeColor(close, panel_3);
				close = randomColor();
			}
		}).start();

		new Thread(() -> {
			Color close = randomColor();
			while (!endAnimation) {
				changeColor(close, panel_5);
				close = randomColor();
			}
		}).start();
	}

	private void stopAnimation() {
		endAnimation = true;
		isTime = true;
	}

	public static void main(String[] args) {
		new FibanocciClock();
	}
}
