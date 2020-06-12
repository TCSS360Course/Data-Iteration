import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;


public class AppGUI {

    private static final String DEVELOPERS =
                    "Tyler Phippen, Nicola Daoud, Gyubeom Kim, and Jun Kim";

    /**
     * This is frame.
     */
    private JFrame myFrame;

    /**
     * This is file chooser.
     */
    private JFileChooser myFileChooser;

    /**
     * ...
     */
    private double myVersionNumb;
    
    /**
     * 
     */
    private String userFirstName;
    
    /**
     * 
     */
    private String userEmail;
    
    /**
     * 
     */
    private String adminId;
    
    /**
     * 
     */
    private String adminPass;
    
    /**
     * 
     */
    private boolean adminStatus;
    
    /**
     * ...
     */
    public AppGUI() {
        this.myFrame = new JFrame("User Guide");
        this.myFileChooser = new JFileChooser(".");
        this.myVersionNumb = 1.0;
        this.userFirstName = "";
        this.userEmail = "";
        this.adminId = "";
        this.adminPass = "";
        this.adminStatus = false;
        myFrame.setBounds(100, 100, 1528, 894);
    }
    
    /**
     * ...
     */
    public void run() {
        createPanel();
        createMenu(this.myFrame);
        centerWindow(this.myFrame);
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * ...
     */
    private void createPanel() {
        myFrame.getContentPane().setLayout(new BorderLayout(0, 0));
        final JPanel mainPanel = new JPanel();
        final JPanel centPanel = new JPanel();
        final JPanel westSidePanel = new JPanel();
        final JPanel southSidePanel = new JPanel();
        final JPanel eastSidePanel = new JPanel();
        
        centPanel.setBounds(261, 0, 1017, 809);
        westSidePanel.setBounds(0, 0, 261, 809);
        southSidePanel.setBounds(0, 811, 1528, 39);
        eastSidePanel.setBounds(1278, 0, 250, 809);

        westSidePanel.setBackground(Color.WHITE);
        southSidePanel.setBackground(Color.GRAY);
        eastSidePanel.setBackground(Color.WHITE);

        mainPanel.setLayout(null);
        eastSidePanel.setLayout(null);
        
        mainPanel.add(centPanel);
        mainPanel.add(westSidePanel);
        mainPanel.add(eastSidePanel);
        mainPanel.add(southSidePanel);

        westSidePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        southSidePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        centerPdfView(centPanel);
        createEastSideButtons(eastSidePanel);
        createSouthSideButtons(southSidePanel);

        this.myFrame.getContentPane().add(mainPanel);

    }

    private void centerPdfView(JPanel theCentSidePanel) {
        String filepath = "/Users/GyubeomKim/Desktop/v8_absolute.pdf";

        // build a controller
        SwingController controller = new SwingController();

        // Build a SwingViewFactory configured with the controller
        SwingViewBuilder factory = new SwingViewBuilder(controller);
        JPanel viewerComponentPanel = factory.buildViewerPanel();
        
        viewerComponentPanel.setPreferredSize(new Dimension(1017, 809));
        viewerComponentPanel.setMaximumSize(new Dimension(1017, 809));
        
        // add copy keyboard command
        ComponentKeyBinding.install(controller, viewerComponentPanel);

        // add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
        new org.icepdf.ri.common.MyAnnotationCallback(
        controller.getDocumentViewController()));

        theCentSidePanel.add(viewerComponentPanel);
        System.getProperties().put("org.icepdf.core.scaleImages", "true"); 
        // Open a PDF document to view
         controller.openDocument(filepath);
    }
    
    /**
     * ...
     * @param theFrame ...
     */
    private void createMenu(final JFrame theFrame) {
        JMenuBar menuBar = new JMenuBar();
        theFrame.setJMenuBar(menuBar);
        JMenu more = new JMenu("More");
        final JMenuItem userSetting = new JMenuItem("User Setting");
        final JMenuItem about = new JMenuItem("About");
        final JMenuItem admin = new JMenuItem("Administrator");
        more.add(admin);
        more.add(userSetting);
        more.add(about);

        userSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(5, 5));

                JPanel labels = new JPanel(new BorderLayout());
                labels.add(new JLabel("Name", SwingConstants.RIGHT),BorderLayout.NORTH);
                labels.add(new JLabel("Email:", SwingConstants.RIGHT), BorderLayout.SOUTH);
                panel.add(labels, BorderLayout.WEST);

                JPanel controls = new JPanel(new BorderLayout());
                JTextField name = new JTextField();
                controls.add(name, BorderLayout.NORTH);
                name.setPreferredSize(new Dimension(200, 20));
                JTextField email = new JTextField();
                email.setPreferredSize(new Dimension(200, 20));
                controls.add(email, BorderLayout.SOUTH);
                panel.add(controls, BorderLayout.CENTER);
                int save = JOptionPane.showConfirmDialog(null, panel, "User Setting", JOptionPane.OK_CANCEL_OPTION);
                if(save == 0) {
                    userFirstName = name.getText();
                    userEmail = email.getText();
                    FileWriter toFile = null;
                    try {
                        toFile = new FileWriter(new File("userInfo.txt"), true);
                        toFile.append("First Name: " + userFirstName + ", " + "Email: " + userEmail + "\n");
                        toFile.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
        
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(5, 5));

                JPanel labels = new JPanel(new BorderLayout());
                labels.add(new JLabel("ID", SwingConstants.RIGHT),BorderLayout.NORTH);
                labels.add(new JLabel("Password:", SwingConstants.RIGHT), BorderLayout.SOUTH);
                panel.add(labels, BorderLayout.WEST);

                JPanel controls = new JPanel(new BorderLayout());
                JTextField id = new JTextField();
                controls.add(id, BorderLayout.NORTH);
                id.setPreferredSize(new Dimension(200, 20));
                JTextField password = new JTextField();
                password.setPreferredSize(new Dimension(200, 20));
                controls.add(password, BorderLayout.SOUTH);
                panel.add(controls, BorderLayout.CENTER);
                int log = JOptionPane.showConfirmDialog(null, panel, "Admin. Log-in", JOptionPane.OK_CANCEL_OPTION);
                if(log == 0) {
                    adminId = id.getText();
                    adminPass = password.getText();
                    if(adminId.equals("admin") && adminPass.equals("1212")) {
                        adminStatus = true;
                        System.out.println(adminStatus);
                    }
                }
            }
        });
        
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(null, aboutMessage(), "Developers",
                                              JOptionPane.PLAIN_MESSAGE);
            }
        });
        menuBar.add(more);
    }
    
    /**
     * ...
     * @return ...
     */
    private String aboutMessage() {
        return DEVELOPERS + "\n                                    Version: "
               + this.myVersionNumb;
    }

    /**
     * ...
     * 
     * @param theVersionNumb ...
     */
    public void setVersionNumb(final double theVersionNumb) {
        this.myVersionNumb = theVersionNumb;
    }

    /**
     * ...
     * 
     * @param theSouthSidePanel ...
     */
    private void createSouthSideButtons(final JPanel theSouthSidePanel) {
        final JButton importButton = new JButton("Import");
        final JButton exportButton = new JButton("Export");
        importButton.setBackground(Color.WHITE);
        exportButton.setBackground(Color.WHITE);
        theSouthSidePanel.add(importButton);
        theSouthSidePanel.add(exportButton);
    }
    
    private boolean getAdminStatus() {
        return this.adminStatus;
    }
    
    /**
     * ...
     * @param theEasthSidePanel ...
     */
    private void createEastSideButtons(final JPanel theEasthSidePanel) {
        JButton addButton = new JButton("ADD");
        addButton.setBounds(16, 207, 117, 29);
        theEasthSidePanel.add(addButton);

        JButton removeButton = new JButton("REMOVE");
        removeButton.setBounds(127, 207, 117, 29);
        theEasthSidePanel.add(removeButton);

        JLabel selectLabel = new JLabel("Select an item to add to or remove");
        selectLabel.setBounds(16, 99, 228, 16);
        theEasthSidePanel.add(selectLabel);

        JTextField input = new JTextField();
        input.setBounds(78, 139, 130, 26);
        theEasthSidePanel.add(input);
        input.setColumns(10);

        JLabel itemLabel = new JLabel("Item:");
        itemLabel.setBounds(31, 144, 61, 16);
        theEasthSidePanel.add(itemLabel);
    }
    
    
    /**
     * It makes the frame to locate in center of window screen.
     * 
     * @param theFrame representing my frame
     */
    private void centerWindow(final Window theFrame) {
        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        final int x = (int) ((dimension.getWidth() - theFrame.getWidth()) / 2);
        final int y = (int) ((dimension.getHeight() - theFrame.getHeight()) / 2);
        theFrame.setLocation(x, y);
    }
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppGUI window = new AppGUI();
                    window.run();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
