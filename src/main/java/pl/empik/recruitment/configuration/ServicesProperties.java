package pl.empik.recruitment.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "services")
public class ServicesProperties {

    private String github;

    public URI getGithubURI() {
        return URI.create(github);
    }

    public void setGithub(String github) {
        this.github = github;
    }
}
