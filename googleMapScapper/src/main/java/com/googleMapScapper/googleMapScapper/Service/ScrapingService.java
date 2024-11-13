package com.googleMapScapper.googleMapScapper.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.googleMapScapper.googleMapScapper.DTO.NearbyPlace;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

@Service
public class ScrapingService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ScrapingService.class);
    // Maximum number of listings to scrape
    private static final int MAX_LISTINGS_TO_SCRAPE = 15;

    public List<NearbyPlace> scrape(String searchForPlace) {
        logger.info("Starrting for {}", searchForPlace);
        List<NearbyPlace> businessList = new ArrayList<>();
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();
            page.navigate("https://www.google.com/maps");
            page.waitForTimeout(5000);

            page.locator("//input[@id='searchboxinput']").fill("Famous places of " + searchForPlace);
            page.keyboard().press("Enter");
            page.waitForTimeout(5000);

            page.hover("//a[contains(@href, 'https://www.google.com/maps/place')]");
            int previouslyCounted = 0;
            int maxListingsToScrape = 5; // Limit the number of listings

            while (true) {
                page.mouse().wheel(0, 10000);
                page.waitForTimeout(5000);
                int listingCount = page.locator("//a[contains(@href, 'https://www.google.com/maps/place')]").count();
                // Calculate progress and print progress bar
                int progress = (previouslyCounted * 100) / maxListingsToScrape; // Progress as a percentage
                printProgress(progress);
                // logger.info("CHeck1");
                if (listingCount > previouslyCounted) {
                    previouslyCounted = listingCount;
                    // Break the loop if we reach the desired number of listings
                    if (previouslyCounted >= maxListingsToScrape) {
                        break;
                    }
                } else {
                    break;
                }
            }

            List<ElementHandle> listings = page.locator("//a[contains(@href, 'https://www.google.com/maps/place')]")
                    .elementHandles().subList(0, Math.min(maxListingsToScrape, previouslyCounted));

            for (int i = 0; i < listings.size() && i < MAX_LISTINGS_TO_SCRAPE; i++) {
                try {
                    ElementHandle listing = listings.get(i);
                    listing.click();
                    page.waitForTimeout(5000);

                    NearbyPlace business = new NearbyPlace();

                    String ariaLabel = listing.getAttribute("aria-label");
                    business.setName(ariaLabel != null ? ariaLabel.split("·")[0].trim() : "N/A");
                    String href = listing.getAttribute("href");
                    Double[] coordinates = extractCoordinatesFromUrl(href);
                    business.setLatitude(coordinates[0]);
                    business.setLongitude(coordinates[1]);

                    businessList.add(business);
                    // logger.info(businessList.toString());

                    // Calculate progress
                    int progress = (int) ((i + 1) / (double) MAX_LISTINGS_TO_SCRAPE * 100);
                    printProgress(progress); // Print progress to console

                } catch (Exception e) {
                    logger.error("Error occurred: " + e.getMessage());
                }
            }

            browser.close();
            logger.info("\nScraping complete.");
            return businessList;
        } catch (Exception e) {
            logger.error(e.toString() + "<<=Error found in Scrapping ");
            ;
        }
        return null;
    }

    private Double[] extractCoordinatesFromUrl(String url) {
        try {
            String latStr = url.split("!3d")[1].split("!")[0];
            String lonStr = url.split("!4d")[1].split("!")[0];
            return new Double[] { Double.parseDouble(latStr), Double.parseDouble(lonStr) };
        } catch (Exception e) {
            return new Double[] { null, null }; // Return null if error occurs
        }
    }

    // Print progress in a similar fashion to tqdm
    private void printProgress(int progress) {
        final int progressBarLength = 50; // Length of the progress bar in characters
        int completed = progress * progressBarLength / 100; // Number of characters representing completed progress
        StringBuilder progressBar = new StringBuilder("[");

        for (int i = 0; i < progressBarLength; i++) {
            if (i < completed) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("] ").append(progress).append("%");

        // Print the progress bar, overwriting the previous line in the console
        System.out.print("\r" + progressBar.toString());
        // logger.info("\r" + progressBar.toString());
    }
}
