package edu.wpi.cs3733.teamO.Sharing;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;

public class ImgurFunctionality {

  public static LinkedList<String> createImgurAlbum() throws UnirestException {

    LinkedList<String> result = new LinkedList<>();

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.post("https://api.imgur.com/3/album")
            .header("Authorization", "Bearer 87600038c4c60be5eff9d42dd09d5010f8cadde5")
            .field("title", "My Map Images")
            .asString();

    String res = response.getBody();

    // getting album ID to use it in the link
    String startId = res.substring(res.indexOf("id") + 5);
    String albumId = startId.substring(0, startId.indexOf("\""));
    result.add(albumId);

    // getting deleteHash for adding images to this album
    String start = res.substring(res.indexOf("deletehash") + 13);
    String deleteHash = start.substring(0, start.indexOf("\""));
    result.add(deleteHash);

    return result;
  }

  public static void uploadToImgur(String imageName, String albumID) throws UnirestException {
    String home = System.getProperty("user.home");
    String file = home + "/Downloads/" + imageName;

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.post("https://api.imgur.com/3/upload")
            .header("Authorization", "Client-ID 546c25a59c58ad7")
            .field("file", new File(file))
            .field("album", albumID)
            .asString();
  }

  public static String parseID(String msgBody) {
    msgBody = msgBody.substring(msgBody.indexOf("'id':'"));
    msgBody = msgBody.substring(0, msgBody.indexOf("','"));
    return msgBody;
  }

  public static void main(String[] args) throws Exception {
    String res =
        "{\"data\":{\"id\":\"4GdeKiq\",\"deletehash\":\"ROsDeYe56rdVsFp\"},\"success\":true,\"status\":200}";

    String startId = res.substring(res.indexOf("id") + 5);
    System.out.print(startId.substring(0, startId.indexOf("\"")));
    String start = res.substring(res.indexOf("deletehash") + 13);
    System.out.print(start.substring(0, start.indexOf("\"")));

    /*
       testing string
       String b =
           "{\"status\":200,\"success\":true,\"data\":{\"id\":\"ImYycv8\",\"deletehash\":\"626l0igShHmm0bE\",\"account_id\":146010003,\"account_url\":\"bwolive3733\",\"ad_type\":null,\"ad_url\":null,\"title\":null,\"description\":null,\"name\":\"\",\"type\":\"image/png\",\"width\":1288,\"height\":1059,\"size\":209967,\"views\":0,\"section\":null,\"vote\":null,\"bandwidth\":0,\"animated\":false,\"favorite\":false,\"in_gallery\":false,\"in_most_viral\":false,\"has_sound\":false,\"is_ad\":false,\"nsfw\":null,\"link\":\"https://i.imgur.com/ImYycv8.png\",\"tags\":[],\"datetime\":1614654552,\"mp4\":\"\",\"hls\":\"\"}}";

       String a = b.substring(b.indexOf("https"));
       System.out.print(a.substring(0, a.indexOf("png") + 3));
    */
  }
}