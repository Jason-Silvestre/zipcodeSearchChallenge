package br.com.silvestre.zipcodeSearch.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * AWS S3 Configuration class
 * Provides conditional bean creation based on active Spring profiles
 */
@Configuration
public class AwsConfig {

    @Value("${aws.access.key:default-access-key}")
    private String awsAccessKey;

    @Value("${aws.secret.key:default-secret-key}")
    private String awsSecretKey;

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    /**
     * Creates real AmazonS3 client bean ONLY when 'aws' profile is active
     */
    @Bean
    @Profile("aws")
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.fromName(awsRegion))
                .build();
    }

    /**
     * NO BEAN for non-AWS environments - this prevents the null injection issue
     * When 'aws' profile is not active, no AmazonS3 bean will be created
     * This means S3BackupService won't be instantiated at all
     */
    // No @Bean method for non-AWS environments
}