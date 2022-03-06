package com.mcaim.core.gui;

public class GuiUtil {
    public static int getMinimumGuiSizeFromAmount(int inputAmount) {
        for (int i = 9; i <= 54; i += 9) {
            if (inputAmount <= i)
                return i;
        }

        // Default return amount
        return 54;
    }
}
