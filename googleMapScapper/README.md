
# Google Map Scraper

A simple Java-based web scraping service that uses Playwright to retrieve the names and coordinates (latitude and longitude) of places of interest near a specified location from Google Maps. This service is built with Spring Boot and can be used to gather data on nearby landmarks for potential future applications such as weather forecasting, location-based recommendations, or regional analysis.

## Features

- Fetches the names and coordinates of notable places from Google Maps based on a user-defined location.
- Limits the number of places returned to reduce scraping time and improve reliability.
- Logs progress and errors during the scraping process.
- Stores place data in the `NearbyPlace` DTO (Data Transfer Object), ready for storage or further processing.

## Prerequisites

- Java 11 or higher
- Maven or Gradle (depending on your project setup)
- Google Chrome (for headless browser scraping using Playwright)
- [Playwright](https://playwright.dev/java/docs/intro) dependency for Java

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/ChinmayKuJena/googleMapScrapper.git
cd googleMapScrapper //(check the dir name properly )
```

### 2. Install Dependencies

Add the Playwright dependency to your `build.gradle` or `pom.xml` file.

**Gradle**:

```groovy
dependencies {
    implementation 'com.microsoft.playwright:playwright:1.18.0'
    // other dependencies
}
```

**Maven**:

```xml
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.18.0</version>
</dependency>
```

### 3. Run the Application

To start the application, use:

```bash
./gradlew bootRun  # for Gradle
# OR
mvn spring-boot:run  # for Maven
```

Once the application is running, it can be accessed locally for testing or integration.

## Usage

The primary method in this application is `scrape`, located in the `ScrapingService` class. This method takes a `String` argument (`searchForPlace`) representing the place to search for nearby points of interest.

### Example Call

```java
@Autowired
private ScrapingService scrapingService;

public void exampleScrape() {
    List<NearbyPlace> places = scrapingService.scrape("Bhubaneswar");
    places.forEach(System.out::println);
}
```

### Sample Output

The result is a list of `NearbyPlace` objects, each containing a name and the coordinates (latitude and longitude):

```json
[
    { "name": "NATIONAL FLAG", "latitude": 22.2733023, "longitude": 86.171495 },
    { "name": "Hanuman Batika", "latitude": 22.2558286, "longitude": 86.1775879 },
    { "name": "Asurghati waterfall", "latitude": 22.2945246, "longitude": 86.2797265 },
    { "name": "Jagannath Temple, Rairangpur", "latitude": 22.2558173, "longitude": 86.1720873 },
    { "name": "Unknown Place", "latitude": 22.2592996, "longitude": 86.1715902 }
]
```

## Code Overview

### Service Class: `ScrapingService`

- **Purpose**: Implements the core scraping functionality using Playwright.
- **Methods**:
    - `scrape(String searchForPlace)`: Main method that initiates the browser, navigates to Google Maps, performs the search, scrolls the results, and retrieves details for nearby places.
    - `extractCoordinatesFromUrl(String url)`: Helper method that extracts latitude and longitude from a URL.
    - `printProgress(int progress)`: Outputs a simple text-based progress bar to the console.

### Data Transfer Object (DTO): `NearbyPlace`

A simple class representing a nearby place with the following attributes:
- `name`: Name of the place (String)
- `latitude`: Latitude coordinate (double)
- `longitude`: Longitude coordinate (double)

## Limitations & Considerations

- **API Limitations**: This project uses a web scraping approach, which may be limited by Google’s terms of service. It is recommended to consider a Google Maps API if extensive or high-volume data is needed.
- **Browser Compatibility**: The scraper runs in headless mode using Chromium by default. Ensure Chromium is installed and compatible with your system.
- **Language & Encoding**: Some place names might not render correctly due to encoding. Adjustments may be required for handling international or special characters.

## Contributing

Contributions are welcome! Please submit a pull request with any improvements, bug fixes, or additional features.

1. Fork the project.
2. Create your feature branch (`git checkout -b feature/new-feature`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Open a pull request.


--- 
