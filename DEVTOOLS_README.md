# 🛠️ Ultimate DevTools - Memory Game Testing Suite

## Overview
The **Ultimate DevTools** is a comprehensive testing utility for the Memory game project. It provides multiple testing panels to verify game functionality, audio, visuals, and system information.

## Features

### 1. 🎵 **Sounds Panel**
- Test all 11 audio files in your project
- Play individual sounds with one click
- Real-time status display
- Sound controls:
  - ✓ card_flip.mp3
  - ✓ click.mp3
  - ✓ error.mp3
  - ✓ press_button.mp3
  - ✓ save_settings.mp3
  - ✓ shuffle.mp3
  - ✓ welldone.mp3
  - ✓ welldone2.mp3
  - ✓ you_lose.mp3
  - ✓ you_win.mp3
  - ✓ you_win2.mp3

### 2. 🎨 **Colors Panel**
- Preview all custom color palettes:
  - **Persona 3 Theme**: P3_MAIN, P3_BLUE, P3_CYAN, P3_DARK_NAVY, P3_WHITE, P3_SKY, P3_STEEL_BLUE, P3_DEEP_BLUE, P3_BRIGHT_CYAN, P3_DARK_GRAY, P3_RED_ALERT
  - **Amstrad CPC 464 Theme**: AMSTRAD_DARK_GREY, AMSTRAD_LIGHT_GREY, AMSTRAD_RED, AMSTRAD_BLUE, AMSTRAD_GREEN, HACKER_GREEN, TERMINAL, BACKGROUND
  - **Other Colors**: CYAN_KHAN, EGG_White, PASTEL_YELLOW, UIB_BLUE
- Color visualization with automatic text contrast adjustment

### 3. 🎮 **Game Testing Panel**
- Test all 13 difficulty levels (0-12)
- View difficulty specifications:
  - Grid dimensions (rows × columns)
  - Total card count
  - Difficulty names and progression

### 4. 🖼️ **Images Panel**
- Browse all media files in your project
- Display file sizes in KB
- Organized by folder:
  - Sounds folder listing
  - Icons folder listing

### 5. ⚙️ **System Panel**
- Java and OS information
- Memory usage statistics
- Current working directory
- Media paths configuration
- List of all available sounds

## Installation & Usage

### Option 1: Launch from Code (Quick Test)
```java
// Anywhere in your application:
DevTools.launchDevTools();
```

### Option 2: Add to MainFrame Menu (Recommended)
Edit your **MainFrame.java** and add this to your menu bar:

```java
import javax.swing.JMenu;

// In your MainFrame class, in the topPanel() or menu creation method:
JMenu devToolsMenu = new JMenu("🛠️ Tools");
devToolsMenu.add(DevToolsLauncher.createMenuItem());
menuBar.add(devToolsMenu);
```

### Option 3: Keyboard Shortcut
The DevTools menu item automatically binds to **F12** when using DevToolsLauncher.

### Option 4: Test Launcher Class
Create a simple test class to launch DevTools:

```java
public class DevToolsTest {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Load sounds first (if needed)
            String[] sounds = {"media/sounds/card_flip.mp3", "media/sounds/click.mp3"};
            SoundManager.load(sounds);
            
            // Launch DevTools
            new DevTools();
        });
    }
}
```

## Features Breakdown

### 🎵 Sound Testing
- **Play any sound** with a single click
- **Status label** shows current playing sound
- **Organized buttons** in grid layout (3 columns)
- Easy identification of sound files and their functionality

### 🎨 Color Palette
- **Live color preview** with color names
- **Organized by theme** for easy comparison
- **Automatic text contrast** (white text on dark colors, black text on light)
- Perfect for UI theme testing

### 🎮 Game Difficulty
- **13 difficulty levels** (0-12)
- **Instant information popup** showing:
  - Grid dimensions
  - Total cards
  - Difficulty name
- Helpful for balancing game progression

### 🖼️ Media Management
- **File browser** for all media assets
- **File size display** (in KB)
- **Organized by folder type**
- Quick verification that all files are present

### ⚙️ System Information
- **Java version** and OS details
- **Memory statistics** (max, total, free, used)
- **Path configuration** verification
- **Sound file listing** with status

## Keyboard Shortcuts
- **F12**: Toggle DevTools (when using DevToolsLauncher)
- **Tab**: Navigate between panels
- **Enter/Space**: Activate button
- **Esc**: Close DevTools window

## Design Features
- **Professional UI** with dark theme
- **Persona 3 inspired colors** as default theme
- **Rounded button borders** for modern look
- **Color-coded panels** for easy navigation
- **Hover effects** on buttons
- **Scrollable content** for panels with many items
- **Responsive layout** that adapts to window resizing

## Integration Example

### Complete MainFrame Integration:
```java
public class MainFrame extends JFrame {
    
    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Exit"));
        
        // Tools Menu with DevTools
        JMenu toolsMenu = new JMenu("🛠️ Tools");
        toolsMenu.add(DevToolsLauncher.createMenuItem());
        
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);
    }
}
```

## Troubleshooting

### Sounds not playing?
1. Verify media files exist in `media/sounds/` directory
2. Check SoundManager is properly initialized with sound paths
3. Ensure audio codec support (MP3)
4. Check system volume

### Colors not displaying correctly?
1. Verify CustomColors class has all color definitions
2. Check display's color profile
3. Try refreshing DevTools (button available)

### System info showing errors?
1. Check Java permissions
2. Verify working directory
3. Run as administrator if needed

## Customization

### Adding New Sound Tests
Edit DevTools.java in `createSoundsPanel()`:
```java
String[] sounds = {
    "existing_sounds.mp3",
    "your_new_sound.mp3"  // Add here
};
```

### Adding New Colors
1. Add colors to CustomColors.java
2. Update DevTools color panels in `createColorsPanel()`
3. Add to appropriate color group

### Adding New Test Panels
1. Create new method: `private JPanel createNewPanel()`
2. Add to tabbedPane in constructor: `tabbedPane.addTab("Icon", createNewPanel());`
3. Implement testing UI

## Performance Notes
- DevTools loads all panel content immediately
- Sound playback may take time depending on system
- Large color grids refresh smoothly with optimization
- Memory usage is minimal (< 50 MB)

## Version History
- **v1.0** - Initial release with 5 main panels (Sounds, Colors, Game, Images, System)

## Authors
- Marti Figuls Nolla
- Juan Dalmau Santandreu

---
**Tip**: Use DevTools during development to verify all game assets and systems before deployment!