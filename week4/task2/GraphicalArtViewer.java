package main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphicalArtViewer extends JFrame {
    private ArtCanvas canvas;
    private String signature;
    
    public GraphicalArtViewer() {
        setTitle("Art Gallery Time-Capsule - Visual Display");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        loadSignature();
        loadArtwork();
        
        ArtPanel artPanel = new ArtPanel();
        add(artPanel);
        
        setVisible(true);
    }
    
    private void loadSignature() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("signature.txt"));
            StringBuilder sb = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            signature = sb.toString();
            
        } catch (IOException e) {
            signature = "Signature not found";
            System.err.println("Could not read signature: " + e.getMessage());
        }
    }
    
    private void loadArtwork() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("artwork.ser"));
            canvas = (ArtCanvas) ois.readObject();
            ois.close();
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load artwork: " + e.getMessage());
            System.err.println("Please run App.java first to create the artwork!");
            System.exit(1);
        }
    }
    
    class ArtPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Art Gallery Time-Capsule", 20, 30);
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            int y = 55;
            for (String line : signature.split("\n")) {
                if (!line.trim().isEmpty()) {
                    g2d.drawString(line, 20, y);
                    y += 15;
                }
            }
            
            g2d.setColor(Color.GRAY);
            g2d.drawRect(10, 120, getWidth() - 20, getHeight() - 130);
            
            if (canvas != null && canvas.getCoordinates() != null) {
                Color[] colors = {
                    new Color(255, 99, 71),   // Tomato
                    new Color(135, 206, 250), // Light Sky Blue
                    new Color(255, 215, 0),   // Gold
                    new Color(147, 112, 219), // Medium Purple
                    new Color(60, 179, 113),  // Medium Sea Green
                    new Color(255, 105, 180), // Hot Pink
                    new Color(255, 165, 0),   // Orange
                    new Color(64, 224, 208),  // Turquoise
                    new Color(220, 20, 60),   // Crimson
                    new Color(123, 104, 238)  // Medium Slate Blue
                };
                
                int colorIndex = 0;
                
                for (String coordinate : canvas.getCoordinates()) {
                    Pattern pattern = Pattern.compile("Circle at \\((\\d+), (\\d+)\\) with radius ([\\d.]+)");
                    Matcher matcher = pattern.matcher(coordinate);
                    
                    if (matcher.find()) {
                        int x = Integer.parseInt(matcher.group(1));
                        int circleY = Integer.parseInt(matcher.group(2));
                        double radius = Double.parseDouble(matcher.group(3));
                        
                        int canvasPadding = 30;
                        int canvasLeft = 10 + canvasPadding;
                        int canvasTop = 120 + canvasPadding;
                        int canvasRight = getWidth() - 10 - canvasPadding;
                        int canvasBottom = getHeight() - 10 - canvasPadding;
                        
                        double scaleX = (canvasRight - canvasLeft - radius * 2) / 500.0;
                        double scaleY = (canvasBottom - canvasTop - radius * 2) / 500.0;
                        
                        int drawX = canvasLeft + (int)(x * scaleX) + (int)radius;
                        int drawY = canvasTop + (int)(circleY * scaleY) + (int)radius;
                        int diameter = (int) (radius * 2);
                        
                        drawX = Math.max((int)radius + canvasLeft, Math.min(drawX, canvasRight - (int)radius));
                        drawY = Math.max((int)radius + canvasTop, Math.min(drawY, canvasBottom - (int)radius));
                        
                        g2d.setColor(colors[colorIndex % colors.length]);
                        
                        g2d.fillOval(drawX - (int)radius, drawY - (int)radius, diameter, diameter);
                        
                        g2d.setColor(Color.BLACK);
                        g2d.drawOval(drawX - (int)radius, drawY - (int)radius, diameter, diameter);
                        
                        colorIndex++;
                    }
                }
                
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.ITALIC, 11));
                g2d.drawString("Total shapes: " + canvas.getCoordinates().size(), 20, getHeight() - 20);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GraphicalArtViewer();
        });
    }
}
