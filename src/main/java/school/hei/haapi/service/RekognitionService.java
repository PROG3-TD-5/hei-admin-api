package school.hei.haapi.service;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.EventParticipant;

@Service
@AllArgsConstructor
public class RekognitionService {

  private final EventParticipantService eventParticipantService;
  List<EventParticipant> eventParticipantList = eventParticipantService.getAll(0,100);

  public String comparedFace(String sourceImage , byte[] toCompare) throws Exception {
    Float similarityThreshold = 90F;
    sourceImage = sourceImage;
    String targetImage = sourceImage;
    ByteBuffer sourceImageBytes = ByteBuffer.wrap(toCompare);
    ByteBuffer targetImageBytes = ByteBuffer.wrap(toCompare);

    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    //Load source and target images and create input parameters
    try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
      sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
    } catch (Exception e) {
      System.out.println("Failed to load source image " + sourceImage);
      System.exit(1);
    }
    try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
      targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
    } catch (Exception e) {
      System.out.println("Failed to load target images: " + targetImage);
      System.exit(1);
    }

    Image source = new Image()
            .withBytes(sourceImageBytes);
    Image target = new Image()
            .withBytes(targetImageBytes);

    CompareFacesRequest request = new CompareFacesRequest()
            .withSourceImage(source)
            .withTargetImage(target)
            .withSimilarityThreshold(similarityThreshold);

    // Call operation
    CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);


    // Display results
    List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
    for (CompareFacesMatch match : faceDetails) {
      ComparedFace face = match.getFace();
      BoundingBox position = face.getBoundingBox();
      System.out.println("Face at " + position.getLeft().toString()
              + " " + position.getTop()
              + " matches with " + match.getSimilarity().toString()
              + "% confidence.");

    }
    List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

    return "There was " + uncompared.size()
            + " face(s) that did not match";
  }
}
