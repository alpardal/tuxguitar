package org.herac.tuxguitar.gui.editors.tab;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.editors.TGPainter;
import org.herac.tuxguitar.gui.editors.tab.layout.ViewLayout;
import org.herac.tuxguitar.gui.editors.tab.painters.TGNotePainter;
import org.herac.tuxguitar.gui.editors.tab.painters.TGTempoPainter;
import org.herac.tuxguitar.gui.editors.tab.painters.TGTripletFeelPainter;
import org.herac.tuxguitar.gui.system.config.TGConfigKeys;
import org.herac.tuxguitar.gui.util.ImageUtils;
import org.herac.tuxguitar.song.models.TGDuration;

public class TGResources {

  private static final int SCORE_NOTE_EMPTY_NORMAL_MODE = 0;
  private static final int SCORE_NOTE_EMPTY_PLAY_MODE = 1;
  private static final int SCORE_NOTE_FULL_NORMAL_MODE = 2;
  private static final int SCORE_NOTE_FULL_PLAY_MODE = 3;

  private Color backgroundColor;
  private Color caretColor1;
  private Color caretColor2;
  private Font chordFont;
  private Font chordFretFont;
  private Color colorBlack;
  private Color colorRed;
  private Color colorWhite;
  private Font defaultFont;
  private Font graceFont;
  private Image[] harmonicNotes;
  private ViewLayout layout;
  private Color lineColor;
  private Color loopEMarkerColor;
  private Color loopSMarkerColor;
  private Font lyricFont;
  private Font markerFont;
  private Font noteFont;
  private Color playNoteColor;
  private Font printerChordFont;
  private Font printerDefaultFont;
  private Font printerGraceFont;
  private Font printerLyricFont;
  private Font printerNoteFont;
  private Font printerTextFont;
  private Font printerTimeSignatureFont;
  private List<Resource> resources;
  private Color scoreNoteColor;
  private Image[] scoreNotes;
  private int scoreNoteWidth;
  private Color tabNoteColor;
  private Image tempoImage;
  private Font textFont;
  private Font timeSignatureFont;
  private Image tripletFeel16;
  private Image tripletFeel8;
  private Image tripletFeelNone16;

  private Image tripletFeelNone8;

  public TGResources(ViewLayout layout) {
    this.layout = layout;
    this.resources = new ArrayList<Resource>();
  }

  public void dispose() {
    for (final Resource resource : this.resources) {
      resource.dispose();
    }
    
    this.resources.clear();
  }

  public Color getBackgroundColor() {
    return this.backgroundColor;
  }

  public Color getCaretColor1() {
    return this.caretColor1;
  }

  public Color getCaretColor2() {
    return this.caretColor2;
  }

  public Font getChordFont() {
    return this.chordFont;
  }

  public Font getChordFretFont() {
    return this.chordFretFont;
  }

  private Color getColor(String key) {
    RGB rgb = TuxGuitar.instance().getConfig().getRGBConfigValue(key);
    if (rgb == null) {
      rgb = new RGB(0, 0, 0);
    }
    Color color = new Color(TuxGuitar.instance().getDisplay(), rgb);
    this.resources.add(color);
    return color;
  }

  public Color getColorBlack() {
    return this.colorBlack;
  }

  public Color getColorRed() {
    return this.colorRed;
  }

  public Color getColorWhite() {
    return this.colorWhite;
  }

  public Font getDefaultFont() {
    return this.defaultFont;
  }

  private Font getFont(String key, float scale) {
    FontData data = TuxGuitar.instance().getConfig()
        .getFontDataConfigValue(key);
    if (data == null) {
      data = new FontData();
    }
    float height = (data.getHeight() * scale);

    data.setHeight((height > 1 ? Math.round(height) : 1));

    Font font = new Font(TuxGuitar.instance().getDisplay(), data);
    this.resources.add(font);
    return font;
  }

  public Font getGraceFont() {
    return this.graceFont;
  }

  private Image getHarmonicImage(Color color, boolean full) {
    int size = getLayout().getScoreLineSpacing();

    int x = 0;
    int y = 1;
    int width = getScoreNoteWidth() - 1;
    int height = size - 2;

    Image image = getImage(x + width + 2, y + height + 2);
    TGPainter painter = new TGPainter(new GC(image));
    painter.setForeground(color);
    painter.setBackground(color);
    painter.initPath((full ? TGPainter.PATH_DRAW | TGPainter.PATH_FILL
        : TGPainter.PATH_DRAW));
    TGNotePainter.paintHarmonic(painter, x, y, height);
    painter.closePath();
    painter.dispose();

    return getImageMask(image, getBackgroundColor().getRGB(), color.getRGB());
  }

  public Image getHarmonicNote(int value, boolean playing) {
    int index = 0;
    index += ((playing) ? 1 : 0);
    index += ((value >= TGDuration.QUARTER) ? 2 : 0);
    return this.harmonicNotes[index];
  }

  private Image getImage(int width, int height) {
    Image image = new Image(getLayout().getTablature().getDisplay(), width,
        height);
    TGPainter painter = new TGPainter(new GC(image));
    painter.setBackground(getBackgroundColor());
    painter.initPath(TGPainter.PATH_FILL);
    painter.addRectangle(0, 0, width, height);
    painter.closePath();
    painter.dispose();
    return image;
  }

  private Image getImageMask(Image src, RGB alpha, RGB none) {
    ImageData srcData = src.getImageData();
    ImageData maskData = ImageUtils.applyMask(srcData, alpha, none);
    src.dispose();
    Image image = new Image(getLayout().getTablature().getDisplay(), srcData,
        maskData);
    this.resources.add(image);
    return image;
  }

  public ViewLayout getLayout() {
    return this.layout;
  }

  public Color getLineColor() {
    return this.lineColor;
  }

  public Color getLoopEMarkerColor() {
    return this.loopEMarkerColor;
  }

  public Color getLoopSMarkerColor() {
    return this.loopSMarkerColor;
  }

  public Font getLyricFont() {
    return this.lyricFont;
  }

  public Font getMarkerFont() {
    return this.markerFont;
  }

  public Font getNoteFont() {
    return this.noteFont;
  }

  public Color getPlayNoteColor() {
    return this.playNoteColor;
  }

  public Font getPrinterChordFont() {
    return this.printerChordFont;
  }

  public Font getPrinterDefaultFont() {
    return this.printerDefaultFont;
  }

  public Font getPrinterGraceFont() {
    return this.printerGraceFont;
  }

  public Font getPrinterLyricFont() {
    return this.printerLyricFont;
  }

  public Font getPrinterNoteFont() {
    return this.printerNoteFont;
  }

  public Font getPrinterTextFont() {
    return this.printerTextFont;
  }

  public Font getPrinterTimeSignatureFont() {
    return this.printerTimeSignatureFont;
  }

  public Image getScoreNote(int value, boolean playing) {
    int index = 0;
    index += ((playing) ? 1 : 0);
    index += ((value >= TGDuration.QUARTER) ? 2 : 0);
    return this.scoreNotes[index];
  }

  public Color getScoreNoteColor() {
    return this.scoreNoteColor;
  }

  private Image getScoreNoteImage(Color color, boolean full) {
    float scale = (full ? getLayout().getScoreLineSpacing() + 1 : getLayout()
        .getScoreLineSpacing()) - 2;
    int width = Math.round(scale * 1.33f);
    int height = Math.round(scale * 1.0f);

    Image image = getImage(width + 1, height + 2);
    TGPainter painter = new TGPainter(new GC(image));
    painter.setBackground(color);
    painter.setForeground(color);
    painter.initPath((full ? TGPainter.PATH_FILL : TGPainter.PATH_DRAW));
    TGNotePainter.paintNote(painter, 0, 1, scale);
    painter.closePath();
    painter.dispose();

    this.scoreNoteWidth = width;

    return getImageMask(image, getBackgroundColor().getRGB(), color.getRGB());
  }

  public int getScoreNoteWidth() {
    return this.scoreNoteWidth;
  }

  public Color getTabNoteColor() {
    return this.tabNoteColor;
  }

  public Image getTempoImage() {
    return this.tempoImage;
  }

  private Image getTempoImage(Color color) {
    float scale = 5f * getLayout().getScale();
    int width = Math.round(scale * 1.33f);
    int height = Math.round(scale * (1.0f + 2.5f));

    Image image = getImage(width + 1, height + 2);
    TGPainter painter = new TGPainter(new GC(image));
    painter.setBackground(color);
    painter.setForeground(color);
    TGTempoPainter.paintTempo(painter, 0, 0, scale);
    painter.dispose();

    return getImageMask(image, getBackgroundColor().getRGB(), color.getRGB());
  }

  public Font getTextFont() {
    return this.textFont;
  }

  public Font getTimeSignatureFont() {
    return this.timeSignatureFont;
  }

  public Image getTripletFeel16() {
    return this.tripletFeel16;
  }

  private Image getTripletFeel16(Color color) {
    float scale = 5f * getLayout().getScale();
    float topSpacing = (1.0f * scale);
    float horizontalSpacing = (1.5f * scale);
    float verticalSpacing = (2.5f * scale);
    float ovalWidth = (1.33f * scale);
    float ovalHeight = (1.0f * scale);

    int width = Math.round((ovalWidth * 2f) + horizontalSpacing);
    int height = Math.round(topSpacing + ovalHeight + verticalSpacing);

    Image image = getImage(width + 1, height + 2);
    TGPainter painter = new TGPainter(new GC(image));
    painter.setBackground(color);
    painter.setForeground(color);
    TGTripletFeelPainter.paintTripletFeel16(painter, 0, 0, scale);
    painter.dispose();

    return getImageMask(image, getBackgroundColor().getRGB(), color.getRGB());
  }

  public Image getTripletFeel8() {
    return this.tripletFeel8;
  }

  private Image getTripletFeel8(Color color) {
    float scale = 5f * getLayout().getScale();
    float topSpacing = (1.0f * scale);
    float horizontalSpacing = (1.5f * scale);
    float verticalSpacing = (2.5f * scale);
    float ovalWidth = (1.33f * scale);
    float ovalHeight = (1.0f * scale);

    int width = Math.round((ovalWidth * 2f) + horizontalSpacing);
    int height = Math.round((topSpacing + ovalHeight + verticalSpacing));

    Image image = getImage(width + 1, height + 2);
    TGPainter painter = new TGPainter(new GC(image));
    painter.setBackground(color);
    painter.setForeground(color);
    TGTripletFeelPainter.paintTripletFeel8(painter, 0, 0, scale);
    painter.dispose();

    return getImageMask(image, getBackgroundColor().getRGB(), color.getRGB());
  }

  public Image getTripletFeelNone16() {
    return this.tripletFeelNone16;
  }

  private Image getTripletFeelNone16(Color color) {
    float scale = 5f * getLayout().getScale();

    float horizontalSpacing = (1.5f * scale);
    float verticalSpacing = (2.5f * scale);
    float ovalWidth = (1.33f * scale);
    float ovalHeight = (1.0f * scale);

    int width = Math.round(ovalWidth + horizontalSpacing);
    int height = Math.round(ovalHeight + verticalSpacing);

    Image image = getImage(width + 1, height + 2);
    TGPainter painter = new TGPainter(new GC(image));
    painter.setBackground(color);
    painter.setForeground(color);
    TGTripletFeelPainter.paintTripletFeelNone16(painter, 0, 0, scale);
    painter.dispose();

    return getImageMask(image, getBackgroundColor().getRGB(), color.getRGB());
  }

  public Image getTripletFeelNone8() {
    return this.tripletFeelNone8;
  }

  private Image getTripletFeelNone8(Color color) {
    float scale = 5f * getLayout().getScale();

    float horizontalSpacing = (1.5f * scale);
    float verticalSpacing = (2.5f * scale);
    float ovalWidth = (1.33f * scale);
    float ovalHeight = (1.0f * scale);

    int width = Math.round(ovalWidth + horizontalSpacing);
    int height = Math.round(ovalHeight + verticalSpacing);

    Image image = getImage(width + 1, height + 2);
    TGPainter painter = new TGPainter(new GC(image));
    painter.setBackground(color);
    painter.setForeground(color);
    TGTripletFeelPainter.paintTripletFeelNone8(painter, 0, 0, scale);
    painter.dispose();

    return getImageMask(image, getBackgroundColor().getRGB(), color.getRGB());
  }

  private void initColors() {
    this.backgroundColor = getColor(TGConfigKeys.COLOR_BACKGROUND);
    this.lineColor = getColor(TGConfigKeys.COLOR_LINE);
    this.scoreNoteColor = getColor(TGConfigKeys.COLOR_SCORE_NOTE);
    this.tabNoteColor = getColor(TGConfigKeys.COLOR_TAB_NOTE);
    this.playNoteColor = getColor(TGConfigKeys.COLOR_PLAY_NOTE);
    this.caretColor1 = getColor(TGConfigKeys.COLOR_CARET_1);
    this.caretColor2 = getColor(TGConfigKeys.COLOR_CARET_2);
    this.loopSMarkerColor = getColor(TGConfigKeys.COLOR_LOOP_S_MARKER);
    this.loopEMarkerColor = getColor(TGConfigKeys.COLOR_LOOP_E_MARKER);
    // Static colors
    this.colorWhite = TuxGuitar.instance().getDisplay().getSystemColor(
        SWT.COLOR_WHITE);
    this.colorBlack = TuxGuitar.instance().getDisplay().getSystemColor(
        SWT.COLOR_BLACK);
    this.colorRed = TuxGuitar.instance().getDisplay().getSystemColor(
        SWT.COLOR_RED);
  }

  private void initFonts() {
    float scale = this.layout.getFontScale();
    this.defaultFont = getFont(TGConfigKeys.FONT_DEFAULT, scale);
    this.noteFont = getFont(TGConfigKeys.FONT_NOTE, scale);
    this.timeSignatureFont = getFont(TGConfigKeys.FONT_TIME_SIGNATURE, scale);
    this.lyricFont = getFont(TGConfigKeys.FONT_LYRIC, scale);
    this.textFont = getFont(TGConfigKeys.FONT_TEXT, scale);
    this.markerFont = getFont(TGConfigKeys.FONT_MARKER, scale);
    this.graceFont = getFont(TGConfigKeys.FONT_GRACE, scale);
    this.chordFont = getFont(TGConfigKeys.FONT_CHORD, scale);
    this.chordFretFont = getFont(TGConfigKeys.FONT_CHORD_FRET, scale);
    this.printerDefaultFont = getFont(TGConfigKeys.FONT_PRINTER_DEFAULT, scale);
    this.printerNoteFont = getFont(TGConfigKeys.FONT_PRINTER_NOTE, scale);
    this.printerTimeSignatureFont = getFont(
        TGConfigKeys.FONT_PRINTER_TIME_SIGNATURE, scale);
    this.printerLyricFont = getFont(TGConfigKeys.FONT_PRINTER_LYRIC, scale);
    this.printerTextFont = getFont(TGConfigKeys.FONT_PRINTER_TEXT, scale);
    this.printerGraceFont = getFont(TGConfigKeys.FONT_PRINTER_GRACE, scale);
    this.printerChordFont = getFont(TGConfigKeys.FONT_PRINTER_CHORD, scale);
  }

  private void initImages() {
    this.scoreNotes = new Image[4];
    this.scoreNotes[SCORE_NOTE_EMPTY_NORMAL_MODE] = getScoreNoteImage(
        getScoreNoteColor(), false);
    this.scoreNotes[SCORE_NOTE_EMPTY_PLAY_MODE] = getScoreNoteImage(
        getPlayNoteColor(), false);
    this.scoreNotes[SCORE_NOTE_FULL_NORMAL_MODE] = getScoreNoteImage(
        getScoreNoteColor(), true);
    this.scoreNotes[SCORE_NOTE_FULL_PLAY_MODE] = getScoreNoteImage(
        getPlayNoteColor(), true);

    this.harmonicNotes = new Image[4];
    this.harmonicNotes[SCORE_NOTE_EMPTY_NORMAL_MODE] = getHarmonicImage(
        getScoreNoteColor(), false);
    this.harmonicNotes[SCORE_NOTE_EMPTY_PLAY_MODE] = getHarmonicImage(
        getPlayNoteColor(), false);
    this.harmonicNotes[SCORE_NOTE_FULL_NORMAL_MODE] = getHarmonicImage(
        getScoreNoteColor(), true);
    this.harmonicNotes[SCORE_NOTE_FULL_PLAY_MODE] = getHarmonicImage(
        getPlayNoteColor(), true);

    this.tempoImage = getTempoImage(this.getColorBlack());

    this.tripletFeel8 = getTripletFeel8(this.getColorBlack());
    this.tripletFeelNone8 = getTripletFeelNone8(this.getColorBlack());
    this.tripletFeel16 = getTripletFeel16(this.getColorBlack());
    this.tripletFeelNone16 = getTripletFeelNone16(this.getColorBlack());
  }

  public void load() {
    this.dispose();
    this.initFonts();
    this.initColors();
    this.initImages();
  }
}
