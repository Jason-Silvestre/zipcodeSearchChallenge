package br.com.silvestre.zipcodeSearch.service;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * S3 Backup Service - ONLY active when 'aws' profile is enabled
 * This service won't even be loaded when AWS is not configured
 */
@Service
@Profile("aws") // This service only exists when 'aws' profile is active
public class S3BackupService {

    private static final Logger logger = LoggerFactory.getLogger(S3BackupService.class);

    private final AmazonS3 amazonS3;
    private final ZipcodeSearchService zipcodeSearchService;
    private final ObjectMapper objectMapper;

    @Value("${aws.s3.bucket.name:zipcode-backups}")
    private String bucketName;

    @Autowired
    public S3BackupService(AmazonS3 amazonS3,
                           ZipcodeSearchService zipcodeSearchService,
                           ObjectMapper objectMapper) {
        this.amazonS3 = amazonS3;
        this.zipcodeSearchService = zipcodeSearchService;
        this.objectMapper = objectMapper;

        logger.info("S3BackupService initialized with bucket: {}", bucketName);
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void performBackup() {
        try {
            String backupData = "Backup performed at: " + LocalDateTime.now();
            String fileName = "backup-" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".txt";

            amazonS3.putObject(bucketName, fileName, backupData);
            logger.info("Backup successfully completed on S3: {}", fileName);

        } catch (Exception e) {
            logger.error("Error performing S3 backup: {}", e.getMessage(), e);
        }
    }
}