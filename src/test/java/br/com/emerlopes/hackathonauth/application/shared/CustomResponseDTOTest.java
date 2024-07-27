package br.com.emerlopes.hackathonauth.application.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomResponseDTOTest {

    @Test
    void testGetData() {
        // Arrange
        String data = "testData";
        CustomResponseDTO<String> responseDTO = new CustomResponseDTO<>();
        responseDTO.setData(data);

        // Act
        String result = responseDTO.getData();

        // Assert
        assertEquals(data, result);
    }

    @Test
    void testSetData() {
        // Arrange
        String data = "testData";
        CustomResponseDTO<String> responseDTO = new CustomResponseDTO<>();

        // Act
        responseDTO.setData(data);
        String result = responseDTO.getData();

        // Assert
        assertEquals(data, result);
    }

    @Test
    void testChainSetData() {
        // Arrange
        String data = "testData";
        CustomResponseDTO<String> responseDTO = new CustomResponseDTO<>();

        // Act
        responseDTO = responseDTO.setData(data);

        // Assert
        assertEquals(data, responseDTO.getData());
    }
}