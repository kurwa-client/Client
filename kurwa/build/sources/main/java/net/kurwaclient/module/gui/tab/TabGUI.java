package net.kurwaclient.module.gui.tab;

import net.kurwaclient.font.GlyphPageFontRenderer;
import net.kurwaclient.manager.impl.FontManager;
import net.kurwaclient.module.gui.tab.elements.Tab;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.kurwaclient.utilities.RenderUtilities.drawRect;
import static org.lwjgl.opengl.GL11.*;

public class TabGUI<T> {
    static final int OFFSET = 3;
    public static Color BACKGROUND = new Color(0, 0, 0, 175);
    public static Color BORDER = new Color(0, 0, 0, 255);
    public static Color SELECTED = new Color(38, 164, 78, 200);
    public static Color FOREGROUND = Color.white;
    private List<Tab<T>> tabs = new ArrayList<>();
    private int selectedTab = 0;
    private int selectedSubTab = -1;

    public void addTab(Tab<T> tab) {
        tabs.add(tab);
    }

    public void render(int x, int y) {
        glTranslated(x, y, 0);

        GlyphPageFontRenderer font = FontManager.HUD;

        int height = (font.getFontHeight() + OFFSET) * tabs.size();

        int width = 0;

        for (Tab<T> tab : tabs) {
            if (font.getStringWidth(tab.getText()) > width) {
                width = font.getStringWidth(tab.getText());
            }
        }

        width += 2 + 2;

        drawRect(GL_QUADS, 0, 0, width, height, BACKGROUND.getRGB());


        int offset = 2;

        int i = 0;

        for (Tab<T> tab : tabs) {
            if (selectedTab == i) {
                drawRect(GL_QUADS, 0, offset - 2, width, offset + font.getFontHeight() + OFFSET - 2, SELECTED.getRGB());

                if (selectedSubTab != -1) {
                    tab.renderSubTabs(width, offset - 2, selectedSubTab);
                }
            }

            font.drawString(tab.getText(), 2, offset, FOREGROUND.getRGB(), false);
            offset += font.getFontHeight() + OFFSET;
            i++;
        }
        glLineWidth(1.0f);
        drawRect(GL_LINE_LOOP, 0, 0, width, height, BORDER.getRGB());

        glTranslated(-x, -y, 0);
    }

    public void handleKey(int keycode) {
        if (keycode == Keyboard.KEY_DOWN) {
            if (selectedSubTab == -1) {
                selectedTab++;

                if (selectedTab >= tabs.size()) {
                    selectedTab = 0;
                }
            } else {
                selectedSubTab++;

                if (selectedSubTab >= tabs.get(selectedTab).getSubTabs().size()) {
                    selectedSubTab = 0;
                }
            }
        } else if (keycode == Keyboard.KEY_UP) {
            if (selectedSubTab == -1) {
                selectedTab--;

                if (selectedTab < 0) {
                    selectedTab = tabs.size() - 1;
                }
            } else {
                selectedSubTab--;

                if (selectedSubTab < 0) {
                    selectedSubTab = tabs.get(selectedTab).getSubTabs().size() - 1;
                }
            }
        } else if (keycode == Keyboard.KEY_LEFT) {
            selectedSubTab = -1;
        } else if (selectedSubTab == -1 && (keycode == Keyboard.KEY_RETURN || keycode == Keyboard.KEY_RIGHT)) {
            selectedSubTab = 0;
        } else if (keycode == Keyboard.KEY_RETURN || keycode == Keyboard.KEY_RIGHT) {
            tabs.get(selectedTab).getSubTabs().get(selectedSubTab).press();
        }
    }
}
