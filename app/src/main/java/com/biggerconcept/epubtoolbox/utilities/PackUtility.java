package com.biggerconcept.epubtoolbox.utilities;

import java.io.File;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Utility class for packing an expanded EPUB
 * 
 * @author Andrew Bigger
 */
public class PackUtility extends Utility {
    /**
     * Constructor for pack utility
     * 
     * @param inFile
     * @param outFile 
     */
    public PackUtility(File inFile, File outFile) {
        super(inFile, outFile);
    }
    
    /**
     * Packs an expanded EPUB into a valid EPUB container.
     * 
     * The first entry in the ZIP file has to be the mime type, and it should 
     * not be compressed.
     * 
     * Then all other files are added with the standard encryption standard.
     * 
     * @throws Exception 
     */
    @Override
    public void doJob() throws Exception {
        getResult().setTitle("");
        
        File mimetype = new File(inLocation.getAbsolutePath() 
                                 + File.separatorChar 
                                 + "mimetype");
        
        File [] files = inLocation.listFiles();
        
        ZipFile outEpub = new ZipFile(outLocation);
        
        if (outLocation.exists()) outLocation.delete();
        
        ZipParameters mimetype_parameters = new ZipParameters();
        ZipParameters content_parameters = new ZipParameters();
        
        mimetype_parameters.setCompressionMethod(Zip4jConstants.COMP_STORE);
        outEpub.addFile(mimetype, mimetype_parameters);
        
        content_parameters.setCompressionMethod(Zip4jConstants.COMP_STORE);
        content_parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
        
        for (File currentDoc : files) {
            if (currentDoc.isDirectory() == true) {
                outEpub.addFolder(currentDoc, content_parameters);
            } else if (currentDoc.getName().equals("mimetype")) {
                //ignore file
            } else {
                outEpub.addFile(currentDoc, content_parameters);
            }
        }
        
    }
    
}
