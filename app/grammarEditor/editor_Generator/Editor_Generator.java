package org.fleen.forsythia.app.grammarEditor.editor_Generator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.UI_Generator;
import org.fleen.forsythia.app.grammarEditor.util.Editor;

/*
 * This is the front end for a forsythia composition generator
 * it displays the composition and controls the generation process
 * we also provide ui for exportcomposition and configure generator, exportcomposition and whatever
 * We use this for viewing art and testing grammars.
 */
public class Editor_Generator extends Editor{

  private static final long serialVersionUID=2946066354233444754L;

  public static final String NAME="GENERATOR";
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Editor_Generator(){
    super(NAME);}
  
  /*
   * ################################
   * CONFIGURE
   * ################################
   */

  public void configureForOpen(){
    generator.startControlThread();
    refreshUI();}
  
  public void configureForClose(){
    generator.stopControlThread();}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  protected JPanel createUI(){
    return new UI_Generator();}
  
  public void refreshUI(){
    refreshViewer();
    refreshButtons();}

  void refreshViewer(){
    UI_Generator ui=(UI_Generator)getUI();
    ui.panviewer.repaint();}
  
  private void refreshButtons(){
    refreshStopGoButton();
    refreshModeButton();
    refreshIntervalButton();
    refreshDetailFloorButton();
    refreshExportDirButton();
    refreshExportSizeButton();}
  
  /*
   * if we are presently stopped then offer the option to go
   * if we are presently going then offer the option to stop
   */
  private void refreshStopGoButton(){
    UI_Generator ui=(UI_Generator)getUI();
    if(generator.isStop())
      ui.btngeneratestopgo.setText("Go");
    else
      ui.btngeneratestopgo.setText("Stop");}
  
  private void refreshModeButton(){
    UI_Generator ui=(UI_Generator)getUI();
    if(generator.isContinuous())
      ui.btngeneratemode.setText("Mode=Continuous");
    else
      ui.btngeneratemode.setText("Mode=Intermittant");}
  
  private void refreshIntervalButton(){
    UI_Generator ui=(UI_Generator)getUI();
    ui.pangenerateinterval.txtinterval.setText(String.valueOf(generator.getInterval()));}
  
  private void refreshDetailFloorButton(){
    UI_Generator ui=(UI_Generator)getUI();
    ui.pandetailfloor.txtfloor.setText(String.valueOf(generator.getDetailFloor()));}
  
  private void refreshExportDirButton(){
    UI_Generator ui=(UI_Generator)getUI();
    ui.btnexportdir.setText(imageexporter.getExportDirectory().getAbsolutePath());}
  
  private void refreshExportSizeButton(){
    UI_Generator ui=(UI_Generator)getUI();
    ui.panexportsize.txtsize.setText(String.valueOf(imageexporter.getImageSize()));}
  
  /*
   * ################################
   * COMPOSITION GENERATOR
   * ################################
   */

  public Generator generator=new Generator(); 
  
  /*
   * ################################
   * IMAGE EXPORTER
   * ################################
   */

  public ImageExporter imageexporter=new ImageExporter(); 
  
  /*
   * ################################
   * COMMAND
   * ################################
   */
  
  public void toggleStopGo(){
    generator.toggleStopGo();
    refreshButtons();}
  
  public void toggleMode(){
    generator.toggleMode();
    refreshButtons();}
  
  public void setInterval(String interval){ 
    try{
      long a=Long.valueOf(interval);
      generator.setInterval(a);
    }catch(Exception x){}
    refreshButtons();}
  
  public void setDetailFloor(String detailfloor){ 
    try{
      double a=Double.valueOf(detailfloor);
      generator.setDetailFloor(a);
    }catch(Exception x){}
    refreshButtons();}
  
  public void setExportDir(){
    JFileChooser fc=new JFileChooser();
    fc.setCurrentDirectory(GE.getLocalDir());
    fc.setDialogTitle("Specify the image export directory.");
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fc.setAcceptAllFileFilterUsed(false);
    int r=fc.showOpenDialog(GE.ge.uimain);
    if(r==JFileChooser.APPROVE_OPTION){
      imageexporter.setExportDirectory(fc.getSelectedFile());
      refreshButtons();}}
  
  public void setExportSize(String size){  
    try{
      int a=Integer.valueOf(size);
      imageexporter.setImageSize(a);
    }catch(Exception x){}
    refreshButtons();}
  
  public void openGrammarEditor(){
    GE.ge.setEditor(GE.ge.editor_grammar);}
  
  public void openAboutPopup(){
    JOptionPane.showMessageDialog(GE.ge.uimain,GE.ABOUT,"About this app",JOptionPane.PLAIN_MESSAGE);}
  
}
