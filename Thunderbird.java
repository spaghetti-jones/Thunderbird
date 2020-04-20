/******************************************************************************
 * Copyright (C) 2019 Eric Pogue.
 * 
 * This file and the ThunderbirdLite application is licensed under the 
 * BSD-3-Clause.
 * 
 * You may use any part of the file as long as you give credit in your 
 * source code.
 * 
 * This application utilizes the HttpRequest.java library developed by 
 * Eric Pogue
 * 
 * Version: 1.3
 *****************************************************************************/

/******************************************************************************
 * Modified the original code by Eric Pogue
 * Edits to code are made by Luis Barrera
 * ThunderbirdLite.java was modified to become Thunderbird.java
 * Eric Pogue is the author of ThunderbirdLite.java, ThunderbirdContacts.java,
 * ThunderbirdModel.java and HttpRequest.java
 *****************************************************************************/
 
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Container; 
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

class ContactTile extends JPanel {
    private int red, green, blue;
    private ThunderbirdContact contactInSeat = null;

    private Boolean isAnAisle = false;
    public void setAisle() { isAnAisle = true; }

    ContactTile() {
        super();

        // Todo: Remove everything to do with random colors.
        // Todo: Implement visually appealing colors for aisles and seats.
        //LB: the seat locations are now light blue
    }

    ContactTile(ThunderbirdContact contactInSeatIn) {
        super();
        red = 51;
        green = 153;
        blue = 255;
        contactInSeat = contactInSeatIn;
    }

     public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        //LB: added grey background
        setBackground(new Color(204,204,204));

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (isAnAisle) {
            // LB: Set aisle tile to fade into background
            g.setColor(new Color(204,204,204));
        } else {
            g.setColor(new Color(red,green,blue));
        }
        
        g.fillRect (10, 10, panelWidth-20, panelHeight-20);

        g.setColor(new Color(GetContrastingColor(red),GetContrastingColor(green),GetContrastingColor(blue)));

        final int fontSize=18;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        //LB: altered stringY
        int stringX = (panelWidth/2)-60;
        int stringY = (panelHeight/2);
        if (contactInSeat != null) {

            // ToDo: Display preferred name instead of first name.
            //LB: Displaying preferred name DONE
            //LB: first name not showing up (only preferred name)

            String firstAndLastName;

            if (contactInSeat.getPreferredName() == null) {
                firstAndLastName = contactInSeat.getFirstName()+" "+contactInSeat.getLastName();
                g.drawString(firstAndLastName,stringX,stringY);
             }else{
                firstAndLastName = contactInSeat.getPreferredName()+" "+contactInSeat.getLastName();
                g.drawString(firstAndLastName,stringX,stringY);
            }
        }
    }

    private static int GetContrastingColor(int colorIn) {
        return ((colorIn+128)%256);
    }
}

class ThunderbirdFrame extends JFrame {
    private ArrayList<ContactTile> tileList;

    public ThunderbirdFrame() {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel contactGridPanel = new JPanel();
        contentPane.add(contactGridPanel, BorderLayout.CENTER);

        contactGridPanel.setLayout(new GridLayout(11,9));

        ThunderbirdModel tbM = new ThunderbirdModel();
        tbM.LoadIndex();
        //LB: Change call to threaded
        tbM.LoadContactsThreaded(); 

        // Todo: Review ThunderbirdModel in detail and implement a multithreaded version of loading contacts. 
        // Hint: Review LoadContact() and LoadContactsThreaded() in detail.
        // LB: switched LoadContacts() with LoadContactsThreaded()

        System.out.println("Printing Model:");
        System.out.println(tbM);
        tbM.ValidateContacts();   


        tileList = new ArrayList<ContactTile>();
        
        for(int i=0; i<99; i++) {
            ThunderbirdContact contactInSeat = tbM.findContactInSeat(i);

            ContactTile tile = new ContactTile(contactInSeat);

            // Todo: Place all the aisle seats in a array or an ArrayList instead of hard coding them.
            //LB: hard coded route
            
            if ((i<=9)||(i==11)||(i>=20&&i<=23)||(i==14)||(i==18)||(i==17)||(i==26)
            ||(i==27)||(i==29)||(i==32)||(i==35)||(i==36)||(i>=38&&i<=45)||(i==47)
            ||(i==50)||(i<=54&&i>=53)||(i>=56&&i<=59)||(i>=62&&i<=63)||(i==65)
            ||(i==68)||(i>=71&&i<=83)||(i>=86&&i<=92)||(i>=95&&i<=98)) {
                tile.setAisle();
            }
            tileList.add(tile);
            contactGridPanel.add(tile);
        }
    }
}

// Todo: Rename the following class to Thunderbird.
// Hint: This will also require you to rename the Java file.
public class Thunderbird { //RENAME TO Thunderbird
    public static void main(String[] args) {

        // Todo: Update the following line so that it reflects the name change to Thunderbird.
        //LB: change to Thunderbird instead of ThunderbirdLite DONE
        //LB: alter startup message DONE
        System.out.println("Executing Thunderbird...");

        ThunderbirdFrame myThunderbirdFrame = new ThunderbirdFrame();
        myThunderbirdFrame.setVisible(true);
    }
}