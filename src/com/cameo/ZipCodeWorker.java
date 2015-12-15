package com.cameo;

import org.w3c.dom.*;
import javax.swing.*;
import javax.xml.parsers.*;
import java.net.URL;
import java.util.ArrayList;

public class ZipCodeWorker extends SwingWorker<Document, Void>{

    //private String url;
    private String zip;
    private String miles;
    private String url;
    private static ArrayList<String> allZipCodesReturned;

    ZipCodesFetchedListener notifyMeWhenDone;

    //Constructor - sends data to the worker
    public ZipCodeWorker(String zip, String miles, ZipCodesFetchedListener notifyMe) {
        this.zip = zip;
        this.miles = miles;
        this.notifyMeWhenDone = notifyMe;
        this.url = "https://www.zipwise.com/webservices/radius.php?" +
                "key=nton18af45ivozgj&zip=" + zip + "&radius=" + miles + "&format=xml";
    }

    public void setZip(String zip) { this.zip = zip; }

    public void setMiles(String miles) { this.miles = miles; }

    @Override
    //Required method. Must have same return type as specified in class definition: ZipWorker extends SwingWorker<Document, Void>.
    protected Document doInBackground(){
        try {
            System.out.println("Background");
            URL urlObject = new URL(url);
            System.out.println(url);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDoc = builder.parse(urlObject.openStream());
            return xmlDoc;
        }
        catch (Exception e) {
            //All kinds of things can go wrong with the web request (wrong URL, no internet connection...) and
            //with parsing the XML (malformed XML for example). A real app would do some more useful error handling.
            System.out.println("Zip code radius request failed with exception " + e);
            return null; //Since we have to return something
        }
    }



    @Override
    protected void done() {
        try {

            Document xmlZipInfo = get(); //get() fetches what's returned from doInBackground
            System.out.println(xmlZipInfo);
            if (xmlZipInfo != null) {
                NodeList zipTexts = xmlZipInfo.getElementsByTagName("zip");
                System.out.println(zipTexts.getLength() + " zip codes were returned");

                //put all the zip codes returned into an arraylist
                //you mean an array?

                ZipCodeListBean.zipCodeList = new String[zipTexts.getLength()];
                //ZipCodeListBean.zipCodeList = new ArrayList<String>();
                for (int x = 0; x < zipTexts.getLength(); x++) {
                    Node node = zipTexts.item(x);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String depText = element.getFirstChild().getNodeValue();
                        ZipCodeListBean.zipCodeList[x] = depText;

                        //ZipCodeListBean.zipCodeList.add(depText); Matt Rowe's idea
                        //allZipCodesReturned.add(depText);
                    }
                }
                for (String zipCode : ZipCodeListBean.zipCodeList){
                    System.out.println(zipCode);
                }

            } else {
                //xmlZipInfo was null, so there was some kind of problem
                System.out.println("No XML data to parse, see doInBackground's error message");
            }
        } catch (Exception e) {
            //Again, many things can go wrong here during parsing the XML, such as unexpected XML structure
            System.out.println("Parsing XML failed with error " + e);
        }

        //Logically, this should not make database calls. All this should do is tell something - like a controller -
        //that it has found zip codes.
        //DatabaseManager.lessonsWithinACertainRadius();

        notifyMeWhenDone.zipCodesFetched(ZipCodeListBean.zipCodeList);
    }

    public static ArrayList<String> getAllZipCodesReturned() {
        return allZipCodesReturned;
    }
}
