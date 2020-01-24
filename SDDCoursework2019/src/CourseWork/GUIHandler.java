/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseWork;

import static CourseWork.DatabaseHandler.handleDbConnection;
import java.awt.Component;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cmdel
 */
public class GUIHandler extends javax.swing.JFrame {

    /**
     * Creates new form GUIHandler
     */
    public GUIHandler() {
        initComponents();
    }

    /**
     * Default variable declaration for selection, changes upon button press
     */
    public int selection = 0;

    /**
     * Default variable declaration for band, changes upon button press
     */
    public String band = "0";

    /**
     * Default variable declaration for results, changes when while loop
     * initiated
     */
    public int results = 0;

    /**
     * handles how the results are retrieved and displayed in the GUI including
     * a custom excpetion handling what to do if an incorrect selection is used
     * (hardcoded) - see individual coded lines for specific code.
     *
     * @throws CourseWork.NoSelectionException if selection greater than 11,
     * shows warning in output box
     */
    public void retrieve() throws NoSelectionException {

        results = 0;

        try {

//            handles the possibility the selection being out of the bounds of 
//            the number of possible cases (set at 11 + -1)
            if (selection > 11) {
                throw new NoSelectionException();
            }

            Connection con = handleDbConnection();

//           makes the table visible
            tblDatabase.setVisible(true);

            Statement stmt = con.createStatement();

//            the SQL command to bring up the relevant data
            String query = "select * from accident WHERE Age_Band_of_Driver = " + selection;

            ResultSet rs = stmt.executeQuery(query);

//            assigns the table column names
            DefaultTableModel table = new DefaultTableModel(new String[]{
                "Age Band",
                "Accident Index",
                "Vehicle Type",
                "Driver Sex",
                "Engine Capacity(CC)",
                "Vehicle Age",
                "Vehicle Manoeuvre",
                "Vehicle Make",
                "Vehicle Model"}, 0);

//            Scans the database line by line for all entries meeting the
//            requirements in the selection
            while (rs.next()) {
                String index = rs.getString("Accident_Index");
                String age = rs.getString("Age_Band_of_Driver");
                String type = rs.getString("Vehicle_Type");
                String sex = rs.getString("Sex_of_Driver");
                String cc = rs.getString("Engine_Capacity_(CC)");
                String vAge = rs.getString("Age_of_Vehicle");
                String man = rs.getString("Vehicle_Manoeuvre");
                String make = rs.getString("make");
                String model = rs.getString("model");

//                converts the number codes in the databaseto the relevant text  
//                entries in the table
// <editor-fold>
                if ("-1".equals(cc)) {
                    cc = "Unknown/NA";
                }
                if ("-1".equals(vAge)) {
                    vAge = "Unknown";
                }
                if (null != sex) {
                    switch (sex) {
                        case "1":
                            sex = "Male";
                            break;
                        case "2":
                            sex = "Female";
                            break;
                        case "3":
                            sex = "Unknown";
                            break;
                        default:
                            break;
                    }
                }
                if (null != type) {
                    switch (type) {
                        case "1":
                            type = "Pedal Cycle";
                            break;
                        case "2":
                            type = "Motorcycle <= 50cc ";
                            break;
                        case "3":
                            type = "Motorcycle <= 125cc";
                            break;
                        case "4":
                            type = "Motorcycle 125 - 500CC";
                            break;
                        case "5":
                            type = "Motorcycle 500CC +";
                            break;
                        case "8":
                            type = "Taxi/Private Hire";
                            break;
                        case "9":
                            type = "Car";
                            break;
                        case "10":
                            type = "Minibus (8-16 seat)";
                            break;
                        case "11":
                            type = "Bus or Coach";
                            break;
                        case "16":
                            type = "Ridden Horse";
                            break;
                        case "17":
                            type = "Agricultural vehicle";
                            break;
                        case "18":
                            type = "Tram";
                            break;
                        case "19":
                            type = "Van / Goods 3.5 tonnes mgw or under";
                            break;
                        case "20":
                            type = "Goods over 3.5t.and under 7.5t";
                            break;
                        case "21":
                            type = "Goods 7.5 tonnes mgw and over";
                            break;
                        case "22":
                            type = "Mobility scooter";
                            break;
                        case "23":
                            type = "Electric motorcycle";
                            break;
                        case "90":
                            type = "Other vehicle";
                            break;
                        case "97":
                            type = "Motorcycle - unknown cc";
                            break;
                        case "98":
                            type = "Goods vehicle - unknown weight";
                            break;
                        case "-1":
                            type = "Data missing or out of range";
                            break;
                        default:
                            break;
                    }
                }
                if (null != man) {
                    switch (man) {
                        case "1":
                            man = "Reversing";
                            break;
                        case "2":
                            man = "Parked";
                            break;
                        case "3":
                            man = "Waiting to go - held up";
                            break;
                        case "4":
                            man = "Slowing or stopping";
                            break;
                        case "5":
                            man = "Moving off";
                            break;
                        case "6":
                            man = "U-Turn";
                            break;
                        case "7":
                            man = "Turning left";
                            break;
                        case "8":
                            man = "Waiting to turn left";
                            break;
                        case "9":
                            man = "Turning right";
                            break;
                        case "10":
                            man = "Waiting to turn right";
                            break;
                        case "11":
                            man = "Changing lane to left";
                            break;
                        case "12":
                            man = "Changing lane to right";
                            break;
                        case "13":
                            man = "Overtaking moving vehicle - offside";
                            break;
                        case "14":
                            man = "Overtaking static vehicle - offside";
                            break;
                        case "15":
                            man = "Overtaking - nearside";
                            break;
                        case "16":
                            man = "Going ahead - left-hand bend";
                            break;
                        case "17":
                            man = "Going ahead - right-hand bend";
                            break;
                        case "18":
                            man = "Going ahead - other";
                            break;
                        case "-1":
                            man = "Unknown";
                            break;
                    }
                }
// </editor-fold>

//                adds the results to the table in the GUI
                table.addRow(new Object[]{age, index, type, sex, cc, vAge, man, make, model});

                tblDatabase.setModel(table);

                results++;

            }

//            Closes the connection to the database            
            rs.close();
            stmt.close();
            con.close();

            lblAgeBand.setText("Age Band = " + band);
            lblResults.setText("Results = " + results);

//            manages what to do if the results are 0 (hides the table so it 
//            doesnt show anything and brings up message letting the user know
//            no results are found
            if (results == 0) {
                tblDatabase.setVisible(false);

                Component frame = null;
                JOptionPane.showMessageDialog(frame,
                        "No Results Available!");
            }

//        Handles any exceptions such as no connection or invalid selection            
        } catch (SQLException | NullPointerException | NoSelectionException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblResults = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatabase = new javax.swing.JTable();
        lblAgeBand = new javax.swing.JLabel();
        btnAge1 = new javax.swing.JButton();
        btnAge2 = new javax.swing.JButton();
        btnAge3 = new javax.swing.JButton();
        btnAge4 = new javax.swing.JButton();
        btnAge5 = new javax.swing.JButton();
        btnAge6 = new javax.swing.JButton();
        btnAge7 = new javax.swing.JButton();
        btnAge8 = new javax.swing.JButton();
        btnAge9 = new javax.swing.JButton();
        btnAge10 = new javax.swing.JButton();
        btnAge11 = new javax.swing.JButton();
        btnAgeUnknown = new javax.swing.JButton();
        lblChoices = new javax.swing.JLabel();
        btnAge0 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Age band viewer");
        setName("Frame"); // NOI18N
        setSize(new java.awt.Dimension(0, 0));

        lblResults.setText("Results");

        tblDatabase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatabase.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tblDatabase);

        lblAgeBand.setText("Age Band");

        btnAge1.setText("All");
        btnAge1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge1ActionPerformed(evt);
            }
        });

        btnAge2.setText("6-10");
        btnAge2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge2ActionPerformed(evt);
            }
        });

        btnAge3.setText("11-15");
        btnAge3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge3ActionPerformed(evt);
            }
        });

        btnAge4.setText("16-20");
        btnAge4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge4ActionPerformed(evt);
            }
        });

        btnAge5.setText("21-25");
        btnAge5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge5ActionPerformed(evt);
            }
        });

        btnAge6.setText("26-35");
        btnAge6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge6ActionPerformed(evt);
            }
        });

        btnAge7.setText("36-45");
        btnAge7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge7ActionPerformed(evt);
            }
        });

        btnAge8.setText("46-55");
        btnAge8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge8ActionPerformed(evt);
            }
        });

        btnAge9.setText("56-65");
        btnAge9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge9ActionPerformed(evt);
            }
        });

        btnAge10.setText("66-75");
        btnAge10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge10ActionPerformed(evt);
            }
        });

        btnAge11.setText("75+");
        btnAge11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge11ActionPerformed(evt);
            }
        });

        btnAgeUnknown.setText("Unknown");
        btnAgeUnknown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgeUnknownActionPerformed(evt);
            }
        });

        lblChoices.setText("Age band of Drivers in accidents:");

        btnAge0.setText("0-5");
        btnAge0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAge0ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblResults)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblAgeBand)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAgeUnknown))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnAge0, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnAge2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnAge3))
                                        .addComponent(lblChoices))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnAge4)
                                        .addComponent(btnAge1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAge5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAge6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAge7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAge8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAge9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAge10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAge11))))
                        .addGap(0, 69, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChoices)
                    .addComponent(btnAge1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAge0)
                    .addComponent(btnAge2)
                    .addComponent(btnAge3)
                    .addComponent(btnAge4)
                    .addComponent(btnAge5)
                    .addComponent(btnAge6)
                    .addComponent(btnAge7)
                    .addComponent(btnAge8)
                    .addComponent(btnAge9)
                    .addComponent(btnAge10)
                    .addComponent(btnAge11))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAgeBand, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgeUnknown))
                .addGap(2, 2, 2)
                .addComponent(lblResults)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnAge5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge5ActionPerformed
        // TODO add your handling code here:

        /**
         * handles what the buttons do by assigning a number (selection) and
         * calling the retrieve method using the assigned number within the
         * retrieve method
         */
        selection = 5;
        band = "21-25";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge5ActionPerformed

    private void btnAge6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge6ActionPerformed
        // TODO add your handling code here:

        selection = 6;
        band = "26-35";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge6ActionPerformed

    private void btnAge1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge1ActionPerformed
        // TODO add your handling code here:

        /**
         * has its own version of the retrieve method set to bring up all
         * results by avoiding the need for sepection.
         */
        results = 0;

        try {
            Connection con = handleDbConnection();

            tblDatabase.setVisible(true);

            //System.out.println("Connection established");
            Statement stmt = con.createStatement();

            String query = "select * from accident";

            ResultSet rs = stmt.executeQuery(query);

            DefaultTableModel table = new DefaultTableModel(new String[]{
                "Age Band",
                "Accident Index",
                "Vehicle Type",
                "Driver Sex",
                "Engine Capacity(CC)",
                "Vehicle Age",
                "Vehicle Manoeuvre",
                "Vehicle Make",
                "Vehicle Model"}, 0);

            while (rs.next()) {
                String index = rs.getString("Accident_Index");
                String age = rs.getString("Age_Band_of_Driver");
                String type = rs.getString("Vehicle_Type");
                String sex = rs.getString("Sex_of_Driver");
                String cc = rs.getString("Engine_Capacity_(CC)");
                String vAge = rs.getString("Age_of_Vehicle");
                String man = rs.getString("Vehicle_Manoeuvre");
                String make = rs.getString("make");
                String model = rs.getString("model");
// <editor-fold>
                if ("-1".equals(cc)) {
                    cc = "Unknown/NA";
                }
                if ("-1".equals(vAge)) {
                    vAge = "Unknown";
                }
                if (null != sex) {
                    switch (sex) {
                        case "1":
                            sex = "Male";
                            break;
                        case "2":
                            sex = "Female";
                            break;
                        case "3":
                            sex = "Unknown";
                            break;
                        default:
                            break;
                    }
                }
                if (null != type) {
                    switch (type) {
                        case "1":
                            type = "Pedal Cycle";
                            break;
                        case "2":
                            type = "Motorcycle <= 50cc ";
                            break;
                        case "3":
                            type = "Motorcycle <= 125cc";
                            break;
                        case "4":
                            type = "Motorcycle 125 - 500CC";
                            break;
                        case "5":
                            type = "Motorcycle 500CC +";
                            break;
                        case "8":
                            type = "Taxi/Private Hire";
                            break;
                        case "9":
                            type = "Car";
                            break;
                        case "10":
                            type = "Minibus (8-16 seat)";
                            break;
                        case "11":
                            type = "Bus or Coach";
                            break;
                        case "16":
                            type = "Ridden Horse";
                            break;
                        case "17":
                            type = "Agricultural vehicle";
                            break;
                        case "18":
                            type = "Tram";
                            break;
                        case "19":
                            type = "Van / Goods 3.5 tonnes mgw or under";
                            break;
                        case "20":
                            type = "Goods over 3.5t.and under 7.5t";
                            break;
                        case "21":
                            type = "Goods 7.5 tonnes mgw and over";
                            break;
                        case "22":
                            type = "Mobility scooter";
                            break;
                        case "23":
                            type = "Electric motorcycle";
                            break;
                        case "90":
                            type = "Other vehicle";
                            break;
                        case "97":
                            type = "Motorcycle - unknown cc";
                            break;
                        case "98":
                            type = "Goods vehicle - unknown weight";
                            break;
                        case "-1":
                            type = "Data missing or out of range";
                            break;
                        default:
                            break;
                    }
                }
                if (null != man) {
                    switch (man) {
                        case "1":
                            man = "Reversing";
                            break;
                        case "2":
                            man = "Parked";
                            break;
                        case "3":
                            man = "Waiting to go - held up";
                            break;
                        case "4":
                            man = "Slowing or stopping";
                            break;
                        case "5":
                            man = "Moving off";
                            break;
                        case "6":
                            man = "U-Turn";
                            break;
                        case "7":
                            man = "Turning left";
                            break;
                        case "8":
                            man = "Waiting to turn left";
                            break;
                        case "9":
                            man = "Turning right";
                            break;
                        case "10":
                            man = "Waiting to turn right";
                            break;
                        case "11":
                            man = "Changing lane to left";
                            break;
                        case "12":
                            man = "Changing lane to right";
                            break;
                        case "13":
                            man = "Overtaking moving vehicle - offside";
                            break;
                        case "14":
                            man = "Overtaking static vehicle - offside";
                            break;
                        case "15":
                            man = "Overtaking - nearside";
                            break;
                        case "16":
                            man = "Going ahead - left-hand bend";
                            break;
                        case "17":
                            man = "Going ahead - right-hand bend";
                            break;
                        case "18":
                            man = "Going ahead - other";
                            break;
                        case "-1":
                            man = "Unknown";
                            break;
                    }
                }
// </editor-fold>
                table.addRow(new Object[]{age, index, type, sex, cc, vAge, man, make, model});

                tblDatabase.setModel(table);

                results++;

                lblAgeBand.setText("Age Band = All");
                lblResults.setText("Results = " + results);

                if (results == 0) {
                    tblDatabase.setVisible(false);

                    Component frame = null;
                    JOptionPane.showMessageDialog(frame,
                            "No Results Available!");
                }

            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }


    }//GEN-LAST:event_btnAge1ActionPerformed

    private void btnAge2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge2ActionPerformed
        // TODO add your handling code here:

        selection = 2;
        band = "6-10";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge2ActionPerformed

    private void btnAge3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge3ActionPerformed
        // TODO add your handling code here:

        selection = 3;
        band = "11-15";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge3ActionPerformed

    private void btnAge4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge4ActionPerformed
        // TODO add your handling code here:

        selection = 4;
        band = "16-20";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge4ActionPerformed

    private void btnAge7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge7ActionPerformed
        // TODO add your handling code here:

        selection = 7;
        band = "36-45";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge7ActionPerformed

    private void btnAge8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge8ActionPerformed
        // TODO add your handling code here:

        selection = 8;
        band = "46-55";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge8ActionPerformed

    private void btnAge9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge9ActionPerformed
        // TODO add your handling code here:

        selection = 9;
        band = "56-65";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge9ActionPerformed

    private void btnAge10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge10ActionPerformed
        // TODO add your handling code here:

        selection = 10;
        band = "66-75";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge10ActionPerformed

    private void btnAge11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge11ActionPerformed
        // TODO add your handling code here:

        selection = 11;
        band = "75+";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge11ActionPerformed

    private void btnAgeUnknownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgeUnknownActionPerformed
        // TODO add your handling code here:

        selection = -1;
        band = "Unknown";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgeUnknownActionPerformed

    private void btnAge0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAge0ActionPerformed
        // TODO add your handling code here:

        selection = 1;
        band = "0-5";

        try {
            retrieve();
        } catch (NoSelectionException ex) {
            Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAge0ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GUIHandler().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAge0;
    private javax.swing.JButton btnAge1;
    private javax.swing.JButton btnAge10;
    private javax.swing.JButton btnAge11;
    private javax.swing.JButton btnAge2;
    private javax.swing.JButton btnAge3;
    private javax.swing.JButton btnAge4;
    private javax.swing.JButton btnAge5;
    private javax.swing.JButton btnAge6;
    private javax.swing.JButton btnAge7;
    private javax.swing.JButton btnAge8;
    private javax.swing.JButton btnAge9;
    private javax.swing.JButton btnAgeUnknown;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAgeBand;
    private javax.swing.JLabel lblChoices;
    private javax.swing.JLabel lblResults;
    private javax.swing.JTable tblDatabase;
    // End of variables declaration//GEN-END:variables
}
