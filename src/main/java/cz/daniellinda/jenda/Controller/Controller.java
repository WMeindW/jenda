package cz.daniellinda.jenda.Controller;

import cz.daniellinda.jenda.Data.ValueDriver;
import cz.daniellinda.jenda.Data.ValueEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private ValueDriver driver;

    @GetMapping("/action")
    public ResponseEntity<String> getAction() {
        List<ValueEntity> list = driver.findAll();
        return new ResponseEntity<>(list.getLast().getValue(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return new ResponseEntity<>(index, HttpStatus.OK);
    }

    @PostMapping("/set")
    public ResponseEntity<String> set(@RequestParam int value, HttpServletRequest request) {
        if (value >= 0 && value <= 100) {
            ValueEntity valueEntity = new ValueEntity();
            valueEntity.setValue(Integer.toString(value));
            valueEntity.setIpAddressRemote(request.getRemoteAddr());
            valueEntity.setIpAddressLocal(request.getLocalAddr());
            valueEntity.setTime(LocalDateTime.now());
            driver.save(valueEntity);
            return new ResponseEntity<>("Successful: " + value, HttpStatus.OK);
        }
        return new ResponseEntity<>("Fail: " + value, HttpStatus.BAD_REQUEST);
    }

    private String index = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Value Form</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 0;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        background-color: #f4f4f4;
                    }

                    .container {
                        background-color: #fff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }

                    form {
                        display: flex;
                        flex-direction: column;
                    }

                    label {
                        margin-bottom: 10px;
                    }

                    input {
                        padding: 8px;
                        margin-bottom: 15px;
                    }

                    button {
                        padding: 10px;
                        background-color: #007BFF;
                        color: #fff;
                        border: none;
                        border-radius: 4px;
                        cursor: pointer;
                    }

                    button:hover {
                        background-color: #0056b3;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <form action="/set" method="POST">
                        <label for="value">Enter a value (0-100):</label>
                        <input type="number" id="value" name="value" min="0" max="100" required>
                        <button type="submit">Submit</button>
                    </form>
                </div>
            </body>
            </html>
            """;
}
