import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class View extends JFrame {
    public View() {


        //creation d'une fenetre
        super();
        Helper helper = new Helper();

        JButton button = new JButton("Clique ici");
        JButton buttonBuild = new JButton("Build Card");


        button.setBounds(130, 100, 100, 40);
        buttonBuild.setBounds(230, 100, 100, 40);

        super.add(buttonBuild);
        super.add(button);
        super.setSize(400, 500);
        super.setLayout(null);
        super.setVisible(true);
        buttonBuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<Integer, Carte> cards = helper.buildAllCard();

                System.out.println(cards);

            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ici");
                ArrayList<Integer> messageEnInt = helper.strToInt("bonjour");
                System.out.println(helper.getCONVERT_MAP());

            }
        });
    }


}
