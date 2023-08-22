package pl.sebastiancielma.Atipera.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int value, String message) {

    }
}
