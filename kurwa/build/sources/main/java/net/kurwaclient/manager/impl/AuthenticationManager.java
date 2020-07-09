package net.kurwaclient.manager.impl;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import net.kurwaclient.Kurwa;
import net.kurwaclient.KurwaConstants;
import net.kurwaclient.manager.IKurwaManager;
import org.json.JSONException;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationManager implements IKurwaManager {
    public Kurwa kurwa;

    @Override
    public void load(Kurwa kurwa) {
        this.kurwa = kurwa;

        if(isAuthenticated()) {
            kurwa.isPlus = true;
            Kurwa.LOGGER.info("Authenticated with Kurwa+");
        }else{
            Kurwa.LOGGER.info("User is not authenticated with Kurwa+, buy it today!");
        }
    }

    private boolean isAuthenticated() {
        //the try statements look horrible i know i'm sorry :crying emoji:
        String hwid;
        try {
            hwid = retrieveHWID();
            Kurwa.LOGGER.info("HWID: " + hwid);
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Could not find your HWID! I cannot authenticate you with Kurwa+, if you think this is an issue on our end, feel free to contact developers.");
            return false;
        }

        HttpResponse<JsonNode> res;
        try {
            res = Unirest.get(KurwaConstants.WS_IP + "/auth?hwid=" + "test").asJson();
        }catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not establish connection to our webserver! I cannot authenticate you with Kurwa+, Make sure you have a working internet connection. If you think this is an issue on our end, feel free to contact developers.");
            return false;
        }
        try {
            if(res.getBody().getObject().getBoolean("found")) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Thank you hexeption!
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @author Hexeption
     */
    public static String retrieveHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String s = "";
        final String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
        final byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        final byte[] md5 = messageDigest.digest(bytes);
        int i = 0;
        for (final byte b : md5) {
            s += Integer.toHexString((b & 0xFF) | 0x300).substring(0, 3);
            i++;
        }
        return s;
    }
}
