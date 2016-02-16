package com.waynaut.utility;

import com.waynaut.exception.ParserException;
import com.waynaut.log.Log4J;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 *
 * @author Rahman Md Redwanur
 */

public class Configuration {

    private static Configuration instance;
    private Log4J log;
    private Document doc;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    
    private String loadInDatabse;
    private String showPogress;
    private String dbUrlPath;
    private String folderPathOfFiles;
    private String dbUserId;
    private String dbUserPass;
    private String conFilePath;
    
    public synchronized static Configuration getInstance(Log4J log, AppPath path) throws Exception{
        
        if(instance == null){
            instance = new Configuration(log,path);
        }
        
    	return instance;
    }
     
    private Configuration(Log4J log,AppPath path){
        
        try{
            
            this.log = log;
            String fileName = "Config.xml";
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            conFilePath = path.getApplicationDirectory()+"/"+fileName;
            
            if(!new File(conFilePath).exists()){
                createEmptyConfiguration();
                loadConfiguration();
            }
            else{
                loadConfiguration();
            }
            
         }
        catch(Exception exp){
            
            String systemError = String.format("%s->%s error : %s",Configuration.class.getName(),ParserException.getCallingMethod(),exp.getMessage());
            System.out.println(exp.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }

    /**
     * 
     * @return database URL path
     */
    public String getDbUrlPath() {
        return dbUrlPath;
    }

    /**
     * 
     * @return database user name 
     */
    public String getDbUserId() {
        return dbUserId;
    }

    /**
     * 
     * @return database user password
     */
    public String getDbUserPass() {
        return dbUserPass;
    }

    /**
     * 
     * @return Folder paths of file that have to read
     */
    public String getFolderPathOfFiles() {
        return folderPathOfFiles;
    }
    
    public String getLoadInDatabse() {
        return loadInDatabse;
    }

    public String getShowPogress() {
        return showPogress;
    }

    /**
     * Create an empty configuration file
     * @throws Exception general exception
     */
    private void createEmptyConfiguration() throws Exception{
        
        FileOutputStream fos = null;
        OutputStreamWriter out = null;
        
        try{
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.newDocument();
            
            Element rootElement = xmlDoc.createElement("Config");
            xmlDoc.appendChild(rootElement);

            Element PDataInDb = xmlDoc.createElement("LoadInDatabase");
            PDataInDb.appendChild(xmlDoc.createTextNode("Inactive"));
            rootElement.appendChild(PDataInDb);

            Element PShowProgress = xmlDoc.createElement("ShowProgress");
            PShowProgress.appendChild(xmlDoc.createTextNode("Inactive"));
            rootElement.appendChild(PShowProgress);
            
            
            Element PDbURL = xmlDoc.createElement("DbUrl");
            PDbURL.appendChild(xmlDoc.createTextNode("jdbc:mysql://localhost/dbname"));
            rootElement.appendChild(PDbURL);
                        
            Element EUserID  = xmlDoc.createElement("UserID");
            EUserID.appendChild(xmlDoc.createTextNode("root"));
            rootElement.appendChild(EUserID);
            
            Element EUserpass  = xmlDoc.createElement("UserPass");
            EUserpass.appendChild(xmlDoc.createTextNode("root"));
            rootElement.appendChild(EUserpass);

            Element PFilePath = xmlDoc.createElement("FolderPathOfFiles");
            PFilePath.appendChild(xmlDoc.createTextNode("C:/GTFS"));
            rootElement.appendChild(PFilePath);
           
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

            DOMSource source = new DOMSource(xmlDoc);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(source, result);

            fos = new FileOutputStream(new File(conFilePath));
            out = new OutputStreamWriter(fos, "UTF-8");
            out.write(result.getWriter().toString());
            
            out.close();
            fos.close();
            
            out = null;
            fos = null;
            
        }
        catch(DOMException | IllegalArgumentException | TransformerException exp){
            
            String systemError = String.format("%s->%s error : %s",Configuration.class.getName(),ParserException.getCallingMethod(),exp.getMessage());
            System.out.println(exp.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(String.format("%s",exp.getMessage()));
        }
        finally{
            
            if(out != null){
                out.close();
            }
            
            if(fos != null){
                fos.close();
            }
        }

    }
    
    /**
     * Load configuration xml file data
     * @throws Exception general exception
     */
    private void loadConfiguration() throws Exception{
       
        FileInputStream fis = null;     
        
        try{
            
            File file = new File(conFilePath);
            fis = new  FileInputStream(file);
            
            byte[] buffer = new byte[(int) file.length()];
                
            fis.read(buffer);
            fis.close();
            fis = null;
            
            StringReader reader     = new StringReader(new String(buffer));
            InputSource inputSource = new InputSource(reader);
            
            doc = db.parse(inputSource);
            
            NodeList nodeList = doc.getChildNodes();
           

            Node rootNode = nodeList.item(0);
            
            if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
                
                nodeList = rootNode.getChildNodes();

                for (int count = 0; count < nodeList.getLength(); count++) {

                    Node tempNode = nodeList.item(count);

                    if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                        if( null != tempNode.getNodeName()){

                            switch (tempNode.getNodeName()){ 

                                case "LoadInDatabase":
                                    loadInDatabse =  tempNode.getTextContent();
                                    break;
                                case "ShowProgress":
                                    showPogress =  tempNode.getTextContent();
                                    break;
                                case "DbUrl":
                                    dbUrlPath =  tempNode.getTextContent();
                                    break;
                                case "UserID":
                                    dbUserId =  tempNode.getTextContent();
                                    break;
                                case "UserPass":
                                    dbUserPass=  tempNode.getTextContent();
                                    break;
                                case "FolderPathOfFiles":
                                    folderPathOfFiles =  tempNode.getTextContent();
                                    break;
                            }
                        }
                    }
                }
            }

        }
        catch(SAXException | IOException | DOMException ex){
            String systemError = String.format("%s->%s error : %s",Configuration.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }
        finally{
            
            if(fis != null){
                fis.close();
            }
        }
    }
}
 
