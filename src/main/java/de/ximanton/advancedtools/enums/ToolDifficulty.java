package de.ximanton.advancedtools.enums;

public enum ToolDifficulty {
    WOOD(7, 1100),
    STONE(15, 1000),
    IRON(25, 900),
    GOLD(20, 1000),
    DIAMOND(45, 800),
    NETHERITE(60, 500);

    private final int msPerClick;
    private final int clickSequenceLength;

    ToolDifficulty(int clickSequenceLength, int msPerClick) {
        this.clickSequenceLength = clickSequenceLength;
        this.msPerClick = msPerClick;
    }

    public int getClickSequenceLength() { return this.clickSequenceLength; }
    public int getMsPerClick() { return this.msPerClick; }
}
