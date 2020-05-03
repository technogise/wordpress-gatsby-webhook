package com.shofee.webhook.contollers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class WebhookController {
    @Value("${gatsby.path}")
    private String gatsbyPath;
    @Value("${gatsby.command}")
    private String gatsbyCommand;
    @Value("${gatsby.app.path}")
    private String gatsbyAppPath;

    @PostMapping("/v1/triggerBuild")
    public ResponseEntity triggerBuild() throws IOException, InterruptedException {
            System.out.println("Building gatsby website...");
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("bash", "-c", gatsbyPath + " " + gatsbyCommand);
            builder.directory(new File(gatsbyAppPath));
            Process process = builder.start();

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

            if( exitCode == 0 )
                return ResponseEntity.ok("Successfully built gatsby app");
            else
                return ResponseEntity.badRequest().body("Error while processing");
    }
}
