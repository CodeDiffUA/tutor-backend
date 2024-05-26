package dev.backend.tutor.controllers.adviceControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/pwf-23rfpw-35vf-3rffp")
public class QuickController {


    @GetMapping
    public String getServiceIP() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ipAddress = inetAddress.getHostAddress();
        return ipAddress;
    }
}
