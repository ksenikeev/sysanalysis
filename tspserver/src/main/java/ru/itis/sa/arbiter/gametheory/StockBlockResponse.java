package ru.itis.sa.arbiter.gametheory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockBlockResponse {
    private int status;
    private String statusString;
    private StockBlockModel block;
}
