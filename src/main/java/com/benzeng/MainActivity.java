package com.benzeng;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainActivity extends JFrame {

    public JTable table1;

    private JPanel buttonsPanel;
    private JButton newButton;
    private JButton saveButton;

    ObjectTableModel<animeData> tableModel;
    List<animeData> data;

    public JTable getTable1() {
        return this.table1;
    }

    public static void main() { //Frame UI setup
        MainActivity mainActivity = new MainActivity();

        //JFrame frame = createFrame();
        //ObjectTableModel<animeData> tableModel = new DisplayableObjectTableModel<>(animeData.class);

        //tableModel.setObjectRows(getAnime());
        //JTable table = new JTable(tableModel);
        //mainActivity.table1.setModel(tableModel);
        // initDeptComboBoxEditor(mainActivity.table1);
        mainActivity.setVisible(true);

        /*
        JScrollPane pane = new JScrollPane(table);
        frame.add(pane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() { //On close, the app will save data to database
            @Override
            public void windowClosing(WindowEvent e) {
                var model = ((JTable)((JViewport)((JScrollPane)((JPanel)((JLayeredPane)((JRootPane)((JFrame)
                        e.getSource()).getComponent(0)).getComponent(1)).getComponent(0)).getComponent(0)).
                        getComponent(0)).getComponent(0)).getModel(); //Call the table.
                List<String> sqls = new ArrayList<>();

                for(int i = 0; i < model.getRowCount(); i++){
                        String name = model.getValueAt(i, 0).toString();
                        String status = model.getValueAt(i, 1).toString();
                        String season = model.getValueAt(i, 2).toString();
                        String episodes = model.getValueAt(i, 3).toString();
                        String rating = model.getValueAt(i, 4).toString();
                        String s = String.format("('%s', '%s', '%s', '%s', '%s')", name, status, season, episodes, rating);
                        sqls.add(s); //Formats the message to databse
                }
                String sql = "INSERT INTO anime(animeName, status, seasons, episodes, rating) VALUES "
                        + String.join(",", sqls) + ";"; //Upload to MySQL database

                Connection connection = null;
                try {
                    // below two lines are used for connectivity.
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/bzdb",
                            "root", "tigertiger");

                    Statement statement;
                    statement = connection.createStatement();
                    statement.executeUpdate("DELETE FROM anime;"); //Delete any excess lines.
                    statement.executeUpdate(sql); //Send message
                    statement.close();
                    connection.close();
                }
                catch (Exception exception) {
                    System.out.println(exception);
                }
                System.exit(0); //exit
            }
        });
        */
    }

    /**
     * CONSTRUCTOR: create the frame.
     */
    public MainActivity() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(450, 190, 1024, 768);
        this.setResizable(false);

        // MenuBar
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Save");
        JMenuItem m12 = new JMenuItem("Refresh");
        JMenuItem m13 = new JMenuItem("Close");
        m1.add(m11);
        m1.add(m12);
        m1.add(m13);
        JMenuItem m21 = new JMenuItem("About...");
        m2.add(m21);

        // Table
        tableModel = new DisplayableObjectTableModel<>(animeData.class);
        this.data = getAnime();
        tableModel.setObjectRows(this.data);
        table1 = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(200, 201, 210);
                Color whiteColor = Color.WHITE;
                if(!comp.getBackground().equals(getSelectionBackground())) {
                    Color c = (row % 2 == 0 ? alternateColor : whiteColor);
                    comp.setBackground(c);
                    c = null;
                }
                return comp;
            }
        };
        table1.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table1.setShowGrid(true);
        table1.setShowVerticalLines(true);
        table1.setGridColor(Color.gray);
        table1.setRowHeight(25);
        JComboBox comboBox = new JComboBox(animeData.STATUS_LIST);
        DefaultCellEditor editor = new DefaultCellEditor(comboBox);
        TableColumn column = table1.getColumnModel().getColumn(animeData.STATUS_INDEX);
        column.setCellEditor(editor);

        // Table scroll pane - need to JScrollPane to JFrame, otherwise table header fails to show
        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.setPreferredSize(new Dimension(380,280));

        // Buttons panel
        buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        newButton = new JButton("Add");
        newButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        buttonsPanel.add(newButton);
        buttonsPanel.add(saveButton);
        newButton.addActionListener(e -> {
            this.data.add(new animeData());
            this.tableModel.fireTableDataChanged();
        });

        saveButton.addActionListener(e -> {
            // Have ID column hidden, or readonly
            // Loop through this.data,
            // If ID does not exist, execute INSERT INTO ... VALUES ... statements;
            // If ID exists, execute UPDATE ... SET ... statements;
        });

        // Top panel
        JPanel mbPanel = new JPanel(new BorderLayout());
        mbPanel.add(BorderLayout.NORTH, mb);
        JLabel tableLabel = new JLabel("Current Anime Data", SwingConstants.CENTER);
        tableLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableLabel.setBorder(new EmptyBorder(10,0,10,0));
        mbPanel.add(BorderLayout.CENTER, tableLabel);

        // Lay out the frame contents
        this.getContentPane().add(BorderLayout.NORTH, mbPanel);
        this.getContentPane().add(BorderLayout.CENTER, scrollPane);
        this.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);

        this.setVisible(true);

        /*
             private l finaString[] columnNames = {"String", "Integer", "Boolean"};
             private final Object[][] data = {{"Tutorials Point", 100, true},
                                              {"Tutorix", 200, false},
                                              {"Tutorials Point", 300, true},
                                              {"Tutorix", 400, false}};
             private final TableModel model = new DefaultTableModel(data, columnNames) {
                 @Override
                 public Class getColumnClass(int column) {
                     return getValueAt(0, column).getClass();
                 }
             };
             private final JTable table = new JTable(model);
             private final JScrollPane scrollPane = new JScrollPane(table);

         JCheckBox check = new JCheckBox("JTableHeader visible: ", true);
         check.addActionListener(ae -> {
             JCheckBox cb = (JCheckBox) ae.getSource();
             scrollPane.getColumnHeader().setVisible(cb.isSelected());
             scrollPane.revalidate();
         });
         */
    }

    public static List<animeData> getAnime() { //Calls getAnime().
        final List<animeData> list = new ArrayList<>();
        Connection connection = null; //SQL connection
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");

//            connection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/AnimeTracker",
//                    "root", "tigertiger");

            ResourceBundle bundle = ResourceBundle.getBundle("mysql");
            connection = DriverManager.getConnection(bundle.getString("mysql.url"),
                    bundle.getString("mysql.username"), bundle.getString("mysql.password"));

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from anime");
            int code;
            String title;
            while (resultSet.next()) {
                animeData e = new animeData(); //table alignment
                e.setAnimeName(resultSet.getString("animeName"));
                e.setStatus(resultSet.getString("status"));
                e.setSeasonCount(resultSet.getString("seasons"));
                e.setEpisodeCount(resultSet.getString("episodes"));
                e.setRating(resultSet.getString("rating"));
                list.add(e);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {

            }
        }
        return list;
    }



    private static void initDeptComboBoxEditor(JTable table) { //Combo box setup
        JComboBox comboBox = new JComboBox(animeData.STATUS_LIST);
        DefaultCellEditor editor = new DefaultCellEditor(comboBox);
        TableColumn column = table.getColumnModel().getColumn(animeData.STATUS_INDEX);
        column.setCellEditor(editor);
    }

    private static JFrame createFrame() { //Create Anime Tracker main frame
        JFrame frame = new JFrame("Anime Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600, 300));
        return frame;
    }
}
