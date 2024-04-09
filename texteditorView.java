package view;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;
import java.io.File;

public class texteditorView extends JFrame {

    JTree tree;
    JTextPane textEditor;
    JScrollPane Scrpane, scrText;
    JScrollPane openedText;
    JPanel mainPane, openPane;
    DefaultMutableTreeNode rootNode;
    JLabel mesLab;

    // Thay thế bằng thư mục của mình
    String pathRoot = "C:\\";

    public texteditorView() {
        initview();
    }

    public void initview() {
        rootNode = new DefaultMutableTreeNode("NOTE");

        tree = new JTree(createNode(pathRoot));
        tree.setRootVisible(false);

        Scrpane = new JScrollPane(tree);
        Scrpane.setBackground(Color.BLACK);
        Scrpane.setOpaque(true);
        Scrpane.setPreferredSize(new Dimension(300, getHeight()));

        textEditor = new JTextPane();
        textEditor.setFont(new Font("Arial", 1, 16));
        textEditor.setEditable(false);

        scrText = new JScrollPane(textEditor);
        scrText.setBackground(Color.BLACK);
        scrText.setOpaque(true);
        scrText.setPreferredSize(new Dimension(500, 700));

        mesLab = new JLabel();
        mesLab.setLayout(null);
        mesLab.setBackground(Color.WHITE);
        mesLab.setOpaque(true);
        mesLab.setVisible(true);

        openPane = new JPanel();
        openPane.setLayout(new BorderLayout());
        openPane.setVisible(true);
        openPane.add(mesLab, BorderLayout.CENTER);
        openPane.setPreferredSize(new Dimension(0, 40));

        openedText = new JScrollPane();
        openedText.setViewportView(openPane);
        openedText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        openedText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        openedText.setPreferredSize(new Dimension(1200, 50));
        openedText.setVisible(true);

        mainPane = new JPanel();
        mainPane.setVisible(true);
        mainPane.setSize(1200, 700);
        mainPane.setBackground(Color.BLACK);
        mainPane.setLayout(new BorderLayout());
        mainPane.setOpaque(true);
        mainPane.add(Scrpane, BorderLayout.WEST);
        mainPane.add(scrText, BorderLayout.CENTER);
        mainPane.add(openedText, BorderLayout.NORTH);

        setTitle("Text Editor");
        setVisible(true);
        setLayout(null);
        setContentPane(mainPane);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public DefaultMutableTreeNode createNode(String path) {
        File file = new File(path);
        DefaultMutableTreeNode parentnode = new DefaultMutableTreeNode(file.getName());
        try {
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    parentnode.add(new DefaultMutableTreeNode(f.getName()));
                } else if (f.isDirectory()) {
                    if (f.listFiles().length > 0) {
                        parentnode.add(createNode(f.getPath()));
                    } else {
                        parentnode.add(new DefaultMutableTreeNode(f.getName()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parentnode;
    }

    public String getText() {
        return textEditor.getText();
    }

    public void addTreeListener(TreeSelectionListener e) {
        tree.addTreeSelectionListener(e);
    }

    public void setContentTextEditor(String document) {
        this.textEditor.setText(document);
        this.textEditor.setEditable(true);
    }

    public void updateTree() {
        tree.removeAll();
    }

    public JLabel getScrollTab() {
        return this.mesLab;
    }

    public void initTabText(JLabel newtab) {
        mesLab.setVisible(false);
        openPane.setVisible(false);

        int count = mesLab.getComponentCount();
        openPane.setPreferredSize(new Dimension(100 * (count + 1), 40));
        mesLab.setPreferredSize(new Dimension(100 * count, 40));
        mesLab.add(newtab);

        openPane.setVisible(true);
        mesLab.setVisible(true);
    }

}
