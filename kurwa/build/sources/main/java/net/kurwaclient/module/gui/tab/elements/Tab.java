package net.kurwaclient.module.gui.tab.elements;

import net.kurwaclient.font.GlyphPageFontRenderer;
import net.kurwaclient.manager.impl.FontManager;
import net.kurwaclient.utilities.RenderUtilities;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static net.kurwaclient.module.gui.tab.TabGUI.*;

public class Tab<T> {
    private List<SubTab<T>> subTabs = new ArrayList<>();
    private String text;
    public int OFFSET = 3;

    public Tab(String text) {
        this.text = text;
    }

    public void addSubTab(SubTab<T> subTab) {
        subTabs.add(subTab);
    }

    public List<SubTab<T>> getSubTabs() {
        return subTabs;
    }

    public void renderSubTabs(int x, int y, int selectedSubTab) {

        glTranslated(x, y, 0);

        GlyphPageFontRenderer font = FontManager.HUD;

        int height = (font.getFontHeight() + OFFSET) * subTabs.size();

        int width = 0;

        for (SubTab<T> tab : subTabs) {
            if (font.getStringWidth(tab.getText()) > width) {
                width = font.getStringWidth(tab.getText());
            }
        }

        width += 2 + 2;

        RenderUtilities.drawRect(GL_QUADS, 0, 0, width, height, BACKGROUND.getRGB());

        glLineWidth(1.0f);
        RenderUtilities.drawRect(GL_LINE_LOOP, 0, 0, width, height, BORDER.getRGB());

        int offset = 2;

        int i = 0;

        for (SubTab<T> tab : subTabs) {
            if (selectedSubTab == i) {
                RenderUtilities.drawRect(GL_QUADS, 0, offset - 2, width, offset + font.getFontHeight() + OFFSET - 1, SELECTED.getRGB());
            }

            font.drawString(tab.getText(), 2, offset, FOREGROUND.getRGB(), false);
            offset += font.getFontHeight() + OFFSET;
            i++;
        }

        glTranslated(-x, -y, 0);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
