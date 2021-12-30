package views;

import controllers.LayoutController;
import managers.AppSDK;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Layout extends JFrame {
    JMenuBar menu;
    JMenu menuUsers;
    JMenu menuMaterials;
    JMenu menuLoans;
    JMenu themeMenu;
    private final ArrayList<JMenuItem> itemsMaterials = new ArrayList<JMenuItem>();
    private final ArrayList<JMenuItem> itemsUsers = new ArrayList<JMenuItem>();
    private final ArrayList<JMenuItem> itemsLoans = new ArrayList<JMenuItem>();
    private final ArrayList<JMenuItem> itemsTheme = new ArrayList<JMenuItem>();
    private final LayoutController controller = new LayoutController(this);
    public Layout() throws IOException {
        super("Gestion Emprunts");
        menu = new JMenuBar();
        menuUsers = new JMenu("Utilisateurs");
        menuMaterials = new JMenu("Matériels");
        menuLoans = new JMenu("Emprunts");
        themeMenu = new JMenu("Theme");
        itemsTheme.add(new JMenuItem("Dark"));
        itemsTheme.add(new JMenuItem("Light"));
        itemsMaterials.add(new JMenuItem("Add"));
        try {
            var data = AppSDK.UsersManager.getUsers();
            System.out.println(data);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        initComponents();
    }
    private void initComponents(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setSize(700,500);
        for (var item :
                itemsTheme) {
            item.addActionListener(e->controller.themeChange(((JMenuItem)e.getSource()).getText()));
            themeMenu.add(item);
        }
        for (var item :
                itemsMaterials) {
            menuMaterials.add(item);
        }
        menu.add(menuUsers);
        menu.add(menuMaterials);
        menu.add(menuLoans);
        menu.add(themeMenu);
        this.setJMenuBar(menu);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab( "Main", new MainView());
        tabs.addTab( "User consultation", new ConsultationUsersView());
        tabs.addTab( "Material back", new JScrollPane() );
        this.add(tabs);
        this.setVisible(true);
    }
}
