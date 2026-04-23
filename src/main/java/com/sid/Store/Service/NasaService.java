package com.sid.Store.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.Store.dto.ApodMetadata;
import com.sid.Store.dto.NasaDto;
import com.sid.Store.dto.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Map;

@Service
public class NasaService {



    @Value("${nasa.api.key}")
    private String API_KEY;
    private final String BASE_URL = "https://api.nasa.gov/planetary/apod";

    // Default for Docker if not provided
    @Value("${image.dir:/app/apod-images/}")
    private String IMAGE_DIR;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public NasaDto getTodayApod() {
        return getApodByDate(LocalDate.now());
    }

//    public String loginUser(UserData loginInfo){
//        return loginInfo.getUserName() ;
//    }
    public NasaDto getApodByDate(LocalDate date) {
        try {
            String fileName = date + ".jpg";

            Path imagePath = Paths.get(IMAGE_DIR, fileName);
            Path metaPath = Paths.get(IMAGE_DIR, date + ".json");

            // 1. Check cache
            if (Files.exists(imagePath) && Files.exists(metaPath)) {
                ApodMetadata meta = objectMapper.readValue(metaPath.toFile(), ApodMetadata.class);

                return new NasaDto(
                        meta.getTitle(),
                        meta.getExplanation(),
                        "/apod-images/" + fileName
                );
            }

            // 2. Call NASA API
            String url = BASE_URL + "?api_key=" + API_KEY + "&date=" + date;

            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) return null;

            String mediaType = (String) response.get("media_type");

            // Skip videos
            if (!"image".equals(mediaType)) {
                return getApodByDate(date.minusDays(1));
            }

            String title = (String) response.get("title");
            String explanation = (String) response.get("explanation");

            String imageUrl = (String) response.get("hdurl");
            if (imageUrl == null) {
                imageUrl = (String) response.get("url");
            }

            // 3. Save image + metadata
            saveImage(imageUrl, imagePath);
            saveMetadata(metaPath, title, explanation);

            // 4. Return response
            return new NasaDto(
                    title,
                    explanation,
                    "/apod-images/" + fileName
            );

        } catch (Exception e) {
            e.printStackTrace();
            return getApodByDate(LocalDate.now().minusDays(2));
        }
    }

    // Save image
    private void saveImage(String imageUrl, Path path) {
        try (InputStream in = new URL(imageUrl).openStream()) {

            Files.createDirectories(path.getParent());
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Image saved: " + path.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Save metadata
    private void saveMetadata(Path path, String title, String explanation) {
        try {
            Files.createDirectories(path.getParent());

            ApodMetadata meta = new ApodMetadata(title, explanation);
            objectMapper.writeValue(path.toFile(), meta);

            System.out.println("Metadata saved: " + path.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}