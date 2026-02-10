package main;

import javax.swing.*;
import java.awt.*;

/**
 * This is the PARENT class for Valentine's Day cards.
 * You do not need to change anything in this file.
 * Create new CHILD classes that extend this to make different types of cards!
 */

// JFrame is a window that can hold components such as buttons, labels, etc.
// We want to see our Valentine's cards in a nice window.
public class ValentineCard extends JFrame {
    protected String recipient;
    protected Color cardColor;
    

    // Constructor here sets up the basic window
    //Child classes will call this with super()
    public ValentineCard(String recipient) {
        this.recipient = recipient;
        this.cardColor = Color.PINK;
        
        setTitle("Valentine's Day Card");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(cardColor);
    }
    
    // Your child classes can override this method to show different types of messages.
    public String getMessage() {
        return "Happy Valentine's Day!";
    }
    
    /**
     * Get the decoration (emoji/symbol)
     * Child classes can OVERRIDE this too
     */
    // Similar to getMessage(), child classes can also override this to show different decorations.
    public String getDecoration() {
        return "❤️";
    }
    

    // When overridden in child classes, this will automatically use the new versions.
    public void displayCard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(cardColor);
        
        // Decoration
        JLabel decorLabel = new JLabel(getDecoration(), SwingConstants.CENTER);
        decorLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        decorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Message
        JLabel messageLabel = new JLabel("<html><center>" + getMessage() + "</center></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 20));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Recipient
        JLabel toLabel = new JLabel("To: " + recipient, SwingConstants.CENTER);
        toLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        toLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(Box.createVerticalGlue());
        panel.add(decorLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(toLabel);
        panel.add(Box.createVerticalGlue());
        
        add(panel);
    }
}