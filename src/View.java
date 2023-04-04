import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    private JButton btn1, btn2;
    private JTextField textField;
    private String filename;
    private JButton saveButton;

    public View(){
        super("Codage");

        btn1 = new JButton("Encode");
        btn2 = new JButton("Decode");
        saveButton = new JButton("Save");

        textField = new JTextField(20);

        JPanel panelBoutons = new JPanel();
        JPanel pannelSave = new JPanel();
        saveButton.setPreferredSize(new Dimension(100, 50));
        pannelSave.add(saveButton);
        panelBoutons.add(btn1);
        panelBoutons.add(btn2);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelBoutons, BorderLayout.NORTH);
        getContentPane().add(saveButton, BorderLayout.CENTER);
        getContentPane().add(textField, BorderLayout.SOUTH);

        btn1.addActionListener(this);
        btn2.addActionListener(this);


        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Helper helper = new Helper();

        if (e.getSource() == btn1) {
            System.out.println("Encode");
            filename = helper.encodeButtonTriggered(textField.getText());
            System.out.println(textField.getText());
        }
        if (e.getSource() == btn2) {
            System.out.println("Decode");
        }
        if (e.getSource() == saveButton) {

        }
    }
}
