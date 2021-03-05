package edu.wpi.cs3733.teamO.Sharing;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.LinkedList;
import lombok.SneakyThrows;

public class QRCodeThreader extends Thread {
  private final String fileToBeSent1;
  private final String fileToBeSent2;
  private final String fileToBeSent3;
  private final String fileToBeSent4;
  private final String fileToBeSent5;
  private final String fileToBeSent6;

  public QRCodeThreader(
      String fileToBeSent1,
      String fileToBeSent2,
      String fileToBeSent3,
      String fileToBeSent4,
      String fileToBeSent5,
      String fileToBeSent6) {
    this.fileToBeSent1 = fileToBeSent1;
    this.fileToBeSent2 = fileToBeSent2;
    this.fileToBeSent3 = fileToBeSent3;
    this.fileToBeSent4 = fileToBeSent4;
    this.fileToBeSent5 = fileToBeSent5;
    this.fileToBeSent6 = fileToBeSent6;
  }

  /**
   * initializes QR Code with link to the imgur album with all images
   *
   * @throws UnirestException
   */
  @SneakyThrows // probably can break the program
  @Override
  public void run() {

    LinkedList<String> albumInfo = ImgurFunctionality.createImgurAlbum();
    String albumID = albumInfo.get(0);
    String albumDeleteHash = albumInfo.get(1);

    // TODO: Add method to get rid off unused floors in pathfinding
    ImgurFunctionality.uploadToImgurAlbum("mapimg1.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg1.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg2.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg3.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg4.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg5.png", albumDeleteHash);
    ImgurFunctionality.uploadToImgurAlbum("mapimg6.png", albumDeleteHash);

    String albumLink = "https://imgur.com/a/" + albumID;

    SharingFunctionality.createQR(albumLink);
    // TODO: Improve speed of method by creating QR Code here, instead of calling other method
  }
}
