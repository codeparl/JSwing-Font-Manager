package fonts;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;


/**
 * In this class we want to use the available system fonts to change the font
 * family of a JTextArea. we will also change its style. Our class extends
 * JPanel to layout its components.
 */

public class FontManager extends JPanel {

    private JTextArea textArea;
    private JComboBox<String> fontListBox;
    private JSpinner fontSizeSpinner;
    private JComboBox<String> fontStyleBox;
    private Font defaultFont;

    // declare the constructor
    public FontManager() {

        // divide this panel into two vertical
        // sections to lay out the items
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // add paddings on the content
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        defaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        // create two horizontal boxes using factory method
        // of Box
        Box sectionOne = Box.createHorizontalBox();
        Box sectionTwo = Box.createHorizontalBox();

        //now, initialize each component
        //and add it on its section
        initFontListBox(sectionOne);
        initFontStyleBox(sectionOne);
        initFontSizeSpinner(sectionOne);
        initTextArea(sectionTwo);

        //add the two section on our main panel
        add(sectionOne);
        add(sectionTwo);
    }

    //Handle the textarea 
    private void initTextArea(Box section) {
        // initialize the components
        String placeholder = "This is a JTextArea with 10 rows and 10 columns";
        textArea = new JTextArea(placeholder, 10, 10);
        // move the cursor to the end of text
        textArea.setCaretPosition(placeholder.length());

        textArea.requestFocusInWindow();//does not work!
        textArea.setFont(defaultFont);
//set text wrapping 
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        //add a gary border
        textArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
       
        //wrap the textarea within a JScrollPane 
        section.add(new JScrollPane(textArea));
    }

    private void initFontListBox(Box section) {

        //initialize the fontListBox with system fonts array
        //as its items
        fontListBox = new JComboBox<String>(getSystemFontList());

        //we will wrap our fontListBox in a panel with a label
        //aligned left
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.add(new JLabel("Font Family: "));
        pane.add(fontListBox);
        section.add(pane);

        //change the font family when the user selects a font name
        fontListBox.addItemListener((event) -> {
            String fontname = event.getItem().toString();
            Font newFont = new Font(fontname, defaultFont.getStyle(), defaultFont.getSize());
            defaultFont = newFont;
            textArea.setFont(defaultFont);
        });

    }

    private void initFontStyleBox(Box section) {

        String[] fontStyles = { "Plain", "Bold", "Italic", "Bold Italic" };
        fontStyleBox = new JComboBox<String>(fontStyles);

        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.add(new JLabel("Font Style: "));
        pane.add(fontStyleBox);
        section.add(pane);

        fontStyleBox.addItemListener((event) -> {
            int style = Font.PLAIN;
            switch (event.getItem().toString().toLowerCase()) {
                case "plain":
                    style = Font.PLAIN;
                    break;
                case "bold":
                    style = Font.BOLD;
                    break;
                case "italic":
                    style = Font.ITALIC;
                    break;
                case "bold italic":
                    style = Font.BOLD | Font.ITALIC;
                break;
            }// end switch

            Font newFont = new Font(defaultFont.getFamily(), style, defaultFont.getSize());
            defaultFont = newFont;
            textArea.setFont(defaultFont);
        });

    }


    private void initFontSizeSpinner(Box section) {
        int initFont = defaultFont.getSize();

        //create a spinner model to handle the content 
        //of the spinner itself, in our case it's number 
        SpinnerNumberModel mNumberModel = new SpinnerNumberModel(initFont, 8, 72, 2);
        fontSizeSpinner = new JSpinner(mNumberModel);

        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.add(new JLabel("Font Size: "));
        pane.add(fontSizeSpinner);
        section.add(pane);

        //change the font size when the user clicks the spinner 
        //buttons
        fontSizeSpinner.addChangeListener((event)->{
          int size=  (int) fontSizeSpinner.getModel().getValue();
          Font newFont = new Font(defaultFont.getFamily(), defaultFont.getStyle(), size);
          defaultFont = newFont;//we could store this object
          textArea.setFont(defaultFont);
        });

    }

    private String[] getSystemFontList() {
        // first obtain the local graphics env on this computer
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = ge.getAvailableFontFamilyNames();
        return fonts;
    }

}// end class
