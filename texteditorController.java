package controller;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import models.textEditor;
import view.texteditorView;

public class texteditorController {

    ArrayList<autoSave> list = new ArrayList<>();
    int count = 0;

    texteditorView view;

    public texteditorController(texteditorView view) {
        this.view = view;
        view.addTreeListener(new treeListener());
    }

    class treeListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            TreePath p = e.getPath();
            Object[] objPath = p.getPath();
            ArrayList<String> pathFile = new ArrayList<>();
            for (Object get : objPath) {
                pathFile.add(get.toString());
            }
            // thay thế bằng path của mình
            String pa = "C:\\" + changetoPath(pathFile);

            if (checkIsFile(pa)) {
                try {
                    if (checkIsOpen(pa) == false) {
                        String NewName = "file" + count;
                        File f = new File(pa);
                        JLabel newtab = new JLabel(f.getName());
                        newtab.setName(pa);
                        if (view.getScrollTab().getComponentCount() > 0) {
                            int count = view.getScrollTab().getComponentCount();
                            newtab.setBounds(count * 100, 0, 100, 30);
                        } else {
                            newtab.setBounds(0, 0, 100, 30);
                        }
                        newtab.setHorizontalAlignment(SwingConstants.CENTER);
                        newtab.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

                        newtab.addMouseListener(new MouseListener() {

                            @Override
                            public void mouseClicked(MouseEvent e) {
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {

                                try {
                                    System.out.println(pa);
                                    openExist(pa);
                                } catch (Exception er) {
                                    er.printStackTrace();
                                }

                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {

                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                            }

                        });
                        newtab.setVisible(true);

                        view.initTabText(newtab);

                        String content = ReadFile(pa);
                        autoSave ats = new autoSave(new textEditor(NewName, content, pa));
                        view.setContentTextEditor(content);
                        list.add(ats);
                        ats.start();
                        stopOtherFile(pa);
                        count++;

                    } else {
                        stopOtherFile(pa);
                        String content = ReadFile(pa);
                        view.setContentTextEditor(content);
                    }
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        }

        public void openExist(String path) throws IOException {
            boolean isopen = false;
            for (autoSave a : list) {
                if (a.check != false) {
                    isopen = true;
                    break;
                }
            }
            if (isopen) {
                stopOtherFile(path);
                String content = ReadFile(path);
                view.setContentTextEditor(content);
            }
        }

        public boolean checkIsFile(String path) {
            File f = new File(path);
            if (f.isFile()) {
                return true;
            }
            return false;
        }

        public String changetoPath(ArrayList<String> path) {
            String result = path.stream().collect(Collectors.joining("\\"));
            return result;
        }

    }

    public String ReadFile(String path) throws IOException {
        String text = "";
        FileReader f = new FileReader(path);
        int c = 0;
        while ((c = f.read()) != -1) {

            text += (char) c;

        }
        return text;
    }

    class autoSave extends Thread {

        textEditor model;

        boolean check = true;

        autoSave(textEditor model) {
            this.model = model;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(700);

                    if (check) {
                        File file = new File(model.getPath());
                        FileWriter fo = new FileWriter(file);
                        for (int i = 0; i < view.getText().length(); i++) {
                            fo.write(view.getText().charAt(i));
                        }
                        System.out.println(model.getName());
                        fo.close();
                    } else {
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public boolean checkIsOpen(String path) {
        boolean check = false;
        for (autoSave a : list) {
            if (a.model.getPath().equals(path)) {
                check = true;
            }
        }
        return check;
    }

    public void stopOtherFile(String path) {
        try {
            for (autoSave a : list) {
                if (a.model.getPath().equals(path) == false) {
                    a.check = false;
                } else {
                    a.check = true;
                }
            }
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

    }

    public void closeText(String path) {
        for (autoSave a : list) {
            if (a.model.getPath().equals(path)) {
                list.remove(a);
                break;
            }
        }
    }

}
