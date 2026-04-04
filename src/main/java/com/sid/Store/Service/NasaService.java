package com.sid.Store.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.Store.dto.ApodMetadata;
import com.sid.Store.dto.NasaDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Map;

@Service
public class NasaService {

    private final String API_KEY = "4loT4Bo1hSX00nAyo1efdefMj0Z6dBWqEso0yjSI";
    private final String BASE_URL = "https://api.nasa.gov/planetary/apod";
    private final String IMAGE_DIR = "C:/Users/Sid/OneDrive/Desktop/Store/Store/apod-images/";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public NasaDto getTodayApod() {
        LocalDate date = LocalDate.now();
        return getApodByDate(date);
    }

    public NasaDto getApodByDate(LocalDate date) {
        try {
            String fileName = date + ".jpg";
            Path imagePath = Paths.get(IMAGE_DIR + fileName);
            Path metaPath = Paths.get(IMAGE_DIR + date + ".json");


            if (Files.exists(imagePath) && Files.exists(metaPath)) {
                ApodMetadata meta = objectMapper.readValue(metaPath.toFile(), ApodMetadata.class);

                return new NasaDto(
                        meta.getTitle(),
                        meta.getExplanation(),
                        "http://192.168.0.101:8080/apod-images/" + fileName
                );
            }

            //  2. Call NASA API
            String url = BASE_URL + "?api_key=" + API_KEY + "&date=" + date;

            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) return null;

            String mediaType = (String) response.get("media_type");

            //  Skip video → fallback
            if (!"image".equals(mediaType)) {
                return getApodByDate(date.minusDays(1));
            }

            String title = (String) response.get("title");
            String explanation = (String) response.get("explanation");

            String imageUrl = (String) response.get("hdurl");
            if (imageUrl == null) {
                imageUrl = (String) response.get("url");
            }

            // 3. Save image
            saveImage(imageUrl, imagePath);

            // 4. Save metadata
            saveMetadata(metaPath, title, explanation);

            //  5. Return response
            return new NasaDto(
                    title,
                    explanation,
                    "http://192.168.0.101:8080/apod-images/" + fileName
            );

        } catch (Exception e) {
            e.printStackTrace();
            return getApodByDate(LocalDate.now().minusDays(2));
        }
    }

    //  Save image
    private void saveImage(String imageUrl, Path path) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.createDirectories(path.getParent());

            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Image saved: " + path.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Save metadata JSON
    private void saveMetadata(Path path, String title, String explanation) {
        try {
            ApodMetadata meta = new ApodMetadata(title, explanation);

            objectMapper.writeValue(path.toFile(), meta);

            System.out.println("Metadata saved: " + path.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}