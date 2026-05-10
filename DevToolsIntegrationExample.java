/*
 * DEVTOOLS INTEGRATION GUIDE
 * 
 * Copy the relevant code sections into your MainFrame class to enable DevTools
 */

package memory;

import javax.swing.*;

/**
 * Integration Examples for DevTools
 * 
 * EXAMPLE 1: Add DevTools menu item to existing menu bar
 * EXAMPLE 2: Add keyboard shortcut directly
 * EXAMPLE 3: Add button to UI
 */

public class DevToolsIntegrationExample {
    
    // ========================================================================
    // EXAMPLE 1: Add DevTools to Menu Bar (RECOMMENDED)
    // ========================================================================
    // Add this method to your MainFrame class:
    
    /*
    private void setupDevToolsMenu() {
        // Get your existing menu bar (or create new one if it doesn't exist)
        JMenuBar menuBar = getJMenuBar();
        if (menuBar == null) {
            menuBar = new JMenuBar();
            setJMenuBar(menuBar);
        }
        
        // Create Tools menu
        JMenu toolsMenu = new JMenu("🛠️ Tools");
        toolsMenu.add(DevToolsLauncher.createMenuItem());  // F12 shortcut included
        menuBar.add(toolsMenu);
    }
    
    // Then call in your MainFrame constructor:
    // public MainFrame() {
    //     super("Memory - UIB");
    //     // ... other initialization ...
    //     setupDevToolsMenu();
    // }
    */
    
    // ========================================================================
    // EXAMPLE 2: Add Direct Keyboard Shortcut (F12)
    // ========================================================================
    // Add this to your MainFrame class:
    
    /*
    private void setupDevToolsShortcut() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F12) {
                DevTools.launchDevTools();
                return true;
            }
            return false;
        });
    }
    
    // Then call in your MainFrame constructor:
    // public MainFrame() {
    //     super("Memory - UIB");
    //     // ... other initialization ...
    //     setupDevToolsShortcut();
    // }
    */
    
    // ========================================================================
    // EXAMPLE 3: Add Button to UI
    // ========================================================================
    // Add this to your MainFrame class:
    
    /*
    private JButton createDevToolsButton() {
        JButton btn = new JButton("🛠️ DevTools");
        btn.addActionListener(e -> DevTools.launchDevTools());
        return btn;
    }
    
    // Then add to your UI:
    // JPanel buttonPanel = new JPanel();
    // buttonPanel.add(createDevToolsButton());
    // add(buttonPanel, BorderLayout.NORTH);
    */
    
    // ========================================================================
    // EXAMPLE 4: Complete Integration in MainFrame
    // ========================================================================
    
    /*
    public class MainFrame extends JFrame {
        
        // ... existing code ...
        
        public MainFrame() {
            super("Memory - UIB");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // ... existing initialization ...
            
            // Add DevTools support
            setupDevToolsMenu();
            setupDevToolsShortcut();
            
            setVisible(true);
        }
        
        // Setup DevTools menu
        private void setupDevToolsMenu() {
            JMenuBar menuBar = new JMenuBar();
            
            // File menu
            JMenu fileMenu = new JMenu("File");
            fileMenu.add(new JMenuItem("Exit"));
            
            // Tools menu with DevTools
            JMenu toolsMenu = new JMenu("🛠️ Tools");
            toolsMenu.add(DevToolsLauncher.createMenuItem());
            
            menuBar.add(fileMenu);
            menuBar.add(toolsMenu);
            setJMenuBar(menuBar);
        }
        
        // Setup F12 shortcut
        private void setupDevToolsShortcut() {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F12) {
                    DevTools.launchDevTools();
                    return true;
                }
                return false;
            });
        }
        
        // ... rest of existing code ...
    }
    */
    
    // ========================================================================
    // QUICK START USAGE
    // ========================================================================
    
    /*
     * To launch DevTools from anywhere in code:
     * 
     *   // Option 1: Simple launch
     *   DevTools.launchDevTools();
     * 
     *   // Option 2: Via menu item (F12 shortcut)
     *   // (Already set up if using DevToolsLauncher)
     * 
     *   // Option 3: Test specific feature
     *   SwingUtilities.invokeLater(() -> {
     *       String[] soundPaths = {
     *           "media/sounds/card_flip.mp3",
     *           "media/sounds/click.mp3"
     *       };
     *       SoundManager.load(soundPaths);
     *       DevTools.launchDevTools();
     *   });
     */
    
    // ========================================================================
    // REQUIRED IMPORTS
    // ========================================================================
    
    /*
     * Add these imports to MainFrame:
     * 
     * import javax.swing.KeyboardFocusManager;
     * import java.awt.event.KeyEvent;
     */
    
}

// ============================================================================
// MINIMAL INTEGRATION (Copy-Paste Ready)
// ============================================================================

/*
 * If you just want the bare minimum, copy this into your MainFrame class:
 */

/*
// Add this import:
import java.awt.event.KeyEvent;
import javax.swing.KeyboardFocusManager;

// Add this method:
private void initDevTools() {
    // Add to menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu toolsMenu = new JMenu("🛠️ Tools");
    toolsMenu.add(DevToolsLauncher.createMenuItem());
    menuBar.add(toolsMenu);
    setJMenuBar(menuBar);
    
    // Add F12 shortcut globally
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F12) {
            DevTools.launchDevTools();
            return true;
        }
        return false;
    });
}

// Then call in MainFrame constructor:
// public MainFrame() {
//     super("Memory - UIB");
//     // ... other code ...
//     initDevTools();  // Add this line
//     setVisible(true);
// }
*/
