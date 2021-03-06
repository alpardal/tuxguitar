package org.herac.tuxguitar.io.ascii;

import java.io.OutputStream;

import org.herac.tuxguitar.io.base.TGFileFormat;
import org.herac.tuxguitar.io.base.TGLocalFileExporter;
import org.herac.tuxguitar.song.models.TGSong;

public class ASCIISongExporter implements TGLocalFileExporter {

  private OutputStream stream;

  public boolean configure(boolean setDefaults) {
    return true;
  }

  public void exportSong(TGSong song) {
    if (this.stream != null) {
      new ASCIITabOutputStream(this.stream).writeSong(song);
    }
  }

  public String getExportName() {
    return "ASCII";
  }

  public TGFileFormat getFileFormat() {
    return new TGFileFormat("ASCII", "*.tab");
  }

  public void init(OutputStream stream) {
    this.stream = stream;
  }

}