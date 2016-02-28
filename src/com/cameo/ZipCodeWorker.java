package com.cameo;

import org.w3c.dom.*;
import javax.swing.*;
import javax.xml.parsers.*;
import java.net.URL;
import java.util.ArrayList;

// This class uses www.zipwise.com to find all zip codes within a certain radius.

public class ZipCodeWorker extends SwingWorker<Document, Void>{

    private String zip;
    private String miles;
    private String url;
    private static ArrayList<String> allZipCodesReturned;

    //Listener will notify when the search for the zip codes within the set number of miles is complete
    ZipCodesFetchedListener notifyMeWhenDone;

    //Constructor - sends data to the worker
    public ZipCodeWorker(String zip, String miles, ZipCodesFetchedListener notifyMe) {
        this.zip = zip;
        this.miles = miles;
        this.notifyMeWhenDone = notifyMe;
        this.url = "https://www.zipwise.com/webservices/radius.php?" +
                "key=56wga97sk2vwkjda&zip=" + zip + "&radius=" + miles + "&format=xml";
        /*
        The following API key stopped working. Try it again if the above one stops working.
        The API key for cameo.hansell@minneapolis.edu
        this.url = "https://www.zipwise.com/webservices/radius.php?" +
                "key=nton18af45ivozgj&zip=" + zip + "&radius=" + miles + "&format=xml";
        */
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
            System.out.println("Zip code radius request failed with exception " + e);
            return null;
        }
    }

    @Override
    protected void done() {
        try {

            Document xmlZipInfo = get(); //get() fetches what's returned from doInBackground
            System.out.println(xmlZipInfo);

            //The following prints lines in the console for testing purposes
            if (xmlZipInfo != null) {
                NodeList zipTexts = xmlZipInfo.getElementsByTagName("zip");
                System.out.println(zipTexts.getLength() + " zip codes were returned");

                //Use of "Bean" suggested by Matt Rowe
                //Put all the zip codes returned into an array
                ZipCodeListBean.zipCodeList = new String[zipTexts.getLength()];
                for (int x = 0; x < zipTexts.getLength(); x++) {
                    Node node = zipTexts.item(x);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String depText = element.getFirstChild().getNodeValue();
                        ZipCodeListBean.zipCodeList[x] = depText;
                    }
                }

            } else {
                //if xmlZipInfo was null, there was some kind of problem
                System.out.println("No XML data to parse, see doInBackground's error message");
            }
        } catch (Exception e) {
            //Again, many things can go wrong here during parsing the XML, such as unexpected XML structure
            System.out.println("Parsing XML failed with error " + e);
        }

        //Notification of completion of search.
        notifyMeWhenDone.zipCodesFetched(ZipCodeListBean.zipCodeList);
    }

//    public static ArrayList<String> getAllZipCodesReturned() {
//        return allZipCodesReturned;
//    }
}
