package dialogs;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import shapes.Triangle;

/**
 * Class represent {@link JDialog} for adding or updating {@link Triangle}.
 */
public class DlgTriangle extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel mainPanel;
	private JTextField txtXcoordinate;
	private JTextField txtYcoordinate;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JLabel lblXcoordinate;
	private JLabel lblYcoordinate;
	private JLabel lblWidth;
	private JLabel lblHeight;
	private int xCoordinate;
	private int yCoordinate;
	private int width;
	private int height;
	private Color edgeColor;
	private Color interiorColor;
	private Color edgeColorOfTriangle;
	private Color interiorColorOfTriangle;
	private boolean confirmed;
	private JButton btnEdgeColor;
	private JButton btnInteriorColor;
	private int drawWidth;
	private int drawHeight;

	public static void main(String [] arrayOfStrings) {
		try {
			DlgTriangle dialog = new DlgTriangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public DlgTriangle() {
		setModal(true);
		setResizable(false);
		setTitle("Triangle values");
		setBounds(100, 100, 418, 386);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		{
			lblXcoordinate = new JLabel("X coordinate");
			GridBagConstraints gbc_lblXcoordinate = new GridBagConstraints();
			gbc_lblXcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblXcoordinate.gridx = 3;
			gbc_lblXcoordinate.gridy = 2;
			mainPanel.add(lblXcoordinate, gbc_lblXcoordinate);
		}
		{
			txtXcoordinate = new JTextField();
			lblXcoordinate.setLabelFor(txtXcoordinate);
			GridBagConstraints gbc_txtXcoordinate = new GridBagConstraints();
			gbc_txtXcoordinate.anchor = GridBagConstraints.NORTH;
			gbc_txtXcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtXcoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXcoordinate.gridx = 6;
			gbc_txtXcoordinate.gridy = 2;
			mainPanel.add(txtXcoordinate, gbc_txtXcoordinate);
			txtXcoordinate.setColumns(10);
		}
		{
			lblYcoordinate = new JLabel("Y coordinate");
			GridBagConstraints gbc_lblYcoordinate = new GridBagConstraints();
			gbc_lblYcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblYcoordinate.gridx = 3;
			gbc_lblYcoordinate.gridy = 4;
			mainPanel.add(lblYcoordinate, gbc_lblYcoordinate);
		}
		{
			txtYcoordinate = new JTextField();
			lblYcoordinate.setLabelFor(txtYcoordinate);
			GridBagConstraints gbc_txtYcoordinate = new GridBagConstraints();
			gbc_txtYcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtYcoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYcoordinate.gridx = 6;
			gbc_txtYcoordinate.gridy = 4;
			mainPanel.add(txtYcoordinate, gbc_txtYcoordinate);
			txtYcoordinate.setColumns(10);
		}
		{
			lblWidth = new JLabel("width");
			GridBagConstraints gbc_lblWidth = new GridBagConstraints();
			gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidth.gridx = 3;
			gbc_lblWidth.gridy = 6;
			mainPanel.add(lblWidth, gbc_lblWidth);
		}
		{
			txtWidth = new JTextField();
			lblWidth.setLabelFor(txtWidth);
			GridBagConstraints gbc_txtSide = new GridBagConstraints();
			gbc_txtSide.insets = new Insets(0, 0, 5, 5);
			gbc_txtSide.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSide.gridx = 6;
			gbc_txtSide.gridy = 6;
			mainPanel.add(txtWidth, gbc_txtSide);
			txtWidth.setColumns(10);
		}
		{
			lblHeight = new JLabel("height");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 3;
			gbc_lblHeight.gridy = 8;
			mainPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			txtHeight = new JTextField();
			lblHeight.setLabelFor(txtHeight);
			GridBagConstraints gbc_txtHeight = new GridBagConstraints();
			gbc_txtHeight.insets = new Insets(0, 0, 5, 5);
			gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtHeight.gridx = 6;
			gbc_txtHeight.gridy = 8;
			mainPanel.add(txtHeight, gbc_txtHeight);
			txtHeight.setColumns(10);
		}
		{
			btnInteriorColor = new JButton("Choose interior color");
			btnInteriorColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnInteriorColor.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent click) {
					interiorColor = JColorChooser.showDialog(null, "Colors pallete", interiorColorOfTriangle);
					if (interiorColor != null) {
						interiorColorOfTriangle = interiorColor;
						if (interiorColorOfTriangle.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
						else if (interiorColorOfTriangle.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
						btnInteriorColor.setBackground(interiorColorOfTriangle);
					}
				}
			});
			
			btnEdgeColor = new JButton("Choose edge color");
			btnEdgeColor.setForeground(Color.WHITE);
			btnEdgeColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnEdgeColor.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent click) {
					edgeColor = JColorChooser.showDialog(null, "Colors pallete", edgeColorOfTriangle);
					if (edgeColor != null) {
						if (edgeColor.equals(Color.WHITE)) JOptionPane.showMessageDialog(null, "Background is white :D");
						else {
							edgeColorOfTriangle = edgeColor;
							btnEdgeColor.setBackground(edgeColorOfTriangle);
						}
					}
				}
			});
			
			GridBagConstraints gbc_btnEdgeColor = new GridBagConstraints();
			gbc_btnEdgeColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnEdgeColor.gridx = 3;
			gbc_btnEdgeColor.gridy = 10;
			mainPanel.add(btnEdgeColor, gbc_btnEdgeColor);
			GridBagConstraints gbc_btnInteriorColor = new GridBagConstraints();
			gbc_btnInteriorColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnInteriorColor.gridx = 6;
			gbc_btnInteriorColor.gridy = 10;
			mainPanel.add(btnInteriorColor, gbc_btnInteriorColor);
		}
		{
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
			{
				JButton btnConfirm = new JButton("Confirm");
				btnConfirm.setBackground(Color.GREEN);
				btnConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnConfirm.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent click) {
						if (txtXcoordinate.getText().isEmpty() || txtYcoordinate.getText().isEmpty() || txtWidth.getText().isEmpty() || txtHeight.getText().isEmpty())
							JOptionPane.showMessageDialog(getParent(), "Values cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
						else {
							try {	
								xCoordinate = Integer.parseInt(txtXcoordinate.getText());
								yCoordinate = Integer.parseInt(txtYcoordinate.getText());
								width = Integer.parseInt(txtWidth.getText());
								height = Integer.parseInt(txtHeight.getText());
								if(xCoordinate <= 0 || yCoordinate <= 0 || width <= 0 || height <= 0) JOptionPane.showMessageDialog(getParent(), "X and Y coordinates of up left point, side and height of Triangle must be positive numbers!", "Error", JOptionPane.ERROR_MESSAGE);
								else if (width + xCoordinate > drawWidth || height + yCoordinate > drawHeight) JOptionPane.showMessageDialog(null, "The Triangle goes out of drawing!");
								else {
									confirmed = true;
									setVisible(false);
									dispose();
								}
							} catch (NumberFormatException nfe) {
								JOptionPane.showMessageDialog(getParent(),"X and Y coordinates of up left point, side and height of Triangle must be whole numbers!", "Error", JOptionPane.ERROR_MESSAGE);
							} 
						}  
					}
				});

				btnConfirm.setActionCommand("OK");
				buttonsPanel.add(btnConfirm);
				getRootPane().setDefaultButton(btnConfirm);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.setBackground(Color.RED);
				btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnCancel.addMouseListener(new MouseAdapter() {
                	@Override
        			public void mouseClicked(MouseEvent click) {
                		setVisible(false);
						dispose();
					}
				});

				btnCancel.setActionCommand("Cancel");
				buttonsPanel.add(btnCancel);
			}
		}
	}

	/** 
     * {@inheritDoc DlgCircle#write(int, int, int, int)}
	 */
	public void write(int xClick, int yClick, int drawWidth, int drawHeight) {
		txtXcoordinate.setText(String.valueOf(xClick));
		txtXcoordinate.setEnabled(false);
		txtYcoordinate.setText(String.valueOf(yClick));
		txtYcoordinate.setEnabled(false);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
	}

	/**
	 * <h3>Fill up fields with parameters of {@link Triangle} that user want to update.</h3>
	 * 
	 * @param Triangle Represent {@link Triangle} that user want to update.
	 */
	public void fillUp(Triangle Triangle, int drawWidth, int drawHeight) {
		txtXcoordinate.setText(String.valueOf((Triangle.getUpPoint().getXcoordinate())));
		txtYcoordinate.setText(String.valueOf((Triangle.getUpPoint().getYcoordinate())));
		txtHeight.setText(String.valueOf(Triangle.getHeight()));
		txtWidth.setText(String.valueOf(Triangle.getWidth()));
		edgeColorOfTriangle = Triangle.getColor();
		interiorColorOfTriangle = Triangle.getInteriorColor();
		if (interiorColorOfTriangle.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
		else if (interiorColorOfTriangle.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
		btnEdgeColor.setBackground(edgeColorOfTriangle);
		btnInteriorColor.setBackground(interiorColorOfTriangle);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
	}

	/**
	 * {@inheritDoc DlgSquare#deleteButtons()}
	 */
	public void deleteButtons() {
		btnEdgeColor.setVisible(false);
		btnInteriorColor.setVisible(false);
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}

	public int getXcoordinate() {
		return xCoordinate;
	}

	public int getYcoordinate() {
		return yCoordinate;
	}

	public int getTriangleWidth() {
		return width;
	}

	public int getTriangleHeight() {
		return height;
	}

	public Color getEdgeColor() {
		return edgeColorOfTriangle;
	}

	public Color getInteriorColor() {
		return interiorColorOfTriangle;
	}
}